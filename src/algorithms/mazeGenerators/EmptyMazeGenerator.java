package algorithms.mazeGenerators;

import java.util.Random;

public class EmptyMazeGenerator extends AMazeGenerator{
    /**
     * Generate a empty maze
     * @param row,column
     * @return empty maze
     */

    @Override
    public Maze generate(int row, int column) {
        Random r = new Random();
        Maze m = new Maze(row,column);
        m.setStart(new Position(0,r.nextInt(column-1))); //Pick up random Start Position in row 0
        m.randomGoal(m,row,column);  //Pick up random Goal Position
        return m;
    }

}
