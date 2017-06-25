package es.uniovi.asw.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import es.uniovi.asw.PropReader;
import es.uniovi.asw.model.Proposal;

public class ProposalDao {
	private static Connection conn;
	public static boolean Refresh;
	public static int NewID;
	public ProposalDao() { 
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

	public List<Proposal> GetProposalsByMin(int min) {
		ArrayList<Proposal> ret = new ArrayList<Proposal>();
		try {
			PreparedStatement pstmt = conn.prepareStatement(PropReader.get("PROPOSAL_BY_MINIMAL"));
			pstmt.setInt(1, min);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				UserDao.getUserByName("");
				Proposal prop = new Proposal(UserDao.getUserByID(rs.getInt("UserID")), rs.getString("Title"),
						rs.getString("Category"), rs.getString("Text"), rs.getInt("ID"), rs.getString("Date"));
				VoteDao.SetVotes(prop);
				ret.add(prop);
			}
		} catch (SQLException e) {
			return null;
		}
		return ret;
	}
	
	public static List<Proposal> GetProposalByUser(int UserID) {
		ArrayList<Proposal> ret = new ArrayList<Proposal>();
		try {
			PreparedStatement pstmt = conn.prepareStatement(PropReader.get("PROPOSAL_BY_USER_ID"));
			pstmt.setInt(1, UserID);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				Proposal prop = new Proposal(UserDao.getUserByID(rs.getInt("UserID")), rs.getString("Title"),
						rs.getString("Category"), rs.getString("Text"), rs.getInt("ID"), rs.getString("Date"));
				VoteDao.SetVotes(prop);
				prop.setComments(CommentDao.getCommentsOf(prop));
				ret.add(prop);
			} 
		} catch (SQLException e) {
			return null;
		}
		return ret;
	}
	public static Proposal GetProposalByID(int UserID) {
		try {
			PreparedStatement pstmt = conn.prepareStatement(PropReader.get("PROPOSAL_BY_ID"));
			pstmt.setInt(1, UserID);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				Proposal prop = new Proposal(UserDao.getUserByID(rs.getInt("UserID")), rs.getString("Title"),
						rs.getString("Category"), rs.getString("Text"), rs.getInt("ID"), rs.getString("Date"));
				VoteDao.SetVotes(prop);
				prop.setComments(CommentDao.getCommentsOf(prop));
				return prop;
			} 
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return null;
	}
	public static List<Proposal> getAllProposals() {
		try {
			Long time = System.currentTimeMillis();
			PreparedStatement pstmt = conn.prepareStatement(PropReader.get("PROPOSAL_ALL"));
			
			ResultSet rs = pstmt.executeQuery();
			
			List<Proposal> propos = new ArrayList<Proposal>();
			
			while (rs.next()){
				Proposal prop = new Proposal(UserDao.getUserByID(rs.getInt("UserID")), rs.getString("Title"),
						rs.getString("Category"), rs.getString("Text"), rs.getInt("ID"), rs.getString("Date"));
				Long timeVotes1 = System.currentTimeMillis();
				VoteDao.SetVotes(prop);
				Long timeVotes2 = System.currentTimeMillis();
				System.out.println("Vote time: " + String.valueOf(timeVotes2-timeVotes1));
				Long timeComments1 = System.currentTimeMillis();
				prop.setComments(CommentDao.getCommentsOf(prop));
				Long timeComments2 = System.currentTimeMillis();
				System.out.println("Comments time: " + String.valueOf(timeComments2-timeComments1));
				propos.add(prop);
			} 
			Long time2 = System.currentTimeMillis();
			System.out.println("Get proposals time: " + String.valueOf(time2-time));
			return propos;
		} catch (SQLException e) {
			return null;
		}
	}
	
	private static boolean exists(Proposal proposal) {
		try {
			PreparedStatement stmt = conn.prepareStatement(PropReader.get("PROPOSAL_EXISTS"));
			stmt.setInt(1, proposal.getId());
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
	
	public static int delete(Proposal proposal) {
		try {
			PreparedStatement stmt = conn.prepareStatement(PropReader.get("PROPOSAL_DELETE"));
			stmt.setInt(1, proposal.getId());
			return stmt.executeUpdate();
		} catch (SQLException e) {
			return 0;
		}
	}
	
	public static int save(Proposal proposal) {
		
		try {
			if(exists(proposal)) {
				VoteDao.SaveVotes(proposal);
				return 1;
			}
			String[] notAllowed = PropReader.get("notAllowedWords").toString().split(",");
			for(String s : notAllowed) {
				if(proposal.getText().contains(s))
					throw new IllegalArgumentException("Word not allowed: " + s);
			}
			PreparedStatement stmt = conn.prepareStatement(PropReader.get("PROPOSAL_INSERT"));

			stmt.setInt(1, proposal.getMinimal());
			stmt.setString(2, proposal.getText());
			stmt.setInt(3, proposal.getUser().getId());
			stmt.setString(4, proposal.getTitle());
			stmt.setString(5, proposal.getCategory());
			stmt.setString(6, proposal.getDate());
			VoteDao.SaveVotes(proposal);
			return stmt.executeUpdate();		

		} catch (SQLException e) {
			return 0;		
		}
	}
	
		public List<Proposal> GetProposalByCategory(String category) {
		ArrayList<Proposal> ret = new ArrayList<Proposal>();
		try {
			PreparedStatement pstmt = conn.prepareStatement(PropReader.get("PROPOSAL_BY_CATEGORY"));
			pstmt.setString(1, category);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				Proposal prop = new Proposal(UserDao.getUserByID(rs.getInt("USERID")), rs.getString("Title"),
						rs.getString("Category"), rs.getString("Text"), rs.getInt("ID"), rs.getString("Date"));
				VoteDao.SetVotes(prop);
				prop.setComments(CommentDao.getCommentsOf(prop));
				ret.add(prop);
			}
		} catch (SQLException e) {
			return null;
		}
		return ret;
	}

		public static int getNewIdNumber() {
			try {
				openConn();
				PreparedStatement stmt = conn.prepareStatement(PropReader.get("PROPOSAL_NEW_ID"));
				
				ResultSet rs = stmt.executeQuery();
				
			if(rs.next())
				return rs.getInt(1);
			
			return 0;
				
			} catch (SQLException e) {
				return 0;		
			}
		}
}
