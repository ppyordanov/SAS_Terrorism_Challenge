import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class Crawler {

	public static void getWikiResults(ArrayList<String> keywords)
			throws IOException, JSONException {
		for (String keyword : keywords) {
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
				System.out.println(searchResult.get("title"));
			}
			connection.disconnect();
		}
	}

}
