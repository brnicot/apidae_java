public class DernierIndexDans {

	public static void main(String[] args) {
		if(args.length != 2) {
			System.out.println("Ex : testeur e");
			System.exit(-1);
		}

		System.out.println("Le dernier " + args[1] + " dans " + args[0] + " se trouve à la position " + args[0].lastIndexOf(args[1]));
	}

}
