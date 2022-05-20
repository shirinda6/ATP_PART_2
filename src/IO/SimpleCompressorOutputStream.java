package IO;

import java.io.IOException;
import java.io.OutputStream;

public class SimpleCompressorOutputStream extends OutputStream {
    OutputStream out;

    public SimpleCompressorOutputStream(OutputStream outputStream){
        out = outputStream;
    }

    @Override
    public void write(int b) throws IOException {
        out.write(b);
        out.flush();
    }

    public void write(byte[] b) throws IOException {
        int counter=0;
        int i=18;

        for (int j = 0; j < 18; j++) {
            write(b[j]);
        }

        while (i<b.length && b[i]==0){
            counter++;
            i++;
            if (i>=b.length) {
                write(counter);
                break;
            }
            if (b[i]==1){
                write(counter);
                counter=0;
            }
            if (counter>255){
                write(255);
                write(0);
                counter=1;
            }
            while (b[i]==1){
                counter++;
                i++;
                if (i>=b.length){
                    write(counter);
                    break;
                }
                if (b[i]==0){
                    write(counter);
                    counter=0;
                }
                if (counter>255){
                    write(255);
                    write(0);
                    counter=1;
                }
            }
        }
    }
}