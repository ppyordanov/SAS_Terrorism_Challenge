import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONException;


public class Driver {

	public static void main(String[] args) throws IOException, JSONException{
		KeywordExtractor keywordExtractor = new KeywordExtractor("globalterrorismdb_0814dist.txt");
		keywordExtractor.extractAllEvents();
		for(ArrayList<String> i:keywordExtractor){
//			HashMap<String, ArrayList<String>> res = Crawler.getWikiResults( (ArrayList<String>) i.subList(1, i.size()));
		}
		
		
	}
	
}
