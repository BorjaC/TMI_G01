package es.ucm.stenography.model;

import lombok.Getter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.ucm.stenography.Constants;

import java.awt.image.BufferedImage;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;

@Getter
public class ImageRgb {

  private static final Log LOGGER = LogFactory.getLog(ImageRgb.class);

  private BufferedImage image;
  private String name;

  /**
   * Construct new image RGB from name image.
   * 
   * @param name of the image.
   * @throws Exception
   */
  public ImageRgb(String image) throws Exception {
    readImage(image);
  }

  /**
   * Return the height of the image.
   * 
   * @return height of the image.
   */
  public int height() {
    return (image != null) ? image.getHeight() : -1;
  }

  /**
   * Return the width of the image.
   * 
   * @return width of the image.
   */
  public int width() {
    return (image != null) ? image.getWidth() : -1;
  }

  /**
   * Get pixel RGB.
   * 
   * @param x coordinate x.
   * @param y coordinate y.
   * @return return pixel RGB.
   */
  public PixelRgb getPixelRgb(int x, int y) {
    if ((x > width()) || (y > height())) {
      return null;
    }

    int pixel = (image != null) ? image.getRGB(x, y) : 0;
    return new PixelRgb((pixel >> Constants.OFFSET_16) & Constants.HEX_FF,
        (pixel >> Constants.OFFSET_8) & Constants.HEX_FF, pixel & Constants.HEX_FF);
  }

  /**
   * Set pixel RGB.
   * 
   * @param x coordinate x.
   * @param y coordinate y.
   * @param pixel pixel RGB.
   */
  public void setPixelRgb(int x, int y, PixelRgb pixel) {
    if ((x > width()) || (y > height())) {
      return;
    }
    if (image != null) {
      image.setRGB(x, y, pixel.getPixel());
    }
  }

  /**
   * Hides the bits in the three components.
   * 
   * @param x coordinate x.
   * @param y coordinate y.
   * @param bitRed bit to hide in the red component.
   * @param bitGreen bit to hide in the green component.
   * @param bitBlue bit to hide in the blue component.
   */
  public void hide(int x, int y, int bitRed, int bitGreen, int bitBlue) {
    PixelRgb pixel = getPixelRgb(x, y);
    pixel.hideRgb(bitRed, bitGreen, bitBlue);
    setPixelRgb(x, y, pixel);
  }

  /**
   * Hides the bit in the red component.
   * 
   * @param x coordinate x.
   * @param y coordinate y.
   * @param bitRed bit to hide in the red component.
   */
  public void hideRed(int x, int y, int bitRed) {
    PixelRgb pixel = getPixelRgb(x, y);
    pixel.hideRed(bitRed);
    setPixelRgb(x, y, pixel);
  }

  /**
   * Hides the bit in the green components.
   * 
   * @param x coordinate x.
   * @param y coordinate y.
   * @param bitGreen bit to hide in the green component.
   */
  public void hideGreen(int x, int y, int bitGreen) {
    PixelRgb pixel = getPixelRgb(x, y);
    pixel.hideGreen(bitGreen);
    setPixelRgb(x, y, pixel);
  }

  /**
   * Hides the bits in the blue components.
   * 
   * @param x coordinate x.
   * @param y coordinate y.
   * @param bitBlue bit to hide in the blue component.
   */
  public void hideBlue(int x, int y, int bitBlue) {
    PixelRgb pixel = getPixelRgb(x, y);
    pixel.hideBlue(bitBlue);
    setPixelRgb(x, y, pixel);
  }

  /**
   * Read image from url.
   * 
   * @param imageUrl image path.
   * @throws Exception
   */
  private void readImage(String imageUrl) throws Exception {
    String[] paths = imageUrl.split("/");
    name = paths[paths.length - 1];
    name = name.substring(0, name.length() - 4);

    try (DataInputStream inBMP = new DataInputStream(new FileInputStream(new File(imageUrl)))) {

      inBMP.skipBytes(18); // salta la cabecera

      // ancho y alto de la imagen
      int ancho = read32Bits(inBMP);
      int altura = read32Bits(inBMP);

      inBMP.skipBytes(28); // salta los datos no útiles de la cabecera

      // crea y rellena img
      image = new BufferedImage(ancho, altura, BufferedImage.TYPE_INT_RGB);

      int tamanoRelleno = (4 - ((ancho * 3) % 4)) % 4; // numero de bytes de relleno en cada linea

      // Lectura de pixels
      for (int y = altura - 1; y >= 0; y--) {
        for (int x = 0; x < ancho; x++) {
          image.setRGB(x, y, read24Bits(inBMP));
        }
        inBMP.skipBytes(tamanoRelleno); // salta el relleno
      }
    } catch (Exception e) {
      LOGGER.error("Error create image", e);
      throw e;
    }
  }

  /**
   * Read 32-bit.
   * 
   * @param in input stream
   * @return bit read.
   * @throws Exception
   */
  private int read32Bits(DataInputStream in) throws Exception {
    // efecto: lectura de un entero sobre 4 bytes desde in
    // resultado: el entero leido
    byte[] b = new byte[4];
    int resul = 0;
    try {
      in.read(b);
      resul = b[0] & 0xFF;
      resul = resul + ((b[1] & Constants.HEX_FF) << Constants.OFFSET_8);
      resul = resul + ((b[2] & Constants.HEX_FF) << Constants.OFFSET_16);
      resul = resul + ((b[3] & Constants.HEX_FF) << Constants.OFFSET_24);
    } catch (Exception e) {
      LOGGER.error("Error read 32-bits", e);
      throw e;
    }
    return resul;
  }

  /**
   * Read 24-bits.
   * 
   * @param in input stream.
   * @return bit read.
   * @throws Exception
   */
  private int read24Bits(DataInputStream in) throws Exception {
    // efecto: lectura de un pixel sobre 3 bytes desde in
    // resultado: el entero correspondiente al pixel leido
    byte[] b = new byte[3];
    int result = 0;
    try {
      in.read(b);
      result = b[0] & 0xFF;
      result = result + ((b[1] & Constants.HEX_FF) << Constants.OFFSET_8);
      result = result + ((b[2] & Constants.HEX_FF) << Constants.OFFSET_16);
    } catch (Exception e) {
      LOGGER.error("Error read 24-bits", e);
      throw e;
    }
    return result;
  }
}
