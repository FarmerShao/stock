package com.farmershao.stock.trade.utils;

import java.io.*;

public class StreamUtils {

    public static final String DFT_CHARSET = "utf-8";
    private static final int BUFFER_SIZE = 2048;

    private StreamUtils() {
    }


    public static String read(Reader reader) throws IOException {
        return read(reader, true);
    }

    public static String read(Reader reader, boolean closeReader)
            throws IOException {
        assert (reader != null);

        try {
            StringBuilder sb = new StringBuilder();
            char[] buffer = new char[BUFFER_SIZE];
            int count = reader.read(buffer);
            while (count != -1) {
                sb.append(buffer, 0, count);
                count = reader.read(buffer);
            }
            return sb.toString();
        } finally {
            if (closeReader) {
                CloseableUtils.close(reader);
            }
        }
    }

    public static String readText(InputStream is, String charset)
            throws IOException {
        assert (is != null);

        try {
            return read(new InputStreamReader(is, charset), true);
        } finally {
            CloseableUtils.close(is);
        }
    }

    public static String readText(InputStream is) throws IOException {
        return readText(is, DFT_CHARSET);
    }

    public static byte[] readBytes(InputStream is)
            throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        fromTo(is, bos, -1, true, true);
        return bos.toByteArray();
    }

    public static byte[] readBytes(InputStream is, int length, boolean closeIS)
            throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        fromTo(is, bos, length, closeIS, true);
        return bos.toByteArray();
    }

    public static long fromTo(InputStream is, OutputStream os)
            throws IOException {
        return fromTo(is, os, -1, true, true);
    }

    public static long fromTo(
            final InputStream is,
            final OutputStream os,
            final boolean closeIS,
            final boolean closeOS
    ) throws IOException {
        return fromTo(is, os, -1, closeIS, closeOS);
    }

    public static long fromTo(
            final InputStream is,
            final OutputStream os,
            final long length,
            final boolean closeIS,
            final boolean closeOS
    ) throws IOException {

        assert (is != null && os != null);
        try {
            byte[] buffer = new byte[BUFFER_SIZE];
            long leftForRead = length;
            int sizeToRead = (leftForRead < 0 || leftForRead > BUFFER_SIZE) ? BUFFER_SIZE : (int) leftForRead;
            int sizeOfRead = is.read(buffer, 0, sizeToRead);

            while (sizeOfRead != -1) {
                os.write(buffer, 0, sizeOfRead);
                leftForRead -= sizeOfRead;

                if (leftForRead == 0) {
                    break;
                }

                sizeToRead = (leftForRead < 0 || leftForRead > BUFFER_SIZE) ? BUFFER_SIZE : (int) leftForRead;
                sizeOfRead = is.read(buffer, 0, sizeToRead);
            }
            os.flush();

            return length - leftForRead;
        } finally {
            if (closeIS) {
                CloseableUtils.close(is);
            }
            if (closeOS) {
                CloseableUtils.close(os);
            }
        }
    }
}
