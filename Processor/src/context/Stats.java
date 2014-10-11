package context;

import java.util.TreeSet;

public class Stats {

	public static double calculateJacard(TreeSet<String> s1, TreeSet<String> s2){
		TreeSet<String> intersection = new TreeSet<String>(s1);
		intersection.retainAll(s2);
		System.out.println("intersection size is "+intersection.size());
		TreeSet<String> union = new TreeSet<String>(s1);
		union.addAll(s2);
		System.out.println("union is "+union.size());
		return union.size() == 0 ? 0 : intersection.size()*1.0/union.size();
	}
	
	
}
