package graph;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

public class GraphTest {
	
	private int initCount;
	private Graph test;
	private Entity testE;
	
	@Before
	public void setUp() {
		test = new Graph();
		initCount = 0;
		ArrayList<Entity> test1 = new ArrayList<Entity>();
		ArrayList<String> test2 = new ArrayList<String>();
		
		testE = new Entity("id", "page", test1, test1 , test2, 2.0);
	}
	


	@Test
	public void instantiateTest() {
		
		assertEquals(test.getSize(), 0);
	}
	
	@Test
	public void addEntitySuccessTest(){
		initCount = test.getSize();
		
		test.addData(testE);
		
		assertEquals(test.getSize(), initCount + 1);
	}
	
	@Test
	public void getEntitySuccessTest(){
		test.addData(testE);
		
		
		Entity compare = test.getEntity(testE.getId());
		
		assertEquals(testE.getPage(), compare.getPage());
		assertEquals(testE.getWeight(), compare.getWeight(), 0.001);
		
		
	}
	
	@Test
	public void getEntityFailTest(){
		
		assertEquals(test.getEntity("random"), null);
		
	}
	
	@Test
	public void addEdgeSucessTest(){
		
		Entity testE2 = testE;
		testE2.setId("random");
	
		test.addData(testE2);
		
		test.addEdge(testE, testE2);
		assertEquals(test.connected(testE, testE2), true);
		
	}

	
	
	
	@Test
	public void getSizeTest(){
		
		
		
		assertEquals(test.getSize(), 0);
		
	}


	
}
