package controller.commands;

import model.Image;
import model.ImageProcessorModel;
import model.MaskProcessorModel;
import util.Utils;

/**
 * A command specifically to execute greyscale on the image.
 * Extends {@link BulkCommand}, as it is a parent command.
 * This command's execute function turns an image into greyscale color.
 */
public final class Greyscale extends BulkCommand {

  /**
   * Constructs a Greyscale using the input command line.
   * Would throw an exception if the input is invalid.
   *
   * @param line the command line
   * @throws IllegalArgumentException if the command line is null
   */
  public Greyscale(String[] line) {
    super(line);
    // red-component image-name dest-image-name
  }

  @Override
  public void execute(MaskProcessorModel model) throws IllegalArgumentException {
    super.execute(model);
    ImageProcessorModel.GreyscaleType greyType = null;

    switch (this.line[0]) {
      case "red-component":
        greyType = ImageProcessorModel.GreyscaleType.Red;
        break;
      case "green-component":
        greyType = ImageProcessorModel.GreyscaleType.Green;
        break;
      case "blue-component":
        greyType = ImageProcessorModel.GreyscaleType.Blue;
        break;
      case "value-component":
        greyType = ImageProcessorModel.GreyscaleType.Value;
        break;
      case "intensity-component":
        greyType = ImageProcessorModel.GreyscaleType.Intensity;
        break;
      case "luma-component":
        greyType = ImageProcessorModel.GreyscaleType.Luma;
        break;
      default:
        System.out.println("Greyscale cannot be executed on the image");
        break;
    }
    try {
      if (line.length == 3) {
        model.multipleGreyscale(this.line[1], greyType, this.line[2]);
      } else if (line.length == 4) {
        Image mask = Utils.othersToImage("res/" + this.line[2], this.line[2]);
        model.multipleGreyscale(this.line[1], greyType, mask.getPixels(), this.line[3]);
      }
    } catch (IllegalArgumentException e) {
      System.out.println("Greyscale cannot be executed on the image");
    }
  }
}