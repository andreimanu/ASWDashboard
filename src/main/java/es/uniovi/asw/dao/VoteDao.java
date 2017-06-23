package es.uniovi.asw.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import es.uniovi.asw.PropReader;
import es.uniovi.asw.kafka.KafkaProducer;
import es.uniovi.asw.model.Comment;
import es.uniovi.asw.model.Proposal;
import es.uniovi.asw.model.User;

public class VoteDao {
	private static Connection conn;

	public VoteDao() {
		try {
			openConn();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private static void openConn() throws SQLException {
		try {
			if (conn == null) {
				Class.forName("com.mysql.jdbc.Driver");
				conn = DriverManager.getConnection(PropReader.get("DATABASE_URL"), PropReader.get("DATABASE_USER"),
						PropReader.get("DATABASE_PASS"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	public static void SetVotes(Proposal prop) {
		try {
			PreparedStatement pstmt = conn.prepareStatement(PropReader.get("VOTE_PROP"));
			pstmt.setInt(1, prop.getId());
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				if(rs.getInt("Type") == 1) 
					prop.AddPositive(UserDao.getUserByID(rs.getInt("VotUserID")));
				else
					prop.AddNegative(UserDao.getUserByID(rs.getInt("VotUserID")));
					
			}
			//KafkaProducer kfc = new KafkaProducer();
			KafkaProducer.send("votedProposal", String.valueOf(prop.getId()));
		} catch (SQLException e) {
			return;
		}
	}

	public static void SetVotes(Comment prop) {
		try {
			PreparedStatement pstmt = conn.prepareStatement(PropReader.get("VOTE_COMM"));
			pstmt.setInt(1, prop.getId());
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				if(rs.getInt("Type") == 1) 
					prop.AddPositive(UserDao.getUserByID(rs.getInt("VotUserID")));
				else
					prop.AddNegative(UserDao.getUserByID(rs.getInt("VotUserID")));
					
			}
			//KafkaProducer kfc = new KafkaProducer();
			KafkaProducer.send("votedComment", String.valueOf(prop.getProposal().getId()));
		} catch (SQLException e) {
			return;
		}
	}
	public static void SaveVotes(Comment com) {
		List<User> pos = com.getPositiveVotes();
		List<User> neg = com.getNegativeVotes();
		for (User us : pos) {
			InsertVotesCom(com.getId(), us.getId(), 1);
		}
		for (User us : neg) {
			InsertVotesCom(com.getId(), us.getId(), 0);
		}
	}
	public static int InsertVotesCom(int PropID, int UserID, int Type) {
		try {
			PreparedStatement stmt = conn.prepareStatement(PropReader.get("VOTE_INSERT_COMM"));
			stmt.setInt(1, PropID);
			stmt.setInt(2, UserID);
			stmt.setInt(3, Type);
			return stmt.executeUpdate();

		} catch (SQLException e) {
			return 0;
		}
	}
	 
	public static void SaveVotes(Proposal prop) {
		List<User> pos = prop.getPositiveVotes();
		List<User> neg = prop.getNegativeVotes();
		for (User us : pos) {
			InsertVotesProp(prop.getId(), us.getId(), 1);
		}
		for (User us : neg) {
			InsertVotesProp(prop.getId(), us.getId(), 0);
		}
	}

	private static boolean Exists(int PropID, int UserID) {
		try {
			PreparedStatement stmt = conn.prepareStatement(PropReader.get("VOTE_EXISTS"));
			stmt.setInt(1, PropID); 
			stmt.setInt(2, UserID); 
			ResultSet rs = stmt.executeQuery();
			if(rs.next())
				return true;
			return false;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	public static int InsertVotesProp(int PropID, int UserID, int Type) {
		try {
			if(Exists(PropID, UserID)) {
				PreparedStatement stmt = conn.prepareStatement(PropReader.get("VOTE_UPDATE"));
				stmt.setInt(1, Type);
				stmt.setInt(2, PropID);
				stmt.setInt(3, UserID); 
				return stmt.executeUpdate();
			}
			PreparedStatement stmt = conn.prepareStatement(PropReader.get("VOTE_INSERT"));
			stmt.setInt(1, PropID);
			stmt.setInt(2, UserID);
			stmt.setInt(3, Type);
			return stmt.executeUpdate();

		} catch (SQLException e) {
			return 0;
		}
	}
}
