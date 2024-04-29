package pago;

import java.util.ArrayList;
import java.util.HashMap;

import gasto.IGasto;
import grupo.IGrupo;
import usuario.IUsuario;

public interface IPago {
	
//	private int id;
//	private String servicio;
//	private Double cantidad;
//	private IUsuario acreedor;
//	private ArrayList<IUsuario> deudores;
	
	public void repartirGasto(ArrayList<IGasto> listagastos);
	
	
	//getters y setters
	public int getId();
	public void setId(int id);
	public Double getTotal();
	public void setTotal(Double total);
	public HashMap<IUsuario, Double> getCuentas();
	public void setCuentas(HashMap<IUsuario, Double> cuentas);
	public IGrupo getGrupoGasto();
	public void setGrupoGasto(IGrupo grupoGasto);
	
}
