package controller;

import model.IPixel;
import model.ImageProcessorModelState;

/**
 * The controller interface specifically built to support the GUI visualization.
 * Stores information necessary to build the GUI, therefore looks
 * slightly different from the original Controller interface.
 */
public interface Features {
  /**
   * Loads the image to the GUI system.
   *
   * @param imagePath a path to the image file
   * @throws IllegalArgumentException if the parameter is null
   */
  void load(String imagePath) throws IllegalArgumentException;

  /**
   * Saves the current image on the screen to an image file.
   * Supports different types files, from ppm to jpg, png, and bmp.
   *
   * @param imagePath a path to the image file
   * @param imageName a name of the image file
   * @throws IllegalArgumentException if either parameter is null
   */
  void save(String imagePath, String imageName) throws IllegalArgumentException;

  /**
   * Brightens the image that had been loaded in GUI.
   *
   * @param strength an integer that brightens an image by
   * @param mask a mask to apply on the preview if applicable
   */
  void brighten(int strength, IPixel[][] mask);

  /**
   * Converts a color of the image that had been loaded
   * in GUI into greyscale by visualizing given greyscale type.
   *
   * @param greyType the greyscale type
   * @param mask a mask to apply on the preview if applicable
   */
  void greyComponent(ImageProcessorModelState.GreyscaleType greyType, IPixel[][] mask);

  /**
   * Flips the image that had been loaded in GUI.
   *
   * @param flipType the flip type
   */
  void flip(ImageProcessorModelState.FlipType flipType);

  /**
   * Applies a filter on the image that had been loaded in GUI.
   *
   * @param filterType the type of the filter between blur and sharpen
   *                   @param mask a mask to apply on the preview if applicable
   */
  void filtering(ImageProcessorModelState.FilteringType filterType, IPixel[][] mask);

  /**
   * Executes color transformation on the image that had been loaded in GUI.
   *
   * @param colorTransType the type of the color transformation between greyscale and sepia
   *                       @param mask a mask to apply on the preview if applicable
   */
  void colorTransformation(ImageProcessorModelState.ColorTransType colorTransType, IPixel[][] mask);

  /**
   * Executes downscale on the image that had been loaded in GUI.
   */
  void downscale(double width, double height);
}