package utils;

import java.util.ArrayList;
import java.util.Random;

import model.Spawn;
import model.State;
import model.action.Action;
import model.action.DownSwipe;
import model.action.LeftSwipe;
import model.action.RightSwipe;
import model.action.UpSwipe;

public class Utils {
	
	//all variable in interface should static final 
	public static final UpSwipe upswipe = new UpSwipe() ; 
	public static final DownSwipe downswipe = new DownSwipe(); 
	public static final RightSwipe rightswipe = new RightSwipe() ; 
	public static final LeftSwipe leftswipe = new LeftSwipe() ; 

	public static int[][] copyBoard(int [][] board) {
		int[][] copy = new int[board.length][board.length ] ; 
		for(int i = 0 ; i < board.length ; i++ )
			System.arraycopy(board[i], 0, copy[i], 0, board.length);
		return copy ; 
	}
	
	public static ArrayList<Integer> getEmptySquares(int [][] board ){
		ArrayList<Integer> index = new ArrayList<Integer> ()  ;
		for(int i = 0 ; i < board.length ; i++ )
			for(int j = 0; j < board.length ; j++)
				if(board[i][j]==0)
					index.add(i * board.length + j) ; 
		return index ; 
	}
	
	public static ArrayList<State.Action > getAllActions(){
    	ArrayList<State.Action> allActions = new ArrayList<State.Action>() ; 
    	allActions.add(State.Action.UP) ; 
    	allActions.add(State.Action.DOWN) ; 
    	allActions.add(State.Action.LEFT) ; 
    	allActions.add(State.Action.RIGHT) ; 
    	return allActions ; 
    }
	
	public static int highValueIndex(int[][] board) {
		int max = 0 ; 
		int index = -1 ; 
		for(int i = 0 ; i < board.length ; i++ )
			for(int j = 0 ; j < board.length ; j++ )
				if(board[i][j] > max) {
					max = board[i][j] ; 
					index = i * board.length + j ; 
				}
		return index ; 			
	}
	
	public static int sum(int [] [] board ) {
		int sum = 0 ; 
		for(int i = 0 ; i < board.length ; i++ )
			for(int j = 0 ; j < board.length ; j++)
				sum += board[i][j] ; 
		return sum ; 
	}
	
	public static State.Action getRandomAction(State state){
		int index = (int) Math.random() * state.legalAction.size() ; 
		int i = 0 ; 
		for(State.Action a : state.legalAction) {
			if(i == index )
				return a ;
			i++ ;
		}
		throw new IllegalArgumentException("no this legal actions in this state" )  ;
	}
	
	public static Action getActionObject(State.Action a) { 
		if(a == State.Action.LEFT)
			return leftswipe ; 
		if(a == State.Action.RIGHT)
			return rightswipe ; 
		if(a == State.Action.DOWN)
			return downswipe ; 
		if(a == State.Action.UP)
			return upswipe ; 
		throw new IllegalArgumentException("no such action") ;
	}
	
	public static State getNewStateAfterAction(State state, State.Action action) {
		return new State(Spawn.spawn(Utils.getActionObject(action).getResult(state).getBoard())) ; 
	}
	
	public static State getNewStateAfterAction(State state, Action action) {
		return new State(Spawn.spawn(action.getResult(state).getBoard())) ; 
	}
}
