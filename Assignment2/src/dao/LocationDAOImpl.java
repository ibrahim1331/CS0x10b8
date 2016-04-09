package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import model.Location;
import utils.DBHelper;

public class LocationDAOImpl implements LocationDAO{

	@Override
	public List<Location> getAllLocations() {
		ArrayList<Location> locations = new ArrayList<Location>();
		
		try {
            Connection con = DBHelper.getConnection();
	        Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = stmt.executeQuery("SELECT * FROM [location] ORDER BY [location_id] ASC");
           
            // populate the locations ArrayList
            populateLocationArray(locations, rs);
            
            DBHelper.close(con);
            DBHelper.close(stmt);
            DBHelper.close(rs);
            
        } catch (SQLException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }
		
		return locations;
	}

	@Override
	public List<String> getAllCountries() {
		ArrayList<String> countries = new ArrayList<String>();
		
		try {
			//	Get all the locations			
			ArrayList<Location> locations = (ArrayList<Location>) getAllLocations();
			
			//	Populate the countries list			
            for(Location location: locations){
            	countries.add(location.getCountry());
            }
        } catch (Exception ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }
		
		return countries;
	}

	@Override
	public List<String> getAllCities() {
		ArrayList<String> cities = new ArrayList<String>();
		
		try {
			//	Get all the locations			
			ArrayList<Location> locations = (ArrayList<Location>) getAllLocations();
			
			//	Populate the countries list			
            for(Location location: locations){
            	cities.add(location.getName());
            }
        } catch (Exception ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }
		
		return cities;
	}

	@Override
	public Location getLocationById(int id) {
		Location location = null;
		try {
            Connection con = DBHelper.getConnection();
            PreparedStatement pstmt = con.prepareStatement("SELECT * FROM [location] WHERE [location_id] = ?");
            pstmt.setInt(1, id);
            
            // execute the SQL statement
            ResultSet rs= pstmt.executeQuery();
            
            // populate the locations ArrayList
            location = populateLocation(rs);
            
            DBHelper.close(con);
            DBHelper.close(pstmt);
            DBHelper.close(rs);
            
        } catch (SQLException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }
		
		return location;
	}

	@Override
	public Location getLocationByName(String name) {
		Location location = null;
		
		try {
            Connection con = DBHelper.getConnection();
            PreparedStatement pstmt = con.prepareStatement("SELECT * FROM [location] WHERE [name] = ?");
            pstmt.setString(1, name);
            
            // execute the SQL statement
            ResultSet rs= pstmt.executeQuery();
            
            // populate the locations ArrayList
            location = populateLocation(rs);
            
            DBHelper.close(con);
            DBHelper.close(pstmt);
            DBHelper.close(rs);
            
        } catch (SQLException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }
		
		return location;
	}

	@Override
	public Location getLocationByCountry(String country) {
		Location location = null;
		
		try {
            Connection con = DBHelper.getConnection();
            PreparedStatement pstmt = con.prepareStatement("SELECT * FROM [location] WHERE [country] = ?");
            pstmt.setString(1, country);
            
            // execute the SQL statement
            ResultSet rs= pstmt.executeQuery();
            
            // populate the locations ArrayList
            location = populateLocation(rs);
            
            DBHelper.close(con);
            DBHelper.close(pstmt);
            DBHelper.close(rs);
            
        } catch (SQLException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }
		
		return location;
	}
	
	private Location populateLocation(ResultSet rs) throws SQLException{
		Location location = null;
		
		if (rs != null && rs.next()) {
			location = new Location();
			location.setLocationId(rs.getInt("location_id"));
			location.setName(rs.getString("name"));
			location.setCountry(rs.getString("country"));
        }
		
		return location;
	}
	
	private void populateLocationArray(ArrayList<Location> locations, ResultSet rs) throws SQLException {
		while(rs != null && rs.next()){
			Location location = new Location();
			location.setLocationId(rs.getInt("location_id"));
			location.setName(rs.getString("name"));
			location.setCountry(rs.getString("country"));
			locations.add(location);
		}
	}

	@Override
	public boolean createLocation(Location location) {
		boolean saved = false;
		
		try {
            if (location != null) {
            	Connection con = DBHelper.getConnection();
            	PreparedStatement pstmt = con.prepareStatement("INSERT INTO [location] ([name], [country]) VALUES (?, ?)");
                pstmt.setString(1, location.getName());
                pstmt.setString(2, location.getCountry());
                
                // execute the SQL statement
                int rows = pstmt.executeUpdate();

                if (rows > 0) {
                	saved = true;
                }
                
                DBHelper.close(pstmt);
                DBHelper.close(con);
            }
        } catch (SQLException ex) {
        	Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
        	Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }
		
		return saved;
	}

	@Override
	public boolean updateLocation(Location location) {
		boolean updated = false;
		
		try {
            if (location != null) {
            	Connection con = DBHelper.getConnection();
            	PreparedStatement pstmt = con.prepareStatement("UPDATE [location] SET [name] = ?, [country] = ? WHERE [location_id] = ?;");
                pstmt.setString(1, location.getName());
                pstmt.setString(2, location.getCountry());
                pstmt.setInt(3, location.getLocationId());
                
                // execute the SQL statement
                int rows = pstmt.executeUpdate();

                if (rows > 0) {
                	updated = true;
                }
                
                DBHelper.close(pstmt);
                DBHelper.close(con);
            }
        } catch (SQLException ex) {
        	Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
        	Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }
		
		return updated;
	}

	@Override
	public boolean deleteLocation(Location location) {
		boolean deleted = false;
		
		try {
            if (location != null) {
            	Connection con = DBHelper.getConnection();
            	PreparedStatement pstmt = con.prepareStatement("DELETE FROM [location] WHERE [location_id] = ?;");
                pstmt.setInt(1, location.getLocationId());
                
                // execute the SQL statement
                int rows = pstmt.executeUpdate();

                if (rows > 0) {
                	deleted = true;
                }
                
                DBHelper.close(pstmt);
                DBHelper.close(con);
            }
        } catch (SQLException ex) {
        	Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
        	Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }
		
		return deleted;
	}

}
