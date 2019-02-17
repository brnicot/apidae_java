package mesObjets;

public class Salarie implements Comparable<Salarie> {
	
	protected String nom;
	protected String prenom;
	protected int salaire;
	
	public Salarie(String n, String p, int s) {
		this.nom = n;
		this.prenom = p;
		this.salaire = s;
	}

	public String toString() {
		return this.nom + " " + this.salaire;
	}

	@Override
	public int compareTo(Salarie o) {
		// TODO Auto-generated method stub
		if(this.salaire > o.salaire) {return -1;}
		else if (this.salaire < o.salaire) {return 1;}
		else {return 0;}
	}
	
}
