package usuario;

import java.time.LocalDate;
import java.util.ArrayList;

import grupo.IGrupo;
import pago.IPago;
import gasto.IGasto;

public interface IUsuario {	
	
	public IGrupo crearGrupo(int id, String nombreGrupo, String descripcion, ArrayList<IUsuario> usuarios);
	public void gestionarGrupo(IGrupo grupo, String descripcion, ArrayList<IUsuario> nuevosUsuarios);
	public void anadirGasto(IGrupo grupo, double cantidad); 
	public void dividirGastos(IGrupo grupo);
	public void notificar(IPago pago);
	public IPago realizarPago(IPago pago);
	
	// Getters
	public int getId();
	public String getNombreUsuario();
	public String getNombreReal();
	public String getCorreoElectronico();
	public LocalDate getFechaNacimiento();
	public String getContrasena();
	public String getDatosBancarios();
	public ArrayList<String> getNotificaciones();
	public ArrayList<IGrupo> getGrupos();
	public ArrayList<IGasto> getGastos();
	public ArrayList<IPago> getPagos();
}
