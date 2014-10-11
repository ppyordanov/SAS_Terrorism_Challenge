import java.io.IOException;
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
	public void test() throws IOException {
		extractor.extractAllEvents();
		System.out.println(Arrays.toString(extractor.getKeywords("197001000003").toArray()));
		System.out.println(Arrays.toString(extractor.getKeywords("197001080001").toArray()));
	}

}
