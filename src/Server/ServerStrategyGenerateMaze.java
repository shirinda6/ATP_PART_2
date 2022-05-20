package Server;

import IO.MyCompressorOutputStream;
import algorithms.mazeGenerators.*;
import java.io.*;

public class ServerStrategyGenerateMaze implements IServerStrategy{
    @Override
    public void ServerStrategy(InputStream inFromClient, OutputStream outToClient) throws IOException {
        try {
            ObjectOutputStream out = new ObjectOutputStream(outToClient);
            ObjectInputStream fromServer = new ObjectInputStream(inFromClient);
            int[] dim = (int[]) fromServer.readObject();
            String algorithm = Server.configurations.getProperties("mazeGeneratingAlgorithm");
            AMazeGenerator generator;
            if (algorithm.equals("MyMazeGenerator"))
                generator = new MyMazeGenerator();
            else if (algorithm.equals("SimpleMazeGenerator"))
                generator = new SimpleMazeGenerator();
            else
                generator = new EmptyMazeGenerator();
            //generate new maze with the selected generator algorithm.
            Maze maze = generator.generate(dim[0], dim[1]);
            ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
            MyCompressorOutputStream compressorOutputStream = new MyCompressorOutputStream(byteOut);
            byte[] compressed = maze.toByteArray();
            compressorOutputStream.write(compressed);
            out.writeObject(byteOut.toByteArray());
        }
        catch (IOException e) {
            //e.printStackTrace();
        }
        catch (ClassNotFoundException e) {
            //e.printStackTrace();
        }
    }
}
