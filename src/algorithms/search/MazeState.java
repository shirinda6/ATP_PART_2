package algorithms.search;

import algorithms.mazeGenerators.Position;
import java.util.Objects;

public class MazeState extends AState{
    private Position current_pos;
    public static int[][] maze;

    /**
     * constructor for maze state which present position in the maze.
     * @param predecessor,double,Position
     */

    public MazeState(AState predecessor, double cost, Position position) {
        super(predecessor, cost);
        current_pos=position;
    }

    public Position getCurrent_pos() {
        return current_pos;
    }

    @Override
    public String toString() {
        return getCurrent_pos().toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MazeState mazeState = (MazeState) o;
        return mazeState.getCurrent_pos().getColumnIndex()==((MazeState) o).getCurrent_pos().getColumnIndex() && mazeState.getCurrent_pos().getRowIndex()==((MazeState) o).getCurrent_pos().getRowIndex();
    }

    @Override
    public int hashCode() {
        return Objects.hash(current_pos);
    }
}
