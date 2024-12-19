package org.theko.logger;

import java.text.SimpleDateFormat;
import java.util.Calendar;
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

    /**
     * A minimal log pattern for concise logging.
     * <p>
     * Format includes:
     * - Timestamp in "HH:mm:ss:SSS" format (UTC).
     * - Log type (e.g., DEBUG, INFO).
     * - Log message.
     * </p>
     * Example output: "[12:34:56:789, UTC] INFO | Application started successfully."
     */
    public static final String MINIMAL_INFO = "[-time<HH:mm:ss:SSS, UTC>] -type | -message";
    /**
     * A default log pattern for general-purpose logging.
     * <p>
     * Format includes:
     * - Timestamp in "yyyy-MM-dd HH:mm:ss" format (UTC).
     * - Log type (e.g., DEBUG, INFO).
     * - Thread name for multi-threaded application debugging.
     * - Class and method from where the log entry originated.
     * - Log message.
     * </p>
     * Example output: "[2024-12-19 12:34:56, UTC] INFO | [Thread: main] | [MyClass.myMethod] > Operation completed."
     */
    public static final String DEFAULT_INFO = "[-time<yyyy-MM-dd HH:mm:ss, UTC>] -type | [Thread: -thread] | [-class.-method] > -message";

        /**
     * A detailed log pattern for comprehensive debugging and tracing.
     * <p>
     * Format includes:
     * - Timestamp in "yyyy-MM-dd HH:mm:ss:SSS" format (UTC).
     * - Log type (e.g., DEBUG, INFO).
     * - Thread name for multi-threaded application debugging.
     * - File name and line number where the log entry originated.
     * - Class and method from where the log entry originated.
     * - Log message.
     * </p>
     * Example output:
     * "[2024-12-19 12:34:56:789, UTC] DEBUG | [Thread: main] | [File: MyClass.java, Line: 42] | [MyClass.myMethod] > Debugging started."
     */
    public static final String DETAILED_INFO = "[-time<yyyy-MM-dd HH:mm:ss:SSS, UTC>] -type | [Thread: -thread] | [File: -file, Line: -line] | [-class.-method] > -message";
    
    // Private constructor to prevent instantiation of this utility class.
    private LogFormatter () {
        throw new IllegalAccessError("Cannot make instance of LogFormatter!");
    }

    /**
     * Formats a log entry according to a specified pattern.
     * <p>
     * The pattern can contain placeholders like <code>-time</code>, <code>-type</code>, <code>-message</code>, 
     * <code>-class</code>, <code>-method()</code>, <code>-module</code>, and <code>-native<onTrue, onFalse></code> 
     * which will be replaced by relevant information from the provided log entry.
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

        // Replace -thread
        if (log.getThreadName() != null && !log.getThreadName().isEmpty()) {
            formatted = formatted.replace("-thread", log.getThreadName());
        } else {
            formatted = formatted.replace("-thread", "<unknown thread>");
        }

        // Replace -class, -method, -module, -file, -line
        if (log.getCallerInfo() != null) {
            formatted = formatted.replace("-class", log.getCallerInfo().getClassName());
            formatted = formatted.replace("-method", log.getCallerInfo().getMethodName());
            formatted = formatted.replace("-file", log.getCallerInfo().getFileName());
            formatted = formatted.replace("-line", String.valueOf(log.getCallerInfo().getLineNumber()));

            String moduleName = log.getCallerInfo().getModuleName();
            if (moduleName != null && !moduleName.isEmpty()) {
                formatted = formatted.replace("-module", moduleName);
            } else {
                formatted = formatted.replace("-module", "<unknown module>");
            }
        } else {
            formatted = formatted.replace("-class", "<unknown class>");
            formatted = formatted.replace("-method", "<unknown method>");
            formatted = formatted.replace("-module", "<unknown module>");
            formatted = formatted.replace("-file", "<unknown file>");
            formatted = formatted.replace("-line", "<unknown line>");
        }

        // Replace -native<onTrue, onFalse>
        Pattern nativePattern = Pattern.compile("-native<([^,>]+), ([^>]+)>");
        Matcher nativeMatcher = nativePattern.matcher(formatted);
        StringBuffer nativeBuffer = new StringBuffer();

        while (nativeMatcher.find()) {
            String onTrue = nativeMatcher.group(1);
            String onFalse = nativeMatcher.group(2);
            boolean isNative = log.getCallerInfo() != null && log.getCallerInfo().isNativeMethod();
            String replacement = isNative ? onTrue : onFalse;

            nativeMatcher.appendReplacement(nativeBuffer, replacement);
        }
        nativeMatcher.appendTail(nativeBuffer);
        formatted = nativeBuffer.toString();

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
    
        // Get current date
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1; // Months are zero-based
        int day = calendar.get(Calendar.DAY_OF_MONTH);
    
        return pattern
            .replace("yyyy", String.format("%04d", year))
            .replace("MM", String.format("%02d", month))
            .replace("dd", String.format("%02d", day))
            .replace("HH", String.format("%02d", hours))
            .replace("mm", String.format("%02d", minutes))
            .replace("ss", String.format("%02d", seconds))
            .replace("SSS", String.format("%03d", millis));
    }
}
