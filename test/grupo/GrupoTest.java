package grupo;



import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

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
import org.junit.jupiter.params.provider.NullSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import gasto.Gasto;
import gasto.IGasto;
import pago.IPago;
import pago.Pago;
import usuario.IUsuario;
import usuario.Usuario;

class GrupoTest {
	//Fixtures
	Grupo grupo;
	// Creamos un mock de usuario
    IUsuario usuarioMock;    
    // Creamos una lista de usuarios que contiene nuestro mock
    ArrayList<IUsuario> usuariosMock;
    // Añadimos el usuario
    ArrayList<IUsuario> usuariosVacio = new ArrayList<>();
	
	
	@BeforeEach
	void setUp() throws Exception {
		usuarioMock = mock(IUsuario.class);
		usuariosMock = new ArrayList<>();
		usuariosMock.add(usuarioMock); // Añadimos el mock al conjunto de usuarios
	}

	@AfterEach
	void tearDown() throws Exception {
	}
	
	@Nested
	@DisplayName("Prueba de creación de grupos")
	class Constructores{
		
		@Test
		@DisplayName("Caso constructor válido")
		void testIdVálido() {
			grupo = new Grupo(4, "nombreGrupo", "descripcion", usuariosMock);
			
			assertAll( () ->{assertEquals(4, grupo.getId(), "Un id no ha sido correctamente establecido");},
					()->{assertEquals("nombreGrupo", grupo.getNombreGrupo(), "Nombre no establecido correctamente");},
					()->{assertEquals("descripcion", grupo.getDescripcion(), "Descripción no establecido correctamente");},
					()->{assertEquals(1, grupo.getUsuarios().size(), "Grupo no establecido correctamente");}
					
					); 
			
		}
		
		@ParameterizedTest
		@DisplayName("Verificación id positivo")
		@CsvSource({"-1", "0"})
		void testIdNoVálido(int id) {
			grupo = new Grupo(id, "nombre Grupo", "descripcion", usuariosMock);
			assertTrue(grupo.getId() <= 0, "El id es cero o negativo");
			
		}
		
		@ParameterizedTest
		@DisplayName("Verificación nombre vacío o nulo")
		@NullAndEmptySource
		void testNombreGrupoNoVálido(String nombreGrupo) {
			grupo = new Grupo(4, nombreGrupo, "descripcion", usuariosMock);
			assertNull(grupo.getNombreGrupo(), "Un nombre no ha sido validado");
		
		}
		
		@ParameterizedTest
		@DisplayName("Verificación descripción vacía o nula")
		@NullAndEmptySource
		void testDescripcionNoVálido(String descripcion) {
			grupo = new Grupo(4, "nombreGrupo", descripcion, usuariosMock);
			assertNull(grupo.getDescripcion(), "Una descripción no ha sido validada");
		
		}
		
		@ParameterizedTest
		@DisplayName("Verificación conjunto de usuarios nulo")
		@NullSource
		void testUsuariosNulo(ArrayList<IUsuario> usuarios) {
			grupo = new Grupo(4, "nombreGrupo", "descripcion", usuarios);
			assertNull(grupo.getUsuarios(), "El conjunto de usuario es nulo");
		}
		
		@Test
		@DisplayName("Verificación conjunto de usuarios vacío")
		void testUsuariosVacio() {
			grupo = new Grupo(4, "nombreGrupo", "descripcion", usuariosVacio);
			assertNull(grupo.getUsuarios(), "El conjunto de usuario es vacío");
		}
		
	}
	
	@Nested 
	@DisplayName("Prueba de añadir lideres al grupo")
	class anhadirLider{
		
		@Test 
		@DisplayName("Verificación de que el lider a añadir es no nulo")
		void testAnhadirLiderNoValido() {
			grupo = new Grupo(4, "nombreGrupo", "descripcion", usuariosMock);
			assertFalse(grupo.anhadirLider(null), "Usuario nulo introducido tomado como usuario validado");
		}
		
