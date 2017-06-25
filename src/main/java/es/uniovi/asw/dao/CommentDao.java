package es.uniovi.asw.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import es.uniovi.asw.PropReader;
import es.uniovi.asw.model.Comment;
import es.uniovi.asw.model.Proposal;
import es.uniovi.asw.model.filtrable.Filtrable;

public class CommentDao {

	private static Connection conn;
	public CommentDao() {
		try {
			openConn();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void openConn() throws SQLException {
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
	  
	public static int save(Comment comment) {
		try {
			String[] notAllowed = PropReader.get("notAllowedWords").toString().split(",");
			for(String s : notAllowed) {
				if(comment.getText().contains(s))
					throw new IllegalArgumentException("Word not allowed: " + s);
			}
			PreparedStatement stmt = conn.prepareStatement(PropReader.get("COMM_INSERT"));
			stmt.setString(1, comment.getText());
			stmt.setInt(2, comment.getUser().getId());
			stmt.setInt(3, comment.getProposal().getId());
			stmt.setString(4, comment.getDate());
			return stmt.executeUpdate();		

		} catch (SQLException e) {
			return 0;		
		}
	}
	
	public static List<Filtrable> getCommentsOf(Proposal proposal){
		try { 
			PreparedStatement stmt = conn.prepareStatement(PropReader.get("COMMENT_BY_PROPOSAL"));
			stmt.setInt(1, proposal.getId());
			
			ResultSet rs = stmt.executeQuery();		
			
			List<Filtrable> comments = new ArrayList<Filtrable>();
			while(rs.next()){
				//Long CreateComment1 = System.currentTimeMillis();
				Comment com = new Comment(UserDao.getUserByID(rs.getInt("UserID")), proposal, rs.getString("Text"), rs.getInt("Comment.ID"), rs.getString("Comment.Date"));
				//Long CreateComment2 = System.currentTimeMillis();
				//System.out.println("Created comment in: " + String.valueOf(CreateComment2-CreateComment1));
				//Long CommentVote1 = System.currentTimeMillis();
				VoteDao.SetVotes(com);
				//Long CommentVote2 = System.currentTimeMillis();
				//System.out.println("Created vote comments in: " + String.valueOf(CommentVote2-CommentVote1));
				comments.add(com);
			}
			
			return comments;
			
		} catch (SQLException e) {
			return null;		
		}
	}
	
}
