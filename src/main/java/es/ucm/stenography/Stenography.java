package es.ucm.stenography;

import es.ucm.stenography.exception.IncorrectSizeException;
import es.ucm.stenography.model.Coordinate;
import es.ucm.stenography.model.ImageRgb;
import es.ucm.stenography.model.PixelRgb;

import java.util.TreeSet;

public class Stenography {

  private final String BLUE = "BLUE";
  private final String GREEN = "GREEN";
  private final String RED = "RED";

  /**
   * Embed secret information/TEXT into a "cover image"
   * 
   * @param image image where hide information.
   * @param character character to hide.
   * @param x coordinate x.
   * @param y coordinate y.
   */
  public void hideChar(ImageRgb image, char character, int x, int y) {
    int width = image.width();
    int bit = character;
    for (int j = 0; j < 8; j += 3) {
      int bitRed = bit & Constants.MASK;
      int bitGreen = bit >> Constants.OFFSET_1 & Constants.MASK;
      int bitBlue = bit >> Constants.OFFSET_2 & Constants.MASK;
      if (x > width) {
        x = 0;
        y++;
      }
      if (j >= 5) {
        image.hideRed(x, y, bitRed);
        image.hideGreen(x, y, bitGreen);
      } else {
        image.hide(x, y, bitRed, bitGreen, bitBlue);
      }
      x++;
      bit >>= Constants.OFFSET_3;
    }
  }

  private void hideCharInComponent(ImageRgb image, char character, int x, int y, String component) {
    int width = image.width();
    int bit = character;
    for (int j = 0; j < 8; j++) {
      int bitFinal = bit & Constants.MASK;
      if (x > width) {
        x = 0;
        y++;
      }
      switch (component) {
        case RED:
          image.hideRed(x, y, bitFinal);
          break;
        case GREEN:
          image.hideGreen(x, y, bitFinal);
          break;
        case BLUE:
          image.hideBlue(x, y, bitFinal);
          break;
        default:
          break;
      }
      x++;
      bit >>= Constants.OFFSET_1;
    }
  }

  /**
   * Embed secret information/TEXT into a "cover image" in the red component.
   * 
   * @param image image where hide information.
   * @param character character to hide.
   * @param x coordinate x.
   * @param y coordinate y.
   */
  public void hideCharInComponentRed(ImageRgb image, char character, int x, int y) {
    hideCharInComponent(image, character, x, y, RED);
  }

  /**
   * Embed secret information/TEXT into a "cover image" in the green component.
   * 
   * @param image image where hide information.
   * @param character character to hide.
   * @param x coordinate x.
   * @param y coordinate y.
   */
  public void hideCharInComponentGreen(ImageRgb image, char character, int x, int y) {
    hideCharInComponent(image, character, x, y, GREEN);
  }

  /**
   * Embed secret information/TEXT into a "cover image" in the blue component.
   * 
   * @param image image where hide information.
   * @param character character to hide.
   * @param x coordinate x.
   * @param y coordinate y.
   */
  public void hideCharInComponentBlue(ImageRgb image, char character, int x, int y) {
    hideCharInComponent(image, character, x, y, BLUE);
  }

  private void hideCharInComponentDistributed(ImageRgb image, char character,
      TreeSet<Coordinate> coordinates, String component) {
    int bit = character;
    chekList(coordinates);
    for (Coordinate<Integer, Integer> coord : coordinates) {
      int bitFinal = bit & Constants.MASK;
      switch (component) {
        case RED:
          image.hideRed(coord.getX(), coord.getY(), bitFinal);
          break;
        case GREEN:
          image.hideGreen(coord.getX(), coord.getY(), bitFinal);
          break;
        case BLUE:
          image.hideBlue(coord.getX(), coord.getY(), bitFinal);
          break;
        default:
          break;
      }
      bit >>= Constants.OFFSET_1;
    }
  }

  /**
   * Embed secret information/TEXT into a "cover image" in the red component.
   * 
   * @param image image where hide information.
   * @param character character to hide.
   * @param x coordinate x.
   * @param y coordinate y.
   */
  public void hideCharInComponentDistributedRed(ImageRgb image, char character,
      TreeSet<Coordinate> coordinates) {
    hideCharInComponentDistributed(image, character, coordinates, RED);
  }

  /**
   * Embed secret information/TEXT into a "cover image" in the green component.
   * 
   * @param image image where hide information.
   * @param character character to hide.
   * @param x coordinate x.
   * @param y coordinate y.
   */
  public void hideCharInComponentDistributedGreen(ImageRgb image, char character,
      TreeSet<Coordinate> coordinates) {
    hideCharInComponentDistributed(image, character, coordinates, GREEN);
  }

  /**
   * Embed secret information/TEXT into a "cover image" in the blue component.
   * 
   * @param image image where hide information.
   * @param character character to hide.
   * @param x coordinate x.
   * @param y coordinate y.
   */
  public void hideCharInComponentDistributedBlue(ImageRgb image, char character,
      TreeSet<Coordinate> coordinates) {
    hideCharInComponentDistributed(image, character, coordinates, BLUE);
  }

