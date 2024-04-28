package usuario;

import java.util.ArrayList;
import java.util.Objects;

import gasto.Gasto;
import gasto.IGasto;
import grupo.Grupo;
import grupo.IGrupo;
import pago.Pago;
import pago.IPago;

public class Usuario implements IUsuario {
	private int id;
	private String nombreUsuario;
	private String nombreReal;
	private String correoElectronico;
	private String fechaNacimiento;
	private String contrasena;
	private String datosBancarios;
	private ArrayList<String> notificaciones; 
	private ArrayList<IGrupo> grupos;
	private ArrayList<IGrupo> gastos;
	private ArrayList<IPago> pagos;
	
	
	// Constructores
	
	public Usuario(int id, String nombreUsuario, String nombreReal, String correoElectronico, String fechaNacimiento, String contrasena, String datosBancarios,
			ArrayList<String> notificaciones, ArrayList<IGrupo> grupos, ArrayList<IGrupo> gastos,  ArrayList<IPago> pagos) {
		if(id > 0 && nombreUsuario != null && correoElectronico != null && fechaNacimiento != null && contrasena != null && datosBancarios != null
				&& notificaciones != null && grupos != null && gastos != null && pagos != null) {
			this.id = id;
			this.nombreUsuario = nombreUsuario;
			this.correoElectronico = correoElectronico;
			this.fechaNacimiento = fechaNacimiento;
			this.contrasena = contrasena;
			this.datosBancarios = datosBancarios;
			this.notificaciones = new ArrayList<>();
			this.grupos = new ArrayList<>();
			this.grupos.addAll(grupos);
			this.gastos = new ArrayList<>();
			this.gastos.addAll(gastos);
			this.pagos = new ArrayList<>();
			this.pagos.addAll(pagos);
		}
	}
	
	
	public Usuario(int id, String nombreUsuario, String nombreReal, String correoElectronico, String fechaNacimiento, String contrasena, String datosBancarios) {
		if(nombreUsuario != null && correoElectronico != null && fechaNacimiento != null && contrasena != null && datosBancarios != null) {
			this.id = id;
			this.nombreUsuario = nombreUsuario;
			this.correoElectronico = correoElectronico;
			this.fechaNacimiento = fechaNacimiento;
			this.contrasena = contrasena;
			this.datosBancarios = datosBancarios;
			this.notificaciones = new ArrayList<>();
			this.grupos = new ArrayList<>();
			this.gastos = new ArrayList<>();
			this.pagos = new ArrayList<>();
		}
	}


	// Métodos 

	@Override
	public IGrupo crearGrupo(int id, String nombreGrupo, String descripcion, ArrayList<IUsuario> lideres) {
		
		// Creamos el grupo
		IGrupo grupo = new Grupo(id, nombreGrupo, descripcion, lideres);
		
		
		this.grupos.add(grupo); // Añadimos el grupo al conjunto de grupos del usuario
		return grupo; // return del grupo creado
		

	}

	@Override
	public void gestionarGrupo(IGrupo grupo, String descripcion, ArrayList<IUsuario> nuevosUsuarios) {
		
		if(grupo != null) {
			
			grupo.modificarDescripcion(descripcion); // Añadimos la descripción
			
			for(IUsuario usr: nuevosUsuarios) { // Añadimos los usuarios
				grupo.anadirMiembro(usr);
			}
		}

	}

	@Override
	public void notificar(String notificacion) {
		
		if(notificacion != null) {
			this.notificaciones.add(notificacion);
		}

	}

	@Override
	public IPago realizarPago(IGasto gasto) {
//		
//		if(gasto != null) {
//			
//			IPago pago = new Pago(); // Creamos el pago FALTAAAA!!!!!!!!!!
//			this.gastos.remove(gasto); // Eliminamos el gasto
//			this.pagos.add(pago); // Añadimos el pago
//			
//			return pago;
//		}
//		
//			
//		
		return null;
	}


	@Override
	public int hashCode() {
		return Objects.hash(id);
	}


	// Dos usuarios son iguales si tienen el mismo id
	@Override 
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Usuario other = (Usuario) obj;
		return id == other.id;
	}
	
	

}
