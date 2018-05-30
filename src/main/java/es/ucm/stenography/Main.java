package es.ucm.stenography;

import org.apache.commons.lang3.StringUtils;

import es.ucm.stenography.model.Coordinate;
import es.ucm.stenography.model.ImageRgb;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Objects;
import java.util.TreeSet;

import javax.imageio.ImageIO;

public class Main {

  private static final char FINAL_CHARACTER = '§';

  private static Stenography stenography = new Stenography();
  private static IGeneratePositions sut = new GeneratePositions();

  public static void main(String[] args) throws Exception {

    ImageRgb image = new ImageRgb("src/test/resources/test.bmp");

    hide(image, "Hola, esto es una prueba para la presentacion de TMI");

    ImageIO.write(image.getImage(), "bmp", new File("tmi.bmp"));

    ImageRgb image2 = new ImageRgb("tmi.bmp");

    String text = extract(image2);

    System.out.println(text);
  }

  /*********************************************************************/ /*********************************************************************/
  /*********************************************************************/ /*********************************************************************/
  /*********************************************************************/ /*********************************************************************/
  /*********************************************************************/ /*********************************************************************/
  /*********************************************************************/ /*********************************************************************/
  /*********************************************************************/ /*********************************************************************/
  /*********************************************************************/ /*********************************************************************/
  /*********************************************************************/ /*********************************************************************/
  /*********************************************************************/ /*********************************************************************/
  /*********************************************************************/ /*********************************************************************/
  /*********************************************************************/ /*********************************************************************/
  /*********************************************************************/ /*********************************************************************/
  /*********************************************************************/ /*********************************************************************/
  /*********************************************************************/
  /*********************************************************************/
  // private static void normal() throws Exception, IOException {
  // ImageRgb image = new ImageRgb("src/test/resources/test.bmp");
  // hide(image, "Hola, esto es una prueba para la presentacion de TMI");
  // ImageIO.write(image.getImage(), "bmp", new File("tmi.bmp"));
  // ImageRgb image2 = new ImageRgb("tmi.bmp");
  // String text = extract(image2);
  // System.out.println(text);
  // }

  private static void ramdon() throws Exception, IOException {
    String root =
        "5AB2E8AC6897E2211638E39A252882AE60C86A6038EDEC47F81CFBB55C083DBE2C094F30E2236A182A98BACA4C31E539A60CFFB14EE4A0FE6EF16DD1094A231B";

    ImageRgb image = new ImageRgb("src/test/resources/test.bmp");
    hideRamdon(image, "Hola, esto es una prueba para la presentacion de TMI"
        + " con pixels generados con la semilla " + root, root);
    ImageIO.write(image.getImage(), "bmp", new File("tmi2.bmp"));
    ImageRgb image2 = new ImageRgb("tmi2.bmp");
    String text = extractRamdon(image2, root);
    System.out.println(text);
  }



  private static void hide(ImageRgb image, String text) {
    if (StringUtils.isNotBlank(text) && Objects.nonNull(image)) {
      text += FINAL_CHARACTER;
      int w = image.width();
      int x = 0;
      int y = 0;
      for (int index = 0; index < text.length(); index++) {
        // System.out.println("x: " + x + " y: " + y + " " + text.charAt(index));
        stenography.hideChar(image, text.charAt(index), x, y);
        x += 8;
        if (x > w) {
          x -= w;
          y++;
        }
      }
    }
  }

  private static String extract(ImageRgb image) {
    String result = new String();
    if (Objects.nonNull(image)) {
      int w = image.width();
      int x = 0;
      int y = 0;
      char character = 0;
      while (true) {
        character = stenography.extractChar(image, x, y);
        if (FINAL_CHARACTER != character) {
          result += character;
          x += 8;
          if (x > w) {
            x -= w;
            y++;
          }
        } else {
          break;
        }
      }
    }
    return result;
  }

  private static void hideRamdon(ImageRgb image, String text, String root) {
    if (StringUtils.isNotBlank(text) && Objects.nonNull(image)) {
      text += FINAL_CHARACTER;
      int size = (image.width() < image.height()) ? image.width() : image.height();
      TreeSet<Coordinate> coordinates = sut.Get(root, size, text.length() * 8);
      // System.out.println(text.length() * 8);
      for (int index = 0; index < text.length(); index++) {
        TreeSet<Coordinate> coordinatesByChar = get8Coordinates(index * 8, coordinates);
        // System.out.println(coordinatesByChar);
        stenography.hideCharInComponentDistributedBlue(image, text.charAt(index),
            coordinatesByChar);
      }
    }
  }

  private static String extractRamdon(ImageRgb image, String root) {
    String result = new String();
    if (Objects.nonNull(image)) {
      int size = (image.width() < image.height()) ? image.width() : image.height();
      TreeSet<Coordinate> coordinates = sut.Get(root, size, 1704);
      char character = 0;
      int index = 0;
      while (true) {
        TreeSet<Coordinate> coordinatesByChar = get8Coordinates(index * 8, coordinates);
        // System.out.println(coordinatesByChar);
        character = stenography.extractCharFromComponentDistributedBlue(image, coordinatesByChar);
        // System.out.println(character);
        if (FINAL_CHARACTER != character) {
          result += character;
        } else {
          break;
        }
        index++;
      }
    }
    return result;
  }

  private static TreeSet<Coordinate> get8Coordinates(Integer from,
      TreeSet<Coordinate> coordinates) {
    Iterator<Coordinate> itr = coordinates.iterator();
    TreeSet<Coordinate> result = new TreeSet<>();
    for (int i = 0; itr.hasNext(); i++) {
      Coordinate value = itr.next();
      if (i >= from && i < from + 8) {
        result.add(value);
      }
    }
    return result;
  }
}
