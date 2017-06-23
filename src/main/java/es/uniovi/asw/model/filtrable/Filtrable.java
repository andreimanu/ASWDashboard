package es.uniovi.asw.model.filtrable;

import java.util.List;

import es.uniovi.asw.model.User;

public interface Filtrable {

	int compareByDate(Filtrable arg1);
	String getDate();
	String getNow();
	String getText();
	User getUser();
	int getId();
	void AddPositive(User user);
	void AddNegative(User user);
	List<User> getPositiveVotes();
	List<User> getNegativeVotes();
	int compareByVotes(Filtrable o2);
	int compareByRatio(Filtrable o2);

}
