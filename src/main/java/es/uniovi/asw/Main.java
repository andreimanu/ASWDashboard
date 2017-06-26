package es.uniovi.asw;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import es.uniovi.asw.dashboard.dao.CategoryDao;
import es.uniovi.asw.dashboard.dao.CommentDao;
import es.uniovi.asw.dashboard.dao.ProposalDao;
import es.uniovi.asw.dashboard.dao.UserDao;
import es.uniovi.asw.dashboard.dao.VoteDao;


@SpringBootApplication
public class Main {

	public static void main (String[] args){
		
		new CategoryDao();
		new CommentDao();
		new ProposalDao();
		new UserDao();
		new VoteDao();
	    SpringApplication.run(Main.class, args);
	    
	}
	
	
}
