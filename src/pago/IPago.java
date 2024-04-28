package pago;

import java.util.ArrayList;
import grupo.IGrupo;
import usuario.IUsuario;

public interface IPago {
	
//	private int id;
//	private String servicio;
//	private Double cantidad;
//	private IUsuario acreedor;
//	private ArrayList<IUsuario> deudores;
	
	public void asignarGrupo(IGrupo grupo);
	public void asignarDeudores(ArrayList<IUsuario> nuevosdeudores);
	
}
