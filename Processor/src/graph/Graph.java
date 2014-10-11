package graph;
import java.util.TreeMap;

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

	public void addData(Entity e) {
		if (!e.equals(null)) {
			graph.put(e.getId(), e);
		}

	}
	
	public int getSize(){
		return this.graph.size();
	}

//	public void removeData(Entity e) {
//
//		if (e.equals(null)) {
//			data.remove(e);
//		}
//	}
}
