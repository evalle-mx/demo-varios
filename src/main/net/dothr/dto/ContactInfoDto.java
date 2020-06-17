package net.dothr.dto;

public class ContactInfoDto  extends ComunDto{
	private String idPersona;
	private String idReferencia;
	private String idEmpresa;
	private String idContacto;
	private String idTipoContacto;
	private String lbTipoContacto;
	private String contacto;
	private String prefijo;
	private String codigoPais;
	private String codigoArea;
	private String numero;
	private String adicional;
	private String  estaVerificado;
	private Byte idClasificacionContacto;

	public ContactInfoDto(){}
	
	//public ContactInfoDto(String idEmpresaConf,String idPersona){
	public ContactInfoDto(String idEmpresaConf,String idPersona,String idEmpresa){
		this.setIdEmpresaConf(idEmpresaConf);
		this.idPersona=idPersona;
		this.idEmpresa = idEmpresa;
	}

	public void setIdPersona(String idPersona) {
		this.idPersona = idPersona;
	}

	public String getIdPersona() {
		return idPersona;
	}

	public void setIdContacto(String idContacto) {
		this.idContacto = idContacto;
	}

	public String getIdContacto() {
		return idContacto;
	}

	public void setIdTipoContacto(String idTipoContacto) {
		this.idTipoContacto = idTipoContacto;
	}

	public String getIdTipoContacto() {
		return idTipoContacto;
	}

	public String getLbTipoContacto() {
		return lbTipoContacto;
	}

	public void setLbTipoContacto(String lbTipoContacto) {
		this.lbTipoContacto = lbTipoContacto;
	}

	public void setIdReferencia(String idReferencia) {
		this.idReferencia = idReferencia;
	}

	public String getIdReferencia() {
		return idReferencia;
	}

	public void setIdEmpresa(String idEmpresa) {
		this.idEmpresa = idEmpresa;
	}

	public String getIdEmpresa() {
		return idEmpresa;
	}

	public void setContacto(String contacto) {
		this.contacto = contacto;
	}

	public String getContacto() {
		return contacto;
	}
	public void setPrefijo(String prefijo) {
		this.prefijo = prefijo;
	}
	public String getPrefijo() {
		return prefijo;
	}
	public void setCodigoPais(String codigoPais) {
		this.codigoPais = codigoPais;
	}
	public String getCodigoPais() {
		return codigoPais;
	}
	public void setCodigoArea(String codigoArea) {
		this.codigoArea = codigoArea;
	}
	public String getCodigoArea() {
		return codigoArea;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}
	public String getNumero() {
		return numero;
	}
	public void setAdicional(String adicional) {
		this.adicional = adicional;
	}
	public String getAdicional() {
		return adicional;
	}

	public void setIdClasificacionContacto(Byte idClasificacionContacto) {
		this.idClasificacionContacto = idClasificacionContacto;
	}

	public Byte getIdClasificacionContacto() {
		return idClasificacionContacto;
	}

	public void setEstaVerificado(String estaVerificado) {
		this.estaVerificado = estaVerificado;
	}

	public String getEstaVerificado() {
		return estaVerificado;
	}
	
}
