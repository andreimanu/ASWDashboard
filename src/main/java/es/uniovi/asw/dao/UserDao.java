package es.uniovi.asw.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.example.PropReader;

import model.User;

public class UserDao {
	private static Connection conn;
	
	public UserDao() {
		try {
			openConn();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	private void openConn() throws SQLException {
		try {
		if(conn == null) {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(PropReader.get("DATABASE_URL"),
					PropReader.get("DATABASE_USER"), PropReader.get("DATABASE_PASS"));
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public static User getUserByName(String userName) {
		try {
			PreparedStatement pstmt = conn.prepareStatement(PropReader.get("USER_BY_NAME"));
			pstmt.setString(1, userName);
			ResultSet rs = pstmt.executeQuery();
			
			if (rs.next()){
				User res = new User(rs.getString("FName"), rs.getString("LName"), rs.getInt("ID"), rs.getString("Email"), 
						rs.getString("DOB"), rs.getInt("Gender") == 0 ? false : true, rs.getString("Password"), rs.getString("Address"));
				return res;
			}
		} catch (SQLException e) {
			return null;
		}
		return null;
	}
	
	
	public static User getUserByID(int ID) {
		try {
			PreparedStatement pstmt = conn.prepareStatement(PropReader.get("USER_BY_ID"));
			pstmt.setInt(1, ID);
			ResultSet rs = pstmt.executeQuery();
			
			if (rs.next()){
				User res = new User(rs.getString("FName"), rs.getString("LName"), rs.getInt("ID"), rs.getString("Email"), 
						rs.getString("DOB"), rs.getInt("Gender") == 0 ? false : true, rs.getString("Password"), rs.getString("Address"));
				return res;
			}
		} catch (SQLException e) {
			return null;
		}
		return null;
	}

	public static User getUserLog(int UserID, String password) {
		try {
			PreparedStatement pstmt = conn.prepareStatement(PropReader.get("USER_LOG"));
			pstmt.setInt(1, UserID);
			pstmt.setString(2, password);
			ResultSet rs = pstmt.executeQuery();
			
			if (rs.next()){
				User res = new User(rs.getString("FName"), rs.getString("LName"), rs.getInt("ID"), rs.getString("Email"), 
						rs.getString("DOB"), rs.getInt("Gender") == 0 ? false : true, rs.getString("Password"), rs.getString("Address"));
				return res;
			}
		} catch (SQLException e) {
			return null;
		}
		return null;
	}
}
