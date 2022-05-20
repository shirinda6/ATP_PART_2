package algorithms.mazeGenerators;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MyMazeGenerator extends AMazeGenerator{
    /**
     * Generate a maze
     * @param row,column
     * @return maze by prim algorithm
     */
    public Maze generate(int row, int column) {
        Maze maze = new Maze(row,column);
        maze.fillMaze();
        Random r = new Random();
        maze.setStart(new Position(0,0));
        cellMaze(maze,maze.getStartPosition());
        List<Position> wallList;
        wallList=addNeighbor(maze,maze.getStartPosition(),1);
        if(wallList.isEmpty()){
            maze.setGoal(new Position(0,1));
        }
        else {
            Position curWall, curNeigh;

            while (!wallList.isEmpty()) {
                curWall = wallList.stream().skip(r.nextInt(wallList.size())).findFirst().orElse(null);
                if (curWall != null) {
                    List<Position> neigh = addNeighbor(maze, curWall, 0);
                    if (!neigh.isEmpty()) {
                        curNeigh = neigh.get(r.nextInt(neigh.size()));
                        connect(maze, curWall, curNeigh);
                    }
                    wallList.addAll(addNeighbor(maze, curWall, 1));
                    wallList.remove(curWall);
                    if (wallList.isEmpty() && !neigh.isEmpty()) {
                        pickGoal(maze, curWall);
                        break;
                    }
                }
            }
        }
        return maze;
    }

    private void connect(Maze maze,Position curWall, Position curNeigh) {
            //Creates a transition between the 2 parameters
            int rowB = (curNeigh.getRowIndex()+curWall.getRowIndex())/2;
            int columnB= (curNeigh.getColumnIndex()+curWall.getColumnIndex())/2;
            maze.getM()[rowB][columnB]=0;
            maze.getM()[curWall.getRowIndex()][curWall.getColumnIndex()]=0;
            maze.getM()[curNeigh.getRowIndex()][curNeigh.getColumnIndex()]=0;

    }

    public List<Position> addNeighbor(Maze m,Position cell, int val) {
            //Adds to the list the possible neighbors of the cell
            List<Position>neighbors = new ArrayList<>();
            int row = cell.getRowIndex();
            int column = cell.getColumnIndex();

            if (row > 1 && m.getM()[row-2][column]==val)
                neighbors.add(new Position(row-2,column));

            if (column < m.getM()[0].length - 2 && m.getM()[row][column+2]==val)
                  neighbors.add( new Position(row, column + 2));

            if (row < m.getM().length - 2 && m.getM()[row+2][column]==val)
                neighbors.add(new Position(row+2, column));

            if (column > 1 && m.getM()[row][column-2]==val)
                neighbors.add(new Position(row, column - 2));

            return neighbors;
        }

    public void cellMaze(Maze maze,Position p){
            //Set 0 at the value of the cell
            int row = p.getRowIndex();
            int column = p.getColumnIndex();
            maze.getM()[row][column]=0;
    }

    public void pickGoal(Maze maze,Position p) {
        //pick up a random cell for a goal
        if((p.getRowIndex()==maze.getM().length-1 || p.getRowIndex()==0 )|| (p.getColumnIndex()==maze.getM()[0].length-1 || p.getColumnIndex()==0))
            maze.setGoal(p);
        else {
            int row= maze.getM().length;
            int column=maze.getM()[0].length;
            if (row+1 == maze.getM().length-1) {
                p.setRow(row+1);
                maze.setGoal(p);
            }
            else if (row-1 == maze.getM().length-1) {
                p.setRow(row-1);
                maze.setGoal(p);
            }
            else if (column+1 == maze.getM()[0].length-1) {
                p.setColumn(column+1);
                maze.setGoal(p);
            }
            else if (column-1 == maze.getM()[0].length-1) {
                p.setColumn(column-1);
                maze.setGoal(p);
            }
        }
    }
}
