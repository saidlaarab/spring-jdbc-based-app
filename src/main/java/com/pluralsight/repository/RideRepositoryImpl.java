package com.pluralsight.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.pluralsight.model.Ride;

@Repository("rideRepository")
public class RideRepositoryImpl implements RideRepository {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public List<Ride> getRides() {
		List<Ride> rides = jdbcTemplate.query("SELECT * FROM ride", new RowMapper<Ride>(){
			@Override
			public Ride mapRow(ResultSet rs, int rowNum) throws SQLException {
				Ride ride = new Ride();
				ride.setId(rs.getInt("id"));
				ride.setName(rs.getString("name"));
				ride.setDuration(rs.getInt("duration"));
				
				return ride;
			}
		});
		
		return rides;
	}

	@Override
	public Ride createNewRide(Ride newRide) {
		jdbcTemplate.update("INSERT INTO ride(name, duration) VALUES(?,?)", 
				newRide.getName(), newRide.getDuration());
		
		return null;
	}
	
}
