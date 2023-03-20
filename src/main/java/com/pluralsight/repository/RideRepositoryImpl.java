package com.pluralsight.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

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
	
	private Ride getSavedRide(Integer rideId) {
		return jdbcTemplate.queryForObject("SELECT * FROM ride WHERE id = ?", new RideRowMapper(), rideId);
	}
	
}
