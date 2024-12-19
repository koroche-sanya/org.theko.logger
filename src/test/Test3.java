package test;

import java.io.FileOutputStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.theko.logger.GlobalLogger;
import org.theko.logger.LogEntry;
import org.theko.logger.LogFormatter;
import org.theko.logger.LogLevel;

public class Test3 {
    
    public static void main(String[] args) {
        try {
            // Set up logger
            GlobalLogger.getLoggerOutput().setPreferredLevel(LogLevel.DEBUG);
            GlobalLogger.getLoggerOutput().setPattern(LogFormatter.DEFAULT_INFO);
            GlobalLogger.getLoggerOutput().setOutputStream(new FileOutputStream("test3.log"));

            // Executor service to run threads
            ExecutorService executor = Executors.newFixedThreadPool(3);

            // Submit tasks to threads
            for (int i = 1; i <= 3; i++) {
                final int taskId = i;
                executor.submit(() -> runTask(taskId));
            }

            // Shutdown executor after tasks are completed
            executor.shutdown();
            while (!executor.isTerminated()) {
                // Wait for all threads to finish
            }
            
            // Optionally, you can fetch the last log after execution
            LogEntry lastLog = GlobalLogger.getLastLog();
            System.out.println("Last log: " + lastLog.getMessage());

        } catch (Exception ex) {
            GlobalLogger.log(LogLevel.ERROR, "Exception: " + ex.getMessage() + ", with class: " + ex.getCause().getClass());
        } finally {
            GlobalLogger.log(LogLevel.DEBUG, "Exiting...");
            System.exit(0);
        }
    }

    /**
     * Simulates a task that each thread performs with logging at different steps.
     * 
     * @param taskId The ID of the task to be logged.
     */
    public static void runTask(int taskId) {
        // Set the current thread's name
        Thread.currentThread().setName("TaskThread-" + taskId);
        
        // Log the start of the task
        GlobalLogger.log(LogLevel.DEBUG, "Task " + taskId + " started by thread " + Thread.currentThread().getName());

        try {
            // Simulate some task processing with sleep (e.g., 2 seconds)
            Thread.sleep(2000);
            // Log the progress after some time
            GlobalLogger.log(LogLevel.DEBUG, "Task " + taskId + " processing by thread " + Thread.currentThread().getName());

            // Simulate more processing with a longer delay
            Thread.sleep(3000);

            // Log the completion of the task
            GlobalLogger.log(LogLevel.DEBUG, "Task " + taskId + " completed by thread " + Thread.currentThread().getName());
        } catch (InterruptedException e) {
            GlobalLogger.log(LogLevel.ERROR, "Task " + taskId + " interrupted by thread " + Thread.currentThread().getName());
        }
    }
}
