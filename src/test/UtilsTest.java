package test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test; //if this fails
//原因一：常规原因，导入的jar包相冲突
//原因二：写junit测试的java类名为Test.java
//一定要用Test.java的话只能在方法上加@org.junit.Test.而不能单纯加@Test

import model.Spawn;
import model.State;
import model.action.* ; 

class UtilsTest {

	//@Test
	void test1() {
		State s1 = new State();
		s1.display();
		int[][] board = Spawn.spawn(s1.getBoard()) ; 
		State s2 = new State(board) ; 
		s2.display();
	}
	
	@Test
	void actionTest() {
		State s1 = new State() ; 
		s1.display() ; 
		State s2 = new RightSwipe().getResult(s1) ; 
		s2.display() ; 
		(new UpSwipe()).getResult(s2).display(); ; 
		(new DownSwipe()).getResult(s2).display();
		(new LeftSwipe()).getResult(s2).display();  
		for(State.Action action : s1.legalAction)
			System.out.println(action.toString()) ; 
		System.out.println(s1.legalAction.size());
	}

}
