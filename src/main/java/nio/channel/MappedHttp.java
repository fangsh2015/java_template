package nio.channel;

import java.io.*;
import java.net.URLConnection;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Created by Niki on 2018/5/11 8:44
 */
public class MappedHttp {
    private static final String OUTPUT_FILE = "MappedHttp.out";
    private static final String LINE_SEP = "\r\n";
    private static final String SERVER_ID = "Server: Ronsoft Dummy Server";
    private static final String HTTP_HDR = "HTTP/1.0 200 OK" + LINE_SEP + SERVER_ID + LINE_SEP;
    private static final String HTTP_404_HDR = "HTTP/1.0 404 Not Found" + LINE_SEP + SERVER_ID + LINE_SEP;
    private static final String MSG_404 = "Could not open file: ";

    public static void main(String[] argv) throws UnsupportedEncodingException {
        if (argv.length < 1) {
            System.err.println("Usage: filename");
            return;
        }
        String file = argv[0];
        ByteBuffer header = ByteBuffer.wrap(bytes(HTTP_HDR));
        ByteBuffer dynhdrs = ByteBuffer.allocate(128);
        ByteBuffer[] gather = {header, dynhdrs, null};

        String contentType = "unknown/unknown";
        long contentLength = -1;
        try {
            FileInputStream fis = new FileInputStream(file);
            FileChannel fc = fis.getChannel();
            MappedByteBuffer filedata = fc.map(FileChannel.MapMode.READ_ONLY, 0, fc.size());

            gather[2] = filedata;
            contentLength = fc.size();
            contentType = URLConnection.guessContentTypeFromName(file);
        } catch (IOException e) {
            e.printStackTrace();
            ByteBuffer buffer = ByteBuffer.allocate(128);
            String msg = MSG_404 + e + LINE_SEP;
            buffer.put(bytes(msg));

            buffer.flip();

            gather[0] = ByteBuffer.wrap(bytes(HTTP_404_HDR));
            gather[2] = buffer;

            contentLength = msg.length();
            contentType = "text/plain";
        }

        StringBuffer sb = new StringBuffer();
        sb.append("Content-Length: " + contentLength);
        sb.append(LINE_SEP);
        sb.append("Content-Type: " + contentType);
        sb.append(LINE_SEP);
        dynhdrs.put(bytes(sb.toString()));
        dynhdrs.flip();

        FileOutputStream fos = null;
        FileChannel out = null;
        try {
            fos = new FileOutputStream(OUTPUT_FILE);
            out = fos.getChannel();
            while (out.write(gather) > 0) {
            }
            System.out.println("output written to " + OUTPUT_FILE);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }


    }

    private static byte[] bytes(String string) throws UnsupportedEncodingException {
        return string.getBytes("UTF-8");
    }
}
