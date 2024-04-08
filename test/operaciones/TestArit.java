package operaciones;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class TestArit {

	@DisplayName("Prueba de Suma CE: Enteros postivos")
	@Test
	void testSuma() {
		// Arrange - preparación
		Arit ca = new Arit();
		int esperado=11;
		
		// Act - Ejecución
		int real = ca.suma(5, 6);
		
		// Assert - Afirmación
		assertEquals(esperado, real, "Fallo al sumar 5+6");
	}

	@Disabled("Prueba no disponible")
	@Test
	void testResta() {
		Arit ca = new Arit();
		
		assertEquals(4, ca.resta(7, 3), "Falla la resta de 7-3");	}

}
