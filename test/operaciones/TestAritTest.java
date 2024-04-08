package operaciones;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class TestAritTest {
	// Fixtures
	Arit ca;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		ca= new Arit();
	}

	@AfterEach
	void tearDown() throws Exception {
		ca=null;
	}

	@DisplayName("Prueba de Suma CE: Enteros postivos")
	@Test
	void testSuma() {
		// Arrange - preparación
		int esperado = 11;

		// Act - Ejecución
		int real = ca.suma(5, 6);

		// Assert - Afirmación
		assertEquals(esperado, real, "Fallo al sumar 5+6");
	}

	@Disabled("Prueba no disponible")
	@Test
	void testResta() {
		assertEquals(4, ca.resta(7, 3), "Falla la resta de 7-3");
	}

}
