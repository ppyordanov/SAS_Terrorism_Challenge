package main;

import extractor.KeywordExtractor;
import graph.Entity;
import graph.EntityPair;
import graph.Graph;

import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONException;

import context.Crawler;
import context.Stats;
import context.Wiki;

public class Driver {

	private static Graph graph;
	private static KeywordExtractor keywordExtractor;
	private static Wiki wiki;

	public static void main(String[] args) throws IOException, JSONException {
		wiki = new Wiki();
		keywordExtractor = new KeywordExtractor("gtd_tabs.csv");
		keywordExtractor.extractAllEvents();
		graph = new Graph();
		for (ArrayList<String> keyword : keywordExtractor) {
			processEntry(keyword);
		}
		System.out.println(graph.getEntity("197001120001"));
	}

	public static void processEntry(ArrayList<String> keyword) throws IOException, JSONException {
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
		// String[] before = wiki
		// .whatLinksHere(pageTitle, Wiki.MAIN_NAMESPACE);
		// for (String s : before)
		// neighbours.add(s);
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
			entity.addSimilarPage(new EntityPair(e, Stats.calculateJacard(
					entity.getCategories(), e.getCategories())
					+ keywordExtractor.getCosineSimilarity(id,
							Crawler.getArticleText(s)) * 0.17));
		}
	}

}
