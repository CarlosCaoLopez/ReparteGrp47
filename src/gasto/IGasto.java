package gasto;

import grupo.IGrupo;

public interface IGasto {
//	private int id;
//	private double cantidad;
//	private IGrupo grupoGasto;
//	private IUsuario pagador;
	
	public void asignarGrupo(IGasto gasto);
	public void modificarGasto(double nuevaCantidad);
	public IGasto registrarGasto(IGrupo grupo, double cantidad);
}
