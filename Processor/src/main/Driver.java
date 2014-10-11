package main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import org.json.JSONException;

import context.Crawler;
import context.Wiki;
import extractor.KeywordExtractor;
import graph.Entity;
import graph.Graph;


public class Driver {
	
	private static Graph graph;
	
	public static void test() throws IOException{
		Wiki wiki = new Wiki();
		System.out.println(Arrays.toString(wiki.getLinksOnPage("Sterling Hall bombing")));
	}

	public static void main(String[] args) throws IOException, JSONException{
		test();
		System.exit(1);
		
		KeywordExtractor keywordExtractor = new KeywordExtractor("globalterrorismdb_0814dist.txt");
		keywordExtractor.extractAllEvents();
		graph = new Graph();
		int c = 0;
		for(ArrayList<String> keyword:keywordExtractor){
			String query = "";
			for(int j=1; j<keyword.size(); j++){
				if(keyword.get(j).isEmpty()) continue;
				query += " "+keyword.get(j);
			}
			query = query.substring(1);
			query = query.replaceAll(" ", "+");
			ArrayList<String> res = Crawler.getWikiResults(query);
			String id = keyword.get(0);
			if(res.size()==0) continue;
			Entity e = new Entity(id, res.get(0), new ArrayList<Entity>(), keyword, 0.0);
			graph.addData(e);
			System.out.println(c);
			if(++c>=20) break;
		}
		
		System.out.println(graph.getEntity("197001030001"));
	}
	
}
