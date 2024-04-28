package usuario;

import java.util.ArrayList;
import grupo.IGrupo;
import pago.IPago;
import gasto.IGasto;

public interface IUsuario {
//	private int id;
//	private String nombreUsuario;
//	private String correoElectronico;
//	private String fechaNacimiento;
//	private String contrasena;
//	private String datosBancarios;
//	private ArrayList<String> notificaciones; 
//	private ArrayList<IGrupo> grupos;
//	private ArrayList<IGrupo> gastos;
//	private ArrayList<IPago> pagos;
	
	
	public IGrupo crearGrupo(String nombreGrupo, ArrayList<IUsuario> usuarios);
	public void gestionarGrupo(IGrupo grupo, String descripcion, ArrayList<IUsuario> nuevosUsuarios);
	public void notificar(String notificacion);
	public IPago realizarPago(IGasto gasto); 
}