		@Test
		@DisplayName("Verificación de que el lider a añadir se encuentra en el grupo (caso inválido)")
		void testAnhadirLiderNoValido2() {
			IUsuario usuario2Mock = mock(IUsuario.class);
			grupo = new Grupo(4, "nombreGrupo", "descripcion", usuariosMock);
			assertFalse(grupo.anhadirLider(usuario2Mock), "Usuario fuera del grupo añadido como lider");
		}
		
		@Test
		@DisplayName("Verificacion de que el lider a añadir se encuentra en el grupo (caso válido)")
		void testAnhadirLiderValido() {
			grupo = new Grupo(4, "nombreGrupo", "descripcion", usuariosMock);
			assertTrue(grupo.anhadirLider(usuarioMock), "Usuario dentro del grupo NO añadido como lider");
		}
	}
	
	@Nested
	@DisplayName("Prueba de añadir miembros al grupo")
	class anadirMiembro{
		
		@Test
		@DisplayName("Verificación de que el usuario es no nulo (caso no válido)")
		void testAnadirMiembroNoValido(){
			grupo = new Grupo(4, "nombreGrupo", "descripcion", usuariosMock);
			assertFalse(grupo.anadirMiembro(null), "El usuario es nulo");
		}
		
		@Test
		@DisplayName("Verificación de que el usuario es no nulo (caso válido)")
		void testAnadirMiembroValido(){
			grupo = new Grupo(4, "nombreGrupo", "descripcion", usuariosMock);
			IUsuario usuario2Mock = mock(IUsuario.class);
			assertAll( ()->{assertTrue(grupo.anadirMiembro(usuario2Mock), "El usuario es correcto");},
					()->{assertTrue(grupo.getUsuarios().contains(usuario2Mock), "El usuario no se ha introducido en la lista");},
					()->{assertEquals(2, grupo.getUsuarios().size(), "El tamaño de la lista de usuarios es incorrecta");} );
		}
		
	}
	
	@Nested
	@DisplayName("Prueba de eliminar miembros del grupo")
	class eliminarMiembro{
		
		@Test
		@DisplayName("Verificación de que el usuario es no nulo (caso no válido)")
		void testEliminarMiembroNoValido(){
			grupo = new Grupo(4, "nombreGrupo", "descripcion", usuariosMock);
			assertFalse(grupo.eliminarMiembro(null), "El usuario es nulo");
		}
		
		@Test
		@DisplayName("Verificación de que el usuario es no nulo (caso válido)")
		void testEliminarMiembroValido(){
			grupo = new Grupo(4, "nombreGrupo", "descripcion", usuariosMock);
			assertAll( ()->{assertTrue(grupo.eliminarMiembro(usuarioMock), "El usuario es correcto");},
					()->{assertEquals(0, grupo.getUsuarios().size(), "El tamaño de la lista de usuarios es incorrecta");} );
		}
		
	}
	
	@Nested
	@DisplayName("Prueba de añadir gastos al grupo")
	class anadirGasto{
		
		@Test
		@DisplayName("Verificación de que el gasto es no nulo (caso no válido)")
		void testAnadirGastoNoValido(){
			grupo = new Grupo(4, "nombreGrupo", "descripcion", usuariosMock);
			assertFalse(grupo.anadirGasto(null), "El gasto es nulo");
		}
		
		@Test
		@DisplayName("Verificación de que el gasto es pagado por una persona del grupo (caso no válido)")
		void testAnadirGastoFuera(){
			grupo = new Grupo(4, "nombreGrupo", "descripcion", usuariosMock);
			IUsuario usuario2Mock = mock(IUsuario.class);
			IGasto gastoMock = mock(IGasto.class);
			when(gastoMock.getPagador()).thenReturn(usuario2Mock);
			assertFalse(grupo.anadirGasto(gastoMock), "El gasto pertenece a una persona que no está en el grupo");
		}
		
