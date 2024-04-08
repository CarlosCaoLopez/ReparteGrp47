package mocks;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class ClaseBTest {

	@Test
	void test() {
		ClaseA ca = mock(ClaseA.class);
		ClaseB cb = new ClaseB(ca); // SUT

		when(ca.func3(anyInt(), anyInt())).thenReturn(Integer.valueOf(5));

		int resultA = ca.func3(4, 3); 	// Invocación directa del Mock
		int resultB = cb.met1();		// Invocación del SUT

		assertEquals(5, resultA, "Falla la invocación directa del Mock");
		assertEquals(5, resultB, "Falla la invocación del SUT");

		Mockito.verify(ca, Mockito.times(2)).func3(anyInt(), anyInt());
	}

}
