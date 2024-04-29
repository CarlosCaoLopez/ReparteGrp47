package grupo;

import usuario.IUsuario;

import java.util.ArrayList;

import gasto.IGasto;
import pago.IPago;

public interface IGrupo {
	
	public void anadirMiembro(IUsuario nuevoUsuario);
	public void eliminarMiembro(IUsuario usuario);
	public void anadirGasto(IGasto gasto);
	public void modificarGasto(IGasto gasto);
	public void dividirGasto();
	public void modificarDescripcion(String descripcion);
	public ArrayList<IUsuario> getUsuarios();
}
