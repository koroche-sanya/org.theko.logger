package org.theko.logger.test;

import java.io.FileOutputStream;
import java.io.PrintStream;

import org.theko.logger.LogLevel;
import org.theko.logger.Logger;
import org.theko.logger.LoggerOutput;
import org.theko.logger.timer.WatchTimer;

public class LoggerTest {
    Logger logger;
    LoggerOutput out;

    public LoggerTest () {
        try {
            // Create a PrintStream to write to a file
            PrintStream fileOut = new PrintStream(new FileOutputStream("logs.txt", true));
            out = new LoggerOutput(fileOut, LogLevel.DEBUG);
            logger = new Logger(out);

            WatchTimer timer = new WatchTimer();

            timer.start();
            doSomething();
            timer.stop();

            logger.log(LogLevel.INFO, "doSomething() delay is " + timer.getElapsed() + " ms.");

            // Close the file stream after logging
            fileOut.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void doSomething() {
        for (int i = 0; i < 1000; i++) { // Repeat many times
            // Log a message
            logger.log(LogLevel.DEBUG, "Iteration " + i);
    
            // Perform some calculations
            int result = (i * 2) - (i / 3) + 7;
            logger.log(LogLevel.INFO, "Calculated result: " + result);
    
            // String manipulations
            String text = "Iteration-" + i;
            String reversed = new StringBuilder(text).reverse().toString();
            logger.log(LogLevel.DEBUG, "Reversed text: " + reversed);
    
            // Conditional logic
            if (result % 2 == 0) {
                logger.log(LogLevel.WARN, "Result is even: " + result);
            } else {
                logger.log(LogLevel.ERROR, "Result is odd: " + result);
            }
    
            // Call helper methods
            helperMethod1(i);
            helperMethod2(reversed);
    /*
            // Simulate delay (optional)
            try {
                Thread.sleep(1); // 1 ms delay
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                logger.log(LogLevel.CRITICAL, "Thread interrupted");
            }*/
        }
    }
    
    private void helperMethod1(int number) {
        logger.log(LogLevel.INFO, "HelperMethod1 processing number: " + number);
        int squared = number * number;
        logger.log(LogLevel.DEBUG, "Squared value: " + squared);
    }
    
    private void helperMethod2(String text) {
        logger.log(LogLevel.INFO, "HelperMethod2 processing text: " + text);
        String upperCase = text.toUpperCase();
        logger.log(LogLevel.DEBUG, "Uppercase text: " + upperCase);
    }
    

    public static void main(String[] args) {
        new LoggerTest();
    }
}
