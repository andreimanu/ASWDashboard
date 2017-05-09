package es.uniovi.asw.filters;

import java.util.ArrayList;
import java.util.List;

import model.Filtrable;

public class WordFinder implements Filter{

	private String[] words;
	public WordFinder(String... words) {
		this.words = words;
	}
	@Override
	public List<Filtrable> filter(List<Filtrable> listOfFiltables) {
		List<Filtrable> ret = new ArrayList<Filtrable>();
		for(Filtrable f : listOfFiltables)
			for(String word : words)
				if(f.getText().toLowerCase().contains(word.toLowerCase()))
					ret.add(f);
		return null;
	}

}
