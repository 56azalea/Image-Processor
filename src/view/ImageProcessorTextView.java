package view;

import java.io.IOException;

import model.ImageProcessorModelState;

/**
 * The class that represents a view implementation of an image processor.
 * Displays a message if the user must be let know of any circumstances.
 * Implements the {@link ImageProcessorView} interface.
 */
public class ImageProcessorTextView implements ImageProcessorView {
  private final Appendable out;

  /**
   * Default constructor of ImageProcessorTextView
   * that uses System.out for an appendable object.
   *
   * @param model the model displaying images
   * @throws IllegalArgumentException if the supplied model is null
   */
  public ImageProcessorTextView(ImageProcessorModelState model) throws IllegalArgumentException {
    this.out = System.out;
  }

  /**
   * Initializes the view using the model and appendable.
   *
   * @param model      the model displaying images
   * @param appendable the appendable producing an output
   * @throws IllegalArgumentException if any parameter is null
   */
  public ImageProcessorTextView(ImageProcessorModelState model, Appendable appendable) {
    if (model == null || appendable == null) {
      throw new IllegalArgumentException("The model or appendable cannot be null");
    }
    this.out = appendable;
  }

  @Override
  public void renderMessage(String message) throws IllegalArgumentException {
    try {
      this.out.append(message);
    } catch (IOException e) {
      throw new IllegalStateException("The transmission of the message has failed");
    }
  }
}