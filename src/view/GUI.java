package view;

import java.awt.BorderLayout;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JFrame;
import javax.swing.JLabel;

import model.State;
import model.action.Action;

public class GUI extends JFrame implements IGUI{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -217740851588006299L;

	Board gameBoard; 
	
	private static final String TITLE = "2048 in Java" ; 
	public static JLabel statusBar ;  
	
	public static final String WIN_MSG = "AI won... AI is happy " ;
	public static final String LOSE_MSG = "This makes me a saaaaaaaaaad AI..." ; 
	
	public GUI() {
		setTitle(TITLE) ; 
		setDefaultCloseOperation(EXIT_ON_CLOSE) ; 
		setSize(340,400 ) ; 
		setResizable(false); 
		
		statusBar = new JLabel("");
		add(statusBar, BorderLayout.SOUTH) ; 
		
		gameBoard = new Board(this); 
		add(gameBoard); 
		setLocationRelativeTo(null ) ; 
		setVisible(true); 
	}

	@Override
	public void show(State state) {
		gameBoard.tiles = state_to_board(state); 
		gameBoard.repaint();
		
	}

	private Tile[] state_to_board(State state) {
		Tile[] tiles = new Tile[4*4] ; 
		int[][] board = state.getBoard() ; 
		for(int i = 0 ; i < board.length ; i++ )
			for(int j =0 ; j < board.length ; j++ )
					tiles[i * board.length + j] = new Tile (Value.of(board[i][j])) ; 
		return tiles ; 
	}

	@Override
	public Action getAction(State state) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void win() {
		statusBar.setText(WIN_MSG);;
	}
	
	public void lose() {
		statusBar.setText(LOSE_MSG);
	}
	
	public static void playSound(String file) { 
		AudioInputStream audioIn = null ; 
		try {
			audioIn = AudioSystem.getAudioInputStream(GUI.class.getResource(file)); 
			Clip clip = AudioSystem.getClip(); 
			clip.open(audioIn) ; 
			clip.start() ; 
		} catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
	}

}
