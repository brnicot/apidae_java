
package testPackage;

public class Bidule implements Cloneable{

  private int value;
  private Personne pers;
 
  public Bidule(int aValue) {
    value = aValue;
    pers = new Personne("toto "+aValue);
  }
 
  @Override
  public String toString() {
    return super.toString()+" contient -> value = "+value+" ; pers = "+pers;
  }

  /**
   * @param value the value to set
   */
  public void setValue(int value) {
    this.value = value;
  }
 

  /**
   * @return the pers
   */
  public Personne getPers() {
    return pers;
  }
  
  public Bidule clone() {
	Bidule b = null;
	try {
		b = (Bidule) super.clone();
	} catch (CloneNotSupportedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	  b.pers = this.pers.clone();
	  return b;
  }
  
  public boolean equals(Object o) {
	  if(o instanceof Bidule) {
		  Bidule b = (Bidule) o;
		  
		  return this.value == b.value && 
				 this.pers.equals(b.pers);
	  }
	  return false;
  }
 
  public static void main(String[] args) {
    Bidule bidule1 = new Bidule(2);
    System.out.println("bidule1 = "+bidule1);
    System.out.println();
    Bidule bidule2 = new Bidule(4);
    System.out.println("bidule2 = "+bidule2);
    System.out.println();
    System.out.println("----------clonage de bidule1 dans bidule2------------");
    System.out.println();

    bidule2 = (Bidule) bidule1.clone();

    System.out.println("bidule1 = "+bidule1);
    System.out.println();
    System.out.println("bidule2 = "+bidule2);
    System.out.println();
    
    System.out.println("---------- test equals -------------");
    System.out.println(bidule1.equals(bidule2));
    System.out.println();
    
    System.out.println("----------manipulation du clone bidule2 sur value -------------");
    System.out.println();

    bidule2.setValue(9);
   
    System.out.println("bidule1 = "+bidule1);
    System.out.println();
    System.out.println("bidule2 = "+bidule2);
    System.out.println();
   
    System.out.println("----------manipulation du clone bidule2 sur personne -------------");
    System.out.println();

    bidule2.getPers().setName("bidule");
   
    System.out.println("bidule1 = "+bidule1);
    System.out.println();
    System.out.println("bidule2 = "+bidule2);
    System.out.println();
   
  }
}