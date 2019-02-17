public class Factorielle2 {

	public static void main(String[] args) {

		if(args.length != 1) {
			System.out.println("Veuillez fournir un nombre en argument");
			System.exit(-1);
		}

		int resultat = 1;
		for(int i = 1; i <= Integer.parseInt(args[0]); i++) {
			resultat *= i;
		}
		System.out.println("Factorielle pour n = " + args[0] + " : " + resultat);
	}

}
