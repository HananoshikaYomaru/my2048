package test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.net.URL;

import org.junit.jupiter.api.Test;

import AI.* ; 
import AI.heuristic.* ; 
import AI.heuristic.nn.* ; 
import model.Spawn;
import model.State;
import utils.Utils;
import view.GUI;

class MCTSTest {

	//@Test
	void test() {
		int iteration = 10 ; 
		double average = 0 ; 
		for(int i = 0 ; i < iteration ; i++ ) {
			State state = new State() ; 
			AI ai1 = new MCTS(200, 10, new OneStepPrediction()) ;  
			ai1.setHeuristic(new Cocktail());
			while(!state.legalAction.isEmpty()){
				state = new State(Spawn.spawn(ai1.getAction(state).getResult(state).getBoard())) ; 
			}
			int score = Utils.sum(state.getBoard()) ; 
			System.out.println("1:\t" + score) ; 
			average += score ; 
		}
		average /= (double)iteration ; 
		System.out.println("average: " + average) ; 
	}

	
	@Test
	void test2 () {
		GUI gui = new GUI () ; 
		int iteration = 10 ; 
		double average = 0 ; 
		for(int i = 0 ; i < iteration ; ++i) {
			State state = new State()  ; 
			AI ai1 = new MCTS(1, 1, new OneStepPrediction()) ;  
			AI ai2 = new Minimax(3) ;
			AI ai3 = new ExpectiMax(3) ; 
			ai1.setHeuristic(new Cocktail());
			ai2.setHeuristic(new Cocktail());
			ai3.setHeuristic(new Cocktail()) ; 
			while(!state.legalAction.isEmpty()){
				gui.show(state);
				state = new State(Spawn.spawn(ai1.getAction(state).getResult(state).getBoard())) ; 
			}
			int score = Utils.sum(state.getBoard()) ; 
			System.out.println(i + ":\t" + score) ; 
			average += score ; 
			if(new HighestNumber().getValue(state) >= 2048) {
				gui.win();
				GUI.playSound("sound/win.wav");
			}
			else {
				gui.lose();
				GUI.playSound("sound/loss.wav");
			}
		}
		average /= (double)iteration ; 
		System.out.println("average: " + average) ; 
	}
	
	//@Test
	void soundTest()	{
		File testFile = new File("");
	    String currentPath = testFile.getAbsolutePath();
	    System.out.println("current path is: " + currentPath);
	    GUI.playSound("sound/loss.wav");
	}
	
	//@Test
	void test3 () {
		double a = 4 ; 
		changea(a)  ;
		System.out.println(a) ; 
	}
	
	private void changea(double a ) {
		a = 3 ; 
	}
}
