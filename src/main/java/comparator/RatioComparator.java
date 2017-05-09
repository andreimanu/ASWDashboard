package comparator;

import java.util.Comparator;

import model.Filtrable;

public class RatioComparator implements Comparator<Filtrable> {

	@Override
	public int compare(Filtrable o1, Filtrable o2) {
		return o1.compareByRatio(o2);
	}

}
