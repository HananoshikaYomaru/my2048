package view.GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

import AI.AI;
import model.Spawn;
import model.State;
import model.action.Action;
import utils.Utils;
import view.IGUI;

public class Board extends JPanel implements IGUI, KeyListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8079866739055506044L;

	public Tile[] tiles;
	public static final int[] _0123 = { 0, 1, 2, 3 };

	public Game game;
	GamePage host;

	public class Game extends SwingWorker<Void,Void>{
		Board host ; 
		model.State state ;
		AI ai ; 
		
		public Game(Board host) { 
			this.host = host ; 
			this.state = new model.State()  ;
		}
		
		public void setUpAI(AI ai) {
			this.ai = ai ; 
		}
		
		@Override
		protected Void doInBackground() throws Exception {
			if(ai == null)
				return null ;
			while(!isCancelled() ) {
				newGame() ; 
				while(!isCancelled())
				{
					state = new model.State(Spawn.spawn(ai.getAction(state).getResult(state).getBoard())) ;
					if(host != null ) host.show(state);
					if(isCancelled())
						return null ; 
				}
				state.display();
				Utils.endGameSound(state);
			}
			return null ; 
		}
		
		@Override 
		public void done( ) {
			
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

	public Board() {
		// TODO Auto-generated constructor stub
		setFocusable(true);
		setPreferredSize(new Dimension(340, 400));
		initTiles();
		// set up game
		game = new Game(this);
//		show(game.state);
	}

	public void addKeyListener() {
		addKeyListener(this);
	}

	private void initTiles() {
		tiles = new Tile[4 * 4];
		for (int i = 0; i < tiles.length; ++i)
			tiles[i] = Tile.ZERO;
	}

	private static final Color BG_COLOR = new Color(0xbbada0);
	private static final Font STR_FONT = new Font(Font.SANS_SERIF, Font.BOLD, 17);

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		g.setColor(BG_COLOR);
		g.setFont(STR_FONT);
		g.fillRect(0, 0, this.getSize().width, this.getSize().height);
		for (int i : _0123) {
			for (int j : _0123)
				drawTile(g, tiles[i * 4 + j], j, i);
		}
	}

	/* Side of the tile square */
	private static final int SIDE = 64;

	/* Margin between tiles */
	private static final int MARGIN = 16;

	/**
	 * Draw a tile use specific number and color in (x, y) coords, x and y need
	 * offset a bit.
	 * 
	 * There is a problem with x and y notation because x and y notation is the
	 * transpose i and j notation in board A solution is to put j as x and i as y in
	 * the paint method
	 */
	private void drawTile(Graphics g, Tile tile, int x, int y) {
		if (tile == null) {
			System.out.println(x + "   " + y);
		}
		Value val = tile.value();
		int xOffset = offsetCoors(x);
		int yOffset = offsetCoors(y);
		g.setColor(val.color());
		g.fillRect(xOffset, yOffset, SIDE, SIDE);
		g.setColor(val.fontColor());
		if (val.score() != 0)
			g.drawString(tile.toString(), xOffset + (SIDE >> 1) - MARGIN, yOffset + (SIDE >> 1));
	}

	private static int offsetCoors(int arg) {
		return arg * (MARGIN + SIDE) + MARGIN;
	}

	@Override
	public void show(State state) {
		// TODO Auto-generated method stub
		this.tiles = state_to_board(state);
		// change to use paintImmediately due to concurrency problem
		// repaint doesn't call paint immediately
		// see :
		// https://stackoverflow.com/questions/13453331/repaint-in-java-doesnt-re-paint-immediately
		this.paintImmediately(0, 0, 340, 400);
		// this.repaint();
		if (host != null)
			host.refreshScore();
	}

	@Override
	public Action getAction(State state) {
		return null;
	}

	private Tile[] state_to_board(State state) {
		Tile[] tiles = new Tile[4 * 4];
		int[][] board = state.getBoard();
		for (int i = 0; i < board.length; i++)
			for (int j = 0; j < board.length; j++)
				tiles[i * board.length + j] = new Tile(Value.of(board[i][j]));
		return tiles;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	/**
	 * something wrong with the action direction
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		switch (e.getKeyCode()) {
		case KeyEvent.VK_UP: // not VK_KP_UP
			game.move(State.Action.UP);
			break;
		case KeyEvent.VK_DOWN:
			game.move(State.Action.DOWN);
			break;
		case KeyEvent.VK_LEFT:
			game.move(State.Action.LEFT);
			break;
		case KeyEvent.VK_RIGHT:
			game.move(State.Action.RIGHT);
			break;
		}

		// if you use repaint() method in show()
		// then you will see game.state.display() is different from show due to
		// concurrency problem of repaint and paint
//		game.state.display();
//		System.out.println() ; 
		show(game.state);
		if (game.state.legalAction.isEmpty()) {
			Utils.endGameSound(game.state);
			// if you use timeunit.seconds.sleep
			// the whole app will stop for 5 second, and you cannot even exit
			// one solution is to make the exit button another thread
			// when click exit, Thread.currentThread.interrupt() ;
			try {
				// TimeUnit.SECONDS.sleep(5);
				Thread.sleep(5000);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
			}
			GUI topFrame = (GUI) (JFrame) SwingUtilities.getWindowAncestor(this);
			topFrame.gameToMain();
		}

	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}
	
	public void setUpGame() {
		game = new Game(this)  ;
		game.newGame();
	}
}
