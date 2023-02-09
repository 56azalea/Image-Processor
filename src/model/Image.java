package model;

/**
 * The interface of an image model; this includes the methods
 * to alter the given image, such as by brightening, darkening,
 * and vertically & horizontally flipping.
 */
public interface Image {

  /**
   * Extracts the width of this image. Returns a copy
   * of the width value instead of an actual width field.
   *
   * @return width of the image
   */
  int getWidth();

  /**
   * Extracts the height of this image. Returns a copy
   * of the height value instead of an actual height field.
   *
   * @return height of image
   */
  int getHeight();

  /**
   * Extracts the maximum value of this image's RGB channel.
   * Returns a copy of the maximum value instead of an actual maxValue field.
   *
   * @return max channel value
   */
  int getMaxValue();

  /**
   * Extracts pixels that make up this image. Returns a copy of
   * the 2D array of pixels instead of an actual pixel field.
   *
   * @return 2D array of pixels
   */
  IPixel[][] getPixels();

  /**
   * Overrides the equals method so that two images are same
   * if they have same width, height, maximum RGB value, and pixels.
   *
   * @param o the object to compare to
   * @return whether the given object matches this object
   */
  @Override
  boolean equals(Object o);

  /**
   * Overrides hashCode method as the equals method has been overridden.
   *
   * @return the hashcode value
   */
  @Override
  int hashCode();

  /**
   * Extracts the pixel at specified location.
   *
   * @param row the row position
   * @param col the column position
   * @return the pixel at the given position
   * @throws IllegalArgumentException when the given position is out of bound
   */
  IPixel getPixelAt(int row, int col) throws IllegalArgumentException;

  /**
   * Returns the information about this image into a string,
   * which would be used later through the byte reader when
   * saving the image.
   *
   * @return the information of the image in string
   */
  String toByteRead();
}