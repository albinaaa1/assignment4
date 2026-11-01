

import main.dp.EditDistance;
import main.dp.LCS;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DPTest {
    @Test
    public void testLCS() {
        assertEquals(2, LCS.findLCSLength("ABC", "ACD"));
        assertEquals(0, LCS.findLCSLength("", "ABC"));
    }

    @Test
    public void testEditDistance() {
        assertEquals(3, EditDistance.calculate("kitten", "sitting"));
        assertEquals(0, EditDistance.calculate("test", "test"));
    }
}