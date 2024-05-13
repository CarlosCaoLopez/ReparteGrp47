package gasto;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
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
import org.junit.jupiter.params.provider.NullAndEmptySource;

import grupo.Grupo;
import grupo.IGrupo;
import usuario.IUsuario;
import usuario.Usuario;
class GastoTest {

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}
	
	
	
	@Nested
	@DisplayName("Pruebas de constructores")
	class Constructores{
		
		@Test
		@DisplayName("Chequeo con entrada valida")
		void testEntradaValida() {
			IGrupo grupoMock =mock(Grupo.class);
			IUsuario pagandoMock = mock(Usuario.class);
			
			when(grupoMock.getUsuarios()).thenReturn(new ArrayList<IUsuario>(Arrays.asList(pagandoMock)));

			IGasto gasto1=new Gasto(10,10.1,grupoMock,pagandoMock);
			IGasto gasto2=new Gasto(10,10.1,grupoMock,pagandoMock,"Negocio");
			
			assertAll(
					()->{assertNotNull(gasto1.getPagador(),"Error, no se asignan las variables del constructor");},
					()->{assertNotNull(gasto2.getPagador(),"Error, no se asignan las variables del constructor");});

		}
		
		@Test
		@DisplayName("Chequeo con grupo nulo")
		void testgruponulo() {
			IUsuario pagandoMock = mock(Usuario.class);
			
			Gasto gasto1=new Gasto(10,10.1,null,pagandoMock);
			Gasto gasto2=new Gasto(10,10.1,null,pagandoMock, "Negocio");
			
			assertAll(
					()->{assertNull(gasto1.getPagador(),"Error, el grupo es nulo");},
					()->{assertNull(gasto2.getPagador(),"Error, el grupo es nulo");});

		}
		
		@Test
		@DisplayName("Chequeo con pagando nulo")
		void testpagadornulo() {
			IGrupo grupoMock =mock(Grupo.class);
			
			IGasto gasto1=new Gasto(10,10.1,grupoMock,null);
			IGasto gasto2=new Gasto(10,10.1,grupoMock,null, "Negocio");
			
			assertAll(
					()->{assertNull(gasto1.getGrupoGasto(),"Error, el usuario que paga es nulo");},
					()->{assertNull(gasto2.getGrupoGasto(),"Error, el usuario que paga es nulo");});

			
		}
		
		@Test
		@DisplayName("Chequeo con pagando no parte del grupo")
		void testpagadorfueragrupo() {
			IGrupo grupoMock =mock(Grupo.class);
			IUsuario pagandoMock = mock(Usuario.class);
			
			when(grupoMock.getUsuarios()).thenReturn(new ArrayList<IUsuario>());

			
			IGasto gasto1=new Gasto(10,10.1,grupoMock,pagandoMock);
			IGasto gasto2=new Gasto(10,10.1,grupoMock,pagandoMock,"Negocio");
			
			assertAll(
					()->{assertNull(gasto1.getPagador(),"Error, el usuario que paga no forma parte del grupo");},
					()->{assertNull(gasto2.getPagador(),"Error, el usuario que paga no forma parte del grupo");});

			
		}
		
		@Test
		@DisplayName("Chequeo con importe menor o igual a cero")
		void testimporteincorrecto() {
			IGrupo grupoMock =mock(Grupo.class);
			IUsuario pagandoMock = mock(Usuario.class);
			
			when(grupoMock.getUsuarios()).thenReturn(new ArrayList<IUsuario>(Arrays.asList(pagandoMock)));

			
			IGasto gasto1=new Gasto(10,0,grupoMock,pagandoMock);
			IGasto gasto2=new Gasto(10,0,grupoMock,pagandoMock,null);
			
			assertAll(
					()->{assertNull(gasto1.getPagador(),"Error, el importe es menor o igual que cero");},
					()->{assertNull(gasto2.getPagador(),"Error, el importe es menor o igual que cero");});

		}
		
		@Test
		@DisplayName("Chequeo con id menor o igual a cero")
		void testidincorrecto() {
			IGrupo grupoMock =mock(Grupo.class);
			IUsuario pagandoMock = mock(Usuario.class);
			
			when(grupoMock.getUsuarios()).thenReturn(new ArrayList<IUsuario>(Arrays.asList(pagandoMock)));

			Gasto gasto1=new Gasto(0,0.1,grupoMock,pagandoMock);
			Gasto gasto2=new Gasto(0,0.1,grupoMock,pagandoMock,"Negocio");
			
			assertAll(
					()->{assertNull(gasto1.getPagador(),"Error, el id es menor o igual que cero");},
					()->{assertNull(gasto2.getPagador(),"Error, el id es menor o igual que cero");});

		}
		
		@ParameterizedTest
		@NullAndEmptySource
		@DisplayName("Chequeo con negocio nulo o vacío")
		void testNegocio(String negocio) {
			IGrupo grupoMock = mock(Grupo.class);
			IUsuario pagandoMock = mock(Usuario.class);
			
			when(grupoMock.getUsuarios()).thenReturn(new ArrayList<IUsuario>(Arrays.asList(pagandoMock)));

			Gasto gasto=new Gasto(10,0.1,grupoMock,pagandoMock, negocio);
			
			assertNull(gasto.getPagador(),"Error, el negocio es nulo o vacío");

		}
		
		
	}
	
	
	//TODO unidad no terminadas
	@Nested
	@DisplayName("Pruebas de unidad de registrarGasto")
	class UnidadRegistrarGasto{
	
	
		IUsuario usermock1;
		IUsuario usermock2;
		IGrupo grupomock;
		IGasto gasto;
		
		@BeforeEach
		void setUp() throws Exception {
			
			//generamos dos usuarios de prueba y la lista que los contiene
			
			usermock1= mock(IUsuario.class);
			usermock2= mock(IUsuario.class);
			grupomock= mock(IGrupo.class);
			
			//generamos un gasto válido con el user1 como pagador
			gasto = new Gasto(99, 99.9, grupomock, usermock1);

		}
		
    
		  @Test
		    @DisplayName("Testeo válido de registrar gasto")
		    void testRegistrarGasto_valido() {
		       
		    	assertTrue(gasto.registrarGasto(grupomock).equals(gasto), "Error, no se nos devuelve this desde el método interno, de haber funcionado tendríamos que recibirlo");
			
		    }
		  
		  @Test
		    @DisplayName("Testeo no válido de registrar gasto (introducimos null)")
		    void testRegistrarGasto_null() {
		        
		    	assertTrue(gasto.registrarGasto(null)==null, "Error, deberíamos recibir null en caso de que la condición interna se rechace. No es así");
			
		    }
		  
		  //el resto de chequeos se realizan en grupo.anadirgasto(), esta función revisa si se cumple o no la condición de null para su ejecución.
		
	}
	
	
	@Nested
	@DisplayName("Pruebas de integración de registrarGasto")
	class IntegraciónRegistrarGasto{
	
	
		IUsuario user1;
		IUsuario user2;
		IGrupo grupo;
		IGasto gasto;
		
		@BeforeEach
		void setUp() throws Exception {
			
			//generamos dos usuarios de prueba y la lista que los contiene
			user1 = new Usuario(1, "user1", "nombreReal", "nombre@dominio.com", LocalDate.of(2000, Month.JANUARY, 1), "Nombr€", "ES0000000000000000000000");
			user2 = new Usuario(99, "user2", "nombreReal", "nombre@dominio.com", LocalDate.of(2000, Month.JANUARY, 1), "Nombr€", "ES0000000000000000000000");
			ArrayList <IUsuario> listausers=new ArrayList<IUsuario>();
			listausers.add(user1);
			listausers.add(user2);
			
			//generamos un grupo válido
			grupo = new Grupo(4, "grupo1", "descripcion", listausers);
			
			//generamos un gasto válido con el user1 como pagador
			gasto = new Gasto(99, 99.9, grupo, user1);

			
			
		}
		
		
	    @Test
	    @DisplayName("Testeo válido de registrar gasto")
	    void testRegistrarGasto() {
	        
	    	gasto.registrarGasto(grupo);
	    	
	    	assertAll( ()->{assertTrue(grupo.getGastos().contains(gasto), "Error, el grupo de gasto no contiene el gasto generado");},
					()->{assertTrue(grupo.getUsuarios().contains(gasto.getPagador()), "Error, el grupo no contiene al usuario pagador");} 
					);
		
	    }
	
	    @Test
	    @DisplayName("Testeo no válido de registrar gasto (grupo nulo)")
	    void testRegistrarGasto_gruponulo() {
	        
	    	gasto.registrarGasto(null);
	    	
	    	assertAll( ()->{assertTrue(gasto.registrarGasto(null)==null, "Error, el grupo de gasto contiene el gasto generado cuando no debería");});
		
	    }
	    
	    
	    @Test
	    @DisplayName("Testeo no válido de registrar gasto (grupo existe y no contiene al pagador)")
	    void testRegistrarGasto_sinpagador() {
	        
	    	ArrayList<IUsuario> listausers2=new ArrayList<IUsuario>();
	    	listausers2.add(user2);
	    	IGrupo grupo2 = new Grupo(99, "grupo2", "descripcion", listausers2);
	    	
	    	gasto.registrarGasto(grupo2);
	    	
	    	assertAll( ()->{assertFalse(grupo2.getGastos().contains(gasto), "Error, el grupo de gasto contiene el gasto generado cuando no debería");},
					()->{assertFalse(grupo2.getUsuarios().contains(gasto.getPagador()), "Error, el pagador está en el grupo");} 
					);
		
	    }
    
	   
    
	}

}
