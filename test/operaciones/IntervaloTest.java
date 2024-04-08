package operaciones;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class IntervaloTest {

	@ParameterizedTest
	@CsvSource({"0,2","2,7","5,5"})
	void testIn5abierto(int a, int b) {
		Intervalo inter= new Intervalo();
		
		assertTrue(inter.in5abierto(a, b));
	}

}
