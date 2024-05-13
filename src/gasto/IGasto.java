package gasto;

import grupo.IGrupo;
import usuario.IUsuario;

public interface IGasto {

	public boolean registrarGasto(IGrupo grupo);
	
	//getters
	public int getId();
	public double getCantidad();
	public IGrupo getGrupoGasto();
	public IUsuario getPagador();
	public String getServicio();
}
