package controller;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import model.Vehicle;

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
	
	//calcFee() tests
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
	
	//calcSeniority() tests
	@Test
	void testCalcSeniority_case1() {
		//Arrange
		FeeCalculator calc = new FeeCalculator();
		
		LocalDate employmentDate = LocalDate.parse("2025-09-16");
		LocalDate todayDate = LocalDate.parse("2025-09-15");
		
		//Act + Assert
	    assertThrows(IllegalArgumentException.class,
	        () -> calc.calcSeniority(employmentDate, todayDate));
		
	}
	
	@Test
	void testCalcSeniority_case2() {
		//Arrange
		FeeCalculator calc = new FeeCalculator();
		
		LocalDate employmentDate = LocalDate.parse("2025-09-15");
		LocalDate todayDate = LocalDate.parse("2025-09-15");
		
		//Act + Assert
		assertEquals(0, calc.calcSeniority(employmentDate, todayDate), 0.001);
		
	}
	
	@Test
	void testCalcSeniority_case3() {
		//Arrange
		FeeCalculator calc = new FeeCalculator();
		
		LocalDate employmentDate = LocalDate.parse("2025-09-14");
		LocalDate todayDate = LocalDate.parse("2025-09-15");
		
		//Act + Assert
		assertEquals(0, calc.calcSeniority(employmentDate, todayDate), 0.001);
	}
	
	@Test
	void testCalcSeniority_case4() {
		//Arrange
		FeeCalculator calc = new FeeCalculator();
		
		LocalDate employmentDate = LocalDate.parse("2024-09-16");
		LocalDate todayDate = LocalDate.parse("2025-09-15");
		
		//Act + Assert
		assertEquals(0, calc.calcSeniority(employmentDate, todayDate), 0.001);
	}
	
	@Test
	void testCalcSeniority_case5() {
		//Arrange
		FeeCalculator calc = new FeeCalculator();
		
		LocalDate employmentDate = LocalDate.parse("2024-09-15");
		LocalDate todayDate = LocalDate.parse("2025-09-15");
		
		//Act + Assert
		assertEquals(1, calc.calcSeniority(employmentDate, todayDate), 0.001);
	}
	
	@Test
	void testCalcSeniority_case6() {
		//Arrange
		FeeCalculator calc = new FeeCalculator();
		
		LocalDate employmentDate = LocalDate.parse("2024-09-14");
		LocalDate todayDate = LocalDate.parse("2025-09-15");
		
		//Act + Assert
		assertEquals(1, calc.calcSeniority(employmentDate, todayDate), 0.001);
	}
	
	//calcGreen tests
	@Test
	void testCalcGreen_case1() {
		//Arrange
		FeeCalculator calc = new FeeCalculator();
		
		List<Vehicle> vv = new ArrayList<>();
		Vehicle g = new Vehicle(1, "", true, LocalDate.now());
		vv.add(g);
		
		Vehicle n = new Vehicle(1, "", false, LocalDate.now());;
		vv.add(n);
		
		Vehicle n2 = new Vehicle(1, "", false, LocalDate.now());;
		vv.add(n2);
		
		//Act + Assert
		assertFalse(calc.calcGreen(vv));
	}
	
	@Test
	void testCalcGreen_case2() {
		//Arrange
		FeeCalculator calc = new FeeCalculator();
		
		List<Vehicle> vv = new ArrayList<>();
		Vehicle g = new Vehicle(1, "", true, LocalDate.now());
		vv.add(g);
		
		Vehicle n = new Vehicle(1, "", true, LocalDate.now());;
		vv.add(n);
		
		Vehicle n2 = new Vehicle(1, "", false, LocalDate.now());;
		vv.add(n2);
		
		//Act + Assert
		assertFalse(calc.calcGreen(vv));
	}
	
	@Test
	void testCalcGreen_case3() {
		//Arrange
		FeeCalculator calc = new FeeCalculator();
		
		List<Vehicle> vv = new ArrayList<>();
		Vehicle g = new Vehicle(1, "", true, LocalDate.now());
		vv.add(g);
		
		Vehicle n = new Vehicle(1, "", true, LocalDate.now());;
		vv.add(n);
		
		Vehicle n2 = new Vehicle(1, "", true, LocalDate.now());;
		vv.add(n2);
		
		//Act + Assert
		assertTrue(calc.calcGreen(vv));
	}
	
	@Test
	void testCalcGreen_case4() {
		//Arrange
		FeeCalculator calc = new FeeCalculator();
		
		List<Vehicle> vv = new ArrayList<>();
		Vehicle g = new Vehicle(1, "", true, LocalDate.now());
		vv.add(g);
		
		Vehicle n = new Vehicle(1, "", false, LocalDate.now());;
		vv.add(n);
		
		//Act + Assert
		assertFalse(calc.calcGreen(vv));
	}
	
	@Test
	void testCalcGreen_case5() {
		//Arrange
		FeeCalculator calc = new FeeCalculator();
		
		List<Vehicle> vv = new ArrayList<>();
		Vehicle g = new Vehicle(1, "", true, LocalDate.now());
		vv.add(g);
		
		Vehicle g2 = new Vehicle(1, "", true, LocalDate.now());;
		vv.add(g2);
		
		//Act + Assert
		assertTrue(calc.calcGreen(vv));
	}
	
	@Test
	void testCalcGreen_case6() {
		//Arrange
		FeeCalculator calc = new FeeCalculator();
		
		List<Vehicle> vv = new ArrayList<>();
		Vehicle g = new Vehicle(1, "", true, LocalDate.now());
		vv.add(g);
		
		//Act + Assert
		assertTrue(calc.calcGreen(vv));
	}
	
	@Test
	void testCalcGreen_case7() {
		//Arrange
		FeeCalculator calc = new FeeCalculator();
		
		List<Vehicle> vv = new ArrayList<>();
		Vehicle n = new Vehicle(1, "", false, LocalDate.now());
		vv.add(n);
		
		//Act + Assert
		assertFalse(calc.calcGreen(vv));
	}
	
	@Test
	void testCalcGreen_case8() {
		//Arrange
		FeeCalculator calc = new FeeCalculator();
		
		List<Vehicle> vv = null;
		
		//Act + Assert
		assertThrows(IllegalArgumentException.class,
		() -> calc.calcGreen(vv));
	}
	
	@Test
	void testCalcGreen_case9() {
		//Arrange
		FeeCalculator calc = new FeeCalculator();
		
		List<Vehicle> vv = new ArrayList<>();
		
		//Act + Assert
		assertThrows(IllegalArgumentException.class,
		() -> calc.calcGreen(vv));
	}
}
