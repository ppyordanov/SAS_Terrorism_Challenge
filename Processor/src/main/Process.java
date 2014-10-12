package main;

import graph.Entity;
import graph.EntityPair;

import java.util.ArrayList;
import java.util.Set;

import context.Crawler;
import context.Stats;

public class Process implements Runnable{

	@Override
	public void run() {
//		int tc = 0;
		ArrayList<String> keyword;
		while ((keyword=Driver.queue.poll())!=null) {
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
			if(res ==null || res.isEmpty()) break;
			String pageTitle = res.get(0);
			Entity entity = Entity.getEntity(id, pageTitle,
					null, null, 0.0);
			try {
				ArrayList<String> categories = Crawler.getCategories(pageTitle);
				if (categories != null && categories.size() > 0)
					for (String category : categories)
						entity.addCategory(category);
				Driver.graph.addData(entity);
				Set<String> bestRelated = Crawler.getMostRelatedArticles(pageTitle);
				ArrayList<String> neighbours = new ArrayList<String>(bestRelated);
				Set<String> adj = Crawler.getAdjacentArticles(pageTitle);
				adj.removeAll(neighbours);
				neighbours.addAll(adj);
//				 String[] before = wiki
//				 .whatLinksHere(pageTitle, Wiki.MAIN_NAMESPACE);
//				 for (String s : before)
//				 neighbours.add(s);
//				String[] after = Driver.wiki.getLinksOnPage(pageTitle);
//				for (String s : after)
//					neighbours.add(s);
				int count = 0;
				for (String eventId : Driver.keywordExtractor.getEvents(id)) {
					entity.addSimilarPage(new EntityPair(Entity.getEntity(eventId, null, null, null, 0.0), 1.0));
				}
				for (String s : neighbours) {
					Entity e = Entity.getEntity(s, s, new ArrayList<Entity>(),
							new ArrayList<String>(), 0.0);
					ArrayList<String> al =  Crawler.getCategories(s);
					String[] c = new String[al.size()];
					for(int i=0; i<c.length; i++) c[i] = al.get(i);
					if (c != null && c.length > 0)
						for (String s1 : c)
							e.addCategory(s1);
					entity.addSimilarPage(new EntityPair(e, Stats
							.calculateJacard(entity.getCategories(),
									e.getCategories())
							+ Driver.keywordExtractor.getCosineSimilarity(id,
									Crawler.getArticleText(s)) * 0.1
					));
					if (++count >= 15) break;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
//			if(++tc > 5)break;
		}
	}

	
	
}
