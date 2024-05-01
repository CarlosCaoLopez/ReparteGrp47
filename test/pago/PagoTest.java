package pago;

import static org.mockito.Mockito.mock;

import java.util.ArrayList;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;

import grupo.Grupo;
import grupo.IGrupo;
import usuario.IUsuario;
import usuario.Usuario;

class PagoTest {
	
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
		IUsuario usuario = mock(Usuario.class);
		IGrupo grupo = mock(Grupo.class);
		
		ArrayList<IUsuario> usuarios = new ArrayList<>();
		usuarios.add(usuario);
		
	}

}