  /**
   * Extract secret information/Text from a "cover image"
   * 
   * @param image image from extract information.
   * @param x coordinate x.
   * @param y coordinate y.
   * @return character extracted.
   */
  public char extractChar(ImageRgb image, int x, int y) {
    int width = image.width();
    String bits = "";
    for (int j = 0; j < 8; j += 3) {
      if (x > width) {
        x = 0;
        y++;
      }
      PixelRgb pixel = image.getPixelRgb(x, y);
      x++;
      if (j >= 5) {
        bits += pixel.extractRed() + "" + pixel.extractGreen();
      } else {
        bits += pixel.extractRed() + "" + pixel.extractGreen() + "" + pixel.extractBlue();
      }
    }

    String ret = new String();
    for (int i = bits.length() - 1; i >= 0; i--) {
      ret = ret + bits.charAt(i);
    }
    char r = (char) Integer.parseInt(ret, 2);
    return r;
  }

  private char extractCharFromComponent(ImageRgb image, int x, int y, String component) {
    int width = image.width();
    String bits = "";
    for (int j = 0; j < 8; j++) {
      if (x > width) {
        x = 0;
        y++;
      }
      PixelRgb pixel = image.getPixelRgb(x, y);
      x++;
      switch (component) {
        case RED:
          bits += pixel.extractRed();
          break;
        case GREEN:
          bits += pixel.extractGreen();
          break;
        case BLUE:
          bits += pixel.extractBlue();
          break;
        default:
          break;
      }
    }
    String ret = new String();
    for (int i = bits.length() - 1; i >= 0; i--) {
      ret = ret + bits.charAt(i);
    }
    return (char) Integer.parseInt(ret, 2);
  }

  /**
   * Extract secret information/Text from a "cover image" in the red component.
   * 
   * @param image image from extract information.
   * @param x coordinate x.
   * @param y coordinate y.
   * @return character extracted.
   */
  public char extractCharFromComponentRed(ImageRgb image, int x, int y) {
    return extractCharFromComponent(image, x, y, RED);
  }

  /**
   * Extract secret information/Text from a "cover image" in the green component.
   * 
   * @param image image from extract information.
   * @param x coordinate x.
   * @param y coordinate y.
   * @return character extracted.
   */
  public char extractCharFromComponentGreen(ImageRgb image, int x, int y) {
    return extractCharFromComponent(image, x, y, GREEN);
  }

  /**
   * Extract secret information/Text from a "cover image" in the blue component.
   * 
   * @param image image from extract information.
   * @param x coordinate x.
   * @param y coordinate y.
   * @return character extracted.
   */
  public char extractCharFromComponentBlue(ImageRgb image, int x, int y) {
    return extractCharFromComponent(image, x, y, BLUE);
  }

  private char extractCharFromComponentDistributed(ImageRgb image, TreeSet<Coordinate> coordinates,
      String component) {
    String bits = "";
    chekList(coordinates);
    for (Coordinate<Integer, Integer> coord : coordinates) {
      PixelRgb pixel = image.getPixelRgb(coord.getX(), coord.getY());
      switch (component) {
        case RED:
          bits += pixel.extractRed();
          break;
        case GREEN:
          bits += pixel.extractGreen();
          break;
        case BLUE:
          bits += pixel.extractBlue();
          break;
        default:
          break;
      }
    }
    String ret = new String();
    for (int i = bits.length() - 1; i >= 0; i--) {
      ret = ret + bits.charAt(i);
    }
    return (char) Integer.parseInt(ret, 2);
  }

  /**
   * Extract secret information/Text from a "cover image" in the red component.
   * 
   * @param image image from extract information.
   * @param x coordinate x.
   * @param y coordinate y.
   * @return character extracted.
   */
  public char extractCharFromComponentDistributedRed(ImageRgb image,
      TreeSet<Coordinate> coordinates) {
    return extractCharFromComponentDistributed(image, coordinates, RED);
  }

  /**
   * Extract secret information/Text from a "cover image" in the green component.
   * 
   * @param image image from extract information.
   * @param x coordinate x.
   * @param y coordinate y.
   * @return character extracted.
   */
  public char extractCharFromComponentDistributedGreen(ImageRgb image,
      TreeSet<Coordinate> coordinates) {
    return extractCharFromComponentDistributed(image, coordinates, GREEN);
  }

  /**
   * Extract secret information/Text from a "cover image" in the blue component.
   * 
   * @param image image from extract information.
   * @param x coordinate x.
   * @param y coordinate y.
   * @return character extracted.
   */
  public char extractCharFromComponentDistributedBlue(ImageRgb image,
      TreeSet<Coordinate> coordinates) {
    return extractCharFromComponentDistributed(image, coordinates, BLUE);
  }

  private void chekList(TreeSet<Coordinate> coordinates) {
    if (coordinates.size() != 8) {
      throw new IncorrectSizeException();
    }
  }

}
