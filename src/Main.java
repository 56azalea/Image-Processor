import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStreamReader;

import controller.Controller;
import controller.ControllerImpl;
import controller.Features;
import controller.GUIController;
import model.MaskProcessorModel;
import model.MaskProcessorModelImpl;
import view.ImageProcessorGUIViewImpl;
import view.ImageProcessorTextView;
import view.ImageProcessorView;

/**
 * The main class where takes in the command line to
 * determine how to run the image processor. There are
 * two options: text view and GUI view.
 */
public final class Main {

  /**
   * Accepts an args argument to run the image processor.
   * There are two ways to run the processor: with GUI and
   * with the console. In order to run with GUI, it takes in
   * no command order. If it is specifically indicated to run
   * with text, the args will indicate that.
   *
   * @param args the order for the image processor
   */
  public static void main(String[] args) {
    MaskProcessorModel model = new MaskProcessorModelImpl();

    // if there's a command line to call on the script file
    if (args.length > 0 && args[0].equals("-file")) {
      ImageProcessorView view = new ImageProcessorTextView(model);
      try {
        File script = new File(args[1]);
        FileReader scriptReader = new FileReader(script);
        Controller controller = new ControllerImpl(model, view, scriptReader);
        controller.runProcessor();
      } catch (FileNotFoundException e) {
        // nothing happens if the file doesn't exist
      }
    } else {
      if (args.length > 0 && args[0].equals("-text")) {
        ImageProcessorView view = new ImageProcessorTextView(model);
        Controller controller = new ControllerImpl(model, view, new InputStreamReader(System.in));
        controller.runProcessor();
      } else {
        if (args.length == 0) {
          ImageProcessorGUIViewImpl.setDefaultLookAndFeelDecorated(false);
          ImageProcessorGUIViewImpl frame = new ImageProcessorGUIViewImpl();
          Features controller = new GUIController(model, frame);
        } else {
          throw new IllegalArgumentException("The provided command-line argument is invalid");
        }
      }
    }
  }
}