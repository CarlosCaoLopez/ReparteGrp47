package ficheros;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class MiFileTest {

	@Test
	void testIgual() {
		// Arrange
		MiFile f1 = new MiFile(5);
		MiFile f2 = new MiFile();
		
		// Act
		f2.setRaw(f1.getRaw());
		
		// Assert
		assertEquals(f1, f2, "Son ficheros distintos");	
	}
	
	@Test
	void testMismo() {
		// Arrange
		MiFile f1 = new MiFile(5);
		MiFile f2;
		
		// Act
		f2=f1;
		
		// Assert
		assertSame(f1, f2, "Son distintos objetos");	
	}
	

}
