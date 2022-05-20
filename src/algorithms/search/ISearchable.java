package algorithms.search;

import java.util.ArrayList;

/**
 * interface which common for problems
 */

public interface ISearchable {
    AState initial();

    ArrayList<AState> getAllSuccessors(AState current);

    boolean TestGoal(AState state);
}
