package model;

import org.junit.Before;
import org.junit.Test;

import static model.ImageProcessorModelState.GreyscaleType;
import static model.ImageProcessorModelState.FilteringType;
import static model.ImageProcessorModelState.ColorTransType;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Tests for {@link ImageProcessorModelImpl}, to check if
 * the constructor works appropriately and if the methods
 * of photos work correctly.
 */
public class ImageProcessorModelImplTest {
  private ImageProcessorModel model;
  private IPixel pixel00;
  private IPixel pixel10;
  private IPixel pixel01;
  private IPixel pixel11;
  private Image photo;

  /**
   * An example of an image processor that is constructed well,
   * without invoking any illegal argument exceptions. This would
   * be used throughout the tests in the future.
   */
  @Before
  public void setup() {
    this.model = new ImageProcessorModelImpl();
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
    this.model.addImage("photo1", photo);
  }

  @Test
  public void testGetImage() {
    assertEquals(this.photo, this.model.getImage().get("photo1"));
    assertEquals(this.photo, this.model.getImage("photo1"));
    assertNull(this.model.getImage("photo2"));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testFlipNullImageTitle() {
    this.model.flip(null, ImageProcessorModelState.FlipType.Vertical,
            "photo1 Vertical");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testFlipNullDest() {
    this.model.flip("photo1", ImageProcessorModelState.FlipType.Vertical, null);
  }

  @Test
  public void testFlip() {
    this.setup();
    // horizontal
    IPixel[][] pixelHorizontal = new Pixel[2][2];
    pixelHorizontal[0][0] = this.pixel01;
    pixelHorizontal[1][0] = this.pixel11;
    pixelHorizontal[0][1] = this.pixel00;
    pixelHorizontal[1][1] = this.pixel10;
    Image photo1Horizontal = new ImageImpl(2, 2, 255, pixelHorizontal);
    this.model.flip("photo1",
            ImageProcessorModelState.FlipType.Horizontal, "photo1 Horizontal");
    assertEquals(photo1Horizontal, this.model.getImage("photo1 Horizontal"));

    // vertical
    IPixel[][] pixelVertical = new Pixel[2][2];
    pixelVertical[0][0] = this.pixel10;
    pixelVertical[1][0] = this.pixel00;
    pixelVertical[0][1] = this.pixel11;
    pixelVertical[1][1] = this.pixel01;
    Image photo1Vertical = new ImageImpl(2, 2, 255, pixelVertical);
    this.model.flip("photo1",
            ImageProcessorModelState.FlipType.Vertical, "photo1 Vertical");
    assertEquals(photo1Vertical, this.model.getImage("photo1 Vertical"));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testBrightenNullImageTitle() {
    this.model.brighten(null, 10, "photo1 Vertical");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testBrightenNullDest() {
    this.model.brighten("photo1", 10, null);
  }

  @Test
  public void testBrighten() {
    this.setup();
    // brighten by 10
    IPixel[][] pixelBrighten = new Pixel[2][2];
    pixelBrighten[0][0] = new Pixel(130, 10, 255);
    pixelBrighten[1][0] = new Pixel(255, 10, 210);
    pixelBrighten[0][1] = new Pixel(255, 255, 255);
    pixelBrighten[1][1] = new Pixel(10, 160, 255);
    Image photo1Brighten = new ImageImpl(2, 2, 255, pixelBrighten);
    this.model.brighten("photo1", 10, "photo1 Brighten");
    assertEquals(photo1Brighten, this.model.getImage("photo1 Brighten"));

    // darken by 20
    IPixel[][] pixelDarken = new Pixel[2][2];
    pixelDarken[0][0] = new Pixel(100, 0, 235);
    pixelDarken[1][0] = new Pixel(235, 0, 180);
    pixelDarken[0][1] = new Pixel(235, 235, 235);
    pixelDarken[1][1] = new Pixel(0, 130, 235);
    Image photo1Darken = new ImageImpl(2, 2, 255, pixelDarken);
    this.model.brighten("photo1", -20, "photo1 Darken");
    assertEquals(photo1Darken, this.model.getImage("photo1 Darken"));
  }

  @Test
  public void testFlipAndBrighten() {
    this.setup();
    // brighten by 40
    IPixel[][] pixelBrighten = new Pixel[2][2];
    pixelBrighten[0][0] = new Pixel(160, 40, 255);
    pixelBrighten[1][0] = new Pixel(255, 40, 240);
    pixelBrighten[0][1] = new Pixel(255, 255, 255);
    pixelBrighten[1][1] = new Pixel(40, 190, 255);
    Image photo1Brighten = new ImageImpl(2, 2, 255, pixelBrighten);
    this.model.brighten("photo1", 40, "photo1 Brighten");

    // flip horizontally
    IPixel[][] pixelHorizontal = new Pixel[2][2];
    pixelHorizontal[0][0] = pixelBrighten[0][1];
    pixelHorizontal[1][0] = pixelBrighten[1][1];
    pixelHorizontal[0][1] = pixelBrighten[0][0];
    pixelHorizontal[1][1] = pixelBrighten[1][0];
    Image photo1BrightenFlip = new ImageImpl(2, 2, 255, pixelHorizontal);
    this.model.flip("photo1 Brighten",
            ImageProcessorModelState.FlipType.Horizontal, "photo1 Brighten Flip");
    assertEquals(photo1BrightenFlip, this.model.getImage("photo1 Brighten Flip"));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMultipleGreyscaleNullImageTitle() {
    this.model.multipleGreyscale(null, GreyscaleType.Red, "photo1 Red Component");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMultipleGreyscaleNullDest() {
    this.model.multipleGreyscale("photo1", GreyscaleType.Green, null);
  }

  @Test
  public void testMultipleGreyscale() {
    this.setup();
    // Red
    IPixel[][] pixelRed = new Pixel[2][2];
    pixelRed[0][0] = this.pixel00.greyscale(GreyscaleType.Red);
    pixelRed[1][0] = this.pixel10.greyscale(GreyscaleType.Red);
    pixelRed[0][1] = this.pixel01.greyscale(GreyscaleType.Red);
    pixelRed[1][1] = this.pixel11.greyscale(GreyscaleType.Red);
    Image photo1Red = new ImageImpl(2, 2, 255, pixelRed);
    this.model.multipleGreyscale("photo1",
            GreyscaleType.Red, "photo1 Red Component");
    assertEquals(photo1Red, this.model.getImage("photo1 Red Component"));

    // Green
    IPixel[][] pixelGreen = new Pixel[2][2];
    pixelGreen[0][0] = this.pixel00.greyscale(GreyscaleType.Green);
    pixelGreen[1][0] = this.pixel10.greyscale(GreyscaleType.Green);
    pixelGreen[0][1] = this.pixel01.greyscale(GreyscaleType.Green);
    pixelGreen[1][1] = this.pixel11.greyscale(GreyscaleType.Green);
    Image photo1Green = new ImageImpl(2, 2, 255, pixelGreen);
    this.model.multipleGreyscale("photo1",
            GreyscaleType.Green, "photo1 Green Component");
    assertEquals(photo1Green, this.model.getImage("photo1 Green Component"));

    // Blue
    IPixel[][] pixelBlue = new Pixel[2][2];
    pixelBlue[0][0] = this.pixel00.greyscale(GreyscaleType.Blue);
    pixelBlue[1][0] = this.pixel10.greyscale(GreyscaleType.Blue);
    pixelBlue[0][1] = this.pixel01.greyscale(GreyscaleType.Blue);
    pixelBlue[1][1] = this.pixel11.greyscale(GreyscaleType.Blue);
    Image photo1Blue = new ImageImpl(2, 2, 255, pixelBlue);
    this.model.multipleGreyscale("photo1",
            GreyscaleType.Blue, "photo1 Blue Component");
    assertEquals(photo1Blue, this.model.getImage("photo1 Blue Component"));

    // Value
    IPixel[][] pixelValue = new Pixel[2][2];
    pixelValue[0][0] = this.pixel00.greyscale(GreyscaleType.Value);
    pixelValue[1][0] = this.pixel10.greyscale(GreyscaleType.Value);
    pixelValue[0][1] = this.pixel01.greyscale(GreyscaleType.Value);
    pixelValue[1][1] = this.pixel11.greyscale(GreyscaleType.Value);
    Image photo1Value = new ImageImpl(2, 2, 255, pixelValue);
    this.model.multipleGreyscale("photo1",
            GreyscaleType.Value, "photo1 Value Component");
    assertEquals(photo1Value, this.model.getImage("photo1 Value Component"));

    // Intensity
    IPixel[][] pixelIntensity = new Pixel[2][2];
    pixelIntensity[0][0] = this.pixel00.greyscale(GreyscaleType.Intensity);
    pixelIntensity[1][0] = this.pixel10.greyscale(GreyscaleType.Intensity);
    pixelIntensity[0][1] = this.pixel01.greyscale(GreyscaleType.Intensity);
    pixelIntensity[1][1] = this.pixel11.greyscale(GreyscaleType.Intensity);
    Image photo1Intensity = new ImageImpl(2, 2, 255, pixelIntensity);
    this.model.multipleGreyscale("photo1",
            GreyscaleType.Intensity, "photo1 Intensity Component");
    assertEquals(photo1Intensity, this.model.getImage("photo1 Intensity Component"));

    // Luma
    IPixel[][] pixelLuma = new Pixel[2][2];
    pixelLuma[0][0] = this.pixel00.greyscale(GreyscaleType.Luma);
    pixelLuma[1][0] = this.pixel10.greyscale(GreyscaleType.Luma);
    pixelLuma[0][1] = this.pixel01.greyscale(GreyscaleType.Luma);
    pixelLuma[1][1] = this.pixel11.greyscale(GreyscaleType.Luma);
    Image photo1Luma = new ImageImpl(2, 2, 255, pixelLuma);
    this.model.multipleGreyscale("photo1",
            GreyscaleType.Luma, "photo1 Luma Component");
    assertEquals(photo1Luma, this.model.getImage("photo1 Luma Component"));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testFilteringNullImageTitle() {
    this.model.filtering(null, FilteringType.Blur, "photo1 Blur");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testFilteringNullDest() {
    this.model.filtering("photo1", FilteringType.Sharpen, null);
  }

  @Test
  public void testFiltering() {
    this.setup();
    // Blur
    IPixel[][] pixelBlur = new Pixel[2][2];
    pixelBlur[0][0] = new Pixel(30 + (255 / 8) + (255 / 8),
            255 / 8 + (150 / 16), (255 / 4) + (200 / 8) + (255 / 8) + (255 / 16));
    pixelBlur[1][0] = new Pixel((120 / 8) + (255 / 16) + (255 / 4),
            (255 / 16) + (150 / 8), (255 / 8) + (200 / 4) + (255 / 16) + (255 / 8));
    pixelBlur[0][1] = new Pixel((120 / 8) + (255 / 16) + (255 / 4),
            (255 / 4) + (150 / 8), (255 / 8) + (200 / 16) + (255 / 4) + (255 / 8));
    pixelBlur[1][1] = new Pixel((120 / 16) + (255 / 8) + (255 / 8),
            (255 / 8) + (150 / 4), (255 / 16) + (200 / 8) + (255 / 8) + (255 / 4));
    Image photo1Blur = new ImageImpl(2, 2, 255, pixelBlur);
    this.model.filtering("photo1", FilteringType.Blur, "photo1 Blur");
    assertEquals(photo1Blur, this.model.getImage("photo1 Blur"));

    // Sharpen
    IPixel[][] pixelSharpen = new Pixel[2][2];
    pixelSharpen[0][0] = new Pixel(120 + (255 / 4) + (255 / 4),
            (255 / 4) + (150 / 4), Math.min(255 + (200 / 4) + (255 / 4) + (255 / 4), 255));
    pixelSharpen[1][0] = new Pixel(Math.min(30 + 255 + (255 / 4), 255),
            (255 / 4) + (150 / 4), Math.min((255 / 4) + 200 + (255 / 4) + (255 / 4), 255));
    pixelSharpen[0][1] = new Pixel(Math.min(30 + (255 / 4) + 255, 255),
            Math.min(255 + (150 / 4), 255), Math.min((255 / 4) + (200 / 4) + 255 + (255 / 4), 255));
    pixelSharpen[1][1] = new Pixel(30 + (255 / 4) + (255 / 4),
            (255 / 4) + 150, Math.min((255 / 4) + (200 / 4) + (255 / 4) + 255, 255));
    Image photo1Sharpen = new ImageImpl(2, 2, 255, pixelSharpen);
    this.model.filtering("photo1", FilteringType.Sharpen, "photo1 Sharpen");
    assertEquals(photo1Sharpen, this.model.getImage("photo1 Sharpen"));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testColorTransNullImageTitle() {
    this.model.colorTransformation(null, ColorTransType.Sepia, "photo1 Sepia");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testColorTransNullDest() {
    this.model.colorTransformation("photo1", ColorTransType.Greyscale, null);
  }

  @Test
  public void testColorTransformation() {
    this.setup();
    // Greyscale
    IPixel[][] pixelGreyscale = new Pixel[2][2];
    pixelGreyscale[0][0] = this.pixel00.colorTrans(ColorTransType.Greyscale);
    pixelGreyscale[1][0] = this.pixel10.colorTrans(ColorTransType.Greyscale);
    pixelGreyscale[0][1] = this.pixel01.colorTrans(ColorTransType.Greyscale);
    pixelGreyscale[1][1] = this.pixel11.colorTrans(ColorTransType.Greyscale);
    Image photo1Greyscale = new ImageImpl(2, 2, 255, pixelGreyscale);
    this.model.colorTransformation("photo1", ColorTransType.Greyscale,
            "photo1 Greyscale");
    assertEquals(photo1Greyscale, this.model.getImage("photo1 Greyscale"));

    // Sepia
    IPixel[][] pixelSepia = new Pixel[2][2];
    pixelSepia[0][0] = this.pixel00.colorTrans(ColorTransType.Sepia);
    pixelSepia[1][0] = this.pixel10.colorTrans(ColorTransType.Sepia);
    pixelSepia[0][1] = this.pixel01.colorTrans(ColorTransType.Sepia);
    pixelSepia[1][1] = this.pixel11.colorTrans(ColorTransType.Sepia);
    Image photo1Sepia = new ImageImpl(2, 2, 255, pixelSepia);
    this.model.colorTransformation("photo1", ColorTransType.Sepia,
            "photo1 Sepia");
    assertEquals(photo1Sepia, this.model.getImage("photo1 Sepia"));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testDownscaleNullImageTitle() {
    this.model.downscale(null, 0.5, 0.5, "photo1 Downscale");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testDownscaleNullDest() {

    this.model.downscale("photo1", 0.5, 0.5, null);
  }

  @Test
  public void testDownscale() {
    this.setup();
    IPixel[][] pixelDownscale = new Pixel[1][1];
    pixelDownscale[0][0] = this.pixel00;
    Image photo1Downscale = new ImageImpl(1, 1, 255, pixelDownscale);
    this.model.downscale("photo1", 0.5, 0.5, "photo1 Downscale");
    assertEquals(photo1Downscale, this.model.getImage("photo1 Downscale"));
  }
}