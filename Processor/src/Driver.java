import java.io.IOException;
import java.util.ArrayList;


public class Driver {

	public static void main(String[] args) throws IOException{
		KeywordExtractor keywordExtractor = new KeywordExtractor("globalterrorismdb_0814dist.txt");
		keywordExtractor.extractAllEvents();
		for(ArrayList<String> i:keywordExtractor){
			for(int j=1; j<i.size(); j++){
				
			}
		}
		
		
	}
	
}
