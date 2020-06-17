package mayatrav.vo;

import java.util.Map;
/* http://www.aprenderaprogramar.com/index.php?option=com_content&view=article&id=614:interfaces-map-y-sortedmap-del-api-de-java-clases-hashmap-y-treemap-ejemplo-diferencias-cu00922c&catid=58:curso-lenguaje-programacion-java-nivel-avanzado-i&Itemid=180*/
public class ViajeroVo {

	private String idViajero;
	private String nombre;
	private String apellidos;	
	private Map<String,String> contactos;	// = new TreeMap<String, String>();	
	private String nPasaporte;
	

	public String getIdViajero() {
		return idViajero;
	}

	public void setIdViajero(String idViajero) {
		this.idViajero = idViajero;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public Map<String, String> getContactos() {
		return contactos;
	}

	public void setContactos(Map<String, String> contactos) {
		this.contactos = contactos;
	}

	public String getnPasaporte() {
		return nPasaporte;
	}

	public void setnPasaporte(String nPasaporte) {
		this.nPasaporte = nPasaporte;
	}
	
	
	

}
