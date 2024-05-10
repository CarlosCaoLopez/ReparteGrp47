package grupo;

import usuario.IUsuario;

import java.util.ArrayList;

import gasto.IGasto;
import pago.IPago;

public interface IGrupo {
	
	public boolean anadirMiembro(IUsuario nuevoUsuario);
	public boolean eliminarMiembro(IUsuario usuario);
	public boolean anadirGasto(IGasto gasto);
	public boolean dividirGastos();
	public boolean anhadirLider(IUsuario usuario);
	
	// Getters
	public int getId();
	public String getNombreGrupo();
	public String getDescripcion();
	public ArrayList<IGasto> getGastos();
	public ArrayList<IUsuario> getUsuarios();
	public ArrayList<IPago> getPagos();
	public ArrayList<IUsuario> getLideres();
	
	// Setters
	public void setNombreGrupo(String nombreGrupo);
	public void setDescripcion(String descripcion);
	public void setGastos(ArrayList<IGasto> gastos);
	public void setUsuarios(ArrayList<IUsuario> usuarios);
	public void setPagos(ArrayList<IPago> pagos);
	
}
