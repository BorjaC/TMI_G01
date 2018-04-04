package es.ucm.stenography.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ImageRgbTest {

  private static final String IMAGE_NAME = "src/test/resources/test.bmp";

  private ImageRgb image;

  @Before
  public void setUp() throws Exception {
    image = new ImageRgb(IMAGE_NAME);
  }

  @Test
  public void heightTest() {
    Assert.assertEquals(image.height(), 452);
  }

  @Test
  public void widthTest() {
    Assert.assertEquals(image.width(), 562);
  }

  @Test
  public void getPixelRgbTest() {
    PixelRgb pixel = new PixelRgb(255, 255, 255);
    Assert.assertEquals(image.getPixelRgb(0, 0), pixel);
  }

  @Test
  public void setPixelRgb() {
    PixelRgb pixel = new PixelRgb(120, 0, 122);
    image.setPixelRgb(0, 0, pixel);
    Assert.assertEquals(image.getPixelRgb(0, 0), pixel);
  }

  @Test
  public void hideTest() {
    image.hide(0, 0, 0, 0, 0);
    PixelRgb pixel = new PixelRgb(254, 254, 254);
    Assert.assertEquals(image.getPixelRgb(0, 0), pixel);
  }

  @Test
  public void hideRedTest() {
    image.hideRed(0, 0, 0);
    PixelRgb pixel = new PixelRgb(254, 255, 255);
    Assert.assertEquals(image.getPixelRgb(0, 0), pixel);
  }

  @Test
  public void hideGreenTest() {
    image.hideGreen(0, 0, 0);
    PixelRgb pixel = new PixelRgb(255, 254, 255);
    Assert.assertEquals(image.getPixelRgb(0, 0), pixel);
  }

  @Test
  public void hideBlueTest() {
    image.hideBlue(0, 0, 0);
    PixelRgb pixel = new PixelRgb(255, 255, 254);
    Assert.assertEquals(image.getPixelRgb(0, 0), pixel);
  }

}
