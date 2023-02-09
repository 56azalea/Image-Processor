package view;

import org.junit.Test;

import model.ImageProcessorModel;
import model.ImageProcessorModelImpl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Tests for {@link ImageProcessorTextView} to check if it initializes correctly
 * and if all the methods inside the {@link ImageProcessorTextView} is built accurately.
 */
public class ImageProcessorTextViewTest {

  @Test
  public void testView() {
    ImageProcessorModel model = new ImageProcessorModelImpl();
    ImageProcessorView view;
    Appendable builder = new StringBuilder();

    try {
      view = new ImageProcessorTextView(null);
    } catch (IllegalArgumentException e) {
      assertEquals("The model or appendable cannot be null", e.getMessage());
    }
    try {
      view = new ImageProcessorTextView(model, null);
    } catch (IllegalArgumentException e) {
      assertEquals("The model or appendable cannot be null", e.getMessage());
    }
    try {
      view = new ImageProcessorTextView(null, new StringBuilder());
    } catch (IllegalArgumentException e) {
      assertEquals("The model or appendable cannot be null", e.getMessage());
    }
    try {
      view = new ImageProcessorTextView(null, null);
    } catch (IllegalArgumentException e) {
      assertEquals("The model or appendable cannot be null", e.getMessage());
    }
    try {
      view = new ImageProcessorTextView(model, builder);
      view.renderMessage("test");
      assertEquals("test", builder.toString());
    } catch (IllegalStateException e) {
      fail(e.getMessage());
    }
  }

  @Test
  public void testRenderMessage() {
    ImageProcessorModel model = new ImageProcessorModelImpl();
    Appendable builder = new StringBuilder();
    ImageProcessorView view = new ImageProcessorTextView(model, builder);

    view.renderMessage("Testing render message method");
    assertEquals("Testing render message method", builder.toString());
  }
}