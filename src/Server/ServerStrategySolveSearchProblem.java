package Server;

import algorithms.mazeGenerators.Maze;
import algorithms.search.*;
import java.io.*;
import java.util.Arrays;


public class ServerStrategySolveSearchProblem implements IServerStrategy{
    static int name_num = 0;
    @Override
    public void ServerStrategy(InputStream inFromClient, OutputStream outToClient) throws IOException {
        try {
            ObjectOutputStream toClient = new ObjectOutputStream(outToClient);
            ObjectInputStream fromServer = new ObjectInputStream(inFromClient);
            Maze maze = (Maze) fromServer.readObject();
            SearchableMaze searchableMaze = new SearchableMaze(maze);
            Solution solution = findSolution(checkInDir(), searchableMaze);
            toClient.writeObject(solution);

        } catch (Exception e) {
        }
    }

    private Solution findSolution(File[] files, SearchableMaze searchableMaze) throws IOException {
        Boolean flag=false;
        if (files!=null && files.length > 0) {
            //if the file list not empty read the files and compare the maze
            for (File f : files) {
                String s = f.getName();
                try {
                    FileInputStream in = new FileInputStream(f.getPath());
                    ObjectInputStream sol = new ObjectInputStream(in);
                    SearchableMaze mazeSearchable = (SearchableMaze) sol.readObject();
                    byte[] m1= mazeSearchable.getMaze().toByteArray();
                    byte[] m2= searchableMaze.getMaze().toByteArray();
                    flag= Arrays.equals(m1,m2);
                    if (flag) {
                        Solution solution = (Solution) sol.readObject();
                        in.close();
                        sol.close();
                        return solution;
                    }
                    in.close();
                    sol.close();

                }catch(Exception e) {
                }
            }
        }
        //if the file list empty or no suitable solution was found
        return newSolution(searchableMaze);
    }

    private Solution newSolution(SearchableMaze searchableMaze) {
        String name = "" + name_num++;
        File file =new File(System.getProperty("java.io.tmpdir")+"mazeProblem"+name);

        ISearchingAlgorithm searcher;
        String algorithm = Server.configurations.getProperties("mazeSearchingAlgorithm");
        if (algorithm.equals("BreadthFirstSearch"))
            searcher = new BreadthFirstSearch();
        else if (algorithm.equals("DepthFirstSearch"))
            searcher = new DepthFirstSearch();
        else
            searcher = new BestFirstSearch();
        //Create a new solution with the above search algorithm
        Solution solution = searcher.solve(searchableMaze);

        try {
           // write the maze and the solution to the disk
            FileOutputStream fileOutputStream = new FileOutputStream(file.getPath(), true);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(searchableMaze);
            objectOutputStream.flush();
            objectOutputStream.writeObject(solution);
            objectOutputStream.flush();
            fileOutputStream.close();
            objectOutputStream.close();
        } catch (IOException e) {
        }
        return solution;
    }

    private File[] checkInDir(){
        // return all files in the path that start with the name of "mazeProblem"
        String path = System.getProperty("java.io.tmpdir");
        File f = new File(path);
        // insert the files into a list
        File[] files = f.listFiles(new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return name.startsWith("mazeProblem");
            }
        });
        return files;
    }
}
