package com.pluralsight.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import com.pluralsight.model.Ride;

@Repository("rideRepository")
public class RideRepositoryImpl implements RideRepository {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public List<Ride> getRides() {
		Ride ride = new Ride();
		ride.setName("Corner Canyon");
		ride.setDuration(120);
		List <Ride> rides = new ArrayList<>();
		rides.add(ride);
		return rides;
	}

	@Override
	public Ride createNewRide(Ride newRide) {
		/*jdbcTemplate.update("INSERT INTO ride(name, duration) VALUES(?,?)", 
				newRide.getName(), newRide.getDuration());*/
		
		SimpleJdbcInsert insert = new SimpleJdbcInsert(jdbcTemplate);
		// Define the table name to insert into, and columns whose values will provided
		insert.setTableName("ride");
		
		List<String> columns = new ArrayList<>();
		columns.add("name");
		columns.add("duration");
		insert.setColumnNames(columns);

		// Define the data of the new line to be inserted:
		Map<String, Object> rideData = new HashMap<>();
		rideData.put("name", newRide.getName());
		rideData.put("duration", newRide.getDuration());
		
		// Define the column whose value will generated automatically:
		insert.setGeneratedKeyName("id");
		
		Number key = insert.executeAndReturnKey(rideData);
		
		System.out.println("generated key for the inserted Ride : " + key);
		
		return null;
	}
	
}
