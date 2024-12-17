package org.theko.logger;

/**
 * Enum representing different log levels.
 * Each level represents the severity or importance of the log message.
 */
public enum LogLevel {
    /**
     * Debug level, used for detailed troubleshooting information.
     */
    DEBUG,

    /**
     * Info level, used for general information about the application's state.
     */
    INFO,

    /**
     * Warning level, used for potentially harmful situations or behavior.
     */
    WARN,

    /**
     * Error level, used for error messages that indicate a problem in the application.
     */
    ERROR,

    /**
     * Critical level, used for severe errors that affect the application's functionality.
     */
    CRITICAL,

    /**
     * Fatal level, used for catastrophic failures that cause the application to crash.
     */
    FATAL,

    /**
     * None level, indicating no logging is active.
     */
    NONE
}
