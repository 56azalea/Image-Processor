package view;

import java.awt.Image;
import java.io.IOException;

import controller.Features;

/**
 * A mock class built to test {@link ImageProcessorGUIView}.
 * Adapted from Controllers and Mocks lecture on Canvas.
 */
public class GUIViewMock implements ImageProcessorGUIView {

  private Appendable log;

  /**
   * A constructor for GUIViewMock class.
   *
   * @param log the appendable log to test if the methods are working
   */
  public GUIViewMock(Appendable log) {
    this.log = log;
  }

  @Override
  public void renderMessage(String message) {
    try {
      this.log.append("Rendering " + message + "\n");
    } catch (IOException e) {
      throw new IllegalArgumentException("IOException occurred; renderMessage failed");
    }
  }

  @Override
  public void addFeatures(Features feature) {
    try {
      this.log.append("Adding features\n");
    } catch (IOException e) {
      throw new IllegalArgumentException("IOException occurred; addFeatures failed");
    }
  }

  @Override
  public void refresh(Image image, Boolean original) {
    try {
      this.log.append("Refreshing the image displayed\n");
    } catch (IOException e) {
      throw new IllegalArgumentException("IOException occurred; refresh failed");
    }
  }
}