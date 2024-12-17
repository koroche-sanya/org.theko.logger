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
