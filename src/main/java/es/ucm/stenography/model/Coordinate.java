package es.ucm.stenography.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Objects;

@Data
@AllArgsConstructor
public class Coordinate<X, Y> {//implements Comparable<Coordinate>{

  private X x;
  private Y y;

//  @Override
// public int compareTo(Coordinate coordinate) {
//   final int BEFORE = -1;
//   final int EQUAL = 0;
  //   final int AFTER = 1;

//    if (equals(coordinate))
  //     return EQUAL;


//    return BEFORE;

  // }
}

