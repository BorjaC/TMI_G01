package es.ucm.stenography.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Coordinate<X, Y> {

  private X x;
  private Y y;

}
