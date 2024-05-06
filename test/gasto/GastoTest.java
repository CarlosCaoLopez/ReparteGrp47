package gasto;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
	
	
	// TODO Habría que comprobar que el gasto se ha creado correctamente, porque el grupo al que está asociado
	// no contiene al usuario (el grupo está vacío), y diría que el gasto no se está creando correctamente
    @Test
    @DisplayName("Testeo de registrar gasto")
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
