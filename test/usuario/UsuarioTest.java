package usuario;


import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;

import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import gasto.IGasto;
import grupo.IGrupo;
import pago.IPago;

class UsuarioTest {
	// Fixtures
	IUsuario usuario1, usuario2;
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
			
			assertAll( () ->{assertNull(usuario1.getCorreoElectronico(), "Un correo no válido ha sido validado (Constructor general)");},
					()->{assertNull(usuario2.getCorreoElectronico(), "Un correo no válido ha sido validado (Constructor específico)");}); 
			
		}
		
		@ParameterizedTest
		@DisplayName("Verificación de correos electrónicos válidos")
		@CsvSource({"nombre@dominio.com"})
		void testCorreoValido(String correo) {
			usuario1 = new Usuario(1, "nombreUsuario", "nombreReal", correo, LocalDate.of(2000, Month.JANUARY, 1), "Contrasena!", "ES0000000000000000000000", 
					notificaciones, grupos, gastos, pagos);
			usuario2 = new Usuario(2, "nombreUsuario", "nombreReal", correo, LocalDate.of(2000, Month.JANUARY, 1), "Contrasena!", "ES0000000000000000000000");
			
			assertAll( () ->{assertEquals(correo, usuario1.getCorreoElectronico(), "Un correo válido ha sido invalidado (Constructor general)");},
					()->{assertEquals(correo, usuario2.getCorreoElectronico(), "Un correo válido ha sido invalidado (Constructor específico)");}); 
			
		}
		
		@ParameterizedTest
		@DisplayName("Verificación de datos bancarios no válidos")
		@NullAndEmptySource
		@CsvSource({"ESP01234567890123456789", "P01234567890123456789", "ES012345678901234567890", "ES0123456789012345678", "E0S1234567890123456789", "!ES01234567890123456789", "eS0123456789012345678901"})
		void testDatosNoValido(String datos) {
			usuario1 = new Usuario(1, "nombreUsuario", "nombreReal", "nombre@dominio.com", LocalDate.of(2000, Month.JANUARY, 1), "Contrasena!", datos, 
					notificaciones, grupos, gastos, pagos);
			usuario2 = new Usuario(2, "nombreUsuario", "nombreReal", "nombre@dominio.com", LocalDate.of(2000, Month.JANUARY, 1), "Contrasena!", datos);
			
			assertAll( () ->{assertNull(usuario1.getDatosBancarios(), "Unos datos bancarios no válidos han sido validados (Constructor general)");},
					()->{assertNull(usuario2.getDatosBancarios(), "Unos datos bancarios no válidos han sido validados (Constructor específico)");}); 
			
		}
		
		@ParameterizedTest
		@DisplayName("Verificación de datos bancarios válidos")
		@CsvSource({"ES0123456789012345678901"})
		void testDatosValido(String datos) {
			usuario1 = new Usuario(1, "nombreUsuario", "nombreReal", "nombre@dominio.com", LocalDate.of(2000, Month.JANUARY, 1), "Contrasena!", datos, 
					notificaciones, grupos, gastos, pagos);
			usuario2 = new Usuario(2, "nombreUsuario", "nombreReal", "nombre@dominio.com", LocalDate.of(2000, Month.JANUARY, 1), "Contrasena!", datos);
			
			assertAll( () ->{assertEquals(datos, usuario1.getDatosBancarios(), "Unos datos bancarios válidos han sido invalidados (Constructor general)");},
					()->{assertEquals(datos, usuario2.getDatosBancarios(), "Unos datos bancarios válidos han sido invalidados (Constructor específico)");}); 
			
		}
		
		@ParameterizedTest
		@DisplayName("Verificación de contraseñas no válidas")
		@NullAndEmptySource
		@CsvSource({"contrasena", "contr", "Contrasena"}) // Sin mayúscula, menos de 6 caracteres, sin símbolo
		void testContrasenaNoValida(String contrasena) {
			usuario1 = new Usuario(1, "nombreUsuario", "nombreReal", "nombre@dominio.com", LocalDate.of(2000, Month.JANUARY, 1), contrasena, "ES0000000000000000000000", 
					notificaciones, grupos, gastos, pagos);
			usuario2 = new Usuario(2, "nombreUsuario", "nombreReal", "nombre@dominio.com", LocalDate.of(2000, Month.JANUARY, 1), contrasena, "ES0000000000000000000000");
			
			assertAll( () ->{assertNull(usuario1.getContrasena(), "Una contraseña no válida ha sido validada (Constructor general)");},
					()->{assertNull(usuario2.getContrasena(), "Una contraseña no válida ha sido validada (Constructor específico)");}); 
			
		}
		
		@ParameterizedTest
		@DisplayName("Verificación de contraseñas válidas")
		@CsvSource({"Nombr€"})
		void testContrasenaValida(String contrasena) {
			usuario1 = new Usuario(1, "nombreUsuario", "nombreReal", "nombre@dominio.com", LocalDate.of(2000, Month.JANUARY, 1), contrasena, "ES0000000000000000000000", 
					notificaciones, grupos, gastos, pagos);
			usuario2 = new Usuario(2, "nombreUsuario", "nombreReal", "nombre@dominio.com", LocalDate.of(2000, Month.JANUARY, 1), contrasena, "ES0000000000000000000000");
			
			assertAll( () ->{assertEquals(contrasena, usuario1.getContrasena(), "Una contraseña válida ha sido invalidada (Constructor general)");},
					()->{assertEquals(contrasena, usuario2.getContrasena(), "Una contraseña válida ha sido invalidada (Constructor específico)");}); 
			
		}
		
	}
	
	@Nested 
	@DisplayName("Pruebas de creación de grupo desde un Usuario")
	class crearGrupo{
		AutoCloseable acl;
		//Para las pruebas de Unidad necesitamos un mock que simule el constructor de Grupo
		// Más tarde para las de integración, se cambiará ese mock por un Objeto real
		@Mock
		IGrupo grupoMock;
		
		@InjectMocks
		Usuario usuario3 = new Usuario(1, "nombreUsuario", "nombreReal", "nombre@dominio.com", LocalDate.of(2000, Month.JANUARY, 1), "contrasena", "ES0000000000000000000000");
		
		@BeforeEach
		void setUp() throws Exception {
			acl = MockitoAnnotations.openMocks(this);
		}

		@AfterEach
		void tearDown() throws Exception {
			acl.close();
		}
		
		@Test
		@DisplayName("Verificación de que el usuario que crea el grupo se encuentra en [usuarios]")
		void testUsuarioDentro() {
			IUsuario usuario4 = new Usuario(4, "nomeUsuario", "nomeReal", "nombre@dominio.com", LocalDate.of(2000, Month.JANUARY, 1), "contrasena", "ES0000000000000000000000");
			ArrayList<IUsuario> usuarios = new ArrayList<IUsuario>();
			ArrayList<IUsuario> usuarios2 = new ArrayList<IUsuario>();
			usuarios.add(usuario3);
			usuarios2.add(usuario4);
			assertAll( ()->{assertNull(usuario3.crearGrupo(1, "nombre", "descripcion", usuarios), "Usuario creador dentro de grupo invalidado");},
						()->{assertNull(usuario3.crearGrupo(2, "nombre", "descripcion", usuarios2), "Usuario creador no dentro de grupo a crear invalidado");});
		}	
		
		@ParameterizedTest
		@DisplayName("Verificación de que el id es un número positivo")
		@CsvSource({"-1", "0"})
		void testIdValido(int id) {
			ArrayList<IUsuario> usuarios = new ArrayList<IUsuario>();
			usuarios.add(usuario3);
			assertNull(usuario3.crearGrupo(id, "nombreGrupo", "descripcion", usuarios), "Id introducido no válido");
		}
		
		@ParameterizedTest
		@DisplayName("Verificación de que el nombre no es nulo ni vacío")
		@CsvSource({"nombre"})
		void testNombre(String nombre) {
			ArrayList<IUsuario> usuarios = new ArrayList<IUsuario>();
			usuarios.add(usuario3);
			assertAll( ()->{assertNull(usuario3.crearGrupo(1, nombre, "descripcion", usuarios), "Nombre válido introducido, no válido");},
						()->{assertNull(usuario3.crearGrupo(1, null, "descripcion", usuarios), "Nombre inválido introducido, no válido");},
						()->{assertNull(usuario3.crearGrupo(1, "", "descripción", usuarios), "Nombre inválido introducido, no válido");});
		}
		
		@ParameterizedTest
		@DisplayName("Verificación de que la descripción no es nula ni vacía")
		@CsvSource({"descripcion"})
		void testDescripcion(String descripcion) {
			ArrayList<IUsuario> usuarios = new ArrayList<IUsuario>();
			usuarios.add(usuario3);			
			assertAll( ()->{assertNull(usuario3.crearGrupo(1, "nombre", descripcion, usuarios), "Descripcion valida introducida, no válido");},
						()->{assertNull(usuario3.crearGrupo(1, "nombre","", usuarios), "Descripcion invalida introducida, no válido");},
						()->{assertNull(usuario3.crearGrupo(1, "nombre", null, usuarios), "Descripcion inválida introducida, no válido");}
					);
		}		
	}
	


}
