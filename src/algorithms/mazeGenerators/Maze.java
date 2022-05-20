package algorithms.mazeGenerators;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.Random;

public class Maze implements Serializable {
    /**
     * represents a maze : by 2-dimensional array
     */
    private Position start;
    private Position goal;
    private int [][]maze;

    public Maze(int row,int column ) {
        this.maze= new int[row][column];
    }

    public Maze(byte[] uncompressed_maze) {
        int row = (uncompressed_maze[0] & 0xFF) +(uncompressed_maze[1] & 0xFF)+(uncompressed_maze[2] & 0xFF)+(uncompressed_maze[3] & 0xFF);
        int column = (uncompressed_maze[4] & 0xFF)+(uncompressed_maze[5] & 0xFF)+(uncompressed_maze[6] & 0xFF)+(uncompressed_maze[7] & 0xFF);
        int k=18;

        this.maze= new int[row][column];
        setStart(new Position(uncompressed_maze[8] & 0xFF, uncompressed_maze[9] & 0xFF));
        setGoal(new Position((uncompressed_maze[10]  & 0xFF) +(uncompressed_maze[11] & 0xFF)+(uncompressed_maze[12] & 0xFF)+(uncompressed_maze[13] & 0xFF), (uncompressed_maze[14] & 0xFF)+(uncompressed_maze[15] & 0xFF)+(uncompressed_maze[16] & 0xFF)+(uncompressed_maze[17] & 0xFF)));

        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[0].length; j++) {
                maze[i][j] = uncompressed_maze[k++];
            }
        }
    }

    public void setStart(Position start) {
        this.start = start;
    }

    public void setGoal(Position goal) {
        this.goal = goal;
        this.getM()[goal.getRowIndex()][goal.getColumnIndex()]=0;
    }

    public Position getStartPosition(){
        return start;
    }

    public Position getGoalPosition(){
        return goal;
    }

    public int[][] getM() {
        return maze ;
    }

    public void print() {
        Position statP = getStartPosition();
        Position goalP = getGoalPosition();

        for (int i = 0; i < getM().length; i++) {
            System.out.print("{ ");
            for (int j = 0; j < getM()[0].length; j++) {
                if (statP.getRowIndex() == i && statP.getColumnIndex() == j)
                    System.out.print("S ");
                else if (goalP.getRowIndex()  == i && goalP.getColumnIndex() == j)
                    System.out.print("E ");
                else
                    System.out.print(getM()[i][j]+ " ");
            }
            System.out.println("}");
        }
    }
    public Maze fillMaze() {
        //fill the maze with value 1 : Turns all the cells into walls
        for (int i = 0; i < getM().length ; i++) {
            for (int j = 0; j < getM()[0].length ; j++) {
                getM()[i][j] = 1;
            }
        }
        return this;
    }

    public void randomGoal(Maze m,int row,int column){
        //pick up a random cell for a goal
        Random r = new Random();
        int x = r.nextInt(3);
        if(x == 0){
            m.setGoal(new Position(r.nextInt(row-1),column-1));
        }
        else if(x == 1){
            m.setGoal(new Position(r.nextInt(row-1)+1,0));
        }
        else {
            m.setGoal(new Position(row-1,r.nextInt(column-1)));
        }
    }

//    public int getValueByCor(int row, int col) {
//        if (row> maze.length-1 || col > 34 || row<0 || col<0)
//            return -1;
//        return maze[row][col];
//    }

    public byte[] toByteArray(){
        byte[] bytes= new byte[18+getM().length*getM()[0].length];
        int row = getM().length;

        for (int i = 0; i < 4; i++) { // cell 0-3 save the row number
            if (row<=0){
                bytes[i] = (byte) 0;
                continue;
            }
            if (row>255) {
                bytes[i] = (byte) 255;
                row-=255;
            }
            else{
                bytes[i]=(byte) row;
                row-=255;
            }
        }
        int col = getM()[0].length;
        for (int j = 4; j < 8; j++) {  // cell 4-7 save the column number
            if (col<=0){
                bytes[j] = (byte) 0;
                continue;
            }
            if (col>255) {
                bytes[j] = (byte) 255;
                col-=255;
            }
            else{
                bytes[j]= (byte) col ;
                col-=255;
            }
        }

        bytes[8]=(byte) getStartPosition().getRowIndex();   // cell 8-9 save the start position
        bytes[9]=(byte) getStartPosition().getColumnIndex();

        int g_row = getGoalPosition().getRowIndex();
        int g_col = getGoalPosition().getColumnIndex();

        for (int k = 10; k < 14; k++) {   // cell 10-13 save the row of goal position
            if (g_row <= 0) {
                bytes[k] = (byte) 0;
                continue;
            }
            else if (g_row > 255) {
                bytes[k] = (byte) 255;
                g_row -= 255;
            }
            else {
                bytes[k] = (byte) g_row;
                g_row -= 255;
            }
        }
        for (int j = 14; j < 18; j++) {  // cell 14-17 save the column of goal position
            if (g_col<=0){
                bytes[j] = (byte) 0;
                continue;
            }
            else if (g_col>255){
                bytes[j] = (byte) 255;
                g_col-=255;
            }
            else{
                bytes[j]=(byte) g_col;
                g_col-=255;
            }
        }

        int k=18;
        for (int i = 0; i <getM().length ; i++) { //save maze of int[][] in byte[]
            for (int j = 0; j < getM()[0].length; j++) {
                bytes[k++]=(byte) getM()[i][j];
            }
        }
        return bytes;
    }
}

