
package testPackage;

public class Personne implements Cloneable {

  private String name;
 
  public Personne(String nom) {
    name = nom;
  }
 
  @Override
  public String toString() {
    return super.toString()+"  -> name = \""+name+"\"";
  }

  /**
   * @param name the name to set
   */
  public void setName(String name) {
    this.name = name;
  }

  public Personne clone() {
	  Personne p = null;
	try {
		p = (Personne) super.clone();
	} catch (CloneNotSupportedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	  return p;
  }
  
  public boolean equals(Object o) {
	  if (o instanceof Personne) {
		  return this.name.equals(((Personne) o).name);
	  }
	  return false;
  }
  
}