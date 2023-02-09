package controller;

import org.junit.Before;
import org.junit.Test;

import java.io.StringReader;

import model.IPixel;
import model.Image;
import model.ImageImpl;
import model.ImageProcessorModelState;
import model.MaskProcessorModel;
import model.MaskProcessorModelImpl;
import model.Pixel;
import view.ImageProcessorGUIView;
import view.ImageProcessorGUIViewImpl;

import static org.junit.Assert.assertEquals;

/**
 * Tests for {@link GUIController} to check if it initializes correctly
 * and if all the methods inside the {@link GUIController} is built accurately.
 */
public class GUIControllerTest {
  private MaskProcessorModel model;
  private Readable input;
  private Controller controller;
  private Features feature;
  private IPixel[][] pixelsHouse;
  private Image imageHouse;

  /**
   * An example of an GUI controller that is constructed well,
   * without invoking any illegal argument exceptions. This would
   * be used throughout the tests in the future.
   */
  @Before
  public void setup() {
    this.model = new MaskProcessorModelImpl();
    this.input = new StringReader("load res/house.ppm house\n");
    this.controller = new ControllerImpl(this.model, this.input);
    ImageProcessorGUIView view = new ImageProcessorGUIViewImpl();
    this.feature = new GUIController(this.model, view);
    this.pixelsHouse = new Pixel[2][2];
    this.pixelsHouse[0][0] = new Pixel(96, 102, 107);
    this.pixelsHouse[1][0] = new Pixel(63, 66, 57);
    this.pixelsHouse[0][1] = new Pixel(119, 115, 109);
    this.pixelsHouse[1][1] = new Pixel(104, 96, 88);
    this.imageHouse = new ImageImpl(2, 2, 255, pixelsHouse);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testLoadNullConstructor() {
    this.setup();
    this.feature.load(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSaveNullImagePath() {
    this.setup();
    this.feature.save(null, "image");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSaveNullImageName() {
    this.setup();
    this.feature.save("res/house.ppm", null);
  }

  @Test
  public void testLoad() {
    this.setup();
    // loads house.ppm from res folder
    this.controller.load("res/house.ppm", "house");
    assertEquals(this.imageHouse.getWidth(), this.model.getImage("house").getWidth());
    assertEquals(this.imageHouse.getHeight(), this.model.getImage("house").getHeight());
    assertEquals(this.imageHouse.getMaxValue(),
            this.model.getImage("house").getMaxValue());
    assertEquals(this.imageHouse.getPixelAt(0, 0),
            this.model.getImage("house").getPixelAt(0, 0));
    assertEquals(this.imageHouse.getPixelAt(1, 0),
            this.model.getImage("house").getPixelAt(1, 0));
    assertEquals(this.imageHouse.getPixelAt(0, 1),
            this.model.getImage("house").getPixelAt(0, 1));
    assertEquals(this.imageHouse.getPixelAt(1, 1),
            this.model.getImage("house").getPixelAt(1, 1));
    assertEquals(this.imageHouse, this.model.getImage("house"));
    // loads house.bmp from res folder
    this.controller.load("res/house.bmp", "house2");
    assertEquals(this.imageHouse.getWidth(), this.model.getImage("house2").getWidth());
    assertEquals(this.imageHouse.getHeight(), this.model.getImage("house2").getHeight());
    assertEquals(this.imageHouse.getMaxValue(),
            this.model.getImage("house2").getMaxValue());
    assertEquals(this.imageHouse.getPixelAt(0, 0),
            this.model.getImage("house2").getPixelAt(0, 0));
    assertEquals(this.imageHouse.getPixelAt(1, 0),
            this.model.getImage("house2").getPixelAt(1, 0));
    assertEquals(this.imageHouse.getPixelAt(0, 1),
            this.model.getImage("house2").getPixelAt(0, 1));
    assertEquals(this.imageHouse.getPixelAt(1, 1),
            this.model.getImage("house2").getPixelAt(1, 1));
    assertEquals(this.imageHouse, this.model.getImage("house2"));
  }

  @Test
  public void testSave() {
    this.setup();
    // loads house.ppm first
    this.controller.load("res/house.ppm", "house");
    assertEquals(this.imageHouse, this.model.getImage("house"));
    // now saves a new version
    Image house2 = new ImageImpl(2, 2, 255, pixelsHouse);
    model.addImage("house2", house2);
    this.controller.save("res/house2.ppm", "house2"); // file is made
    assertEquals(house2, this.model.getImage("house2"));
    // now makes one change and save that
    IPixel[][] pixelHorizontal = new Pixel[2][2];
    pixelHorizontal[0][0] = this.pixelsHouse[0][1];
    pixelHorizontal[1][0] = this.pixelsHouse[1][1];
    pixelHorizontal[0][1] = this.pixelsHouse[0][0];
    pixelHorizontal[1][1] = this.pixelsHouse[1][0];
    Image photo1Horizontal = new ImageImpl(2, 2, 255, pixelHorizontal);
    this.model.flip("house",
            ImageProcessorModelState.FlipType.Horizontal, "houseHorizontal");
    // saving as ppm
    this.controller.save("res/houseHorizontal.ppm",
            "houseHorizontal"); // file is made
    // testing different image formats
    // jpg
    this.controller.save("res/house.jpg", "house");
    this.controller.save("res/houseHorizontal.jpg", "houseHorizontal");
    // png
    this.controller.save("res/house.png", "house");
    this.controller.save("res/houseHorizontal.png", "houseHorizontal");
    // bmp
    this.controller.save("res/house.bmp", "house");
    this.controller.save("res/houseHorizontal.bmp", "houseHorizontal");

    assertEquals(photo1Horizontal, this.model.getImage("houseHorizontal"));
  }

  @Test
  public void testBrighten() {
    this.setup();
    IPixel[][] pixelBrighten = new Pixel[2][2];
    pixelBrighten[0][0] = new Pixel(146, 152, 157);
    pixelBrighten[1][0] = new Pixel(113, 116, 107);
    pixelBrighten[0][1] = new Pixel(169, 165, 159);
    pixelBrighten[1][1] = new Pixel(154, 146, 138);
    Image imageBrighten = new ImageImpl(2, 2, 255, pixelBrighten);

    this.input = new StringReader("load res/house.ppm house\n" +
            "brighten house 50 house-brighten\n" +
            "save res/house-brighten.ppm house-brighten\n q");
    this.controller = new ControllerImpl(this.model, this.input);
    this.controller.runProcessor();

    assertEquals(imageBrighten, this.model.getImage("house-brighten"));
  }

  @Test
  public void testDarken() {
    this.setup();
    IPixel[][] pixelDarken = new Pixel[2][2];
    pixelDarken[0][0] = new Pixel(46, 52, 57);
    pixelDarken[1][0] = new Pixel(13, 16, 7);
    pixelDarken[0][1] = new Pixel(69, 65, 59);
    pixelDarken[1][1] = new Pixel(54, 46, 38);
    Image imageDarken = new ImageImpl(2, 2, 255, pixelDarken);

    this.input = new StringReader("load res/house.ppm house\n" +
            "brighten house -50 house-darken\n" +
            "save res/house-darken.ppm house-darken\n q");
    this.controller = new ControllerImpl(this.model, this.input);
    this.controller.runProcessor();

    assertEquals(imageDarken, this.model.getImage("house-darken"));
  }

  @Test
  public void testRedComponent() {
    this.setup();
    IPixel[][] pixelRed = new Pixel[2][2];
    pixelRed[0][0] = new Pixel(96, 96, 96);
    pixelRed[1][0] = new Pixel(63, 63, 63);
    pixelRed[0][1] = new Pixel(119, 119, 119);
    pixelRed[1][1] = new Pixel(104, 104, 104);
    Image imageRed = new ImageImpl(2, 2, 255, pixelRed);

    this.input = new StringReader("load res/house.ppm house\n" +
            "red-component house house-red\n" +
            "save res/house-red.ppm house-red\n q");
    this.controller = new ControllerImpl(this.model, this.input);
    this.controller.runProcessor();

    assertEquals(imageRed, this.model.getImage("house-red"));
  }

  @Test
  public void testGreenComponent() {
    this.setup();
    IPixel[][] pixelGreen = new Pixel[2][2];
    pixelGreen[0][0] = new Pixel(102, 102, 102);
    pixelGreen[1][0] = new Pixel(66, 66, 66);
    pixelGreen[0][1] = new Pixel(115, 115, 115);
    pixelGreen[1][1] = new Pixel(96, 96, 96);
    Image imageGreen = new ImageImpl(2, 2, 255, pixelGreen);

    this.input = new StringReader("load res/house.ppm house\n" +
            "green-component house house-green\n" +
            "save res/house-green.ppm house-green\n q");
    this.controller = new ControllerImpl(this.model, this.input);
    this.controller.runProcessor();

    assertEquals(imageGreen, this.model.getImage("house-green"));
  }

  @Test
  public void testBlueComponent() {
    this.setup();
    IPixel[][] pixelBlue = new Pixel[2][2];
    pixelBlue[0][0] = new Pixel(107, 107, 107);
    pixelBlue[1][0] = new Pixel(57, 57, 57);
    pixelBlue[0][1] = new Pixel(109, 109, 109);
    pixelBlue[1][1] = new Pixel(88, 88, 88);
    Image imageBlue = new ImageImpl(2, 2, 255, pixelBlue);

    this.input = new StringReader("load res/house.ppm house\n" +
            "blue-component house house-blue\n" +
            "save res/house-blue.ppm house-blue\n q");
    this.controller = new ControllerImpl(this.model, this.input);
    this.controller.runProcessor();

    assertEquals(imageBlue, this.model.getImage("house-blue"));
  }

  @Test
  public void testValueComponent() {
    this.setup();
    IPixel[][] pixelValue = new Pixel[2][2];
    pixelValue[0][0] = new Pixel(107, 107, 107);
    pixelValue[1][0] = new Pixel(66, 66, 66);
    pixelValue[0][1] = new Pixel(119, 119, 119);
    pixelValue[1][1] = new Pixel(104, 104, 104);
    Image imageValue = new ImageImpl(2, 2, 255, pixelValue);

    this.input = new StringReader("load res/house.ppm house\n" +
            "value-component house house-value\n" +
            "save res/house-value.ppm house-value\n q");
    this.controller = new ControllerImpl(this.model, this.input);
    this.controller.runProcessor();

    assertEquals(imageValue, this.model.getImage("house-value"));
  }

  @Test
  public void testIntensityComponent() {
    this.setup();
    IPixel[][] pixelIntensity = new Pixel[2][2];
    pixelIntensity[0][0] = new Pixel(101, 101, 101);
    pixelIntensity[1][0] = new Pixel(62, 62, 62);
    pixelIntensity[0][1] = new Pixel(114, 114, 114);
    pixelIntensity[1][1] = new Pixel(96, 96, 96);
    Image imageIntensity = new ImageImpl(2, 2, 255, pixelIntensity);

    this.input = new StringReader("load res/house.ppm house\n" +
            "intensity-component house house-intensity\n" +
            "save res/house-intensity.ppm house-intensity\n q");
    this.controller = new ControllerImpl(this.model, this.input);
    this.controller.runProcessor();

    assertEquals(imageIntensity, this.model.getImage("house-intensity"));
  }

  @Test
  public void testLumaComponent() {
    this.setup();
    IPixel[][] pixelLuma = new Pixel[2][2];
    pixelLuma[0][0] = new Pixel(101, 101, 101);
    pixelLuma[1][0] = new Pixel(64, 64, 64);
    pixelLuma[0][1] = new Pixel(115, 115, 115);
    pixelLuma[1][1] = new Pixel(97, 97, 97);
    Image imageLuma = new ImageImpl(2, 2, 255, pixelLuma);

    this.input = new StringReader("load res/house.ppm house\n" +
            "luma-component house house-luma\n" +
            "save res/house-luma.ppm house-luma\n q");
    this.controller = new ControllerImpl(this.model, this.input);
    this.controller.runProcessor();

    assertEquals(imageLuma, this.model.getImage("house-luma"));
  }

  @Test
  public void testHorizontalFlip() {
    this.setup();
    IPixel[][] pixelHorizontal = new Pixel[2][2];
    pixelHorizontal[0][0] = new Pixel(119, 115, 109);
    pixelHorizontal[1][0] = new Pixel(104, 96, 88);
    pixelHorizontal[0][1] = new Pixel(96, 102, 107);
    pixelHorizontal[1][1] = new Pixel(63, 66, 57);
    Image imageHorizontal = new ImageImpl(2, 2, 255, pixelHorizontal);

    this.input = new StringReader("load res/house.ppm house\n" +
            "horizontal-flip house house-horizontal\n" +
            "save res/house-horizontal.ppm house-horizontal\n q");
    this.controller = new ControllerImpl(this.model, this.input);
    this.controller.runProcessor();

    assertEquals(imageHorizontal, this.model.getImage("house-horizontal"));
  }

  @Test
  public void testBlur() {
    this.setup();
    IPixel[][] pixelBlur = new Pixel[2][2];
    pixelBlur[0][0] = new Pixel((96 / 4) + (63 / 8) + (119 / 8) + (104 / 16),
            (102 / 4) + (66 / 8) + (115 / 8) + (96 / 16),
            (107 / 4) + (57 / 8) + (109 / 8) + (88 / 16));
    pixelBlur[1][0] = new Pixel((96 / 8) + (63 / 4) + (119 / 16) + (104 / 8),
            (102 / 8) + (66 / 4) + (115 / 16) + (96 / 8),
            (107 / 8) + (57 / 4) + (109 / 16) + (88 / 8));
    pixelBlur[0][1] = new Pixel((96 / 8) + (63 / 16) + (119 / 4) + (104 / 8),
            (102 / 8) + (66 / 16) + (115 / 4) + (96 / 8),
            (107 / 8) + (57 / 16) + (109 / 4) + (88 / 8));
    pixelBlur[1][1] = new Pixel((96 / 16) + (63 / 8) + (119 / 8) + (104 / 4),
            (102 / 16) + (66 / 8) + (115 / 8) + (96 / 4),
            (107 / 16) + (57 / 8) + (109 / 8) + (88 / 4));
    Image imageBlur = new ImageImpl(2, 2, 255, pixelBlur);

    this.input = new StringReader("load res/house.ppm house\n" +
            "blur house house-blur\n" +
            "save res/house-blur.ppm house-blur\n q");
    this.controller = new ControllerImpl(this.model, this.input);
    this.controller.runProcessor();

    assertEquals(imageBlur, this.model.getImage("house-blur"));
  }

  @Test
  public void testSharpen() {
    this.setup();
    IPixel[][] pixelSharpen = new Pixel[2][2];
    pixelSharpen[0][0] = new Pixel(96 + (63 / 4) + (119 / 4) + (104 / 4),
            102 + (66 / 4) + (115 / 4) + (96 / 4),
            107 + (57 / 4) + (109 / 4) + (88 / 4));
    pixelSharpen[1][0] = new Pixel((96 / 4) + 63 + (119 / 4) + (104 / 4),
            (102 / 4) + 66 + (115 / 4) + (96 / 4),
            (107 / 4) + 57 + (109 / 4) + (88 / 4));
    pixelSharpen[0][1] = new Pixel((96 / 4) + (63 / 4) + 119 + (104 / 4),
            (102 / 4) + (66 / 4) + 115 + (96 / 4),
            (107 / 4) + (57 / 4) + 109 + (88 / 4));
    pixelSharpen[1][1] = new Pixel((96 / 4) + (63 / 4) + (119 / 4) + 104,
            (102 / 4) + (66 / 4) + (115 / 4) + 96,
            (107 / 4) + (57 / 4) + (109 / 4) + 88);
    Image imageBlur = new ImageImpl(2, 2, 255, pixelSharpen);

    this.input = new StringReader("load res/house.ppm house\n" +
            "sharpen house house-sharpen\n" +
            "save res/house-sharpen.ppm house-sharpen\n q");
    this.controller = new ControllerImpl(this.model, this.input);
    this.controller.runProcessor();

    assertEquals(imageBlur, this.model.getImage("house-sharpen"));
  }

  @Test
  public void testGreyscale() {
    this.setup();
    IPixel[][] pixelGreyscale = new Pixel[2][2];
    pixelGreyscale[0][0] = new Pixel(101, 101, 101);
    pixelGreyscale[1][0] = new Pixel(64, 64, 64);
    pixelGreyscale[0][1] = new Pixel(115, 115, 115);
    pixelGreyscale[1][1] = new Pixel(97, 97, 97);
    Image imageGreyscale = new ImageImpl(2, 2, 255, pixelGreyscale);

    this.input = new StringReader("load res/house.ppm house\n" +
            "greyscale house house-greyscale\n" +
            "save res/house-greyscale.ppm house-greyscale\n q");
    this.controller = new ControllerImpl(this.model, this.input);
    this.controller.runProcessor();

    assertEquals(imageGreyscale, this.model.getImage("house-greyscale"));
  }

  @Test
  public void testSepia() {
    this.setup();
    IPixel[][] pixelSepia = new Pixel[2][2];
    pixelSepia[0][0] = this.pixelsHouse[0][0].colorTrans(
            ImageProcessorModelState.ColorTransType.Sepia);
    pixelSepia[1][0] = this.pixelsHouse[1][0].colorTrans(
            ImageProcessorModelState.ColorTransType.Sepia);
    pixelSepia[0][1] = this.pixelsHouse[0][1].colorTrans(
            ImageProcessorModelState.ColorTransType.Sepia);
    pixelSepia[1][1] = this.pixelsHouse[1][1].colorTrans(
            ImageProcessorModelState.ColorTransType.Sepia);
    Image imageSepia = new ImageImpl(2, 2, 255, pixelSepia);

    this.input = new StringReader("load res/house.ppm house\n" +
            "sepia house house-sepia\n" +
            "save res/house-sepia.ppm house-sepia\n q");
    this.controller = new ControllerImpl(this.model, this.input);
    this.controller.runProcessor();

    assertEquals(imageSepia, this.model.getImage("house-sepia"));
  }

  @Test
  public void testDownscale() {
    this.setup();
    IPixel[][] pixelDownscale = new Pixel[1][1];
    pixelDownscale[0][0] = this.pixelsHouse[0][0];
    Image imageDownscale = new ImageImpl(1, 1, 255, pixelDownscale);

    this.input = new StringReader("load res/house.ppm house\n" +
            "downscale 0.5 0.5 house house-downscale\n" +
            "save res/house-downscale.ppm house-downscale\n q");
    this.controller = new ControllerImpl(this.model, this.input);
    this.controller.runProcessor();

    assertEquals(imageDownscale, this.model.getImage("house-downscale"));
  }
}