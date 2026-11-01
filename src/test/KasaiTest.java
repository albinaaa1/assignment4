import main.advanced.KasaiLCP;
import main.advanced.SimpleSuffixArray;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class KasaiTest {
    @Test
    public void testKasai() {
        String text = "banana";
        int[] sa = SimpleSuffixArray.build(text);
        int[] lcp = KasaiLCP.buildLCP(text, sa);

        assertNotNull(lcp);
        assertEquals(text.length(), lcp.length);
        assertEquals(3, lcp[2]);
    }
}