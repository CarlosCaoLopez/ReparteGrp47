package operaciones;

public class Principal {

	public static void main(String[] args) {
		Arit ca = new Arit();
		
		System.out.println(ca.suma(5, 6));
		System.out.println(ca.resta(7, 3));
		ca.suma(5, 6);
		ca.resta(7, 3);
	}

}
