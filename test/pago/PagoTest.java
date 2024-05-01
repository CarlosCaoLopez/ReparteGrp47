package pago;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;

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
		
		@Test
		@DisplayName("Verificación con grupo no nulo y vacío")
		void testGrupoNoValido() {
			IGrupo grupoMock = mock(Grupo.class);
			
			pago1 = new Pago(1, null);
			pago2 = new Pago(2, grupoMock);
			
			assertAll( ()->{assertNull(pago1.getGrupoGasto(), "Se crea un pago con un grupo nulo");},
					()->{assertNull(pago1.getGrupoGasto(), "Se crea un pago con un grupo vacío");}); 
			
		}
		
		@Test
		@DisplayName("Verificación con grupo sin gastos")
		void testGrupoSinGastos() {
			IUsuario usuarioMock = mock(Usuario.class);
			IGrupo grupoMock = mock(Grupo.class);

			// Añadimos el mock usuario al mock grupo para que el grupo no esté vacío
			grupoMock.anadirMiembro(usuarioMock);
			pago1 = new Pago(1, grupoMock);
			
			assertAll( ()->{assertNull(pago1.getGrupoGasto(), "Se crea un pago sin gastos");},
				    ()->{verify(grupoMock, times(1)).anadirMiembro(usuarioMock);}); 
		}
		
		@Test
		@DisplayName("Verificación con grupo sin personas")
		void testGrupoSinPersonas() {
			IGasto gastoMock = mock(Gasto.class);
			IGrupo grupoMock = mock(Grupo.class);

			// Añadimos el mock gasto al mock grupo para que el grupo tenga gastos
			grupoMock.anadirGasto(gastoMock);
			pago1 = new Pago(1, grupoMock);
			
			assertAll( ()->{assertNull(pago1.getGrupoGasto(), "Se crea un pago sin personas");},
				    ()->{verify(grupoMock, times(1)).anadirGasto(gastoMock);}); 
		}
		
		
		// TODO no funciona este test
		@Test
		@DisplayName("Verificación de grupo con personas y gastos")
		void testGrupoValido() {
			IGasto gastoMock = mock(Gasto.class);
			IUsuario usuarioMock = mock(Usuario.class);
			IGrupo grupoMock = mock(Grupo.class);

			// Añadimos el mock gasto al mock grupo para que el grupo tenga gastos
			//grupoMock.anadirGasto(gastoMock);
			// Posible alternativa
			ArrayList<IGasto> gastos = new ArrayList<>();
			gastos.add(gastoMock);
			grupoMock.setGastos(gastos);
			// Añadimos el mock usuario al mock grupo para que el grupo no esté vacío
			grupoMock.anadirMiembro(usuarioMock);
			pago1 = new Pago(1, grupoMock);
			
			assertAll( 
					()->{assertNotNull(grupoMock.getGastos(), "Se crea un pago sin gastos");},
					()->{assertNotNull(grupoMock.getUsuarios(), "Se crea un pago sin personas");},
					()->{assertFalse(grupoMock.getGastos().isEmpty(), "Se crea un pago sin gastos");},
					()->{assertFalse(grupoMock.getUsuarios().isEmpty(), "Se crea un pago sin personas");},
					()->{assertNotNull(pago1.getGrupoGasto(), "Pago válido no genera pago");},
					()->{verify(grupoMock, times(1)).anadirMiembro(usuarioMock);},
				    ()->{verify(grupoMock, times(1)).anadirGasto(gastoMock);}); 
		}
		
		
		
		
	}

}
