package testPackage;

public class TestClone implements Cloneable {
 
  private int value;

  public TestClone(int aValue) {
    value = aValue;
  }
 
  @Override
  public String toString() {
    return super.toString()+" contient -> value = "+value;
  }

  public static void main(String[] args) {
    TestClone t1 = new TestClone(2);
    System.out.println("t1 = "+t1);
    System.out.println();
    TestClone t2 = new TestClone(4);
    System.out.println("t2 = "+t2);
    System.out.println();
   
    try {
      t2 = (TestClone) t1.clone();
    } catch (CloneNotSupportedException e) {
      e.printStackTrace();
    }
    System.out.println("t2 = "+t2);
  }
}