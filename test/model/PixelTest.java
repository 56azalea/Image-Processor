package model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

/**
 * Tests for {@link Pixel} to confirm that the single pixel
 * constructs and operates as it is supposed to do.
 */
public class PixelTest {
  private IPixel pixel1;
  private IPixel pixel2;
  private IPixel pixel3;
  private IPixel pixel4;

  /**
   * Several examples of pixels that are constructed well,
   * without invoking any illegal argument exceptions. This would
   * be used throughout the tests in the future.
   */
  @Before
  public void setup() {
    this.pixel1 = new Pixel(255, 0, 0);
    this.pixel2 = new Pixel(0, 255, 0);
    this.pixel3 = new Pixel(10, 30, 50);
    this.pixel4 = new Pixel(32, 52, 123);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorNeg() {
    new Pixel(-1, 240, 32);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullBrighten() {
    this.pixel1.brighten(0, -20);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullGreyscale() {
    this.pixel1.greyscale(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullColorTrans() {
    this.pixel1.colorTrans(null);
  }

  @Test
  public void testGetRed() {
    this.setup();
    assertEquals(255, this.pixel1.getRed());
    assertEquals(0, this.pixel2.getRed());
    assertEquals(10, this.pixel3.getRed());
    assertEquals(32, this.pixel4.getRed());
  }

  @Test
  public void testGetGreen() {
    this.setup();
    assertEquals(0, this.pixel1.getGreen());
    assertEquals(255, this.pixel2.getGreen());
    assertEquals(30, this.pixel3.getGreen());
    assertEquals(52, this.pixel4.getGreen());
  }

  @Test
  public void testGetBlue() {
    this.setup();
    assertEquals(0, this.pixel1.getBlue());
    assertEquals(0, this.pixel2.getBlue());
    assertEquals(50, this.pixel3.getBlue());
    assertEquals(123, this.pixel4.getBlue());
  }

  @Test
  public void testFilters() {
    this.setup();
    // brightening & darkening
    assertEquals(new Pixel(255, 100, 100),
            this.pixel1.brighten(100, 255));
    assertEquals(new Pixel(90, 255, 90),
            this.pixel2.brighten(90, 255));
    assertEquals(new Pixel(0, 10, 30),
            this.pixel3.brighten(-20, 255));
    assertEquals(new Pixel(30, 50, 121),
            this.pixel4.brighten(-2, 255));
    // greyscale
    assertEquals(new Pixel(255, 255, 255),
            this.pixel1.greyscale(ImageProcessorModel.GreyscaleType.Red));
    assertEquals(new Pixel(0, 0, 0),
            this.pixel1.greyscale(ImageProcessorModel.GreyscaleType.Green));
    assertEquals(new Pixel(0, 0, 0),
            this.pixel2.greyscale(ImageProcessorModel.GreyscaleType.Blue));
    assertEquals(new Pixel(255, 255, 255),
            this.pixel2.greyscale(ImageProcessorModel.GreyscaleType.Value));
    assertEquals(new Pixel(30, 30, 30),
            this.pixel3.greyscale(ImageProcessorModel.GreyscaleType.Intensity));
    assertEquals(new Pixel(52, 52, 52),
            this.pixel4.greyscale(ImageProcessorModel.GreyscaleType.Luma));
    // color transformation
    assertEquals(new Pixel(54, 54, 54),
            this.pixel1.colorTrans(ImageProcessorModelState.ColorTransType.Greyscale));
    assertEquals(new Pixel(196, 174, 136),
            this.pixel2.colorTrans(ImageProcessorModelState.ColorTransType.Sepia));
    assertEquals(new Pixel((int) ((10 * 0.393) + (30 * 0.769) + (50 * 0.189)),
                    (int) ((10 * 0.349) + (30 * 0.686) + (50 * 0.168)),
                    (int) ((10 * 0.272) + (30 * 0.534) + (50 * 0.131))),
            this.pixel3.colorTrans(ImageProcessorModelState.ColorTransType.Sepia));
    assertEquals(new Pixel(52, 52, 52),
            this.pixel4.colorTrans(ImageProcessorModelState.ColorTransType.Greyscale));
  }

  @Test
  public void testOverMaxValue() {
    assertTrue(new Pixel(270, 40, 313).overMaxValue(255));
    assertFalse(new Pixel(20, 40, 255).overMaxValue(255));
  }

  @Test
  public void testEquals() {
    this.setup();
    assertEquals(this.pixel1, new Pixel(255, 0, 0));
    assertEquals(this.pixel2, new Pixel(0, 255, 0));
    assertEquals(this.pixel3, new Pixel(10, 30, 50));
    assertEquals(this.pixel4, new Pixel(32, 52, 123));
  }

  @Test
  public void testHashCode() {
    this.setup();
    assertEquals(274846, this.pixel1.hashCode());
    assertEquals(274846, new Pixel(255, 0, 0).hashCode());
    assertEquals(37696, this.pixel2.hashCode());
    assertEquals(37696, new Pixel(0, 255, 0).hashCode());
  }

  @Test
  public void testGetValues() {
    this.setup();
    int[] rgb = new int[3];
    rgb[0] = 255;
    rgb[1] = 0;
    rgb[2] = 0;
    assertEquals(rgb[0], this.pixel1.getValues()[0]);
    assertEquals(rgb[1], this.pixel1.getValues()[1]);
    assertEquals(rgb[2], this.pixel1.getValues()[2]);
  }
}