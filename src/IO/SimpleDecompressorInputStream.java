package IO;

import java.io.IOException;
import java.io.InputStream;

public class SimpleDecompressorInputStream extends InputStream {
    InputStream in;

    public SimpleDecompressorInputStream(InputStream inputStream){
        in=inputStream;
    }

    @Override
    public int read() throws IOException {
        return 0;
    }

    public int read(byte[] b) throws IOException {
        int counter = 0;
        int b_count=18;
        int data;

        for (int i = 0; i < 18; i++) {
            data = in.read();
            b[i] =(byte) (data & 0xFF);
        }

        data = in.read();
        while(data != -1) {
            for (int j = 0; j < data; j++) {
                if (counter % 2 == 0) {
                    b[b_count]=0;
                } else b[b_count]=1;
                b_count++;
            }
            counter++;
            data = in.read(); // read next byte
        }
        return 0;
    }
}
