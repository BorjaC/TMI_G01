package es.ucm.stenography.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class PixelRgbTest {

  private PixelRgb pixelWhite;
  private PixelRgb pixelBlack;
  private PixelRgb pixelModified;

  @Before
  public void setUp() {
    pixelWhite = new PixelRgb(255, 255, 255);
    pixelBlack = new PixelRgb(0, 0, 0);
    pixelModified = new PixelRgb(120, 0, 122); // Purple #78007a
    pixelModified.hideRgb(1, 1, 1);
  }

  @Test
  public void getPixelTest() {
    Assert.assertEquals(pixelWhite.getPixel(), 16777215);
    Assert.assertEquals(pixelBlack.getPixel(), 0);
  }

  @Test
  public void hideRedTest() {
    Assert.assertEquals(pixelModified.getRed(), 121);
  }

  @Test
  public void hideGreenTest() {
    Assert.assertEquals(pixelModified.getGreen(), 1);
  }

  @Test
  public void hideBlueTest() {
    Assert.assertEquals(pixelModified.getBlue(), 123);
  }

  @Test
  public void hideRgbTest() {
    Assert.assertEquals(pixelModified.getRed(), 121);
    Assert.assertEquals(pixelModified.getGreen(), 1);
    Assert.assertEquals(pixelModified.getBlue(), 123);
  }

  @Test
  public void extractRedTest() {
    Assert.assertEquals(pixelModified.extractRed(), 1);
  }

  @Test
  public void extractGreenTest() {
    Assert.assertEquals(pixelModified.extractGreen(), 1);
  }

  @Test
  public void extractBlueTest() {
    Assert.assertEquals(pixelModified.extractBlue(), 1);
  }

  @Test
  public void toBinaryStringTest() {
    Assert.assertEquals(pixelModified.toBinaryString(), "111100111111011");
    Assert.assertEquals(pixelWhite.toBinaryString(), "111111111111111111111111");
    Assert.assertEquals(pixelBlack.toBinaryString(), "000");
  }

  @Test
  public void toBinaryStringRgbTest() {
    Assert.assertEquals(pixelModified.toBinaryStringRgb(), "1111001-1-1111011");
    Assert.assertEquals(pixelWhite.toBinaryStringRgb(), "11111111-11111111-11111111");
    Assert.assertEquals(pixelBlack.toBinaryStringRgb(), "0-0-0");
  }

  @Test
  public void toBinaryStringRedTest() {
    Assert.assertEquals(pixelModified.toBinaryStringRed(), "1111001");
    Assert.assertEquals(pixelWhite.toBinaryStringRed(), "11111111");
    Assert.assertEquals(pixelBlack.toBinaryStringRed(), "0");
  }

  @Test
  public void toBinaryStringGreenTest() {
    Assert.assertEquals(pixelModified.toBinaryStringGreen(), "1");
    Assert.assertEquals(pixelWhite.toBinaryStringGreen(), "11111111");
    Assert.assertEquals(pixelBlack.toBinaryStringGreen(), "0");
  }

  @Test
  public void toBinaryStringBlueTest() {
    Assert.assertEquals(pixelModified.toBinaryStringBlue(), "1111011");
    Assert.assertEquals(pixelWhite.toBinaryStringBlue(), "11111111");
    Assert.assertEquals(pixelBlack.toBinaryStringBlue(), "0");
  }

}
