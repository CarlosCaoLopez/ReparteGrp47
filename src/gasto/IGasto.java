package gasto;

import grupo.IGrupo;
import usuario.IUsuario;

public interface IGasto {
//	private int id;
//	private double cantidad;
//	private String servicio;
//	private IGrupo grupoGasto;
//	private IUsuario pagador;
	
	//public void asignarGrupo(IGasto gasto);
	public boolean registrarGasto(IGrupo grupo);
	
	//getters
	public int getId();
	public double getCantidad();
	public IGrupo getGrupoGasto();
	public IUsuario getPagador();
	public String getServicio();
}
