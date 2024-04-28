package grupo;

import usuario.IUsuario;
import gasto.IGasto;
import pago.IPago;

public interface IGrupo {

//	private int id;
//	private String nombreGrupo;
//	private String descripcion;
//	private ArrayList<IGasto> gastos;
//	private ArrayList<IUsuario> usuarios;
//	private ArrayList<IUsuario> lideres;
	
	public void anadirMiembro(IUsuario nuevoUsuario);
	public void eliminarMiembro(IUsuario usuario);
	public void anadirGasto(IGasto gasto);
	public void modificarGasto(IGasto gasto);
	public void anadirPago(IPago pago);
}
