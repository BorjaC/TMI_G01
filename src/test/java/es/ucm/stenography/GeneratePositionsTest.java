package es.ucm.stenography;

import es.ucm.stenography.model.Coordinate;
import org.junit.Assert;
import org.junit.Test;

import java.util.TreeSet;

/** 
* GeneratePositions Tester. 
* 
* @author <Authors name> 
* @since <pre>Apr 5, 2018</pre> 
* @version 1.0 
*/ 
public class GeneratePositionsTest { 

/** 
* 
* Method: Get(String hashStr, int size, int positions) 
* 
*/ 
@Test
public void testGet() {

    //Arrange
    String hashStr = "5AB2E8AC6897E2211638E39A252882AE60C86A6038EDEC47F81CFBB55C083DBE2C094F30E2236A182A98BACA4C31E539A60CFFB14EE4A0FE6EF16DD1094A231B";
    IGeneratePositions sut = new GeneratePositions();
    TreeSet<Coordinate> expected = new TreeSet<>();
    Coordinate coordinate = new Coordinate(722,837);
    expected.add(coordinate);

    //Act
    TreeSet<Coordinate> actual = sut.Get(hashStr, 2048, 1);

    //Assert
    Assert.assertArrayEquals(expected.toArray(), actual.toArray());
}

} 
