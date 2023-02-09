package view;

/**
 * An interface allowing the user to view outputs produced by an image processor.
 */
public interface ImageProcessorView {

  /**
   * Displays a message when the user must be let know of any circumstances.
   *
   * @param message the message to be displayed
   * @throws IllegalStateException if the transmission of the message has failed
   */
  void renderMessage(String message) throws IllegalArgumentException;
}