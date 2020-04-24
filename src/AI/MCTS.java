package AI;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import AI.heuristic.Heuristic;
import model.Spawn;
import model.State;
import model.action.Action;
import utils.Utils; 

public class MCTS implements AI {

	private Heuristic heuristic ; 
	private int iteration  ;
	private int selection_iteration ; 
	private int simulation_iteration; 
	private AI simBot ;
	private State.Action lastAction ; 
	private boolean done_search_root; 
	private int round = 0 ; 
	private Node root ; 
	
	//constant 
	private final double infinity = Double.MAX_VALUE ; 
	private final ArrayList<State.Action> allActions = Utils.getAllActions() ;
	
	
	public MCTS(int iteration, int simulation_iteration, AI simBot) { 
		this.iteration = iteration ; 
		this.simulation_iteration = simulation_iteration ; 
		this.simBot = simBot ; 
	}
	
	private class Node {
		State.Action action_from_parent ; 
		State state ; 
		Set<State.Action> allLegalActions ; 
		double score = 0; 
		double UCB ; 
		int n = 0 ;
		
		Node parent ; 
		ArrayList<Node> children = new ArrayList<Node>()  ; 
		Node (State.Action action ){
			this.action_from_parent = action ; 
		}
	}
	
	@Override
	public Action getAction(State state) {
		// TODO Auto-generated method stub
		round++ ; 
		if(root == null) {
			root = new Node(null) ; 
			root.state = state ; 
			root.allLegalActions = state.legalAction ; 
			root.parent = null ; 
		}
		else 
			root = trimTree(state) ; 
		done_search_root = false ;
		
		for(int i = 0 ; i < iteration ; ++i) {
			if(root.children.isEmpty()) {
				expand(root) ; 
				continue ;
			}
			ArrayList<Node> bp_node = new ArrayList<Node>() ;
			ArrayList<Double> bp_score = new ArrayList<Double >() 	;
			selection_iteration =  Utils.getEmptySquares(Utils.getActionObject(findMaxChild(root).action_from_parent).getResult(state).getBoard()).size() * 2  ; 
			for(int j = 0 ; j < selection_iteration ; ++j) {
				Node n = selection(root) ; 
				double score =  0; 
				if( n.allLegalActions.isEmpty()) { // if cannot do anything about it
					score = Utils.sum(n.state.getBoard()) ; 
					bp_node.add(n) ; 
					bp_score.add(score) ; 
				}
				else {
					Node nodeToSimulate = n ; 
					if(n.n > 0 || n==root) {
						expand(n) ; 
						nodeToSimulate = actionFindChild(n, Utils.getRandomAction(n.state)) ; 
					}
					score = simulate(nodeToSimulate) ;
					bp_node.add(nodeToSimulate) ;
					bp_score.add(score ); 
				}
			}
			for(int j = 0 ; j  < bp_node.size() ; j++)
				backpropagate(bp_node.get(j) , bp_score.get(j)) ;
		}
		lastAction = findMaxChild(root).action_from_parent ; 
		return Utils.getActionObject(lastAction );
	}
	
	private double simulate(Node node) {
		//when you do simulation, the node has no state because it is newly generated from expansion
		node.state = Utils.getNewStateAfterAction(node.parent.state, node.action_from_parent) ;
		State state = node.state ; 
		double average = 0 ; 
		double new_simulation_iteration = simulation_iteration / (Math.log(selection_iteration) + 1 ) ; 
		if(new_simulation_iteration < 1)
			new_simulation_iteration = 1  ;
		for(int i =0 ; i < new_simulation_iteration ; ++i ) 
		{
			while(! state.legalAction.isEmpty())
				state = Utils.getNewStateAfterAction(state, simBot.getAction(state)) ; 
			average += Utils.sum(state.getBoard()) ; 
		}
		average /= new_simulation_iteration ; 
		return average ;
	}

	private Node actionFindChild(Node n, State.Action a) {
		for(Node child : n.children) 
			if(child.action_from_parent == a)
				return child ; 
		throw new IllegalArgumentException("no such action in this state") ;
	}
	
	private void expand(Node node) {
		if(!node.children.isEmpty())
			throw new IllegalArgumentException("illegal expansion because this node has children") ; 
		for(State.Action action : allActions) {
			Node actionChild = new Node(action ) ; 
			actionChild.parent = node ; 
			node.children.add(actionChild ); 
		}
	}
	private void backpropagate(Node node, double score) {
		node.score += score ; 
		node.n++ ; 
		if(node.parent != null )
			backpropagate(node.parent, score) ; 
		
	}
	
	private Node findMaxChild(Node node) { 
		double maxUCB = - infinity ; 
		Node result =null  ; 
		for(Node child : node.children) {
			child.UCB = UCB(child) ; 
			if(child.UCB > maxUCB && node.allLegalActions.contains(child.action_from_parent) ) {
				maxUCB = child.UCB; 
				result = child ; 
			}
		}
		if(result == null)
			throw new IllegalStateException("no max child " ) ; 
		return result ; 
	}
	
	private double UCB(Node node) {
		if(node.n == 0)
			return infinity ; 
		return node.score / node.n + Math.sqrt(2 * Math.log(root.n)) / node.n;
	}

	private Node selection(Node node) {
		if(node.children.isEmpty() || node.allLegalActions.isEmpty())
			return node; 
		Node result = null ; 
		//basic branch selection in root 
		if(node == root && done_search_root == false )
			for(Node child:node.children) {
				double minimumSelection = Math.log(iteration / allActions.size()) * Utils.getEmptySquares(Utils.getActionObject(child.action_from_parent).getResult(node.state).getBoard()).size() * 2 ;
				double minimumSelectionUpperLimit = iteration / 2.0 / node.allLegalActions.size() ; 
				if(child.n  < (minimumSelection < minimumSelectionUpperLimit ? minimumSelection :minimumSelectionUpperLimit )) 
					result = child ; 
			}
		if(result == null ) {
			done_search_root = true ; 
			result = findMaxChild(node); 
  		}
		
		//generate State for the selected node
		result.state =  Utils.getNewStateAfterAction(node.state, result.action_from_parent);
		result.allLegalActions = result.state.legalAction;
		return selection(result) ;
	}

	Node trimTree(State state ) {
		Node newRoot = actionFindChild(root, lastAction) ; 
		newRoot.state = state ; 
		newRoot.allLegalActions = state.legalAction ; 
		newRoot.parent = null ; 
		newRoot.action_from_parent = null ; 
		Set<Node> remove = new HashSet<Node>(); 
		for(Node child: newRoot.children) 
			if(!newRoot.allLegalActions.contains(child.action_from_parent))
				remove.add(child ) ; 
		newRoot.children.removeAll(remove) ; 
		return newRoot ; 
	}

	@Override
	public void setHeuristic(Heuristic heuristic) {
		// TODO Auto-generated method stub
		this.heuristic = heuristic ; 
		simBot.setHeuristic(heuristic);
	}

}
