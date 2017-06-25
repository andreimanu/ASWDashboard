package es.uniovi.asw.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import es.uniovi.asw.PropReader;
import es.uniovi.asw.dao.ProposalDao;
import es.uniovi.asw.filters.Filter;
import es.uniovi.asw.model.filtrable.CFiltrable;
import es.uniovi.asw.model.filtrable.Filtrable;
public class Proposal extends CFiltrable {
	@Autowired
	private int id;
	@Autowired
	private int minimal;
	// option 1
	//private Map<String, List<User>> votes;
	//option 2
	private List<Filtrable> comments;
	private String category;
	private User user;
	private String title;
	public Proposal(int minimalNumberVotes, User user, String title, String category, String text, String date) {
		this(minimalNumberVotes, user, title, category, text);
		this.date = date;
	}
	public Proposal(int minimalNumberVotes, User user, String title, String category, String text) {
		this.id=ProposalDao.getNewIdNumber()+1;
		this.minimal = minimalNumberVotes;
		positiveVotes = new ArrayList<User>();
		negativeVotes = new ArrayList<User>();
		this.comments = new ArrayList<Filtrable>();
		this.category = category;
		this.text = text;
		this.user = user;
		this.title = title;
		this.date = getNow();
	}

	public Proposal(User user, String title, String category, String text) {
		this(Integer.parseInt(PropReader.get("minimumVotesNumber")), user, title, category, text);
	}

	public Proposal(User user, String title, String category, String text, int id, String date) {
		this(user, title, category, text);
		this.id = id;
		this.date = date;
	}
	
	public Proposal() {
		super();
	}
	/**
	 * returns the list of comments of the proposal. It may be filtered by one
	 * of the predefined filters
	 * 
	 * @param filter
	 *            null, Category, Chronological, NAllowedWords, Popularity,
	 *            WordFinder
	 * @return
	 */
	public List<Filtrable> getComments(Filter filter) {
		return filter == null ? getComments() : filter.filter(this.comments);
	}

	/*
	 * Public?
	 */
	public int delete() {
		// return ProposalDAO.delete(this);
		return 0;
	}

	public int getId() {
		return id;
	}

	public int getMinimal() {
		return minimal;
	}

	public String getText() {
		return text;
	}

	public Map<String, List<User>> getVotes() {
		Map<String, List<User>> votes = new HashMap<String, List<User>>();
		votes.put("positive", this.getPositiveVotes());
		votes.put("negative", this.getNegativeVotes());
		return votes;
	}

	public List<Filtrable> getComments() {
		return comments;
	}

	public String getCategory() {
		return category;
	}

	public User getUser() {
		return user;
	}

	@Override
	public String toString() {
		return "Proposal [Positive votes=" + positiveVotes.size() + ", Negative votes = " + negativeVotes.size() 
				+ ", comments=" + Arrays.toString(comments.toArray()) + ", category=" + category + ", text=" + text + ", user=" + user.getName() + "]";
	}

	public String getTitle() {
		return this.title;
	}


	public void addComment(Comment comment) {
		this.comments.add(comment);
	}
	

	public void SetID(int int1) {
		this.id = int1;
	}


	public void setComments(List<Filtrable> list) {
		this.comments = list;
	}
	public String getDate() {
		return date;
	} 
 
}
