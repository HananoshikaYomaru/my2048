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
import view.TextUI;
import view.GUI.Game;

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

	
	
	//@Test
	void soundTest()	{
		File testFile = new File("");
	    String currentPath = testFile.getAbsolutePath();
	    System.out.println("current path is: " + currentPath);
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
	
	@Test
	void test4() { 
		Game game = new Game (null)  ;
		AI ai = new ExpectiMax(2) ; 
		ai.setHeuristic(new Cocktail() );
		game.setUpAI(ai );
		game.start();
	}
}
