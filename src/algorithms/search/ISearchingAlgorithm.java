package algorithms.search;

public interface ISearchingAlgorithm {
    /**
     * @param problem to solve
     * @return a solution for the problem
     */
    Solution solve(ISearchable problem);
    /**
     * get the number of nodes evaluated for each algorithm using this interface
     * @return integer
     */

    int getNumberOfNodesEvaluated();
    /**
     * get the name for each algorithm using this interface
     * @return string
     */

    String getName();
}
