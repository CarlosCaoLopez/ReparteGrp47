package grupo;


import java.util.ArrayList;
import java.util.Objects;

import gasto.IGasto;
import pago.IPago;
import usuario.IUsuario;

public class Grupo implements IGrupo {
	private int id;
	private String nombreGrupo;
	private String descripcion;
	private ArrayList<IGasto> gastos;
	private ArrayList<IUsuario> usuarios;
	private ArrayList<IPago> pagos;
	
	
	// Constructores
	
	public Grupo(int id, String nombreGrupo, String descripcion, ArrayList<IGasto> gastos, ArrayList<IUsuario> usuarios, ArrayList<IPago> pagos) {
		boolean flag = true;
		
		if(gastos != null && usuarios != null) {
		/* Comprobar que el pagador de cada gasto se encuentra dentro de los usuarios */
			for(IGasto gasto:gastos) {
				if(!usuarios.contains(gasto.getPagador())) {
					flag = false;
				}
			}
			if(id > 0 && nombreGrupo != null && descripcion != null && pagos != null && flag) {
				this.id = id;
				this.nombreGrupo = nombreGrupo;
				this.descripcion = descripcion;
				this.gastos = new ArrayList<>();
				this.gastos.addAll(gastos);
				this.usuarios = new ArrayList<>();
				this.usuarios.addAll(usuarios);
				this.pagos = new ArrayList<>();
				this.pagos.addAll(pagos);
			}
		}
	}

	public Grupo(int id, String nombreGrupo, String descripcion, ArrayList<IUsuario> usuarios) {
		
		if(id > 0 && nombreGrupo != null && descripcion != null && usuarios != null) {
			if(!usuarios.isEmpty()) {
				this.id = id;
				this.nombreGrupo = nombreGrupo;
				this.descripcion = descripcion;
				this.gastos = new ArrayList<>();
				this.usuarios = new ArrayList<>();
				this.usuarios.addAll(usuarios);
				this.pagos = new ArrayList<>();		
			}
						
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
	
	// Métodos
	
	@Override
	public void anadirMiembro(IUsuario nuevoUsuario) {
		
		if(nuevoUsuario != null) {
			this.usuarios.add(nuevoUsuario);
		}

	}

	@Override
	public void eliminarMiembro(IUsuario usuario) {
		if(usuario != null) {
			this.usuarios.remove(usuario);
		}
	}

	@Override
	public void anadirGasto(IGasto gasto) {
		if(gasto != null) {
			this.gastos.add(gasto);
		}

	}

	@Override
	public void modificarGasto(IGasto gasto) {
		if(gasto != null) {
			
			int indice = this.gastos.indexOf(gasto); // Obtenemos el índice donde se encuentra el objeto
			if(indice != -1) { // Si el objeto existe en el ArrayList
				this.gastos.set(indice, gasto); // Lo sustituímos
			}
		}

	}

	@Override
	public void anadirPago(IPago pago) {
		

	}
	
	@Override
	public void modificarDescripcion(String descripcion){
		if(descripcion != null) {
			this.descripcion = descripcion;
		}

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
