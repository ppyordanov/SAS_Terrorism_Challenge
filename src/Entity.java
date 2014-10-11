import java.util.ArrayList;


public class Entity {

	private String id;
	private String page;
	private ArrayList<Entity> backlinkEntities;
	private ArrayList<Entity> forwardlinkEntities;
	private ArrayList<String> keywords;
	private double weight;
	
	
	public Entity(){
		page = "";
		keywords = new ArrayList<String>();
		weight = 0;
		id = "";
	}
	
	public Entity(String id, String page, ArrayList<Entity> relevantEntities, ArrayList<String> keywords, double weight ){
		this.id = id;
		this.page = page;
		this.keywords = keywords;
		this.weight = weight;
		
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


	public ArrayList<Entity> getBacklinkEntities() {
		return backlinkEntities;
	}

	public void setBacklinkEntities(ArrayList<Entity> backlinkEntities) {
		this.backlinkEntities = backlinkEntities;
	}

	public ArrayList<Entity> getForwardlinkEntities() {
		return forwardlinkEntities;
	}

	public void setForwardlinkEntities(ArrayList<Entity> forwardlinkEntities) {
		this.forwardlinkEntities = forwardlinkEntities;
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
	
	
	
	
	
	
}
