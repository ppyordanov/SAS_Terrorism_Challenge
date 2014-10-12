package main;

import extractor.KeywordExtractor;
import graph.Graph;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.LinkedBlockingQueue;

import org.json.JSONException;

import context.Wiki;

public class Driver{

	public static Graph graph;
	public static KeywordExtractor keywordExtractor;
	public static Wiki wiki;
	public static LinkedBlockingQueue<ArrayList<String>> queue;

	public static void main(String[] args) throws IOException, JSONException {
		wiki = new Wiki();
		keywordExtractor = new KeywordExtractor("globalterrorismdb_0814dist.txt");
		keywordExtractor.extractAllEvents();
		graph = new Graph();
		queue = new LinkedBlockingQueue<ArrayList<String>>();
		for (ArrayList<String> keyword : keywordExtractor) {
			queue.add(keyword);
		}
		for(int i=0; i<100; i++){
			new Thread(new Process()).start();;
		}
	}


}
