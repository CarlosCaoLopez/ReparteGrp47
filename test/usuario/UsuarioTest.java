package usuario;



import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import gasto.Gasto;
import gasto.IGasto;
import grupo.Grupo;
import grupo.IGrupo;
import pago.IPago;
import pago.Pago;

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
		
		@ParameterizedTest
		@DisplayName("Verificación de que el id es un número positivo (caso no válido)")
		@CsvSource({"-1", "0"})
		void testIdNoValido(int id) {
			usuario1 = new Usuario(id, "nombreUsuario", "nombreReal", "nombre@dominio.com", LocalDate.of(2000, Month.JANUARY, 1), "Contrasena!", "ES0000000000000000000000", 
					notificaciones, grupos, gastos, pagos);
			usuario2 = new Usuario(id, "nombreUsuario", "nombreReal", "nombre@dominio.com", LocalDate.of(2000, Month.JANUARY, 1), "Contrasena!", "ES0000000000000000000000");
			assertAll( () ->{assertTrue(usuario1.getId() <= 0, "Un id no válido ha sido validado (Constructor general)");},
					()->{assertTrue(usuario2.getId() <= 0, "Un id no válido ha sido validado (Constructor específico)");}); 
		}
		
		@ParameterizedTest
		@DisplayName("Verificación de que el id es un número positivo (caso válido)")
		@CsvSource({"1"})
		void testIdValido(int id) {
			usuario1 = new Usuario(id, "nombreUsuario", "nombreReal", "nombre@dominio.com", LocalDate.of(2000, Month.JANUARY, 1), "Contrasena!", "ES0000000000000000000000", 
					notificaciones, grupos, gastos, pagos);
			usuario2 = new Usuario(id, "nombreUsuario", "nombreReal", "nombre@dominio.com", LocalDate.of(2000, Month.JANUARY, 1), "Contrasena!", "ES0000000000000000000000");
			assertAll( () ->{assertEquals(id, usuario1.getId(), "Un id válido ha sido invalidado (Constructor general)");},
					()->{assertEquals(id, usuario2.getId(), "Un id válido ha sido invalidado (Constructor específico)");}); 
		}
		
		@ParameterizedTest
		@DisplayName("Verificación de que el nombre de usuario es correcto (caso no válido)")
		@NullAndEmptySource
		void testNombreUsuarioNoValido(String nombreUsuario) {
			usuario1 = new Usuario(1, nombreUsuario, "nombreReal", "nombre@dominio.com", LocalDate.of(2000, Month.JANUARY, 1), "Contrasena!", "ES0000000000000000000000", 
					notificaciones, grupos, gastos, pagos);
			usuario2 = new Usuario(2, nombreUsuario, "nombreReal", "nombre@dominio.com", LocalDate.of(2000, Month.JANUARY, 1), "Contrasena!", "ES0000000000000000000000"); 
			assertAll( () ->{assertNull(usuario1.getNombreUsuario(), "Un nombre de usuario no válido ha sido validado (Constructor general)");},
					()->{assertNull(usuario2.getNombreUsuario(), "Un nombre de usuario no válido ha sido validado (Constructor específico)");}); 
		}
		
		@ParameterizedTest
		@DisplayName("Verificación de que el nombre de usuario es correcto (caso válido)")
		@CsvSource({"nombreUsuario"})
		void testNombreUsuarioValido(String nombreUsuario) {
			usuario1 = new Usuario(1, nombreUsuario, "nombreReal", "nombre@dominio.com", LocalDate.of(2000, Month.JANUARY, 1), "Contrasena!", "ES0000000000000000000000", 
					notificaciones, grupos, gastos, pagos);
			usuario2 = new Usuario(2, nombreUsuario, "nombreReal", "nombre@dominio.com", LocalDate.of(2000, Month.JANUARY, 1), "Contrasena!", "ES0000000000000000000000"); 
			assertAll( () ->{assertEquals(nombreUsuario, usuario1.getNombreUsuario(), "Un nombre de usuario válido ha sido invalidado (Constructor general)");},
					()->{assertEquals(nombreUsuario, usuario2.getNombreUsuario(), "Un nombre de usuario ha sido invalidado (Constructor específico)");}); 
		}
		
		@ParameterizedTest
		@DisplayName("Verificación de que el nombre real es correcto (caso no válido)")
		@NullAndEmptySource
		void testNombreRealNoValido(String nombreReal) {
			usuario1 = new Usuario(1, "nombreUsuario", nombreReal, "nombre@dominio.com", LocalDate.of(2000, Month.JANUARY, 1), "Contrasena!", "ES0000000000000000000000", 
					notificaciones, grupos, gastos, pagos);
			usuario2 = new Usuario(1, "nombreUsuario", nombreReal, "nombre@dominio.com", LocalDate.of(2000, Month.JANUARY, 1), "Contrasena!", "ES0000000000000000000000");
			assertAll( () ->{assertNull(usuario1.getNombreReal(), "Un nombre real no válido ha sido validado (Constructor general)");},
					()->{assertNull(usuario2.getNombreReal(), "Un nombre real no válido ha sido validado (Constructor específico)");}); 
		}
		
		@ParameterizedTest
		@DisplayName("Verificación de que el nombre real es correcto (caso válido)")
		@CsvSource({"nombreReal"})
		void testNombreRealValido(String nombreReal) {
			usuario1 = new Usuario(1, "nombreUsuario", nombreReal, "nombre@dominio.com", LocalDate.of(2000, Month.JANUARY, 1), "Contrasena!", "ES0000000000000000000000", 
					notificaciones, grupos, gastos, pagos);
			usuario2 = new Usuario(1, "nombreUsuario", nombreReal, "nombre@dominio.com", LocalDate.of(2000, Month.JANUARY, 1), "Contrasena!", "ES0000000000000000000000");
			assertAll( () ->{assertEquals(nombreReal, usuario1.getNombreReal(), "Un nombre real válido ha sido invalidado (Constructor general)");},
					()->{assertEquals(nombreReal, usuario2.getNombreReal(), "Un nombre real válido ha sido invalidado (Constructor específico)");}); 
		}
		
		@Test
		@DisplayName("Verificación de que la fecha de nacimiento es no nula (caso no válido)")
		void testFechaNacimientoNoValido() {
			usuario1 = new Usuario(1, "nombreUsuario", "nombreReal", "nombre@dominio.com", null, "Contrasena!", "ES0000000000000000000000", 
					notificaciones, grupos, gastos, pagos);
			usuario2 = new Usuario(2, "nombreUsuario", "nombreReal", "nombre@dominio.com", null, "Contrasena!", "ES0000000000000000000000");
			assertAll( () ->{assertNull(usuario1.getFechaNacimiento(), "La fecha de nacimiento es no nula (Constructor general)");},
					()->{assertNull(usuario2.getFechaNacimiento(), "La fecha de nacimiento es no nula (Constructor específico)");}); 
			
					
		}
		
		@Test
		@DisplayName("Verificación de que las notificaciones son no nulas (caso no válido)")
		void testNotificacionesNoValido() {
			usuario1 = new Usuario(1, "nombreUsuario", "nombreReal", "nombre@dominio.com", LocalDate.of(2000, Month.JANUARY, 1), "Contrasena!", "ES0000000000000000000000", 
					null, grupos, gastos, pagos);
			
			assertNull(usuario1.getNotificaciones(), "Las notificaciones son no nulas (Constructor general)");
					
		}
		
		@Test
		@DisplayName("Verificación de que los grupos son no nulos (caso no válido)")
		void testGruposNoValido() {
			usuario1 = new Usuario(1, "nombreUsuario", "nombreReal", "nombre@dominio.com", LocalDate.of(2000, Month.JANUARY, 1), "Contrasena!", "ES0000000000000000000000", 
					notificaciones, null, gastos, pagos);
			
			assertNull(usuario1.getGrupos(), "Los grupos son no nulos (Constructor general)");
					
		}
		
		@Test
		@DisplayName("Verificación de que los gastos son no nulos (caso no válido)")
		void testGastosNoValido() {
			usuario1 = new Usuario(1, "nombreUsuario", "nombreReal", "nombre@dominio.com", LocalDate.of(2000, Month.JANUARY, 1), "Contrasena!", "ES0000000000000000000000", 
					notificaciones, grupos, null, pagos);
			
			assertNull(usuario1.getGastos(), "Los gastos son no nulos (Constructor general)");
					
		}
		
		@Test
		@DisplayName("Verificación de que los pagos son no nulos (caso no válido)")
		void testPagosNoValido() {
			usuario1 = new Usuario(1, "nombreUsuario", "nombreReal", "nombre@dominio.com", LocalDate.of(2000, Month.JANUARY, 1), "Contrasena!", "ES0000000000000000000000", 
					notificaciones, grupos, gastos, null);
			
			assertNull(usuario1.getPagos(), "Los pagos son no nulos (Constructor general)");
					
		}
		
		
	}
	
	
	
	
	@Nested 
	@DisplayName("Pruebas de unidad de creación de grupo desde un Usuario")
	class crearGrupoUnidad{
		AutoCloseable ac1;
		ArrayList<IUsuario> usuarios;

		@Mock
		IGrupo grupoMock;
		
		@InjectMocks
		Usuario usuario3 = new Usuario(1, "nombreUsuario", "nombreReal", "nombre@dominio.com", LocalDate.of(2000, Month.JANUARY, 1), "Nombr€", "ES0000000000000000000000");;
				
		@BeforeEach
		void setUp() throws Exception {
			ac1 = MockitoAnnotations.openMocks(this);
			when(grupoMock.anhadirLider(any(IUsuario.class))).thenReturn(true); // Definir el comportamiento del mock 
																				// que nos interesa para estas pruebas
			usuarios = new ArrayList<>();
		}

		@AfterEach
		void tearDown() throws Exception {
			ac1.close();
		}
		
		@ParameterizedTest
		@DisplayName("Verificación de que el usuario que crea el grupo debe incluir algún usuario")
		@NullAndEmptySource
		void testUsuariosNulos(ArrayList<IUsuario> usuarios_) {
			assertNull(usuario3.crearGrupo(1, "nombre", "descripcion", usuarios_), "Usuario creador del grupo no forma parte del grupo");
		}	
		
		@Test
		@DisplayName("Verificación de que el usuario que crea el grupo se encuentra en [usuarios] (caso no válido)")
		void testUsuarioFuera() {
			IUsuario usuario4 = new Usuario(4, "nomeUsuario", "nomeReal", "nombre@dominio.com", LocalDate.of(2000, Month.JANUARY, 1), "Nombr€", "ES0000000000000000000000");
			usuarios.add(usuario4);
			assertNull(usuario3.crearGrupo(1, "nombre", "descripcion", usuarios), "Usuario creador del grupo no forma parte del grupo");
		}	
		
		@Test
		@DisplayName("Verificación de que el usuario que crea el grupo se encuentra en [usuarios] (caso válido)")
		void testUsuarioDentro() {
			usuarios.add(usuario3);
			assertNotNull(usuario3.crearGrupo(1, "nombre", "descripcion", usuarios), "Usuario creador dentro de grupo invalidado");
		}
		
		@ParameterizedTest
		@DisplayName("Verificación de que el id es un número positivo (caso no válido)")
		@CsvSource({"-1", "0"})
		void testIdNoValido(int id) {
			usuarios.add(usuario3);
			assertNull(usuario3.crearGrupo(id, "nombreGrupo", "descripcion", usuarios), "Id introducido no válido");
		}
		
		@ParameterizedTest
		@DisplayName("Verificación de que el id es un número positivo (caso válido)")
		@CsvSource({"1"})
		void testIdValido(int id) {
			usuarios.add(usuario3);
			assertNotNull(usuario3.crearGrupo(id, "nombreGrupo", "descripcion", usuarios), "Id introducido válido, se trata como no válido");
		}
		
		@ParameterizedTest
		@DisplayName("Verificación de que el nombre no es nulo ni vacío (caso no válido)")
		@NullAndEmptySource
		void testNombreNoValido(String nombre) {
			usuarios.add(usuario3);
			assertNull(usuario3.crearGrupo(1, nombre, "descripcion", usuarios), "Nombre inválido introducido, se trata como válido");
		}
		
		@ParameterizedTest
		@DisplayName("Verificación de que el nombre no es nulo ni vacío (caso válido)")
		@CsvSource({"nombre"})
		void testNombreValido(String nombre) {
			usuarios.add(usuario3);
			assertNotNull(usuario3.crearGrupo(1, nombre, "descripcion", usuarios), "Nombre válido introducido, se trata como no válido");
		}
		
		@ParameterizedTest
		@DisplayName("Verificación de que la descripción no es nula ni vacía (caso no válido)")
		@NullAndEmptySource
		void testDescripcionNoValida(String descripcion) {
			usuarios.add(usuario3);	
			assertNull(usuario3.crearGrupo(1, "nombre", descripcion, usuarios), "Descripcion inválida introducida, se trata como válida");

		}		
		
		@ParameterizedTest
		@DisplayName("Verificación de que la descripción no es nula ni vacía (caso válido)")
		@CsvSource({"descripcion"})
		void testDescripcionValida(String descripcion) {
			ArrayList<IUsuario> usuarios = new ArrayList<IUsuario>();
			usuarios.add(usuario3);	
			assertNotNull(usuario3.crearGrupo(1, "nombre", descripcion, usuarios), "Descripcion válida introducida, se trata como no válida");

		}
	}
	
	@Nested 
	@DisplayName("Pruebas de integración de creación de grupo desde un Usuario")
	class crearGrupoIntegracion{
		IGrupo grupo;
		IUsuario usuario3;
		ArrayList<IUsuario> usuarios;
				
		@BeforeEach
		void setUp() throws Exception {
			usuario3 = new Usuario(1, "nombreUsuario", "nombreReal", "nombre@dominio.com", LocalDate.of(2000, Month.JANUARY, 1), "Nombr€", "ES0000000000000000000000");
			usuarios = new ArrayList<>();
		}

		@AfterEach
		void tearDown() throws Exception {
			
		}
		
		@ParameterizedTest
		@DisplayName("Verificación de que el usuario que crea el grupo debe incluir algún usuario")
		@NullAndEmptySource
		void testUsuariosNulos(ArrayList<IUsuario> usuarios_) {
			assertNull(usuario3.crearGrupo(1, "nombre", "descripcion", usuarios_), "Usuario creador del grupo no forma parte del grupo");
		}	
		
		@Test
		@DisplayName("Verificación de que el usuario que crea el grupo se encuentra en [usuarios] (caso no válido)")
		void testUsuarioFuera() {
			IUsuario usuario4 = new Usuario(4, "nomeUsuario", "nomeReal", "nombre@dominio.com", LocalDate.of(2000, Month.JANUARY, 1), "Nombr€", "ES0000000000000000000000");
			usuarios.add(usuario4);
			assertNull(usuario3.crearGrupo(1, "nombre", "descripcion", usuarios), "Usuario creador del grupo no forma parte del grupo");
		}	
		
		@Test
		@DisplayName("Verificación de que el usuario que crea el grupo se encuentra en [usuarios] (caso válido)")
		void testUsuarioDentro() {
			usuarios.add(usuario3);
			assertAll(	()->{assertNotNull(usuario3.crearGrupo(1, "nombre", "descripcion", usuarios), "Usuario creador dentro de grupo invalidado");},
						()->{assertEquals(1, usuario3.crearGrupo(1, "nombre", "descripcion", usuarios).getId(), "Grupo creado con usuario dentro, pero id incorrecto");},
						()->{assertEquals("nombre", usuario3.crearGrupo(1, "nombre", "descripcion", usuarios).getNombreGrupo(), "Grupo creado con usuario dentro, pero  nombre incorrecto");},
						()->{assertEquals("descripcion", usuario3.crearGrupo(1, "nombre", "descripcion", usuarios).getDescripcion(), "Grupo creado con usuario dentro, pero descripción incorrecto");},
						()->{assertTrue(usuario3.crearGrupo(1, "nombre", "descripcion", usuarios).getUsuarios().contains(usuario3), "Grupo creado con usuario dentro, pero no está el usuario dentro");}
					);
		}
		
		@ParameterizedTest
		@DisplayName("Verificación de que el id es un número positivo (caso no válido)")
		@CsvSource({"-1", "0"})
		void testIdNoValido(int id) {
			usuarios.add(usuario3);
			assertNull(usuario3.crearGrupo(id, "nombreGrupo", "descripcion", usuarios), "Id introducido no válido");
		}
		
		@ParameterizedTest
		@DisplayName("Verificación de que el id es un número positivo (caso válido)")
		@CsvSource({"1"})
		void testIdValido(int id) {
			usuarios.add(usuario3);
			assertNotNull(usuario3.crearGrupo(id, "nombreGrupo", "descripcion", usuarios), "Id introducido válido, se trata como no válido");
			assertAll(	()->{assertNotNull(usuario3.crearGrupo(1, "nombre", "descripcion", usuarios), "Id introducido válido, se trata como no válido");},
					()->{assertEquals(1, usuario3.crearGrupo(1, "nombre", "descripcion", usuarios).getId(), "Grupo creado con id valido, tiene id incorrecto");},
					()->{assertEquals("nombre", usuario3.crearGrupo(1, "nombre", "descripcion", usuarios).getNombreGrupo(), "Grupo creado con id válido, con nombre incorrecto");},
					()->{assertEquals("descripcion", usuario3.crearGrupo(1, "nombre", "descripcion", usuarios).getDescripcion(), "Grupo creado con id válido, con descripción incorrecto");},
					()->{assertTrue(usuario3.crearGrupo(1, "nombre", "descripcion", usuarios).getUsuarios().contains(usuario3), "Grupo creado con id válido, sin el creador en sus usuarios");}
				);
		}
		
		@ParameterizedTest
		@DisplayName("Verificación de que el nombre no es nulo ni vacío (caso no válido)")
		@NullAndEmptySource
		void testNombreNoValido(String nombre) {
			usuarios.add(usuario3);
			assertNull(usuario3.crearGrupo(1, nombre, "descripcion", usuarios), "Nombre inválido introducido, se trata como válido");
		}
		
		@ParameterizedTest
		@DisplayName("Verificación de que el nombre no es nulo ni vacío (caso válido)")
		@CsvSource({"nombre"})
		void testNombreValido(String nombre) {
			usuarios.add(usuario3);
			assertAll(	()->{assertNotNull(usuario3.crearGrupo(1, "nombre", "descripcion", usuarios), "Nombre introducido válido, se trata como no válido");},
					()->{assertEquals(1, usuario3.crearGrupo(1, "nombre", "descripcion", usuarios).getId(), "Grupo creado con nombre valido, tiene id incorrecto");},
					()->{assertEquals("nombre", usuario3.crearGrupo(1, "nombre", "descripcion", usuarios).getNombreGrupo(), "Grupo creado con nombre válido, con nombre incorrecto");},
					()->{assertEquals("descripcion", usuario3.crearGrupo(1, "nombre", "descripcion", usuarios).getDescripcion(), "Grupo creado con nombre válido, con descripción incorrecto");},
					()->{assertTrue(usuario3.crearGrupo(1, "nombre", "descripcion", usuarios).getUsuarios().contains(usuario3), "Grupo creado con nombre válido, sin el creador en sus usuarios");}
				);
		}
		
		@ParameterizedTest
		@DisplayName("Verificación de que la descripción no es nula ni vacía (caso no válido)")
		@NullAndEmptySource
		void testDescripcionNoValida(String descripcion) {
			usuarios.add(usuario3);	
			assertAll(	()->{assertNotNull(usuario3.crearGrupo(1, "nombre", "descripcion", usuarios), "Descripcion introducida válida, se trata como no válido");},
					()->{assertEquals(1, usuario3.crearGrupo(1, "nombre", "descripcion", usuarios).getId(), "Grupo creado con descripcion valida, tiene id incorrecto");},
					()->{assertEquals("nombre", usuario3.crearGrupo(1, "nombre", "descripcion", usuarios).getNombreGrupo(), "Grupo creado con descripcion válida, con nombre incorrecto");},
					()->{assertEquals("descripcion", usuario3.crearGrupo(1, "nombre", "descripcion", usuarios).getDescripcion(), "Grupo creado con descripcion válida, con descripción incorrecto");},
					()->{assertTrue(usuario3.crearGrupo(1, "nombre", "descripcion", usuarios).getUsuarios().contains(usuario3), "Grupo creado con descripcion válida, sin el creador en sus usuarios");}
				);
		}		
		
		@ParameterizedTest
		@DisplayName("Verificación de que la descripción no es nula ni vacía (caso válido)")
		@CsvSource({"descripcion"})
		void testDescripcionValida(String descripcion) {
			ArrayList<IUsuario> usuarios = new ArrayList<IUsuario>();
			usuarios.add(usuario3);	
			assertNotNull(usuario3.crearGrupo(1, "nombre", descripcion, usuarios), "Descripcion válida introducida, se trata como no válida");

		}
	}
	
	@Nested
	@DisplayName("Pruebas de incorporación y eliminación de miembros en Grupo desde Usuario")
	class incorporar_eliminarEnGrupoUnidad{
		IGrupo grupoMock;
		Usuario usuario, usuario1;
		ArrayList<IUsuario> Lideres;
		
		@BeforeEach
		void setUp() throws Exception {
			grupoMock = mock(IGrupo.class);
			
			usuario = new Usuario(1, "nombreUsuario", "nombreReal", "nombre@dominio.com", LocalDate.of(2000, Month.JANUARY, 1), "Nombr€", "ES0000000000000000000000");
			usuario1 = new Usuario(2, "nombreUsuario", "nombreReal", "nombre@dominio.com", LocalDate.of(2000, Month.JANUARY, 1), "Nombr€", "ES0000000000000000000000");
			
			Lideres = new ArrayList<IUsuario>();
			Lideres.add(usuario);
			when(grupoMock.anadirMiembro(any(IUsuario.class))).thenReturn(true);
			when(grupoMock.eliminarMiembro(any(IUsuario.class))).thenReturn(true);
			when(grupoMock.getLideres()).thenReturn(Lideres);
		}

		@AfterEach
		void tearDown() throws Exception {
		}
		
		/**
		 * TESTS DE INCORPORAR
		 */
		
		@Test
		@DisplayName("[Incorporar] Verificación de que el usuario a incorporar es correcto")
		void testIUsuarioNoNulo() {
			
			assertAll( 	()-> {assertFalse(usuario.incorporarMiembroEnGrupo(null, grupoMock), "Usuario nulo introducido tratado como válido");},
						()-> {assertTrue(usuario.incorporarMiembroEnGrupo(usuario1, grupoMock), "Usuario correcto introducido tratado como inválido");}
					);
		}
		
		 @Test
		 @DisplayName("[Incorporar] Verificación grupo nulo o no nulo")
		 void testIGrupoNulo() {
			 assertAll(	()->{assertFalse(usuario.incorporarMiembroEnGrupo(usuario1, null), "Grupo nulo tratado como válido");},
					 	()->{assertTrue(usuario.incorporarMiembroEnGrupo(usuario1, grupoMock), "Grupo no nulo tratado como inválido");}
					 	);
		 }
		
		 @Test
		 @DisplayName("[Incorporar] Verificación grupo sin llamador en lideres")
		 void testIGrupoSinLlamadorEnLideres() {
			 Lideres.remove(usuario);
			 assertFalse(usuario.incorporarMiembroEnGrupo(usuario1, grupoMock), "Grupo sin llamador en lideres validado");
		 }
		 @Test
		 @DisplayName("[Incorporar] Verificación grupo con llamador en lideres")
		 void testIGrupoLlamadorEnLideres() {
			 assertTrue(usuario.incorporarMiembroEnGrupo(usuario1, grupoMock), "Grupo con llamador en lideres invalidado");
		 }
		 
		 
		 /**
		  * TESTS DE ELIMINAR
		  */
		 
		@Test
		@DisplayName("[Eliminar] Verificación de que el usuario a eliminar es correcto")
		void testEUsuarioNoNulo() {
			
			assertAll( 	()-> {assertFalse(usuario.eliminarMiembroEnGrupo(null, grupoMock), "Usuario nulo introducido tratado como válido");},
						()-> {assertTrue(usuario.eliminarMiembroEnGrupo(usuario1, grupoMock), "Usuario correcto introducido tratado como inválido");}
					);
		}
		 @Test
		 @DisplayName("[Eliminar] Verificación grupo nulo o no nulo")
		 void testEGrupoNulo() {
			 assertAll(	()->{assertFalse(usuario.eliminarMiembroEnGrupo(usuario1, null), "Grupo nulo tratado como válido");},
					 	()->{assertTrue(usuario.eliminarMiembroEnGrupo(usuario1, grupoMock), "Grupo no nulo tratado como inválido");}
					 	);
		 }
		 
		 @Test
		 @DisplayName("[Eliminar] Verificación grupo sin llamador en lideres")
		 void testEGrupoSinLlamadorEnLideres() {
			 Lideres.remove(usuario);
			 assertFalse(usuario.eliminarMiembroEnGrupo(usuario1, grupoMock), "Grupo sin llamador en lideres validado");
		 }
		 
		 @Test
		 @DisplayName("[Eliminar] Verificación grupo con llamador en lideres")
		 void testEGrupoLlamadorEnLideres() {
			 assertTrue(usuario.eliminarMiembroEnGrupo(usuario1, grupoMock), "Grupo con llamador en lideres invalidado");
		 }
	}
	
	@Nested
	@DisplayName("Pruebas de integración de incorporar un miembro")
	class incorporarMiembroIntegracion {
		IGrupo grupo;
		Usuario usuario, usuario1;
		ArrayList<IUsuario> Lideres, usuarios;
		
		@BeforeEach
		void setUp() throws Exception {
			usuarios = new ArrayList<IUsuario>();
			usuario = new Usuario(1, "nombreUsuario", "nombreReal", "nombre@dominio.com", LocalDate.of(2000, Month.JANUARY, 1), "Nombr€", "ES0000000000000000000000");
			usuario1 = new Usuario(2, "nombreUsuario", "nombreReal", "nombre@dominio.com", LocalDate.of(2000, Month.JANUARY, 1), "Nombr€", "ES0000000000000000000000");
			usuarios.add(usuario);
			grupo = new Grupo(1, "nombreGrupo", "descripcion", usuarios);
		}

		@AfterEach
		void tearDown() throws Exception {
		}
		@Test
		@DisplayName("[Incorporar] Verificación de que el usuario a incorporar es correcto")
		void testUsuarioNoNulo() {
			
			assertAll(	()-> {assertTrue(grupo.anhadirLider(usuario), "Usuario añadido correcto invalidado en anhadir lider");}, 	
						()-> {assertFalse(usuario.incorporarMiembroEnGrupo(null, grupo), "Usuario nulo introducido tratado como válido");},
						()-> {assertTrue(usuario.incorporarMiembroEnGrupo(usuario1, grupo), "Usuario correcto introducido tratado como inválido");}
					);
		}
		
		 @Test
		 @DisplayName("[Incorporar] Verificación grupo nulo o no nulo")
		 void testIGrupoNulo() {
			 assertAll(	()->{assertTrue(grupo.anhadirLider(usuario), "Usuario añadido correcto invalidado en anhadir lider");},
					 	()->{assertFalse(usuario.incorporarMiembroEnGrupo(usuario1, null), "Grupo nulo tratado como válido");},
					 	()->{assertTrue(usuario.incorporarMiembroEnGrupo(usuario1, grupo), "Grupo no nulo tratado como inválido");}
					 	);
		 }
		
		 @Test
		 @DisplayName("[Incorporar] Verificación grupo sin llamador en lideres")
		 void testIGrupoSinLlamadorEnLideres() {
			 assertFalse(usuario.incorporarMiembroEnGrupo(usuario1, grupo), "Grupo sin llamador en lideres validado");
		 }
		 @Test
		 @DisplayName("[Incorporar] Verificación grupo con llamador en lideres")
		 void testIGrupoLlamadorEnLideres() {
			 assertAll(	()->{assertTrue(grupo.anhadirLider(usuario), "Usuario añadido correcto invalidado en anhadir lider");},
					 	()->{assertTrue(usuario.incorporarMiembroEnGrupo(usuario1, grupo), "Grupo con llamador en lideres invalidado");}
					 	);
			
		 }
		 
	}
	
	@Nested
	@DisplayName("Pruebas de integración de eliminar un miembro")
	class eliminarMiembroIntegracion {
		IGrupo grupo;
		Usuario usuario, usuario1;
		ArrayList<IUsuario> Lideres, usuarios;
		
		@BeforeEach
		void setUp() throws Exception {
			usuarios = new ArrayList<IUsuario>();
			usuario = new Usuario(1, "nombreUsuario", "nombreReal", "nombre@dominio.com", LocalDate.of(2000, Month.JANUARY, 1), "Nombr€", "ES0000000000000000000000");
			usuario1 = new Usuario(2, "nombreUsuario", "nombreReal", "nombre@dominio.com", LocalDate.of(2000, Month.JANUARY, 1), "Nombr€", "ES0000000000000000000000");
			usuarios.add(usuario);
			usuarios.add(usuario1);
			grupo = new Grupo(1, "nombreGrupo", "descripcion", usuarios);
		}

		@AfterEach
		void tearDown() throws Exception {
		}
		@Test
		@DisplayName("[Eliminar] Verificación de que el usuario a eliminar es correcto")
		void testUsuarioNoNulo() {
			
			assertAll(	()-> {assertTrue(grupo.anhadirLider(usuario), "Usuario añadido correcto invalidado en anhadir lider");}, 	
						()-> {assertFalse(usuario.eliminarMiembroEnGrupo(null, grupo), "Usuario nulo introducido tratado como válido");},
						()-> {assertTrue(usuario.eliminarMiembroEnGrupo(usuario1, grupo), "Usuario correcto introducido tratado como inválido");}
					);
		}
		
		 @Test
		 @DisplayName("[Eliminar] Verificación grupo nulo o no nulo")
		 void testGrupoNulo() {
			 assertAll(	()->{assertTrue(grupo.anhadirLider(usuario), "Usuario añadido correcto invalidado en anhadir lider");},
					 	()->{assertFalse(usuario.eliminarMiembroEnGrupo(usuario1, null), "Grupo nulo tratado como válido");},
					 	()->{assertTrue(usuario.eliminarMiembroEnGrupo(usuario1, grupo), "Grupo no nulo tratado como inválido");}
					 	);
		 }
		
		 @Test
		 @DisplayName("[Eliminar] Verificación grupo sin llamador en lideres")
		 void testGrupoSinLlamadorEnLideres() {
			 assertFalse(usuario.eliminarMiembroEnGrupo(usuario1, grupo), "Grupo sin llamador en lideres validado");
		 }
		 @Test
		 @DisplayName("[Eliminar] Verificación grupo con llamador en lideres")
		 void testGrupoLlamadorEnLideres() {
			 assertAll(	()->{assertTrue(grupo.anhadirLider(usuario), "Usuario añadido correcto invalidado en anhadir lider");},
					 	()->{assertTrue(usuario.eliminarMiembroEnGrupo(usuario1, grupo), "Grupo con llamador en lideres invalidado");}
					 	);
			
		 }
		 
	}
	
	@Nested 
	@DisplayName("Pruebas (integración) del método de Usuario.eliminarGrupo()")
	class eliminarGrupoIntegracion{
		// Las pruebas de eliminar grupo son directamente de integración, ya que no hay funcionalidad en usuario,
		// y dependen del método dividirGastos(), que a su vez depende de otros métodos de grupo (lo cual complica 
		// bastante el uso de mocks)
		IGrupo grupo;
		Usuario usuario, usuario1;
		ArrayList<IUsuario> Lideres, usuarios;
		IGasto gasto;
		
		@BeforeEach
		void setUp() throws Exception {
			usuarios = new ArrayList<IUsuario>();
			usuario = new Usuario(1, "nombreUsuario", "nombreReal", "nombre@dominio.com", LocalDate.of(2000, Month.JANUARY, 1), "Nombr€", "ES0000000000000000000000");
			usuario1 = new Usuario(2, "nombreUsuario", "nombreReal", "nombre@dominio.com", LocalDate.of(2000, Month.JANUARY, 1), "Nombr€", "ES0000000000000000000000");
			usuarios.add(usuario);
			usuarios.add(usuario1);
			grupo = new Grupo(1, "nombreGrupo", "descripcion", usuarios);
			
			gasto = new Gasto(1, 4.5, grupo, usuario);
			grupo.getGastos().add(gasto);
		}

		@AfterEach
		void tearDown() throws Exception {
		}
		
		@Test
		@DisplayName("Verificación grupo nulo")
		void testGrupoNulo() {
			assertFalse(usuario.eliminarGrupo(null), "El grupo es nulo");
		}
		
		@Test
		@DisplayName("Verificación grupo no nulo sin llamador en lideres")
		void testGrupoSinLlamadorEnLideres() {
			
			assertFalse(usuario.eliminarGrupo(grupo), "Grupo sin llamador en líderes validado");
		}
		
		@Test
		@DisplayName("Verificación grupo sin gastos (no se pueden dividir gastos)")
		void testGrupoSinGastos() {
			grupo.anhadirLider(usuario);
			grupo.getGastos().clear();
			
			assertFalse(usuario.eliminarGrupo(grupo), "Grupo sin gastos validado");
		}
		
		@Test
		@DisplayName("Verificación grupo no nulo con llamador en lideres")
		void testGrupoConLlamadorEnLideres() {
			grupo.anhadirLider(usuario);

			assertTrue(usuario.eliminarGrupo(grupo), "Grupo con llamador en líderes invalidado");
		}
	}

	@Nested 
	@DisplayName("Pruebas de gestión de grupo desde un Usuario")
	class gestionarGrupo{
		
		IGrupo grupoMock;
		Usuario usuario3;
		ArrayList<IUsuario> usuarios;
		
		@BeforeEach
		void setUp() throws Exception {
			grupoMock =mock(Grupo.class);
			usuario3 = new Usuario(1, "nombreUsuario", "nombreReal", "nombre@dominio.com", LocalDate.of(2000, Month.JANUARY, 1), "Nombr€", "ES0000000000000000000000");
			usuarios = new ArrayList<>();
		}

		@AfterEach
		void tearDown() throws Exception {
			
		}
		
		@Test
		@DisplayName("Verificación de que el grupo a modificar es no nulo")
		void testGrupoNulo() {
			assertFalse(usuario3.gestionarGrupo(null, "nombre", "descripcion", usuarios), "El grupo es nulo");
		}
		
		@ParameterizedTest
		@DisplayName("Verificación de que el usuario que gestiona el grupo debe incluir algún usuario")
		@NullAndEmptySource
		void testUsuariosNulos(ArrayList<IUsuario> usuarios_) {
			assertFalse(usuario3.gestionarGrupo(grupoMock, "nombre", "descripcion", usuarios_), "Usuario no forma parte del grupo");
		}	
		
		@Test
		@DisplayName("Verificación de que el usuario que crea el grupo se encuentra en [usuarios] (caso no válido)")
		void testUsuarioFuera() {
			IUsuario usuario4 = new Usuario(4, "nomeUsuario", "nomeReal", "nombre@dominio.com", LocalDate.of(2000, Month.JANUARY, 1), "Nombr€", "ES0000000000000000000000");
			usuarios.add(usuario4);
			assertFalse(usuario3.gestionarGrupo(grupoMock, "nombre", "descripcion", usuarios), "Usuario no forma parte del grupo");
		}	
		
		@Test
		@DisplayName("Verificación de que el usuario que crea el grupo se encuentra en [usuarios] (caso válido)")
		void testUsuarioDentro() {
			usuarios.add(usuario3);
			assertTrue(usuario3.gestionarGrupo(grupoMock, "nombre", "descripcion", usuarios), "Usuario dentro del grupo invalidado");
		}
		
		@ParameterizedTest
		@DisplayName("Verificación de que el nombre no es nulo ni vacío (caso no válido)")
		@NullAndEmptySource
		void testNombreNoValido(String nombre) {
			usuarios.add(usuario3);
			assertFalse(usuario3.gestionarGrupo(grupoMock, nombre, "descripcion", usuarios), "Nombre inválido introducido, se trata como válido");
		}
		
		@ParameterizedTest
		@DisplayName("Verificación de que el nombre no es nulo ni vacío (caso válido)")
		@CsvSource({"nombre"})
		void testNombreValido(String nombre) {
			usuarios.add(usuario3);
			assertTrue(usuario3.gestionarGrupo(grupoMock, nombre, "descripcion", usuarios), "Nombre válido introducido, se trata como no válido");
		}
		
		@ParameterizedTest
		@DisplayName("Verificación de que la descripción no es nula ni vacía (caso no válido)")
		@NullAndEmptySource
		void testDescripcionNoValida(String descripcion) {
			usuarios.add(usuario3);	
			assertFalse(usuario3.gestionarGrupo(grupoMock, "nombre", descripcion, usuarios), "Descripcion inválida introducida, se trata como válida");

		}		
		
		@ParameterizedTest
		@DisplayName("Verificación de que la descripción no es nula ni vacía (caso válido)")
		@CsvSource({"descripcion"})
		void testDescripcionValida(String descripcion) {
			ArrayList<IUsuario> usuarios = new ArrayList<IUsuario>();
			usuarios.add(usuario3);	
			assertTrue(usuario3.gestionarGrupo(grupoMock, "nombre", descripcion, usuarios), "Descripcion válida introducida, se trata como no válida");

		}
	}
	
	@Nested 
	@DisplayName("Pruebas de integración de gestión de grupo desde un Usuario")
	class gestionarGrupoIntegración{
		
		IGrupo grupo;
		Usuario usuario3;
		ArrayList<IUsuario> usuarios;
		
		@BeforeEach
		void setUp() throws Exception {
			usuario3 = new Usuario(1, "nombreUsuario", "nombreReal", "nombre@dominio.com", LocalDate.of(2000, Month.JANUARY, 1), "Nombr€", "ES0000000000000000000000");
			usuarios = new ArrayList<>();
			usuarios.add(usuario3);
			grupo = new Grupo(1, "nombre", "descripción", usuarios);
		}

		@AfterEach
		void tearDown() throws Exception {
			
		}
		
		@Test
		@DisplayName("Verificación de que el grupo a modificar es no nulo")
		void testGrupoNulo() {
			assertFalse(usuario3.gestionarGrupo(null, "nombre", "descripcion", usuarios), "El grupo es nulo");
		}
		
		@ParameterizedTest
		@DisplayName("Verificación de que el usuario que gestiona el grupo debe incluir algún usuario")
		@NullAndEmptySource
		void testUsuariosNulos(ArrayList<IUsuario> usuarios_) {
			assertFalse(usuario3.gestionarGrupo(grupo, "nombre", "descripcion", usuarios_), "Usuario no forma parte del grupo");
		}	
		
		@Test
		@DisplayName("Verificación de que el usuario que crea el grupo se encuentra en [usuarios] (caso no válido)")
		void testUsuarioFuera() {
			IUsuario usuario4 = new Usuario(4, "nomeUsuario", "nomeReal", "nombre@dominio.com", LocalDate.of(2000, Month.JANUARY, 1), "Nombr€", "ES0000000000000000000000");
			usuarios.clear();
			usuarios.add(usuario4);
			assertFalse(usuario3.gestionarGrupo(grupo, "nombre", "descripcion", usuarios), "Usuario no forma parte del grupo");
		}	
		
		@Test
		@DisplayName("Verificación de que el usuario que crea el grupo se encuentra en [usuarios] (caso válido)")
		void testUsuarioDentro() {
			usuarios.add(usuario3);
			assertTrue(usuario3.gestionarGrupo(grupo, "nombre", "descripcion", usuarios), "Usuario dentro del grupo invalidado");
		}
		
		@ParameterizedTest
		@DisplayName("Verificación de que el nombre no es nulo ni vacío (caso no válido)")
		@NullAndEmptySource
		void testNombreNoValido(String nombre) {
			usuarios.add(usuario3);
			assertFalse(usuario3.gestionarGrupo(grupo, nombre, "descripcion", usuarios), "Nombre inválido introducido, se trata como válido");
		}
		
		@ParameterizedTest
		@DisplayName("Verificación de que el nombre no es nulo ni vacío (caso válido)")
		@CsvSource({"nombre"})
		void testNombreValido(String nombre) {
			usuarios.add(usuario3);
			assertTrue(usuario3.gestionarGrupo(grupo, nombre, "descripcion", usuarios), "Nombre válido introducido, se trata como no válido");
		}
		
		@ParameterizedTest
		@DisplayName("Verificación de que la descripción no es nula ni vacía (caso no válido)")
		@NullAndEmptySource
		void testDescripcionNoValida(String descripcion) {
			usuarios.add(usuario3);	
			assertFalse(usuario3.gestionarGrupo(grupo, "nombre", descripcion, usuarios), "Descripcion inválida introducida, se trata como válida");

		}		
		
		@ParameterizedTest
		@DisplayName("Verificación de que la descripción no es nula ni vacía (caso válido)")
		@CsvSource({"descripcion"})
		void testDescripcionValida(String descripcion) {
			ArrayList<IUsuario> usuarios = new ArrayList<IUsuario>();
			usuarios.add(usuario3);	
			assertTrue(usuario3.gestionarGrupo(grupo, "nombre", descripcion, usuarios), "Descripcion válida introducida, se trata como no válida");

		}
	}
	
	
	
	@Nested 
	@DisplayName("Pruebas de añadir un gasto desde un Usuario")
	class anadirGasto{
		
		IGrupo grupoMock;
		IGasto gastoMock;
		Usuario usuario3;
		
		@BeforeEach
		void setUp() throws Exception {
			grupoMock = mock(Grupo.class);
			gastoMock = mock(Gasto.class);
			usuario3 = new Usuario(1, "nombreUsuario", "nombreReal", "nombre@dominio.com", LocalDate.of(2000, Month.JANUARY, 1), "Nombr€", "ES0000000000000000000000");
		}

		@AfterEach
		void tearDown() throws Exception {
			
		}
		
		@Test
		@DisplayName("Verificación de que el grupo es no nulo")
		void testUsuariosNulos() {
			assertFalse(usuario3.anadirGasto(null, 10.0), "El grupo es nulo");
		}
		
		@Test
		@DisplayName("Verificación de que el grupo debe incluir algún usuario")
		void testUsuariosVacio() {
			assertFalse(usuario3.anadirGasto(grupoMock, 10.0), "Usuario no forma parte del grupo");
		}	
		
		@Test
		@DisplayName("Verificación de que el usuario se encuentra en [usuarios] del grupo (caso no válido)")
		void testUsuarioFuera() {
			IUsuario usuario4 = new Usuario(4, "nomeUsuario", "nomeReal", "nombre@dominio.com", LocalDate.of(2000, Month.JANUARY, 1), "Nombr€", "ES0000000000000000000000");
			when(grupoMock.getUsuarios()).thenReturn(new ArrayList<IUsuario>(Arrays.asList(usuario3)));
			assertFalse(usuario4.anadirGasto(grupoMock, 10.0), "Usuario no forma parte del grupo");
		}	
		
		@Test
		@DisplayName("Verificación de que el usuario se encuentra en [usuarios] del grupo (caso válido)")
		void testUsuarioDentro() {
			when(grupoMock.getUsuarios()).thenReturn(new ArrayList<IUsuario>(Arrays.asList(usuario3)));
			assertTrue(usuario3.anadirGasto(grupoMock, 10.0), "Usuario dentro del grupo invalidado");
		}
		
		@ParameterizedTest
		@DisplayName("Verificación de que el importe del gasto es positivo (caso no válido)")
		@CsvSource({"-10", "0"})
		void testImporteNoValido(double importe) {
			when(grupoMock.getUsuarios()).thenReturn(new ArrayList<IUsuario>(Arrays.asList(usuario3)));
			assertFalse(usuario3.anadirGasto(grupoMock, importe), "Gasto con importe cero o negativo");
		}
		
		@ParameterizedTest
		@DisplayName("Verificación de que el importe del gasto es positivo (caso válido)")
		@CsvSource({"0.01", "10"})
		void testImporteValido(double importe) {
			when(grupoMock.getUsuarios()).thenReturn(new ArrayList<IUsuario>(Arrays.asList(usuario3)));
			assertTrue(usuario3.anadirGasto(grupoMock, importe), "Gasto con importe positivo invalidado");
		}
		
	}
	
	
	@Nested 
	@DisplayName("Pruebas de integración de añadir un gasto desde un Usuario")
	class anadirGastoIntegracion{
		
		IGrupo grupo;
		IGasto gasto;
		Usuario usuario3;
		
		@BeforeEach
		void setUp() throws Exception {
			usuario3 = new Usuario(1, "nombreUsuario", "nombreReal", "nombre@dominio.com", LocalDate.of(2000, Month.JANUARY, 1), "Nombr€", "ES0000000000000000000000");
		}

		@AfterEach
		void tearDown() throws Exception {
			
		}
		
		@Test
		@DisplayName("Verificación de que el grupo es no nulo")
		void testUsuariosNulos() {
			assertFalse(usuario3.anadirGasto(null, 10.0), "El grupo es nulo");
		}
		
		@Test
		@DisplayName("Verificación de que el grupo no tiene un conjunto de usuarios nulo")
		void testUsuariosNull() {
			//Creamos incorrectamente el grupo para poder poner el conjunto de usuario nulo
			grupo = new Grupo(0, null, null, null);
			assertFalse(usuario3.anadirGasto(grupo, 10.0), "El conjunto de usuarios es nulo");
		}	
		
		@Test
		@DisplayName("Verificación de que el grupo debe incluir algún usuario")
		void testUsuariosVacio() {
			grupo = new Grupo(1, "nombre", "descripcion", new ArrayList<IUsuario>(Arrays.asList(usuario3)));
			grupo.getUsuarios().clear();
			assertFalse(usuario3.anadirGasto(grupo, 10.0), "Usuario no forma parte del grupo");
		}
		
		@Test
		@DisplayName("Verificación de que el usuario se encuentra en [usuarios] del grupo (caso no válido)")
		void testUsuarioFuera() {
			grupo = new Grupo(1, "nombre", "descripcion", new ArrayList<IUsuario>(Arrays.asList(usuario3)));
			IUsuario usuario4 = new Usuario(4, "nomeUsuario", "nomeReal", "nombre@dominio.com", LocalDate.of(2000, Month.JANUARY, 1), "Nombr€", "ES0000000000000000000000");
			assertFalse(usuario4.anadirGasto(grupo, 10.0), "Usuario no forma parte del grupo");
		}	
		
		@Test
		@DisplayName("Verificación de que el usuario se encuentra en [usuarios] del grupo (caso válido)")
		void testUsuarioDentro() {
			grupo = new Grupo(1, "nombre", "descripcion", new ArrayList<IUsuario>(Arrays.asList(usuario3)));
			assertTrue(usuario3.anadirGasto(grupo, 10.0), "Usuario dentro del grupo invalidado");
		}
		
		@ParameterizedTest
		@DisplayName("Verificación de que el importe del gasto es positivo (caso no válido)")
		@CsvSource({"-10", "0"})
		void testImporteNoValido(double importe) {
			grupo = new Grupo(1, "nombre", "descripcion", new ArrayList<IUsuario>(Arrays.asList(usuario3)));
			assertFalse(usuario3.anadirGasto(grupo, importe), "Gasto con importe cero o negativo");
		}
		
		@ParameterizedTest
		@DisplayName("Verificación de que el importe del gasto es positivo (caso válido)")
		@CsvSource({"0.01", "10"})
		void testImporteValido(double importe) {
			grupo = new Grupo(1, "nombre", "descripcion", new ArrayList<IUsuario>(Arrays.asList(usuario3)));
			assertTrue(usuario3.anadirGasto(grupo, importe), "Gasto con importe positivo invalidado");
		}
		
	}
	
	
	@Nested 
	@DisplayName("Pruebas de dividir gastos desde un Usuario")
	class dividirGastos{
		
		IGrupo grupoMock;
		IGasto gastoMock;
		Usuario usuario3;
		
		@BeforeEach
		void setUp() throws Exception {
			grupoMock = mock(Grupo.class);
			gastoMock = mock(Gasto.class);
			usuario3 = new Usuario(1, "nombreUsuario", "nombreReal", "nombre@dominio.com", LocalDate.of(2000, Month.JANUARY, 1), "Nombr€", "ES0000000000000000000000");
			// Suponemos que si se llega a la parte más interior de las condiciones, se pasa la prueba 
			when(grupoMock.dividirGastos()).thenReturn(true);
		}

		@AfterEach
		void tearDown() throws Exception {
			
		}
		
		@Test
		@DisplayName("Verificación de que el grupo es no nulo")
		void testGrupoNulo() {
			assertFalse(usuario3.dividirGastos(null), "El grupo es nulo");
		}
		
		@Test
		@DisplayName("Verificación de que el conjunto de usuarios del grupo es no nulo")
		void testUsuariosNulo() {
			when(grupoMock.getUsuarios()).thenReturn(null);
			when(grupoMock.getGastos()).thenReturn(new ArrayList<IGasto>(Arrays.asList(gastoMock)));
			assertFalse(usuario3.dividirGastos(grupoMock), "El conjunto de usuarios del grupo es nulo");
		}	
		
		@Test
		@DisplayName("Verificación de que el grupo debe incluir algún usuario")
		void testUsuariosVacio() {
			when(grupoMock.getUsuarios()).thenReturn(new ArrayList<IUsuario>());
			when(grupoMock.getGastos()).thenReturn(new ArrayList<IGasto>(Arrays.asList(gastoMock)));
			assertFalse(usuario3.dividirGastos(grupoMock), "El grupo no tiene integrantes");
		}
		
		@Test
		@DisplayName("Verificación de que el conjunto de gastos del grupo es no nulo")
		void testGastosNulo() {
			when(grupoMock.getUsuarios()).thenReturn(new ArrayList<IUsuario>(Arrays.asList(usuario3)));
			when(grupoMock.getGastos()).thenReturn(null);
			assertFalse(usuario3.dividirGastos(grupoMock), "El conjunto de gastos del grupo es nulo");
		}	
		
		@Test
		@DisplayName("Verificación de que el grupo debe incluir algún gasto")
		void testGastosVacio() {
			when(grupoMock.getUsuarios()).thenReturn(new ArrayList<IUsuario>(Arrays.asList(usuario3)));
			when(grupoMock.getGastos()).thenReturn(new ArrayList<IGasto>());
			assertFalse(usuario3.dividirGastos(grupoMock), "El conjunto de gastos está vacío");
		}
		
		@Test
		@DisplayName("Verificación del pago caso válido")
		void testValido() {
			when(grupoMock.getUsuarios()).thenReturn(new ArrayList<IUsuario>(Arrays.asList(usuario3)));
			when(grupoMock.getGastos()).thenReturn(new ArrayList<IGasto>(Arrays.asList(gastoMock)));
			assertTrue(usuario3.dividirGastos(grupoMock), "El grupo es en realidad correcto");
		}
		
	}
	
	@Nested 
	@DisplayName("Pruebas integración de dividir gastos desde un Usuario")
	class dividirGastosIntegración{
		
		IGrupo grupo;
		IGasto gasto;
		Usuario usuario3;
		
		@BeforeEach
		void setUp() throws Exception {
			usuario3 = new Usuario(1, "nombreUsuario", "nombreReal", "nombre@dominio.com", LocalDate.of(2000, Month.JANUARY, 1), "Nombr€", "ES0000000000000000000000");
		}

		@AfterEach
		void tearDown() throws Exception {
			
		}
		
		@Test
		@DisplayName("Verificación de que el grupo es no nulo")
		void testGrupoNulo() {
			assertFalse(usuario3.dividirGastos(null), "El grupo es nulo");
		}
		
		@Test
		@DisplayName("Verificación de que el conjunto de usuarios del grupo es no nulo")
		void testUsuariosNulo() {
			grupo = new Grupo(0, null, null, null);
			grupo.setGastos(new ArrayList<IGasto>(Arrays.asList(gasto)));
			assertFalse(usuario3.dividirGastos(grupo), "El conjunto de usuarios del grupo es nulo");
		}	
		
		@Test
		@DisplayName("Verificación de que el grupo debe incluir algún usuario")
		void testUsuariosVacio() {
			grupo = new Grupo(1, "nombre", "descripcion", new ArrayList<IUsuario>(Arrays.asList(usuario3)));
			grupo.getUsuarios().clear();
			assertFalse(usuario3.dividirGastos(grupo), "El grupo no tiene integrantes");
		}
		
		@Test
		@DisplayName("Verificación de que el conjunto de gastos del grupo es no nulo")
		void testGastosNulo() {
			grupo = new Grupo(0, null, null, null);
			grupo.setUsuarios(new ArrayList<>(Arrays.asList(usuario3)));
			assertFalse(usuario3.dividirGastos(grupo), "El conjunto de gastos del grupo es nulo");
		}	
		
		@Test
		@DisplayName("Verificación de que el grupo debe incluir algún gasto")
		void testGastosVacio() {
			grupo = new Grupo(1, "nombre", "descripcion", new ArrayList<IUsuario>(Arrays.asList(usuario3)));
			grupo.getGastos().clear();
			assertFalse(usuario3.dividirGastos(grupo), "El conjunto de gastos está vacío");
		}
		
		@Test
		@DisplayName("Verificación del pago caso válido")
		void testValido() {
			grupo = new Grupo(1, "nombre", "descripcion", new ArrayList<IUsuario>(Arrays.asList(usuario3)));
			gasto = new Gasto(1, 5.0, grupo, usuario3);
			grupo.getGastos().add(gasto);
			assertTrue(usuario3.dividirGastos(grupo), "El grupo es en realidad correcto");
		}
		
	}
	
	@Nested 
	@DisplayName("Pruebas de unidad de notificar")
	class notificar{
		IPago pagoMock;
		IGrupo grupoMock;
		HashMap<IUsuario, Double> misPagos;
		HashMap<IUsuario, HashMap<IUsuario, Double>> cuotas;
		
		@BeforeEach
		void setUp() throws Exception {
			usuario1 = new Usuario(1, "nombreUsuario1", "nombreReal1", "nombre@dominio.com", LocalDate.of(2000, Month.JANUARY, 1), "Contrasena!", "ES0000000000000000000000");
			usuario2 = new Usuario(2, "nombreUsuario2", "nombreReal2", "nombre@dominio.com", LocalDate.of(2000, Month.JANUARY, 1), "Contrasena!", "ES0000000000000000000000");
			misPagos = new HashMap<>();
			misPagos.put(usuario2, 20.5);
			
			cuotas = new HashMap<>();
			cuotas.put(usuario1, misPagos);
			
			pagoMock = mock(IPago.class);
			grupoMock = mock(IGrupo.class);
			when(pagoMock.getCuotas()).thenReturn(cuotas);
			when(pagoMock.getGrupoGasto()).thenReturn(grupoMock);
			when(grupoMock.getUsuarios()).thenReturn(new ArrayList<IUsuario>(Arrays.asList(usuario1, usuario2)));

		}

		@AfterEach
		void tearDown() throws Exception {
			
		}
		
		// No hay más casos no válidos ya que ninguna de las comprobaciones se realiza sobre la clase Usuario, sino sobre las auxiliares
		@Test
		@DisplayName("Notificación caso no válido")
		void testNotificacionNoValido() {
			assertFalse(usuario1.notificar(null), "Se ha notificado una notificación incorrecta");
		}
		
		@Test
		@DisplayName("Notificación caso válido")
		void testNotificacionValido() {
			assertAll( ()->{assertTrue(usuario1.notificar(pagoMock), "No se ha notificado una notificación correcta");},
					()->{assertEquals("Pago pendiente de 20.5 a nombreReal2\n\n", usuario1.getNotificaciones().get(0), "La notificación no es igual a la correcta");});
		}
	}
	
	@Nested 
	@DisplayName("Pruebas de integración de notificar")
	class notificarIntegracion{
		IPago pago;
		IGrupo grupo;
		HashMap<IUsuario, Double> misPagos;
		HashMap<IUsuario, HashMap<IUsuario, Double>> cuotas;
		
		@BeforeEach
		void setUp() throws Exception {
			usuario1 = new Usuario(1, "nombreUsuario1", "nombreReal1", "nombre@dominio.com", LocalDate.of(2000, Month.JANUARY, 1), "Contrasena!", "ES0000000000000000000000");
			usuario2 = new Usuario(2, "nombreUsuario2", "nombreReal2", "nombre@dominio.com", LocalDate.of(2000, Month.JANUARY, 1), "Contrasena!", "ES0000000000000000000000");
			
			
			grupo = new Grupo(1, "nombreGrupo", "descripcion", new ArrayList<IUsuario>(Arrays.asList(usuario1, usuario2)));
			grupo.anadirGasto(new Gasto(1, 20.5, grupo, usuario2));
			
			pago = new Pago(1, grupo);
			
			misPagos = new HashMap<>();
			misPagos.put(usuario2, 20.5);
			cuotas = new HashMap<>();
			cuotas.put(usuario1, misPagos);
			pago.setCuotas(cuotas);

		}

		@AfterEach
		void tearDown() throws Exception {
			
		}
		
		@Test
		@DisplayName("Notificación caso válido")
		void testNotificacionValido() {
			assertAll( ()->{assertTrue(usuario1.notificar(pago), "No se ha notificado una notificación correcta");},
					()->{assertEquals("Pago pendiente de 20.5 a nombreReal2\n\n", usuario1.getNotificaciones().get(0), "La notificación no es igual a la correcta");});
		}
		
		@Test
		@DisplayName("Notificación pago nulo")
		void testPagoNulo() {
			assertFalse(usuario1.notificar(null), "El pago es nulo");
		}
		
		@Test
		@DisplayName("Notificación grupo nulo")
		void testGrupoNulo() {
			pago = new Pago(1, null); //Para poner el grupo a nulo, hay que inicializar incorrectamente el pago
			
			assertFalse(usuario1.notificar(pago), "El grupo es nulo");
		}
		
		@Test
		@DisplayName("Notificación conjunto de usuarios nulo")
		void testUsuarioNulo() {
			grupo = new Grupo(1, null, null, null); //Para poner a nulo, hay que inicializar incorrectamente el grupo
			pago.setGrupoGasto(grupo);
			
			assertFalse(usuario1.notificar(pago), "El conjunto de usuarios es nulo");
		}
		
		@Test
		@DisplayName("Notificación el usuario se encuentra fuera del grupo")
		void testUsuarioFuera() {
			grupo.getUsuarios().remove(usuario1);
			pago.setGrupoGasto(grupo);
			
			assertFalse(usuario1.notificar(pago), "El usuario se encuentra fuera del grupo");
		}
		
		@Test
		@DisplayName("Notificación cuotas del pago nulo")
		void testCuotasNulo() {
			pago = new Pago(1, null); //Para poner las cuotas a nulo, hay que inicializar incorrectamente el pago
			pago.setGrupoGasto(grupo);
			
			assertFalse(usuario1.notificar(pago), "Las cuotas del pago es nulo");
		}
		
		@Test
		@DisplayName("Notificación cuotas del pago no contienen al usuario")
		void testCuotasFuera() {
			misPagos = new HashMap<>();
			misPagos.put(usuario1, 20.5);
			cuotas = new HashMap<>();
			cuotas.put(usuario2, misPagos);
			pago.setCuotas(cuotas);
			
			assertFalse(usuario1.notificar(pago), "Las cuotas del pago no contienen al usuario");
		}
		
		
	}
	
	@Nested 
	@DisplayName("Pruebas de unidad de realizar pago")
	class realizarPago{
		IPago pagoMock;
		IGasto gastoMock;
		IGrupo grupoMock;
		HashMap<IUsuario, HashMap<IUsuario, Double>> cuotas;
		HashMap<IUsuario, Boolean> pagado;
		
		@BeforeEach
		void setUp() throws Exception {
			usuario1 = new Usuario(1, "nombreUsuario1", "nombreReal1", "nombre@dominio.com", LocalDate.of(2000, Month.JANUARY, 1), "Contrasena!", "ES0000000000000000000000");
			usuario2 = new Usuario(2, "nombreUsuario2", "nombreReal2", "nombre@dominio.com", LocalDate.of(2000, Month.JANUARY, 1), "Contrasena!", "ES0000000000000000000000");
			
			cuotas = new HashMap<>();
			cuotas.put(usuario1, new HashMap<>());
			cuotas.put(usuario2, new HashMap<>());
			cuotas.get(usuario1).put(usuario2, 20.5);
			cuotas.get(usuario2).put(usuario1, 0.0);
			
			pagado = new HashMap<>();
			pagado.put(usuario1, false);
			pagado.put(usuario2, false);
			
			pagoMock = mock(IPago.class);
			gastoMock = mock(IGasto.class);
			grupoMock = mock(IGrupo.class);
			usuario1.getPagos().add(pagoMock);
			usuario1.getNotificaciones().add("Pago pendiente de 20.5 a nombreReal2\n\n");
			
			when(pagoMock.getCuotas()).thenReturn(cuotas);
			when(pagoMock.getPagado()).thenReturn(pagado);
			when(pagoMock.getGrupoGasto()).thenReturn(grupoMock);
			when(grupoMock.getUsuarios()).thenReturn(new ArrayList<IUsuario>(Arrays.asList(usuario1, usuario2)));

		}

		@AfterEach
		void tearDown() throws Exception {
			
		}
		
		// No hay más casos no válidos ya que las otras comprobaciones no se realizan sobre la clase Usuario, sino sobre las auxiliares
		@Test
		@DisplayName("Realizar pago caso no válido")
		void testPagoNoValido() {
			usuario1.getPagos().clear();
			assertFalse(usuario1.realizarPago(pagoMock), "El pago no se encuentra en los pagos del usuario");
		}
		
		@Test
		@DisplayName("Realizar pago caso válido")
		void testPagoValido() {
			assertAll( ()->{assertTrue(usuario1.realizarPago(pagoMock), "No se ha notificado una notificación correcta");},
					()->{assertEquals("Se ha pagado 20.5 a nombreReal2\n\n", usuario1.getNotificaciones().get(0), "La notificación no es igual a la correcta");});
		}
	}
	
	@Nested 
	@DisplayName("Pruebas de integración de realizar pago")
	class realizarPagoIntegración{
		IPago pago;
		IGasto gasto;
		IGrupo grupo;
		HashMap<IUsuario, HashMap<IUsuario, Double>> cuotas;
		HashMap<IUsuario, Boolean> pagado;
		
		@BeforeEach
		void setUp() throws Exception {
			usuario1 = new Usuario(1, "nombreUsuario1", "nombreReal1", "nombre@dominio.com", LocalDate.of(2000, Month.JANUARY, 1), "Contrasena!", "ES0000000000000000000000");
			usuario2 = new Usuario(2, "nombreUsuario2", "nombreReal2", "nombre@dominio.com", LocalDate.of(2000, Month.JANUARY, 1), "Contrasena!", "ES0000000000000000000000");
			grupo = new Grupo(1, "nombreGrupo", "descripcion", new ArrayList<IUsuario>(Arrays.asList(usuario1, usuario2)));
			gasto = new Gasto(1, 20.5, grupo, usuario2);
			grupo.setGastos(new ArrayList<IGasto>(Arrays.asList(gasto)));
			pago = new Pago(1, grupo);
					
			cuotas = new HashMap<>();
			cuotas.put(usuario1, new HashMap<>());
			cuotas.put(usuario2, new HashMap<>());
			cuotas.get(usuario1).put(usuario2, 20.5);
			cuotas.get(usuario2).put(usuario1, 0.0);
			
			pagado = new HashMap<>();
			pagado.put(usuario1, false);
			pagado.put(usuario2, false);
			
			usuario1.getPagos().add(pago);
			usuario1.getNotificaciones().add("Pago pendiente de 20.5 a nombreReal2\n\n");
			pago.setCuotas(cuotas);
			pago.setPagado(pagado);
			pago.setGastos(new ArrayList<IGasto>(Arrays.asList(gasto)));
		}

		@AfterEach
		void tearDown() throws Exception {
			
		}
		
		@Test
		@DisplayName("Realizar pago caso válido")
		void testPagoValido() {
			assertAll( ()->{assertTrue(usuario1.realizarPago(pago), "No se ha notificado una notificación correcta");},
					()->{assertEquals("Se ha pagado 20.5 a nombreReal2\n\n", usuario1.getNotificaciones().get(0), "La notificación no es igual a la correcta");},
					()->{assertTrue(pago.getPagado().get(usuario1), "Aparece que el usuario aún no ha pagado");});
		}
		
		@Test
		@DisplayName("Realizar pago nulo")
		void testPagoNulo() {
			assertFalse(usuario1.realizarPago(null), "El pago es nulo");
		}
		
		@Test
		@DisplayName("Realizar pago sin pagos asignados al usuario")
		void testPagoNoAsignado() {
			usuario1.getPagos().clear();
			assertFalse(usuario1.realizarPago(pago), "El usuario no tiene pagos asignados");
		}
		
		@Test
		@DisplayName("Realizar pago con un grupo nulo")
		void testPagoGrupoNulo() {
			// Para poder poner el grupo a null, hay que crear el pago de forma incorrecta, y añadirle los otros valores después 
			usuario1.getPagos().clear();
			pago = new Pago(0, null);
			pago.setCuotas(cuotas);
			pago.setPagado(pagado);
			//pago.setGrupoGasto(grupo);
			pago.setGastos(new ArrayList<IGasto>(Arrays.asList(gasto)));
			usuario1.getPagos().add(pago);
			
			assertFalse(usuario1.realizarPago(pago), "El pago tiene el grupo nulo");
		}
		
		@Test
		@DisplayName("Realizar pago con un grupo con un conjunto de usuario nulo")
		void testPagoUsuariosNulo() {
			// Para poner el conjunto de usuario a nulo, hay que crear el grupo de forma incorrecta
			grupo = new Grupo(0, null, null, null);
			grupo.setGastos(new ArrayList<IGasto>(Arrays.asList(gasto)));
			pago.setGrupoGasto(grupo);

			assertFalse(usuario1.realizarPago(pago), "El grupo tiene un conjunto de usuario nulo");
		}
		
		@Test
		@DisplayName("Realizar pago con un grupo que no contiene al usuario")
		void testPagoUsuarioFuera() {
			grupo.setUsuarios(new ArrayList<IUsuario>(Arrays.asList(usuario2)));
			pago.setGrupoGasto(grupo);

			assertFalse(usuario1.realizarPago(pago), "El usuario no pertenece al grupo");
		}
		
		@Test
		@DisplayName("Realizar pago con cuotas a nulo")
		void testPagoCuotasNulo() {
			// Para poder poner las cuotas a null, hay que crear el pago de forma incorrecta, y añadirle los otros valores después 
			usuario1.getPagos().clear();
			pago = new Pago(0, null);
			//pago.setCuotas(cuotas);
			pago.setPagado(pagado);
			pago.setGrupoGasto(grupo);
			pago.setGastos(new ArrayList<IGasto>(Arrays.asList(gasto)));
			usuario1.getPagos().add(pago);

			assertFalse(usuario1.realizarPago(pago), "Las cuotas del grupo son nulas");
		}
		
		@Test
		@DisplayName("Realizar pago con ninguna cuota asignada al usuario")
		void testPagoCuotaFuera() {
			pago.getCuotas().remove(usuario1);

			assertFalse(usuario1.realizarPago(pago), "No hay ninguna cuota asignada al usuario");
		}
		
		@Test
		@DisplayName("Realizar pago con el array de Pagado nulo")
		void testPagoPagadoNulo() {
			// Para poder poner pagado a null, hay que crear el pago de forma incorrecta, y añadirle los otros valores después 
			usuario1.getPagos().clear();
			pago = new Pago(0, null);
			pago.setCuotas(cuotas);
			//pago.setPagado(pagado);
			pago.setGrupoGasto(grupo);
			pago.setGastos(new ArrayList<IGasto>(Arrays.asList(gasto)));
			usuario1.getPagos().add(pago);

			assertFalse(usuario1.realizarPago(pago), "El array de pagado es nulo");
		}
		
		@Test
		@DisplayName("Realizar pago sin que el array de Pagado incluya al usuario")
		void testPagoPagadoFuera() {
			pago.getPagado().remove(usuario1);

			assertFalse(usuario1.realizarPago(pago), "El array de pagado no incluye al usuario");
		}
		
		@Test
		@DisplayName("Realizar pago de un importe ya pagado")
		void testPagoPagadoPreviamente() {
			pago.getPagado().put(usuario1, true);

			assertFalse(usuario1.realizarPago(pago), "El usuario ya realizó el pago previamente");
		}
		
	}
	
	
	
	@Nested 
	@DisplayName("Prueba de aceptación del sprint 2 (se testean desde el reparto de los pagos a la realización de estos)")
	class realizarPago_repartirgastos{
		
		int notificacioneseva=0,notificacionesjuan=0;
		int pagadaseva=0,pagadasjaun=0;
		@Test
		@DisplayName("Realizar pago caso válido")
		void testAceptacion() {
			
			//generacion de los usuarios
			IUsuario eva=new Usuario(1, "evauser", "eva", "nombre@dominio.com", LocalDate.of(2000, Month.JANUARY, 1), "Nombr€", "ES0000000000000000000000");
			IUsuario luis=new Usuario(2, "luisuser", "luis", "nombre@dominio.com", LocalDate.of(2000, Month.JANUARY, 1), "Nombr€", "ES0000000000000000000000");
			IUsuario marta=new Usuario(3, "martauser", "marta", "nombre@dominio.com", LocalDate.of(2000, Month.JANUARY, 1), "Nombr€", "ES0000000000000000000000");
			IUsuario juan=new Usuario(4, "juanuser", "juan", "nombre@dominio.com", LocalDate.of(2000, Month.JANUARY, 1), "Nombr€", "ES0000000000000000000000");
			
			//se crea la lista a introducir en el grupo
			ArrayList<IUsuario> lista=new ArrayList<IUsuario>();
			lista.add(eva);
			lista.add(luis);
			lista.add(marta);
			lista.add(juan);
			IGrupo loscuatro= eva.crearGrupo(99, "LosCuatro", "grupo", lista);
			
			//se imputan los gastos al grupo
			eva.anadirGasto(loscuatro, 11.30);
			eva.anadirGasto(loscuatro, 23.15);
			eva.anadirGasto(loscuatro, 2.05);
			luis.anadirGasto(loscuatro, 12.0);
			luis.anadirGasto(loscuatro, 17.49);
			marta.anadirGasto(loscuatro, 20.22);
			juan.anadirGasto(loscuatro, 5.75);
			
			//se dividen los gastos del grupo
			eva.dividirGastos(loscuatro);
			
			
			
			
			String str = eva.getNotificaciones().get(0);
			String str2 = juan.getNotificaciones().get(0);
			String buscar = "Pago pendiente";
			int indice = 0;
			
			// Comprobamos cuántas veces pone "Pago pendiente" y "Se ha pagado" en la notificación de eva y juan
			while (indice != -1) {
				indice = str.indexOf("Pago pendiente", indice);
			    if (indice != -1) {
			    	notificacioneseva++;
			        indice += buscar.length();
			    }
			}
			indice = 0;
			while (indice != -1) {
				indice = str2.indexOf("Pago pendiente", indice);
			    if (indice != -1) {
			    	notificacionesjuan++;
			        indice += buscar.length();
			    }
			}
			
			eva.realizarPago(eva.getPagos().get(0));
			str = eva.getNotificaciones().get(0);
			str2 = juan.getNotificaciones().get(0);
			
			indice = 0;
			while (indice != -1) {
				indice = str.indexOf("Se ha pagado", indice);
			    if (indice != -1) {
			    	pagadaseva++;
			        indice += buscar.length();
			    }
			}
			indice = 0;
			while (indice != -1) {
				indice = str2.indexOf("Se ha pagado", indice);
			    if (indice != -1) {
			    	pagadasjaun++;
			        indice += buscar.length();
			    }
			}
			
			
			
			
			assertAll( 
					
					()->{assertTrue(notificacioneseva==3,"La cantidad de no pagadas de eva está mal");},
					()->{assertTrue(notificacionesjuan==3,"La cantidad no pagadas de juan está mal");},
					()->{assertTrue(pagadaseva==3,"La cantidad de pagadas de eva está mal");},
					()->{assertTrue(pagadasjaun==0,"La cantidad de pagadas de juan está mal");});
			
			

			
	}
	
	
	}
	
	
	
	
	
}
