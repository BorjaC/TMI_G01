package es.ucm.stenography.exception;

public class IncorrectSizeException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  public IncorrectSizeException() {
    super("The size of the list is incorrect, it only accepts 8 elements.");
  }

}
