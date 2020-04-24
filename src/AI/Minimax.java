package AI;

import java.util.ArrayList;

import AI.heuristic.Heuristic;
import model.Spawn;
import model.State;
import model.action.Action;
import utils.Utils;

public class Minimax implements AI{
	
	private int maxDepth ; 
	private Heuristic heuristic ; 
	private final double  infinity = Double.MAX_VALUE ; 
	
	public Minimax(int depth) {
		if(depth < 1)
			throw new IllegalArgumentException("depth cannot be smaller than 1 ") ; 
		this.maxDepth = depth ; 
	}

	@Override
	public Action getAction(State state) {
		double alpha = -infinity ; 
		double beta = infinity ; 
		
		State.Action bestAct = null;
		double max = -infinity ; 
		for(State.Action action : state.legalAction) {
			State child = Utils.getActionObject(action).getResult(state);
			double value = min(child, alpha, beta, 0) ;
			if(value > max) {
				max = value ; 
				bestAct = action ; 
			}
		}
		return Utils.getActionObject(bestAct) ; 
	}

	/**
	 * 
	 * @param state
	 * @param alpha the so far best value for max, the larger the better
	 * @param beta the so far best value for min, the smaller the better
	 * @param depth
	 * @return
	 */
	private double max (State state, double alpha, double beta, int depth) {
		if(state.legalAction.isEmpty() || depth == maxDepth)
			return heuristic.getValue(state);
		double value = -infinity ; 
		for(State.Action action : state.legalAction) {
			State child = Utils.getActionObject(action).getResult(state);
			value = Math.max(value, min(child, alpha,beta,depth)); 
			alpha = Math.max(value, alpha) ; 
			//have to prune because min won't let max to have better value 
			if(alpha >= beta)
				break ; 
		}
		return value  ;
	}
	
	
	/**
	 * 
	 * @param state
	 * @param alpha the so far best value for max, the larger the better
	 * @param beta the so far best value for min, the smaller the better
	 * @param depth
	 * @return
	 */
	private double min(State state, double alpha, double beta, int depth ) {
		double value = infinity ; 
		for(Spawn child : Spawn.getAllpossibleSpawn(state)) { // for all action choice of our enemy
			value = Math.min(value, max(child.getState(), alpha, beta, depth + 1  )); 
			beta = Math.min(value , beta) ; 
			//have to prune because max 
			if (alpha >= beta )
				break ; 
		}
		return value ;
	}

	@Override
	public void setHeuristic(Heuristic heuristic) {
		// TODO Auto-generated method stub
		this.heuristic = heuristic ; 
	}

}
