package main;

import graph.Entity;
import graph.EntityPair;

import java.util.ArrayList;

import context.Crawler;
import context.Stats;

public class Process implements Runnable{

	@Override
	public void run() {
		ArrayList<String> keyword;
		while ((keyword=Driver.queue.poll())!=null) {
//			ArrayList<String> keyword = queue.poll();
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
			try {
				String[] categories = Driver.wiki.getCategories(pageTitle);
				if (categories != null && categories.length > 0)
					for (String category : categories)
						entity.addCategory(category);
				Driver.graph.addData(entity);
				ArrayList<String> neighbours = new ArrayList<String>();
				// String[] before = wiki
				// .whatLinksHere(pageTitle, Wiki.MAIN_NAMESPACE);
				// for (String s : before)
				// neighbours.add(s);
				String[] after = Driver.wiki.getLinksOnPage(pageTitle);
				for (String s : after)
					neighbours.add(s);
				for (String s : neighbours) {
					Entity e = Entity.getEntity(s, s, new ArrayList<Entity>(),
							new ArrayList<String>(), 0.0);
					String[] c = Driver.wiki.getCategories(s);
					if (c != null && c.length > 0)
						for (String s1 : c)
							e.addCategory(s1);
					// Weight = Jackard + cos similarity
					entity.addSimilarPage(new EntityPair(e, Stats
							.calculateJacard(entity.getCategories(),
									e.getCategories())
							+ Driver.keywordExtractor.getCosineSimilarity(id,
									Crawler.getArticleText(s)) * 0.17));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println("Processed "+pageTitle);
		}
	}

	
	
}
