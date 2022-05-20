package IO;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class MyCompressorOutputStream extends OutputStream {
    OutputStream out;
    public MyCompressorOutputStream(FileOutputStream outputStream) {
        out=outputStream;
    }
    public void write(int b) throws IOException {
        out.write(b);
        out.flush();
    }

    public MyCompressorOutputStream(ByteArrayOutputStream outputStream){
        out=outputStream;
    }

    @Override
    public void write(byte[] b) throws IOException {
        String hex="";
        String num="";
        int x;
        for (int j = 0; j < 18; j++) {
            write(b[j]);
        }
        for (int i = 18; i < b.length; i+=8) {  //compress binary maze of 8 cell to base 10
            for (int j = i; j < i + 8; j++) {
                if (j >= b.length)
                    break;
                num += (int) b[j];
            }
            x = Integer.parseInt(num, 2);
            write(x);
            num = "";
        }
    }
}
