package usuario;

import java.util.ArrayList;
import grupo.IGrupo;
import pago.IPago;
import gasto.IGasto;

public interface IUsuario {	
	
	public IGrupo crearGrupo(int id, String nombreGrupo, String descripcion, ArrayList<IUsuario> lideres);
	public void gestionarGrupo(IGrupo grupo, String descripcion, ArrayList<IUsuario> nuevosUsuarios);
	public void notificar(String notificacion);
	public IPago realizarPago(IGasto gasto); 
}
