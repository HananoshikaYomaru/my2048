package AI;

import AI.heuristic.Heuristic;
import model.State;
import model.action.Action; 

public interface AI {
	Action getAction(State state ) ; 
	void setHeuristic(Heuristic heuristic) ; 
}
