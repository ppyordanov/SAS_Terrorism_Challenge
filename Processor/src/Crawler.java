import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Crawler {

	public static HashMap<String, ArrayList<String>> getWikiResults(ArrayList<String> keywords)
			throws IOException, JSONException {
		HashMap<String, ArrayList<String>> results = new HashMap<String, ArrayList<String>>();
		for (String keyword : keywords) {
			ArrayList<String> links = new ArrayList<String>();
			HttpURLConnection connection = null;
			URL url = new URL(
					"http://en.wikipedia.org/w/api.php?format=json&action=query&list=search&srsearch="
							+ keyword);
			connection = (HttpURLConnection) url.openConnection();
			connection.connect();
			BufferedReader br = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));
			StringBuffer sb = new StringBuffer();
			String line = "";
			while ((line = br.readLine()) != null)
				sb.append(line);
			JSONObject rootJson = new JSONObject(sb.toString());
			JSONObject queryJson = rootJson.getJSONObject("query");
			JSONArray searchResults = queryJson.getJSONArray("search");
			for (int i = 0; i < searchResults.length(); i++) {
				JSONObject searchResult = (JSONObject) searchResults.get(i);
				links.add((String) searchResult.get("title"));
			}
			results.put(keyword, links);
			connection.disconnect();
		}
		return results;
	}

}
