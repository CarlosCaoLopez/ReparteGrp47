package grupo;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.fail;

import java.time.LocalDate;
import java.time.Month;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import usuario.Usuario;

class GrupoTest {

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}
	
	@Nested
	@DisplayName("Verificación de correos electrónicos no válidos")
	class Constructores{
		
		@ParameterizedTest
		@DisplayName()
		@NullAndEmptySource
		@CsvSource({})
		void testCrearGrupo() {
			// Crear un mock para simular un usuario correcto 
			Usuario
		}
	}
	
	@Test
	void testAnadirMiembro() {
		fail("Not yet implemented");
	}

	@Test
	void testEliminarMiembro() {
		fail("Not yet implemented");
	}

	@Test
	void testAnadirGasto() {
		fail("Not yet implemented");
	}

	@Test
	void testModificarGasto() {
		fail("Not yet implemented");
	}

	@Test
	void testModificarDescripcion() {
		fail("Not yet implemented");
	}

}
