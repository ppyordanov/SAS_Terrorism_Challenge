package extractor;

import graph.Entity;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.TreeSet;

import context.Crawler;

public class KeywordExtractor implements Iterable<ArrayList<String>> {
	private static final String[] MONTH_NAME = { "", "January", "February",
			"March", "April", "May", "June", "July", "August", "September",
			"October", "November", "December" };
	private static final String FILENAME = "gtd_tabs.csv";
	private static final String[] IMPORTANT_ATTR = { "eventid", "iyear",
			"imonth", "country_txt", "city", "location", "target1", "gname" };
	private static String[] STOPWORDS = {"", "a", "about", "above", "above",
			"across", "after", "afterwards", "again", "against", "all",
			"almost", "alone", "along", "already", "also", "although",
			"always", "am", "among", "amongst", "amoungst", "amount", "an",
			"and", "another", "any", "anyhow", "anyone", "anything", "anyway",
			"anywhere", "are", "around", "as", "at", "back", "be", "became",
			"because", "become", "becomes", "becoming", "been", "before",
			"beforehand", "behind", "being", "below", "beside", "besides",
			"between", "beyond", "bill", "both", "bottom", "but", "by", "call",
			"can", "cannot", "cant", "co", "con", "could", "couldnt", "cry",
			"de", "describe", "detail", "do", "done", "down", "due", "during",
			"each", "eg", "eight", "either", "eleven", "else", "elsewhere",
			"empty", "enough", "etc", "even", "ever", "every", "everyone",
			"everything", "everywhere", "except", "few", "fifteen", "fify",
			"fill", "find", "fire", "first", "five", "for", "former",
			"formerly", "forty", "found", "four", "from", "front", "full",
			"further", "get", "give", "go", "had", "has", "hasnt", "have",
			"he", "hence", "her", "here", "hereafter", "hereby", "herein",
			"hereupon", "hers", "herself", "him", "himself", "his", "how",
			"however", "hundred", "ie", "if", "in", "inc", "indeed",
			"interest", "into", "is", "it", "its", "itself", "keep", "last",
			"latter", "latterly", "least", "less", "ltd", "made", "many",
			"may", "me", "meanwhile", "might", "mill", "mine", "more",
			"moreover", "most", "mostly", "move", "much", "must", "my",
			"myself", "name", "namely", "neither", "never", "nevertheless",
			"next", "nine", "no", "nobody", "none", "noone", "nor", "not",
			"nothing", "now", "nowhere", "of", "off", "often", "on", "once",
			"one", "only", "onto", "or", "other", "others", "otherwise", "our",
			"ours", "ourselves", "out", "over", "own", "part", "per",
			"perhaps", "please", "put", "rather", "re", "same", "see", "seem",
			"seemed", "seeming", "seems", "serious", "several", "she",
			"should", "show", "side", "since", "sincere", "six", "sixty", "so",
			"some", "somehow", "someone", "something", "sometime", "sometimes",
			"somewhere", "still", "such", "system", "take", "ten", "than",
			"that", "the", "their", "them", "themselves", "then", "thence",
			"there", "thereafter", "thereby", "therefore", "therein",
			"thereupon", "these", "they", "thickv", "thin", "third", "this",
			"those", "though", "three", "through", "throughout", "thru",
			"thus", "to", "together", "too", "top", "toward", "towards",
			"twelve", "twenty", "two", "un", "under", "unknown", "until", "up", "upon",
			"us", "very", "via", "was", "we", "well", "were", "what",
			"whatever", "when", "whence", "whenever", "where", "whereafter",
			"whereas", "whereby", "wherein", "whereupon", "wherever",
			"whether", "which", "while", "whither", "who", "whoever", "whole",
			"whom", "whose", "why", "will", "with", "within", "without",
			"would", "yet", "you", "your", "yours", "yourself", "yourselves",
			"the" };
	private static final TreeSet<String> STOPWORDS_SET = new TreeSet<String>(
			Arrays.asList(STOPWORDS));
	private static final String[] TEXT_ATTR = { "summary", "motive", "addnotes" };
	private static final String DELIMINATOR = "\t";
	private static final String SAMPLE_EVENT_ID = "197001020002";

	private TreeMap<String, Integer> attributeIndices;
	private TreeMap<String, ArrayList<String>> eventKeywords;
	private TreeMap<String, ArrayList<String>> relatedEvents;
	private TreeMap<String, TreeMap<String, Integer>> freqCount;
	private BufferedReader scanner;

	public KeywordExtractor() {
		this(FILENAME);
	}

	public KeywordExtractor(String filename) {
		try {
			scanner = new BufferedReader(new FileReader(filename));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.exit(1);
		}
		attributeIndices = new TreeMap<String, Integer>();
		eventKeywords = new TreeMap<String, ArrayList<String>>();
		relatedEvents = new TreeMap<String, ArrayList<String>>();
		freqCount = new TreeMap<String, TreeMap<String, Integer>>();
	}

