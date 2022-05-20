package algorithms.mazeGenerators;

public abstract class AMazeGenerator implements IMazeGenerator {
    public long measureAlgorithmTimeMillis(int row, int column){
       long x= System.currentTimeMillis();
        generate(row, column);
        long y=System.currentTimeMillis();
        return (y-x);
    }



}
