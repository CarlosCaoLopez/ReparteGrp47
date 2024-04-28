package usuario;

import java.util.ArrayList;
import java.util.Objects;
import java.time.LocalDate;

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
	private LocalDate fechaNacimiento;
	private String contrasena;
	private String datosBancarios;
	private ArrayList<String> notificaciones; 
	private ArrayList<IGrupo> grupos;
	private ArrayList<IGasto> gastos;
	private ArrayList<IPago> pagos;
	
	
	// Constructores
	
	public Usuario(int id, String nombreUsuario, String nombreReal, String correoElectronico, LocalDate fechaNacimiento, String contrasena, String datosBancarios,
			ArrayList<String> notificaciones, ArrayList<IGrupo> grupos, ArrayList<IGasto> gastos,  ArrayList<IPago> pagos) {
		if(id > 0 && nombreUsuario != null && correoElectronico != null && fechaNacimiento != null && contrasena != null && datosBancarios != null
				&& notificaciones != null && grupos != null && gastos != null && pagos != null) {
			if(checkCorreoElectronico(correoElectronico) && checkDatosBancarios(datosBancarios) && checkContrasena(contrasena)) {
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
	}
	
	
	public Usuario(int id, String nombreUsuario, String nombreReal, String correoElectronico, LocalDate fechaNacimiento, String contrasena, String datosBancarios) {
		if(nombreUsuario != null && correoElectronico != null && fechaNacimiento != null && contrasena != null && datosBancarios != null) {
			if(checkCorreoElectronico(correoElectronico) && checkDatosBancarios(datosBancarios) && checkContrasena(contrasena)) {
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
	}
	
	
	// Getters
	public int getId() {
		return id;
	}


	public String getNombreUsuario() {
		return nombreUsuario;
	}


	public String getNombreReal() {
		return nombreReal;
	}


	public String getCorreoElectronico() {
		return correoElectronico;
	}


	public LocalDate getFechaNacimiento() {
		return fechaNacimiento;
	}


	public String getContrasena() {
		return contrasena;
	}


	public String getDatosBancarios() {
		return datosBancarios;
	}


	public ArrayList<String> getNotificaciones() {
		return notificaciones;
	}


	public ArrayList<IGrupo> getGrupos() {
		return grupos;
	}


	public ArrayList<IGasto> getGastos() {
		return gastos;
	}


	public ArrayList<IPago> getPagos() {
		return pagos;
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
	
	
	/**
	 * Verifica si el correo electrónico dado es correcto. Para ser correcto debe seguir el formato:
	 * {}@{}.{}, donde {} indican cadenas de caracteres no vacías. Y solo puede haber un @ en la cadena
	 * 
	 * @param correoElectronico
	 * @return true si es correcto, false en caso contrario
	 */
	private boolean checkCorreoElectronico(String correoElectronico) {
		// Restricciones: el correo electrónico
		if(!correoElectronico.isEmpty() && !correoElectronico.startsWith("@") && correoElectronico.contains("@") && (correoElectronico.chars().filter(c -> c == '@').count() == 1) 
			&& correoElectronico.contains(".") && !correoElectronico.endsWith(".") && (correoElectronico.lastIndexOf("@")+1<correoElectronico.lastIndexOf(".")))
			return true;
		
		return false;
	}
	
	/**
	 * Verifica si el código IBAN dado es correcto. Para ser correcto debe seguir el formato:
	 * LLNNNNNNNNNNNNNNNNNNNNNN, es decir, 2 letras mayúsculas (L) y 22 números (N)
	 * En realidad, habría que realizar más comprobaciones, pero son complejas ya que dependen del país de origen de la cuenta 
	 * (indicado con las dos primeras letras) y de otros parámetros
	 * 
	 * @param datosBancarios
	 * @return true si es correcto, false en caso contrario
	 */
	private boolean checkDatosBancarios(String datosBancarios) {
		if(datosBancarios.length()!=24) return false;
		
		// Los dos primeros caracteres del String son letrsa y los otros 22 números
		String verificacion = "^[A-Z]{2}\\d{22}$";
		if(datosBancarios.matches(verificacion))
			return true;
		
		return false;
	}
	
	/**
	 * Verifica si la contraseña dada es correcta. Para ser correcta debe tener al menos 6 caracteres, e incluir al menos una mayúscula y un símbolo
	 * 
	 * @param correoElectronico
	 * @return true si es correcto, false en caso contrario
	 */
	private boolean checkContrasena(String contrasena) {
		if(contrasena.length()<6) 
			return false;
		
		int checks[]= {0, 0};
		// Comprobamos que contenga una mayúscula y un símbolo
		for(char c : contrasena.toCharArray()) {
			if(Character.isUpperCase(c))
				checks[0]=1;
			if (!Character.isLetterOrDigit(c))
				checks[1]=1;
		}
		
		if(checks[0] == 1 && checks[1] == 1) 
			return true;
		
		return false;
	}
	
	

}
