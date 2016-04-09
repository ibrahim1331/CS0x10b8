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

import model.User;
import utils.DBHelper;

public class UserDAOImpl implements UserDAO {

	@Override
	public List<User> getAllCustomers() {
		ArrayList<User> customers = new ArrayList<User>();
		
		try {
            Connection con = DBHelper.getConnection();
	        Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = stmt.executeQuery("SELECT * FROM [user] WHERE role='1' ORDER BY [user_id] ASC");
           
            // populate the customer ArrayList
            populateUserArray(customers, rs);
            
            DBHelper.close(con);
            DBHelper.close(stmt);
            DBHelper.close(rs);
            
        } catch (SQLException ex) {
            Logger.getLogger(UserDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(UserDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
		
		return customers;
	}

	@Override
	public List<User> getAllManagers() {
		ArrayList<User> managers = new ArrayList<User>();
		
		try {
			Connection con = DBHelper.getConnection();
	        Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = stmt.executeQuery("SELECT * FROM [user] WHERE role='2' ORDER BY [user_id] ASC");
           
            // populate the managers ArrayList
            populateUserArray(managers, rs);
            
            DBHelper.close(con);
            DBHelper.close(stmt);
            DBHelper.close(rs);
            
        } catch (SQLException ex) {
            Logger.getLogger(UserDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(UserDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
		
		return managers;
	}
	
	@Override
	public List<User> getAllChiefManagers() {
		ArrayList<User> chief_managers = new ArrayList<User>();
		
		try {
			Connection con = DBHelper.getConnection();
	        Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = stmt.executeQuery("SELECT * FROM [user] WHERE role='3' ORDER BY [user_id] ASC");
           
            // populate the chief managers ArrayList
            populateUserArray(chief_managers, rs);
            
            DBHelper.close(con);
            DBHelper.close(stmt);
            DBHelper.close(rs);
            
        } catch (SQLException ex) {
            Logger.getLogger(UserDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(UserDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
		
		return chief_managers;
	}
	
	/**
	 * @param users
	 * @param rs
	 * @throws SQLException
	 */
	private void populateUserArray(ArrayList<User> users, ResultSet rs) throws SQLException {
		while(rs != null && rs.next()){
			User user = new User();
			user.setUserId(rs.getInt("user_id"));
			user.setTitle(rs.getString("title"));
			user.setFirstName(rs.getString("first_name"));
			user.setLastName(rs.getString("last_name"));
			user.setEmail(rs.getString("email"));
			user.setPassword(rs.getString("password"));
			user.setGender(rs.getString("gender"));
			user.setRole(rs.getInt("role"));
			user.setIsRegistered(rs.getBoolean("is_registered"));
			users.add(user);
		}
	}
	
	private User populateUser(ResultSet rs) throws SQLException{
		User user = null;
		
		if (rs != null && rs.next()) {
			user = new User();
        	user.setUserId(rs.getInt("user_id"));
        	user.setTitle(rs.getString("title"));
        	user.setFirstName(rs.getString("first_name"));
        	user.setLastName(rs.getString("last_name"));
        	user.setEmail(rs.getString("email"));
        	user.setPassword(rs.getString("password"));
        	user.setGender(rs.getString("gender"));
        	user.setRole(rs.getInt("role"));
        	user.setIsRegistered(rs.getBoolean("is_registered"));
        }
		
		return user;
	}
	
	@Override
	public User getUserById(Integer id) {
		User user = null;
		
		try {
            Connection con = DBHelper.getConnection();
            PreparedStatement pstmt = con.prepareStatement("SELECT * FROM [user] WHERE [user_id] = ?");
            pstmt.setInt(1, id);
            
            // execute the SQL statement
            ResultSet rs= pstmt.executeQuery();
            
            user = this.populateUser(rs);
            
            DBHelper.close(con);
            DBHelper.close(pstmt);
            DBHelper.close(rs);
        } catch (SQLException ex) {
        	user = null;
        	Logger.getLogger(UserDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
        	user = null;
        	Logger.getLogger(UserDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
		
		return user;
	}
	
	@Override
	public User getUser(String email, String password) {
		User user = null;
		
		try {
            Connection con = DBHelper.getConnection();
            PreparedStatement pstmt = con.prepareStatement("SELECT * FROM [user] WHERE [email] = ? AND [password] = ?");
            pstmt.setString(1, email);
            pstmt.setString(2, password);
            
            // execute the SQL statement
            ResultSet rs= pstmt.executeQuery();
            
            user = this.populateUser(rs);
            
            DBHelper.close(con);
            DBHelper.close(pstmt);
            DBHelper.close(rs);
        } catch (SQLException ex) {
        	user = null;
        	Logger.getLogger(UserDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
        	user = null;
        	Logger.getLogger(UserDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
		
		return user;
	}
	
	@Override
	public User getUser(String email) {
		User user = null;
		
		try {
			Connection con = DBHelper.getConnection();
            PreparedStatement pstmt = con.prepareStatement("SELECT * FROM [user] WHERE [email] = ?");
            pstmt.setString(1, email);
            
            // execute the SQL statement
            ResultSet rs= pstmt.executeQuery();
            
            user = this.populateUser(rs);
            
            DBHelper.close(con);
            DBHelper.close(pstmt);
            DBHelper.close(rs);
        } catch (SQLException ex) {
        	user = null;
        	Logger.getLogger(UserDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
        	user = null;
        	Logger.getLogger(UserDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
		
		return user;
	}
	
	@Override
	public boolean createUser(User user) {
		boolean saved = false;
		
		try {
            if (user != null) {
            	Connection con = DBHelper.getConnection();
            	PreparedStatement pstmt = con.prepareStatement("INSERT INTO [user] ([title], [first_name], [last_name], [gender], [email], [password], [role], [is_registered]) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
                pstmt.setString(1, user.getTitle());
                pstmt.setString(2, user.getFirstName());
                pstmt.setString(3, user.getLastName());
                pstmt.setString(4, user.getGender());
                pstmt.setString(5, user.getEmail());
                pstmt.setString(6, user.getPassword());
                pstmt.setInt(7, user.getRole().getValue());
                pstmt.setBoolean(8, user.getIsRegistered());
                
                // execute the SQL statement
                int rows = pstmt.executeUpdate();

                if (rows > 0) {
                	saved = true;
                }
                
                DBHelper.close(con);
            }
        } catch (SQLException ex) {
        	Logger.getLogger(UserDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
        	Logger.getLogger(UserDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
		
		return saved;
	}
	
	@Override
	public boolean updateUser(User user) {
		boolean updated = false;
		
		try {
            if (user != null) {
            	Connection con = DBHelper.getConnection();
                PreparedStatement pstmt = con.prepareStatement("UPDATE [user] SET [title]= ?, [first_name]= ?, [last_name]= ?, [gender]= ?, [email]= ?, [password]= ?, [role]= ?, [is_registered]= ? WHERE [user_id] = ?");
                pstmt.setString(1, user.getTitle());
                pstmt.setString(2, user.getFirstName());
                pstmt.setString(3, user.getLastName());
                pstmt.setString(4, user.getGender());
                pstmt.setString(5, user.getEmail());
                pstmt.setString(6, user.getPassword());
                pstmt.setInt(7, user.getRole().getValue());
                pstmt.setBoolean(8, user.getIsRegistered());
                pstmt.setInt(9, user.getUserId());
                
                // execute the SQL statement
                int rows = pstmt.executeUpdate();

                if (rows > 0) {
                	updated = true;
                }
                
                DBHelper.close(con);
                DBHelper.close(pstmt);
            }
        } catch (SQLException ex) {
        	Logger.getLogger(UserDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
        	Logger.getLogger(UserDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
		
		return updated;
	}

	@Override
	public boolean deleteUser(User user) {
		boolean deleted = false;
		
		try {
            if (user != null) {
            	Connection con = DBHelper.getConnection();
                PreparedStatement pstmt = con.prepareStatement("DELETE FROM [user] WHERE [user_id] = ?");
                pstmt.setInt(1, user.getUserId());
                
                // execute the SQL statement
                int rows = pstmt.executeUpdate();

                if (rows > 0) {
                	deleted = true;
                }
                
                DBHelper.close(con);
                DBHelper.close(pstmt);
            }
        } catch (SQLException ex) {
        	Logger.getLogger(UserDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
        	Logger.getLogger(UserDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
		
		return deleted;
	}



}
