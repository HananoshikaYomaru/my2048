package my2048;

import view.GUI2.GUI;


/**
 * use Main but not Main2 because Main 2 is too messy 
 * @author Yomaru
 *
 */
public class Main {
	static int iteration = 10;
	public static String[] AINames = { "ExpectiMax", "MCTS", "Minimax", "One Step Prediction", "Random Action" };
	public static void main(String args[]) {

		javax.swing.SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				try {
					@SuppressWarnings("unused")
					GUI gui = new GUI();
				} catch (Exception e) {
					e.printStackTrace();
				}

			}

		});
	}
}
