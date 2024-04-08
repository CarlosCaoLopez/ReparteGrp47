package operaciones;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class AritTestParametrizada {

	@DisplayName("test parametrizados")
	@ParameterizedTest(name="{index}.- Suma {0} + {1} = {2}")
	@CsvSource({"1,2,3","2,3,5","3,4,5"})
	void testSuma(int s1, int s2, int result) {
		Arit ca=new Arit();
		
		assertEquals(result, ca.suma(s1, s2));
	}

}
