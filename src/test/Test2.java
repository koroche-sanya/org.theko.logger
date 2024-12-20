package test;

import java.io.FileOutputStream;

import org.theko.logger.GlobalLogger;
import org.theko.logger.LogFormatter;
import org.theko.logger.LogLevel;

public class Test2 {
    
    public static void main(String[] args) {
        try {
            // Set logger properties
            GlobalLogger.getLoggerOutput().setPreferredLevel(LogLevel.DEBUG);
            GlobalLogger.getLoggerOutput().setPattern(LogFormatter.DEFAULT_INFO);
            GlobalLogger.getLoggerOutput().addOutputStream(new FileOutputStream("test2.log"));

            // Log the start of the program
            GlobalLogger.log(LogLevel.DEBUG, "Starting Fibonacci calculation...");

            // Call the recursive Fibonacci function
            int n = 10;  // Fibonacci number to calculate
            int result = fibonacci(n);

            // Log the result of the Fibonacci calculation
            GlobalLogger.log(LogLevel.DEBUG, "Fibonacci of " + n + " is " + result);

            System.out.println("Fibonacci(" + n + ") = " + result);
        } catch (Exception ex) {
            GlobalLogger.log(LogLevel.ERROR, "Exception: " + ex.getMessage() + ", with class: " + ex.getCause().getClass());
        } finally {
            GlobalLogger.log(LogLevel.DEBUG, "Exiting...");
            System.exit(0);
        }
    }

    /**
     * Calculates the nth Fibonacci number using recursion and logs each step.
     * 
     * @param n The Fibonacci number to calculate.
     * @return The nth Fibonacci number.
     */
    public static int fibonacci(int n) {
        // Log the current step of the Fibonacci calculation
        GlobalLogger.log(LogLevel.DEBUG, "Calculating Fibonacci of " + n);
        
        if (n <= 1) {
            // Log the base case result
            GlobalLogger.log(LogLevel.DEBUG, "Fibonacci of " + n + " is " + n);
            return n;
        }

        // Recursive call for Fibonacci(n-1) and Fibonacci(n-2)
        int result = fibonacci(n - 1) + fibonacci(n - 2);

        // Log the result of the Fibonacci calculation at this step
        GlobalLogger.log(LogLevel.DEBUG, "Fibonacci of " + n + " is " + result);

        return result;
    }
}
