package pago;

import java.util.ArrayList;
import java.util.Random;

import grupo.IGrupo;
import usuario.IUsuario;

public class Pago implements IPago {

	
	private int id;
	private String servicio;
	private Double cantidad;
	private IUsuario acreedor;
	private ArrayList<IUsuario> deudores;
	private IGrupo grupoGasto;
	
	
	//constructor del pago
	public Pago(String servicio,Double cantidad,IUsuario acreedor, ArrayList<IUsuario> deudores,IGrupo grupoGasto) {
		
		//chequeos de que la cantidad, acreedor y lista existen
		if(cantidad!=null&&acreedor!=null&&deudores!=null&&grupoGasto!=null) {
			//chequeos de que la cantidad no es nula y hay deudores
			if (cantidad>0&&deudores.size()>0) {
				Random ran=new Random();
				this.id= ran.nextInt(1000000);
				this.servicio=servicio;
				this.cantidad=cantidad;
				this.acreedor=acreedor;
				this.deudores=deudores;
				this.grupoGasto=grupoGasto;
			}
		}
	}
	
	//constructor pero sin la string servicio
	public Pago(Double cantidad,IUsuario acreedor, ArrayList<IUsuario> deudores,IGrupo grupoGasto) {
		
		//chequeos de que la cantidad, acreedor y lista existen
		if(cantidad!=null&&acreedor!=null&&deudores!=null&&grupoGasto!=null) {
			//chequeos de que la cantidad no es nula y hay deudores
			if (cantidad>0&&deudores.size()>0) {
				Random ran=new Random();
				this.id= ran.nextInt(1000000);
				this.cantidad=cantidad;
				this.acreedor=acreedor;
				this.deudores=deudores;
				this.grupoGasto=grupoGasto;
			}
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
