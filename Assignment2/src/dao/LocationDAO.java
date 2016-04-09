package dao;

import java.util.List;

import model.Location;

public interface LocationDAO {
	
	List<Location> getAllLocations();
	
	List<String> getAllCountries();
	
	List<String> getAllCities();
	
	Location getLocationById(int id);
	
	Location getLocationByName(String name);
	
	Location getLocationByCountry(String country);
	
	boolean createLocation(Location location);
	
	boolean updateLocation(Location location);
	
	boolean deleteLocation(Location location);
}
