package extractor;
import java.io.IOException;
import java.util.Arrays;

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
		String[] expected1 = {"197001000003", "1970", "January", "Japan", "Fukouka", "U.S. Consulate", "Unknown"};
		String[] expected2 = {"197001080001", "1970", "January", "Italy", "Rome", "Flight 802 Boeing 707", "Unknown"};
		Assert.assertArrayEquals(expected1, extractor.getKeywords("197001000003").toArray());
		Assert.assertArrayEquals(expected2, extractor.getKeywords("197001080001").toArray());
	}
	
	@Test
	public void testRelatedEvents() throws IOException {
		String[] expected = {"197001020003", "197008240001"};
		Assert.assertArrayEquals(expected, extractor.getEvents("197001030001").toArray());
	}

}
