package AI;

import java.util.Set;

import AI.heuristic.Heuristic;
import model.Spawn;
import model.State;
import model.action.Action;
import utils.Utils;

public class ExpectiMax implements AI{
	
	private int maxDepth; 
	private Heuristic heuristic ; 
	
	public ExpectiMax(int depth) {
		this.maxDepth = depth ; 
	}

	@Override
	public Action getAction(State state) {
		double max = Double.MIN_VALUE ; 
		State.Action bestAct = null ; 
		for(State.Action action : state.legalAction) {
			double value = getValue(action, state, 1) ;
			if(value >= max || bestAct == null) {
				max = value ; 
				bestAct = action ;
			} 
			
		}
		
		/**
		 * this error appear because of double comparison
		 * solution 1 : epsilon comparison for double 
		 * solution 2 : simply exclude the case , add || bestAct == null
		 * here use the solution
		 */
		if(bestAct == null && !state.legalAction.isEmpty()) {
//			Set<State.Action> actions = state.legalActions(state.getBoard()) ; 
//			
//			max = Double.MIN_VALUE ; 
//			bestAct = null ; 
//			for(State.Action action : state.legalAction) {
//				if(action == null )throw new IllegalStateException("program bug"); 
//					
//				double value = getValue(action, state, 1) ;
//				if(value >= max ) {
//					max = value ; 
//					bestAct = action ;
//				} 
//			}
			
			
			throw new IllegalStateException("program bug "); 
		}
		return Utils.getActionObject(bestAct) ; 
	}

	private double getValue(State.Action action, State state, int depth) {
		State temp = Utils.getActionObject(action).getResult(state) ;
		double sum = 0 ; 
		Set<Spawn> spawnedStates = Spawn.getAllpossibleSpawn(temp) ; 
		for(Spawn child : spawnedStates) {
			double value = child.getP() * getValue(child.getState(), depth )  ; 
			sum += value ; 
		}
		return 2 * sum / spawnedStates.size() ;
	}

	// state after spawned
	private double getValue(State state, int depth) {
		Set<State.Action> actions = state.legalAction ; 
		if(actions.isEmpty() || depth == maxDepth)
			return heuristic.getValue(state) ; 
		double max = Integer.MIN_VALUE ; 
		for(State.Action action : actions ) {
			double value = getValue(action, state, depth + 1) ; 
			if(value > max)
				max = value ; 
			
		}
		return max;
	}

	@Override
	public void setHeuristic(Heuristic heuristic) {
		this.heuristic = heuristic ; 	
	}

}
