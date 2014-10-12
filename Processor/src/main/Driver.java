package main;

import db.InsertEvent;
import extractor.KeywordExtractor;
import graph.Entity;
import graph.Graph;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.json.JSONException;

import context.Wiki;

public class Driver{

	public static Graph graph;
	public static KeywordExtractor keywordExtractor;
	public static Wiki wiki;
	public static ConcurrentLinkedQueue<ArrayList<String>> queue;
	public static InsertEvent db;

	public static void main(String[] args) throws IOException, JSONException, InterruptedException {
		if (args.length != 1) {
			System.err.println("No thread count");
			System.exit(1);
		}
		wiki = new Wiki();
		keywordExtractor = new KeywordExtractor("2009data.txt");
		keywordExtractor.extractAllEvents();
		graph = new Graph();
		db = new InsertEvent();
		queue = new ConcurrentLinkedQueue<ArrayList<String>>();
		for (ArrayList<String> keyword : keywordExtractor) {
			queue.add(keyword);
		}
		LinkedList<Thread> t = new LinkedList<Thread>();
		for(int i=0; i<Integer.parseInt(args[0]); i++){
			Thread t1 = new Thread(new Process());
			t.add(t1);
			t1.start();
		}
		for(Thread t1: t){
			t1.join();
		}
		
		System.out.println("to insert now");
		System.out.println("size is"+Driver.graph.graph.size());
		for(Entry<String, Entity> e: Entity.map.entrySet()){
//			System.out.println(e.getKey());
			try {
				Driver.db.Insert(e.getValue());
			} catch(Exception ex) {
				ex.printStackTrace();
			}
		}
	}


}
