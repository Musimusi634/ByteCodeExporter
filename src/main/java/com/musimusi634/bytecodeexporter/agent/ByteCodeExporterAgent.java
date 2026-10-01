package com.musimusi634.bytecodeexporter.agent;

import java.lang.instrument.Instrumentation;
import java.util.Arrays;

public class ByteCodeExporterAgent {
    private static Instrumentation instrumentation;
    public static void agentmain(String agentArgs, Instrumentation inst) {
        inst.addTransformer(new AgentTransformer(), true);
        System.out.println("[bytecodeexporter] agentmain called");
        instrumentation = inst;
    }
    public static void premain(String agentArgs, Instrumentation instrumentation){
        agentmain(agentArgs, instrumentation);
    }
    public static byte[] getByteCode(String classname){
        try {
            instrumentation.retransformClasses(Class.forName(classname,false,Thread.currentThread().getContextClassLoader()));
            return AgentTransformer.ClassByteCode;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
