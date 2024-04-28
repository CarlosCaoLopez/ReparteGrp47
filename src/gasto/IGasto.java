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
	public void modificarGasto(double nuevacantidad);
	public void modificarServicio(String nuevoservicio);
	public void modificarPagador(IUsuario nuevopagador);
	public IGasto registrarGasto(IGrupo grupo);
}
