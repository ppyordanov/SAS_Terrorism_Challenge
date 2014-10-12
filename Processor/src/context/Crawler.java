package context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;

import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;

public class Crawler {

	public static ArrayList<String> getWikiResults(String keyword) {
		ArrayList<String> links = new ArrayList<String>();
		HttpURLConnection connection = null;
		URL url;
		try {
			url = new URL(
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

		} catch (Exception e) {
			e.printStackTrace();
		}
		connection.disconnect();
		return links;
	}

	public static String getArticleText(String pageTitle) throws IOException {
		String title = "";
		for (String word : pageTitle.split(" ")) {
			title += "_" + word;
		}
		URL url = new URL("http://en.wikipedia.org/wiki/" + title.substring(1));
		URLConnection connection = null;
		String text = "";
		try {
			connection = url.openConnection();
			BufferedReader br = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));
			for (String line = br.readLine(); line != null; line = br
					.readLine()) {
				text += line;
			}
			System.out.println("Done with " + url.getPath());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return Jsoup.parse(text).text();
		// return Jsoup.parse((new Wiki()).getRenderedText(pageTitle)).text();
	}
	
	public static ArrayList<String> getCategories(String pageTitle){
		ArrayList<String> a = new ArrayList<String>();
		String title = "";
		for (String word : pageTitle.split(" ")) {
			title += "_" + word;
		}
		URL url;
		try {
			url = new URL("http://en.wikipedia.org/wiki/" + title.substring(1));
			URLConnection connection = null;
			String text = "";
			connection = url.openConnection();
			BufferedReader br = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));
			for (String line = br.readLine(); line != null; line = br
					.readLine()) {
				text += line;
			}
			int curr = 0;
			String pattern = "<a href=\"/wiki/Category:";
			int next = text.indexOf(pattern, curr);
			String ref = "";
			while(next > 0){
				for(int i=next + pattern.length(); i<text.length(); i++){
					char c = text.charAt(i);
					if(c=='"'){
						a.add(ref);
						ref = "";
						break;
					}
					ref += c;
				}
				curr = next + 1;
				next = text.indexOf(pattern,curr);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return a;
	}
	
	public static Set<String> getAdjacentArticles(String pageTitle) {
		Set<String> neighbours = new TreeSet<String>();
		Wiki wiki = new Wiki();
		try {
			neighbours.addAll(Arrays.asList(wiki.whatLinksHere(pageTitle, Wiki.MAIN_NAMESPACE)));
			neighbours.addAll(Arrays.asList(wiki.getLinksOnPage(pageTitle)));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return neighbours;
	}
	
	public static Set<String> getSetIntersection(Set<String> a, Set<String> b) {
		if (a == null || b == null) return new TreeSet<String>();
		Set<String> res = new TreeSet<String>(a);
		res.retainAll(b);
		return res;
	}
	
	public static Set<String> getMostRelatedArticles(String pageTitle) {
		Set<String> neighs = Crawler.getAdjacentArticles(pageTitle);
		Set<String> results = null;
		try {
			results = new TreeSet<String>(Crawler.getWikiResults(pageTitle));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Crawler.getSetIntersection(neighs, results);
	}
}
