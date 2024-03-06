package agenda.modelo;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Embeddable;

@Embeddable
public class Domicilio implements Comparable<Domicilio>, Serializable {

	private String tipoVia;
	private String via;
	private int numero;
	private int piso;
	private String puerta;
	private String codigoPostal;
	private String ciudad;
	private String provincia;
	
	
	
	
	
	public Domicilio() {
		
	}


	public String getTipoVia() {
		return tipoVia;
	}


	public void setTipoVia(String tipoVia) {
		this.tipoVia = tipoVia;
	}


	public String getVia() {
		return via;
	}


	public void setVia(String via) {
		this.via = via;
	}


	public int getNumero() {
		return numero;
	}


	public void setNumero(int numero) {
		this.numero = numero;
	}


	public int getPiso() {
		return piso;
	}


	public void setPiso(int piso) {
		this.piso = piso;
	}


	public String getPuerta() {
		return puerta;
	}


	public void setPuerta(String puerta) {
		this.puerta = puerta;
	}


	public String getCodigoPostal() {
		return codigoPostal;
	}


	public void setCodigoPostal(String codigoPostal) {
		this.codigoPostal = codigoPostal;
	}


	public String getCiudad() {
		return ciudad;
	}


	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}


	public String getProvincia() {
		return provincia;
	}


	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}


	@Override
	public int hashCode() {
		return Objects.hash(ciudad, codigoPostal, numero, piso, provincia, puerta, tipoVia, via);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Domicilio other = (Domicilio) obj;
		return Objects.equals(ciudad, other.ciudad) && Objects.equals(codigoPostal, other.codigoPostal)
				&& numero == other.numero && piso == other.piso && Objects.equals(provincia, other.provincia)
				&& Objects.equals(puerta, other.puerta) && Objects.equals(tipoVia, other.tipoVia)
				&& Objects.equals(via, other.via);
	}


	@Override
	public int compareTo(Domicilio o) { //para este caso no es necesario
		// TODO Auto-generated method stub
		return 0;
	}
	
	
	
}
