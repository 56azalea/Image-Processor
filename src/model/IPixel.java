package model;

/**
 * An interface that represents a pixel. A class implementing
 * this interface must be able to handle the methods given
 * as these are required for any kind of pixels.
 */
public interface  IPixel {

  /**
   * Extracts red channel value of this pixel. Prevents any
   * invasions of access by returning a local integer equivalent
   * to the red channel, instead of the real red channel field.
   *
   * @return red channel value
   */
  int getRed();

  /**
   * Extracts green channel value of this pixel. Prevents any
   * invasions of access by returning a local integer equivalent
   * to the green channel, instead of the real green channel field.
   *
   * @return green channel value.
   */
  int getGreen();

  /**
   * Extracts blue channel value of this pixel. Prevents any
   * invasions of access by returning a local integer equivalent
   * to the blue channel, instead of the real blue channel field.
   *
   * @return blue channel value.
   */
  int getBlue();

  /**
   * Applies a change in the brightness of the pixel. Takes in
   * an integer that determines the strength of the brightness.
   * No further change in the channel value will occur if the pixel
   * has already reached the end. Returns a new pixel instead of modifying the actual pixel.
   *
   * @param strength the amount to change each RGB value by
   * @param maxValue the maximum value of the RGB channel depending on the desired bit
   * @return the resulting pixel
   */
  IPixel brighten(int strength, int maxValue);

  /**
   * Applies the greyscale representation of the pixel. There are
   * various ways to produce greyscale color, each calculating colors
   * differently. Returns a new pixel instead of modifying the actual pixel.
   *
   * @param greyType the type of greyscale component to apply
   * @return the resulting pixel
   */
  IPixel greyscale(ImageProcessorModel.GreyscaleType greyType);

  /**
   * Converts a color image into a greyscale image using
   * the luma-component or into a sepia-toned image.
   *
   * @param colorType the type of the color transformation
   * @return the resulting pixel
   */
  IPixel colorTrans(ImageProcessorModelState.ColorTransType colorType)
          throws IllegalArgumentException;

  /**
   * Determines if any of the RGB values are over the maximum value.
   * This exists separately since the maximum value depends on the bit (1 or 255).
   *
   * @param maxValue of the image
   * @return whether any RBG parameter is over the max value
   */
  boolean overMaxValue(int maxValue);

  /**
   * Overrides the equals method to return true if
   * two pixels have the same RGB channel values.
   *
   * @param o the object
   * @return whether the two pixels are the same
   */
  @Override
  boolean equals(Object o);

  /**
   * Overrides hashCode method because the equals method has
   * been overridden.
   *
   * @return the hashcode based on RGB values
   */
  @Override
  int hashCode();

  /**
   * Gets the values of this pixel as an array.
   *
   * @return the array representation of this pixel's RGB values
   */
  int[] getValues();

  /**
   * Returns the information about the pixel into string.
   * This is to use when reading the pixels using a byte reader.
   *
   * @return the information of pixel in string
   */
  String toByteRead();
}