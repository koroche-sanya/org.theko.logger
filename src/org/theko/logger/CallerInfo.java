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

package org.theko.logger;

/**
 * Represents information about the caller's stack trace element, which provides details about the class,
 * method, line number, and other related information from where the log entry was created.
 * This class encapsulates a {@link StackTraceElement} to allow easy access to its details.
 */
public class CallerInfo {
    
    /**
     * The {@link StackTraceElement} representing the caller's stack trace information.
     */
    protected StackTraceElement element;

    

    /**
     * Constructs a {@link CallerInfo} instance with the provided {@link StackTraceElement}.
     * 
     * @param element The {@link StackTraceElement} representing the caller's information.
     */
    public CallerInfo(StackTraceElement element) {
        this.element = element;
    }

    /**
     * Gets the name of the class where the method is located.
     * 
     * @return The name of the class.
     */
    public String getClassName() {
        return element.getClassName();
    }

    /**
     * Gets the name of the method where the log entry was generated.
     * 
     * @return The name of the method.
     */
    public String getMethodName() {
        return element.getMethodName();
    }

    /**
     * Gets the line number where the log entry was created.
     * 
     * @return The line number in the source code.
     */
    public int getLineNumber() {
        return element.getLineNumber();
    }

    /**
     * Gets the file name of the source code where the log entry was created.
     * 
     * @return The file name.
     */
    public String getFileName() {
        return element.getFileName();
    }

    /**
     * Checks if the method where the log entry was generated is a native method.
     * 
     * @return {@code true} if the method is native, {@code false} otherwise.
     */
    public boolean isNativeMethod() {
        return element.isNativeMethod();
    }

    /**
     * Gets the name of the module where the log entry was created (Java 9+ feature).
     * 
     * @return The name of the module, or {@code null} if not available.
     */
    public String getModuleName() {
        return element.getModuleName();
    }

    /**
     * Gets the version of the module where the log entry was created (Java 9+ feature).
     * 
     * @return The module version, or {@code null} if not available.
     */
    public String getModuleVersion() {
        return element.getModuleVersion();
    }

    /**
     * Gets the name of the class loader that loaded the class where the log entry was created.
     * 
     * @return The class loader name, or {@code null} if not available.
     */
    public String getClassLoaderName() {
        return element.getClassLoaderName();
    }

    /**
     * Gets the underlying {@link StackTraceElement} representing the caller's stack trace information.
     * 
     * @return The {@link StackTraceElement}.
     */
    public StackTraceElement getStackTraceElement() {
        return element;
    }

    /**
     * Returns a string representation of the stack trace element.
     * 
     * @return A string representation of the {@link StackTraceElement}.
     */
    @Override
    public String toString() {
        return element.toString();
    }
}
