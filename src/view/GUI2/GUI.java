package view.GUI2;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.CardLayout;

public class GUI extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7629025040265086497L;
	/**
	 * when you are creating UI, very often you come to a place in thinking about
	 * whether you should use extend (is a relationship) or (has a relationship).
	 * Keep in mind that extends is for if you want to create more functions for the
	 * superclass or override the super class function. If you want to do that, use
	 * extends. If not you can simply use has a relationship. The third option is
	 * not to use extend but use anonymous child class. This is a (is a
	 * relationship) too. The good thing is anonymous class is simpler and you can
	 * keep the child class in the same file. Bad thing is anonymous class has no
	 * constructor and the method it override should be defined / declared in the
	 * super class otherwise, only this anonymous class has the special function
	 * which doesn't make sense.
	 * 
	 * read this :
	 * https://stackoverflow.com/questions/16235191/overridding-methods-in-anonymous-class
	 */
	JPanel cards;
	MainPage mainpage;
	GamePage gamepage;

	/**
	 * after you implement this, you can change the size of frame when changing card
	 * in card layout note that you also have to use do pack() in mainToGame and
	 * gameToMain. Pack() will do this by finding the preferred layout size
	 * 
	 * @author Yomaru
	 *
	 */
	public static class MyCardLayout extends CardLayout {

		/**
		 * 
		 */
		private static final long serialVersionUID = -8307613296966579310L;

		@Override
		public Dimension preferredLayoutSize(Container parent) {

			Component current = findCurrentComponent(parent);
			if (current != null) {
				Insets insets = parent.getInsets();
				Dimension pref = current.getPreferredSize();
				pref.width += insets.left + insets.right;
				pref.height += insets.top + insets.bottom;
				return pref;
			}
			return super.preferredLayoutSize(parent);
		}

		public Component findCurrentComponent(Container parent) {
			for (Component comp : parent.getComponents()) {
				if (comp.isVisible()) {
					return comp;
				}
			}
			return null;
		}

	}

	public GUI() {
		super("my 2048");
		createAndShowGUI();
	}

	private void createAndShowGUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		cards = new JPanel(new MyCardLayout());
		mainpage = new MainPage();
		cards.add(mainpage, "Main");
		gamepage = new GamePage(this);
		cards.add(gamepage, "Game");
		setContentPane(cards);

		pack();
		// set the frame visible at the very last
		setVisible(true);
	}

	void mainToGame() {
		((CardLayout) cards.getLayout()).show(cards, "Game");
		pack();
	}

	void gameToMain() {
		((CardLayout) cards.getLayout()).show(cards, "Main");
		gamepage.gameBoard.removeKeyBind();
		pack();
	}

}
