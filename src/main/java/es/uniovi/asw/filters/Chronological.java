package es.uniovi.asw.filters;

import java.util.Collections;
import java.util.List;

import es.uniovi.asw.dashboard.comparator.DateComparator;
import es.uniovi.asw.model.filtrable.Filtrable;


public class Chronological implements Filter{


	@Override
	public List<Filtrable> filter(List<Filtrable> listOfFiltables) {
		Collections.sort(listOfFiltables, new DateComparator());
		return listOfFiltables;
	}
}
