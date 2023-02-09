package controller.commands;

import model.Image;
import model.MaskProcessorModel;
import model.ImageProcessorModelState.ColorTransType;
import util.Utils;

/**
 * A command specifically to execute greyscale or sepia on the image.
 * Extends {@link BulkCommand}, as it is a parent command.
 * This command's execute function turns a color
 * image into a greyscale image or a sepia-toned image.
 */
public final class ColorTransformation extends BulkCommand {

  /**
   * Constructs a color transformation using the input command line.
   * Would throw an exception if the input is invalid.
   *
   * @param line the command line
   * @throws IllegalArgumentException if the command line is null
   */
  public ColorTransformation(String[] line) {
    super(line);
    // greyscale image-name dest-image-name
  }

  @Override
  public void execute(MaskProcessorModel model) throws IllegalArgumentException {
    super.execute(model);

    String command = this.line[0];
    try {
      if (command.charAt(0) == 'g') {
        if (line.length == 4) {
          Image mask = Utils.othersToImage("res/" + this.line[2], this.line[2]);
          model.colorTransformation(this.line[1], ColorTransType.Greyscale,
                  mask.getPixels(), this.line[3]);
        } else if (line.length == 3) {
          model.colorTransformation(this.line[1], ColorTransType.Greyscale, this.line[2]);
        }
      }else {
        if (line.length == 4) {
          Image mask = Utils.othersToImage("res/" + this.line[2], this.line[2]);
          model.colorTransformation(this.line[1], ColorTransType.Sepia,
                  mask.getPixels(), this.line[3]);
        } else if (line.length == 3) {
          model.colorTransformation(this.line[1], ColorTransType.Sepia, this.line[2]);
        }
      }
    } catch (IllegalArgumentException e) {
      System.out.println("Color transformation cannot be executed on the image");
    }
  }
}