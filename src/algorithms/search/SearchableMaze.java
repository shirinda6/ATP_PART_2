package algorithms.search;
import algorithms.mazeGenerators.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

public class SearchableMaze implements ISearchable, Serializable {
    private Maze maze;

    public SearchableMaze(Maze maze_1){
        maze = maze_1;
    }

    public Maze getMaze() {
        return maze;
    }

    @Override
    public AState initial() {
        MazeState m = new MazeState(null,0, maze.getStartPosition());
        return m;
    }

    @Override
    public ArrayList<AState> getAllSuccessors(AState current) {
        if (!(current instanceof MazeState))
            return null;
        int[][] m1 = maze.getM();
        MazeState mazeState = (MazeState) current;
        Position position = mazeState.getCurrent_pos();
        ArrayList<AState> ans = new ArrayList<>();

        for (int i = -1; i <= 1; i++) {
            if (i==0)
                continue;
            if (position.getRowIndex()+i >=0 && position.getRowIndex()+i < m1.length)
                if (m1[position.getRowIndex()+i][position.getColumnIndex()]==0)
                    ans.add(new MazeState(current, 10, new Position(position.getRowIndex()+i, position.getColumnIndex())));
        }
        for (int j = -1; j <= 1; j++) {
            if (j==0)
                continue;
            if (position.getColumnIndex()+j >=0 && position.getColumnIndex()+j < m1[0].length)
                if (m1[position.getRowIndex()][position.getColumnIndex()+j]==0)
                    ans.add(new MazeState(current, 10, new Position(position.getRowIndex(), position.getColumnIndex()+j)));
        }
        for (int row=-1; row <= 1; row++){
            if (row==0)
                continue;
            for (int col = -1; col <= 1; col++) {
                if (col==0)
                    continue;
                if (position.getRowIndex()+row >=0 && position.getRowIndex()+row < m1.length)
                    if (position.getColumnIndex()+col >=0 && position.getColumnIndex()+col < m1[0].length)
                        if (m1[position.getRowIndex()+row][position.getColumnIndex()+col]==0)
                            if (m1[position.getRowIndex()+row][position.getColumnIndex()]==0 || m1[position.getRowIndex()][position.getColumnIndex()+col]==0)
                                ans.add(new MazeState(current, 15, new Position(position.getRowIndex()+row, position.getColumnIndex()+col)));
            }
        }
        return ans;
    }

    @Override
    public boolean TestGoal(AState state) {
        return ((state instanceof MazeState) && ((MazeState) state).getCurrent_pos().equals(maze.getGoalPosition()));
    }
}
