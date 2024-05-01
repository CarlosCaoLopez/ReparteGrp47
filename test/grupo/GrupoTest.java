package grupo;



import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.mock;

import java.util.ArrayList;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.NullSource;

import usuario.IUsuario;

class GrupoTest {
	//Fixtures
	Grupo grupo;
	// Creamos un mock de usuario
    IUsuario usuarioMock = mock(IUsuario.class);    
    // Creamos una lista de usuarios que contiene nuestro mock
    ArrayList<IUsuario> usuariosMock = new ArrayList<>();
    // Añadimos el usuario
    ArrayList<IUsuario> usuarios = new ArrayList<>();
	
	
	@BeforeEach
	void setUp() throws Exception {
		usuariosMock.add(usuarioMock); // Añadimos el mock al conjunto de usuarios
		
	}

	@AfterEach
	void tearDown() throws Exception {
	}
	
	@Nested
	@DisplayName("Prueba de creación de grupos")
	class Constructores{
		
		@Test
		@DisplayName("Caso válido")
		void testIdNoVálido() {
			grupo = new Grupo(4, "nombreGrupo", "descripcion", usuariosMock);
			
			assertAll( () ->{assertEquals(4, grupo.getId(), "Un id no ha sido correctamente establecido");},
					()->{assertEquals("nombreGrupo", grupo.getNombreGrupo(), "Nombre no establecido correctamente");},
					()->{assertEquals("descripcion", grupo.getDescripcion(), "Descripción no establecido correctamente");},
					()->{assertEquals(1, grupo.getUsuarios().size(), "Grupo no establecido correctamente");}
					
					); 
			
		}
		
		@ParameterizedTest
		@DisplayName("Verificación id positivo")
		@CsvSource({"-1"})
		void testIdNoVálido(int id) {
			grupo = new Grupo(id, "nombre Grupo", "descripcion", usuariosMock);
			assertEquals(0, grupo.getId(), "Un id no  ha sido validado");
			
		}
		
		@ParameterizedTest
		@DisplayName("Verificación nombre vacío o nulo")
		@NullAndEmptySource
		void testNombreGrupoNoVálido(String nombreGrupo) {
			grupo = new Grupo(4, nombreGrupo, "descripcion", usuariosMock);
			assertNull(grupo.getNombreGrupo(), "Un nombre no ha sido validado");
		
		}
		
		@ParameterizedTest
		@DisplayName("Verificación descripcion vacío o nulo")
		@NullAndEmptySource
		void testDescripcionNoVálido(String descripcion) {
			grupo = new Grupo(4, "nombreGrupo", descripcion, usuariosMock);
			assertNull(grupo.getDescripcion(), "Una descripción no ha sido validada");
		
		}
		
		@ParameterizedTest
		@DisplayName("Verificación usuarios nulo")
		@NullSource
		void testUsuariosNoNulosVálido(ArrayList<IUsuario> usuarios) {
			grupo = new Grupo(4, "nombreGrupo", "descripcion", usuarios);
			assertNull(grupo.getUsuarios(), "Unos usuarios no han sido validados");
		
		}
		
		@Test
		@DisplayName("Verificación usuarios vacíos")
		void testUsuariosNoVálido() {
			grupo = new Grupo(4, "nombreGrupo", "descripcion", usuarios);
			assertEquals(0, grupo.getUsuarios().size(), "Unos usuarios no han sido validados");
		
		}
		
		
	}
	

}
