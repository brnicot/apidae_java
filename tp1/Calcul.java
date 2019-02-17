public class Calcul {

	public static void main(String[] args) {
		if(args.length != 3) {
			System.out.println("Ex : 5 + 9");
			System.exit(-1);		
		}

		float resultat = 0;
		switch(args[1]) {
			case "+":
				resultat = Float.parseFloat(args[0]) + Float.parseFloat(args[2]);
				break;
			case "-":
				resultat = Float.parseFloat(args[0]) - Float.parseFloat(args[2]);
				break;
			case "*":
				resultat = Float.parseFloat(args[0]) * Float.parseFloat(args[2]);
				break;
			case "/":
				resultat = Float.parseFloat(args[0]) / Float.parseFloat(args[2]);
				break;
		}

		System.out.println(resultat);
	}

}
