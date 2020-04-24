package model.action;

import model.State;
import utils.Utils;

public class RightSwipe implements Action {

    @Override
    public State getResult(State state) {
        int[][] board = Utils.copyBoard(state.getBoard());
        for (int[] row : board) {
            int head = row.length-1;
            for (int i = row.length-2; i >= 0; i--) {
                int value = row[i];
                if (value == 0)
                    continue;
                if (row[head] == 0) {
                    row[head] = value;
                    row[i] = 0;
                } else if (row[head] == value) {
                    row[head--] += value;
                    row[i] = 0;
                } else {
                    if (i != --head) {
                        row[i] = 0;
                    }
                    row[head] = value;
                }
            }
        }
        return new State(board);
    }

    @Override
    public boolean equals(Object o) {
        return(o instanceof RightSwipe);
    }
}
