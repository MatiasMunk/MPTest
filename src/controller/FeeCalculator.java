package controller; 

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

import model.Vehicle;

public class FeeCalculator {
	
	protected static final int MIN_YEAR = 1900;
	protected static final String EXCEP_MESSAGE_DATE_NULL = "Date should be not null!";
	protected static final String EXCEP_MESSAGE_DATE_TOO_OLD = "Year should be later than 1900!";
	protected static final String EXCEP_MESSAGE_DATE_EMPL_BEFORE = "Employment date should be before current date";
			
	
	public double calcFee(double baseMonthlyFee, int seniority, boolean hasElectric) {
		
		if (baseMonthlyFee<0 || seniority <0) throw new IllegalArgumentException();
		
		int discountPercentage = switch (seniority) {
			case 0 -> 0;
			case 1,2,3,4 -> 3;  
			case 5,6,7,8,9 -> 5;
			case 10 -> 8;
			default ->8;		
		};
		
		discountPercentage += (hasElectric ? 20 : 0);
		
		baseMonthlyFee *= (1 - discountPercentage / 100d);
		return baseMonthlyFee;
	}
	
	
	//This should be easier to test
	// it should be called with todayDate i.e. LocalDate.now
	public int calcSeniority(LocalDate employmentDate, LocalDate todayDate) {
		if (employmentDate == null || todayDate == null) {
			throw new IllegalArgumentException(EXCEP_MESSAGE_DATE_NULL);			
		}
		if (employmentDate.getYear()<MIN_YEAR || todayDate.getYear()<MIN_YEAR) {
			throw new IllegalArgumentException(EXCEP_MESSAGE_DATE_TOO_OLD);			
		}
		if (employmentDate.compareTo(todayDate) > 0) {
			throw new IllegalArgumentException(EXCEP_MESSAGE_DATE_EMPL_BEFORE);			
		}
		Period period = employmentDate.until(todayDate);
		int seniority = period.getYears();
		return seniority; 
	}
	
	public boolean calcGreen(List<Vehicle> vv) {
		
		if (vv == null) throw new IllegalArgumentException("Vehicle list must not be null.");		
		if (vv.isEmpty()) throw new IllegalArgumentException("Vehicle list must not be empty");
		
		boolean green = false;
		if (!vv.isEmpty()) {

			for(Vehicle v : vv) {
				if (v != null ) {
				  green = v.isElectric();
				} else {
					throw new IllegalArgumentException();
				}
				
				if(green == false)
					break;
			}
		}
		return green;
	}

}
