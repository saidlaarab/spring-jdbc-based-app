package com.pluralsight.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
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
		jdbcTemplate.update("INSERT INTO ride(name, duration) VALUES(?,?)", 
				newRide.getName(), newRide.getDuration());
		
		return null;
	}
	
}
