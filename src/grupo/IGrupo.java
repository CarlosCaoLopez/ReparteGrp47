package grupo;

import usuario.IUsuario;

import java.util.ArrayList;

import gasto.IGasto;
import pago.IPago;

public interface IGrupo {
	
	public void anadirMiembro(IUsuario nuevoUsuario);
	public void eliminarMiembro(IUsuario usuario);
	public void modificarDescripcion(String descripcion);
	public void anadirGasto(IGasto gasto);
	//public void modificarGasto(IGasto gasto);
	public void dividirGastos();
	
	// Getters
	public int getId();
	public String getNombreGrupo();
	public String getDescripcion();
	public ArrayList<IGasto> getGastos();
	public ArrayList<IUsuario> getUsuarios();
	public ArrayList<IPago> getPagos();
	
	// Setters
	public void setGastos(ArrayList<IGasto> gastos);
	public void setUsuarios(ArrayList<IUsuario> usuarios);
	public void setPagos(ArrayList<IPago> pagos);
	
}
