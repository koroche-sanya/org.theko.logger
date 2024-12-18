package org.theko.logger;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A utility class for formatting log entries into human-readable strings based on customizable patterns.
 * 
 * Example patterns and their results:
 * <pre>
 * Pattern 1: "-time<HH:mm:ss:SSS, UTC> [-type] - [-method] > -message"
 * Result:    "12:04:53:294 INFO - exampleMethod > Here, the test message!"
 * 
 * Pattern 2: "[-time<ss:SSS, START>] [-type] [-module] > [-class] > [-method()] > -message"
 * Result:    "[194:846] ERROR java.base > StringBuilder > append() > Cannot do action"
 * 
 * Pattern 3: "-time<SSS, START> [-type] | -message"
 * Result:    "56724 WARN | Audio samples count, is too big. Simplifying..."
 * </pre>
 */
public class LogFormatter {
    
    /**
     * The start time of the program to calculate the elapsed time since the application started.
     */
    private static final long START_TIME = System.currentTimeMillis();
    
    // Private constructor to prevent instantiation of this utility class.
    private LogFormatter () {
        throw new IllegalAccessError("Cannot make instance of LogFormatter!");
    }

    /**
     * Formats a log entry according to a specified pattern.
     * <p>
     * The pattern can contain placeholders like <code>-time</code>, <code>-type</code>, <code>-message</code>, 
     * <code>-class</code>, <code>-method()</code>, and <code>-module</code> which will be replaced by relevant 
     * information from the provided log entry.
     * 
     * @param log     The log entry to be formatted.
     * @param pattern The pattern to use for formatting.
     * @return The formatted log string.
     * @throws IllegalArgumentException If the log entry or pattern is null or empty.
     */
    public static String format(LogEntry log, String pattern) {
        if (log == null || pattern == null || pattern.isEmpty()) {
            throw new IllegalArgumentException("LogEntry and pattern must not be null or empty");
        }

        String formatted = pattern;

        // Replace -time<TIME_PATTERN, TIMEZONE>
        Pattern timePattern = Pattern.compile("-time<([^,>]+), ([^>]+)>");
        Matcher timeMatcher = timePattern.matcher(formatted);
        StringBuffer timeBuffer = new StringBuffer();

        while (timeMatcher.find()) {
            String timePatternStr = timeMatcher.group(1);
            String timeZoneId = timeMatcher.group(2);
            SimpleDateFormat dateFormat = new SimpleDateFormat(timePatternStr);

            String replacement;
            if ("START".equalsIgnoreCase(timeZoneId)) {
                long elapsedTime = System.currentTimeMillis() - START_TIME;
                replacement = formatElapsedTime(elapsedTime, timePatternStr);
            } else {
                dateFormat.setTimeZone(TimeZone.getTimeZone(timeZoneId));
                replacement = dateFormat.format(new Date(log.getTime()));
            }

            timeMatcher.appendReplacement(timeBuffer, replacement);
        }
        timeMatcher.appendTail(timeBuffer);
        formatted = timeBuffer.toString();

        // Replace -type
        formatted = formatted.replace("-type", log.getLevel().toString());

        // Replace -message
        formatted = formatted.replace("-message", log.getMessage());

        // Replace -class
        if (log.getCallerInfo() != null) {
            formatted = formatted.replace("-class", log.getCallerInfo().getClassName());
        } else {
            formatted = formatted.replace("-class", "<unknown class>");
        }

        // Replace -method()
        if (log.getCallerInfo() != null) {
            formatted = formatted.replace("-method", log.getCallerInfo().getMethodName() + "()");
        } else {
            formatted = formatted.replace("-method", "<unknown method>");
        }

        // Replace -module (if applicable, fallback to Java 9+ modules if implemented)
        String moduleName = null;
        if (log.getCallerInfo() != null && log.getCallerInfo().getStackTraceElement() != null) {
            moduleName = log.getCallerInfo().getModuleName();
            if (moduleName == null) {
                moduleName = "<unknown module>";
            }
        }
        formatted = formatted.replace("-module", moduleName);

        return formatted;
    }

    /**
     * Formats the elapsed time since the application started into a string using the given pattern.
     * 
     * @param elapsedTime The elapsed time in milliseconds.
     * @param pattern     The pattern to format the elapsed time.
     * @return The formatted string representing the elapsed time.
     */
    private static String formatElapsedTime(long elapsedTime, String pattern) {
        long hours = elapsedTime / 3600000;
        long minutes = (elapsedTime % 3600000) / 60000;
        long seconds = (elapsedTime % 60000) / 1000;
        long millis = elapsedTime % 1000;

        return pattern
            .replace("HH", String.format("%02d", hours))
            .replace("mm", String.format("%02d", minutes))
            .replace("ss", String.format("%02d", seconds))
            .replace("SSS", String.format("%03d", millis));
    }
}
