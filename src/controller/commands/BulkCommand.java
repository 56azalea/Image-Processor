package controller.commands;

import model.MaskProcessorModel;

/**
 * The bulk command that implements {@link Command}.
 * This would serve as a parent command for other operations.
 */
public class BulkCommand implements Command {
  protected String[] line;

  /**
   * Constructs a bulk command which takes in a command line.
   * Would throw an exception if the given command line is invalid.
   *
   * @param line the command line
   * @throws IllegalArgumentException if the command line is null
   */
  public BulkCommand(String[] line) throws IllegalArgumentException {
    if (line == null) {
      throw new IllegalArgumentException("The command line cannot be null");
    }
    this.line = line;
  }

  @Override
  public void execute(MaskProcessorModel model) throws IllegalArgumentException {
    if (model == null) {
      throw new IllegalArgumentException("A null model cannot be executed");
    }
  }
}