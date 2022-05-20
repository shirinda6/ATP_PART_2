package algorithms.search;

public abstract class ASearchingAlgorithm implements ISearchingAlgorithm {
    protected int numOfEvaluated;

    /**
     * the function calculate the path from the start to the end of the maze.
     * for every state the solution take the predecessor of the state until we reach to the beginning.
     * @param state: state that present the goal of the maze.
     * @return a path from the beginning to the end.
     */

    protected Solution buildSolution(AState state){
        Solution solution = new Solution();
        while (state.getPredecessor()!=null){
            solution.AddToPath(state);
            state=state.getPredecessor();
        }
        return solution;
    }
}
