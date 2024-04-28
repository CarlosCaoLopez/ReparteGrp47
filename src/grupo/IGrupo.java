package grupo;

import usuario.IUsuario;
import gasto.IGasto;
import pago.IPago;

public interface IGrupo {
	
	public void anadirMiembro(IUsuario nuevoUsuario);
	public void eliminarMiembro(IUsuario usuario);
	public void anadirGasto(IGasto gasto);
	public void modificarGasto(IGasto gasto);
	public void anadirPago(IPago pago);
	public void modificarDescripcion(String descripcion);
}
