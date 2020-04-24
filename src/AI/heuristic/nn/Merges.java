package AI.heuristic.nn;

import model.State;
import AI.heuristic.Heuristic;

public class Merges implements Heuristic {
    @Override
    public double getValue(State state) {
        int merges = 0;
        int[][] board = state.getBoard();
        for (int i = 0; i < board.length; i++){
            for (int j = 0; j < board.length; j++){
                if (board[i][j] == -1) continue;
                if (j != board.length-1 && board[i][j] == board[i][j+1]) merges++;
                if (i != board.length-1 && board[i][j] == board[i+1][j]) merges++;
            }
        }
        return merges;
    }
}