package gasto;

import grupo.IGrupo;
import usuario.IUsuario;
import usuario.Usuario;

import java.util.Random;

public class Gasto implements IGasto {

	
	private int id;
	private double cantidad;
	private IGrupo grupoGasto;
	private IUsuario pagador;
	private String servicio;
	
	//constructor de la clase
	//necesita una cifra de cuanto es el gasto
	//necesita el grupo al que se va a asignar el gasto
	//necesita el usuario que va a pagar el gasto
	public Gasto(double importe, IGrupo grupo, IUsuario pagando,String negocio) {
		
		//si el grupo, pagador existen y el importe es mayor a cero
		//creamos su id aleatorio y asignamos el resto
		//es posible que el nombre de negocio quede a null
		if(grupo!=null&&pagando!=null&&importe>0) {
		Random ran=new Random();
		this.id= ran.nextInt(1000000);
		this.cantidad=importe;
		this.grupoGasto=grupo;
		this.pagador=pagando;
		this.servicio=negocio;
		}
		
	}
	
	//version del constructor que no pone un negocio
	public Gasto(double importe, IGrupo grupo, IUsuario pagando) {
		
		//si el grupo, pagador existen y el importe es mayor a cero
		//creamos su id aleatorio y asignamos el resto
		//es posible que el nombre de negocio quede a null
		if(grupo!=null&&pagando!=null&&importe>0) {
		Random ran=new Random();
		this.id= ran.nextInt(1000000);
		this.cantidad=importe;
		this.grupoGasto=grupo;
		this.pagador=pagando;
		this.servicio=null;
		}
		
	}
	
	
	//funciones de tipo setter para cambiar el servicio, importe y pagador
	@Override
	public void modificarServicio(String nuevoservicio) {
		this.servicio=nuevoservicio;
	}
	@Override
	public void modificarPagador(IUsuario nuevopagador) {
		if(nuevopagador!=null) {
			this.pagador=nuevopagador;
		}
	}
	@Override
	public void modificarGasto(double nuevacantidad) {
		if(nuevacantidad>0) {
			this.cantidad=nuevacantidad;
		}
	}

	//funcion para meter el gasto dentro de un grupo
	@Override
	public IGasto registrarGasto(IGrupo grupo) {
		
		grupo.anadirGasto(this);
		
		return this;
	}
	
	
	
	// equals de gastos (es literalmente el de usuario ya que ambos usan id)
		@Override 
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Gasto other = (Gasto) obj;
			return id == other.id;
		}

}
