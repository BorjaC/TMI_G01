package es.ucm.stenography;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import es.ucm.stenography.exception.IncorrectSizeException;
import es.ucm.stenography.model.Coordinate;
import es.ucm.stenography.model.ImageRgb;

import java.io.File;
import java.util.TreeSet;

import javax.imageio.ImageIO;

public class StenographyTest {

  private static final String BMP_TYPE = "bmp";
  private static final String IMAGE_MODIFIED = "modified.bmp";
  private static final String IMAGE_NAME = "src/test/resources/test.bmp";

  private ImageRgb image;
  private char insert;
  private TreeSet<Coordinate> coordinates;
  private Stenography stenography;

  @Before
  public void setUp() throws Exception {
    insert = (char) Math.floor(Math.random() * (32 - 126 + 1) + 126);
    image = new ImageRgb(IMAGE_NAME);

    stenography = new Stenography();

    coordinates = new TreeSet<>();
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
    stenography.hideChar(image, insert, 0, 0);
    ImageIO.write(image.getImage(), BMP_TYPE, new File(IMAGE_MODIFIED));
    ImageRgb imageModified = new ImageRgb(IMAGE_MODIFIED);
    char extract = stenography.extractChar(imageModified, 0, 0);
    Assert.assertEquals(insert, extract);
  }

  @Test
  public void redTest() throws Exception {
    stenography.hideCharInComponentRed(image, insert, 0, 0);
    ImageIO.write(image.getImage(), BMP_TYPE, new File(IMAGE_MODIFIED));
    ImageRgb imageModified = new ImageRgb(IMAGE_MODIFIED);
    char extract = stenography.extractCharFromComponentRed(imageModified, 0, 0);
    Assert.assertEquals(insert, extract);
  }

  @Test
  public void greenTest() throws Exception {
    stenography.hideCharInComponentGreen(image, insert, 0, 0);
    ImageIO.write(image.getImage(), BMP_TYPE, new File(IMAGE_MODIFIED));
    ImageRgb imageModified = new ImageRgb(IMAGE_MODIFIED);
    char extract = stenography.extractCharFromComponentGreen(imageModified, 0, 0);
    Assert.assertEquals(insert, extract);
  }

  @Test
  public void blueTest() throws Exception {
    stenography.hideCharInComponentBlue(image, insert, 0, 0);
    ImageIO.write(image.getImage(), BMP_TYPE, new File(IMAGE_MODIFIED));
    ImageRgb imageModified = new ImageRgb(IMAGE_MODIFIED);
    char extract = stenography.extractCharFromComponentBlue(imageModified, 0, 0);
    Assert.assertEquals(insert, extract);
  }

  @Test
  public void redDistributedTest() throws Exception {
    stenography.hideCharInComponentDistributedRed(image, insert, coordinates);
    ImageIO.write(image.getImage(), BMP_TYPE, new File(IMAGE_MODIFIED));
    ImageRgb imageModified = new ImageRgb(IMAGE_MODIFIED);
    char extract = stenography.extractCharFromComponentDistributedRed(imageModified, coordinates);
    Assert.assertEquals(insert, extract);
  }

  @Test
  public void greenDistributedTest() throws Exception {
    stenography.hideCharInComponentDistributedGreen(image, insert, coordinates);
    ImageIO.write(image.getImage(), BMP_TYPE, new File(IMAGE_MODIFIED));
    ImageRgb imageModified = new ImageRgb(IMAGE_MODIFIED);
    char extract = stenography.extractCharFromComponentDistributedGreen(imageModified, coordinates);
    Assert.assertEquals(insert, extract);
  }

  @Test
  public void blueDistributedTest() throws Exception {
    stenography.hideCharInComponentDistributedBlue(image, insert, coordinates);
    ImageIO.write(image.getImage(), BMP_TYPE, new File(IMAGE_MODIFIED));
    ImageRgb imageModified = new ImageRgb(IMAGE_MODIFIED);
    char extract = stenography.extractCharFromComponentDistributedBlue(imageModified, coordinates);
    Assert.assertEquals(insert, extract);
  }

  @Test
  public void redDistributedErrorTest() {
    try {
      coordinates.add(new Coordinate<Integer, Integer>(0, 0));
      stenography.hideCharInComponentDistributedRed(image, insert, coordinates);
    } catch (Exception e) {
      Assert.assertEquals(e.getClass(), IncorrectSizeException.class);
      Assert.assertEquals(e.getMessage(), new IncorrectSizeException().getMessage());
    }
  }

  @Test
  public void greenDistributedErrorTest() throws Exception {
    try {
      coordinates.add(new Coordinate<Integer, Integer>(0, 0));
      stenography.hideCharInComponentDistributedGreen(image, insert, coordinates);
    } catch (Exception e) {
      Assert.assertEquals(e.getClass(), IncorrectSizeException.class);
      Assert.assertEquals(e.getMessage(), new IncorrectSizeException().getMessage());
    }
  }

  @Test
  public void blueDistributedErrorTest() throws Exception {
    try {
      coordinates.add(new Coordinate<Integer, Integer>(0, 0));
      stenography.hideCharInComponentDistributedBlue(image, insert, coordinates);
    } catch (Exception e) {
      Assert.assertEquals(e.getClass(), IncorrectSizeException.class);
      Assert.assertEquals(e.getMessage(), new IncorrectSizeException().getMessage());
    }
  }

}
