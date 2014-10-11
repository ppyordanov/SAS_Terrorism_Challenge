import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;


public class KeywordExtractorTest {
	private static final String filename = "gtd_testdata.csv";
	private KeywordExtractor extractor;
	@Before
	public void setUp() throws Exception {
		extractor = new KeywordExtractor(filename);
	}

	@Test
	public void test() {
		extractor.extractAllEvents();
		System.out.println(Arrays.toString(extractor.getKeywords("197001010002").toArray()));
	}

}
