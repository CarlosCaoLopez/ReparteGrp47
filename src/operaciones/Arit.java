package operaciones;

public class Arit implements ItfArit {

	@Override
	public int suma(int a, int b) {
		
		return a+b;
	}

	@Override
	public int resta(int a, int b) {
		
		return a-b;
	}

}
