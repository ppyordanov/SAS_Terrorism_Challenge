import java.util.ArrayList;


public class Data_Struct {
	

	private ArrayList<Entity> data;
	
	public Data_Struct(){
		data = new ArrayList<Entity>();
	}
	
	public Data_Struct(ArrayList<Entity> data){
		this.data = data;
	}

	public ArrayList<Entity> getData() {
		return data;
	}

	public void setData(ArrayList<Entity> data) {
		this.data = data;
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
