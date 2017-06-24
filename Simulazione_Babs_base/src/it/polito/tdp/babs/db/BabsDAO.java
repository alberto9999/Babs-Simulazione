package it.polito.tdp.babs.db;

import it.polito.tdp.babs.model.Station;
import it.polito.tdp.babs.model.Trip;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class BabsDAO {

	public List<Station> getAllStations() {
		List<Station> result = new ArrayList<Station>();
		Connection conn = DBConnect.getInstance().getConnection();
		String sql = "SELECT * FROM station";

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				Station station = new Station(rs.getInt("station_id"), rs.getString("name"), rs.getDouble("lat"), rs.getDouble("long"), rs.getInt("dockcount"));
				result.add(station);
			}
			st.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Error in database query", e);
		}

		return result;
	}

	public List<Trip> getAllTrips() {
		List<Trip> result = new LinkedList<Trip>();
		Connection conn = DBConnect.getInstance().getConnection();
		String sql = "SELECT * FROM trip";

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				Trip trip = new Trip(rs.getInt("tripid"), rs.getInt("duration"), rs.getTimestamp("startdate").toLocalDateTime(), rs.getInt("startterminal"),
						rs.getTimestamp("enddate").toLocalDateTime(), rs.getInt("endterminal"));
				result.add(trip);
			}
			st.close();
			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Error in database query", e);
		}

		return result;
	}

	public void setCountArrivi(LocalDate data, Station station) {
	
	
		Connection conn = DBConnect.getInstance().getConnection();
		String sql = "SELECT count(*) "+
		             "FROM trip "+
		              "WHERE EndTerminal=? AND DATE(EndDate)=?";
		int result=0;

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, station.getStationID());
			st.setDate(2, Date.valueOf(data));
			ResultSet rs = st.executeQuery();
			if (rs.next()) {
				result=rs.getInt("count(*)");
			}
			st.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Error in database query", e);
		}

		station.setTripArrivo(result);
		
	}

	public void setCountPartenze(LocalDate data, Station station) {
		
		Connection conn = DBConnect.getInstance().getConnection();
		String sql = "SELECT count(*) "+
		             "FROM trip "+
		              "WHERE StartTerminal=? AND DATE(StartDate)=?";
		int result=0;

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, station.getStationID());
			st.setDate(2, Date.valueOf(data));
			ResultSet rs = st.executeQuery();
			if (rs.next()) {
				result=rs.getInt("count(*)");
			}
			st.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Error in database query", e);
		}

		station.setTripPartenza(result);
	}

	public List<Trip> getAllTripsDataPartenza(LocalDate data) {
		List<Trip> result = new LinkedList<Trip>();
		Connection conn = DBConnect.getInstance().getConnection();
		String sql = "SELECT * FROM trip WHERE StartDate=? ";

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			st.setDate(1, Date.valueOf(data));
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				Trip trip = new Trip(rs.getInt("tripid"), rs.getInt("duration"), rs.getTimestamp("startdate").toLocalDateTime(), rs.getInt("startterminal"),
						rs.getTimestamp("enddate").toLocalDateTime(), rs.getInt("endterminal"));
				result.add(trip);
			}
			st.close();
			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Error in database query", e);
		}

		return result;
	}

	public List<Trip> getAllTripsDataArrivo(LocalDate data) {
		List<Trip> result = new LinkedList<Trip>();
		Connection conn = DBConnect.getInstance().getConnection();
		String sql = "SELECT * FROM trip WHERE EndDate=? ";

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			st.setDate(1, Date.valueOf(data));
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				Trip trip = new Trip(rs.getInt("tripid"), rs.getInt("duration"), rs.getTimestamp("startdate").toLocalDateTime(), rs.getInt("startterminal"),
						rs.getTimestamp("enddate").toLocalDateTime(), rs.getInt("endterminal"));
				result.add(trip);
			}
			st.close();
			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Error in database query", e);
		}

		return result;
	}
}


