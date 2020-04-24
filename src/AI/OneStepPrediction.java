package AI;

import AI.heuristic.Heuristic;
import model.State;
import model.action.Action;
import utils.Utils;

public class OneStepPrediction implements AI {

	private final double infinity = Double.MAX_VALUE ; 
	private Heuristic heuristic ; 
	@Override
	public Action getAction(State state) {
		double highest = -infinity  ; 
		State.Action bestAct = null ; 
		for(State.Action action : state.legalAction) {
			double score = heuristic.getValue(Utils.getActionObject(action).getResult(state)) ; 
			if( score > highest) {
				highest = score ; 
				bestAct = action ; 
			}
		}
		return Utils.getActionObject(bestAct);
	}

	@Override
	public void setHeuristic(Heuristic heuristic) {
		// TODO Auto-generated method stub
		this.heuristic = heuristic ; 
	}

}
