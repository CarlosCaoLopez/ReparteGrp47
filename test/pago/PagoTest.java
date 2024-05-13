package pago;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.clearAllCaches;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.mockito.internal.util.reflection.FieldInitializer;

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

import gasto.Gasto;
import gasto.IGasto;
import grupo.Grupo;
import grupo.IGrupo;
import usuario.IUsuario;
import usuario.Usuario;

class PagoTest {
	
	IPago pago1, pago2, pago3;
	
	/*
	 * AutoCloseable acl; // Clases simuladas
	 * 
	 * @Mock IGasto gasto;
	 * 
	 * @Mock IUsuario usuario;
	 * 
	 * @Mock IGrupo grupo;
	 * 
	 * @InjectMocks IPago pago;
	 */

	@BeforeEach
	void setUp() throws Exception {
		//acl = MockitoAnnotations.openMocks(this);
	}

	@AfterEach
	void tearDown() throws Exception {
		//acl.close();
	}

	@Nested
	@DisplayName("Pruebas de creación de pagos")
	class Constructores{
		IGrupo grupoMock;
		IUsuario usuarioMock;
		IGasto gastoMock;
		
		@BeforeEach
		void setUp() throws Exception {
			grupoMock = mock(Grupo.class);	
			usuarioMock = mock(Usuario.class);
			gastoMock = mock(Gasto.class);
		}
		
		@Test
		@DisplayName("Verificación id válido")
		void testIdNoValido() {
			when(grupoMock.getUsuarios()).thenReturn(new ArrayList<IUsuario>(Arrays.asList(usuarioMock)));
			when(grupoMock.getGastos()).thenReturn(new ArrayList<IGasto>(Arrays.asList(gastoMock)));
			pago1 = new Pago(0, grupoMock);
			
			assertTrue(pago1.getId()<=0, "El id no es válido");
			
		}
		
		@Test
		@DisplayName("Verificación grupo no nulo")
		void testGrupoNulo() {			
			pago1 = new Pago(1, null);
			
			assertNull(pago1.getGrupoGasto(), "Se crea un pago con un grupo nulo");			
		}
		
		@Test
		@DisplayName("Verificación grupo con el conjunto de usuario no nulo")
		void testGrupoUsuariosNulo() {	
			when(grupoMock.getUsuarios()).thenReturn(null);
			when(grupoMock.getGastos()).thenReturn(new ArrayList<IGasto>(Arrays.asList(gastoMock)));
			pago1 = new Pago(1, grupoMock);
			
			assertNull(pago1.getGrupoGasto(), "Se crea un pago con un grupo con un conjunto de usuarios nulo");			
		}
		
		@Test
		@DisplayName("Verificación grupo con el conjunto de usuario no vacío")
		void testGrupoUsuariosVacio() {	
			when(grupoMock.getUsuarios()).thenReturn(new ArrayList<IUsuario>());
			when(grupoMock.getGastos()).thenReturn(new ArrayList<IGasto>(Arrays.asList(gastoMock)));
			pago1 = new Pago(1, grupoMock);
			
			assertNull(pago1.getGrupoGasto(), "Se crea un pago con un grupo con un conjunto de usuarios vacío");			
		}
		
		@Test
		@DisplayName("Verificación grupo con el conjunto de gastos no nulo")
		void testGrupoGastosNulo() {	
			when(grupoMock.getUsuarios()).thenReturn(new ArrayList<IUsuario>(Arrays.asList(usuarioMock)));
			when(grupoMock.getGastos()).thenReturn(null);
			pago1 = new Pago(1, grupoMock);
			
			assertNull(pago1.getCuotas(), "Se crea un pago con un grupo con un conjunto de gastos nulo");			
		}
		
		@Test
		@DisplayName("Verificación grupo con el conjunto de gastos no vacío")
		void testGrupoGastosVacio() {	
			when(grupoMock.getUsuarios()).thenReturn(new ArrayList<IUsuario>(Arrays.asList(usuarioMock)));
			when(grupoMock.getGastos()).thenReturn(new ArrayList<IGasto>());
			pago1 = new Pago(1, grupoMock);
			
			assertNull(pago1.getCuotas(), "Se crea un pago con un grupo con un conjunto de gastos vacío");			
		}
		
	}
		
	@Nested
	@DisplayName("Pruebas de integración y sistema de repartir gastos")
	class repartirGastos{
		
		IPago pago;
		IUsuario usuario1, usuario2;
		IGrupo grupo;
		IGasto gasto;
		
