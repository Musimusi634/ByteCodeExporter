package com.musimusi634.bytecodeexporter.agent;

import com.musimusi634.bytecodeexporter.ByteCodeExporter;
import com.sun.tools.attach.VirtualMachine;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.jar.Attributes;
import java.util.jar.JarEntry;
import java.util.jar.JarOutputStream;
import java.util.jar.Manifest;

public class AgentLoader {
    public  static boolean loadAgent() throws Exception {
        String pid = String.valueOf(ProcessHandle.current().pid());
        VirtualMachine vm = null;
        String AgentJarPath = buildAgentJar().toAbsolutePath().toString();
        ByteCodeExporter.LOGGER.info("Agent Jar Created at " + AgentJarPath);

        try {
            vm = VirtualMachine.attach(pid);
            vm.loadAgent(AgentJarPath);
            ByteCodeExporter.LOGGER.info("Byte Code Exporter Agent Loaded!");
            return true;
        } catch (Throwable t) {
            ByteCodeExporter.LOGGER.error("agent load failed!", t);
            return false;
        }finally {
            if (vm != null) vm.detach();
        }
    }

    private static Path buildAgentJar() throws Exception {
        Manifest manifest = new Manifest();
        Attributes manifestAttributes = manifest.getMainAttributes();
        manifestAttributes.putValue("Manifest-Version", "1.0");
        manifestAttributes.putValue("Agent-Class", "com.musimusi634.bytecodeexporter.agent.ByteCodeExporterAgent");
        manifestAttributes.putValue("Premain-Class", "com.musimusi634.bytecodeexporter.agent.ByteCodeExporterAgent");
        manifestAttributes.putValue("Can-Redefine-Classes","true");
        manifestAttributes.putValue("Can-Retransform-Classes","true");

        Path agentJar = Files.createTempFile("bytecodeexporter-agent-",".jar");
        JarOutputStream jaroutputstream = new JarOutputStream(Files.newOutputStream(agentJar),manifest);

        copyClassFromJar(jaroutputstream,"com/musimusi634/bytecodeexporter/agent/AgentTransformer.class");
        copyClassFromJar(jaroutputstream,"com/musimusi634/bytecodeexporter/agent/ByteCodeExporterAgent.class");
        jaroutputstream.close();
        
        return agentJar;
    }

    private static void copyClassFromJar(JarOutputStream jaroutputstream,String path) throws Exception {
        InputStream inputStream = ByteCodeExporter.class.getClassLoader().getResourceAsStream(path);
        if (inputStream == null) return;
        jaroutputstream.putNextEntry(new JarEntry(path));
        jaroutputstream.write(inputStream.readAllBytes());
        jaroutputstream.closeEntry();
    }
}
