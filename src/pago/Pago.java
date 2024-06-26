package pago;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.Random;

import gasto.IGasto;
import grupo.IGrupo;
import usuario.IUsuario;

public class Pago implements IPago {

	
	private int id;
	private ArrayList<IGasto> gastos;
	private double total;
	private HashMap<IUsuario, HashMap<IUsuario, Double>> cuotas;
	private HashMap<IUsuario, Boolean> pagado;
	private IGrupo grupoGasto;
	
	
	
	
	
	//constructor del pago
	public Pago(int id, IGrupo grupoGasto) {
		
		if(id>0 && grupoGasto!=null && grupoGasto.getUsuarios()!=null && grupoGasto.getGastos()!=null) {
			if(!grupoGasto.getUsuarios().isEmpty() && !grupoGasto.getGastos().isEmpty()) {
				this.id = id;
				this.gastos = new ArrayList<>();
				this.gastos.addAll(grupoGasto.getGastos());
				this.total = 0.0;
				this.cuotas = new HashMap<>();
				this.pagado = new HashMap<>();
				this.grupoGasto = grupoGasto;
			}	
		}
	}

	
	
	
	@Override
	public int getId() {
		return id;
	}
	@Override
	public Double getTotal() {
		return total;
	}
	@Override
	public HashMap<IUsuario, HashMap<IUsuario, Double>> getCuotas() {
		return cuotas;
	}
	@Override
	public IGrupo getGrupoGasto() {
		return grupoGasto;
	}
	@Override
	public HashMap<IUsuario, Boolean> getPagado() {
		return pagado;
	}
	
	@Override
	public void setTotal(Double total) {
		this.total = total;
	}
	@Override
	public void setCuotas(HashMap<IUsuario, HashMap<IUsuario, Double>> cuotas) {
		if(cuotas != null)
			this.cuotas = cuotas;
	}
	
	@Override
	public void setGrupoGasto(IGrupo grupoGasto) {
		if(grupoGasto != null)
			this.grupoGasto = grupoGasto;
	}
	
	@Override
	public void setPagado(HashMap<IUsuario, Boolean> pagado) {
		if(pagado != null)
			this.pagado = pagado;
	}
	
	@Override
	public void setGastos(ArrayList<IGasto> gastos) {
		if(gastos != null)
			this.gastos = gastos;
	}




	@Override
	public boolean repartirGastos() {
		int n;
		double sumaParcial;
		
		if(grupoGasto!=null && grupoGasto.getUsuarios()!=null && grupoGasto.getGastos()!=null) {
			if(!grupoGasto.getUsuarios().isEmpty() && !grupoGasto.getGastos().isEmpty()) {
				for(IUsuario user : grupoGasto.getUsuarios()) {
					// Añadimos a cada usuario una lista, donde se indicará lo que le debe a cada uno
					cuotas.put(user, new HashMap<>()); 
					for(IUsuario otro : grupoGasto.getUsuarios()) {
						if(!user.equals(otro)) cuotas.get(user).put(otro, 0.0);
					}	
				} 
	
				n = grupoGasto.getUsuarios().size(); // Número de personas sobre las que se reparte el gasto				
				for(IGasto gasto : gastos) {
					IUsuario pagador = gasto.getPagador(); // Separamos al pagador
					double cantidad = gasto.getCantidad(); // Obtenemos el gasto realizado por el pagador
					total += cantidad; // Actualizamos el total de dinero en el pago
					cantidad=cantidad/n; // Dividimos la cantidad entre el número de miembros
					
					for(IUsuario user: grupoGasto.getUsuarios()) {
						// Por cada miembro del grupo que no sea el pagador, se le imputa el gasto.
						if(!user.equals(pagador)) {
							// Se busca lo que le debía previamente la persona al pagador
							sumaParcial = cuotas.get(user).get(pagador);
							// Y se actualiza esta cantidad
							cuotas.get(user).put(pagador, sumaParcial+cantidad);
						}
					}
				}
				
				
				for(IUsuario user : grupoGasto.getUsuarios()) {
					// Indicamos el estado de cada pago como falso
					pagado.put(user, false);
					// Notificamos a cada usuario del nuevo pago pendiente
					user.notificar(this);
				}
				return true;
			}
				
		}
		return false;
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
		Pago other = (Pago) obj;
		return id == other.id;
	}
}
