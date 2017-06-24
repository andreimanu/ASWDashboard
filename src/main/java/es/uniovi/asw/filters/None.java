package es.uniovi.asw.filters;

import java.util.List;

import es.uniovi.asw.model.filtrable.Filtrable;



public class None implements Filter{


	@Override
	public List<Filtrable> filter(List<Filtrable> listOfFiltables) {
		return listOfFiltables;
	}

}
