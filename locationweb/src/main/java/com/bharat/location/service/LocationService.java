package com.bharat.location.service;

import java.util.List;

import com.bharat.location.entities.Location;

public interface LocationService {
	
	Location saveLocation(Location location);

	Location updateLocation(Location location);
	
	void deleteLocation(Location location);
	
	Location getLocation(Integer id);
	
	List<Location> getAllLocations();
}