		@Test
		@DisplayName("Verificación de que el gasto es pagado por una persona del grupo (caso válido)")
		void testAnadirGastoDentro(){
			grupo = new Grupo(4, "nombreGrupo", "descripcion", usuariosMock);
			IGasto gastoMock = mock(IGasto.class);
			when(gastoMock.getPagador()).thenReturn(usuarioMock);
			assertTrue(grupo.anadirGasto(gastoMock), "El gasto pertenece a una persona que sí que está al grupo");
		}
		
	}
	
	@Nested 
	@DisplayName("Pruebas de dividir gastos desde un Grupo")
	class dividirGastos{
		
		IGasto gastoMock;
		IPago pagoMock;
		AutoCloseable acl;
		
		@Mock
		IPago pagoMock_;
		@InjectMocks
		IGrupo grupo_ = new Grupo(4, "nombreGrupo", "descripcion", usuariosMock);
		
		@BeforeEach
		void setUp() throws Exception {
			grupo = new Grupo(4, "nombreGrupo", "descripcion", usuariosMock);
			gastoMock = mock(Gasto.class);
			usuarioMock = mock(Usuario.class);
			pagoMock = mock(Pago.class);
			// Suponemos que si se llega a la parte más interior de las condiciones, se pasa la prueba 
			when(pagoMock.repartirGastos()).thenReturn(true);
						
			acl = MockitoAnnotations.openMocks(this);
					
		}

		@AfterEach
		void tearDown() throws Exception {
			acl.close();
		}
		
		@Test
		@DisplayName("Verificación de que el conjunto de usuarios del grupo es no nulo")
		void testUsuariosNulo() {
			// Como no se puede settear un valor nulo, hay que crear un grupo con todo a null e ir usando los setter
			grupo = new Grupo(0, "", "", null);
			grupo.setDescripcion("descripcion");
			grupo.setNombreGrupo("nombreGrupo");
			grupo.setGastos(new ArrayList<IGasto>(Arrays.asList(gastoMock)));
			assertFalse(grupo.dividirGastos(), "El conjunto de usuarios del grupo es nulo");
		}	
		
		@Test
		@DisplayName("Verificación de que el grupo debe incluir algún usuario")
		void testUsuariosVacio() {
			grupo.setUsuarios(new ArrayList<IUsuario>());
			grupo.setGastos(new ArrayList<IGasto>(Arrays.asList(gastoMock)));
			assertFalse(grupo.dividirGastos(), "El grupo no tiene integrantes");
		}
		
		@Test
		@DisplayName("Verificación de que el conjunto de gastos del grupo es no nulo")
		void testGastosNulo() {
			// Como no se puede settear un valor nulo, hay que crear un grupo con todo a null e ir usando los setter
			grupo = new Grupo(0, "", "", null);
			grupo.setDescripcion("descripcion");
			grupo.setNombreGrupo("nombreGrupo");
			grupo.setUsuarios(usuariosMock);
			assertFalse(grupo.dividirGastos(), "El conjunto de gastos del grupo es nulo");
		}	
		
		@Test
		@DisplayName("Verificación de que el grupo debe incluir algún gasto")
		void testGastosVacio() {
			grupo.setGastos(new ArrayList<IGasto>());
			assertFalse(grupo.dividirGastos(), "El conjunto de gastos está vacío");
		}
		
		// TODO: No funciona correctamente. Está usando la clase Pago, no sé cómo mockearla
		//       Quizás haya que indicar que no se puede hacer prueba de unidad, solo de integración
		@Test
		@DisplayName("Verificación del pago caso válido")
		void testValido() {
			when(pagoMock_.repartirGastos()).thenReturn(true);
			grupo_.setGastos(new ArrayList<IGasto>(Arrays.asList(gastoMock)));
			grupo_.setUsuarios(usuariosMock);
			assertTrue(grupo_.dividirGastos(), "El grupo es en realidad correcto");
			// when(usuarioMock.equals(any(Usuario.class))).thenReturn(true);
		}
		
	}

}
