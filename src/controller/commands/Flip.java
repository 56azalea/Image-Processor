package controller.commands;

import model.ImageProcessorModelState;
import model.MaskProcessorModel;

/**
 * A command specifically to execute a flip on the image, either
 * horizontally or vertically. Extends {@link BulkCommand}, as
 * it is a parent command. This command's execute function
 * horizontal or vertical flip on the image.
 */
public class Flip extends BulkCommand {

  /**
   * Constructs a Flip command using the input command line.
   * Would throw an exception if the input is invalid.
   *
   * @param line the command line
   * @throws IllegalArgumentException if the command line is null
   */
  public Flip(String[] line) {
    super(line);
    // horizontal-flip image-name dest-image-name
  }

  @Override
  public void execute(MaskProcessorModel model) throws IllegalArgumentException {
    super.execute(model);

    String command = this.line[0];
    try {
      if (command.charAt(0) == 'h') {
        model.flip(this.line[1], ImageProcessorModelState.FlipType.Horizontal, this.line[2]);
      } else {
        model.flip(this.line[1], ImageProcessorModelState.FlipType.Vertical, this.line[2]);
      }
    } catch (IllegalArgumentException e) {
      System.out.println("Flip cannot be executed on the image");
    }
  }
}