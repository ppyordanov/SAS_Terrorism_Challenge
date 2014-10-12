package graph;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.TreeMap;
import java.util.TreeSet;


public class Entity {

	private static TreeMap<String, Entity> map = new TreeMap<String, Entity>();
	
	private String id;
	private String page;
	private ArrayList<Entity> backlinkEntities;
	private ArrayList<Entity> forwardlinkEntities;
	private ArrayList<String> keywords;
	private TreeSet<String> categoies;
	private double weight;

	//TODO: SortedList
	private ArrayList<EntityPair> similarPages;
	
	public static Entity getEntity(String id, String page, ArrayList<Entity> relevantEntities, ArrayList<String> keywords, double weight ){
		Entity entity = map.get(id);
		if(entity != null) {
			if (entity.page == null || entity.page.isEmpty()) {
				entity.page = page;
			}
			return map.get(id);
		}
		entity = new Entity(id, page, null, null, weight );
		map.put(id, entity);
		return entity;
	}
	
	private Entity(){
		page = "";
		keywords = new ArrayList<String>();
		weight = 0;
		id = "";
		this.categoies = new TreeSet<String>();
		this.similarPages = new ArrayList<EntityPair>();
	}
	
	private Entity(String id, String page, ArrayList<Entity> relevantEntities, ArrayList<String> keywords, double weight ){
		this.id = id;
		this.page = page;
		this.keywords = keywords;
		this.weight = weight;
		this.categoies = new TreeSet<String>();
		this.similarPages = new ArrayList<EntityPair>();
	}
	
	public ArrayList<EntityPair> getSimilarPages(){
		Collections.sort(similarPages);
		return similarPages;
	}
	
	public void addSimilarPage(EntityPair e){
		similarPages.add(e);
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
		s += id + " " + page + " " + Arrays.toString(keywords.toArray()) + " {";
		for (EntityPair p : similarPages) {
			s += p.similarity + " <- " + p.entity.page + "\n";
		}
		s += "}";
		return s;
	}
	
	
}
