/*
 * MIT License
 *
 * Copyright (c) 2024 Sasha Soloviev
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
