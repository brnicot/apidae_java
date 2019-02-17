public class SystemProperty {

	public static void main(String[] args) {

		System.out.println("Nom d'utilisateur : " + System.getProperty("user.name"));
		System.out.println("Répertoire courant : " + System.getProperty("user.dir"));
		System.out.println("Version de la JVM : " + System.getProperty("java.vm.version"));

	}

}
