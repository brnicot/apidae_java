import java.util.Arrays;

public class Exercice12 {

	public static void main(String[] args) {

		int[] tableau = new int[100];
		Arrays.fill(tableau, 0, 20, 0);
		Arrays.fill(tableau, 20, 80, 2);
		Arrays.fill(tableau, 80, 100, 8);

		System.out.println(Arrays.toString(tableau));
	}

}
