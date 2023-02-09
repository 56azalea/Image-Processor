package view;

import java.awt.Image;

import controller.Features;

/**
 * The interface to visualize the image processor in the GUI system.
 * The class that implements this view must be able
 * to show a GUI view for the image processor.
 */
public interface ImageProcessorGUIView extends ImageProcessorView {

  /**
   * Refreshes the screen anytime it needs to be doing so.
   * Called when the something on the screen is updated,
   * and therefore it must be redrawn.
   *
   * @param image the updated image
   * @param original whether the original image must be refreshed or the preview must be
   */
  void refresh(Image image, Boolean original);

  /**
   * Accepts an object of the features interface.
   * Called by the controller in its constructor.
   *
   * @param feature the feature object to accept
   */
  void addFeatures(Features feature);
}