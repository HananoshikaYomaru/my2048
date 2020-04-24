package model.action;

import model.State;
import utils.Utils;

public class UpSwipe implements Action {

    @Override
    public State getResult(State state) {
        int[][] board = Utils.copyBoard(state.getBoard());
        for (int j = 0; j < board[0].length; j++) {
            int head = 0;
            for (int i = 1; i < board.length; i++) {
                int value = board[i][j];
                if(value == 0)
                    continue;
                if(board[head][j] == 0){
                    board[head][j] = value;
                    board[i][j] = 0;
                } else if (board[head][j] == value) {
                    board[head++][j] += value;
                    board[i][j] = 0;
                } else {
                    if(i != ++head){
                        board[i][j] = 0;
                    }
                    board[head][j] = value;
                }
            }
        }
        return new State(board);
    }

    @Override
    public boolean equals(Object o) {
        return(o instanceof UpSwipe);
    }
}
