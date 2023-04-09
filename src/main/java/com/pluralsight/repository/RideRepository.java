package com.pluralsight.repository;

import java.util.List;

import com.pluralsight.model.Ride;

public interface RideRepository {

	List<Ride> getRides();
	
	Ride createNewRide(Ride newRide);
	
	Ride getRide(Integer id);

	Ride updateRide(Ride updatedRide);

	Object updateAll(List<Object[]> batchArgs);

	Object deleteById(Integer id);

}