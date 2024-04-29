package pago;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import gasto.IGasto;
import grupo.IGrupo;
import usuario.IUsuario;

public class Pago implements IPago {

	
	private int id;
	private Double total;
	private HashMap<IUsuario, Double> cuentas;
	private IGrupo grupoGasto;
	
	
	
	
	
	//constructor del pago
	public Pago(int id, IGrupo grupoGasto) {
		
		if(id>0&&grupoGasto!=null&&) {
			if(!grupoGasto.getUsuarios().isEmpty()) {
				this.id = id;
				this.total = 0.0;
				this.cuentas = new HashMap<>();
				for(IUsuario user : grupoGasto.getUsuarios()) {
					cuentas.put(user, 0.0);
				}
			this.grupoGasto = grupoGasto;
			}	
		}
	}

	
	
	
	@Override
	public int getId() {
		return id;
	}
	@Override
	public void setId(int id) {
		this.id = id;
	}
	@Override
	public Double getTotal() {
		return total;
	}
	@Override
	public void setTotal(Double total) {
		this.total = total;
	}
	@Override
	public HashMap<IUsuario, Double> getCuentas() {
		return cuentas;
	}
	@Override
	public void setCuentas(HashMap<IUsuario, Double> cuentas) {
		this.cuentas = cuentas;
	}
	@Override
	public IGrupo getGrupoGasto() {
		return grupoGasto;
	}
	@Override
	public void setGrupoGasto(IGrupo grupoGasto) {
		this.grupoGasto = grupoGasto;
	}




	@Override
	public void repartirGasto(ArrayList<IGasto> listagastos) {
		
		for(IGasto gasto : listagastos) {
			IUsuario pagador=gasto.getPagador();//separamos al pagador
			double cantidad=gasto.getCantidad();//obtenemos el pago total
			cantidad=cantidad/listagastos.size();//lo dividimos entre los miembros
			
			for(IUsuario user: grupoGasto.getUsuarios()) {//por cada miembro del grupo, si no eres el pagador se te imputa el pago
				if(!user.equals(pagador)) {
					cuentas.put(user, cuentas.get(user)+cantidad);
				}
			}
		}
	}


	
	// equals de pagos (es literalmente el de usuario ya que ambos usan id)
			@Override 
			public boolean equals(Object obj) {
				if (this == obj)
					return true;
				if (obj == null)
					return false;
				if (getClass() != obj.getClass())
					return false;
				Pago other = (Pago) obj;
				return id == other.id;
			}
}
