package pago;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import grupo.IGrupo;
import usuario.IUsuario;

public class Pago implements IPago {

	
	private int id;
	private Double total;
	private HashMap<IUsuario, Double> cuentas;
	private IGrupo grupoGasto;
	
	
	
	
	
	//constructor del pago
	public Pago(int id, IGrupo grupoGasto) {
		
		if(id>0&&grupoGasto!=null) {
		this.id = id;
		this.total = 0.0;
		this.cuentas = new HashMap<>();
		for(Usuario user in grupoGasto.)
		this.grupoGasto = grupoGasto;
		}
	}

	
	
	
	
	
	
	@Override
	public void asignarGrupo(IGrupo grupo) {
		if(grupo!=null) {
			this.grupoGasto=grupo;
		}
	}
	
	@Override
	public void asignarDeudores(ArrayList<IUsuario> nuevosdeudores) {
		if(nuevosdeudores!=null) {
			if(nuevosdeudores.size()>0) {
				this.deudores=nuevosdeudores;
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
