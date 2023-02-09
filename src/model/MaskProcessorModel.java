package model;

/**
 * This model adds a mask feature to every image operation.
 * Extends {@link ImageProcessorModel}. This is to prevent editing
 * every operation method we have made so far, since that would cause
 * a whole chaos in every part. This is similar to how TracingTurtleModel
 * extends TurtleModel in Command Design Pattern lecture.
 */
public interface MaskProcessorModel extends ImageProcessorModel {

  /**
   * Alters the brightness of an image.
   * Applies only where the mask is applied.
   *
   * @param imageTitle the target image
   * @param strength   the amount to change each RGB value by
   * @param mask       the mask image
   * @param dest       destination path of the file
   * @throws IllegalArgumentException if any parameter is null or the image cannot be found
   */
  void brighten(String imageTitle, int strength, IPixel[][] mask, String dest)
          throws IllegalArgumentException;

  /**
   * Converts a color image to greyscale by visualizing
   * individual components, or value/intensity/luma.
   * Applies only where the mask is applied.
   *
   * @param imageTitle the target image
   * @param greyType   the method type of the greyscale
   * @param mask       the mask image
   * @param dest       destination path of the file
   * @throws IllegalArgumentException if any parameter is null or the image cannot be found
   */
  void multipleGreyscale(String imageTitle, GreyscaleType greyType, IPixel[][] mask, String dest)
          throws IllegalArgumentException;

  /**
   * Applies the filter on the image using kernel.
   * The filters include blurring and sharpening.
   * Applies only where the mask is applied.
   *
   * @param imageTitle the target image
   * @param filterType the type of the filter
   * @param mask       the mask image
   * @param dest       the name of the new image
   * @throws IllegalArgumentException if any parameter is null or the image cannot be found
   */
  void filtering(String imageTitle, FilteringType filterType, IPixel[][] mask, String dest)
          throws IllegalArgumentException;

  /**
   * Color transformations on individual pixels.
   * There are two operations: greyscale and sepia.
   * Applies only where the mask is applied.
   *
   * @param imageTitle the target image
   * @param colorType  the type of the color transformation
   * @param mask       the mask image
   * @param dest       the name of the new image
   * @throws IllegalArgumentException if any parameter is null or the image cannot be found
   */
  void colorTransformation(String imageTitle, ColorTransType colorType, IPixel[][] mask, String dest)
          throws IllegalArgumentException;
}