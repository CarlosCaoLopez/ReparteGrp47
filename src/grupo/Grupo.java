package grupo;


import java.util.ArrayList;
import java.util.Objects;

import gasto.IGasto;
import pago.IPago;
import pago.Pago;
import usuario.IUsuario;

public class Grupo implements IGrupo {
	private int id;
	private String nombreGrupo;
	private String descripcion;
	private ArrayList<IGasto> gastos;
	private ArrayList<IUsuario> usuarios;
	private ArrayList<IPago> pagos;
	
	
	// Constructores
	public Grupo(int id, String nombreGrupo, String descripcion, ArrayList<IUsuario> usuarios) {
		
		if(id > 0 && nombreGrupo != null && !nombreGrupo.isEmpty() && descripcion != null && !descripcion.isEmpty() && usuarios != null && !usuarios.isEmpty()) {
			this.id = id;
			this.nombreGrupo = nombreGrupo;
			this.descripcion = descripcion;
			this.gastos = new ArrayList<>();
			this.usuarios = new ArrayList<>();
			this.usuarios.addAll(usuarios);
			this.pagos = new ArrayList<>();		
				
		}
	}
	
	//Getters 

	public int getId() {
		return id;
	}

	public String getNombreGrupo() {
		return nombreGrupo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public ArrayList<IGasto> getGastos() {
		return gastos;
	}

	public ArrayList<IUsuario> getUsuarios() {
		return usuarios;
	}

	public ArrayList<IPago> getPagos() {
		return pagos;
	}
	
	// Setters
	
	
	// Métodos
	public void setNombreGrupo(String nombreGrupo) {
		if(nombreGrupo != null && !nombreGrupo.isEmpty()) {
			this.nombreGrupo = nombreGrupo;
		}
	}
	public void setDescripcion(String descripcion) {
		if(descripcion != null && !descripcion.isEmpty()) {
			this.descripcion = descripcion;
		}
	}
	
	public void setGastos(ArrayList<IGasto> gastos) {
		if(gastos != null)
			this.gastos = gastos;
	}

	public void setUsuarios(ArrayList<IUsuario> usuarios) {
		if(usuarios != null)
			this.usuarios = usuarios;
	}

	public void setPagos(ArrayList<IPago> pagos) {
		if(pagos != null)
			this.pagos = pagos;
	}

	@Override
	public boolean anadirMiembro(IUsuario nuevoUsuario) {
		
		if(nuevoUsuario != null) {
			this.usuarios.add(nuevoUsuario);
			return true;
		}
		return false;
	}

	@Override
	public boolean eliminarMiembro(IUsuario usuario) {
		if(usuario != null) {
			this.usuarios.remove(usuario);
			return true;
		}
		return false;
	}
	

	@Override
	public boolean anadirGasto(IGasto gasto) {
		if(gasto != null && this.usuarios.contains(gasto.getPagador())) {
			this.gastos.add(gasto);
			return true;
		}
		return false;
	}

	/*
	 * @Override public void modificarGasto(IGasto gasto) { if(gasto != null) {
	 * 
	 * int indice = this.gastos.indexOf(gasto); // Obtenemos el índice donde se
	 * encuentra el objeto if(indice != -1) { // Si el objeto existe en el ArrayList
	 * this.gastos.set(indice, gasto); // Lo sustituímos } }
	 * 
	 * }
	 */

	@Override
	public boolean dividirGastos() {
		if(this.getUsuarios()!=null && this.getGastos()!=null) {
			if(!this.getUsuarios().isEmpty() && !this.getGastos().isEmpty()) {
				IPago pago = new Pago(this.id+this.gastos.size(), this);
				boolean check = pago.repartirGastos();
				this.pagos.add(pago);
				// Vaciamos la lista de gastos, pues ya se han computado en el pago
				this.gastos.clear();
				return check;
				
			}
		}
		return false;
	}

	
	

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	// Dos grupos son iguales si tienen el mismo id
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Grupo other = (Grupo) obj;
		return id == other.id;
	}
	
	

}
