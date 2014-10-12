package graph;

public class Pair<K, V extends Comparable<V>> implements Comparable<Pair<K,V>> {
	public K left;
	public V right;
	
	public Pair(K k, V v) {
		left = k;
		right = v;
	}

	@Override
	public int compareTo(Pair<K, V> o) {
		// TODO Auto-generated method stub
		return right.compareTo(o.right);
	}
	
	
}
