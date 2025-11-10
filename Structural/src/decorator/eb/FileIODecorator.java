package decorator.eb;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.InputStream;
import java.util.Base64;

// (No explicit interfaces or abstract classes defined by us, as Java provides them)
// 1. Component Interface: InputStream (built-in Java interface)
// 2. Concrete Components: ByteArrayInputStream, FileInputStream, etc.
// 3. Decorator Abstract Class: FilterInputStream (built-in Java abstract class)
// 4. Concrete Decorators: BufferedInputStream, DataInputStream, GZIPInputStream, etc.

public class FileIODecorator {
    public static void main(String[] args) throws Exception {
        String data = "Hello, Decorator Pattern in Java I/O!";
        byte[] rawBytes = data.getBytes();

        // Start with a basic InputStream (Concrete Component)
        // This is like our SimpleCoffee.
        InputStream rawStream = new ByteArrayInputStream(rawBytes);
        System.out.println("Original stream capacity: " + rawStream.available() + " bytes");

        // Decorate it with a BufferedInputStream
        // This adds buffering functionality without changing the InputStream interface.
        // This is like our MilkDecorator.
        InputStream bufferedStream = new BufferedInputStream(rawStream);
        System.out.println("Buffered stream capacity: " + bufferedStream.available() + " bytes");

        // Decorate it further with a DataInputStream
        // This adds the ability to read primitive data types (int, double, etc.).
        // This is like our SugarDecorator.
        DataInputStream dataStream = new DataInputStream(bufferedStream);

        // Now we can use the added functionality
        System.out.println("Reading 1st byte: " + dataStream.readByte()); // Reads 'H'

        // We can also use methods from the original InputStream interface
        // because DataInputStream IS-A InputStream.
        byte[] remainingBytes = new byte[dataStream.available()];
        dataStream.read(remainingBytes);
        System.out.println("Remaining data: " + new String(remainingBytes));

        dataStream.close(); // Closing the outermost decorator closes all wrapped streams.

        System.out.println("\n--- Another example with Base64 encoding ---");
        String encodedData = Base64.getEncoder().encodeToString("Secret data".getBytes());

        // Imagine a scenario where you have an encoded stream that you need to decode
        InputStream encodedStream = new ByteArrayInputStream(encodedData.getBytes());

        // Java's Base64.Decoder is not a "stream decorator" in the same way,
        // but we can conceptualize adding "decoding" behavior to a stream.
        // For a true stream decorator here, you'd use a special DecodingInputStream
        // (which you'd implement yourself, similar to how GZIPInputStream works).
        // Let's manually illustrate the concept.

        // Read encoded stream
        byte[] readEncodedBytes = new byte[encodedStream.available()];
        encodedStream.read(readEncodedBytes);

        // Now "decorate" the bytes with decoding functionality.
        // If Base64InputStream existed, it would wrap the encodedStream.
        byte[] decodedBytes = Base64.getDecoder().decode(readEncodedBytes);
        System.out.println("Decoded data: " + new String(decodedBytes));

        encodedStream.close();
    }
}
