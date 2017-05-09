package es.uniovi.asw.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.example.PropReader;


public class CategoryDao {

private static Connection conn;
	
	public CategoryDao() {
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
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public static String[] listCategories() {
		return PropReader.get("categories").split(",");
	}
}
