package main;

import extractor.KeywordExtractor;
import graph.Graph;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.json.JSONException;

import context.Wiki;

public class Driver{

	public static Graph graph;
	public static KeywordExtractor keywordExtractor;
	public static Wiki wiki;
	public static ConcurrentLinkedQueue<ArrayList<String>> queue;

	public static void main(String[] args) throws IOException, JSONException {
		wiki = new Wiki();
		keywordExtractor = new KeywordExtractor("gtd_tabs.csv");
		keywordExtractor.extractAllEvents();
		graph = new Graph();
		queue = new ConcurrentLinkedQueue<ArrayList<String>>();
		for (ArrayList<String> keyword : keywordExtractor) {
			queue.add(keyword);
		}
		for(int i=0; i<1; i++){
			new Thread(new Process()).start();;
		}
	}


}
