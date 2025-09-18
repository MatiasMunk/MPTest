package db;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import model.ParkingSpot;

class ParkingSpotDBTest {
	
	private ParkingSpotDB psdb;

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
        cleanDB();
        createTablesDB();
        insertDataDB();
        psdb = new ParkingSpotDB();
    }

    // ---------- actual tests ----------
    @Test
    void testAvailable_1970_01_01_to_2025_09_15_1() throws Exception {
        LocalDate from = LocalDate.of(1970, 1, 1);
        LocalDate to = LocalDate.of(2025, 9, 15);

        List<ParkingSpot> available = psdb.findAvailable(from, to);

        assertTrue(available.stream().anyMatch(ps -> ps.getNumber() == 1), "ps1 should be available");
        assertTrue(available.stream().anyMatch(ps -> ps.getNumber() == 2), "ps2 should be available");
    }

    @Test
    void testAvailable_2025_09_15_to_2025_09_16_2() throws Exception {
        LocalDate from = LocalDate.of(2025, 9, 15);
        LocalDate to = LocalDate.of(2025, 9, 16);

        List<ParkingSpot> available = psdb.findAvailable(from, to);

        assertFalse(available.stream().anyMatch(ps -> ps.getNumber() == 1), "ps1 should NOT be available");
        assertTrue(available.stream().anyMatch(ps -> ps.getNumber() == 2), "ps2 should be available");
    }

    @Test
    void testAvailable_2025_09_16_to_2025_09_17_3() throws Exception {
        LocalDate from = LocalDate.of(2025, 9, 16);
        LocalDate to = LocalDate.of(2025, 9, 17);

        List<ParkingSpot> available = psdb.findAvailable(from, to);

        assertFalse(available.stream().anyMatch(ps -> ps.getNumber() == 1), "ps1 should NOT be available");
        assertTrue(available.stream().anyMatch(ps -> ps.getNumber() == 2), "ps2 should be available");
    }

    @Test
    void testAvailable_2025_12_17_to_2025_12_19_4() throws Exception {
        LocalDate from = LocalDate.of(2025, 12, 17);
        LocalDate to = LocalDate.of(2025, 12, 19);

        List<ParkingSpot> available = psdb.findAvailable(from, to);

        assertFalse(available.stream().anyMatch(ps -> ps.getNumber() == 1), "ps1 should NOT be available");
        assertTrue(available.stream().anyMatch(ps -> ps.getNumber() == 2), "ps2 should be available");
    }

    @Test
    void testAvailable_2025_12_19_to_2025_12_20_5() throws Exception {
        LocalDate from = LocalDate.of(2025, 12, 19);
        LocalDate to = LocalDate.of(2025, 12, 20);

        List<ParkingSpot> available = psdb.findAvailable(from, to);

        assertFalse(available.stream().anyMatch(ps -> ps.getNumber() == 1), "ps1 should NOT be available");
        assertFalse(available.stream().anyMatch(ps -> ps.getNumber() == 2), "ps2 should NOT be available");
    }

    @Test
    void testAvailable_2025_12_20_to_2025_12_21_6() throws Exception {
        LocalDate from = LocalDate.of(2025, 12, 20);
        LocalDate to = LocalDate.of(2025, 12, 21);

        List<ParkingSpot> available = psdb.findAvailable(from, to);

        assertFalse(available.stream().anyMatch(ps -> ps.getNumber() == 1), "ps1 should NOT be available");
        assertFalse(available.stream().anyMatch(ps -> ps.getNumber() == 2), "ps2 should NOT be available");
    }

    @Test
    void testAvailable_2025_12_21_to_2025_12_23_7() throws Exception {
        LocalDate from = LocalDate.of(2025, 12, 21);
        LocalDate to = LocalDate.of(2025, 12, 23);

        List<ParkingSpot> available = psdb.findAvailable(from, to);

        assertFalse(available.stream().anyMatch(ps -> ps.getNumber() == 1), "ps1 should NOT be available");
        assertFalse(available.stream().anyMatch(ps -> ps.getNumber() == 2), "ps2 should NOT be available");
    }

    @Test
    void testAvailable_2025_12_23_to_2025_12_24_8() throws Exception {
        LocalDate from = LocalDate.of(2025, 12, 23);
        LocalDate to = LocalDate.of(2025, 12, 24);

        List<ParkingSpot> available = psdb.findAvailable(from, to);

        assertFalse(available.stream().anyMatch(ps -> ps.getNumber() == 1), "ps1 should NOT be available");
        assertFalse(available.stream().anyMatch(ps -> ps.getNumber() == 2), "ps2 should NOT be available");
    }

    @Test
    void testAvailable_2025_12_24_to_2025_12_25_9() throws Exception {
        LocalDate from = LocalDate.of(2025, 12, 24);
        LocalDate to = LocalDate.of(2025, 12, 25);

        List<ParkingSpot> available = psdb.findAvailable(from, to);

        assertFalse(available.stream().anyMatch(ps -> ps.getNumber() == 1), "ps1 should be available");
        assertFalse(available.stream().anyMatch(ps -> ps.getNumber() == 2), "ps2 should NOT be available");
    }

    @Test
    void testAvailable_2025_12_25_to_2027_01_04_10() throws Exception {
        LocalDate from = LocalDate.of(2025, 12, 25);
        LocalDate to = LocalDate.of(2027, 1, 4);

        List<ParkingSpot> available = psdb.findAvailable(from, to);

        assertTrue(available.stream().anyMatch(ps -> ps.getNumber() == 1), "ps1 should be available");
        assertFalse(available.stream().anyMatch(ps -> ps.getNumber() == 2), "ps2 should NOT be available");
    }

    @Test
    void testAvailable_2027_01_04_to_2027_01_05_11() throws Exception {
        LocalDate from = LocalDate.of(2027, 1, 4);
        LocalDate to = LocalDate.of(2027, 1, 5);

        List<ParkingSpot> available = psdb.findAvailable(from, to);

        assertTrue(available.stream().anyMatch(ps -> ps.getNumber() == 1), "ps1 should be available");
        assertFalse(available.stream().anyMatch(ps -> ps.getNumber() == 2), "ps2 should NOT be available");
    }

    @Test
    void testAvailable_2027_01_05_to_2027_01_06_12() throws Exception {
        LocalDate from = LocalDate.of(2027, 1, 5);
        LocalDate to = LocalDate.of(2027, 1, 6);

        List<ParkingSpot> available = psdb.findAvailable(from, to);

        assertTrue(available.stream().anyMatch(ps -> ps.getNumber() == 1), "ps1 should be available");
        assertFalse(available.stream().anyMatch(ps -> ps.getNumber() == 2), "ps2 should NOT be available");
    }

    @Test
    void testAvailable_2027_01_06_to_2100_01_01_13() throws Exception {
        LocalDate from = LocalDate.of(2027, 1, 6);
        LocalDate to = LocalDate.of(2100, 1, 1);

        List<ParkingSpot> available = psdb.findAvailable(from, to);

        assertTrue(available.stream().anyMatch(ps -> ps.getNumber() == 1), "ps1 should be available");
        assertTrue(available.stream().anyMatch(ps -> ps.getNumber() == 2), "ps2 should be available");
    }

}
