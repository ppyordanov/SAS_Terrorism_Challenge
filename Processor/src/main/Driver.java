package main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import org.json.JSONException;

import context.Crawler;
import context.Stats;
import context.Wiki;
import extractor.KeywordExtractor;
import graph.Entity;
import graph.EntityPair;
import graph.Graph;

public class Driver {

	private static Graph graph;

	public static void main(String[] args) throws IOException, JSONException {
		Wiki wiki = new Wiki();
		KeywordExtractor keywordExtractor = new KeywordExtractor(
				"gtd_tabs.csv");
		keywordExtractor.extractAllEvents();
		graph = new Graph();
		int count = 0;
		for (ArrayList<String> keyword : keywordExtractor) {
			String query = "";
			for (int j = 1; j < keyword.size(); j++) {
				if (keyword.get(j).isEmpty())
					continue;
				query += " " + keyword.get(j);
			}
			query = query.substring(1);
			query = query.replaceAll(" ", "+");
			ArrayList<String> res = Crawler.getWikiResults(query);
			String id = keyword.get(0);
			if (res.size() == 0)
				continue;
			String pageTitle = res.get(0);
			System.err.println(id);
			Entity entity = Entity.getEntity(id, pageTitle,
					new ArrayList<Entity>(), keyword, 0.0);
			String[] categories = wiki.getCategories(pageTitle);
			if (categories != null && categories.length > 0)
				for (String category : categories)
					entity.addCategory(category);
			graph.addData(entity);

			ArrayList<String> neighbours = new ArrayList<String>();
//			String[] before = wiki
//					.whatLinksHere(pageTitle, Wiki.MAIN_NAMESPACE);
//			for (String s : before)
//				neighbours.add(s);
			String[] after = wiki.getLinksOnPage(pageTitle);
			for (String s : after)
				neighbours.add(s);
			for (String s : neighbours) {
				Entity e = Entity.getEntity(s, s, new ArrayList<Entity>(),
						new ArrayList<String>(), 0.0);
				String[] c = wiki.getCategories(s);
				if (c != null && c.length > 0)
					for (String s1 : c)
						e.addCategory(s1);
				// Weight = Jackard + cos similarity
				entity.addSimilarPage(new EntityPair(e, 
						Stats.calculateJacard(
						entity.getCategories(), e.getCategories())
						+
						keywordExtractor.getCosineSimilarity(id,
								Crawler.getArticleText(s))*0.17
						));
				if (++count >= 125) break;
			}
			System.out.println(count);
			if (++count >= 1)
				break;
		}

		System.out.println(graph.getEntity("197001120001"));
	}

}
