package usuario;


import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import gasto.Gasto;
import gasto.IGasto;
import grupo.Grupo;
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
	@DisplayName("Pruebas de creación de grupo desde un Usuario")
	class crearGrupo{

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
			assertFalse(usuario3.gestionarGrupo(null, "nombre", "descripcion", usuarios), "Usuario no forma parte del grupo");
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
			assertFalse(usuario3.anadirGasto(null, 10.0), "Usuario no forma parte del grupo");
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
}
