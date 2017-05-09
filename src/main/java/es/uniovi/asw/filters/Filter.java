package es.uniovi.asw.filters;

import java.util.List;

import model.Filtrable;


public interface Filter {

	
	
	List<Filtrable> filter(List<Filtrable> listOfFiltrables);
}
