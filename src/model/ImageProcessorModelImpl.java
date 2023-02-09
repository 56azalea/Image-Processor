package model;

import java.util.HashMap;
import java.util.Map;

/**
 * The implementation of {@link ImageProcessorModel}. Applies different
 * changes on the pixels of the image based on the desired order. For now,
 * the image can: flip horizontally/vertically, brighten/darken,
 * visualize individual RGB components, and visualize value/intensity/luma.
 */
public class ImageProcessorModelImpl implements ImageProcessorModel {
  protected final Map<String, Image> images;
  protected int width = -1;
  protected int height = -1;
  protected int maxValue = -1;
  protected Image image;

  /**
   * Constructs an image processor model,
   * which instantiates the storage with a hash map.
   */
  public ImageProcessorModelImpl() {
    this.images = new HashMap<String, Image>();
  }

  @Override
  public Map<String, Image> getImage() {
    return new HashMap<>(this.images);
  }

  @Override
  public Image getImage(String imageTitle) {
    return this.getImage().getOrDefault(imageTitle, null);
  }

  @Override
  public void addImage(String imageTitle, Image image) {
    this.images.put(imageTitle, image);
  }

  /**
   * A private helper function for all the image operations,
   * which instantiates the image, width, height, and maximum value
   * fields using the imageTitle input.
   *
   * @param imageTitle the title of the image to operate on
   */
  protected void operationSetup(String imageTitle) {
    this.image = this.images.get(imageTitle);
    this.width = this.image.getWidth();
    this.height = this.image.getHeight();
    this.maxValue = this.image.getMaxValue();
  }

  @Override
  public void flip(String imageTitle, FlipType flipType, String dest)
          throws IllegalArgumentException {
    nullInputs(imageTitle, dest);
    imageInStorage(imageTitle, "flip");
    // extracts the image from the storage
    this.operationSetup(imageTitle);
    IPixel[][] original = this.image.getPixels();
    IPixel[][] updated = new Pixel[this.height][this.width];

    for (int i = 0; i < this.height; i++) {
      for (int j = 0; j < this.width; j++) {
        if (flipType.equals(FlipType.Horizontal)) {
          updated[i][j] = original[i][this.width - j - 1];
        }
        if (flipType.equals(FlipType.Vertical)) {
          updated[i][j] = original[this.height - i - 1][j];
        }
      }
    }
    this.addImage(dest, new ImageImpl(width, height, maxValue, updated));
  }

  @Override
  public void brighten(String imageTitle, int strength, String dest)
          throws IllegalArgumentException {
    nullInputs(imageTitle, dest);
    imageInStorage(imageTitle, "brighten");
    // extracts the image from the storage
    this.operationSetup(imageTitle);
    IPixel[][] original = this.image.getPixels();
    IPixel[][] updated = new Pixel[this.height][this.width];

    for (int i = 0; i < this.height; i++) {
      for (int j = 0; j < this.width; j++) {
        IPixel newPixel;
        newPixel = original[i][j].brighten(strength, this.maxValue);
        updated[i][j] = newPixel;
      }
    }
    // saves it in the storage
    this.addImage(dest, new ImageImpl(width, height, maxValue, updated));
  }

  @Override
  public void multipleGreyscale(String imageTitle, GreyscaleType greyType, String dest)
          throws IllegalArgumentException {
    nullInputs(imageTitle, dest);
    imageInStorage(imageTitle, "to-greyscale");
    // extracts the image from the storage
    this.operationSetup(imageTitle);
    IPixel[][] original = this.image.getPixels();
    IPixel[][] updated = new Pixel[this.height][this.width];

    for (int i = 0; i < this.height; i++) {
      for (int j = 0; j < this.width; j++) {
        IPixel newPixel;
        newPixel = original[i][j].greyscale(greyType);
        updated[i][j] = newPixel;
      }
    }
    this.addImage(dest, new ImageImpl(width, height, maxValue, updated));
  }

