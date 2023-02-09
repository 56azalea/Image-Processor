package model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Tests for {@link ImageImpl} to check if it initializes correctly
 * and if all the methods inside the {@link ImageImpl} is built accurately.
 */
public class ImageImplTest {
  private IPixel pixel00;
  private IPixel pixel10;
  private IPixel pixel20;
  private IPixel pixel01;
  private IPixel pixel11;
  private IPixel pixel21;
  private IPixel[][] pixels1;
  private Image image1;

  /**
   * An example of an image that is constructed well,
   * without invoking any illegal argument exceptions. This would
   * be used throughout the tests in the future.
   */
  @Before
  public void setup() {
    this.pixel00 = new Pixel(120, 0, 255);
    this.pixel10 = new Pixel(255, 0, 200);
    this.pixel20 = new Pixel(255, 255, 255);
    this.pixel01 = new Pixel(0, 150, 255);
    this.pixel11 = new Pixel(255, 0, 0);
    this.pixel21 = new Pixel(0, 200, 50);

    this.pixels1 = new Pixel[3][2];
    this.pixels1[0][0] = this.pixel00;
    this.pixels1[1][0] = this.pixel10;
    this.pixels1[2][0] = this.pixel20;
    this.pixels1[0][1] = this.pixel01;
    this.pixels1[1][1] = this.pixel11;
    this.pixels1[2][1] = this.pixel21;
    this.image1 = new ImageImpl(2, 3, 255, this.pixels1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorNull1() {
    this.setup();
    new ImageImpl(-1, 3, 255, this.pixels1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorNull2() {
    this.setup();
    new ImageImpl(2, 3, 255, null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorNull3() {
    this.setup();
    new ImageImpl(3, 3, 255, this.pixels1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorNull4() {
    this.setup();
    new ImageImpl(2, 3, 200, this.pixels1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testOutOfBound() {
    this.setup();
    this.image1.getPixelAt(0, 4);
  }

  @Test
  public void testGetDimensions() {
    this.setup();
    // width
    assertEquals(2, this.image1.getWidth());
    // height
    assertEquals(3, this.image1.getHeight());
    // maximum value
    assertEquals(255, this.image1.getMaxValue());
  }

  @Test
  public void testGetPixels() {
    this.setup();
    assertEquals(this.pixels1, this.image1.getPixels());
  }

  @Test
  public void testGetPixelAt() {
    this.setup();
    assertEquals(this.pixel00, this.image1.getPixelAt(0, 0));
    assertEquals(this.pixel10, this.image1.getPixelAt(1, 0));
    assertEquals(this.pixel20, this.image1.getPixelAt(2, 0));
    assertEquals(this.pixel01, this.image1.getPixelAt(0, 1));
    assertEquals(this.pixel11, this.image1.getPixelAt(1, 1));
    assertEquals(this.pixel21, this.image1.getPixelAt(2, 1));
  }
}