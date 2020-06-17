package net.dothr.dto;

import java.util.List;

public class CompensacionDto extends ComunDto {

	private String idPersona;
	private String diasAguinaldo;
	private String cantidadFondoAhorro;
	private String comedor;
	private String celular;
	private String clubGym;
	private String checkUp;
	private String ultimoMontoUtilidades;
	private String curp;
	private String otro;
	private String fechaCaptura;
	
	/* A partir de Persona */
	private String nombreCompleto;
	private String nombre;
	private String apellidoMaterno;
	private String apellidoPaterno;	
	private List<ReferenceDto> referencias;
	private List<ContactInfoDto> contactos;
	
	private String numeroHijos;
	private String fechaNacimiento;
	private String edad;
	private String idTipoGenero;
	private String lbGenero;
	private String idEstadoCivil;
	private String lbEstadoCivil;
	private String idPais;
	private String stPais;
	
	private VacacionesDto vacaciones;
	private SueldoDto sueldo;
	private AutomovilDto automovil;	
	
	/* Pendientes de creación Dto */
	private List<PlanDto> plans = null;		//PlanDto
	private List<SeguroDto> seguros = null;		//SeguroDto
	private List<ValeDto> vales = null;		//ValeDto
	private List<BonoDto> bonos = null;		//BonoDto
	
	private Boolean hayCambio;
	
	public String getIdPersona() {
		return idPersona;
	}
	public void setIdPersona(String idPersona) {
		this.idPersona = idPersona;
	}
	public String getDiasAguinaldo() {
		return diasAguinaldo;
	}
	public void setDiasAguinaldo(String diasAguinaldo) {
		this.diasAguinaldo = diasAguinaldo;
	}
	public String getCantidadFondoAhorro() {
		return cantidadFondoAhorro;
	}
	public void setCantidadFondoAhorro(String cantidadFondoAhorro) {
		this.cantidadFondoAhorro = cantidadFondoAhorro;
	}
	public String getComedor() {
		return comedor;
	}
	public void setComedor(String comedor) {
		this.comedor = comedor;
	}
	public String getCelular() {
		return celular;
	}
	public void setCelular(String celular) {
		this.celular = celular;
	}
	public String getClubGym() {
		return clubGym;
	}
	public void setClubGym(String clubGym) {
		this.clubGym = clubGym;
	}
	public String getCheckUp() {
		return checkUp;
	}
	public void setCheckUp(String checkUp) {
		this.checkUp = checkUp;
	}
	public String getUltimoMontoUtilidades() {
		return ultimoMontoUtilidades;
	}
	public void setUltimoMontoUtilidades(String ultimoMontoUtilidades) {
		this.ultimoMontoUtilidades = ultimoMontoUtilidades;
	}
	public String getCurp() {
		return curp;
	}
	public void setCurp(String curp) {
		this.curp = curp;
	}
	public String getOtro() {
		return otro;
	}
	public void setOtro(String otro) {
		this.otro = otro;
	}
	public String getFechaCaptura() {
		return fechaCaptura;
	}
	public void setFechaCaptura(String fechaCaptura) {
		this.fechaCaptura = fechaCaptura;
	}
	public String getNombreCompleto() {
		return nombreCompleto;
	}
	public void setNombreCompleto(String nombreCompleto) {
		this.nombreCompleto = nombreCompleto;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getApellidoMaterno() {
		return apellidoMaterno;
	}
	public void setApellidoMaterno(String apellidoMaterno) {
		this.apellidoMaterno = apellidoMaterno;
	}
	public String getApellidoPaterno() {
		return apellidoPaterno;
	}
	public void setApellidoPaterno(String apellidoPaterno) {
		this.apellidoPaterno = apellidoPaterno;
	}
	public List<ReferenceDto> getReferencias() {
		return referencias;
	}
	public void setReferencias(List<ReferenceDto> referencias) {
		this.referencias = referencias;
	}
	public List<ContactInfoDto> getContactos() {
		return contactos;
	}
	public void setContactos(List<ContactInfoDto> contactos) {
		this.contactos = contactos;
	}
	public String getFechaNacimiento() {
		return fechaNacimiento;
	}
	public void setFechaNacimiento(String fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}
	public String getEdad() {
		return edad;
	}
	public void setEdad(String edad) {
		this.edad = edad;
	}
	public String getIdTipoGenero() {
		return idTipoGenero;
	}
	public void setIdTipoGenero(String idTipoGenero) {
		this.idTipoGenero = idTipoGenero;
	}
	public String getLbGenero() {
		return lbGenero;
	}
	public void setLbGenero(String lbGenero) {
		this.lbGenero = lbGenero;
	}
	public String getIdEstadoCivil() {
		return idEstadoCivil;
	}
	public void setIdEstadoCivil(String idEstadoCivil) {
		this.idEstadoCivil = idEstadoCivil;
	}
	public String getLbEstadoCivil() {
		return lbEstadoCivil;
	}
	public void setLbEstadoCivil(String lbEstadoCivil) {
		this.lbEstadoCivil = lbEstadoCivil;
	}
	public String getIdPais() {
		return idPais;
	}
	public void setIdPais(String idPais) {
		this.idPais = idPais;
	}
	public String getStPais() {
		return stPais;
	}
	public void setStPais(String stPais) {
		this.stPais = stPais;
	}
	public VacacionesDto getVacaciones() {
		return vacaciones;
	}
	public void setVacaciones(VacacionesDto vacaciones) {
		this.vacaciones = vacaciones;
	}
	public SueldoDto getSueldo() {
		return sueldo;
	}
	public void setSueldo(SueldoDto sueldo) {
		this.sueldo = sueldo;
	}
	public AutomovilDto getAutomovil() {
		return automovil;
	}
	public void setAutomovil(AutomovilDto automovil) {
		this.automovil = automovil;
	}
	public List<PlanDto> getPlans() {
		return plans;
	}
	public void setPlans(List<PlanDto> plans) {
		this.plans = plans;
	}
	public List<SeguroDto> getSeguros() {
		return seguros;
	}
	public void setSeguros(List<SeguroDto> seguros) {
		this.seguros = seguros;
	}
	public List<ValeDto> getVales() {
		return vales;
	}
	public void setVales(List<ValeDto> vales) {
		this.vales = vales;
	}
	public List<BonoDto> getBonos() {
		return bonos;
	}
	public void setBonos(List<BonoDto> bonos) {
		this.bonos = bonos;
	}
	public Boolean getHayCambio() {
		return hayCambio;
	}
	public void setHayCambio(Boolean hayCambio) {
		this.hayCambio = hayCambio;
	}
	public String getNumeroHijos() {
		return numeroHijos;
	}
	public void setNumeroHijos(String numeroHijos) {
		this.numeroHijos = numeroHijos;
	}
}
