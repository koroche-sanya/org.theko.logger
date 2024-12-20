package test;

import java.io.FileOutputStream;
import java.util.Scanner;

import org.theko.logger.GlobalLogger;
import org.theko.logger.LogFormatter;
import org.theko.logger.LogLevel;

public class Test1 {
    public static void main(String[] args) {
        // Text reverse utility
        try {
            GlobalLogger.getLoggerOutput().setPreferredLevel(LogLevel.DEBUG);
            GlobalLogger.getLoggerOutput().setPattern(LogFormatter.DETAILED_INFO);
            GlobalLogger.getLoggerOutput().addOutputStream(new FileOutputStream("test1.log"));

            GlobalLogger.log(LogLevel.DEBUG, "Scanner initializing...");

            Scanner scanner = new Scanner(System.in);
            GlobalLogger.log(LogLevel.DEBUG, "Scanner initialized.");

            for (int attempts = 0; attempts < 10; attempts++) {
                String txt = scanner.nextLine();

                GlobalLogger.log(LogLevel.INFO, "Received text: " + txt);

                GlobalLogger.log(LogLevel.DEBUG, "Reversing...");

                StringBuilder reversed = new StringBuilder();
                GlobalLogger.log(LogLevel.DEBUG, "StringBuilder was created.");
                GlobalLogger.log(LogLevel.INFO, "Text length is " + txt.length() + " characters.");

                for (int i = txt.length() - 1; i >= 0; i--) {

                    char c = txt.charAt(i);
                    GlobalLogger.log(LogLevel.DEBUG, "Iteration: " + i + ", character: " + c);
                    reversed.append(c);
                    GlobalLogger.log(LogLevel.DEBUG, "StringBuilder appended with charatcer: " + c);

                }
                String target = reversed.toString();

                GlobalLogger.log(LogLevel.DEBUG, "Loop completed.");
                GlobalLogger.log(LogLevel.INFO, "Result of reversation: " + target);

                System.out.println("Reversed: " + target);

                GlobalLogger.log(LogLevel.DEBUG, "Reversed text has been printed.");
            }

            GlobalLogger.log(LogLevel.INFO, "Attempts are empty. No reversations now.");

            scanner.close();
            GlobalLogger.log(LogLevel.DEBUG, "Scanner was been closed.");
        } catch (Exception ex) {
            GlobalLogger.log(LogLevel.ERROR, "Exception: " + ex.getMessage() + ", with class: " + ex.getCause().getClass());
        } finally {
            GlobalLogger.log(LogLevel.DEBUG, "Exiting...");
            System.exit(0);
        }
    }
}
