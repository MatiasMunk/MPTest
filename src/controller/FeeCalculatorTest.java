package controller;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * Unit test class for the FeeCalculator methods as part of Exercise 3: Design of Unit Tests.
 * 
 * The following methods are tested using Equivalence Class Partitioning (ECP) 
 * and Boundary Value Analysis (BVA) techniques:
 * 
 * Each test is designed to verify correct behavior across valid input ranges, 
 * edge cases, and invalid inputs where applicable.
 * 
 * @author gruppe 4
 * @version 2025-09-17
 */

class FeeCalculatorTest {

	@Test
	void testCalcFee_case1() {
		//Arrange
	    FeeCalculator calc = new FeeCalculator();
	  	
	    //Act + Assert
	    assertThrows(IllegalArgumentException.class,
	        () -> calc.calcFee(-1, 0, false));
	}

	@Test
	void testCalcFee_case2() {
		//Arrange
	    FeeCalculator calc = new FeeCalculator();
	    
	    //Act + Assert
	    assertEquals(0.0, calc.calcFee(0, 0, false), 0.001);
	}
	
	@Test
	void testCalcFee_case3() {
		//Arrange
	    FeeCalculator calc = new FeeCalculator();
	    
	    //Act + Assert
	    assertEquals(1.0, calc.calcFee(1, 0, false), 0.001);
	}
	
	@Test
	void testCalcFee_case4() {
		//Arrange
	    FeeCalculator calc = new FeeCalculator();
	    
	    //Act + Assert
	    assertThrows(IllegalArgumentException.class,
	        () -> calc.calcFee(100, -1, false));
	}
	
	@Test
	void testCalcFee_case5() {
		//Arrange
	    FeeCalculator calc = new FeeCalculator();
	    
	    //Act + Assert
	    assertEquals(100, calc.calcFee(100, 0, false), 0.001);
	}
	
	@Test
	void testCalcFee_case6() {
		//Arrange
	    FeeCalculator calc = new FeeCalculator();
	    
	    //Act + Assert
	    assertEquals(97, calc.calcFee(100, 1, false), 0.001);
	}
	
	@Test
	void testCalcFee_case7() {
		//Arrange
	    FeeCalculator calc = new FeeCalculator();
	    
	    //Act + Assert
	    assertEquals(97, calc.calcFee(100, 2, false), 0.001);
	}
	
	@Test
	void testCalcFee_case8() {
		//Arrange
	    FeeCalculator calc = new FeeCalculator();
	    
	    //Act + Assert
	    assertEquals(97, calc.calcFee(100, 4, false), 0.001);
	}
	
	@Test
	void testCalcFee_case9() {
		//Arrange
	    FeeCalculator calc = new FeeCalculator();
	    
	    //Act + Assert
	    assertEquals(95, calc.calcFee(100, 5, false), 0.001);
	}
	
	@Test
	void testCalcFee_case10() {
		//Arrange
	    FeeCalculator calc = new FeeCalculator();
	    
	    //Act + Assert
	    assertEquals(95, calc.calcFee(100, 6, false), 0.001);
	}
	
	@Test
	void testCalcFee_case12() {
		//Arrange
	    FeeCalculator calc = new FeeCalculator();
	    
	    //Act + Assert
	    assertEquals(92, calc.calcFee(100, 10, false), 0.001);
	}
	
	@Test
	void testCalcFee_case13() {
		//Arrange
	    FeeCalculator calc = new FeeCalculator();
	    
	    //Act + Assert
	    assertEquals(92, calc.calcFee(100, 11, false), 0.001);
	}
	
	@Test
	void testCalcFee_case14() {
		//Arrange
	    FeeCalculator calc = new FeeCalculator();
	    
	    //Act + Assert
	    assertEquals(80, calc.calcFee(100, 0, true), 0.001);
	}
}
