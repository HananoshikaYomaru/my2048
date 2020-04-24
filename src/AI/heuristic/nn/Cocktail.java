package AI.heuristic.nn;

import AI.heuristic.Heuristic;
import AI.heuristic.ScoreHeuristic;
import AI.heuristic.SmallExponentGrid;
import model.State;
import utils.Utils;

public class Cocktail implements Heuristic {
    @Override
    public double getValue(State state) {
        int[][] board = state.getBoard();
        double sum = 0, merges = 0, empty = 0;
        boolean[] monotonous = new boolean[4];
        int[] deltas = new int[4];
        int moves = state.legalAction.size();


        sum = new ScoreHeuristic().getValue(state);
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                int value = board[i][j];
                if(value == -1) continue;
                if(i > 0 && board[i-1][j] == value)
                    merges++;
                if(j > 0 && board[i][j-1] == value)
                    merges++;
            }
        }
        empty = Utils.getEmptySquares(state.getBoard()).size();
        for (int i = 0; i < monotonous.length; i++) {
            monotonous[i] = isMonotonous(board[i]);
        }
        for (int i = 0; i < board.length; i++) {
            int delta = 0;
            for (int j = 1; j < board[i].length; j++) {
                delta += Math.abs(board[i][j] - board[i][j-1]);
            }
            deltas[i] = delta;
        }

        /*
        double sum = 0, merges = 0, empty = 0;
        boolean[] monotonous = new boolean[4];
        int[] deltas = new int[4];
        int moves;
         */

        double evaluation = 0;
        if(moves == 0){
            return Integer.MIN_VALUE;
        }
        evaluation += sum;
        evaluation += merges * sum / 10;
        //evaluation += Math.pow(Math.E, empty) * sum / 5;
        evaluation += empty * sum / 5;
        for (int i = 0; i < monotonous.length; i++) {
            if(!monotonous[i])
                evaluation -=  deltas[i];
        }

        return evaluation + new SmallExponentGrid().getValue(state) * 10;
    }

    private boolean isMonotonous(int[] nums) {
        boolean rising = true, falling = true;
        for (int i = 1; i < nums.length; i++) {
            int value = nums[i];
            int previous = nums[i-1];
            if(previous > value)
                rising = false;
            if(previous < value)
                falling = false;
        }
        return rising || falling;
    }
}
