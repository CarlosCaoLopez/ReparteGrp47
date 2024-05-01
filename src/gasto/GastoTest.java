package gasto;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import grupo.IGrupo;
import usuario.IUsuario;

class GastoTest {
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

    @Test
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
