package graph;

public class EntityPair implements Comparable<EntityPair>{

	public Entity entity;
	public double similarity;
	
	public EntityPair(Entity e, double similarity){
		this.entity = e;
		this.similarity = similarity;
	}

	public double getSimilarity(){
		return similarity;
	}
	
	@Override
	public int compareTo(EntityPair o) {
		if(similarity>o.similarity) return 1;
		else if(similarity == o.similarity) return 0;
		return -1;
	}
	
	
	
}
