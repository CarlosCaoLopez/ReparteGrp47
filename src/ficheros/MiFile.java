// Hola
package ficheros;

public class MiFile {
	int raw;

	public MiFile() {
		super();
		raw=0;
	}

	public MiFile(int raw) {
		super();
		this.raw = raw;
	}

	public int getRaw() {
		return raw;
	}

	public void setRaw(int raw) {
		this.raw = raw;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + raw;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MiFile other = (MiFile) obj;
		if (raw != other.raw)
			return false;
		return true;
	}
	
	
	

}
