package org.theko.logger.trace;

public interface TraceListener {
    MethodInfo methodCalled();
    VariableInfo variableAssigned();
}
