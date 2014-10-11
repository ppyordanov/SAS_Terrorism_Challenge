import java.util.ArrayList;


public class Entity {

	
	private String page;
	private ArrayList<Entity> relevantEntities;
	private ArrayList<String> keywords;
	private double weight;
	private String id;
	
	
	public Entity(){
		page = "";
		relevantEntities = new ArrayList<Entity>();
		keywords = new ArrayList<String>();
		weight = 0;
		id = "";
	}


	public String getPage() {
		return page;
	}


	public void setPage(String page) {
		this.page = page;
	}


	public ArrayList<Entity> getRelevantEntities() {
		return relevantEntities;
	}


	public void setRelevantEntities(ArrayList<Entity> relevantEntities) {
		this.relevantEntities = relevantEntities;
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
	
	
	
	
	
	
}
