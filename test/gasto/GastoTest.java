package gasto;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.*;

import grupo.Grupo;
import grupo.IGrupo;
import usuario.IUsuario;
import usuario.Usuario;

import java.util.*;
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
			IGasto gasto2=new Gasto(10,10.1,grupoMock,pagandoMock,null);
			
			assertAll(
					()->{assertNotNull(gasto1.getPagador(),"Error, no se asignan las variables del constructor");},
					()->{assertNotNull(gasto2.getPagador(),"Error, no se asignan las variables del constructor");});

		}
		
		@Test
		@DisplayName("Chequeo con grupo nulo")
		void testgruponulo() {
			IUsuario pagandoMock = mock(Usuario.class);
			
			Gasto gasto1=new Gasto(10,10.1,null,pagandoMock);
			Gasto gasto2=new Gasto(10,10.1,null,pagandoMock,null);
			
			assertAll(
					()->{assertNull(gasto1.getPagador(),"Error, se asignan las variables del constructor, debería fallar");},
					()->{assertNull(gasto2.getPagador(),"Error, se asignan las variables del constructor, debería fallar");});

		}
		
		@Test
		@DisplayName("Chequeo con pagando nulo")
		void testpagadornulo() {
			IGrupo grupoMock =mock(Grupo.class);
			
			IGasto gasto1=new Gasto(10,10.1,grupoMock,null);
			IGasto gasto2=new Gasto(10,10.1,grupoMock,null,null);
			
			assertAll(
					()->{assertNull(gasto1.getGrupoGasto(),"Error, se asignan las variables del constructor, debería fallar");},
					()->{assertNull(gasto2.getGrupoGasto(),"Error, se asignan las variables del constructor, debería fallar");});

			
		}
		
		@Test
		@DisplayName("Chequeo con pagando no parte del grupo")
		void testpagadorfueragrupo() {
			IGrupo grupoMock =mock(Grupo.class);
			IUsuario pagandoMock = mock(Usuario.class);
			
			when(grupoMock.getUsuarios()).thenReturn(new ArrayList<IUsuario>());

			
			IGasto gasto1=new Gasto(10,10.1,grupoMock,pagandoMock);
			IGasto gasto2=new Gasto(10,10.1,grupoMock,pagandoMock,null);
			
			assertAll(
					()->{assertNull(gasto1.getPagador(),"Error, se asignan las variables del constructor, debería fallar");},
					()->{assertNull(gasto2.getPagador(),"Error, se asignan las variables del constructor, debería fallar");});

			
		}
		
		@Test
		@DisplayName("Chequeo con importe menoroigual a cero")
		void testimporteincorrecto() {
			IGrupo grupoMock =mock(Grupo.class);
			IUsuario pagandoMock = mock(Usuario.class);
			
			when(grupoMock.getUsuarios()).thenReturn(new ArrayList<IUsuario>(Arrays.asList(pagandoMock)));

			
			IGasto gasto1=new Gasto(10,0,grupoMock,pagandoMock);
			IGasto gasto2=new Gasto(10,0,grupoMock,pagandoMock,null);
			
			assertAll(
					()->{assertNull(gasto1.getPagador(),"Error, se asignan las variables del constructor, debería fallar");},
					()->{assertNull(gasto2.getPagador(),"Error, se asignan las variables del constructor, debería fallar");});

		}
		
		@Test
		@DisplayName("Chequeo con id menoroigual a cero")
		void testidincorrecto() {
			IGrupo grupoMock =mock(Grupo.class);
			IUsuario pagandoMock = mock(Usuario.class);
			
			when(grupoMock.getUsuarios()).thenReturn(new ArrayList<IUsuario>(Arrays.asList(pagandoMock)));

			Gasto gasto1=new Gasto(0,0.1,grupoMock,pagandoMock);
			Gasto gasto2=new Gasto(0,0.1,grupoMock,pagandoMock,null);
			
			assertAll(
					()->{assertNull(gasto1.getPagador(),"Error, se asignan las variables del constructor, debería fallar");},
					()->{assertNull(gasto2.getPagador(),"Error, se asignan las variables del constructor, debería fallar");});

		}
		
		
	}
	
	
	
    @Test
    @DisplayName("Testeo de registrargasto")
    void testRegistrarGasto() {
        // Creamos un mock de la interfaz IGrupo
        IGrupo grupoMock = mock(IGrupo.class);
        // Creamos un mock de la interfaz IUsuario
        IUsuario usuarioMock = mock(IUsuario.class);

        // Creamos una instancia de la clase Gasto
        IGasto gasto = new Gasto(1, 100.0, grupoMock, usuarioMock, "Negocio");

        // Llamamos al método registrarGasto con el mock de grupo como argumento
        gasto.registrarGasto(grupoMock);

        // Verificamos que el método anadirGasto del grupoMock haya sido llamado exactamente una vez
        verify(grupoMock, times(1)).anadirGasto(gasto);
    }

}
