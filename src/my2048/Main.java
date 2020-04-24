package my2048;

import model.Spawn;
import model.State;
import utils.Utils;
import view.TextUI;
import AI.OneStepPrediction;
import AI.RandomAction;
import AI.AI;
import AI.MCTS;
import AI.heuristic.nn.Cocktail;

public class Main {
	
	static int iteration = 10 ; 
	static TextUI tui = new TextUI(); 
	public static void main(String args[]) {
		double average = 0 ; 
		for(int i = 0 ; i < iteration ; i++ ) {
			State state = new State() ; 
			AI ai1 = new MCTS(20, 5, new OneStepPrediction()) ;  
			ai1.setHeuristic(new Cocktail());
			while(!state.legalAction.isEmpty()){
				tui.show(state);
				state = Utils.getNewStateAfterAction(state, ai1.getAction(state)) ;  
			}
			int score = Utils.sum(state.getBoard()) ; 
			System.out.println("1:\t" + score) ; 
			average += score ; 
		}
		average /= (double)iteration ; 
		System.out.println("average: " + average) ; 
	}
}
