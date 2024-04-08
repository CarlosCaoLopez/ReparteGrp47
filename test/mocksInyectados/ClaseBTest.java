package mocksInyectados;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

class ClaseBTest {
	AutoCloseable ac;
	// ClaseA que simulamos con Mock
	@Mock
	ClaseA ca;

	// ClaseB en la que se Injectará la ClaseA
	@InjectMocks
	ClaseB cb;

	@BeforeEach 
	void setUp() throws Exception {
		// Inyectamos en las clases anotadas sus clases simuladas
		ac=MockitoAnnotations.openMocks(this);
		
	}

	@AfterEach
	void tearDown() throws Exception {
		ac.close();
	}

	@Test
	void test() {
		// Arrange en Fixtures
		when(ca.func3(anyInt(), anyInt())).thenReturn(Integer.valueOf(5))
				.thenReturn(Integer.valueOf(5)).thenReturn(Integer.valueOf(5));

		int resultA = ca.func3(5, 6); 	// Invocación directa del Mock
		int resultB = cb.met1();		// Invocación del SUT

		assertEquals(5, resultA, "Falla la invocación directa del Mock");
		assertEquals(5, resultB, "Falla la invocación del SUT");

		Mockito.verify(ca, times(2)).func3(anyInt(), anyInt());	}

}
