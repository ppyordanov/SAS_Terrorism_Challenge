import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONException;


public class Driver {

	public static void main(String[] args) throws IOException, JSONException{
		KeywordExtractor keywordExtractor = new KeywordExtractor("globalterrorismdb_0814dist.txt");
		keywordExtractor.extractAllEvents();
		for(ArrayList<String> keyword:keywordExtractor){
			for(int j=1; j<keyword.size(); j++){
				//create a node here
				ArrayList<String> res = Crawler.getWikiResults(keyword.get(j));
			}
		}
	}
	
}
