package graph;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * class to represent a directed graph using adjacency lists
 */
public class Graph {

	private TreeMap<String, Entity> graph;

	/**
	 * creates a new instance of Graph with n vertices
	 */
	public Graph(int n) {
		this.graph = new TreeMap<String, Entity>();
	}

	public Graph() {
		this.graph = new TreeMap<String, Entity>();
	}

	// //copy constructor
	// public Graph(ArrayList<Entity> input) {
	// this.data = input;
	// }

	public Entity getEntity(String id) {
		return graph.get(id);
	}

	// public ArrayList<Entity> getData() {
	// return data;
	// }
	//
	// public void setData(ArrayList<Entity> data) {
	// this.data = data;
	// }

	public TreeMap<String, Entity> getGraph() {
		return graph;
	}

	public void setGraph(TreeMap<String, Entity> graph) {
		this.graph = graph;
	}

	public void addEdge(Entity x, Entity y) {
		x.addForwardLink(y);
		y.addBackLink(x);
	}

	public void removeEdge(Entity x, Entity y) {
		x.removeForwardLink(y);
		y.removeBackLink(x);
	}
	
	public boolean connected(Entity x, Entity y){
		return ((x.getBacklinkEntities().contains(y) && y.getForwardlinkEntities().contains(x)) || (y.getBacklinkEntities().contains(x) && x.getForwardlinkEntities().contains(y)));
	}

	public void addData(Entity e) {
		if (!e.equals(null)) {
			graph.put(e.getId(), e);
		}

	}
	
	public int getSize(){
		return this.graph.size();
	}
	
	public Entity firstUnmarked(Entity e, TreeSet<Entity> marked, boolean t){
		
		ArrayList<Entity> neighbours = new ArrayList<Entity>();
		Entity firstUnmarked = null;
		if(t == true){
			neighbours = e.getForwardlinkEntities();
		}
		else{
			neighbours = e.getBacklinkEntities();
		}
		
		for(Entity x: neighbours){
			if(!marked.contains(x)){
				firstUnmarked = x;
				break;
			}
		}
		
		return firstUnmarked;
		
	}
	
	
	
	public void bfs(Entity start, int maxDepth, boolean t){
		
		final class Pair{
			public Entity entity;
			public int depth;
			
			public Pair(Entity l, int d){
				this.entity = l;
				this.depth =d;
			}
		}
		
		
		
		if(maxDepth <0){
			return;
		}
		
		int currentDepth = 0;
		TreeSet<Entity> marked = new TreeSet<Entity>();
		marked.add(start);
		
		Pair i = null;
		Pair j = null;
		
		Queue<Pair> q = new LinkedList<Pair>();
		q.add(new Pair(start, 0));
		while(!q.isEmpty()){
			i = q.remove();
			j.entity = firstUnmarked(i.entity, marked, t);
			
			if(i.depth>=maxDepth){
				continue;
			}
			j.depth = i.depth + 1;
			while(!j.equals(null)){
				marked.add(j.entity);
				q.add(j);
				j.entity = firstUnmarked(i.entity, marked, t);
			}
		}
	}

//	public void removeData(Entity e) {
//
//		if (e.equals(null)) {
//			data.remove(e);
//		}
//	}
}
