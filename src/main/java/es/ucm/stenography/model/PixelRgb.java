package es.ucm.stenography.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Setter;

import es.ucm.stenography.Constants;

@AllArgsConstructor
@Data
public class PixelRgb {

  @Setter(AccessLevel.NONE)
  private int red;
  @Setter(AccessLevel.NONE)
  private int green;
  @Setter(AccessLevel.NONE)
  private int blue;

  /**
   * Get pixel.
   * 
   * @return pixel.
   */
  public int getPixel() {
    return (red << Constants.OFFSET_16) + (green << Constants.OFFSET_8) + blue;
  }

  /**
   * Hides the bit in the red component.
   * 
   * @param bit to hide.
   */
  public void hideRed(int bit) {
    if (bit == 1) {
      red |= Constants.MASK_ONE;
    } else {
      red &= Constants.MASK_ZERO;
    }
  }

  /**
   * Hides the bit in the green component.
   * 
   * @param bit to hide.
   */
  public void hideGreen(int bit) {
    if (bit == 1) {
      green |= Constants.MASK_ONE;
    } else {
      green &= Constants.MASK_ZERO;
    }
  }

  /**
   * Hides the bit in the blue component.
   * 
   * @param bit to hide.
   */
  public void hideBlue(int bit) {
    if (bit == 1) {
      blue |= Constants.MASK_ONE;
    } else {
      blue &= Constants.MASK_ZERO;
    }
  }

  /**
   * Hides the bits in the three components.
   * 
   * @param bitRed bit to hide in the red component.
   * @param bitGreen bit to hide in the green component.
   * @param bitBlue bit to hide in the blue component.
   */
  public void hideRgb(int bitRed, int bitGreen, int bitBlue) {
    hideRed(bitRed);
    hideGreen(bitGreen);
    hideBlue(bitBlue);
  }

  /**
   * Extract bit from the red component.
   * 
   * @return bit extracted.
   */
  public int extractRed() {
    return red & Constants.MASK;
  }

  /**
   * Extract bit from the green component.
   * 
   * @return bit extracted.
   */
  public int extractGreen() {
    return green & Constants.MASK;
  }

  /**
   * Extract bit from the blue component.
   * 
   * @return bit extracted.
   */
  public int extractBlue() {
    return blue & Constants.MASK;
  }

  /**
   * Returns a string binary representation of the pixel.
   * 
   * @return binary representation of the pixel.
   */
  public String toBinaryString() {
    return Integer.toBinaryString(red) + Integer.toBinaryString(green)
        + Integer.toBinaryString(blue);
  }

  /**
   * Returns a string binary representation of the pixel in format RGB.
   * 
   * @return binary representation of the pixel in format RGB.
   */
  public String toBinaryStringRgb() {
    return Integer.toBinaryString(red) + "-" + Integer.toBinaryString(green) + "-"
        + Integer.toBinaryString(blue);
  }

  /**
   * Returns a string binary representation of the red component.
   * 
   * @return binary representation of the red component.
   */
  public String toBinaryStringRed() {
    return Integer.toBinaryString(red);
  }

  /**
   * Returns a string binary representation of the green component.
   * 
   * @return binary representation of the green component.
   */
  public String toBinaryStringGreen() {
    return Integer.toBinaryString(green);
  }

  /**
   * Returns a string binary representation of the blue component.
   * 
   * @return binary representation of the blue component.
   */
  public String toBinaryStringBlue() {
    return Integer.toBinaryString(blue);
  }
}
