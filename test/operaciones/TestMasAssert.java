package operaciones;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTimeout;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.assumeTrue;
import static org.junit.jupiter.api.Assumptions.assumingThat;

import java.io.EOFException;
import java.time.Duration;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class TestMasAssert {
	@Test
	void TestExcepcion() {
		EOFException eoe = assertThrows(EOFException.class, () -> {
			throw new EOFException("Fin de fichero");
		});
		assertEquals("Fin de fichero", eoe.getMessage());
	}

	@Test
	void testmultiple() {
		assertEquals(4, 4, "No hemos recibido un 4");
		assertNull(null, "No hemos recibido un nulo");
		assertTrue(true, "La respuesta es falsa");
	}
	
	@Test
	void testmultiplenot() {
		assertNotEquals(4, 4, "hemos recibido un 4");
		assertNotNull(null, "hemos recibido un nulo");
		assertFalse(true, "La respuesta es true");
	}
	
	@Tag("EstaNo")
	@Test
	void testMultipleAll() {
		assertAll(
				()->{assertNotEquals(4, 4, "hemos recibido un 4");},
				()->{assertNotNull(null, "hemos recibido un nulo");},
				()->{assertFalse(true, "La respuesta es true");}
				);
	}
	
	@Tag("EstaSi")
	@Nested
	@DisplayName("Pruebas de rendimiento")
	class Rendimientos{
        @BeforeEach
        void creaAlgoAnidada() {
            // TODO Inicializa o crea Fixtures para los metodos de Test de la clase anidada
        }	
    
		@AfterEach
		void DestruyeAlgoAnidado() {
			// TODO Destruye objetos fin de pruebas anidadas.
		}	
	@Test
	@DisplayName("Test de rendimiento sin límite")
	void SinLimite() {
		assertTimeout(Duration.ofSeconds(2), ()->{
			Thread.sleep(3000);
		} );
	}

	@Test
	@DisplayName("Test de rendimiento con límite")
	void ConLimite() {
		assertTimeoutPreemptively(Duration.ofSeconds(2), ()->{
			Thread.sleep(3000);
		} );
	}
	
	@Test
	@DisplayName("Test de rendimiento sin límite con supuesto")
	void SinLimiteConSupuesto() {
		int procesadores=Runtime.getRuntime().availableProcessors();
		
		assumeTrue(procesadores>4, "Insuficientes recursos");
		
		assertTimeout(Duration.ofSeconds(2), ()->{
			Thread.sleep(3000);
		} );
	}

	@Test
	@DisplayName("Test de rendimiento con límite suponiendo")
	void ConLimiteSuponiendo() {
		int procesadores=Runtime.getRuntime().availableProcessors();

		assumingThat(procesadores>3, ()->{
			// Solo en los sistema que cumplen la suposición
			assertTimeout(Duration.ofSeconds(2), ()->{
				Thread.sleep(3000);
			} );
			
		} );
		
		// Todos los sistemas
		assertTimeoutPreemptively(Duration.ofSeconds(2), ()->{
			Thread.sleep(3000);
		} );
	}
	}
		
}
