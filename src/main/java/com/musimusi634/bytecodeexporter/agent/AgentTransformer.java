package com.musimusi634.bytecodeexporter.agent;

import java.lang.instrument.ClassFileTransformer;
import java.security.ProtectionDomain;

public class AgentTransformer implements ClassFileTransformer {
    public static byte[] ClassByteCode = null;
    @Override
    public byte[] transform(Module module, ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer){
        ClassByteCode = classfileBuffer;
        return null;
    }
}