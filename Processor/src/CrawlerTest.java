import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import org.json.JSONException;
import org.junit.Assert;
import org.junit.Test;


public class CrawlerTest {
	
	@Test
	public void test1() throws IOException, JSONException{
		ArrayList<String> keywords = new ArrayList<String>();
		keywords.add("Dark_Avengers");
		String[] assertList = {"Dark Avengers"
				,"Dark Avenger"
				,"The Dark Avenger"
				,"Dark Reign (comics)"
				,"Utopia (comics)"
				,"Ares (Marvel Comics)"
				,"Sentry (Robert Reynolds)"
				,"Siege (comics)"
				,"Thunderbolts (comics)"
				,"Daken"};
		HashMap<String, ArrayList<String>> result = Crawler.getWikiResults(keywords);
		for(Entry<String, ArrayList<String>> e : result.entrySet()){
			ArrayList<String> al = e.getValue();
			for(int i=0; i<assertList.length; i++){
				Assert.assertTrue(al.get(i).equals(assertList[i]));
			}
		}
	}
	
}
