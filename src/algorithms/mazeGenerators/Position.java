package algorithms.mazeGenerators;

import java.io.Serializable;
import java.util.Objects;

public class Position implements Serializable {
    /**
     * represents location in the maze - by row and column
     */
    private int Row;
    private int Column;

    public Position(int row, int column) {
        Row = row;
        Column = column;
    }
    public Position() {
        Row = 0;
        Column = 0;
    }

    public int getRowIndex(){
        return Row;
    }

    public int getColumnIndex(){
        return Column;
    }

    public void setAll(int row, int column) {
        Row=row;
        Column = column;
    }

    public void setColumn(int column) {
        Column = column;
    }

    public void setRow(int row) {
        Row = row;
    }

    @Override
    public String toString() {
        return "{" +Row +","+ Column + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return Row == position.Row && Column == position.Column;
    }

    @Override
    public int hashCode() {
        return Objects.hash(Row, Column);
    }
}
