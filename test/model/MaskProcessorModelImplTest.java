package model;

import org.junit.Before;
import org.junit.Test;

import static model.ImageProcessorModelState.GreyscaleType;
import static model.ImageProcessorModelState.FilteringType;
import static model.ImageProcessorModelState.ColorTransType;

import static org.junit.Assert.assertEquals;

/**
 * Tests for the new model made for HW08, specifically
 * {@link MaskProcessorModelImpl}. This tests if the mask can
 * be applied on each operation (brighten, multiple greyscale,
 * filtering, color transformation).
 */
public class MaskProcessorModelImplTest {
  private MaskProcessorModel model;
  private IPixel pixel00;
  private IPixel pixel10;
  private IPixel pixel01;
  private IPixel pixel11;
  private Image photo;
  private IPixel[][] maskPixel;

  @Before
  public void setup() {
    this.model = new MaskProcessorModelImpl();
    IPixel[][] pixels = new Pixel[2][2];
    this.pixel00 = new Pixel(120, 0, 255);
    this.pixel10 = new Pixel(255, 0, 200);
    this.pixel01 = new Pixel(255, 255, 255);
    this.pixel11 = new Pixel(0, 150, 255);
    pixels[0][0] = this.pixel00;
    pixels[1][0] = this.pixel10;
    pixels[0][1] = this.pixel01;
    pixels[1][1] = this.pixel11;
    this.photo = new ImageImpl(2, 2, 255, pixels);
    this.model.addImage("house", photo);
    // mask
    this.maskPixel = new Pixel[2][2];
    maskPixel[0][0] = new Pixel(0, 0, 0);
    maskPixel[1][0] = new Pixel(0, 0, 0);
    maskPixel[0][1] = new Pixel(255, 255, 255);
    maskPixel[1][1] = new Pixel(255, 255, 255);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMaskSizeException() {
    this.setup();
    this.model.brighten("house", 10, new Pixel[1][1], "house-mask-brighten");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testBrightenException1() {
    this.setup();
    this.model.brighten(null, 10, maskPixel, "house-mask-brighten");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testBrightenException2() {
    this.setup();
    this.model.brighten("house", 10, null, "house-mask-brighten");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testBrightenException3() {
    this.setup();
    this.model.brighten("house", 10, maskPixel, null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMultipleGreyscaleException1() {
    this.setup();
    this.model.multipleGreyscale(null, GreyscaleType.Red,
            maskPixel, "house-mask-red");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMultipleGreyscaleException2() {
    this.setup();
    this.model.multipleGreyscale("house", GreyscaleType.Green,
            null, "house-mask-green");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMultipleGreyscaleException3() {
    this.setup();
    this.model.multipleGreyscale("house", GreyscaleType.Blue, maskPixel, null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMultipleFilteringException1() {
    this.setup();
    this.model.filtering(null, FilteringType.Blur, maskPixel, "house-mask-blur");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMultipleFilteringException2() {
    this.setup();
    this.model.filtering("house", FilteringType.Blur,
            null, "house-mask-blur");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMultipleFilteringException3() {
    this.setup();
    this.model.filtering("house", FilteringType.Sharpen, maskPixel, null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMultipleColorTransException1() {
    this.setup();
    this.model.colorTransformation(null, ColorTransType.Greyscale,
            maskPixel, "house-mask-greyscale");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMultipleColorTransException2() {
    this.setup();
    this.model.colorTransformation("house", ColorTransType.Greyscale,
            null, "house-mask-greyscale");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMultipleColorTransException3() {
    this.setup();
    this.model.colorTransformation("house", ColorTransType.Sepia,
            maskPixel, null);
  }

  @Test
  public void testBrightenMask() {
    this.setup();
    // applies mask & brighten 100
    IPixel[][] pixelBrighten = new Pixel[2][2];
    pixelBrighten[0][0] = new Pixel(220, 100, 255);
    pixelBrighten[1][0] = new Pixel(255, 100, 255);
    // no changes in pixels at (0,1) & (1,1)
    pixelBrighten[0][1] = new Pixel(255, 255, 255);
    pixelBrighten[1][1] = new Pixel(0, 150, 255);
    Image houseBrightenMask = new ImageImpl(2, 2, 255, pixelBrighten);
    this.model.brighten("house", 100, maskPixel, "house-mask-brighten");

    assertEquals(houseBrightenMask.getPixelAt(0, 0),
            this.model.getImage("house-mask-brighten").getPixelAt(0, 0));
    assertEquals(houseBrightenMask.getPixelAt(1, 0),
            this.model.getImage("house-mask-brighten").getPixelAt(1, 0));
    assertEquals(houseBrightenMask.getPixelAt(0, 1),
            this.model.getImage("house-mask-brighten").getPixelAt(0, 1));
    assertEquals(houseBrightenMask.getPixelAt(1, 1),
            this.model.getImage("house-mask-brighten").getPixelAt(1, 1));
    assertEquals(houseBrightenMask, this.model.getImage("house-mask-brighten"));

    // applies mask & darken 20
    IPixel[][] pixelDarken = new Pixel[2][2];
    pixelDarken[0][0] = new Pixel(100, 0, 235);
    pixelDarken[1][0] = new Pixel(235, 0, 180);
    // no changes in pixels at (0,1) & (1,1)
    pixelDarken[0][1] = new Pixel(255, 255, 255);
    pixelDarken[1][1] = new Pixel(0, 150, 255);
    Image houseDarkenMask = new ImageImpl(2, 2, 255, pixelDarken);
    this.model.brighten("house", -20, maskPixel, "house-mask-darken");

    assertEquals(houseDarkenMask, this.model.getImage("house-mask-darken"));
  }

  @Test
  public void testMultipleGreyscaleMask() {
    this.setup();
    // applies mask & red
    IPixel[][] pixelRed = new Pixel[2][2];
    pixelRed[0][0] = this.pixel00.greyscale(GreyscaleType.Red);
    pixelRed[1][0] = this.pixel10.greyscale(GreyscaleType.Red);
    // no changes in pixels at (0,1) & (1,1)
    pixelRed[0][1] = new Pixel(255, 255, 255);
    pixelRed[1][1] = new Pixel(0, 150, 255);
    Image houseRedMask = new ImageImpl(2, 2, 255, pixelRed);
    this.model.multipleGreyscale("house", GreyscaleType.Red,
            maskPixel, "house-mask-red");

    assertEquals(houseRedMask, this.model.getImage("house-mask-red"));

    // applies mask & green
    IPixel[][] pixelGreen = new Pixel[2][2];
    pixelGreen[0][0] = this.pixel00.greyscale(GreyscaleType.Green);
    pixelGreen[1][0] = this.pixel10.greyscale(GreyscaleType.Green);
    // no changes in pixels at (0,1) & (1,1)
    pixelGreen[0][1] = new Pixel(255, 255, 255);
    pixelGreen[1][1] = new Pixel(0, 150, 255);
    Image houseGreenMask = new ImageImpl(2, 2, 255, pixelGreen);
    this.model.multipleGreyscale("house", GreyscaleType.Green,
            maskPixel, "house-mask-green");

    assertEquals(houseGreenMask, this.model.getImage("house-mask-green"));

    // applies mask & blue
    IPixel[][] pixelBlue = new Pixel[2][2];
    pixelBlue[0][0] = this.pixel00.greyscale(GreyscaleType.Blue);
    pixelBlue[1][0] = this.pixel10.greyscale(GreyscaleType.Blue);
    // no changes in pixels at (0,1) & (1,1)
    pixelBlue[0][1] = new Pixel(255, 255, 255);
    pixelBlue[1][1] = new Pixel(0, 150, 255);
    Image houseBlueMask = new ImageImpl(2, 2, 255, pixelBlue);
    this.model.multipleGreyscale("house", GreyscaleType.Blue,
            maskPixel, "house-mask-blue");

    assertEquals(houseBlueMask, this.model.getImage("house-mask-blue"));

    // applies mask & value
    IPixel[][] pixelValue = new Pixel[2][2];
    pixelValue[0][0] = this.pixel00.greyscale(GreyscaleType.Value);
    pixelValue[1][0] = this.pixel10.greyscale(GreyscaleType.Value);
    // no changes in pixels at (0,1) & (1,1)
    pixelValue[0][1] = new Pixel(255, 255, 255);
    pixelValue[1][1] = new Pixel(0, 150, 255);
    Image houseValueMask = new ImageImpl(2, 2, 255, pixelValue);
    this.model.multipleGreyscale("house", GreyscaleType.Value,
            maskPixel, "house-mask-value");

    assertEquals(houseValueMask, this.model.getImage("house-mask-value"));

    // applies mask & intensity
    IPixel[][] pixelIntensity = new Pixel[2][2];
    pixelIntensity[0][0] = this.pixel00.greyscale(GreyscaleType.Intensity);
    pixelIntensity[1][0] = this.pixel10.greyscale(GreyscaleType.Intensity);
    // no changes in pixels at (0,1) & (1,1)
    pixelIntensity[0][1] = new Pixel(255, 255, 255);
    pixelIntensity[1][1] = new Pixel(0, 150, 255);
    Image houseIntensityMask = new ImageImpl(2, 2, 255, pixelIntensity);
    this.model.multipleGreyscale("house", GreyscaleType.Intensity,
            maskPixel, "house-mask-intensity");

    assertEquals(houseIntensityMask, this.model.getImage("house-mask-intensity"));

    // applies mask & luma
    IPixel[][] pixelLuma = new Pixel[2][2];
    pixelLuma[0][0] = this.pixel00.greyscale(GreyscaleType.Luma);
    pixelLuma[1][0] = this.pixel10.greyscale(GreyscaleType.Luma);
    // no changes in pixels at (0,1) & (1,1)
    pixelLuma[0][1] = new Pixel(255, 255, 255);
    pixelLuma[1][1] = new Pixel(0, 150, 255);
    Image houseLumaMask = new ImageImpl(2, 2, 255, pixelLuma);
    this.model.multipleGreyscale("house", GreyscaleType.Luma,
            maskPixel, "house-mask-luma");

    assertEquals(houseLumaMask, this.model.getImage("house-mask-luma"));
  }

  @Test
  public void testFilteringMask() {
    this.setup();
    // applies mask & blur
    IPixel[][] pixelBlur = new Pixel[2][2];
    pixelBlur[0][0] = new Pixel(30 + (255 / 8) + (255 / 8),
            255 / 8 + (150 / 16), (255 / 4) + (200 / 8) + (255 / 8) + (255 / 16));
    pixelBlur[1][0] = new Pixel((120 / 8) + (255 / 16) + (255 / 4),
            (255 / 16) + (150 / 8), (255 / 8) + (200 / 4) + (255 / 16) + (255 / 8));
    // no changes in pixels at (0,1) & (1,1)
    pixelBlur[0][1] = new Pixel(255, 255, 255);
    pixelBlur[1][1] = new Pixel(0, 150, 255);
    Image houseBlurMask = new ImageImpl(2, 2, 255, pixelBlur);
    this.model.filtering("house", FilteringType.Blur,
            maskPixel, "house-mask-blur");

    assertEquals(houseBlurMask, this.model.getImage("house-mask-blur"));

    // applies mask & sharpen
    IPixel[][] pixelSharpen = new Pixel[2][2];
    pixelSharpen[0][0] = new Pixel(120 + (255 / 4) + (255 / 4),
            (255 / 4) + (150 / 4), Math.min(255 + (200 / 4) + (255 / 4) + (255 / 4), 255));
    pixelSharpen[1][0] = new Pixel(Math.min(30 + 255 + (255 / 4), 255),
            (255 / 4) + (150 / 4), Math.min((255 / 4) + 200 + (255 / 4) + (255 / 4), 255));
    // no changes in pixels at (0,1) & (1,1)
    pixelSharpen[0][1] = new Pixel(255, 255, 255);
    pixelSharpen[1][1] = new Pixel(0, 150, 255);
    Image houseSharpenMask = new ImageImpl(2, 2, 255, pixelSharpen);
    this.model.filtering("house", FilteringType.Sharpen,
            maskPixel, "house-mask-sharpen");

    assertEquals(houseSharpenMask, this.model.getImage("house-mask-sharpen"));
  }

  @Test
  public void testColorTransformationMask() {
    this.setup();
    // applies mask & greyscale
    IPixel[][] pixelGreyscale = new Pixel[2][2];
    pixelGreyscale[0][0] = this.pixel00.colorTrans(ColorTransType.Greyscale);
    pixelGreyscale[1][0] = this.pixel10.colorTrans(ColorTransType.Greyscale);
    // no changes in pixels at (0,1) & (1,1)
    pixelGreyscale[0][1] = new Pixel(255, 255, 255);
    pixelGreyscale[1][1] = new Pixel(0, 150, 255);
    Image houseGreyscaleMask = new ImageImpl(2, 2, 255, pixelGreyscale);
    this.model.colorTransformation("house", ColorTransType.Greyscale,
            maskPixel, "house-mask-greyscale");

    assertEquals(houseGreyscaleMask, this.model.getImage("house-mask-greyscale"));

    // applies mask & sepia
    IPixel[][] pixelSepia = new Pixel[2][2];
    pixelSepia[0][0] = this.pixel00.colorTrans(ColorTransType.Sepia);
    pixelSepia[1][0] = this.pixel10.colorTrans(ColorTransType.Sepia);
    // no changes in pixels at (0,1) & (1,1)
    pixelSepia[0][1] = new Pixel(255, 255, 255);
    pixelSepia[1][1] = new Pixel(0, 150, 255);
    Image houseSepiaMask = new ImageImpl(2, 2, 255, pixelSepia);
    this.model.colorTransformation("house", ColorTransType.Sepia,
            maskPixel, "house-mask-sepia");

    assertEquals(houseSepiaMask, this.model.getImage("house-mask-sepia"));
  }
}