/*
 * MIT License
 *
 * Copyright (c) 2024 Alex Krasnobaev
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

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

/**
 * Represents detailed stack trace information and provides methods for retrieving
 * specific stack trace elements or related caller information.
 */
public class StackTraceInfo {

    /**
     * Array containing the stack trace elements.
     */
    protected StackTraceElement[] elements;

    /**
     * Constructs a {@code StackTraceInfo} instance with the given stack trace elements.
     *
     * @param elements The array of stack trace elements to be stored.
     */
    public StackTraceInfo(StackTraceElement[] elements) {
        this.elements = elements;
    }

    /**
     * Retrieves the stack trace element at the specified index.
     *
     * @param index The position of the stack trace element in the array.
     * @return The {@link StackTraceElement} at the specified index.
     * @throws ArrayIndexOutOfBoundsException If the index is out of range.
     */
    public StackTraceElement getStackTraceElementAt(int index) {
        if (index < 0 || index >= elements.length) {
            throw new ArrayIndexOutOfBoundsException(index);
        }
        return elements[index];
    }

    /**
     * Retrieves the caller information (method/class details) based on the stack trace
     * element at the specified index.
     *
     * @param index The position of the stack trace element.
     * @return A {@link CallerInfo} object containing caller information.
     * @throws ArrayIndexOutOfBoundsException If the index is out of range.
     */
    public CallerInfo getCallerInfoAt(int index) {
        return new CallerInfo(getStackTraceElementAt(index));
    }

    /**
     * Retrieves all the stack trace elements stored in this object.
     *
     * @return An array of {@link StackTraceElement}.
     */
    public StackTraceElement[] getStackTraceElements() {
        return elements;
    }

    /**
     * Writes the stack trace information as a string to the specified output stream.
     *
     * @param os The output stream where the stack trace information will be written.
     * @throws IOException If an I/O error occurs while writing to the stream.
     */
    public void writeTo(OutputStream os) throws IOException {
        os.write(toString().getBytes(StandardCharsets.UTF_8));
    }

    /**
     * Returns a formatted string representation of the stack trace elements,
     * with each element displayed on a new line.
     *
     * @return A formatted string of the stack trace.
     */
    @Override
    public String toString() {
        StringBuilder out = new StringBuilder();
        Arrays.asList(elements).stream().forEach(element -> {
            out.append(element.toString()).append("\n");
        });
        return out.toString();
    }
}
