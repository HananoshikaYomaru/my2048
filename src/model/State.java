package model;

import java.util.ArrayList;
import utils.Utils;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class State{
	public static enum Action {UP, DOWN, LEFT, RIGHT } ; 
	private final int[][] board ; 
	public final Set<Action> legalAction ; 
	
	
	public State() {
		int[][ ]temp =  new int[4][] ; 
		for(int i = 0 ; i < 4 ; i++)
			temp[i] = new int [4] ; 
		for(int i = 0 ; i < 4 ; i++)
			for(int j = 0; j < 4 ; j++ )
				temp[i][j] = 0 ; 
		temp = Spawn.spawn(temp) ; 
		board = temp ; 
		legalAction = legalActions(board)  ;
	}
	
	public State(int[][] board ) {
		this.board = board ; 
		legalAction = legalActions(board) ; 
	}
	
	public Set<Action> legalActions(int [] [] board ){
		
		Set<Action> actions = new HashSet<Action >()  ; 
		// Enjoy :)
		outer:
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if(j > 0 && (board[i][j] != 0 && (board[i][j-1] == 0 || board[i][j-1] == board[i][j])) && !actions.contains(Action.LEFT))
                    actions.add(Action.LEFT) ; 
                if(j < board[i].length-1 && (board[i][j] != 0 && (board[i][j+1] == 0 || board[i][j+1] == board[i][j])) && !actions.contains(Action.RIGHT))
                    actions.add(Action.RIGHT) ; 
                if(i > 0 && (board[i][j] != 0 && (board[i-1][j] == 0 || board[i-1][j] == board[i][j]))&& !actions.contains(Action.UP))
                   	actions.add(Action.UP) ; 
                if(i < board.length-1 && (board[i][j] != 0 && (board[i+1][j] == 0 || board[i+1][j] == board[i][j]))&& !actions.contains(Action.DOWN))
                    actions.add(Action.DOWN);
                if(actions.contains(Action.LEFT) && actions.contains(Action.RIGHT) && actions.contains(Action.UP) && actions.contains(Action.DOWN))
                	break outer; 
            }
        }
        return actions ; 
	}
	
	
	public void display() {
		StringBuilder sb = new StringBuilder() ; 
		for(int i = 0 ; i < board.length ; i++ ) {
			for(int j = 0 ; j <board.length ; j++)
				sb.append(board[i][j]).append("\t") ; 
			sb.append("\n") ; 
		}
		System.out.print(sb.toString()); 
	}
	
	public int[][] getBoard() {
		return board ; 
	}
	
	
	@Override
	public boolean equals(Object o) {
		if(o instanceof State == false )
			return false ; 
		for(int i = 0 ; i < board.length ; i++ )
			for(int j = 0; j < board.length ; j++)
				if(((State)o).board[i][j] != board[i][j])
					return false ; 
		return true ;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder( );
		for(int i = 0 ; i < board.length ; i++ )
			for(int j = 0 ; j< board.length ;j++)
				sb.append(board[i][j]) ; 
		return sb.toString();
		
	}
	
	@Override
	public int hashCode() {
		return this.toString().hashCode();
		
	}
}