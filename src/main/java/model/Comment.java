package model;

import java.util.ArrayList;
import java.util.Arrays;

import model.CFiltrable;

public class Comment extends CFiltrable implements Removable{

	
	private Proposal proposal;
	
	
	public Comment(User user, Proposal proposal, String text){
		super();
		this.positiveVotes = new ArrayList<User>();
		this.negativeVotes = new ArrayList<User>();
		this.user = user;
		this.proposal = proposal;
		this.text = text;
		this.date = getNow();
	}
	
	
	public Comment(User user, Proposal proposal, String text, int id, String Date){
		super();
		this.id = id;
		this.positiveVotes = new ArrayList<User>();
		this.negativeVotes = new ArrayList<User>();
		this.user = user;
		this.proposal = proposal;
		this.text = text;
		this.date = Date;
	}
	
	

	public Proposal getProposal() {
		return proposal;
	}

	@Override
	public String toString() {
		return "Comment [text=" + text + ", Positive votes=" + Arrays.toString(positiveVotes.toArray()) + ", Negative votes= " + Arrays.toString(negativeVotes.toArray()) +
				", user=" + user + ", date=" + date + "]";
	}


	@Override
	public int delete() {
		// TODO Auto-generated method stub
		return 0;
	}
}
