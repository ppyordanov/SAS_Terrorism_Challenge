package graph;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.TreeSet;


public class Entity {

	private String id;
	private String page;
	private ArrayList<Entity> backlinkEntities;
	private ArrayList<Entity> forwardlinkEntities;
	private ArrayList<String> keywords;
	private TreeSet<String> categoies;
	private double weight;
	
	
	public Entity(){
		page = "";
		keywords = new ArrayList<String>();
		weight = 0;
		id = "";
		this.categoies = new TreeSet<String>();
	}
	
	public Entity(String id, String page, ArrayList<Entity> relevantEntities, ArrayList<String> keywords, double weight ){
		this.id = id;
		this.page = page;
		this.keywords = keywords;
		this.weight = weight;
		this.categoies = new TreeSet<String>();
		
	}
	
	public void addCategory(String category){
		categoies.add(category);
	}
	
	public TreeSet<String> getCategories(){
		return categoies;
	}
	
	public void addForwardLink(Entity e){
		this.forwardlinkEntities.add(e);
	}
	
	public void removeForwardLink(Entity e){
		this.forwardlinkEntities.remove(e);
	}
	
	public void addBackLink(Entity e){
		this.backlinkEntities.add(e);
		this.weight += e.getWeight();
	}
	
	public void removeBackLink(Entity e ){
		
		this.backlinkEntities.remove(e);
		this.weight -= e.getWeight();
	}


	public String getPage() {
		return page;
	}


	public void setPage(String page) {
		this.page = page;
	}


	public ArrayList<String> getKeywords() {
		return keywords;
	}


	public void setKeywords(ArrayList<String> keywords) {
		this.keywords = keywords;
	}


	public double getWeight() {
		return weight;
	}


	public void setWeight(double weight) {
		this.weight = weight;
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}
	
	
	@Override
	public String toString(){
		String s = "";
		s += id + " " + page + " " + Arrays.toString(keywords.toArray());
		return s;
	}
	
	
}