		@BeforeEach
		void setUp() throws Exception {
			usuario1 = new Usuario(1, "user1", "user1", "nombre@dominio.com", LocalDate.of(2000, Month.JANUARY, 1), "Nombr€", "ES0000000000000000000000");
			usuario2 = new Usuario(1, "user2", "user2", "nombre@dominio.com", LocalDate.of(2000, Month.JANUARY, 1), "Nombr€", "ES0000000000000000000000");
			grupo = new Grupo(1, "nombre", "descripcion", new ArrayList<IUsuario>(Arrays.asList(usuario1, usuario2)));
			gasto = new Gasto(1, 10.0, grupo, usuario2);
			grupo.setGastos(new ArrayList<IGasto>(Arrays.asList(gasto)));
			
			/*
			pago.setGastos(grupo.getGastos());
			pago.setCuotas(new HashMap<>());
			pago.getCuotas().put(usuario1, new HashMap<>());
			pago.getCuotas().put(usuario2, new HashMap<>());
			pago.getCuotas().get(usuario1).put(usuario2, 10.0);
			pago.getCuotas().get(usuario2).put(usuario1, 0.0);
			pago.setPagado(new HashMap<>());
			pago.getPagado().put(usuario1, false);
			pago.getPagado().put(usuario2, true);
			 */
		}
		
		@Test
		@DisplayName("Verificación con grupo de gasto nulo")
		void testGrupoNulo(){
			// Creamos el pago incorrectamente para que el grupo sea nulo
			pago = new Pago(0, null);
			
			assertFalse(pago.repartirGastos(), "El grupo de gasto es nulo");
		}
		
		@Test
		@DisplayName("Verificación con grupo de gasto con usuarios nulo")
		void testGrupoUsuariosNulo(){
			pago = new Pago(1, grupo);
			
			// Creamos el grupo incorrectamente para que el conjunto de usuarios sea nulo
			grupo = new Grupo(0, null, null, null);
			grupo.setGastos(new ArrayList<IGasto>(Arrays.asList(gasto)));
			pago.setGrupoGasto(grupo);

			assertFalse(pago.repartirGastos(), "El grupo de gasto tiene un conjunto de usuarios nulo");
		}
		
		@Test
		@DisplayName("Verificación con grupo de gasto con usuarios vacío")
		void testGrupoUsuariosVacio(){
			pago = new Pago(1, grupo);
			grupo.getUsuarios().clear();

			assertFalse(pago.repartirGastos(), "El grupo de gasto tiene un conjunto de usuarios vacío");
		}
		
		@Test
		@DisplayName("Verificación con grupo de gasto con gastos nulo")
		void testGrupoGastosNulo(){
			pago = new Pago(1, grupo);
			
			// Creamos el grupo incorrectamente para que el conjunto de gastos sea nulo
			grupo = new Grupo(0, null, null, null);
			grupo.setUsuarios(new ArrayList<IUsuario>(Arrays.asList(usuario1, usuario2)));
			pago.setGrupoGasto(grupo);

			assertFalse(pago.repartirGastos(), "El grupo de gasto tiene un conjunto de gastos nulo");
		}
		
		@Test
		@DisplayName("Verificación con grupo de gasto con gastos vacío")
		void testGrupoGastosVacio(){
			pago = new Pago(1, grupo);
			grupo.getGastos().clear();

			assertFalse(pago.repartirGastos(), "El grupo de gasto tiene un conjunto de gastos vacío");
		}
		
		
		
		
		//if(grupoGasto!=null && grupoGasto.getUsuarios()!=null && grupoGasto.getGastos()!=null) {
		//if(!grupoGasto.getUsuarios().isEmpty() && !grupoGasto.getGastos().isEmpty()) {
		
		
		
		
		//la función debe tomar los pagos de un usuario, obtener de cada pago lo que debe dar y hacer un sumatorio
		Double sumarvalores(IUsuario user) {
			Double resul=0.0;
			
			for (IPago elm : user.getPagos()) {
				for(Double num : elm.getCuotas().get(user).values()) {
					resul+=num;
				}
			}
			
			return resul;
		}
		
		//la función chequea si para un sumatorio de pagos dado es similar a uno calculado a mano
		boolean enrango(IUsuario user, double cantidad) {
			Double sumatorio_guardado=sumarvalores(user);
			return (sumatorio_guardado <= cantidad+0.01 && sumatorio_guardado >= cantidad-0.01);
		}
			
			
		@Test
		@DisplayName("PRUEBA DE ACEPTACIÓN DEL SPRINT 1:"
				+ "\tVerificación de la funcion repartirGastos con inputs válidos (prueba de sistema)")
		void testcorrecto() {
			
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
			
			
			assertAll( 
					
					()->{assertTrue(enrango(eva,13.86),"La cantidad de eva está mal");},
					()->{assertTrue(enrango(luis,15.62),"La cantidad de luis está mal");},
					()->{assertTrue(enrango(marta,17.94),"La cantidad de marta está mal");},
					()->{assertTrue(enrango(juan,21.56),"La cantidad de juan está mal");},
					()->{assertFalse(luis.getNotificaciones().isEmpty(), "No se notifica a luis");},
					()->{assertFalse(marta.getNotificaciones().isEmpty(), "No se notifica a marta");},
					()->{assertFalse(juan.getNotificaciones().isEmpty(), "No se notifica a juan");},
					()->{assertFalse(eva.getNotificaciones().isEmpty(), "No se notifica a eva");});
			
		}
		
		
		
		
	}	
		
}


