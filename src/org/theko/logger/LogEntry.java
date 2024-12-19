package org.theko.logger;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

/**
 * Represents a log entry containing information about the log level, message,
 * timestamp, caller details, stack trace, and thread name.
 * <p>
 * This class provides methods to retrieve log details and display or format
 * them for output.
 */
public class LogEntry {
    /**
     * The severity level of the log (e.g., DEBUG, INFO, WARN, ERROR).
     */
    protected LogLevel level;

    /**
     * The actual log message.
     */
    protected String message;

    /**
     * The time the log was created, represented as milliseconds since epoch.
     */
    protected long time;

    /**
     * Information about the method or class where the log was generated.
     */
    protected CallerInfo caller;

    /**
     * Information about the stack trace at the time of logging.
     */
    protected StackTraceInfo stackTrace;

    /**
     * The name of the thread where the log was created.
     */
    protected String threadName;

    /**
     * Constructs a log entry with the provided details.
     *
     * @param level      The severity level of the log.
     * @param message    The log message.
     * @param time       The timestamp when the log was created.
     * @param caller     Information about the caller (method/class).
     * @param stackTrace Stack trace information for the log.
     * @param threadName The name of the thread where the log was created.
     */
    public LogEntry(LogLevel level, String message, long time, CallerInfo caller, StackTraceInfo stackTrace, String threadName) {
        if (level == LogLevel.NONE) {
            throw new IllegalArgumentException("Log level cannot be set to NONE.");
        }
        
        this.level = level;
        this.message = message;
        this.time = time;
        this.caller = caller;
        this.stackTrace = stackTrace;
        this.threadName = threadName;
    }

    /**
     * Writes the log information to the specified {@link OutputStream}.
     *
     * @param os The output stream to which the log information will be written.
     * @throws IOException If an I/O error occurs while writing to the stream.
     */
    public void showInfo(OutputStream os) throws IOException {
        os.write(toString().getBytes(StandardCharsets.UTF_8));
    }

    /**
     * Returns the severity level of the log.
     *
     * @return The log level.
     */
    public LogLevel getLevel() {
        return level;
    }

    /**
     * Returns the log message.
     *
     * @return The message of the log.
     */
    public String getMessage() {
        return message;
    }

    /**
     * Returns the timestamp when the log was created.
     *
     * @return The time of the log in milliseconds since epoch.
     */
    public long getTime() {
        return time;
    }

    /**
     * Returns the caller information for the log entry.
     *
     * @return The caller information.
     */
    public CallerInfo getCallerInfo() {
        return caller;
    }

    /**
     * Returns the stack trace information associated with the log.
     *
     * @return The stack trace information.
     */
    public StackTraceInfo getStackTraceInfo() {
        return stackTrace;
    }

    /**
     * Returns the name of the thread where the log was created.
     *
     * @return The thread name.
     */
    public String getThreadName() {
        return threadName;
    }

    /**
     * Sets the name of the thread where the log was created.
     *
     * @param threadName The thread name.
     */
    public void setThreadName(String threadName) {
        this.threadName = threadName;
    }

    /**
     * Formats the log entry into a readable string representation using the
     * default log pattern.
     *
     * @return A formatted string representing the log entry.
     */
    @Override
    public String toString() {
        return LogFormatter.format(this, LogFormatter.DEFAULT_INFO);
    }
}
