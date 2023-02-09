package model;

import java.util.Arrays;
import java.util.Objects;

/**
 * The class that represents an image, which is
 * composed of {@link Pixel}. The number and organization
 * of the pixels depend on the image's width and height.
 * Implements the {@link Image} interface.
 */
public final class ImageImpl implements Image {
  private final int width;
  private final int height;
  private final int maxValue;
  private final IPixel[][] pixels;

  /**
   * Constructs an image using the given information.
   * The unit of width and height is in pixels. The size of the
   * 2D array of pixels must resort on the width and height of the image.
   *
   * @param width    the image width
   * @param height   the image height
   * @param maxValue the maximum RBG value
   * @param pixels   the pixel components in a 2D array
   * @throws IllegalArgumentException if any parameter is negative or null, or if not compilable
   */
  public ImageImpl(int width, int height, int maxValue, IPixel[][] pixels)
          throws IllegalArgumentException {
    // integers 0
    if (width <= 0 || height <= 0 || maxValue < 0) {
      throw new IllegalArgumentException("Only positive values allowed.");
    }
    // null pixels
    if (pixels == null) {
      throw new IllegalArgumentException("Pixels must not be null.");
    }
    // pixels & width-height not matching
    if (pixels[0].length != width || pixels.length != height) {
      throw new IllegalArgumentException("The given pixels don't match the width or height.");
    }
    // checks on individual pixels whether any exceeds the maxValue
    for (IPixel[] pixelHeight : pixels) {
      for (IPixel pixelRow : pixelHeight) {
        if (pixelRow.overMaxValue(maxValue)) {
          throw new IllegalArgumentException(
                  "RGB channel values of a pixel cannot exceed the maximum value.");
        }
      }
    }
    this.width = width;
    this.height = height;
    this.maxValue = maxValue;
    this.pixels = pixels;
  }

  @Override
  public int getWidth() {
    int width = this.width;
    return width;
  }

  @Override
  public int getHeight() {
    int height = this.height;
    return height;
  }

  @Override
  public int getMaxValue() {
    int maxValue = this.maxValue;
    return maxValue;
  }

  @Override
  public IPixel[][] getPixels() {
    Pixel[][] arrayPixel = new Pixel[this.height][this.width];
    for (int i = 0; i < this.height; i++) {
      for (int j = 0; j < this.width; j++) {
        IPixel pixel = this.pixels[i][j];
        arrayPixel[i][j] = new Pixel(pixel.getRed(), pixel.getGreen(), pixel.getBlue());
      }
    }
    return arrayPixel;
  }

  @Override
  public IPixel getPixelAt(int row, int col) throws IllegalArgumentException {
    if (row < 0 || col < 0 || row > this.height || col > this.width) {
      throw new IllegalArgumentException("Position out of bound.");
    }
    return this.pixels[row][col];
  }

  @Override
  public String toByteRead() {
    StringBuilder builder = new StringBuilder("P3 \n");
    builder.append(this.width).append(" ");
    builder.append(this.height).append("\n");
    builder.append(this.maxValue).append("\n\n");

    for (int i = 0; i < this.height; i++) {
      for (int j = 0; j < this.width; j++) {
        if ((i == this.height - 1) && (j == this.width - 1)) {
          builder.append(this.pixels[i][j].toByteRead());
        } else {
          builder.append(this.pixels[i][j].toByteRead()).append("\n");
        }
      }
    }
    return builder.toString();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof ImageImpl)) {
      return false;
    }
    ImageImpl image = (ImageImpl) o;
    return (this.width == image.width
            && this.height == image.height
            && this.maxValue == image.maxValue
            && (Arrays.deepEquals(this.pixels, image.pixels)));
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.width, this.height, this.maxValue, this.pixels);
  }
}