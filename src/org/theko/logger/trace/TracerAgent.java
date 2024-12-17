package org.theko.logger.trace;

import java.lang.instrument.Instrumentation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.security.ProtectionDomain;

import javassist.*;

/**
 * Java Agent for tracing method calls and variable assignments.
 */
public class TracerAgent {

    public static void premain(String agentArgs, Instrumentation inst) {
        inst.addTransformer(new TraceTransformer());
    }
    
    public static class TraceTransformer implements ClassFileTransformer {

        @Override
        public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) {
            if (!className.startsWith("org/theko/logger")) {
                return classfileBuffer;
            }

            try {
                // Use Javassist to modify the bytecode and add trace hooks
                ClassPool pool = ClassPool.getDefault();
                CtClass ctClass = pool.makeClass(new java.io.ByteArrayInputStream(classfileBuffer));

                // Add tracing to methods
                for (CtMethod ctMethod : ctClass.getDeclaredMethods()) {
                    ctMethod.insertBefore("System.out.println(\"Method called: \" + $0.getClass().getName() + \".\" + \"" + ctMethod.getName() + "\");");
                    ctMethod.insertAfter("System.out.println(\"Method returned: \" + $0.getClass().getName() + \".\" + \"" + ctMethod.getName() + "\");");
                }

                // Add tracing to fields (variables)
                for (CtField ctField : ctClass.getDeclaredFields()) {
                    ctField.insertBefore("System.out.println(\"Variable " + ctField.getName() + " accessed\");");
                }

                return ctClass.toBytecode();
            } catch (Exception e) {
                e.printStackTrace();
                return classfileBuffer;
            }
        }
    }
}
