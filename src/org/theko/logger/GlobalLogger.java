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
        loggerOutput = new LoggerOutput(LogLevel.INFO);
        loggerOutput.addOutputStream(System.out);
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
