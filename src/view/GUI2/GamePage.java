package view.GUI2;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import utils.Utils;
import view.GUI2.GUI;
import view.GUI2.Board;

public class GamePage extends JPanel implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5850508133117637162L;
	JButton exitButton;
	public Board gameBoard;
	JLabel statusBar;
	GUI host ; 
	
	/**
	 * @param host can be null 
	 */
	public GamePage(GUI host) {
		this.host = host ; 

		exitButton = new JButton("Exit");
		exitButton.setActionCommand("Exit");
		exitButton.addActionListener(this);

		gameBoard = new Board(this);

		// statusBar = new JLabel ("", SwingConstants.CENTER) ;
		statusBar = new JLabel();
		statusBar.setHorizontalAlignment(SwingConstants.CENTER);
		//refreshScore();

		setLayout(new BorderLayout());
		add(statusBar, BorderLayout.PAGE_START);
		add(gameBoard, BorderLayout.CENTER);
		add(exitButton, BorderLayout.PAGE_END);
	}
	
	void refreshScore() {
		if(gameBoard.game.state != null )
			statusBar.setText("score : " + Utils.sum(gameBoard.game.state.getBoard()) );
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("Exit")) {
			GUI topFrame = (GUI) (JFrame) SwingUtilities.getWindowAncestor(this);
			gameBoard.game.stopFlag = true ; 
			topFrame.gameToMain();
		}
	}
}
