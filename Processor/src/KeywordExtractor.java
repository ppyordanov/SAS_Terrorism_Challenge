import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.TreeMap;

public class KeywordExtractor implements Iterable<ArrayList<String>>{
	private static final String[] MONTH_NAME = {"", "January", "February",
			"March", "April", "May", "June", "July", "August", "September",
			"October", "November", "December" };
	private static final String FILENAME = "gtd_tabs.csv";
	private static final String[] IMPORTANT_ATTR = {"eventid", "iyear", "imonth",
			"country_txt", "city", "location", "target1", "gname"};
	private static final String[] TEXT_ATTR = { "summary", "motive", "addnotes" };
	private static final String DELIMINATOR = "\t";
	private static final String SAMPLE_EVENT_ID = "197001020002";
	private TreeMap<String, Integer> attributeIndices;
	private TreeMap<String, ArrayList<String>> eventKeywords;
	private TreeMap<String, ArrayList<String>> relatedEvents;
	private BufferedReader scanner;

	public KeywordExtractor() {
		this(FILENAME);
	}
	
	public KeywordExtractor(String filename) {
		try {
			System.out.println(filename);
			scanner = new BufferedReader(new FileReader(filename));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.exit(1);
		}
		attributeIndices = new TreeMap<String, Integer>();
		eventKeywords = new TreeMap<String, ArrayList<String>>();
		relatedEvents = new TreeMap<String, ArrayList<String>>();
	}

	public void extractAllEvents() throws IOException {
		String[] header = scanner.readLine().split(DELIMINATOR);
		int index = 0;
		for (String h : header) {
			attributeIndices.put(h, index++);
		}
		for (String line = scanner.readLine(); line != null && !line.isEmpty(); line = scanner.readLine().trim()) {
			ArrayList<String> keywords = new ArrayList<String>();
			ArrayList<String> events = new ArrayList<String>();
			String eventId = extractSingleEvent(line, keywords, events);
//			System.out.println(eventId);
			eventKeywords.put(eventId, keywords);
			relatedEvents.put(eventId, events);
		}
		scanner.close();
	}

	private String extractSingleEvent(String line,
			ArrayList<String> keywords, ArrayList<String> relatedEvents) {
		String[] items = line.split(DELIMINATOR);
		for (String attr : IMPORTANT_ATTR) {
			Integer index = attributeIndices.get(attr);
			if (index == null) {
				System.err.println("Unexpected attribute " + attr);
				System.exit(1);
			}
			if (index >= items.length) System.err.println(Arrays.toString(items) + " line = " + line);
			if (!items[index].isEmpty()) {
				if (attr.equals("imonth")) {
					keywords.add(MONTH_NAME[Integer.parseInt(items[index])]);
				} else {
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
						if (Character.isUpperCase(word.charAt(0))) {
							if (!keyword.isEmpty()) {
								keyword += " ";
							}
							keyword += word;
						} else if (!keyword.isEmpty()) {
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

	@Override
	public Iterator<ArrayList<String>> iterator() {
		return eventKeywords.values().iterator();
	}
}
