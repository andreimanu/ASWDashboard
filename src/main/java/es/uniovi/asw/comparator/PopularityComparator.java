package es.uniovi.asw.comparator;

import java.util.Comparator;

import es.uniovi.asw.model.Filtrable;

public class PopularityComparator implements Comparator<Filtrable> {

	@Override
	public int compare(Filtrable o1, Filtrable o2) {
		return o1.compareByVotes(o2);
	}
	
}
