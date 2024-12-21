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

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * LoggerOutput is responsible for outputting log entries to multiple {@link OutputStream} objects.
 * It filters log entries based on the preferred log level and formats them according to a specified pattern.
 */
public class LoggerOutput {
    
    /**
     * The list of {@link OutputStream}s where log entries are written to.
     */
    protected List<OutputStream> outputStreams;
    
    /**
     * The preferred log level. Only log entries with a level equal to or higher than this level will be output.
     */
    protected LogLevel preferredLevel;
    
    /**
     * The pattern used to format the log entries.
     * The default pattern is "[-time<HH:mm:ss:SSS, UTC>] -type | [-class.-method] > -message".
     */
    protected String pattern;

    /**
     * Constructs a LoggerOutput instance with the specified output streams and preferred log level.
     * 
     * @param preferredLevel the {@link LogLevel} representing the minimum level of logs to output.
     */
    public LoggerOutput(LogLevel preferredLevel) {
        this.outputStreams = new ArrayList<>();
        this.preferredLevel = preferredLevel;
        this.pattern = "[-time<HH:mm:ss:SSS, UTC>] -type | [-class.-method] > -message";
    }

    /**
     * Sets the pattern used for formatting log entries.
     * 
     * @param pattern the new pattern to use for formatting log entries.
     */
    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    /**
     * Sets the preferred log level.
     * 
     * @param preferredLevel the {@link LogLevel} to be set as the minimum level of logs to output.
     */
    public void setPreferredLevel(LogLevel preferredLevel) {
        this.preferredLevel = preferredLevel;
    }

    /**
     * Gets the preferred log level.
     * 
     * @return the {@link LogLevel} representing the minimum level of logs to output.
     */
    public LogLevel getPreferredLevel() {
        return preferredLevel;
    }

    /**
     * Gets the current pattern used for formatting log entries.
     * 
     * @return the pattern used for formatting log entries.
     */
    public String getPattern() {
        return pattern;
    }

    /**
     * Gets the list of {@link OutputStream}s where log entries are being written.
     * 
     * @return the list of {@link OutputStream}s used for logging.
     */
    public List<OutputStream> getOutputStreams() {
        return outputStreams;
    }

    /**
     * Adds an {@link OutputStream} to the list of output streams.
     * 
     * @param os the {@link OutputStream} to be added for logging.
     */
    public void addOutputStream(OutputStream os) {
        if (os != null) {
            outputStreams.add(os);
        }
    }

    /**
     * Removes all {@link OutputStream}s from the list.
     */
    public void removeAllOutputStreams() {
        outputStreams.clear();
    }

    /**
     * Checks if there are any {@link OutputStream}s in the list.
     * 
     * @return true if the list contains at least one {@link OutputStream}, false otherwise.
     */
    public boolean containsOutputStream() {
        return outputStreams != null && !outputStreams.isEmpty();
    }

    /**
     * Clears output streams list, and set single
     * 
     * @param os the {@link OutputStream} to be added for logging.
     */
    public void setSingleOutputStream(OutputStream os) {
        outputStreams.clear();
        outputStreams.add(os);
    }

    /**
     * Adds a log entry to all output streams if its level is equal to or higher than the preferred level.
     * The log entry is formatted using the specified pattern before being written to the output streams.
     * 
     * @param log the {@link LogEntry} to be added to the output.
     */
    public void addToOutput(LogEntry log) {
        if (log == null) {
            return;
        }
        if (log.level.ordinal() >= preferredLevel.ordinal()) {
            String formatted = LogFormatter.format(log, pattern) + "\n";
            byte[] bytes = formatted.getBytes(StandardCharsets.UTF_8);
            for (OutputStream os : outputStreams) {
                try {
                    os.write(bytes);
                } catch (IOException ioex) {
                    ioex.printStackTrace();
                }
            }
        }
    }

    public void close() {
        for (OutputStream os : outputStreams) {
            try {
                os.close();
            } catch (IOException ioex) {
                ioex.printStackTrace();
            }
        }
    }
}
