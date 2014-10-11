import java.util.ArrayList;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 class to represent a directed graph using adjacency lists
 */
public class Graph {

	private ArrayList<Entity> data;
	private int numVertices = 0;

	/**
	 creates a new instance of Graph with n vertices
	*/
	public Graph(int n) {
		this.data = new ArrayList<Entity>(n);
		this.numVertices = n;
	}
	
	public Graph(){
		this.data = new ArrayList<Entity>();
		this.numVertices = 0;
	}
	
	//copy constructor
	public Graph(ArrayList<Entity> input) {
		this.numVertices = input.size();
		this.data = input;
	}
	
	public Entity getEntity(int i) {
		return data.get(i);
	}
	
	public ArrayList<Entity> getData() {
		return data;
	}

	public void setData(ArrayList<Entity> data) {
		this.data = data;
	}

	public int getNumVertices() {
		return numVertices;
	}

	public void setNumVertices(int numVertices) {
		this.numVertices = numVertices;
	}
	
	public void addEdge(Entity x, Entity y){
		x.addForwardLink(y);
		y.addBackLink(x);
	}
	
	public void removeEdge(Entity x, Entity y){
		x.removeForwardLink(y);
		y.removeBackLink(x);
	}
	
	public void addData(Entity e){
		if(!e.equals(null)){
			data.add(e);
		}
		
	}
	
	public void removeData(Entity e){
		
		if(e.equals(null)){
			data.remove(e);
		}
	}

}
