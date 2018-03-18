package es.ucm.stenography;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import es.ucm.stenography.exception.IncorrectSizeException;
import es.ucm.stenography.model.Coordinate;
import es.ucm.stenography.model.ImageRgb;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

public class StenographyTest {

  private static final String BMP_TYPE = "bmp";
  private static final String IMAGE_MODIFIED = "modified.bmp";
  private static final String IMAGE_NAME = "src/test/resources/test.bmp";

  private ImageRgb image;
  private char insert;
  private List<Coordinate<Integer, Integer>> coordinates;

  @Before
  public void setUp() throws Exception {
    insert = (char) Math.floor(Math.random() * (32 - 126 + 1) + 126);
    image = new ImageRgb(IMAGE_NAME);

    coordinates = new ArrayList<>();
    int i = 0;
    while (i < 8) {
      int x = (int) Math.floor(Math.random() * image.width());
      int y = (int) Math.floor(Math.random() * image.height());
      Coordinate<Integer, Integer> coord = new Coordinate<Integer, Integer>(x, y);
      if (!coordinates.contains(coord)) {
        coordinates.add(coord);
        i++;
      }
    }
  }

  @After
  public void tearDown() {
    File file = new File(IMAGE_MODIFIED);
    file.delete();
  }

  @Test
  public void rgbTest() throws Exception {
    Stenography.hideChar(image, insert, 0, 0);
    ImageIO.write(image.getImage(), BMP_TYPE, new File(IMAGE_MODIFIED));
    ImageRgb imageModified = new ImageRgb(IMAGE_MODIFIED);
    char extract = Stenography.extractChar(imageModified, 0, 0);
    Assert.assertEquals(insert, extract);
  }

  @Test
  public void redTest() throws Exception {
    Stenography.hideCharInComponentRed(image, insert, 0, 0);
    ImageIO.write(image.getImage(), BMP_TYPE, new File(IMAGE_MODIFIED));
    ImageRgb imageModified = new ImageRgb(IMAGE_MODIFIED);
    char extract = Stenography.extractCharFromComponentRed(imageModified, 0, 0);
    Assert.assertEquals(insert, extract);
  }

  @Test
  public void greenTest() throws Exception {
    Stenography.hideCharInComponentGreen(image, insert, 0, 0);
    ImageIO.write(image.getImage(), BMP_TYPE, new File(IMAGE_MODIFIED));
    ImageRgb imageModified = new ImageRgb(IMAGE_MODIFIED);
    char extract = Stenography.extractCharFromComponentGreen(imageModified, 0, 0);
    Assert.assertEquals(insert, extract);
  }

  @Test
  public void blueTest() throws Exception {
    Stenography.hideCharInComponentBlue(image, insert, 0, 0);
    ImageIO.write(image.getImage(), BMP_TYPE, new File(IMAGE_MODIFIED));
    ImageRgb imageModified = new ImageRgb(IMAGE_MODIFIED);
    char extract = Stenography.extractCharFromComponentBlue(imageModified, 0, 0);
    Assert.assertEquals(insert, extract);
  }

  @Test
  public void redDistributedTest() throws Exception {
    Stenography.hideCharInComponentDistributedRed(image, insert, coordinates);
    ImageIO.write(image.getImage(), BMP_TYPE, new File(IMAGE_MODIFIED));
    ImageRgb imageModified = new ImageRgb(IMAGE_MODIFIED);
    char extract = Stenography.extractCharFromComponentDistributedRed(imageModified, coordinates);
    Assert.assertEquals(insert, extract);
  }

  @Test
  public void greenDistributedTest() throws Exception {
    Stenography.hideCharInComponentDistributedGreen(image, insert, coordinates);
    ImageIO.write(image.getImage(), BMP_TYPE, new File(IMAGE_MODIFIED));
    ImageRgb imageModified = new ImageRgb(IMAGE_MODIFIED);
    char extract = Stenography.extractCharFromComponentDistributedGreen(imageModified, coordinates);
    Assert.assertEquals(insert, extract);
  }

  @Test
  public void blueDistributedTest() throws Exception {
    Stenography.hideCharInComponentDistributedBlue(image, insert, coordinates);
    ImageIO.write(image.getImage(), BMP_TYPE, new File(IMAGE_MODIFIED));
    ImageRgb imageModified = new ImageRgb(IMAGE_MODIFIED);
    char extract = Stenography.extractCharFromComponentDistributedBlue(imageModified, coordinates);
    Assert.assertEquals(insert, extract);
  }

  @Test
  public void redDistributedErrorTest() {
    try {
      coordinates.add(new Coordinate<Integer, Integer>(0, 0));
      Stenography.hideCharInComponentDistributedRed(image, insert, coordinates);
    } catch (Exception e) {
      Assert.assertEquals(e.getClass(), IncorrectSizeException.class);
      Assert.assertEquals(e.getMessage(), new IncorrectSizeException().getMessage());
    }
  }

  @Test
  public void greenDistributedErrorTest() throws Exception {
    try {
      coordinates.add(new Coordinate<Integer, Integer>(0, 0));
      Stenography.hideCharInComponentDistributedGreen(image, insert, coordinates);
    } catch (Exception e) {
      Assert.assertEquals(e.getClass(), IncorrectSizeException.class);
      Assert.assertEquals(e.getMessage(), new IncorrectSizeException().getMessage());
    }
  }

  @Test
  public void blueDistributedErrorTest() throws Exception {
    try {
      coordinates.add(new Coordinate<Integer, Integer>(0, 0));
      Stenography.hideCharInComponentDistributedBlue(image, insert, coordinates);
    } catch (Exception e) {
      Assert.assertEquals(e.getClass(), IncorrectSizeException.class);
      Assert.assertEquals(e.getMessage(), new IncorrectSizeException().getMessage());
    }
  }

}
