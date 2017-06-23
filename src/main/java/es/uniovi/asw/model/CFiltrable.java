package es.uniovi.asw.model;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import es.uniovi.asw.model.User;

public class CFiltrable implements Filtrable{

	protected List<User> positiveVotes, negativeVotes;
	protected String text;
	protected String date;
	protected int id;
	protected User user;

	public String getNow() {
		return Calendar.YEAR + "/" + Calendar.MONTH + "/" + Calendar.DAY_OF_MONTH;
	}
	
	public String getText() {
		return text;
	}
	
	public String getDate() {
		return date;
	}
	
	public User getUser() {
		return user;
	}
	
	public int getId() {
		return id;
	}
	
	public void AddPositive(User user) {
		positiveVotes.add(user);
	}

	public void AddNegative(User user) {
		negativeVotes.add(user);
	}
	
	public List<User> getPositiveVotes() {
		return positiveVotes;
	}

	public List<User> getNegativeVotes() {
		return negativeVotes;
	}

	@Override
	public int compareByDate(Filtrable arg1) {
		Calendar cd1 = new GregorianCalendar(Integer.parseInt(getDate().split("/")[0]),
				Integer.parseInt(getDate().split("/")[1]),
				Integer.parseInt(getDate().split("/")[2]));
		Calendar cd2 = new GregorianCalendar(Integer.parseInt(arg1.getDate().split("/")[0]),
				Integer.parseInt(arg1.getDate().split("/")[1]),
				Integer.parseInt(arg1.getDate().split("/")[2]));
		return cd1.compareTo(cd2);
	}

	@Override
	public int compareByVotes(Filtrable o2) {
		return Integer.compare(this.getPositiveVotes().size() + this.getNegativeVotes().size(), o2.getPositiveVotes().size() + o2.getNegativeVotes().size());
	}

	@Override
	public int compareByRatio(Filtrable o2) {
		return Double.compare(this.getPositiveVotes().size() / this.getNegativeVotes().size() == 0 ? 1 : this.getNegativeVotes().size(), o2.getPositiveVotes().size() / o2.getNegativeVotes().size() == 0 ? 1 : o2.getNegativeVotes().size());
	}
}
