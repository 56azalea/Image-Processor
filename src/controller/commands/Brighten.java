package controller.commands;

import model.Image;
import model.MaskProcessorModel;
import util.Utils;

/**
 * A command specifically to execute brightness on the image.
 * Extends {@link BulkCommand}, as it is a parent command.
 * This command's execute function brightens or darkens the image.
 */
public final class Brighten extends BulkCommand {
  private final int strength;

  /**
   * Constructs a Brighten using the input command line.
   * Would throw an exception if the input is invalid.
   *
   * @param line the command line
   * @throws IllegalArgumentException if the command line is null
   */
  public Brighten(String[] line) throws IllegalArgumentException {
    super(line);
    // brighten increment image-name dest-image-name
    if (line.length == 4 || line.length == 5) {
      try {
        this.strength = Integer.parseInt(line[2]);
      } catch (IllegalArgumentException e) {
        throw new IllegalArgumentException("Increment amount cannot be found");
      }
    } else {
      throw new IllegalArgumentException("Invalid parameters");
    }
  }

  @Override
  public void execute(MaskProcessorModel model) throws IllegalArgumentException {
    super.execute(model);
      if (line.length == 4) {
        model.brighten(this.line[1], this.strength, this.line[3]);
      } else {
    if (line.length == 5) {
      Image mask = Utils.othersToImage("res/" + this.line[3], this.line[3]);
      model.brighten(this.line[1], this.strength, mask.getPixels(), this.line[4]);
      } else
        throw new IllegalArgumentException("Brighten cannot be executed on the image");
      }
  }
}