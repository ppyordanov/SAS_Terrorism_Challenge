import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

public class Crawler {
	
	//TODO: test method, remove later
	public static void main(String[] args) throws IOException, JSONException{
		ArrayList<String> list = new ArrayList<String>();
		list.add("Dark_Avengers");
		getWikiResults(list);
	}

	
	public static void getWikiResults(ArrayList<String> keywords) throws IOException, JSONException {
		for(String keyword: keywords){
		HttpURLConnection connection = null;
	    try {
	        URL url = new URL("http://en.wikipedia.org/w/api.php?format=json&action=query&list=search&srsearch="+keyword);
	        connection = (HttpURLConnection) url.openConnection();
	        connection.connect();
	        BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
	        String json = "";
	        String line = "";
	        while((line = br.readLine())!=null){
	        	json += line;
	        }
	        
	        JSONObject object = new JSONObject(json);
	        JSONObject q = object.getJSONObject("query");
	        JSONArray v = q.getJSONArray("search");
	        for(int i=0; i<v.length(); i++){
	        	JSONObject j = (JSONObject) v.get(i);
	        	System.out.println(j.get("title"));
	        }
	        
	        
	    } catch (MalformedURLException e1) {
	        e1.printStackTrace();
	    } catch (IOException e1) {
	        e1.printStackTrace();
	    } finally {
	        if(null != connection) { connection.disconnect(); }
	    }
		}
	}

}
