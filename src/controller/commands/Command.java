package controller.commands;

import model.MaskProcessorModel;

/**
 * Works as a macro command to operate on different methods
 * to alter the image.
 */
public interface Command {

  /**
   * Executes the command function on the given model.
   *
   * @param model the model to be executed
   * @throws IllegalArgumentException if the parameter is null
   */
  void execute(MaskProcessorModel model) throws IllegalArgumentException;
}