package model;

import java.util.Objects;

/**
 * Represents a single pixel in a image. It's consisting of
 * three fields: red, green, and blue (excluded alpha since it's
 * unnecessary for now). All of these fields are integers in a range of
 * 0 to 255.
 */
public final class Pixel implements IPixel {
  private final int red;
  private final int green;
  private final int blue;

  /**
   * Constructing this pixel with supplied the RGB values.
   * Would throw an exception if any of the parameters are invalid
   * formats of RGB value.
   *
   * @param red   Red RGB value
   * @param green Green RGB value
   * @param blue  Blue RGB value
   * @throws IllegalArgumentException if any parameter is negative
   */
  public Pixel(int red, int green, int blue) throws IllegalArgumentException {
    if (red < 0 || green < 0 || blue < 0) {
      throw new IllegalArgumentException("RGB values must be positive.");
    }
    this.red = red;
    this.green = green;
    this.blue = blue;
  }

  @Override
  public int getRed() {
    int red = this.red;
    return red;
  }

  @Override
  public int getGreen() {
    int green = this.green;
    return green;
  }

  @Override
  public int getBlue() {
    int blue = this.blue;
    return blue;
  }

  @Override
  public Pixel brighten(int strength, int maxValue) throws IllegalArgumentException {
    if (maxValue < 1) {
      throw new IllegalArgumentException("MaxValue should be greater 1");
    }
    int newRed;
    int newGreen;
    int newBlue;

    // brightening
    if (strength > 0) {
      newRed = Math.min(this.red + strength, maxValue);
      newGreen = Math.min(this.green + strength, maxValue);
      newBlue = Math.min(this.blue + strength, maxValue);
    }
    // darkening
    else if (strength < 0) {
      newRed = Math.max(this.red + strength, 0);
      newGreen = Math.max(this.green + strength, 0);
      newBlue = Math.max(this.blue + strength, 0);
    }
    // no change
    else {
      newRed = this.red;
      newGreen = this.green;
      newBlue = this.blue;
    }
    return new Pixel(newRed, newGreen, newBlue);
  }

  @Override
  public Pixel greyscale(ImageProcessorModel.GreyscaleType greyType)
          throws IllegalArgumentException {
    if (greyType == null) {
      throw new IllegalArgumentException("Greyscale type must not be null");
    }
    Pixel pixel;

    switch (greyType) {
      case Red:
        pixel = new Pixel(this.red, this.red, this.red);
        break;
      case Green:
        pixel = new Pixel(this.green, this.green, this.green);
        break;
      case Blue:
        pixel = new Pixel(this.blue, this.blue, this.blue);
        break;
      case Value:
        int maximum = Math.max(Math.max(this.red, this.green), this.blue);
        pixel = new Pixel(maximum, maximum, maximum);
        break;
      case Intensity:
        int avg = (this.red + this.green + this.blue) / 3;
        pixel = new Pixel(avg, avg, avg);
        break;
      case Luma:
        int weightedSum = (int) ((this.red * 0.2126) + (this.green * 0.7152)
                + (this.blue * 0.0722));
        pixel = new Pixel(weightedSum, weightedSum, weightedSum);
        break;
      default:
        pixel = new Pixel(this.red, this.green, this.blue);
        break;
    }
    return pixel;
  }

  @Override
  public Pixel colorTrans(ImageProcessorModelState.ColorTransType colorType)
          throws IllegalArgumentException {
    if (colorType == null) {
      throw new IllegalArgumentException("Color transformation type must not be null");
    }
    Pixel pixel = new Pixel(this.red, this.green, this.blue);
    if (colorType == ImageProcessorModelState.ColorTransType.Greyscale) {
      int weightedSum = (int) ((this.red * 0.2126) + (this.green * 0.7152) + (this.blue * 0.0722));
      pixel = new Pixel(weightedSum, weightedSum, weightedSum);
    } else if (colorType == ImageProcessorModelState.ColorTransType.Sepia) {
      int r = (int) ((this.red * 0.393) + (this.green * 0.769) + (this.blue * 0.189));
      int g = (int) ((this.red * 0.349) + (this.green * 0.686) + (this.blue * 0.168));
      int b = (int) ((this.red * 0.272) + (this.green * 0.534) + (this.blue * 0.131));
      pixel = new Pixel(Math.max(0,Math.min(r, 255)), Math.max(0,Math.min(g, 255)),
              Math.max(0,Math.min(b, 255)));
    }
    return pixel;
  }

  @Override
  public boolean overMaxValue(int maxValue) {
    return (this.red > maxValue || this.green > maxValue || this.blue > maxValue);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }

    if (!(o instanceof Pixel)) {
      return false;
    }

    Pixel that = (Pixel) o;

    return ((this.red == that.red)
            && (this.green == that.green)
            && (this.blue == that.blue));
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.red, this.green, this.blue);
  }

  @Override
  public int[] getValues() {
    int[] rgb = new int[3];
    rgb[0] = this.red;
    rgb[1] = this.green;
    rgb[2] = this.blue;
    return rgb;
  }

  @Override
  public String toByteRead() {
    StringBuilder builder = new StringBuilder();
    builder.append(this.red + "\n");
    builder.append(this.green + "\n");
    builder.append(this.blue + "\n");
    return builder.toString();
  }
}