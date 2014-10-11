package context;

import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONException;
import org.junit.Assert;
import org.junit.Test;

public class CrawlerTest {

	@Test
	public void testPage() throws IOException, JSONException {
		String keywords = "Dark_Avengers";
		String[] assertList = { "Dark Avengers", "Dark Avenger",
				"The Dark Avenger", "Dark Reign (comics)", "Utopia (comics)",
				"Ares (Marvel Comics)", "Sentry (Robert Reynolds)",
				"Siege (comics)", "Thunderbolts (comics)", "Daken" };
		ArrayList<String> result = Crawler.getWikiResults(keywords);
		for (int i=0; i<assertList.length; i++) {
			Assert.assertTrue(result.get(i).equals(assertList[i]));
		}
	}
	
}