	public void extractAllEvents() throws IOException {
		String[] header = scanner.readLine().split(DELIMINATOR);
		int index = 0;
		for (String h : header) {
			attributeIndices.put(h, index++);
		}
		for (String line = scanner.readLine(); line != null && !line.isEmpty(); line = scanner
				.readLine().trim()) {
			ArrayList<String> keywords = new ArrayList<String>();
			ArrayList<String> events = new ArrayList<String>();
			TreeMap<String, Integer> freq = new TreeMap<String, Integer>();
			String eventId = extractSingleEvent(line, keywords, events, freq);
			// System.out.println(eventId);
			eventKeywords.put(eventId, keywords);
			relatedEvents.put(eventId, events);
			freqCount.put(eventId, freq);
			Entity e = Entity.getEntity(keywords.get(0), "", null, null, 0.0);
		}
		scanner.close();
	}

	public static boolean isStopWord(String word) {
		return STOPWORDS_SET.contains(word.toLowerCase());
	}
	
	public static void getWordFrequency(String line, TreeMap<String, Integer> freq) {
		String[] items = line.split("[\\p{P} \\t\\n\\r]");
		for (int i = 0; i < items.length; ++i) items[i] = items[i].toLowerCase();
		for (String item : items) {
			if (isStopWord(item)) continue;
			if (!item.matches("[a-zA-Z]{3,}")) continue;
			Integer count = freq.get(item);
			if (count == null) {
				count = 0;
			}
			count++;
			freq.put(item, count);
		}
	}

	private String extractSingleEvent(String line, ArrayList<String> keywords,
			ArrayList<String> relatedEvents, TreeMap<String, Integer> freq) {
		getWordFrequency(line, freq);
		String[] items = line.split(DELIMINATOR);
		for (String attr : IMPORTANT_ATTR) {
			Integer index = attributeIndices.get(attr);
			if (index == null) {
				System.err.println("Unexpected attribute " + attr);
				System.exit(1);
			}
			if (index >= items.length)
				System.err.println(Arrays.toString(items) + " line = " + line);
			if (!items[index].isEmpty()) {
				if (attr.equals("imonth")) {
					keywords.add(MONTH_NAME[Integer.parseInt(items[index])]);
				} else if (!isStopWord(items[index])) {
					keywords.add(items[index]);
				}
			}
		}
		for (String attr : TEXT_ATTR) {
			int index = attributeIndices.get(attr);
			if (!items[index].isEmpty()) {
				String[] parts = items[index].split("[^a-zA-Z0-9 ]");
				for (String part : parts) {
					String[] words = part.split(" ");
					String keyword = "";
					for (String word : words) {
						if (word.isEmpty()) {
							continue;
						}
						if (isStopWord(word)) {
							if (!keyword.isEmpty() && keyword.contains(" ")) {
								keywords.add(keyword);
								keyword = "";
							}
							continue;
						}
						if (Character.isUpperCase(word.charAt(0))) {
							if (!keyword.isEmpty()) {
								keyword += " ";
							}
							keyword += word;
						} else if (!keyword.isEmpty() && keyword.contains(" ")) {
							keywords.add(keyword);
							keyword = "";
						}
						if (word.length() == SAMPLE_EVENT_ID.length()
								&& word.matches("[0-9]+")) {
							relatedEvents.add(word);
						}
					}

				}
			}
		}
		return items[0];
	}

	public ArrayList<String> getKeywords(String eventId) {
		return eventKeywords.get(eventId);
	}

	public ArrayList<String> getEvents(String eventId) {
		return relatedEvents.get(eventId);
	}

	public TreeMap<String, Integer> getFreqCount(String eventId) {
		return freqCount.get(eventId);
	}

	@Override
	public Iterator<ArrayList<String>> iterator() {
		return eventKeywords.values().iterator();
	}
	
	public double getCosineSimilarity(String eventId, String text) {
		Entity entity = Entity.getEntity(eventId, null, null, null, 0.0);
		String thisText = "";
		
		try {
			thisText = Crawler.getArticleText(entity.getPage());
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		TreeMap<String, Integer> thisFreqCount = new TreeMap<String, Integer>();
		TreeMap<String, Integer> other = new TreeMap<String, Integer>();
		getWordFrequency(thisText, thisFreqCount);
		getWordFrequency(text, other);
		double sim = 0;
		double thisLel = 0;
		double otherLel = 0;
		for (Entry<String, Integer> e : thisFreqCount.entrySet()) {
			if (e.getValue() <= 0) continue;
			thisLel += e.getValue() * e.getValue();
			Integer otherFreq = other.get(e.getKey());
			if (otherFreq == null || otherFreq <= 0) continue;
			otherLel += otherFreq * otherFreq;
			sim += otherFreq * e.getValue();
		}
		thisLel = Math.sqrt(thisLel);
		otherLel = Math.sqrt(otherLel);
		return (thisLel == 0 || otherLel == 0) ? 0 : sim / (thisLel * otherLel);
	}
}
