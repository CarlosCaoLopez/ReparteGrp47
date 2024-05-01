package pago;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;

import java.util.ArrayList;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;

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
		
		
		
		@ParameterizedTest
		@DisplayName("Verificación de grupo no nulo y no vacío")
		void testGrupoValido() {
			IGrupo grupo = mock(Grupo.class);
			
			pago1 = new Pago(1, null);
			pago2 = new Pago(2, grupo);
			
			assertAll( ()->{assertNull(pago1.getGrupoGasto(), "Se crea un gasto con un grupo nulo");},
					()->{assertNull(pago1.getGrupoGasto(), "Se crea un gasto con un grupo vacío");}); 
			
		}
		
		@ParameterizedTest
		@DisplayName("Verificación de grupo con algún usuario y algún gasto")
		void testGrupoConUsuariosYGastos() {
			IUsuario usuario = mock(Usuario.class);
			IGrupo grupo = mock(Grupo.class);
			
			
			
			pago1 = new Pago(1, null);
			pago2 = new Pago(2, grupo);
			
			ArrayList<IUsuario> usuarios = new ArrayList<>();
			usuarios.add(usuario);
			
			assertAll( ()->{assertNull(pago1.getGrupoGasto(), "Se crea un gasto con un grupo nulo");},
					()->{assertNull(pago1.getGrupoGasto(), "Se crea un gasto con un grupo vacío");}); 
			
		}
		
		
		
		
	}

}
