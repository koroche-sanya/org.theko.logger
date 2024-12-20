package test;

import java.io.FileOutputStream;

import org.theko.logger.GlobalLogger;
import org.theko.logger.LogFormatter;
import org.theko.logger.LogLevel;

public class Test4 {

    // Main method to run the demonstration
    public static void main(String[] args) {
        try {
            // Set up logger
            GlobalLogger.getLoggerOutput().setPreferredLevel(LogLevel.DEBUG);
            GlobalLogger.getLoggerOutput().setPattern(LogFormatter.DETAILED_INFO);
            GlobalLogger.getLoggerOutput().addOutputStream(new FileOutputStream("test4.log"));

            TaskRunner taskRunner = new TaskRunner();
            taskRunner.runTask1();
            taskRunner.runTask2();
            taskRunner.runTask3();
        } catch (Exception ex) {
            // Log the exception with a detailed message
            GlobalLogger.log(LogLevel.ERROR, "Exception: " + ex.getMessage() + ", caused by: " + ex.getClass());
        } finally {
            // Log when the program exits
            GlobalLogger.log(LogLevel.DEBUG, "Test4 exiting...");
            System.exit(0);
        }
    }

    // A class that simulates tasks
    static class TaskRunner {
        
        // Task 1: Simulate a simple task and log
        public void runTask1() {
            try {
                GlobalLogger.log(LogLevel.DEBUG, "Task 1 started...");
                // Simulate task processing
                Thread.sleep(500);
                GlobalLogger.log(LogLevel.DEBUG, "Task 1 completed.");
            } catch (InterruptedException e) {
                GlobalLogger.log(LogLevel.ERROR, "Task 1 encountered an error: " + e.getMessage());
                throw new RuntimeException("Task 1 interrupted.", e);
            }
        }

        // Task 2: Simulate a task with a deliberate exception
        public void runTask2() {
            try {
                GlobalLogger.log(LogLevel.DEBUG, "Task 2 started...");
                // Simulate an error occurring in Task 2
                if (true) {
                    throw new IllegalArgumentException("Simulated error in Task 2");
                }
                GlobalLogger.log(LogLevel.DEBUG, "Task 2 completed.");
            } catch (IllegalArgumentException e) {
                GlobalLogger.log(LogLevel.ERROR, "Task 2 failed: " + e.getMessage());
                throw new RuntimeException("Task 2 failed", e);
            }
        }

        // Task 3: Simulate a task that runs successfully
        public void runTask3() {
            try {
                GlobalLogger.log(LogLevel.DEBUG, "Task 3 started...");
                // Simulate task processing
                Thread.sleep(400);
                GlobalLogger.log(LogLevel.DEBUG, "Task 3 completed.");
            } catch (InterruptedException e) {
                GlobalLogger.log(LogLevel.ERROR, "Task 3 encountered an error: " + e.getMessage());
                throw new RuntimeException("Task 3 interrupted.", e);
            }
        }
    }

    // A helper class that simulates another task (could be for error handling)
    static class Helper {
        
        // Simulate a helper method that could throw an exception
        public void performHelp() {
            try {
                GlobalLogger.log(LogLevel.DEBUG, "Helper task started...");
                // Simulating an error
                throw new UnsupportedOperationException("Helper task is not supported.");
            } catch (UnsupportedOperationException e) {
                GlobalLogger.log(LogLevel.ERROR, "Helper task failed: " + e.getMessage());
            }
        }
    }
}
