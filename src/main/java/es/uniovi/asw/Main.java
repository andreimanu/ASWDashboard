package es.uniovi.asw;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import es.uniovi.asw.dao.CategoryDao;
import es.uniovi.asw.dao.CommentDao;
import es.uniovi.asw.dao.ProposalDao;
import es.uniovi.asw.dao.UserDao;
import es.uniovi.asw.dao.VoteDao;
import es.uniovi.asw.kafka.KafkaConsumer;
//import es.uniovi.asw.menus.MainMenu;
//import es.uniovi.asw.menus.Menu;
import es.uniovi.asw.model.User;


@SpringBootApplication
public class Main {

	private static BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
	private static User currentUser = null;

	public static void main (String[] args){
		/*
		Thread kafka = new Thread() {
		    public void run() {
		        KafkaConsumer kfc = new KafkaConsumer();
		        kfc.Subscribe("votedProposal");
		        kfc.Subscribe("votedComment" );
		        kfc.Subscribe("createdProposal" );
		        kfc.Subscribe("createdComment" );
		        kfc.Subscribe("deletedProposal" );
		        kfc.Read();
		    }  
		};

		kafka.run();*/
		
		Thread kafka = new Thread() {
		    public void run() {
		    	KafkaConsumer kfc = new KafkaConsumer();
		    	kfc.SubscribeTo(Arrays.asList("votedProposal", "votedComment", "createdProposal", "createdComment", "deletedProposal"));
		    	kfc.Read();
		    }
		};
		kafka.run();
		
		///currentUser = new User("Andrei Manu", 1679344);
		new CategoryDao();
		new CommentDao();
		new ProposalDao();
		new UserDao();
		new VoteDao();
	    SpringApplication.run(Main.class, args);
	    
	}
	
	
}
