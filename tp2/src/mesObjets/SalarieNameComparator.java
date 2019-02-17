package mesObjets;

import java.util.Comparator;

public class SalarieNameComparator implements Comparator<Salarie> {

	@Override
	public int compare(Salarie o1, Salarie o2) {
		// TODO Auto-generated method stub
		return o1.nom.compareTo(o2.nom);
	}
	
	

}
