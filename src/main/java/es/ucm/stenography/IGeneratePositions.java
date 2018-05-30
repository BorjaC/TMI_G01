package es.ucm.stenography;

import es.ucm.stenography.model.Coordinate;

import java.util.TreeSet;

public interface IGeneratePositions {
  TreeSet<Coordinate> Get(String hashStr, int size, int positions);
}
