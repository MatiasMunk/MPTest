package db;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import model.ParkingSpot;

public class ParkingSpotDB implements ParkingSpotDAO {
	private static final String SELECT_ALL = "select parkingspot.id, number, baseMonthlyFee from parkingspot";	
		
	private static final String SELECT_BY_AVAILABILITY = SELECT_ALL + " where not exists (" 
			+ " select 1 from rentagreement"
			+ " where parkingspot.id = rentagreement.parkingspot_id" 
			+ " and enddate >= ?"// fromdate
			+ " and rentagreement.startdate <= ?)";// todate
	
	private static final String SELECT_BY_ID = SELECT_ALL + " where id = ?";
	private PreparedStatement selectByAvailability;
	private PreparedStatement selectById;

	public ParkingSpotDB() throws DataAccessException {
		try {
			selectByAvailability = DBConnection.getInstance().getConnection().prepareStatement(SELECT_BY_AVAILABILITY);
			selectById = DBConnection.getInstance().getConnection().prepareStatement(SELECT_BY_ID);
		} catch (SQLException e) {
			throw new DataAccessException("could not prepare statements for parkingspot", e);
		}
	}

	@Override
	public List<ParkingSpot> findAvailable(LocalDate fromDate, LocalDate toDate)
			throws DataAccessException {
	
		List<ParkingSpot> pp = null;	
		if (fromDate.compareTo(toDate) > 0) {
			pp = new ArrayList<>(); //return empty list
		} else {
			try {
				selectByAvailability.setDate(1, Date.valueOf(fromDate));
				selectByAvailability.setDate(2, Date.valueOf(toDate));  
				ResultSet rs = selectByAvailability.executeQuery();
				pp = buildObjects(rs);
			} catch (SQLException e) {
				throw new DataAccessException("could not bind params or execute query for available parkinspots", e);
			}
		}
		return pp;
	}
	

	@Override
	public ParkingSpot findById(int id) throws DataAccessException {
		try {
			selectById.setInt(1, id);
			ResultSet rs = selectById.executeQuery();
			ParkingSpot res = buildObject(rs);
			return res;
		} catch (SQLException e) {
			throw new DataAccessException("Could not find parking spot by ID", e);
		}
		
	}

	private List<ParkingSpot> buildObjects(ResultSet rs) throws DataAccessException {
		List<ParkingSpot> res = new ArrayList<>();
		ParkingSpot ps = buildObject(rs);
		while (ps != null) {
			res.add(ps);
			ps = buildObject(rs);
		}
		return res;
	}

	private ParkingSpot buildObject(ResultSet rs) throws DataAccessException {
		ParkingSpot ps = null;
		try {
			if (rs.next()) {
				ps = new ParkingSpot(rs.getInt("id"), rs.getInt("id"), rs.getDouble("baseMonthlyFee")); 
			}
		} catch (SQLException e) {
			throw new DataAccessException("could not read result set for parkingspot", e);
		}
		return ps;
	}


}
