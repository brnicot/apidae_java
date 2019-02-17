public class Factorielle {

	public static void main(String[] args) {
		int n = 5;
		int resultat = 1;

		for(int i = 1; i <= n; i++) {
			resultat = resultat * i;
		}

		System.out.println("factorielle pour n = " + n + " : " + resultat);
	}

}
