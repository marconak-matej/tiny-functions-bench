package io.github.mm.concat.impl;

import io.github.mm.concat.ConcatArray;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * String concatenation using ByteBuffer / NIO.
 * 
 * Complexity: O(n) - but includes encoding/decoding overhead per string
 * Use case: Suited for pipelines that already deal in bytes (network I/O, file channels).
 * The charset encoding round-trip makes this slower than char[] for pure string work.
 */
public class ByteBufferConcat implements ConcatArray {

    @Override
    public String concat(List<String> items) {
        int totalBytes = 0;
        byte[][] encoded = new byte[items.size()][];

        for (int i = 0; i < items.size(); i++) {
            encoded[i] = items.get(i).getBytes(StandardCharsets.UTF_8);
            totalBytes += encoded[i].length;
        }

        ByteBuffer buffer = ByteBuffer.allocate(totalBytes);
        for (byte[] bytes : encoded) {
            buffer.put(bytes);
        }

        return new String(buffer.array(), StandardCharsets.UTF_8);
    }
}

