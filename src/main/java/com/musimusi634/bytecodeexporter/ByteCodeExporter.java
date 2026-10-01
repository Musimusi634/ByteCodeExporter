package com.musimusi634.bytecodeexporter;

import com.mojang.logging.LogUtils;
import com.musimusi634.bytecodeexporter.agent.AgentLoader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.loading.FMLPaths;
import org.slf4j.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Mod(ByteCodeExporter.MODID)
public class ByteCodeExporter
{
    public static final String MODID = "bytecode_exporter";
    public static final Logger LOGGER = LogUtils.getLogger();
    public static Path DIR;
    public static boolean AGENTLOADED = false;

    static {
        DIR = FMLPaths.GAMEDIR.get().resolve("ByteCodeExporter");
        try {
            Files.createDirectories(DIR);
        }catch (IOException e){
            LOGGER.error("Failed to create ByteCodeExporter directory",e);
        }
        try {
            AGENTLOADED = AgentLoader.loadAgent();
        }catch (Exception e){
            LOGGER.error("Failed to load ByteCodeExporter agent",e);
        }
    }
}
