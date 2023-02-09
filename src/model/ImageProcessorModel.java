package model;

/**
 * An interface that operates as an image processor, which executes
 * methods on the image that has been uploaded. Extends {@link ImageProcessorModelState}
 * which doesn't execute any image-altering methods by itself.
 */
public interface ImageProcessorModel extends ImageProcessorModelState {

  /**
   * Adds an image to the model's storage.
   *
   * @param imageTitle title/name of the image to save
   * @param image      the image to save
   */
  void addImage(String imageTitle, Image image);

  /**
   * Flips an image either horizontally or vertically.
   *
   * @param imageTitle the target image
   * @param flipType   whether the target must be flipped horizontally or vertically
   * @param dest       destination path of the file
   * @throws IllegalArgumentException if any parameter is null or the image cannot be found
   */
  void flip(String imageTitle, FlipType flipType, String dest) throws IllegalArgumentException;

  /**
   * Alters the brightness of an image.
   *
   * @param imageTitle the target image
   * @param strength   the amount to change each RGB value by
   * @param dest       destination path of the file
   * @throws IllegalArgumentException if any parameter is null or the image cannot be found
   */
  void brighten(String imageTitle, int strength, String dest) throws IllegalArgumentException;

  /**
   * Converts a color image to greyscale by visualizing
   * individual components, or value/intensity/luma.
   *
   * @param imageTitle the target image
   * @param greyType   the method type of the greyscale
   * @param dest       destination path of the file
   * @throws IllegalArgumentException if any parameter is null or the image cannot be found
   */
  void multipleGreyscale(String imageTitle, GreyscaleType greyType, String dest)
          throws IllegalArgumentException;

  /**
   * Applies the filter on the image using kernel.
   * The filters include blurring and sharpening.
   *
   * @param imageTitle the target image
   * @param filterType the type of the filter
   * @param dest       the name of the new image
   * @throws IllegalArgumentException if any parameter is null or the image cannot be found
   */
  void filtering(String imageTitle, FilteringType filterType, String dest)
          throws IllegalArgumentException;

  /**
   * Color transformations on individual pixels.
   * There are two operations: greyscale and sepia.
   *
   * @param imageTitle the target image
   * @param colorType  the type of the color transformation
   * @param dest       the name of the new image
   * @throws IllegalArgumentException if any parameter is null or the image cannot be found
   */
  void colorTransformation(String imageTitle, ColorTransType colorType, String dest)
          throws IllegalArgumentException;

  /**
   * Applies downscaling on the image.
   *
   * @param imageTitle the target image
   * @param width      the scale factor to downsize the width of the image by (in decimal
   *                   percentage) - 0.5 results in the image with 50% of the width and
   *                   1.0 results in the same width
   * @param height     the scale factor to downsize the height of the image by (in decimal
   *                   percentage) - 0.5 results in the image with 50% of the height and
   *                   1.0 results in the same height
   * @param dest       the name of the new image
   * @throws IllegalArgumentException if any parameter is null or the image cannot be found
   */
  void downscale(String imageTitle, double width, double height, String dest)
          throws IllegalArgumentException;
}