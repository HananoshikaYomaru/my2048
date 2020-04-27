package my2048;

import java.awt.Dimension;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.swing.JFrame;
import javax.swing.SwingWorker;

import AI.AI;
import AI.ExpectiMax;
import AI.RandomAction;
import AI.heuristic.nn.Cocktail;

import view.GUI2.*; 

public class Main2 {

	static int iteration = 10;
	public static String[] AINames = { "ExpectiMax", "MCTS", "Minimax", "One Step Prediction", "Random Action" };
	// same as
	// public static String[] AINames = new String[] {"ExpectiMax" ,
	// "MCTS","Minimax" , "One Step Prediction" , "Random Action"} ;

	public static void main2(String args[]) {

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
	
	
	/**
	 * pay attention to the difference of runnable, thread and swingworker !!!
	 * 
	 * there are multiple ways of stopping a thread
	 * using stopFlag
	 * using stop function (if any) note that the stop function of thread is depreciated. There is done function in swingworker
	 * using interrupt 
	 * 
	 * @param args
	 */
	public static void main5( String args[] ) {
		
		ExecutorService executor = Executors.newFixedThreadPool(1) ; 
		AI ai = new ExpectiMax(2) ; 
		ai.setHeuristic(new Cocktail());
		Game game = new Game(null) ; 
		game.setAI(ai);
		executor.execute(game);
		Game game2 = new Game(null) ; 
		game2.setAI(ai);
		executor.execute(game2);
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//is there any way that I can synchornize this two ? 
//		System.out.println("change stopFlag!!!!!!!!!!") ; 
//		game.flag = false ; 
		System.out.println(Thread.activeCount()) ; 
		System.out.println("cancel!!!!!!!!!") ; 
		System.out.println(Thread.activeCount()) ; 
		game.stopFlag = true ; 
//		System.out.println("done!!!!!!!!!!!!!!") ; 
//		game.done();
		System.out.println(Thread.activeCount()) ; 
		System.out.println("stop it!!!!!!!!!")  ; 
		System.out.println(Thread.activeCount()) ; 
	}
	
	static class SleepingThread extends Thread {
		
		SleepingThread another ; 
		@Override 
		public void run () { 
			int i = 1 ; 
			while(true)
			{
				System.out.println(i + "\t" + Thread.currentThread().getPriority()) ; 
				another.interrupt() ; 
				try{Thread.sleep(10000);}catch(InterruptedException e){}  
				i++ ; 
			}
		}
	}
	
	public static void main4(String args[]) {
		System.out.println("start") ; 
		SleepingThread st1 = new SleepingThread()  ;
		SleepingThread st2 = new SleepingThread()  ;
		
		st1.start();
		try {
			st1.join() ;
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		st2.start();
		try {
			st2.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("show where I am ") ; 
	}
	
	public static void main3(String args[] ) {
		System.out.println(Thread.currentThread().getName())  ; 
		System.out.println(Thread.currentThread().getId() )  ; 
		System.out.println(Thread.currentThread().getPriority())  ; 
		
		SleepingThread st1 = new SleepingThread()  ;
		SleepingThread st2 = new SleepingThread()  ;
		
		st1.setPriority(10);
		st2.setPriority(1);
		
		st1.another = st2 ; 
		st2.another = st1 ; 
		
		st1.start();
		st2.start();
		
		System.out.println("show where I am ") ; 
	}

	public static void main8(String args [] ) {
		JFrame jf = new JFrame() ; 
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GamePage gp = new GamePage(null) ; 
		Board board = new Board(gp)  ; 
		board.setUpGame(false )  ;
		AI ai = new RandomAction() ; 
		ai.setHeuristic(new Cocktail() );
		board.game.setAI(ai);
		jf.getContentPane().add(board) ; 
		jf.setSize(new Dimension(340,450));
		jf.setVisible(true);
		board.executor.execute(board.game);
	}
	
	
	public static void main6(String args[] ) {
		A a1 = new A() ; 
		A a2 = new A () ;
		a1.another = a2 ; 
		a2.another = a1 ; 
		a1.execute();
//		a2.execute()  ;
		
		try {
			Thread.sleep(5000);
		}catch (Exception e ) {
			e.printStackTrace();
		}
		
		try {
			a1.wait();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
//		try {
//			a2.wait();
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
	
	private static class A extends SwingWorker<Void , Void >{
		
		@SuppressWarnings("unused")
		A another ; 
	
		@Override
		protected Void doInBackground() throws Exception {
			while(true) {
				System.out.println("AAAAAAAAAAAAAAAAAAAA") ; 
			} 
		}
		@Override 
		public void done() {
			System.out.println("done") ; 
		}
		
	}
	public static void main7(String args []) {
		B b = new B() ; 
		b.execute();
		try {
			Thread.sleep(500000);
		}catch (Exception e ) {
			e.printStackTrace();
		}
		
	}
	
	private static class B extends SwingWorker<Void , Void >{

		@Override
		protected Void doInBackground() throws Exception {
			while(true) {
				System.out.println("BBBBBBBBBBBBBBBBBBBB") ; 
			 
			}
		}
		
	}
	
	public static void main (String args[]) {
		@SuppressWarnings("unused")
		GUI gui = new GUI () ; 
	}
}
