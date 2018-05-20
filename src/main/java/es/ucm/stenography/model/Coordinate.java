package es.ucm.stenography.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Coordinate<X, Y> implements Comparable<Coordinate<X, Y>> {

  private X x;
  private Y y;

  @Override
  public int compareTo(Coordinate<X, Y> coordinate) {
    final int BEFORE = -1;
    final int EQUAL = 0;

    if (equals(coordinate))
      return EQUAL;


    return BEFORE;

  }
}

