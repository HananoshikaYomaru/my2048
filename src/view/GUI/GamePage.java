package view.GUI;

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

public class GamePage extends JPanel implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1688527754214400607L;
	JButton exitButton;
	public Board gameBoard;
	JLabel statusBar;

	// cache
	public static final String WIN_MSG = "AI won... AI is happy ";
	public static final String LOSE_MSG = "This makes me a saaaaaaaaaad AI...";

	public GamePage() {

		exitButton = new JButton("Exit");
		exitButton.setActionCommand("Exit");
		exitButton.addActionListener(this);

		gameBoard = new Board();
		gameBoard.host = this;

		// statusBar = new JLabel ("", SwingConstants.CENTER) ;
		statusBar = new JLabel();
		statusBar.setHorizontalAlignment(SwingConstants.CENTER);
		refreshScore();

		setLayout(new BorderLayout());
		add(statusBar, BorderLayout.PAGE_START);
		add(gameBoard, BorderLayout.CENTER);
		add(exitButton, BorderLayout.PAGE_END);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getActionCommand().equals("Exit")) {
			// return to main
			GUI topFrame = (GUI) (JFrame) SwingUtilities.getWindowAncestor(this);

			// if player mode, stop key listener.
			// this can be omitted because when we click the button, the focus will be shift
			// to button but not
			// the board

			// if AI mode, stop run(). This cannot be omitted, otherwise the game will
			// continue to run in background
			gameBoard.game.cancel(true);
			topFrame.gameToMain();
		}
	}

	void refreshScore() {
		statusBar.setText("score : " + Utils.sum(gameBoard.game.state.getBoard()));
	}

}
