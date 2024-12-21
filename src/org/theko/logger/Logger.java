/*
 * MIT License
 *
 * Copyright (c) 2024 Alex Krasnobaev
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
*/

package org.theko.logger;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * Logger class that implements {@link ILogger}.
 * This class is responsible for logging messages at various levels (DEBUG, INFO, WARN, etc.)
 * and outputting them to a specified {@link LoggerOutput}.
 * It stores log entries and provides access to them.
 */
public class Logger implements ILogger {
    
    /**
     * A list to store all the log entries created by the logger.
     */
    protected List<LogEntry> logs;
    
    /**
     * The {@link LoggerOutput} instance where log entries are sent for output.
     */
    protected LoggerOutput loggerOutput;

    /**
     * Offset value used to determine the caller's position in the stack trace.
     */
    private final int stackFunctionOffset;

    private Consumer<LogEntry> onLogCreated; // Handler for log creation events

    /**
     * Constructs a Logger instance with the specified {@link LoggerOutput} and stack trace offset.
     *
     * @param loggerOutput       The {@link LoggerOutput} where the logs will be written.
     * @param stackFunctionOffset Offset for identifying the caller in the stack trace.
     */
    public Logger(LoggerOutput loggerOutput, int stackFunctionOffset) {
        this.loggerOutput = loggerOutput;
        this.logs = new ArrayList<>();
        this.stackFunctionOffset = stackFunctionOffset;

        if (loggerOutput == null || !loggerOutput.containsOutputStream()) {
            System.err.println("LoggerOutput passed is null.");
            this.log(LogLevel.WARN, "LoggerOutput passed is null.");
        }
    }

    /**
     * Constructs a Logger instance with the specified {@link LoggerOutput}.
     * Uses a default stack trace offset of 1.
     * 
     * @param loggerOutput The {@link LoggerOutput} where the logs will be written.
     */
    public Logger(LoggerOutput loggerOutput) {
        this(loggerOutput, 1);
    }

    /**
     * Logs a message at the specified log level. 
     * Captures caller information and stack trace details.
     * The log entry is added to the internal log list and sent to the {@link LoggerOutput}.
     * 
     * @param level   The severity level of the log (DEBUG, INFO, WARN, etc.).
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
                if (i + stackFunctionOffset < stackTrace.length) {
                    callerElement = stackTrace[i + stackFunctionOffset];
                }
                break;
            }
        }

        // Create and add the log entry
        LogEntry log = new LogEntry(
                level,
                message,
                System.currentTimeMillis(),
                Thread.currentThread().getName(),
                new CallerInfo(callerElement),
                new StackTraceInfo(stackTrace)
            );
        logs.add(log);
        
        // Output the log entry if the loggerOutput is set
        if (loggerOutput != null) {
            loggerOutput.addToOutput(log);
        }

        if (onLogCreated != null) {
            onLogCreated.accept(log);
        }
    }

    /**
     * Retrieves the stack trace of the current thread.
     * 
     * @return An array of {@link StackTraceElement} objects representing the current thread's stack trace.
     */
    protected StackTraceElement[] getStackTrace() {
        return Thread.currentThread().getStackTrace();
    }

    /**
     * Sets the handler for when a new log is created.
     *
     * @param handler A consumer that takes the last log entry as an argument.
     */
    public void setOnLogCreated(Consumer<LogEntry> handler) {
        this.onLogCreated = handler;
    }

    /**
     * Retrieves the most recent log entry recorded by the logger.
     * 
     * @return The last {@link LogEntry}, or {@code null} if no logs exist.
     */
    @Override
    public LogEntry getLastLog() {
        if (logs == null || logs.isEmpty()) {
            return null;
        }
        return logs.get(logs.size() - 1);
    }

    /**
     * Retrieves all log entries recorded by the logger.
     * 
     * @return A list containing all {@link LogEntry} instances.
     */
    @Override
    public List<LogEntry> getAllLogs() {
        return logs;
    }

    /**
     * Retrieves all log entries recorded by the logger as an array.
     * 
     * @return An array of {@link LogEntry} objects.
     */
    @Override
    public LogEntry[] getAllLogsArray() {
        return logs.toArray(new LogEntry[0]);
    }
}
