package org.theko.logger.trace;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * Holds information about a method, including its class, name, input arguments, return type, and other modifiers.
 */
public class MethodInfo {
    private final Class<?> parentClass;
    private final String methodName;
    private final Class<?> returnType;
    private final Object[] inputArgs;
    private final Object returnObject;
    private final boolean isStatic;
    private final boolean isFinal;

    /**
     * Constructs a MethodInfo object to encapsulate information about a method.
     *
     * @param parentClass   The class in which the method is declared.
     * @param methodName    The name of the method.
     * @param returnType    The return type of the method.
     * @param inputArgs     The input arguments passed to the method.
     * @param returnObject  The return value of the method.
     * @param isStatic      Whether the method is static.
     * @param isFinal       Whether the method is final.
     */
    public MethodInfo(Class<?> parentClass, String methodName, Class<?> returnType, 
                      Object[] inputArgs, Object returnObject, boolean isStatic, boolean isFinal) {
        this.parentClass = parentClass;
        this.methodName = methodName;
        this.returnType = returnType;
        this.inputArgs = inputArgs;
        this.returnObject = returnObject;
        this.isStatic = isStatic;
        this.isFinal = isFinal;
    }

    public Class<?> getParentClass() {
        return parentClass;
    }

    public String getMethodName() {
        return methodName;
    }

    public Class<?> getReturnType() {
        return returnType;
    }

    public Object[] getInputArgs() {
        return inputArgs;
    }

    public Object getReturnObject() {
        return returnObject;
    }

    public boolean isStatic() {
        return isStatic;
    }

    public boolean isFinal() {
        return isFinal;
    }

    @Override
    public String toString() {
        return "MethodInfo{" +
                "parentClass=" + parentClass.getName() +
                ", methodName='" + methodName + '\'' +
                ", returnType=" + returnType.getName() +
                ", inputArgs=" + (inputArgs != null ? inputArgs.length : 0) + " args" +
                ", returnObject=" + returnObject +
                ", isStatic=" + isStatic +
                ", isFinal=" + isFinal +
                '}';
    }

    /**
     * Extracts information about a method from a class and returns a MethodInfo object.
     *
     * @param parentClass   The class in which the method is declared.
     * @param method        The method to extract information from.
     * @param inputArgs     The arguments passed to the method.
     * @param returnObject  The return value of the method.
     * @return A MethodInfo object containing the method's information.
     */
    public static MethodInfo fromMethod(Class<?> parentClass, Method method, Object[] inputArgs, Object returnObject) {
        boolean isStatic = Modifier.isStatic(method.getModifiers());
        boolean isFinal = Modifier.isFinal(method.getModifiers());
        return new MethodInfo(parentClass, method.getName(), method.getReturnType(), inputArgs, returnObject, isStatic, isFinal);
    }
}