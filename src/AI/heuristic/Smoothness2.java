package AI.heuristic;

import model.State;

public class Smoothness2 implements Heuristic {

	@Override
	public double getValue(State state) {
		// TODO Auto-generated method stub
		int[][] board = state.getBoard() ; 
		int maxvalue = 0 ; 
		int maxi = 0 ; 
		int maxj = 0 ; 
		for(int i = 0 ; i < board.length ; i++)
			for(int j = 0 ; j < board.length ; j++ )
				if(board[i][j] > maxvalue) {
					maxvalue = board[i][j]; 
					maxi = i ; 
					maxj = j  ;
				}
		double total = 0; 
		for(int i = 0 ; i < board.length ; i++ ) {
			for(int j = 0 ; j < board.length ; j++) {
				total += (board[i][j] == -1 ? 0 :board[i][j]  ) * board[maxi][maxj] * EDSqaure(i,j,maxi,maxj) ; 
			}
		}
				
		return total;
	}
	
	private double EDSqaure(int i1, int j1, int i2, int j2){
		return Math.pow(Math.abs(i1 - i2),2) + Math.pow(Math.abs(j1 - j2 ), 2) ; 
	}

}
