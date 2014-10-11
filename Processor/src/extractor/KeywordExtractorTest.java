package extractor;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map.Entry;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class KeywordExtractorTest {
	private static final String filename = "gtd_testdata.csv";
	private KeywordExtractor extractor;
	@Before
	public void setUp() throws Exception {
		extractor = new KeywordExtractor(filename);
		extractor.extractAllEvents();
	}

	@Test
	public void testKeywords() throws IOException {
		String[] expected1 = {"197001000003", "1970", "January", "Japan", "Fukouka", "U.S. Consulate"};
		String[] expected2 = {"197001080001", "1970", "January", "Italy", "Rome", "Flight 802 Boeing 707"};
		Assert.assertArrayEquals(expected1, extractor.getKeywords("197001000003").toArray());
		Assert.assertArrayEquals(expected2, extractor.getKeywords("197001080001").toArray());
		System.out.println(Arrays.toString(extractor.getKeywords("197003140004").toArray()));
	}
	
	@Test
	public void testRelatedEvents() throws IOException {
		String[] expected = {"197001020003", "197008240001"};
		Assert.assertArrayEquals(expected, extractor.getEvents("197001030001").toArray());
	}
	
	@Test
	public void testFrequencyCount() throws IOException {
		String[][] expected = {
				{"armstrong", "5"},
				{"august", "2"},
				{"bombing", "2"},
				{"building", "2"},
				{"floor", "2"},
				{"gang", "2"},
		};
		String[][] actual = {{"", ""}, {"", ""}, {"", ""}, {"", ""}, {"", ""}, {"", ""}};
		int rows = 0;
		for (Entry<String, Integer> e : extractor.getFreqCount("197001030001").entrySet()) {
			if (e.getValue() <= 1) continue;
			if (++rows > 6) break;
			actual[rows - 1][0] = e.getKey();
			actual[rows - 1][1] = e.getValue().toString();
		}
		for (int i = 0; i < expected.length; i++)
			Assert.assertArrayEquals(expected[i], actual[i]);
	}

}
