package view.GUI;

import AI.* ; 
import model.* ;
import utils.Utils;  


/**
 * note that the game use thread instead of swing worker, 
 * swing worker might be better when come with GUI
 * see : https://stackoverflow.com/questions/26836742/difference-between-swing-worker-and-normal-threads
 * @author Yomaru
 *
 */
public class Game extends Thread{
	Board host ; 
	model.State state ;
	AI ai ; 
	
	
	/**
	 * the game will be set up and create a new state 
	 * @param host can be null 
	 */
	public Game(Board host) { 
		this.host = host ; 
		this.state = new model.State()  ;
	}
	
	public void setUpAI(AI ai) {
		this.ai = ai ; 
	}
	
	@Override 
	public void run( ) { 
		if(ai == null)
			return  ;
		while(!this.isInterrupted()) {
			newGame() ; 
			while(!state.legalAction.isEmpty())
			{
				state = new model.State(Spawn.spawn(ai.getAction(state).getResult(state).getBoard())) ;
				if(host != null ) host.show(state);
				if(this.isInterrupted())
					return  ; 
			}
			state.display();
			Utils.endGameSound(state);
			try {
				sleep(5000);
			} catch (InterruptedException e) {
				// this.interrupt is need because if the AI is too fast to loss
				// the we can only interrup the sleep but not the game 
				// however, gametomain() in gamepage is still called. 
				// Which lead to game running background, and cannot be called again. 
				this.interrupt();
			}
		}
		return ; 
	}
	
	
	public void move(model.State.Action action) {
		if(state.legalAction.contains(action))
			state = new model.State(Spawn.spawn(Utils.getActionObject(action).getResult(state).getBoard())) ; 
	}

	/**
	 * create new state and show it 
	 */
	void newGame() {
		state = new model.State () ; 
		if(host != null ) host.show(state) ; 
	}
}
