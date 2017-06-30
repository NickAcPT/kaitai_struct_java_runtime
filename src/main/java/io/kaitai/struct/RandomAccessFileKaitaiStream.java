package io.kaitai.struct;

import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * An implementation of {@link KaitaiStream} backed by a {@link RandomAccessFile}.
 *
 * Allows reading from local files. Generally, one would want to use
 * {@link ByteBufferKaitaiStream} instead, as it is most likely would be faster,
 * but there are two situation when one should consider this one instead:
 *
 * <ul>
 * <li>Processing many small files. Every ByteBuffer invocation requires a mmap
 * call, which can be relatively expensive (per file).</li>
 * <li>Accessing extra-long files (>31 bits positioning). Unfortunately, Java's
 * implementation of mmap uses ByteBuffer, which is not addressable beyond 31 bit
 * offsets, even if you use a 64-bit platform.</li>
 * </ul>
 */
public class RandomAccessFileKaitaiStream extends KaitaiStream {
    protected RandomAccessFile raf;

    public RandomAccessFileKaitaiStream(String fileName) throws IOException {
        raf = new RandomAccessFile(fileName, "r");
    }

    @Override
    public void close() throws IOException {
        raf.close();
    }

    @Override
    public boolean isEof() {
        try {
            return !(raf.getFilePointer() < raf.length());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void seek(int newPos) {
        try {
            raf.seek(newPos);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void seek(long newPos) {
        try {
            raf.seek(newPos);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int pos() {
        try {
            // FIXME cast
            return (int) raf.getFilePointer();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public long size() {
        try {
            return raf.length();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public byte readS1() {
        try {
            int t = raf.read();
            if (t < 0) {
                throw new EOFException();
            } else {
                return (byte) t;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public short readS2be() {
        try {
            int b1 = raf.read();
            int b2 = raf.read();
            if ((b1 | b2) < 0) {
                throw new EOFException();
            } else {
                return (short) ((b1 << 8) + (b2 << 0));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int readS4be() {
        try {
            int b1 = raf.read();
            int b2 = raf.read();
            int b3 = raf.read();
            int b4 = raf.read();
            if ((b1 | b2 | b3 | b4) < 0) {
                throw new EOFException();
            } else {
                return (b1 << 24) + (b2 << 16) + (b3 << 8) + (b4 << 0);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public long readS8be() {
        long b1 = readU4be();
        long b2 = readU4be();
        return (b1 << 32) + (b2 << 0);
    }

    @Override
    public short readS2le() {
        try {
            int b1 = raf.read();
            int b2 = raf.read();
            if ((b1 | b2) < 0) {
                throw new EOFException();
            } else {
                return (short) ((b2 << 8) + (b1 << 0));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int readS4le() {
        try {
            int b1 = raf.read();
            int b2 = raf.read();
            int b3 = raf.read();
            int b4 = raf.read();
            if ((b1 | b2 | b3 | b4) < 0) {
                throw new EOFException();
            } else {
                return (b4 << 24) + (b3 << 16) + (b2 << 8) + (b1 << 0);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public long readS8le() {
        long b1 = readU4le();
        long b2 = readU4le();
        return (b2 << 32) + (b1 << 0);
    }

    @Override
    public int readU1() {
        try {
            int t = raf.read();
            if (t < 0) {
                throw new EOFException();
            } else {
                return t;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int readU2be() {
        try {
            int b1 = raf.read();
            int b2 = raf.read();
            if ((b1 | b2) < 0) {
                throw new EOFException();
            } else {
                return (b1 << 8) + (b2 << 0);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public long readU4be() {
        try {
            long b1 = raf.read();
            long b2 = raf.read();
            long b3 = raf.read();
            long b4 = raf.read();
            if ((b1 | b2 | b3 | b4) < 0) {
                throw new EOFException();
            } else {
                return (b1 << 24) + (b2 << 16) + (b3 << 8) + (b4 << 0);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int readU2le() {
        try {
            int b1 = raf.read();
            int b2 = raf.read();
            if ((b1 | b2) < 0) {
                throw new EOFException();
            } else {
                return (b2 << 8) + (b1 << 0);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public long readU4le() {
        try {
            long b1 = raf.read();
            long b2 = raf.read();
            long b3 = raf.read();
            long b4 = raf.read();
            if ((b1 | b2 | b3 | b4) < 0) {
                throw new EOFException();
            } else {
                return (b4 << 24) + (b3 << 16) + (b2 << 8) + (b1 << 0);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //region Floating point numbers

    //region Big-endian

    @Override
    public float readF4be() {
        return wrapBufferBe(4).getFloat();
    }

    @Override
    public double readF8be() {
        return wrapBufferBe(8).getDouble();
    }
    
    //endregion

    //region Little-endian

    @Override
    public float readF4le() {
        return wrapBufferLe(4).getFloat();
    }

    @Override
    public double readF8le() {
        return wrapBufferLe(8).getDouble();
    }

    //endregion

    //endregion

    @Override
    public byte[] readBytes(long n) {
        if (n > Integer.MAX_VALUE) {
            throw new RuntimeException(
                    "Java byte arrays can be indexed only up to 31 bits, but " + n + " size was requested"
            );
        }
        byte[] buf = new byte[(int) n];
        try {
            int readCount = raf.read(buf);
            if (readCount < n) {
                throw new EOFException();
            }
            return buf;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static final int DEFAULT_BUFFER_SIZE = 4 * 1024;

    @Override
    public byte[] readBytesFull() {
        byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        int readCount;
        try {
            while (-1 != (readCount = raf.read(buffer)))
                baos.write(buffer, 0, readCount);

            return baos.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public byte[] readBytesTerm(int term, boolean includeTerm, boolean consumeTerm, boolean eosError) {
        try {
            ByteArrayOutputStream buf = new ByteArrayOutputStream();
            while (true) {
                int c = raf.read();
                if (c < 0) {
                    if (eosError) {
                        throw new RuntimeException("End of stream reached, but no terminator " + term + " found");
                    } else {
                        return buf.toByteArray();
                    }
                } else if (c == term) {
                    if (includeTerm)
                        buf.write(c);
                    if (!consumeTerm)
                        raf.seek(raf.getFilePointer() - 1);
                    return buf.toByteArray();
                }
                buf.write(c);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //region Helper methods

    private ByteBuffer wrapBufferLe(int count) {
        return ByteBuffer.wrap(readBytes(count)).order(ByteOrder.LITTLE_ENDIAN);
    }

    private ByteBuffer wrapBufferBe(int count) {
        return ByteBuffer.wrap(readBytes(count)).order(ByteOrder.BIG_ENDIAN);
    }

    //endregion
}
