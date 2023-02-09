package controller;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Function;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import controller.commands.Brighten;
import controller.commands.ColorTransformation;
import controller.commands.Command;
import controller.commands.Downscale;
import controller.commands.Filtering;
import controller.commands.Flip;
import controller.commands.Greyscale;
import model.Image;
import model.MaskProcessorModel;
import view.ImageProcessorTextView;
import view.ImageProcessorView;
import util.Utils;

/**
 * The class that represents an image processor controller, which accepts an
 * input from the user and passes it to the image process model using commands.
 * Implements the {@link Controller} interface.
 */
public class ControllerImpl implements Controller {
  private final MaskProcessorModel model;
  private final ImageProcessorView view;
  private final Readable in;
  private final Map<String, Function<String[], Command>> commandType = new HashMap<>();

  /**
   * Initializes the controller using the model and readable.
   *
   * @param model    the model processing and manipulating images
   * @param readable the readable reading an input from the user
   * @throws IllegalArgumentException if any parameter is null
   */
  public ControllerImpl(MaskProcessorModel model, Readable readable) {
    this(model, new ImageProcessorTextView(model, System.out), readable);
  }

  /**
   * Initializes the controller using the model, view, and readable.
   *
   * @param model    the model processing and manipulating images
   * @param view     the view transmitting messages to the user
   * @param readable the readable reading an input from the user
   * @throws IllegalArgumentException if any parameter is null
   */
  public ControllerImpl(MaskProcessorModel model, ImageProcessorView view, Readable readable)
          throws IllegalArgumentException {
    if (model == null || view == null || readable == null) {
      throw new IllegalArgumentException("The model, view, or readable cannot null");
    }
    this.model = model;
    this.view = view;
    this.in = readable;

    // initializes all command types
    this.commandType.put("brighten", Brighten::new);
    this.commandType.put("horizontal-flip", Flip::new);
    this.commandType.put("vertical-flip", Flip::new);
    this.commandType.put("red-component", Greyscale::new);
    this.commandType.put("green-component", Greyscale::new);
    this.commandType.put("blue-component", Greyscale::new);
    this.commandType.put("value-component", Greyscale::new);
    this.commandType.put("intensity-component", Greyscale::new);
    this.commandType.put("luma-component", Greyscale::new);
    // added for HW05
    this.commandType.put("blur", Filtering::new);
    this.commandType.put("sharpen", Filtering::new);
    this.commandType.put("greyscale", ColorTransformation::new);
    this.commandType.put("sepia", ColorTransformation::new);
    // added for HW08
    this.commandType.put("downscale", Downscale::new);
  }

