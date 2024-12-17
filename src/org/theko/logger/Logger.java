package org.theko.logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Logger class that implements {@link ILogger}. This class is responsible for logging messages at various levels
 * (DEBUG, INFO, WARN, etc.) and outputting them to a specified {@link LoggerOutput}.
 * It stores log entries and allows access to them.
 */
public class Logger implements ILogger {
    
    /**
     * A list to store all the log entries.
     */
    protected List<LogEntry> logs;
    
    /**
     * The {@link LoggerOutput} instance where log entries are sent for output.
     */
    protected LoggerOutput loggerOutput;

    /**
     * Constructs a Logger instance with the specified LoggerOutput.
     * 
     * @param loggerOutput the {@link LoggerOutput} where the logs will be written.
     */
    public Logger (LoggerOutput loggerOutput) {
        this.loggerOutput = loggerOutput;
        this.logs = new ArrayList<>();

        if (loggerOutput == null || loggerOutput.os == null) {
            System.err.println("LoggerOutput passed is null.");
            this.log(LogLevel.WARN, "LoggerOutput passed is null.");
        }
    }

    /**
     * Logs a message at the specified log level and captures the stack trace information.
     * The log entry is added to the internal log list and sent to the {@link LoggerOutput}.
     * 
     * @param level   The log level (DEBUG, INFO, WARN, etc.).
     * @param message The message to be logged.
     */
    @Override
    public void log(LogLevel level, String message) {
        StackTraceElement[] stackTrace = getStackTrace();
        StackTraceElement callerElement = null;

        // Find the calling method to include in the log entry
        for (int i = 0; i < stackTrace.length; i++) {
            StackTraceElement element = stackTrace[i];
            
            if (element.getMethodName().equals("log") && element.getClassName().equals(this.getClass().getName())) {
                if (i + 1 < stackTrace.length) {
                    callerElement = stackTrace[i + 1];
                }
                break;
            }
        }

        // Create and add the log entry
        LogEntry log = new LogEntry(level, message, System.currentTimeMillis(), new CallerInfo(callerElement), new StackTraceInfo(stackTrace));
        logs.add(log);
        
        // Output the log entry if the loggerOutput is set
        if (loggerOutput != null) {
            loggerOutput.addToOutput(log);
        }
    }

    /**
     * Retrieves the stack trace of the current thread.
     * 
     * @return The stack trace of the current thread as an array of {@link StackTraceElement}.
     */
    protected StackTraceElement[] getStackTrace() {
        return Thread.currentThread().getStackTrace();
    }

    /**
     * Gets the most recent log entry.
     * 
     * @return The last log entry, or {@code null} if no logs exist.
     */
    @Override
    public LogEntry getLastLog() {
        if (logs == null || logs.size() <= 0) {
            return null;
        }
        return logs.get(logs.size() - 1);
    }

    /**
     * Retrieves all log entries stored in the logger.
     * 
     * @return A list containing all log entries.
     */
    @Override
    public List<LogEntry> getAllLogs() {
        return logs;
    }

    /**
     * Retrieves all log entries stored in the logger as an array.
     * 
     * @return An array containing all log entries.
     */
    @Override
    public LogEntry[] getAllLogsArray() {
        return logs.toArray(new LogEntry[0]);
    }
}