  @Override
  public void filtering(String imageTitle, FilteringType filterType, String dest)
          throws IllegalArgumentException {
    nullInputs(imageTitle, dest);
    imageInStorage(imageTitle, "filtering");
    // extracts the image from the storage
    this.operationSetup(imageTitle);
    IPixel[][] original = this.image.getPixels();
    IPixel[][] updated = new Pixel[this.height][this.width];
    IPixel newPixel;
    for (int i = 0; i < this.height; i++) {
      for (int j = 0; j < this.width; j++) {
        newPixel = this.blurOrSharpen(i, j, filterType, original);
        updated[i][j] = newPixel;
      }
    }
    this.addImage(dest, new ImageImpl(width, height, maxValue, updated));
  }

  /**
   * A private helper function that returns a new pixel with
   * updated RGB channel values, based on its surrounding pixels'
   * RBG values and the filter type. Appropriate kernel is applied
   * to the pixel to update the channel value.
   *
   * @param pixelRow   the row position of the pixel
   * @param pixelCol   the column position of the pixel
   * @param filterType the filter type of the pixel
   * @param original   the original 2d array of pixels
   * @return the new pixel with updated RBG values after filtering
   */
  protected IPixel blurOrSharpen(int pixelRow, int pixelCol,
                                 FilteringType filterType, IPixel[][] original) {
    int[][] kernel = new int[][]{};
    int[][][] surrounding = new int[][][]{};
    int newRed = 0;
    int newGreen = 0;
    int newBlue = 0;

    if (filterType == FilteringType.Blur) {
      kernel = new int[][]{{16, 8, 16}, {8, 4, 8}, {16, 8, 16}};
      surrounding = new int[][][]{{{-1, -1}, {-1, 0}, {-1, 1}}, {{0, -1}, {0, 0}, {0, 1}},
              {{1, -1}, {1, 0}, {1, 1}}};
    } else if (filterType == FilteringType.Sharpen) {
      kernel = new int[][]{{-8, -8, -8, -8, -8}, {-8, 4, 4, 4, -8}, {-8, 4, 1, 4, -8},
              {-8, 4, 4, 4, -8}, {-8, -8, -8, -8, -8}};
      surrounding = new int[][][]{{{-2, -2}, {-2, -1}, {-2, 0}, {-2, 1}, {-2, 2}},
              {{-2, -2}, {-1, -1}, {-1, 0}, {-1, 1}, {-1, 2}},
              {{0, -2}, {0, -1}, {0, 0}, {0, 1}, {0, 2}},
              {{1, -2}, {1, -1}, {1, 0}, {1, 1}, {1, 2}},
              {{2, -2}, {2, -1}, {2, 0}, {2, 1}, {2, 2}}};
    }
    for (int i = 0; i < kernel.length; i++) {
      for (int j = 0; j < kernel.length; j++) {
        try {
          newRed += (original[pixelRow + surrounding[i][j][0]][pixelCol
                  + surrounding[i][j][1]].getRed()) / kernel[i][j];
        } catch (ArrayIndexOutOfBoundsException e) {
          newRed += 0;
        }
        try {
          newGreen += (original[pixelRow + surrounding[i][j][0]][pixelCol
                  + surrounding[i][j][1]].getGreen()) / kernel[i][j];
        } catch (ArrayIndexOutOfBoundsException e) {
          newGreen += 0;
        }
        try {
          newBlue += (original[pixelRow + surrounding[i][j][0]][pixelCol
                  + surrounding[i][j][1]].getBlue()) / kernel[i][j];
        } catch (ArrayIndexOutOfBoundsException e) {
          newBlue += 0;
        }
      }
    }
    return new Pixel(Math.max(0, Math.min(newRed, 255)), Math.max(0, Math.min(newGreen, 255)),
            Math.max(0, Math.min(newBlue, 255)));
  }

  @Override
  public void colorTransformation(String imageTitle, ColorTransType colorType, String dest)
          throws IllegalArgumentException {
    nullInputs(imageTitle, dest);
    imageInStorage(imageTitle, "color transformation");
    // extracts the image from the storage
    this.operationSetup(imageTitle);
    IPixel[][] original = this.image.getPixels();
    IPixel[][] updated = new Pixel[this.height][this.width];

    for (int i = 0; i < this.height; i++) {
      for (int j = 0; j < this.width; j++) {
        IPixel newPixel = original[i][j].colorTrans(colorType);
        updated[i][j] = newPixel;
      }
    }
    this.addImage(dest, new ImageImpl(width, height, maxValue, updated));
  }

