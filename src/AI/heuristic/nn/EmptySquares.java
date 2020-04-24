package AI.heuristic.nn;

import AI.heuristic.Heuristic;
import model.State;
import utils.Utils;

public class EmptySquares implements Heuristic {
    @Override
    public double getValue(State state) {
        //int maxEmptySquares = 16;
        return Utils.getEmptySquares(state.getBoard()).size();
    }
}
