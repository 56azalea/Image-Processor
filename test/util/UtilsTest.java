package util;

import org.junit.Before;
import org.junit.Test;

import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import model.IPixel;
import model.Image;
import model.ImageImpl;
import model.Pixel;

import static org.junit.Assert.assertEquals;

/**
 * Tests for {@link Utils} class' static void methods.
 * First, test if ppm file can be converted into image,
 * Second, test if other files can be converted into image,
 * Last, test if that image file can be buffered image.
 */
public class UtilsTest {
  IPixel[][] pixelsHouse;
  Image imageHouse;

  @Before
  public void initSetting() {
    this.pixelsHouse = new Pixel[2][2];
    this.pixelsHouse[0][0] = new Pixel(96, 102, 107);
    this.pixelsHouse[1][0] = new Pixel(63, 66, 57);
    this.pixelsHouse[0][1] = new Pixel(119, 115, 109);
    this.pixelsHouse[1][1] = new Pixel(104, 96, 88);
    this.imageHouse = new ImageImpl(2, 2, 255, pixelsHouse);
  }

  @Test
  public void testPpmToImage() {
    this.initSetting();

    String imagePath = "res/house.ppm";
    Image image = Utils.ppmToImage(imagePath, "house");
    assertEquals(this.imageHouse.getWidth(), image.getWidth());
    assertEquals(this.imageHouse.getHeight(), image.getHeight());
    assertEquals(this.imageHouse.getMaxValue(), image.getMaxValue());
    assertEquals(this.imageHouse.getPixelAt(0, 0), image.getPixelAt(0, 0));
    assertEquals(this.imageHouse.getPixelAt(1, 0), image.getPixelAt(1, 0));
    assertEquals(this.imageHouse.getPixelAt(0, 1), image.getPixelAt(0, 1));
    assertEquals(this.imageHouse.getPixelAt(1, 1), image.getPixelAt(1, 1));
    assertEquals(imageHouse, image);
  }

  @Test
  public void testOthersToImage() {
    this.initSetting();
    // testing with bmp file
    String imagePath = "res/house.bmp";
    Image image = Utils.othersToImage(imagePath, "house");
    assertEquals(this.imageHouse.getWidth(), image.getWidth());
    assertEquals(this.imageHouse.getHeight(), image.getHeight());
    assertEquals(this.imageHouse.getMaxValue(), image.getMaxValue());
    assertEquals(this.imageHouse.getPixelAt(0, 0), image.getPixelAt(0, 0));
    assertEquals(this.imageHouse.getPixelAt(1, 0), image.getPixelAt(1, 0));
    assertEquals(this.imageHouse.getPixelAt(0, 1), image.getPixelAt(0, 1));
    assertEquals(this.imageHouse.getPixelAt(1, 1), image.getPixelAt(1, 1));
    assertEquals(imageHouse, image);
  }

  @Test
  public void testToBufferedImage() {
    BufferedImage houseBuffer = null;

    try {
      houseBuffer = ImageIO.read(new FileInputStream("res/house.bmp"));
    } catch (IOException e) {
      throw new IllegalArgumentException("failed to read the image.");
    }

    Image imageToConvert = Utils.ppmToImage("res/house.ppm", "house");
    BufferedImage convertedImage = Utils.toBufferedImage(imageToConvert, "");
    assertEquals(houseBuffer.getHeight(), convertedImage.getHeight());
    assertEquals(houseBuffer.getWidth(), convertedImage.getWidth());

    for (int i = 0; i < houseBuffer.getWidth(); i++) {
      for (int j = 0; j < houseBuffer.getHeight(); j++) {
        assertEquals(houseBuffer.getRGB(i, j), convertedImage.getRGB(i, j));
      }
    }
  }
}