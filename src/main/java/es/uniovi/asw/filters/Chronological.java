package es.uniovi.asw.filters;

import java.util.Collections;
import java.util.List;

import comparator.DateComparator;
import model.Filtrable;


public class Chronological implements Filter{


	@Override
	public List<Filtrable> filter(List<Filtrable> listOfFiltables) {
		Collections.sort(listOfFiltables, new DateComparator());
		return listOfFiltables;
	}
}
