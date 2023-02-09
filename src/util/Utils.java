package util;

import java.awt.image.BufferedImage;
import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import javax.imageio.ImageIO;

import model.IPixel;
import model.Image;
import model.ImageImpl;
import model.Pixel;

import static java.awt.image.BufferedImage.TYPE_INT_RGB;

/**
 * A utility class built to reduce overlaps of codes between
 * different parts of the project, like image processing
 * of ppm or {@link BufferedImage}.
 */
public class Utils {

  /**
   * A utility that generates an {@link Image} of a PPM
   * file using its path and name. Would be used in multiple
   * locations, including ControllerImpl and GUIController.
   * Adapted from the provided ImageUtil.
   *
   * @param imagePath the image path
   * @param imageName the image name
   * @return the image of the ppm file
   */
  public static Image ppmToImage(String imagePath, String imageName) {
    int width = 0;
    int height = 0;
    int red = 0;
    int green = 0;
    int blue = 0;
    int maxValue = 255;
    Pixel[][] pixels;

    Scanner sc;
    try {
      sc = new Scanner(new FileInputStream(imagePath));
    } catch (FileNotFoundException e) {
      throw new IllegalArgumentException("Invalid file");
    }
    StringBuilder builder = new StringBuilder();
    while (sc.hasNextLine()) {
      String str = sc.nextLine();
      if (!str.equals("") && str.charAt(0) != '#') {
        builder.append(str).append(System.lineSeparator());
      }
    }

    // now sets up the scanner to read from the string we just built
    sc = new Scanner(builder.toString());
    String token;

    if (sc.hasNext()) {
      token = sc.next();
    } else {
      throw new IllegalArgumentException("Nothing to load");
    }
    if (!token.equals("P3")) {
      throw new IllegalArgumentException("Invalid PPM file: plain RAW file should begin with P3");
    }
    if (sc.hasNextInt()) {
      width = sc.nextInt();
    }
    if (sc.hasNextInt()) {
      height = sc.nextInt();
    }
    if (sc.hasNextInt()) {
      maxValue = sc.nextInt();
    }

    pixels = new Pixel[height][width];
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        if (sc.hasNextInt()) {
          red = sc.nextInt();
        }
        if (sc.hasNextInt()) {
          green = sc.nextInt();
        }
        if (sc.hasNextInt()) {
          blue = sc.nextInt();
        }
        pixels[i][j] = new Pixel(red, green, blue);
      }
    }
    return new ImageImpl(width, height, maxValue, pixels);
  }

  /**
   * A utility that generates an {@link Image} of an image
   * file (other than PPM) using its path and name. Would be used in multiple
   * locations, including ControllerImpl and GUIController.
   *
   * @param imagePath the image path
   * @param imageName the image name
   * @return the image of the ppm file
   */
  public static Image othersToImage(String imagePath, String imageName)
          throws IllegalArgumentException {
    int width = 0;
    int height = 0;
    int red = 0;
    int green = 0;
    int blue = 0;
    int maxValue = 255;
    IPixel[][] pixels;

    BufferedImage imageToLoad = null;
    try {
      imageToLoad = ImageIO.read(new File(imagePath));
    } catch (IOException e) {
      throw new IllegalArgumentException("Invalid file");
    }

    height = imageToLoad.getHeight();
    width = imageToLoad.getWidth();

    pixels = new Pixel[height][width];
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        red = new Color(imageToLoad.getRGB(j, i)).getRed();
        green = new Color(imageToLoad.getRGB(j, i)).getGreen();
        blue = new Color(imageToLoad.getRGB(j, i)).getBlue();
        pixels[i][j] = new Pixel(red, green, blue);
      }
    }
    return new ImageImpl(width, height, maxValue, pixels);
  }

  /**
   * A utility to create a {@link BufferedImage} from an
   * {@link Image} object. Uses its individual pixels to
   * produce an image.
   *
   * @param saved     the Image file
   * @param imagePath the path of the image
   * @return a buffered image
   */
  public static BufferedImage toBufferedImage(Image saved, String imagePath) {
    BufferedImage imageToSave = new BufferedImage(
            saved.getWidth(), saved.getHeight(), TYPE_INT_RGB);
    for (int i = 0; i < imageToSave.getHeight(); i++) {
      for (int j = 0; j < imageToSave.getWidth(); j++) {
        Color col = new Color(saved.getPixelAt(i, j).getRed(),
                saved.getPixelAt(i, j).getGreen(),
                saved.getPixelAt(i, j).getBlue());
        imageToSave.setRGB(j, i, col.getRGB());
      }
    }
    return imageToSave;
  }
}