package db;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.Employee;
import model.ParkingSpot;
import model.RentalAgreement;

class RentalAgreementDBTest {

    private RentalAgreementDB raDB;
    private ParkingSpotDB psDB;
    private EmployeeDB empDB;

    private Employee employee1;
    private ParkingSpot ps1;
    private ParkingSpot ps2;

    public static void cleanDB() throws SQLException, IOException, DataAccessException {

		try (Statement stmt = DBConnection.getInstance().getConnection().createStatement()) {
			String sqlClean = readAllBytesJava("scripts/mptestDropTables.sql");
			stmt.executeUpdate(sqlClean);
		}
	}

	public static void createTablesDB() throws SQLException, IOException, DataAccessException {
		try (Statement stmt = DBConnection.getInstance().getConnection().createStatement()) {

			String sqlRestore = readAllBytesJava("scripts/mptestCreateTables.sql");
			stmt.executeUpdate(sqlRestore);

		}
	}

	public static void insertDataDB() throws SQLException, IOException, DataAccessException {
	    String sqlAll = """
	        -- Employees
	        SET IDENTITY_INSERT employee ON;
	        insert into employee (id, initials, fname, lname, employmentdate) values
	        (1, 'fml', 'Fedt', 'Mule', '2018-07-01'),
	        (2, 'asa', 'Andersine', 'And', '2021-05-01');
	        SET IDENTITY_INSERT employee OFF;

	        -- Parking spots
	        SET IDENTITY_INSERT parkingspot ON;
	        insert into parkingspot (id, number, basemonthlyfee) values
	        (1, 1, 100.00),
	        (2, 2, 150.0);
	        SET IDENTITY_INSERT parkingspot OFF;

	        -- Vehicles
	        SET IDENTITY_INSERT vehicle ON;
	        insert into vehicle (id, numberplate, electric, registrationdate, employee_id) values
	        (1, 'FM12123', 1, '2023-01-15', 1),
	        (2, 'AN69699', 1, '2022-05-01', 2);
	        SET IDENTITY_INSERT vehicle OFF;

	        -- Rent agreements
	        SET IDENTITY_INSERT rentagreement ON;
	        insert into rentagreement (id, startdate, enddate, monthlyfee, employee_id, parkingspot_id) values
	        (1, '2025-09-16', '2025-12-24', 250.0, 1, 1),
	        (2, '2025-12-20', '2027-01-05', 300.0, 2, 2);
	        SET IDENTITY_INSERT rentagreement OFF;
	    """;

	    try (Statement stmt = DBConnection.getInstance().getConnection().createStatement()) {
	        // Split by semicolon to run each statement safely
	        for (String sql : sqlAll.split(";")) {
	            if (!sql.trim().isEmpty()) {
	                stmt.executeUpdate(sql);
	            }
	        }
	    }
	}

	// Read file content into string with - Files.readAllBytes(Path path)

	private static String readAllBytesJava(String filePath) throws IOException {
		String content = "";
		content = new String(Files.readAllBytes(Paths.get(filePath)));
		return content;
	}
	
	// ---------- JUnit lifecycle ----------
    @BeforeEach
    void setup() throws Exception {
        // Reset DB
    	cleanDB();
        createTablesDB();
        insertDataDB();

        // Initialize DAOs
        raDB = new RentalAgreementDB();
        psDB = new ParkingSpotDB();
        empDB = new EmployeeDB();

        // Load existing objects
        employee1 = empDB.findById(1, true); // fedtmule
        ps1 = psDB.findById(1);
        ps2 = psDB.findById(2);
    }

    

    @Test
    void testSave_nullRentalAgreement() {
    	//AAA
        assertThrows(NullPointerException.class, () -> raDB.save(null));
    }
    
    @Test
    void testSave_RentalAgreement() throws Exception {
        // Arrange
        RentalAgreement ra = new RentalAgreement(employee1);
        ra.setParkingSpot(ps1);
        ra.setStartDate(LocalDate.parse("1970-01-01"));
        ra.setEndDate(LocalDate.parse("2025-09-15"));
        ra.setMonthlyFee(50);

        // Act
        assertTrue(raDB.save(ra), "RentalAgreement should be saved successfully");

        // Assert: fetch inserted row from DB and check values
        try (Statement stmt = DBConnection.getInstance().getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(
                     "SELECT startdate, enddate, monthlyfee, employee_id, parkingspot_id " +
                     "FROM rentagreement WHERE id = " + ra.getId())) {

            assertTrue(rs.next(), "RentalAgreement not found in database");

            // Compare start and end dates
            assertEquals(ra.getStartDate(), rs.getDate("startdate").toLocalDate(), "Start date mismatch");
            assertEquals(ra.getEndDate(), rs.getDate("enddate").toLocalDate(), "End date mismatch");

            // Compare monthly fee numerically to avoid 50.0 vs 50.00 issue
            assertEquals(ra.getMonthlyFee(), rs.getDouble("monthlyfee"), 0.001, "Monthly fee mismatch");

            // Compare foreign keys
            assertEquals(ra.getEmployee().getId(), rs.getInt("employee_id"), "Employee ID mismatch");
            assertEquals(ra.getParkingSpot().getId(), rs.getInt("parkingspot_id"), "ParkingSpot ID mismatch");
        }
    }
}
