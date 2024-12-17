package org.theko.logger;

import java.util.List;

/**
 * ILogger interface defines the contract for logging functionality.
 * It provides methods for logging messages, retrieving the last log entry,
 * and fetching all log entries in list or array format.
 */
public interface ILogger {
    /**
     * Logs a message at the specified log level.
     *
     * @param level   The severity level of the log (e.g., DEBUG, INFO, WARN, ERROR).
     * @param message The log message to record.
     */
    public void log(LogLevel level, String message);

    /**
     * Retrieves the last logged message.
     *
     * @return The last log entry as a {@link LogEntry} object.
     *         Returns null if no logs are available.
     */
    public LogEntry getLastLog();

    /**
     * Retrieves all logged messages as a list.
     *
     * @return A list of {@link LogEntry} objects representing all log entries.
     *         Returns an empty list if no logs are available.
     */
    public List<LogEntry> getAllLogs();

    /**
     * Retrieves all logged messages as an array.
     *
     * @return An array of {@link LogEntry} objects representing all log entries.
     *         Returns an empty array if no logs are available.
     */
    public LogEntry[] getAllLogsArray();
}
