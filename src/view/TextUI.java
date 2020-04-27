package view;

import model.State;
import model.action.Action;
import utils.Utils;

import java.util.Scanner ;

public class TextUI implements IGUI{
	
	private Scanner scanner = new Scanner(System.in) ; 

	@Override
	public void show(State state) {
		// TODO Auto-generated method stub
		state.display(); 
		System.out.println() ; 
		
	}

	@Override
	public Action getAction(State state) {
		// TODO Auto-generated method stub
		State.Action action ; 
		do {
			action = readAction() ; 
		}while(!state.legalAction.contains(action)) ;
		return Utils.getActionObject(action);
	}

	private State.Action readAction() {
		// TODO Auto-generated method stub
		State.Action action = null ; 
		System.out.println("Enter direction") ; 
		String line = scanner.nextLine( ); 
		char move = line.toUpperCase().charAt(0);
		switch(move ) {
		case 'U' : 
			action = State.Action.UP; 
			break ; 
		case 'D' : 
			action = State.Action.DOWN ; 
			break ; 
		case 'L' : 
			action = State.Action.LEFT;
			break ; 
		case 'R' : 
			action = State.Action.RIGHT;
			break ; 
		default: 
			break ; 
		}
				
		return action ;
	}
	
}
