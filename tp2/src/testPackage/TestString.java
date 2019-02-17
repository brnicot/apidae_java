package testPackage;

public class TestString {
	
	public static void main(String[] args) {
	    String ch1 = "to",ch2="to";

	    System.out.println("ch1 = \""+ch1+"\"");
	    System.out.println("ch2 = \""+ch2+"\"");
	    System.out.println();
	   
	    System.out.println("ch2 == ch1 : "+(ch2==ch1));
	    System.out.println("ch2 == \"to\" : "+(ch2=="to"));
	    System.out.println();

	    String ch3 = "toto";
	    System.out.println("ch3 = \""+ch3+"\"");
	    System.out.println();
	    System.out.println("ch3 == \"toto\" : "+(ch3=="toto"));
	    System.out.println("ch3 == ch1+ch2 : "+(ch3==(ch1+ch2)));
	    System.out.println();
	   
	    String ch4 = ch1+ch2;
	    System.out.println("ch4 = \""+ch4+"\"");
	    System.out.println();
	    System.out.println("ch4 == \"toto\" : "+(ch4=="toto"));
	    System.out.println("ch4 == ch3 : "+(ch3==ch4));
	    System.out.println();

	    String ch5 = "to"+"to";
	   
	    System.out.println("ch5 = \""+ch5+"\"");
	    System.out.println("ch5 == \"toto\" : "+(ch5=="toto"));
	    System.out.println("ch5 == ch4 : "+(ch5==ch4));
	    System.out.println();
	    
	    System.out.println("ch1 equals ch2 : "+ch1.equals(ch2));
	    System.out.println("ch2 equals \"to\" : "+ch2.equals("to"));
	    System.out.println("ch3 equals \"toto\" : "+ch3.equals("toto"));
	    System.out.println("ch3 equals ch1+ch2 : "+ch3.equals(ch1+ch2));
	    System.out.println("ch4 equals \"toto\" : "+ch4.equals("toto"));
	    System.out.println("ch4 equals ch3 : "+ch4.equals(ch3));
	    System.out.println("ch5 equals \"toto\" : "+ch5.equals("toto"));
	    System.out.println("ch5 equals ch4 : "+ch5.equals(ch4));
	   
	   }
}
