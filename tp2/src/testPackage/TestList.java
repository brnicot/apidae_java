package testPackage;

import java.util.ArrayList;
import java.util.Collections;

import mesObjets.Salarie;
import mesObjets.SalarieNameComparator;
import mesObjets.SalarieSalaireNameComparator;

public class TestList {
	
	public static void main(String[] args) {
		ArrayList<Salarie> liste = new ArrayList<Salarie>();
		
	    liste.add(new Salarie("Pierre","Gold n Gold",60000));
	    liste.add(new Salarie("Jacques", "Clean n Dry",1000));
	    liste.add(new Salarie("Jules", "Clean n Dry",1000));
	    liste.add(new Salarie("Albert", "No where", 2000));
	    liste.add(new Salarie("Zizou", "RMCF", 60000));
	    liste.add(new Salarie("Charles","Dad n Son", 5000));
	    
	    System.out.println("Origine");
	    System.out.println(liste);
	    
	    System.out.println("Triée par salaire");
	    Collections.sort(liste);
	    System.out.println(liste);
	    
	    System.out.println("Triée par nom");
	    Collections.sort(liste, new SalarieNameComparator());
	    System.out.println(liste);
	    
	    System.out.println("Triée par salaire puis par nom");
	    Collections.sort(liste, new SalarieSalaireNameComparator());
	    System.out.println(liste);
	}

}
