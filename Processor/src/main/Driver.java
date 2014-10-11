package main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import org.json.JSONException;

import context.Crawler;
import extractor.KeywordExtractor;


public class Driver {

	public static void main(String[] args) throws IOException, JSONException{
		KeywordExtractor keywordExtractor = new KeywordExtractor("globalterrorismdb_0814dist.txt");
		keywordExtractor.extractAllEvents();
		for(ArrayList<String> keyword:keywordExtractor){
			String query = "";
			for(int j=1; j<keyword.size(); j++){
				if(keyword.get(j).isEmpty()) continue;
				query += " "+keyword.get(j);
			}
			query = query.substring(1);
			query = query.replaceAll(" ", "+");
			System.out.println("query>" + query);
			ArrayList<String> res = Crawler.getWikiResults(query);
			System.out.println("result"+Arrays.toString(res.toArray()));
		}
	}
	
}
