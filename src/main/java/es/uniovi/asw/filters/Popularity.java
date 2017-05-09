package es.uniovi.asw.filters;

import java.util.Collections;
import java.util.List;

import comparator.PopularityComparator;
import model.Filtrable;


public class Popularity implements Filter{


	@Override
	public List<Filtrable> filter(List<Filtrable> listOfFiltables) {
		Collections.sort(listOfFiltables, new PopularityComparator());
		return listOfFiltables;
	}

}
