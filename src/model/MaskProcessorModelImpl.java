package model;

/**
 * A model newly made for HW08, specifically for masking.
 * It extends {@link ImageProcessorModelImpl}, similar to how
 * SmarterTurtle extends SimpleTurtle and implements
 * TracingTurtleModel in the lecture.
 */
public class MaskProcessorModelImpl extends ImageProcessorModelImpl implements MaskProcessorModel {
  private Image maskImage;
  private IPixel[][] maskPixel;

  /**
   * Constructor for {@link MaskProcessorModelImpl}. Needs a
   * super method since it's extending another class.
   */
  public MaskProcessorModelImpl() {
    super();
    this.maskImage = null;
    this.maskPixel = new Pixel[][]{};
  }

  /**
   * A private helper function to determine if any of the parameters
   * are null. If so, throw an illegal argument exception.
   *
   * @param input1 the first string input
   * @param input2 the 2D pixel array
   * @param input3 the second string input
   * @throws IllegalArgumentException if any of the parameters is null
   */
  private void nullInputs(String input1, IPixel[][] input2, String input3)
          throws IllegalArgumentException {
    if (input1 == null || input2 == null || input3 == null) {
      throw new IllegalArgumentException("At least one parameter is null");
    }
  }

  /**
   * A private helper function for all the image operations,
   * which instantiates the image, width, height, and maximum value
   * fields using the imageTitle input. It also pulls out the mask
   * image if it exists.
   *
   * @param imageTitle the title of the image to operate on
   * @param mask       the mask 2D pixel array to use
   * @throws IllegalArgumentException if the mask size isn't same with the image size
   */
  private void operationSetup(String imageTitle, IPixel[][] mask) throws IllegalArgumentException {
    super.operationSetup(imageTitle);
    this.maskPixel = mask;
    if (this.maskPixel.length != this.image.getPixels().length) {
      throw new IllegalArgumentException("The mask size must be the same with the image size");
    }
  }

  /**
   * A private helper function to check if the pixel must be
   * manipulated or not. For this model, only where the mask
   * pixels are black will be modified.
   *
   * @param height the height position of the pixel
   * @param width  the width position of the pixel
   * @return true if it must be modified
   */
  private boolean conditionCheck(int height, int width) {
    return (this.maskPixel.length == 0
            || this.maskPixel[height][width].getRed() < 200);
  }

  @Override
  public void brighten(String imageTitle, int strength, IPixel[][] mask, String dest)
          throws IllegalArgumentException {
    this.nullInputs(imageTitle, mask, dest);
    this.imageInStorage(imageTitle, "brighten");
    this.operationSetup(imageTitle, mask);
    IPixel[][] original = this.image.getPixels();
    IPixel[][] updated = new Pixel[this.height][this.width];

    for (int i = 0; i < this.height; i++) {
      for (int j = 0; j < this.width; j++) {
        IPixel newPixel;
        if (conditionCheck(i, j)) {
          newPixel = original[i][j].brighten(strength, this.maxValue);
          updated[i][j] = newPixel;
        } else {
          updated[i][j] = original[i][j];
        }
      }
    }
    // saves it in the storage
    this.addImage(dest, new ImageImpl(width, height, maxValue, updated));
  }

  @Override
  public void multipleGreyscale(String imageTitle, GreyscaleType greyType,
                                IPixel[][] mask, String dest) throws IllegalArgumentException {
    this.nullInputs(imageTitle, mask, dest);
    this.imageInStorage(imageTitle, "greyscale");
    this.operationSetup(imageTitle, mask);
    IPixel[][] original = this.image.getPixels();
    IPixel[][] updated = new Pixel[this.height][this.width];

    for (int i = 0; i < this.height; i++) {
      for (int j = 0; j < this.width; j++) {
        IPixel newPixel;
        if (conditionCheck(i, j)) {
          newPixel = original[i][j].greyscale(greyType);
          updated[i][j] = newPixel;
        } else {
          updated[i][j] = original[i][j];
        }
      }
    }
    this.addImage(dest, new ImageImpl(width, height, maxValue, updated));
  }

  @Override
  public void filtering(String imageTitle, FilteringType filterType,
                        IPixel[][] mask, String dest) throws IllegalArgumentException {
    this.nullInputs(imageTitle, mask, dest);
    this.imageInStorage(imageTitle, "filtering");
    this.operationSetup(imageTitle, mask);
    IPixel[][] original = this.image.getPixels();
    IPixel[][] updated = new Pixel[this.height][this.width];
    for (int i = 0; i < this.height; i++) {
      for (int j = 0; j < this.width; j++) {
        if (conditionCheck(i, j)) {
          IPixel newPixel = this.blurOrSharpen(i, j, filterType, original);
          updated[i][j] = newPixel;
        } else {
          updated[i][j] = original[i][j];
        }
      }
    }
    this.addImage(dest, new ImageImpl(width, height, maxValue, updated));
  }

  @Override
  public void colorTransformation(String imageTitle, ColorTransType colorType,
                                  IPixel[][] mask, String dest) throws IllegalArgumentException {
    this.nullInputs(imageTitle, mask, dest);
    this.imageInStorage(imageTitle, "color transformation");
    this.operationSetup(imageTitle, mask);
    IPixel[][] original = this.image.getPixels();
    IPixel[][] updated = new Pixel[this.height][this.width];

    for (int i = 0; i < this.height; i++) {
      for (int j = 0; j < this.width; j++) {
        if (conditionCheck(i, j)) {
          IPixel newPixel = original[i][j].colorTrans(colorType);
          updated[i][j] = newPixel;
        } else {
          updated[i][j] = original[i][j];
        }
      }
    }
    this.addImage(dest, new ImageImpl(width, height, maxValue, updated));
  }
}