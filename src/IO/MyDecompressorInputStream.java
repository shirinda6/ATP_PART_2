package IO;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;

public class MyDecompressorInputStream extends InputStream {
    InputStream in;


    public MyDecompressorInputStream(FileInputStream inputStream) {
        in=inputStream;
    }

    public MyDecompressorInputStream(ByteArrayInputStream inputStream) {
        in=inputStream;
    }

    @Override
    public int read() throws IOException {
        return 0;
    }

    @Override
    public int read(byte[] b) throws IOException {
        int counter = 18;
        int data;

        for (int i = 0; i < 18; i++) {
            data = in.read();
            b[i] =(byte) (data & 0xFF);
        }
        data = in.read();
        while(data != -1 ) {
            String binary = Integer.toBinaryString(data);  //read integer and cast to binary string
            if(binary.length()<8 && counter+binary.length() < b.length){
                for(int i=0 ; i<8-binary.length();i++){
                    if(counter+binary.length() < b.length)  // add zero to complete 8 char
                       counter++;
                }
            }
            byte []bb=binary.getBytes(StandardCharsets.UTF_8);//casting binary string to byte[]
            for (int i = 0; i <binary.length() ; i++) {
                bb[i]=(byte)((int)bb[i]- 48);
                b[counter++]= bb[i];
            }
            data = in.read(); // read next byte
        }
        return 0;
    }
}
