import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * A test class for {@link Main} to see if the exception
 * is thrown and the processor quits when the input is wrong.
 */
public class MainTest {

  @Test
  public void testArgument() {
    String[] args = new String[]{"hello"};
    try {
      Main.main(args);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("The provided command-line argument is invalid", e.getMessage());
    }
  }
}