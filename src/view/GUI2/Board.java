package view.GUI2;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import model.State;
import model.action.Action;
import view.IGUI;


/**
 * this is a remake of Board in the GUI package. 
 * change : 
 * 1. key action is done by using key binding and abstract action instead of keylistener
 * >>>>good thing : doesn't require request focus in window, good management of key binding and key event
 * 
 * 
 * @author Yomaru
 *
 */
public class Board extends JPanel implements IGUI{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7220773861050733934L;
	public Tile[] tiles ; 
	public static final int[] _0123 = {0,1,2,3} ; 
	public Game game ; 
	public ExecutorService executor = Executors.newFixedThreadPool(1) ; 
	public GamePage host ; 
	boolean flag = false ;
	
	private static final int IFW = JComponent.WHEN_IN_FOCUSED_WINDOW;

	private class Move extends AbstractAction{
		/**
		 * 
		 */
		private static final long serialVersionUID = -1446239023651520426L;
		String s ; 
		Move(String s ){
			this.s = s ; 
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			switch (s ) {
			case "UP":
				game.move(State.Action.UP);
				break ; 
			case "DOWN" : 
				game.move(State.Action.DOWN);
				break ; 
			case "LEFT" : 
				game.move(State.Action.LEFT);
				break ; 
			case "RIGHT" : 
				game.move(State.Action.RIGHT);
				break ; 
			default : 
				break ; 
				
			}
		}
		
	}
	
	/**
	 * create a board with action map
	 * set up a game with no initial state
	 * @param host can be null 
	 */
	public Board(GamePage host ) {
		setFocusable(true);
		setPreferredSize(new Dimension(340, 400));
		initTiles();
		// set up game
		game = new Game(this) ; 
		this.host = host ; 
		
		this.getActionMap().put("UP", new Move("UP"));
		this.getActionMap().put("DOWN", new Move("DOWN"));
		this.getActionMap().put("LEFT", new Move("LEFT"));
		this.getActionMap().put("RIGHT", new Move("RIGHT"));
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
	
	/**
	 * only one thread can show the board 
	 */
	@Override
	public synchronized void show(State state) {
		this.tiles = state_to_board(state);
		this.paintImmediately(0, 0, 340, 400);
		//System.out.println(game.getState()); 
		if(host != null )
			host.refreshScore();
		if(state.legalAction.isEmpty()) 
			flag = true; 
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
	public Action getAction(State state) {
		// TODO Auto-generated method stub
		return null;
	}

	
	/**
	 * set key binding or remove key binding depends on the player 
	 * create a new game with initial state 
	 * @param player
	 */
	public void setUpGame(boolean player) {
		if(player)
			setKeyBind() ; 
		else 
			removeKeyBind() ;
		game = new Game(this ) ; 
		game.newGame() ;
	}
	
	public void setKeyBind() {
		//add key binding 
		this.getInputMap(IFW).put(KeyStroke.getKeyStroke("UP"), "UP");
		this.getInputMap(IFW).put(KeyStroke.getKeyStroke("DOWN"), "DOWN");
		this.getInputMap(IFW).put(KeyStroke.getKeyStroke("LEFT"), "LEFT");
		this.getInputMap(IFW).put(KeyStroke.getKeyStroke("RIGHT"), "RIGHT");
	}
	
	public void removeKeyBind() {
		this.getInputMap(IFW).remove(KeyStroke.getKeyStroke("UP"));
		this.getInputMap(IFW).remove(KeyStroke.getKeyStroke("DOWN"));
		this.getInputMap(IFW).remove(KeyStroke.getKeyStroke("LEFT"));
		this.getInputMap(IFW).remove(KeyStroke.getKeyStroke("RIGHT"));

	}
}
