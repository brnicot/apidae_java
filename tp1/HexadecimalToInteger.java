public class HexadecimalToInteger {

	public static void main(String[] args) {
		if(args.length != 1) {
			System.out.println("Ex : A");
			System.exit(-1);
		}

		try {
			System.out.println(Integer.valueOf(args[0], 16));

		}
		catch (NumberFormatException e) {
			System.out.println("Erreur : " + args[0] + " n'est pas parsable");
		}
	}

}
