package es.uniovi.asw.filters;

import java.util.List;

import model.Filtrable;



public class None implements Filter{


	@Override
	public List<Filtrable> filter(List<Filtrable> listOfFiltables) {
		return listOfFiltables;
	}

}