  @Override
  public void runProcessor() throws IllegalStateException {
    Scanner scan = new Scanner(this.in);
    boolean quit = false;
    while (!quit) {
      while (scan.hasNext()) {
        String command = scan.next();
        if (command.equalsIgnoreCase("q")) {
          quit = true;
          transmit("Image processor quited");
          return;
        } else if (command.equals("load")) {
          try {
            String imagePath = scan.next();
            String imageName = scan.next();
            this.load(imagePath, imageName);
            transmit("Image path: " + imagePath + ", Image name: " + imageName);
          } catch (IllegalArgumentException e) {
            transmit(e.getMessage());
          }
          break;
        } else if (command.equals("save")) {
          try {
            String imageP = scan.next();
            String imageN = scan.next();
            this.save(imageP, imageN);
            transmit("Image path: " + imageP + ", Image name: " + imageN);
          } catch (IllegalArgumentException e) {
            transmit(e.getMessage());
          }
          break;
        } else if (command.equals("brighten")) {
          String line = scan.nextLine();
          String[] inputs = line.split(" ");
          if (inputs.length == 4) {
            transmit("Operation: brighten, Image name: " + inputs[1]
                    + ", Increment: " + inputs[2] + ", New file name: " + inputs[3]);
          } else if (inputs.length == 5) {
            transmit("Operation: brighten, Image name: " + inputs[1]
                    + ", Increment: " + inputs[2] + ", Mask name: " + inputs[3]
                    + ", New file name: " + inputs[4]);
          }
          Function<String[], Command> functionCommand =
                  this.commandType.getOrDefault("brighten", null);
          functionCommand.apply(inputs).execute(this.model);
          break;
      } else if (command.equals("downscale")) {
        String line = scan.nextLine();
        String[] inputs = line.split(" ");
        inputs[0] = command;
        transmit("Operation: downscale, Width scale: " + inputs[1]
                  + ", Height scale: " + inputs[2] + ", Image name: " + inputs[3]
                  + ", New file name: " + inputs[4]);
        Function<String[], Command> functionCommand =
                this.commandType.getOrDefault("downscale", null);
        functionCommand.apply(inputs).execute(this.model);
        break;
      } else if (this.commandType.containsKey(command)) {
            String line = scan.nextLine();
              String[] input = line.split(" ");
              input[0] = command;
              if (input.length == 3) {
                transmit("Operation: " + command + ", Image name: "
                        + input[1] + ", New file name: " + input[2]);
              } else if (input.length == 4) {
                transmit("Operation: " + command + ", Image name: "
                        + input[1] + ", Mask name: " + input[2] + ", New file name: " + input[3]);
              }
              Function<String[], Command> functionCommands =
                      this.commandType.getOrDefault(command, null);
              functionCommands.apply(input).execute(this.model);
            break;
          }
      }
    }
    throw new IllegalStateException("Running out of inputs");
  }

  /**
   * Transmits a message to the view.
   *
   * @param message the message to be transmitted
   * @throws IllegalArgumentException if the transmission of the message has failed
   */
  private void transmit(String message) throws IllegalStateException {
    try {
      this.view.renderMessage(message + "\n");
    } catch (IllegalStateException e) {
      throw new IllegalStateException("The transmission of the message has failed");
    }
  }

  @Override
  public void load(String imagePath, String imageName) throws IllegalArgumentException {
    if (imagePath.endsWith("ppm")) {
      this.model.addImage(imageName, Utils.ppmToImage(imagePath, imageName));
    } else {
      this.model.addImage(imageName, Utils.othersToImage(imagePath, imageName));
    }
  }

  @Override
  public void save(String imagePath, String imageName) throws IllegalArgumentException {
    Image saved = this.model.getImage(imageName);
    if (saved == null) {
      throw new IllegalArgumentException("Nothing to save");
    } else {
      if (imagePath.endsWith("ppm")) {
        this.savePPM(saved, new File(imagePath));
      } else {
        this.saveOther(saved, imagePath);
      }
    }
  }

  /**
   * A private helper function built to save other
   * formats of images onto the computer device.
   *
   * @param saved     the image to save on computer
   * @param imagePath the path of the image
   * @throws IllegalArgumentException if it fails to save a file
   */
  private void saveOther(Image saved, String imagePath) {
    BufferedImage imageToSave = Utils.toBufferedImage(saved, imagePath);
    try {
      ImageIO.write(imageToSave, imagePath.substring(imagePath.length() - 3),
              new File(imagePath));
    } catch (IOException e) {
      throw new IllegalArgumentException("Failed to save file");
    }
  }

  /**
   * A private helper function to save an image, specifically in a ppm format.
   * Saves the current state of the image to the file directory.
   *
   * @param saved an image to save
   * @param file  an output file
   * @throws IllegalArgumentException if it fails to save a file
   */
  private void savePPM(Image saved, File file) throws IllegalArgumentException {
    DataOutputStream output;
    try {
      output = new DataOutputStream(new FileOutputStream(file));
    } catch (FileNotFoundException e) {
      throw new IllegalArgumentException("Failed to save file");
    }
    try {
      output.writeBytes(saved.toByteRead());
      output.close();
    } catch (IOException e) {
      throw new IllegalArgumentException("Failed to save file");
    }
  }
}