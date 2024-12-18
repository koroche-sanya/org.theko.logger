package org.theko.logger;

import java.util.List;

/**
 * A global logger wrapper that delegates to a shared static Logger instance.
 * Provides global logging access across the entire application.
 */
public class GlobalLogger {
    /**
     * A single static Logger instance shared across all GlobalLogger instances.
     */
    protected static final Logger logger;

    /**
     * A single static LoggerOutput instance shared across all GlobalLogger instances.
     */
    protected static final LoggerOutput loggerOutput;

    // Static initializer to set up the shared logger instance
    static {
        loggerOutput = new LoggerOutput(System.out, LogLevel.INFO);
        logger = new Logger(loggerOutput, 2);
    }

    private GlobalLogger () {
        throw new IllegalAccessError("Cannot make instance of GlobalLogger!");
    }

    /**
     * Logs a message at the specified log level.
     *
     * @param level   The log level (DEBUG, INFO, WARN, etc.).
     * @param message The message to be logged.
     */
    public static void log(LogLevel level, String message) {
        synchronized (logger) { // Ensure thread safety
            logger.log(level, message);
        }
    }

    /**
     * Retrieves the last logged message.
     *
     * @return The last log information.
     */
    public static LogEntry getLastLog() {
        synchronized (logger) {
            return logger.getLastLog();
        }
    }

    /**
     * Retrieves all logged messages as a list.
     *
     * @return A list of all log information.
     */
    public static List<LogEntry> getAllLogs() {
        synchronized (logger) {
            return logger.getAllLogs();
        }
    }

    /**
     * Retrieves all logged messages as an array.
     *
     * @return An array of all log information.
     */
    public static LogEntry[] getAllLogsArray() {
        synchronized (logger) {
            return logger.getAllLogsArray();
        }
    }

    /**
     * Provides direct access to the global LoggerOutput instance.
     *
     * @return The static LoggerOutput instance.
     */
    public static LoggerOutput getLoggerOutput() {
        return loggerOutput;
    }

    /**
     * Provides direct access to the global Logger instance.
     *
     * @return The static Logger instance.
     */
    public static Logger getLogger() {
        return logger;
    }
}
