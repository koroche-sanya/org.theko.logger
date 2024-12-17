package org.theko.logger;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

/**
 * LoggerOutput is responsible for outputting log entries to an {@link OutputStream}.
 * It filters log entries based on the preferred log level and formats them according to a specified pattern.
 */
public class LoggerOutput {
    
    /**
     * The {@link OutputStream} where log entries are written to.
     */
    protected OutputStream os;
    
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
     * Constructs a LoggerOutput instance with the specified output stream and preferred log level.
     * 
     * @param os the {@link OutputStream} where log entries will be written.
     * @param preferredLevel the {@link LogLevel} representing the minimum level of logs to output.
     */
    public LoggerOutput (OutputStream os, LogLevel preferredLevel) {
        this.os = os;
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
     * Adds a log entry to the output stream if its level is equal to or higher than the preferred level.
     * The log entry is formatted using the specified pattern before being written to the output stream.
     * 
     * @param log the {@link LogEntry} to be added to the output.
     */
    public void addToOutput(LogEntry log) {
        if (log == null) {
            return;
        }
        if (log.level.ordinal() >= preferredLevel.ordinal()) {
            String formatted = LogFormatter.format(log, pattern) + "\n";
            try {
                os.write(formatted.getBytes(StandardCharsets.UTF_8));
            } catch (IOException ioex) {
                ioex.printStackTrace();
            }
        }
    }
}
