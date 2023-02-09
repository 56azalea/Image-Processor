package controller.commands;

import model.MaskProcessorModel;

/**
 * A command specifically to execute downscale on the image.
 * Extends {@link BulkCommand}, as it is a parent command.
 * This command's execute function downsizes the image.
 */
public class Downscale extends BulkCommand {
  private final double width;
  private final double height;

  /**
   * Constructs a Downscale using the input command line.
   * Would throw an exception if the input is invalid.
   *
   * @param line the command line
   * @throws IllegalArgumentException if the command line is null
   */
  public Downscale(String[] line) throws IllegalArgumentException {
    super(line);
    // downscale width height image-name dest-image-name
    if (line.length == 5) {
      try {
        this.width = Double.parseDouble(line[1]);
        this.height = Double.parseDouble(line[2]);
      } catch (IllegalArgumentException e) {
        throw new IllegalArgumentException("Width or height cannot be found");
      }
    } else {
      throw new IllegalArgumentException("Invalid parameters.");
    }
  }

  @Override
  public void execute(MaskProcessorModel model) throws IllegalArgumentException {
    super.execute(model);
    try {
      model.downscale(this.line[3], this.width, this.height, this.line[4]);
    } catch (IllegalArgumentException e) {
      System.out.println("Downscale cannot be executed on the image");
    }
  }
}