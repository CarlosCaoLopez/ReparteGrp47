package usuario;



import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;

import org.junit.jupiter.api.AfterEach;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import grupo.Grupo;
import grupo.IGrupo;
import pago.Pago;
import pago.IPago;
import gasto.Gasto;
import gasto.IGasto;

class UsuarioTest {
	// Fixtures
	Usuario usuario1, usuario2;
	ArrayList<String> notificaciones = new ArrayList<>();
	// No necesitamos mocks ni que las clases estén implementadas, porque solo los vamos a crear
	ArrayList<IGrupo> grupos = new ArrayList<>();
	ArrayList<IGasto> gastos = new ArrayList<>();
	ArrayList<IPago> pagos = new ArrayList<>();

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	
	
	@Nested
	@DisplayName("Pruebas de creación de usuarios")
	class Constructores{

		@ParameterizedTest
		@DisplayName("Verificación de correos electrónicos no válidos")
		@NullAndEmptySource
		@CsvSource({"nombredominio.com", "nomb@re@dominio.com", "nombre@dominiocom", "@dominio.com", "nombre@.com", "nombre@dominio."})
		void testCorreoNoValido(String correo) {
			usuario1 = new Usuario(1, "nombreUsuario", "nombreReal", correo, LocalDate.of(2000, Month.JANUARY, 1), "Contrasena!", "ES0000000000000000000000", 
					notificaciones, grupos, gastos, pagos);
			usuario2 = new Usuario(2, "nombreUsuario", "nombreReal", correo, LocalDate.of(2000, Month.JANUARY, 1), "Contrasena!", "ES0000000000000000000000");
			
			assertAll( () ->{assertNull("Un correo no válido ha sido validado (Constructor general)", usuario1.getCorreoElectronico());},
					()->{assertNull("Un correo no válido ha sido validado (Constructor específico)", usuario2.getCorreoElectronico());}); 
			
		}
		
		@ParameterizedTest
		@DisplayName("Verificación de correos electrónicos válidos")
		@CsvSource({"nombre@dominio.com"})
		void testCorreoValido(String correo) {
			usuario1 = new Usuario(1, "nombreUsuario", "nombreReal", correo, LocalDate.of(2000, Month.JANUARY, 1), "Contrasena!", "ES0000000000000000000000", 
					notificaciones, grupos, gastos, pagos);
			usuario2 = new Usuario(2, "nombreUsuario", "nombreReal", correo, LocalDate.of(2000, Month.JANUARY, 1), "Contrasena!", "ES0000000000000000000000");
			
			assertAll( () ->{assertEquals("Un correo válido ha sido invalidado (Constructor general)", correo, usuario1.getCorreoElectronico());},
					()->{assertEquals("Un correo válido ha sido invalidado (Constructor específico)", correo, usuario2.getCorreoElectronico());}); 
			
		}
		
		@ParameterizedTest
		@DisplayName("Verificación de datos bancarios no válidos")
		@NullAndEmptySource
		@CsvSource({"ESP01234567890123456789", "P01234567890123456789", "ES012345678901234567890", "ES0123456789012345678", "E0S1234567890123456789", "!ES01234567890123456789"})
		void testDatosNoValido(String datos) {
			usuario1 = new Usuario(1, "nombreUsuario", "nombreReal", "nombre@dominio.com", LocalDate.of(2000, Month.JANUARY, 1), "Contrasena!", datos, 
					notificaciones, grupos, gastos, pagos);
			usuario2 = new Usuario(2, "nombreUsuario", "nombreReal", "nombre@dominio.com", LocalDate.of(2000, Month.JANUARY, 1), "Contrasena!", datos);
			
			assertAll( () ->{assertNull("Unos datos bancarios no válidos han sido validados (Constructor general)", usuario1.getDatosBancarios());},
					()->{assertNull("Unos datos bancarios no válidos han sido validados (Constructor específico)", usuario2.getDatosBancarios());}); 
			
		}
		
		@ParameterizedTest
		@DisplayName("Verificación de datos bancarios válidos")
		@CsvSource({"ES0123456789012345678901"})
		void testDatosValido(String datos) {
			usuario1 = new Usuario(1, "nombreUsuario", "nombreReal", "nombre@dominio.com", LocalDate.of(2000, Month.JANUARY, 1), "Contrasena!", datos, 
					notificaciones, grupos, gastos, pagos);
			usuario2 = new Usuario(2, "nombreUsuario", "nombreReal", "nombre@dominio.com", LocalDate.of(2000, Month.JANUARY, 1), "Contrasena!", datos);
			
			assertAll( () ->{assertEquals("Unos datos bancarios válidos han sido invalidados (Constructor general)", datos, usuario1.getDatosBancarios());},
					()->{assertEquals("Unos datos bancarios válidos han sido invalidados (Constructor específico)", datos, usuario2.getDatosBancarios());}); 
			
		}
		
		@ParameterizedTest
		@DisplayName("Verificación de contraseñas no válidas")
		@NullAndEmptySource
		@CsvSource({"contrasena", "contr", "Contrasena"}) // Sin mayúscula, menos de 6 caracteres, sin símbolo
		void testContrasenaNoValida(String contrasena) {
			usuario1 = new Usuario(1, "nombreUsuario", "nombreReal", "nombre@dominio.com", LocalDate.of(2000, Month.JANUARY, 1), contrasena, "ES0000000000000000000000", 
					notificaciones, grupos, gastos, pagos);
			usuario2 = new Usuario(2, "nombreUsuario", "nombreReal", "nombre@dominio.com", LocalDate.of(2000, Month.JANUARY, 1), contrasena, "ES0000000000000000000000");
			
			assertAll( () ->{assertNull("Una contraseña no válida ha sido validada (Constructor general)", usuario1.getContrasena());},
					()->{assertNull("Una contraseña no válida ha sido validada (Constructor específico)", usuario2.getContrasena());}); 
			
		}
		
		@ParameterizedTest
		@DisplayName("Verificación de contraseñas válidas")
		@CsvSource({"Nombr€"})
		void testContrasenaValida(String contrasena) {
			usuario1 = new Usuario(1, "nombreUsuario", "nombreReal", "nombre@dominio.com", LocalDate.of(2000, Month.JANUARY, 1), contrasena, "ES0000000000000000000000", 
					notificaciones, grupos, gastos, pagos);
			usuario2 = new Usuario(2, "nombreUsuario", "nombreReal", "nombre@dominio.com", LocalDate.of(2000, Month.JANUARY, 1), contrasena, "ES0000000000000000000000");
			
			assertAll( () ->{assertEquals("Una contraseña válida ha sido invalidada (Constructor general)", contrasena, usuario1.getContrasena());},
					()->{assertEquals("Una contraseña válida ha sido invalidada (Constructor específico)", contrasena, usuario2.getContrasena());}); 
			
		}
		
		
		
		
	}
	
	
	


}
