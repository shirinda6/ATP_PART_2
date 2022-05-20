package algorithms.search;

import static org.junit.jupiter.api.Assertions.*;

/**
 * class which checks extreme cases of Best First Search algorithm
 */

class BestFirstSearchTest {
    BestFirstSearch best;

    BestFirstSearchTest(){
        best = new BestFirstSearch();
    }

    @org.junit.jupiter.api.Test
    void solve() {
        Solution solution = best.solve(null);
        String s = "Error - solution is not null";
        if (solution!=null)
            System.out.println(s);
    }

    @org.junit.jupiter.api.Test
    void getName() {
        assertEquals("BestFirstSearch", best.getName());
    }

    @org.junit.jupiter.api.Test
    void getNumberOfNodesEvaluated() {
        assertEquals(0, best.numOfEvaluated);
    }
}
