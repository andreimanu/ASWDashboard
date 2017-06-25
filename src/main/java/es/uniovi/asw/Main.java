package es.uniovi.asw;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import es.uniovi.asw.dao.CategoryDao;
import es.uniovi.asw.dao.CommentDao;
import es.uniovi.asw.dao.ProposalDao;
import es.uniovi.asw.dao.UserDao;
import es.uniovi.asw.dao.VoteDao;


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
