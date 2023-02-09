package model;

import java.util.Map;

/**
 * An image processor model state interface, which only has
 * accessor methods. This cannot alter the image by itself alone.
 */
public interface ImageProcessorModelState {

  /**
   * This enum represents the types, or methods to alter the pixel color
   * into a greyscale.
   */
  enum GreyscaleType { Red, Green, Blue, Value, Intensity, Luma }

  /**
   * This enum represents the types of the flips. There are two
   * types, Horizontal and Vertical.
   */
  enum FlipType { Horizontal, Vertical }

  /**
   * This enum represents the types of filters. There are two types,
   * Blur and Sharpen.
   */
  enum FilteringType { Blur, Sharpen }

  /**
   * This enum represents the types of color transformations.
   * There are two types, Greyscale and Sepia.
   */
  enum ColorTransType { Greyscale, Sepia }

  /**
   * Retrieves the model's loaded images. Gets a copy of
   * the images instead of the actual images.
   *
   * @return the copy of an image saved in the map
   */
  Map<String, Image> getImage();

  /**
   * Retrieves a specific image by searching the given name
   * in the map, if it exists.
   *
   * @param imageTitle the target image to retrieve
   * @return image stored with the given name or null if it doesn't exist
   */
  Image getImage(String imageTitle);
}