  @Override
  public void downscale(String imageTitle, double width, double height, String dest)
          throws IllegalArgumentException {
    nullInputs(imageTitle, dest);
    imageInStorage(imageTitle, "downscale");
    // extracts the image from the storage
    this.operationSetup(imageTitle);
    int newWidth = (int) Math.floor(this.image.getWidth() * width);
    int newHeight = (int) Math.floor(this.image.getHeight() * height);
    Pixel[][] updated = new Pixel[newHeight][newWidth];
    IPixel pixel2;
    IPixel pixel3;
    IPixel pixel4;

    for (int i = 0; i < newHeight; i++) {
      for (int j = 0; j < newWidth; j++) {
        double row = i * (1.0 / height);
        double column = j * (1.0 / width);
        IPixel pixel1 = this.image.getPixelAt((int) Math.floor(row), (int) Math.floor(column));
        try {
          pixel2 = this.image.getPixelAt((int) Math.floor(row) + 1, (int) Math.floor(column));
        } catch (ArrayIndexOutOfBoundsException e) {
          pixel2 = this.image.getPixelAt((int) Math.ceil(row), (int) Math.floor(column));
        }
        try {
          pixel3 = this.image.getPixelAt((int) Math.floor(row), (int) Math.floor(column) + 1);
        } catch (ArrayIndexOutOfBoundsException e) {
          pixel3 = this.image.getPixelAt((int) Math.floor(row), (int) Math.ceil(column));
        }
        try {
          pixel4 = this.image.getPixelAt((int) Math.floor(row) + 1,
                  (int) Math.floor(column) + 1);
        } catch (ArrayIndexOutOfBoundsException e) {
          pixel4 = this.image.getPixelAt((int) Math.ceil(row), (int) Math.ceil(column));
        }
        int[] pixel1Values = pixel1.getValues();
        int[] pixel2Values = pixel2.getValues();
        int[] pixel3Values = pixel3.getValues();
        int[] pixel4Values = pixel4.getValues();
        int[] rgbSum = new int[3];

        for (int k = 0; k <= 2; k++) {
          double value1 = (pixel2Values[k] * (row - Math.floor(row))) + (pixel1Values[k]
                  * ((Math.floor(row) + 1) - row));
          double value2 = (pixel4Values[k] * (row - Math.floor(row))) + (pixel3Values[k]
                  * ((Math.floor(row) + 1) - row));
          int computation = (int) ((value2 * (column - Math.floor(column))) + (value1
                  * ((Math.floor(column) + 1) - column)));
          rgbSum[k] = computation;
        }
        for (int k = 0; k < 3; k++) {
          if (rgbSum[k] > this.maxValue) {
            rgbSum[k] = this.maxValue;
          }
        }
        updated[i][j] = new Pixel(rgbSum[0], rgbSum[1], rgbSum[2]);
      }
    }
    this.addImage(dest, new ImageImpl(newWidth, newHeight, maxValue, updated));
  }

  /**
   * A private helper function to determine if any of the
   * parameters are null. If so, throw an illegal argument exception.
   *
   * @param input1 the first string input
   * @param input2 the second string input
   * @throws IllegalArgumentException if either string parameter is null
   */
  private void nullInputs(String input1, String input2) throws IllegalArgumentException {
    if (input1 == null || input2 == null) {
      throw new IllegalArgumentException("At least one parameter is null.");
    }
  }

  /**
   * A private helper function to determine the desired image exists
   * in the storage. If it cannot be found, must throw an illegal
   * state exception with a message telling that the given operation is
   * not able to be performed on that image.
   *
   * @param imageTitle   the target image
   * @param errorMessage the type of command that is being attempted on the image
   * @throws IllegalArgumentException if the image cannot be found
   */
  protected void imageInStorage(String imageTitle, String errorMessage)
          throws IllegalArgumentException {
    if (!this.images.containsKey(imageTitle)) {
      throw new IllegalArgumentException(
              "The image file to " + errorMessage + " cannot be found.");
    }
  }
}