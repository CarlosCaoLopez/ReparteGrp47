package gasto;

import grupo.IGrupo;
import usuario.IUsuario;
import usuario.Usuario;

import java.util.Objects;
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
	public Gasto(int id, double importe, IGrupo grupo, IUsuario pagando,String negocio) {
		
		if(id>0 && grupo!=null && pagando!=null && grupo.getUsuarios().contains(pagando) && importe>0 && negocio!=null && !negocio.isEmpty()) {
			this.id = id;
			this.cantidad = importe;
			this.grupoGasto = grupo;
			this.pagador = pagando;
			this.servicio = negocio;
		}
		
	}
	
	//version del constructor que no pone un negocio
	public Gasto(int id, double importe, IGrupo grupo, IUsuario pagando) {
		
		if(id>0 && grupo!=null && pagando!=null && grupo.getUsuarios().contains(pagando) && importe>0) {
			this.id = id;
			this.cantidad = importe;
			this.grupoGasto = grupo;
			this.pagador = pagando;
			this.servicio = null;
		}
		
	}
	

	//funcion para meter el gasto dentro de un grupo
	@Override
	public IGasto registrarGasto(IGrupo grupo) {
		if(grupo != null) {
			grupo.anadirGasto(this);
		}
		
		return this;
	}
	
	@Override
	public int getId(){
		return this.id;
	}
	@Override
	public double getCantidad(){
		return this.cantidad;
	}
	@Override
	public IGrupo getGrupoGasto(){
		return this.grupoGasto;
	}
	@Override
	public IUsuario getPagador(){
		return this.pagador;
	}
	@Override
	public String getServicio(){
		return this.servicio;
	}
	
	
	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

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
