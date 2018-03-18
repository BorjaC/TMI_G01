package es.ucm.stenography;

import es.ucm.stenography.model.ImageRgb;
import es.ucm.stenography.model.PixelRgb;

public class Stenography {

  private static final String BLUE = "BLUE";
  private static final String GREEN = "GREEN";
  private static final String RED = "RED";

  /**
   * Embed secret information/TEXT into a "cover image"
   * 
   * @param image image where hide information.
   * @param character character to hide.
   * @param x coordinate x.
   * @param y coordinate y.
   */
  public static void hideChar(ImageRgb image, char character, int x, int y) {
    int width = image.width();
    int bit = character;
    for (int j = 0; j < 8; j += 3) {
      int bitRed = bit & Conts.MASK;
      int bitGreen = bit >> Conts.OFFSET_1 & Conts.MASK;
      int bitBlue = bit >> Conts.OFFSET_2 & Conts.MASK;
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
      bit >>= Conts.OFFSET_3;
    }
  }

  private static void hideCharInComponent(ImageRgb image, char character, int x, int y,
      String component) {
    int width = image.width();
    int bit = character;
    for (int j = 0; j < 8; j += 3) {
      int bitRed = bit & Conts.MASK;
      if (x > width) {
        x = 0;
        y++;
      }
      switch (component) {
        case RED:
          image.hideRed(x, y, bitRed);
          break;
        case GREEN:
          image.hideGreen(x, y, bitRed);
          break;
        case BLUE:
          image.hideBlue(x, y, bitRed);
          break;
        default:
          break;
      }
      x++;
      bit >>= Conts.OFFSET_1;
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
  public static void hideCharInComponentRed(ImageRgb image, char character, int x, int y) {
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
  public static void hideCharInComponentGreen(ImageRgb image, char character, int x, int y) {
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
  public static void hideCharInComponentBlue(ImageRgb image, char character, int x, int y) {
    hideCharInComponent(image, character, x, y, BLUE);
  }

  /**
   * Extract secret information/Text from a "cover image"
   * 
   * @param image image from extract information.
   * @param x coordinate x.
   * @param y coordinate y.
   * @return character extracted.
   */
  public static char extractChar(ImageRgb image, int x, int y) {
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

  private static char extractCharFromComponent(ImageRgb image, int x, int y, String component) {
    int width = image.width();
    // int bit = 0;
    String bits = "";
    // 8 digits form a character
    for (int j = 0; j < 8; j += 3) {
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
      bits += pixel.extractRed();
    }

    String ret = new String();
    for (int i = bits.length() - 1; i >= 0; i--) {
      ret = ret + bits.charAt(i);
    }
    char r = (char) Integer.parseInt(ret, 2);
    return r;
  }

  /**
   * Extract secret information/Text from a "cover image" in the red component.
   * 
   * @param image image from extract information.
   * @param x coordinate x.
   * @param y coordinate y.
   * @return character extracted.
   */
  public static char extractCharFromComponentRed(ImageRgb image, int x, int y) {
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
  public static char extractCharFromComponentGreen(ImageRgb image, int x, int y) {
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
  public static char extractCharFromComponentBlue(ImageRgb image, int x, int y) {
    return extractCharFromComponent(image, x, y, BLUE);
  }

}
