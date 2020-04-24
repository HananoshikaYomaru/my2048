package AI;

import AI.heuristic.Heuristic;
import model.State;
import model.action.Action;
import utils.Utils;

public class RandomAction implements AI {

	@Override
	public Action getAction(State state) {
		return Utils.getActionObject(Utils.getRandomAction(state)) ; 
	}

	@Override
	public void setHeuristic(Heuristic heuristic) {
	}

}
