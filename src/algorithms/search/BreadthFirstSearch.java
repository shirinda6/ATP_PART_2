package algorithms.search;

import java.util.*;

public class BreadthFirstSearch extends ASearchingAlgorithm {
    protected Queue<AState> queue;

    public BreadthFirstSearch() {
        super();
        queue=new LinkedList<>();
    }
    /**
     * solving the problem by breadth first search algorithm
     * @param problem: the problem to solve
     * @return a solution
     */

    public Solution solve(ISearchable problem) {
        Set<String> visited=new HashSet<>();
        AState start=problem.initial();

        queue.add(start);
        visited.add(start.toString());
        while (!queue.isEmpty()) {
            numOfEvaluated++;
            AState current = queue.poll();
            for (AState state : problem.getAllSuccessors(current)) {
                if (!visited.contains(state.toString())) {
                    if(problem.TestGoal(state))
                        return buildSolution(state);
                    visited.add(state.toString());
                    queue.add(state);
                }
            }
        }

        return null;
    }

    @Override
    public String getName() {
        return "BreadthFirstSearch";
    }

    @Override
    public int getNumberOfNodesEvaluated() {
        return numOfEvaluated;
    }
}