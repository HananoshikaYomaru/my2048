package model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import utils.Utils;

public class Spawn {
	
	private State state ; 
	private double probability ; 
	
	public Spawn(State state , double p) {
		this.state = state ;
		this.probability = p  ; 
	}
	
	public static int[][] spawn(int [][] board) {
		ArrayList<Integer> index = Utils.getEmptySquares(board) ; 
		//randomly put an index 
		int r = index.get((int)(Math.random() * index.size()))  ;
		int [][] temp = Utils.copyBoard(board) ; 
		temp[r / board.length][r % board.length] = (Math.random() > 0.9 ? 4 : 2) ; 
		return temp ; 
	}
	
	public static double getSpawnProbability(int i) {
		if(i == 2)
			return 0.9 ; 
		else if (i==4 )
			return 0.1 ; 
		throw new IllegalArgumentException("no such spawn number "); 
	}
	
	public static int[][] spawn(int[][] board, int index, int i ){
		int [][] temp = Utils.copyBoard(board) ; 
		if(temp[index / temp.length][index % temp.length] != 0)
			throw new IllegalArgumentException("cannot spawn because " + temp[index / temp.length][index % temp.length] + " is on the index " + index ) ; 
		temp[index / temp.length][index % temp.length] = i ;
		return temp ; 
	}
	
	public static Set<Spawn> getAllpossibleSpawn(State state){
		Set<Spawn> result = new HashSet<Spawn>( ); 
		int[] [] board = state.getBoard() ; 
		for(int index : Utils.getEmptySquares(board)) {
			result.add(new Spawn(new State(Spawn.spawn(board, index, 2 )), 0.9)) ; 
			result.add(new Spawn(new State(Spawn.spawn(board, index, 4 )), 0.1)) ; 
 		}
		return result ; 
	}
	
	public State getState() {
		return state ; 
	}
	
	public double getP() {
		return probability ; 
	}
}
