package org.theko.logger.trace;

import java.lang.reflect.Field;

/**
 * Holds information about a variable, including its class, name, type, and value.
 */
public class VariableInfo {
    private final Class<?> parentClass;
    private final String variableName;
    private final Class<?> variableType;
    private final Object variableValue;
    private final boolean isStatic;
    private final boolean isFinal;

    /**
     * Constructs a VariableInfo object to encapsulate information about a variable.
     *
     * @param parentClass    The class in which the variable is declared.
     * @param variableName   The name of the variable.
     * @param variableType   The type of the variable.
     * @param variableValue  The current value of the variable.
     * @param isStatic       Whether the variable is static.
     * @param isFinal        Whether the variable is final.
     */
    public VariableInfo(Class<?> parentClass, String variableName, Class<?> variableType, 
                        Object variableValue, boolean isStatic, boolean isFinal) {
        this.parentClass = parentClass;
        this.variableName = variableName;
        this.variableType = variableType;
        this.variableValue = variableValue;
        this.isStatic = isStatic;
        this.isFinal = isFinal;
    }

    public Class<?> getParentClass() {
        return parentClass;
    }

    public String getVariableName() {
        return variableName;
    }

    public Class<?> getVariableType() {
        return variableType;
    }

    public Object getVariableValue() {
        return variableValue;
    }

    public boolean isStatic() {
        return isStatic;
    }

    public boolean isFinal() {
        return isFinal;
    }
    /*
    // Skip for now
    @Override
    public String toString() {
        return "VariableInfo{" +
                "parentClass=" + parentClass.getName() +
                ", variableName='" + variableName + '\'' +
                ", variableType=" + variableType.getName() +
                ", variableValue=" + variableValue +
                ", isStatic=" + isStatic +
                ", isFinal=" + isFinal +
                '}';
    }*/

    /**
     * Extracts information about a field from a class and returns a VariableInfo object.
     *
     * @param parentClass   The class in which the field is declared.
     * @param field         The field to extract information from.
     * @return A VariableInfo object containing the field's information.
     */
    public static VariableInfo fromField(Class<?> parentClass, Field field, Object instance) {
        try {
            field.setAccessible(true);
            Object value = field.get(instance);
            boolean isStatic = java.lang.reflect.Modifier.isStatic(field.getModifiers());
            boolean isFinal = java.lang.reflect.Modifier.isFinal(field.getModifiers());
            return new VariableInfo(parentClass, field.getName(), field.getType(), value, isStatic, isFinal);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Error accessing field value", e);
        }
    }
}