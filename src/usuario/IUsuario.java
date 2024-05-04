package usuario;

import java.time.LocalDate;
import java.util.ArrayList;

import grupo.IGrupo;
import pago.IPago;
import gasto.IGasto;

public interface IUsuario {	
	
	public IGrupo crearGrupo(int id, String nombreGrupo, String descripcion, ArrayList<IUsuario> usuarios);
	public boolean gestionarGrupo(IGrupo grupo, String nombreGrupo, String descripcion, ArrayList<IUsuario> usuarios);
	public boolean anadirGasto(IGrupo grupo, double cantidad); 
	public boolean dividirGastos(IGrupo grupo);
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
