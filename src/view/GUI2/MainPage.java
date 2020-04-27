package view.GUI2;

import java.awt.BorderLayout;
import java.awt.ComponentOrientation;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import AI.AI;
import AI.ExpectiMax;
import AI.MCTS;
import AI.Minimax;
import AI.OneStepPrediction;
import AI.RandomAction;
import AI.heuristic.nn.Cocktail;
import my2048.Main;

/**
 * this approach might be wrong because I should not create a class to extend
 * Panel simply because I want to use it once
 * 
 * @author Yomaru
 *
 */
public class MainPage extends JPanel implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6418746035880845295L;
	private JLabel l1;
	private JButton b1, b2;
	private JComboBox<String> AIchooser;

	private static final Font STR_FONT = new Font(Font.SANS_SERIF, Font.BOLD, 17);

	/**
	 * there are multiple ways to do event handling : 1. the class itself implements
	 * actionlistener <- using now 2. use anonymous class 3. use inner class 4.
	 * create an separated class
	 */
	public MainPage() {
		super(new BorderLayout());

		l1 = new JLabel("My 2048", SwingConstants.CENTER);
		l1.setText("My 2048");

		b1 = new JButton("Player");
		b1.setFont(STR_FONT);
		b1.setActionCommand("Player");
		b1.addActionListener(this); // or you can use anonymous class,
		// because ActionListen is an abstract class you must have a child class to
		// inherit it

//		b1.addActionListener(new ActionListener() {
//
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				// TODO Auto-generated method stub
//				
//			}
//			
//		});

		b2 = new JButton("AI");
		b2.setFont(STR_FONT);
		b2.setActionCommand("AI");
		b2.addActionListener(this);

		// jComboBox needs a string array as parameter
		// and jcombobox in java8 is different from that in java12 because that in
		// java12 make it raw type / generic type
		// and it need you to specify what type of object jcombobox is holding
		AIchooser = new JComboBox<String>(Main.AINames);
		AIchooser.setSelectedIndex(0);
		// AIchooser.addActionListener(this) ;

		// this.setBackground(BG_COLOR);

		JPanel AIpanel = new JPanel();
		AIpanel.setLayout(new FlowLayout());
		AIpanel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		AIpanel.add(AIchooser);
		AIpanel.add(b2);

		JPanel ButtonPanel = new JPanel();
		// ButtonPanel.setLayout(new BoxLayout(ButtonPanel, BoxLayout.PAGE_AXIS));
		// ButtonPanel.add(b1 ) ;
		// ButtonPanel.add(AIpanel) ;

		ButtonPanel.setLayout(new GridLayout(2, 2));
		ButtonPanel.add(new JPanel());
		ButtonPanel.add(b1);
		ButtonPanel.add(AIchooser);
		ButtonPanel.add(b2);
		this.add(l1, BorderLayout.PAGE_START);
		this.add(ButtonPanel, BorderLayout.CENTER);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

		GUI topFrame = (GUI) (JFrame) SwingUtilities.getWindowAncestor(this);
		Board gameBoard = topFrame.gamepage.gameBoard;
		//checking the old game status
		if(gameBoard.game != null )
			System.out.println(gameBoard.game.getState().toString()) ; 
		// switch to game page
		if (e.getActionCommand().equals("Player")) {
			// it is possible that that the board has multiple same keylistener
			// then when the game ends, it will play the end game sound track multiple times
			// a way will be remove keylistener every time from game to main
			gameBoard.setUpGame(true);
		}
		if (e.getActionCommand().equals("AI")) {
			// enter the AI page
			AI ai = null;
			switch ((String) AIchooser.getSelectedItem()) {
			case "ExpectiMax":
				ai = new ExpectiMax(2);
				break;
			case "MCTS":
				ai = new MCTS(20, 5, new OneStepPrediction());
				break;
			case "Minimax":
				ai = new Minimax(3);
				break;
			case "One Step Prediction":
				ai = new OneStepPrediction();
				break;
			case "Random Action":
				ai = new RandomAction();
				break;
			default:
				throw new IllegalStateException("jcombobox show a non-exist AI ");
			}
			
			gameBoard.game = new Game(gameBoard) ; 
			gameBoard.setUpGame(false );
			ai.setHeuristic(new Cocktail());
			gameBoard.game.setAI(ai);
			gameBoard.executor.execute(gameBoard.game); // this will call start a new thread 
		}
		topFrame.mainToGame();
	}

}
