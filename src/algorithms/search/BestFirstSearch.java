package algorithms.search;

import java.util.*;

/**
 * Comparator for the Priority Queue. compare by cost of every state.
 */

class StComp implements Comparator<AState> {
    @Override
    public int compare(AState st1, AState st2) {
        return Double.compare(st1.getCost(), st2.getCost());
    }
}
/**
 * implement a best first search by using solve of BreadthFirstSearch and priority queue.
 * extends BreadthFirstSearch.
 */

public class BestFirstSearch extends BreadthFirstSearch {
    public BestFirstSearch() {
        super();
        queue = new PriorityQueue<>((s1, s2)->(int)(s1.getCost() - s2.getCost()));
    }

    @Override
    public String getName() {
        return "BestFirstSearch";
    }
}
