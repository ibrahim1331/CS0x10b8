package utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class DBHelper {
	
	public static Connection getConnection() throws SQLException, NamingException{
		Context initCtx = new InitialContext();
        Context envCtx = (Context)initCtx.lookup("java:comp/env");
        DataSource ds = (DataSource)envCtx.lookup("jdbc/hotelbooking");
        return ds.getConnection();
	}
	
	public static void close(ResultSet rs) throws SQLException{
		if(rs!=null) rs.close();
	}
	
	public static void close(Statement stmt) throws SQLException{
		if(stmt!=null) stmt.close();
	}
	
	public static void close(Connection conn) throws SQLException{
		if(conn!=null) conn.close();
	}
	
	public static void close(PreparedStatement pstmt) throws SQLException{
		if(pstmt!=null) pstmt.close();
	}
}
