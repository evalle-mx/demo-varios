package net.dothr.expreg;

import java.util.Arrays;
import java.util.List;

public class TerminoDto {

	private String termino;
	private String indiceA;
	private String indiceB;
	private String eliminado;
	
	public TerminoDto() {
		this.termino="";
		this.indiceA="";
		this.indiceB="";
		this.eliminado="";
	}
	
	public TerminoDto(String linea){
		List<String> elems = Arrays.asList(linea.split("\\s*;\\s*"));
		if(elems.size()==2){
			this.termino=elems.get(0);
			this.indiceA=elems.get(1);
			this.indiceB="";
			this.eliminado="";
		}
		else if(elems.size()==3){
			this.termino=elems.get(0);
			this.indiceA=elems.get(1);
			this.indiceB=elems.get(2);	
			this.eliminado="";
		}
		else if(elems.size()==4){
			this.termino=elems.get(0);
			this.indiceA=elems.get(1);
			this.indiceB=elems.get(2);
			this.eliminado=elems.get(3);
		}
	}

	public String getTermino() {
		return termino;
	}

	public void setTermino(String termino) {
		this.termino = termino;
	}

	public String getIndiceA() {
		return indiceA;
	}

	public void setIndiceA(String indiceA) {
		this.indiceA = indiceA;
	}

	public String getIndiceB() {
		return indiceB;
	}

	public void setIndiceB(String indiceB) {
		this.indiceB = indiceB;
	}

	public String getEliminado() {
		return eliminado;
	}

	public void setEliminado(String eliminado) {
		this.eliminado = eliminado;
	}
}
