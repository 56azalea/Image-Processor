package view;

import org.junit.Test;

import java.awt.Image;

import static org.junit.Assert.assertEquals;

import controller.Features;
import controller.GUIController;
import model.MaskProcessorModel;
import model.MaskProcessorModelImpl;

/**
 * Tests for {@link view.ImageProcessorGUIView}.
 * Uses {@link GUIViewMock} to test this because
 * GUI is difficult to test in nature.
 */
public class GUIViewTest {

  @Test
  public void testAll() {
    Appendable inputLog = new StringBuilder();
    MaskProcessorModel model = new MaskProcessorModelImpl();
    ImageProcessorGUIView view = new GUIViewMock(inputLog);

    view.renderMessage("Testing render message");
    assertEquals("Rendering Testing render message\n", inputLog.toString());

    Features controller = new GUIController(model, view);
    view.addFeatures(controller);
    // Since the controller calls on addFeature in its constructor
    assertEquals("Rendering Testing render message\n" +
            "Adding features\n" +
            "Adding features\n", inputLog.toString());

    Image image = null;
    view.refresh(image, true);
    assertEquals("Rendering Testing render message\n" +
            "Adding features\n" +
            "Adding features\n" +
            "Refreshing the image displayed\n", inputLog.toString());
  }
}