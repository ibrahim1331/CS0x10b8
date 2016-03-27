package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import model.Customer;

public class CustomerDAOImpl implements CustomerDAO {

	@Override
	public List<Customer> getAllCustomers() {
		ArrayList<Customer> customers = new ArrayList<Customer>();
		
		try {
//            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Context initCtx = new InitialContext();
            Context envCtx = (Context)initCtx.lookup("java:comp/env");
            DataSource ds = (DataSource)envCtx.lookup("jdbc/hotelbooking");
            Connection con = ds.getConnection();
//            Connection con = DriverManager.getConnection("jdbc:sqlserver://w2ksa.cs.cityu.edu.hk:1433;databaseName=aiad053_db", "aiad053", "aiad053");
	        Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = stmt.executeQuery("SELECT * FROM [customer] ORDER BY [customer_id] ASC");
           
            // populate the customer ArrayList
            while(rs != null && rs.next()){
            	Customer customer = new Customer();
            	customer.setCustomerId(rs.getInt("customer_id"));
            	customer.setTitle(rs.getString("title"));
            	customer.setFirstName(rs.getString("first_name"));
            	customer.setLastName(rs.getString("last_name"));
            	customer.setEmail(rs.getString("email"));
            	customer.setPassword(rs.getString("password"));
            	customer.setIsRegistered(rs.getBoolean("is_registered"));
            	customers.add(customer);
            }
            
            if (rs != null) {
                rs.close();
            }
            if (stmt != null) {
                stmt.close();
            }
            if (con != null) {
                con.close();
            }
            
            return customers;
        } catch (SQLException ex) {
            Logger.getLogger(CustomerDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(CustomerDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
		
		return null;
	}

	@Override
	public Customer getCustomerById(Integer id) {
		Customer customer = null;
		
		try {
//            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
//            Connection con = DriverManager.getConnection("jdbc:sqlserver://w2ksa.cs.cityu.edu.hk:1433;databaseName=aiad053_db", "aiad053", "aiad053");
			Context initCtx = new InitialContext();
            Context envCtx = (Context)initCtx.lookup("java:comp/env");
            DataSource ds = (DataSource)envCtx.lookup("jdbc/hotelbooking");
            Connection con = ds.getConnection();
            PreparedStatement pstmt = con.prepareStatement("SELECT * FROM [customer] WHERE [customer_id] = ?");
            pstmt.setInt(1, id);
            
            // execute the SQL statement
            ResultSet rs= pstmt.executeQuery();

            if (rs != null && rs.next()) {
            	customer = new Customer();
            	customer.setCustomerId(rs.getInt("customer_id"));
            	customer.setTitle(rs.getString("title"));
            	customer.setFirstName(rs.getString("first_name"));
            	customer.setLastName(rs.getString("last_name"));
            	customer.setEmail(rs.getString("email"));
            	customer.setPassword(rs.getString("password"));
            	customer.setIsRegistered(rs.getBoolean("is_registered"));
            }
            
            if (rs != null) {
                rs.close();
            }
            if (pstmt != null) {
            	pstmt.close();
            }
            if (con != null) {
                con.close();
            }
        } catch (SQLException ex) {
        	customer = null;
        	Logger.getLogger(CustomerDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
        	customer = null;
        	Logger.getLogger(CustomerDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
		
		return customer;
	}
	
	@Override
	public Customer getCustomer(String email, String password) {
		Customer customer = null;
		
		try {
//            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
//            Connection con = DriverManager.getConnection("jdbc:sqlserver://w2ksa.cs.cityu.edu.hk:1433;databaseName=aiad053_db", "aiad053", "aiad053");
			Context initCtx = new InitialContext();
            Context envCtx = (Context)initCtx.lookup("java:comp/env");
            DataSource ds = (DataSource)envCtx.lookup("jdbc/hotelbooking");
            Connection con = ds.getConnection();
            PreparedStatement pstmt = con.prepareStatement("SELECT * FROM [customer] WHERE [email] = ? AND [password] = ?");
            pstmt.setString(1, email);
            pstmt.setString(2, password);
            
            // execute the SQL statement
            ResultSet rs= pstmt.executeQuery();

            if (rs != null && rs.next()) {
            	customer = new Customer();
            	customer.setCustomerId(rs.getInt("customer_id"));
            	customer.setTitle(rs.getString("title"));
            	customer.setFirstName(rs.getString("first_name"));
            	customer.setLastName(rs.getString("last_name"));
            	customer.setEmail(rs.getString("email"));
            	customer.setPassword(rs.getString("password"));
            	customer.setIsRegistered(rs.getBoolean("is_registered"));
            }
            
            if (rs != null) {
                rs.close();
            }
            if (pstmt != null) {
            	pstmt.close();
            }
            if (con != null) {
                con.close();
            }
        } catch (SQLException ex) {
        	customer = null;
        	Logger.getLogger(CustomerDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
        	customer = null;
        	Logger.getLogger(CustomerDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
		
		return customer;
	}
	
	@Override
	public Customer getCustomer(String email) {
		Customer customer = null;
		
		try {
//            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
//            Connection con = DriverManager.getConnection("jdbc:sqlserver://w2ksa.cs.cityu.edu.hk:1433;databaseName=aiad053_db", "aiad053", "aiad053");
			Context initCtx = new InitialContext();
            Context envCtx = (Context)initCtx.lookup("java:comp/env");
            DataSource ds = (DataSource)envCtx.lookup("jdbc/hotelbooking");
            Connection con = ds.getConnection();
            PreparedStatement pstmt = con.prepareStatement("SELECT * FROM [customer] WHERE [email] = ?");
            pstmt.setString(1, email);
            
            // execute the SQL statement
            ResultSet rs= pstmt.executeQuery();

            if (rs != null && rs.next()) {
            	customer = new Customer();
            	customer.setCustomerId(rs.getInt("customer_id"));
            	customer.setTitle(rs.getString("title"));
            	customer.setFirstName(rs.getString("first_name"));
            	customer.setLastName(rs.getString("last_name"));
            	customer.setEmail(rs.getString("email"));
            	customer.setPassword(rs.getString("password"));
            	customer.setIsRegistered(rs.getBoolean("is_registered"));
            }
            
            if (rs != null) {
                rs.close();
            }
            if (pstmt != null) {
            	pstmt.close();
            }
            if (con != null) {
                con.close();
            }
        } catch (SQLException ex) {
        	customer = null;
        	Logger.getLogger(CustomerDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
        	customer = null;
        	Logger.getLogger(CustomerDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
		
		return customer;
	}
	
	@Override
	public boolean createCustomer(Customer customer) {
		boolean saved = false;
		
		try {
            if (customer != null) {
//                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
//                Connection con = DriverManager.getConnection("jdbc:sqlserver://w2ksa.cs.cityu.edu.hk:1433;databaseName=aiad053_db", "aiad053", "aiad053");
            	Context initCtx = new InitialContext();
                Context envCtx = (Context)initCtx.lookup("java:comp/env");
                DataSource ds = (DataSource)envCtx.lookup("jdbc/hotelbooking");
                Connection con = ds.getConnection();
            	PreparedStatement pstmt = con.prepareStatement("INSERT INTO [customer] ([title], [first_name], [last_name], [email], [password], [is_registered]) VALUES (?, ?, ?, ?, ?, ?)");
                pstmt.setString(1, customer.getTitle());
                pstmt.setString(2, customer.getFirstName());
                pstmt.setString(3, customer.getLastName());
                pstmt.setString(4, customer.getEmail());
                pstmt.setString(5, customer.getPassword());
                pstmt.setBoolean(6, customer.getIsRegistered());
                
                // execute the SQL statement
                int rows = pstmt.executeUpdate();

                if (rows > 0) {
                	saved = true;
                }
            }
        } catch (SQLException ex) {
        	Logger.getLogger(CustomerDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
        	Logger.getLogger(CustomerDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
		
		return saved;
	}

	@Override
	public boolean updateCustomer(Customer customer) {
		boolean updated = false;
		
		try {
            if (customer != null) {
//                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
//                Connection con = DriverManager.getConnection("jdbc:sqlserver://w2ksa.cs.cityu.edu.hk:1433;databaseName=aiad053_db", "aiad053", "aiad053");
            	Context initCtx = new InitialContext();
                Context envCtx = (Context)initCtx.lookup("java:comp/env");
                DataSource ds = (DataSource)envCtx.lookup("jdbc/hotelbooking");
                Connection con = ds.getConnection();
                PreparedStatement pstmt = con.prepareStatement("UPDATE [customer] SET [title]= ?, [first_name]= ?, [last_name]= ?, [email]= ?, [password]= ?, [is_registered]= ? WHERE [customer_id] = ?");
                pstmt.setString(1, customer.getTitle());
                pstmt.setString(2, customer.getFirstName());
                pstmt.setString(3, customer.getLastName());
                pstmt.setString(4, customer.getEmail());
                pstmt.setString(5, customer.getPassword());
                pstmt.setBoolean(6, customer.getIsRegistered());
                pstmt.setInt(7, customer.getCustomerId());
                
                // execute the SQL statement
                int rows = pstmt.executeUpdate();

                if (rows > 0) {
                	updated = true;
                }
            }
        } catch (SQLException ex) {
        	Logger.getLogger(CustomerDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
        	Logger.getLogger(CustomerDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
		
		return updated;
	}

	@Override
	public boolean deleteCustomer(Customer customer) {
		boolean deleted = false;
		
		try {
            if (customer != null) {
//                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
//                Connection con = DriverManager.getConnection("jdbc:sqlserver://w2ksa.cs.cityu.edu.hk:1433;databaseName=aiad053_db", "aiad053", "aiad053");
            	Context initCtx = new InitialContext();
                Context envCtx = (Context)initCtx.lookup("java:comp/env");
                DataSource ds = (DataSource)envCtx.lookup("jdbc/hotelbooking");
                Connection con = ds.getConnection();
                PreparedStatement pstmt = con.prepareStatement("DELETE FROM [customer] WHERE [customer_id] = ?");
                pstmt.setInt(1, customer.getCustomerId());
                
                // execute the SQL statement
                int rows = pstmt.executeUpdate();

                if (rows > 0) {
                	deleted = true;
                }
            }
        } catch (SQLException ex) {
        	Logger.getLogger(CustomerDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
        	Logger.getLogger(CustomerDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
		
		return deleted;
	}
}
