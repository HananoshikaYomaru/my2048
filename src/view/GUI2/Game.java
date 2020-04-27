package view.GUI2;

import model.Spawn;
import utils.Utils;
import view.IGUI;
import AI.AI;

/**
 * each time only one game is start, there are a few solution, solution 1 : make
 * static sync playgame() function solution 2 : make a thread pool with only one
 * game thread <- this is better
 * 
 * @author Yomaru
 *
 */
public class Game extends Thread {

	model.State state;
	AI ai;
	IGUI host;
	public boolean stopFlag = false;

	/**
	 * create a new game with host note that AI and state are not set
	 * 
	 * @param host can be null
	 */
	public Game(IGUI host) {
		this.host = host;
	}

	/**
	 * use sleep interrupt to sync a better way would be using lock and sync
	 */
	@Override
	public void run() {
		if (ai == null)
			return;
		System.out.println("do in background !!!!!!!");
		while (!stopFlag) {
			newGame();
			while (!state.legalAction.isEmpty()) {
				state = new model.State(Spawn.spawn(ai.getAction(state).getResult(state).getBoard()));
				if (host != null) {
					try {
						host.show(state);
						sleep(3);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				if (stopFlag)
					return;
			}
			try {
				Utils.endGameSound(state);
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void setAI(AI ai) {
		this.ai = ai;
	}

	/**
	 * for player mode, move when there is action
	 * 
	 * @param action
	 */
	public void move(model.State.Action action) {
		if (state.legalAction.contains(action))
			state = new model.State(Spawn.spawn(Utils.getActionObject(action).getResult(state).getBoard()));
		if (host != null)
			host.show(state);
		if (state.legalAction.isEmpty()) {
			if (host.getClass() == Board.class) {
				((Board) host).removeKeyBind();
			}
			Utils.endGameSound(state);
		}
	}

	/**
	 * create new state and show it
	 */
	void newGame() {
		state = new model.State();
		if (host != null)
			host.show(state);
	}

}
