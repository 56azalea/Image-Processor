package controller;

/**
 * An interface that operates as an image processor controller.
 */
public interface Controller {

  /**
   * Runs the controller by accepting an input and passing it to the image processor model.
   *
   * @throws IllegalStateException when failing to append an output to the Appendable. Also, in
   *                               the case of the program not being quit or the Readable running
   *                               out of inputs.
   */
  void runProcessor() throws IllegalStateException;

  /**
   * Loads an image from a file; this supports different
   * types files, from ppm to jpg, png, and bmp.
   *
   * @param imagePath a path to the image file
   * @param imageName a name of the image file
   * @throws IllegalArgumentException if either parameter is null
   */
  void load(String imagePath, String imageName) throws IllegalArgumentException;

  /**
   * Saves an image to an image file; this supports different
   * types files, from ppm to jpg, png, and bmp.
   *
   * @param imagePath a path to the image file
   * @param imageName a name of the image file
   * @throws IllegalArgumentException if either parameter is null
   */
  void save(String imagePath, String imageName) throws IllegalArgumentException;
}