public class AfficheArgs {

	public static void main(String[] args) {
		System.out.println(args.length + " arguments trouvés");
		for(int i = 0; i < args.length; i++) {
			System.out.println("Argument " + i + " = " + args[i]);
		}
	}

}
