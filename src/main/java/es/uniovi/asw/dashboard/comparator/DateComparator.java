package es.uniovi.asw.dashboard.comparator;

import java.util.Comparator;

import es.uniovi.asw.model.filtrable.Filtrable;


public class DateComparator implements Comparator<Filtrable> {

	@Override
	public int compare(Filtrable arg0, Filtrable arg1) {
		return arg0.compareByDate(arg1);
	}

}
