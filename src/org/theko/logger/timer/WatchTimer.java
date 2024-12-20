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

package org.theko.logger.timer;

/**
 * A simple timer utility class to measure elapsed time in milliseconds.
 * It supports starting, stopping, pausing, resuming, and resetting the timer.
 */
public class WatchTimer {
    private long started = -1;       // Start time in milliseconds
    private long ended = -1;         // End time in milliseconds
    private long pausedTime = 0;     // Total paused duration in milliseconds
    private long pauseStart = -1;    // Time when pause started

    /**
     * Default constructor for WatchTimer.
     * Initializes the timer but does not start it.
     */
    public WatchTimer() {
        // Default constructor
    }

    /**
     * Starts the timer if it has not been started already.
     * If the timer is paused, this resets the pause state.
     */
    public void start() {
        if (started == -1) {
            this.started = System.currentTimeMillis();
        }
        this.ended = -1;    // Clear the end time as the timer is running
        this.pausedTime = 0;
        this.pauseStart = -1;
    }

    /**
     * Pauses the timer if it is running.
     * Paused time will not be included in the elapsed time.
     */
    public void pause() {
        if (isRunning() && pauseStart == -1) {
            this.pauseStart = System.currentTimeMillis();
        }
    }

    /**
     * Resumes the timer if it is currently paused.
     */
    public void resume() {
        if (pauseStart != -1) {
            this.pausedTime += System.currentTimeMillis() - pauseStart;
            this.pauseStart = -1;
        }
    }

    /**
     * Stops the timer and records the end time.
     */
    public void stop() {
        if (started == -1) {
            throw new IllegalStateException("Timer has not been started yet.");
        }
        if (pauseStart != -1) {
            resume(); // Automatically resume paused timer before stopping
        }
        this.ended = System.currentTimeMillis();
    }

    /**
     * Returns the total elapsed time in milliseconds.
     * If the timer is paused or running, it calculates elapsed time up to the current time.
     *
     * @return Elapsed time in milliseconds.
     */
    public long getElapsed() {
        if (started == -1) {
            throw new IllegalStateException("Timer has not been started yet.");
        }
        long currentTime = (ended == -1 ? System.currentTimeMillis() : ended);
        long pausedDuration = (pauseStart == -1) ? pausedTime : pausedTime + (System.currentTimeMillis() - pauseStart);
        return (currentTime - started) - pausedDuration;
    }

    /**
     * Resets the timer to its initial state.
     */
    public void reset() {
        this.started = -1;
        this.ended = -1;
        this.pausedTime = 0;
        this.pauseStart = -1;
    }

    /**
     * Checks if the timer is currently running.
     * @return true if the timer is running, false otherwise.
     */
    public boolean isRunning() {
        return started != -1 && ended == -1 && pauseStart == -1;
    }

    /**
     * Checks if the timer is currently paused.
     * @return true if the timer is paused, false otherwise.
     */
    public boolean isPaused() {
        return pauseStart != -1;
    }

    /**
     * Gets the start time of the timer.
     *
     * @return Start time in milliseconds since epoch, or -1 if the timer has not started.
     */
    public long getStartTime() {
        return started;
    }

    /**
     * Gets the total paused duration in milliseconds.
     *
     * @return Total paused time in milliseconds.
     */
    public long getPausedTime() {
        return pausedTime + (pauseStart != -1 ? System.currentTimeMillis() - pauseStart : 0);
    }
}
