package com.pluralsight.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.pluralsight.model.Ride;
import com.pluralsight.repository.util.RideRowMapper;

@Repository("rideRepository")
public class RideRepositoryImpl implements RideRepository {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public List<Ride> getRides() {
		List<Ride> rides = jdbcTemplate.query("SELECT * FROM ride", new RideRowMapper());
		
		return rides;
	}

	@Override
	public Ride createNewRide(Ride newRide) {
		// The following pattern is to extract the auto-generated key into a key-holder object, so we used to create the Ride Object that reflect the data saved in the DB
		KeyHolder keyHolder = new GeneratedKeyHolder();
		
		jdbcTemplate.update(new PreparedStatementCreator() {
			
			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement ps = con.prepareStatement("INSERT INTO ride(name, duration) VALUES(?,?)", new String[] {"id"});
				ps.setString(1, newRide.getName());
				ps.setInt(2, newRide.getDuration());
				return ps;
			}
		}, keyHolder);
		
		// Get the Ride object saved in the DB:
		return getSavedRide(keyHolder.getKey().intValue());
	}
	
	@Override
	public Ride getRide(Integer rideId) {
		Ride ride = jdbcTemplate.queryForObject("SELECT * FROM ride WHERE id = ?", new RideRowMapper(), rideId);
		return ride;
	}
	
	private Ride getSavedRide(Integer rideId) {
		return jdbcTemplate.queryForObject("SELECT * FROM ride WHERE id = ?", new RideRowMapper(), rideId);
	}

	@Override
	public Ride updateRide(Ride updatedRide) {
		jdbcTemplate.update("UPDATE ride SET name = ?, duration = ? WHERE id = ?", 
				updatedRide.getName(), updatedRide.getDuration(), updatedRide.getId());
		
		return getRide(updatedRide.getId());
	}

	@Override
	public Object updateAll(List<Object[]> batchArgs) {
		return jdbcTemplate.batchUpdate("UPDATE ride SET ride_date = ? WHERE id = ?", batchArgs);
	}
	
}
