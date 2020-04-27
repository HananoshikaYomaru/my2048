package utils;

import java.io.IOException;
import java.util.ArrayList;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import model.Spawn;
import model.State;
import model.action.Action;
import model.action.DownSwipe;
import model.action.LeftSwipe;
import model.action.RightSwipe;
import model.action.UpSwipe;

public class Utils {

	// all variable in interface should static final
	public static final UpSwipe upswipe = new UpSwipe();
	public static final DownSwipe downswipe = new DownSwipe();
	public static final RightSwipe rightswipe = new RightSwipe();
	public static final LeftSwipe leftswipe = new LeftSwipe();

	public static int[][] copyBoard(int[][] board) {
		int[][] copy = new int[board.length][board.length];
		for (int i = 0; i < board.length; i++)
			System.arraycopy(board[i], 0, copy[i], 0, board.length);
		return copy;
	}

	public static ArrayList<Integer> getEmptySquares(int[][] board) {
		ArrayList<Integer> index = new ArrayList<Integer>();
		for (int i = 0; i < board.length; i++)
			for (int j = 0; j < board.length; j++)
				if (board[i][j] == 0)
					index.add(i * board.length + j);
		return index;
	}

	public static ArrayList<State.Action> getAllActions() {
		ArrayList<State.Action> allActions = new ArrayList<State.Action>();
		allActions.add(State.Action.UP);
		allActions.add(State.Action.DOWN);
		allActions.add(State.Action.LEFT);
		allActions.add(State.Action.RIGHT);
		return allActions;
	}

	public static int highValueIndex(int[][] board) {
		int max = 0;
		int index = -1;
		for (int i = 0; i < board.length; i++)
			for (int j = 0; j < board.length; j++)
				if (board[i][j] > max) {
					max = board[i][j];
					index = i * board.length + j;
				}
		return index;
	}

	public static int sum(int[][] board) {
		int sum = 0;
		for (int i = 0; i < board.length; i++)
			for (int j = 0; j < board.length; j++)
				sum += board[i][j];
		return sum;
	}

	public static State.Action getRandomAction(State state) {
		int index = (int) Math.random() * state.legalAction.size();
		int i = 0;
		for (State.Action a : state.legalAction) {
			if (i == index)
				return a;
			i++;
		}
		throw new IllegalArgumentException("no this legal actions in this state");
	}

	public static Action getActionObject(State.Action a) {
		if (a == State.Action.LEFT)
			return leftswipe;
		if (a == State.Action.RIGHT)
			return rightswipe;
		if (a == State.Action.DOWN)
			return downswipe;
		if (a == State.Action.UP)
			return upswipe;
		throw new IllegalArgumentException("no such action");
	}

	public static State getNewStateAfterAction(State state, State.Action action) {
		return new State(Spawn.spawn(Utils.getActionObject(action).getResult(state).getBoard()));
	}

	public static State getNewStateAfterAction(State state, Action action) {
		return new State(Spawn.spawn(action.getResult(state).getBoard()));
	}

	/**
	 * this method will create numerous daemon because clip itself is a clip and
	 * thread cannot be distroyed. OH NO. YOu can actually close the thread by using
	 * clip.close(). Therefore, there are two solutions : solution 1. use
	 * clip.setMediaTime(new Time(0)) solution 2: use linelisetener / audiolistener
	 * and clip.close() <- this is much easier
	 * 
	 * @param file
	 */
	public static synchronized void playSound(String file) {

		try {
			final AudioInputStream audioIn = AudioSystem.getAudioInputStream(Utils.class.getResource(file));
			Clip clip = AudioSystem.getClip();
			clip.addLineListener(new LineListener() {
				@Override
				public void update(LineEvent event) {
					if (event.getType() == LineEvent.Type.STOP) {
						event.getLine().close();
						try {
							audioIn.close();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			});
			clip.open(audioIn);
			// calling start will create a daemon thread playing the sound track without
			// blocking
			// you can see the creation on daemon thread from debug menu of eclipse
			// https://coderanch.com/t/554303/java/Clip-start-behaviour
			clip.start();
			// clip.close();
		} catch (Exception e) {// generalize exception
			e.printStackTrace();
		}

//			catch (UnsupportedAudioFileException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (LineUnavailableException e) {
//            e.printStackTrace();
//        }
	}

	
	/**
	 * this method has to be sync because cannot have multiple game playing the sound at the same time
	 * @param state
	 */
	public static synchronized void endGameSound(State state) {
		int index = Utils.highValueIndex(state.getBoard()) ; 
		if ( state.getBoard()[index/4][index%4]>= 2048) {
			playSound("sound/win.wav");
		} else {
			playSound("sound/loss.wav");
		}
	}
}
