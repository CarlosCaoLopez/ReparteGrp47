package pago;

import java.util.ArrayList;
import java.util.HashMap;

import gasto.IGasto;
import grupo.IGrupo;
import usuario.IUsuario;

public interface IPago {
	
	
	public boolean repartirGastos();
	
	
	//getters y setters
	public int getId();
	public HashMap<IUsuario, HashMap<IUsuario, Double>> getCuotas();
	public Double getTotal();
	public IGrupo getGrupoGasto();
	public HashMap<IUsuario, Boolean> getPagado();
	
	public void setTotal(Double total);
	public void setCuotas(HashMap<IUsuario, HashMap<IUsuario, Double>> cuotas);
	public void setGrupoGasto(IGrupo grupoGasto);
	
}
