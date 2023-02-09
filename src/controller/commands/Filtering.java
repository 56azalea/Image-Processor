package controller.commands;

import model.Image;
import model.MaskProcessorModel;
import model.ImageProcessorModelState.FilteringType;
import util.Utils;

/**
 * A command specifically to execute blur or sharpen on the image.
 * Extends {@link BulkCommand}, as it is a parent command.
 * This command's execute function turns an image into a blurred
 * or a sharpened version.
 */
public class Filtering extends BulkCommand {

  /**
   * Constructs a blurring or sharpening command which takes in a command line.
   * Would throw an exception if the given command line is invalid.
   *
   * @param line the command line
   * @throws IllegalArgumentException if the command line is null
   */
  public Filtering(String[] line) throws IllegalArgumentException {
    super(line);
    // blur image-name dest-image-name
  }

  @Override
  public void execute(MaskProcessorModel model) throws IllegalArgumentException {
    super.execute(model);

    String command = this.line[0];
    try {
      if (command.charAt(0) == 'b') {
      if (line.length == 4) {
        Image mask = Utils.othersToImage("res/" + this.line[2], this.line[2]);
        model.filtering(this.line[1], FilteringType.Blur, mask.getPixels(), this.line[3]);
      } else if (line.length == 3) {
        model.filtering(this.line[1], FilteringType.Blur, this.line[2]);
      }
      } else {
        if (line.length == 4) {
          Image mask = Utils.othersToImage("res/" + this.line[2], this.line[2]);
          model.filtering(this.line[1], FilteringType.Sharpen, mask.getPixels(), this.line[3]);
        } else if (line.length == 3) {
          model.filtering(this.line[1], FilteringType.Sharpen, this.line[2]);
        }
      }
    } catch (IllegalArgumentException e) {
      System.out.println("Filtering cannot be executed on the image");
    }
  }
}