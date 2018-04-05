package es.ucm.stenography;

import es.ucm.stenography.model.Coordinate;
import org.junit.Assert;
import org.junit.Test;
import org.junit.Before; 
import org.junit.After;

import java.util.TreeSet;

/** 
* GeneratePositions Tester. 
* 
* @author <Authors name> 
* @since <pre>Apr 5, 2018</pre> 
* @version 1.0 
*/ 
public class GeneratePositionsTest { 

@Before
public void before() throws Exception { 
} 

@After
public void after() throws Exception { 
} 

/** 
* 
* Method: Get(String hashStr, int size, int positions) 
* 
*/ 
@Test
public void testGet() throws Exception {

    //Arrange
    String hashStr = "5AB2E8AC6897E2211638E39A252882AE60C86A6038EDEC47F81CFBB55C083DBE2C094F30E2236A182A98BACA4C31E539A60CFFB14EE4A0FE6EF16DD1094A231B";
    IGeneratePositions sut = new GeneratePositions();
    TreeSet<Coordinate> expeted = new TreeSet<>();
    expeted.add(new Coordinate(722,837));

    //Act
    TreeSet<Coordinate> actual = sut.Get(hashStr, 2048, 1);

    //Assert
    Assert.assertArrayEquals(expeted.toArray(), actual.toArray());
} 


/** 
* 
* Method: convert(long value) 
* 
*/ 
@Test
public void testConvert() throws Exception { 
//TODO: Test goes here... 
/* 
try { 
   Method method = GeneratePositions.getClass().getMethod("convert", long.class); 
   method.setAccessible(true); 
   method.invoke(<Object>, <Parameters>); 
} catch(NoSuchMethodException e) { 
} catch(IllegalAccessException e) { 
} catch(InvocationTargetException e) { 
} 
*/ 
} 

/** 
* 
* Method: hexStringToBitSet(String hexString) 
* 
*/ 
@Test
public void testHexStringToBitSet() throws Exception { 
//TODO: Test goes here... 
/* 
try { 
   Method method = GeneratePositions.getClass().getMethod("hexStringToBitSet", String.class); 
   method.setAccessible(true); 
   method.invoke(<Object>, <Parameters>); 
} catch(NoSuchMethodException e) { 
} catch(IllegalAccessException e) { 
} catch(InvocationTargetException e) { 
} 
*/ 
} 

/** 
* 
* Method: hexStringToByteArray(String s) 
* 
*/ 
@Test
public void testHexStringToByteArray() throws Exception { 
//TODO: Test goes here... 
/* 
try { 
   Method method = GeneratePositions.getClass().getMethod("hexStringToByteArray", String.class); 
   method.setAccessible(true); 
   method.invoke(<Object>, <Parameters>); 
} catch(NoSuchMethodException e) { 
} catch(IllegalAccessException e) { 
} catch(InvocationTargetException e) { 
} 
*/ 
} 

/** 
* 
* Method: GetSignificantBits(BitSet sizeInBits) 
* 
*/ 
@Test
public void testGetSignificantBits() throws Exception { 
//TODO: Test goes here... 
/* 
try { 
   Method method = GeneratePositions.getClass().getMethod("GetSignificantBits", BitSet.class); 
   method.setAccessible(true); 
   method.invoke(<Object>, <Parameters>); 
} catch(NoSuchMethodException e) { 
} catch(IllegalAccessException e) { 
} catch(InvocationTargetException e) { 
} 
*/ 
} 

/** 
* 
* Method: PreviousPowerOf2(int x) 
* 
*/ 
@Test
public void testPreviousPowerOf2() throws Exception { 
//TODO: Test goes here... 
/* 
try { 
   Method method = GeneratePositions.getClass().getMethod("PreviousPowerOf2", int.class); 
   method.setAccessible(true); 
   method.invoke(<Object>, <Parameters>); 
} catch(NoSuchMethodException e) { 
} catch(IllegalAccessException e) { 
} catch(InvocationTargetException e) { 
} 
*/ 
} 

/** 
* 
* Method: encryptSHA512(String target) 
* 
*/ 
@Test
public void testEncryptSHA512() throws Exception { 
//TODO: Test goes here... 
/* 
try { 
   Method method = GeneratePositions.getClass().getMethod("encryptSHA512", String.class); 
   method.setAccessible(true); 
   method.invoke(<Object>, <Parameters>); 
} catch(NoSuchMethodException e) { 
} catch(IllegalAccessException e) { 
} catch(InvocationTargetException e) { 
} 
*/ 
} 

/** 
* 
* Method: GetAllPositions(String hashStr, int significantBits, int maxPositions) 
* 
*/ 
@Test
public void testGetAllPositions() throws Exception { 
//TODO: Test goes here... 
/* 
try { 
   Method method = GeneratePositions.getClass().getMethod("GetAllPositions", String.class, int.class, int.class); 
   method.setAccessible(true); 
   method.invoke(<Object>, <Parameters>); 
} catch(NoSuchMethodException e) { 
} catch(IllegalAccessException e) { 
} catch(InvocationTargetException e) { 
} 
*/ 
} 

/** 
* 
* Method: GetPositions(String hashStr, int significantBits, int maxPositions) 
* 
*/ 
@Test
public void testGetPositions() throws Exception { 
//TODO: Test goes here... 
/* 
try { 
   Method method = GeneratePositions.getClass().getMethod("GetPositions", String.class, int.class, int.class); 
   method.setAccessible(true); 
   method.invoke(<Object>, <Parameters>); 
} catch(NoSuchMethodException e) { 
} catch(IllegalAccessException e) { 
} catch(InvocationTargetException e) { 
} 
*/ 
} 

} 
