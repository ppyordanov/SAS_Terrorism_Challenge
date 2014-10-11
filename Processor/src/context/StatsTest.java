package context;

import static org.junit.Assert.assertEquals;

import java.util.TreeSet;

import org.junit.Test;

public class StatsTest {

	@Test
	public void testJaccard(){
		TreeSet<String> s1 = new TreeSet<String>();
		s1.add("a");
		s1.add("b");
		TreeSet<String> s2 = new TreeSet<String>();
		s2.add("a");
		s2.add("b");
		s2.add("c");
		assertEquals(Stats.calculateJacard(s1, s2),2.0/3,0.1);
	}
	
}
