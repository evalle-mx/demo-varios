package net.dothr.expreg;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import utily.FileUtily;

public class CompareTerms {

	private final static int areaPrincipal = 21;	//8 /* Area que se va a comparar */
	private final static String DEPURADO_ROOT = "/home/dothr/Documents/TCE/semillas_cvs/";	
	
	private static Map<Integer, String> mapaAreas = new HashMap<Integer, String>(){
		private static final long serialVersionUID = 1L;
		{
			put(1,"Administracin");
			put(3,"Construccin_Urbanismo");
			put(4,"Calidad");
			put(7,"Compras_Abastecimiento_Suministro ");
			put(8,"Derecho");
			put(9,"Contabilidad_Finanzas");
			put(17,"Publicidad_Marketing");
			put(18,"Recursos_Humanos");
			put(21,"Tecnologias_Informacion");
			put(22,"Comercializacion_ventas");
    	}
	};
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		int areaComparar = 22;
		/* I. se comparan las listas de Términos (Originales) para generar un archivo de términos comunes 
		compareTermFile(areaPrincipal, areaComparar, DEPURADO_ROOT); //*/
		
		/* II. Se agrega resultado en el archivo DepuracionAreaX-B.xls en Pestaña original (para referencia)*/
		
		/* III. Del archivo de Comunes, se procesa para marcar los eliminados en ANteriores comparaciones con el AreaPrincipal
		comparaYaEliminados(areaComparar); //*/
		
		/* IV. Se copia el archivo generado en YaELiminados en la matriz DepuracionAreaX-B.xls 
		 * para descartar manualmente terminos (Filtrando los ya eliminados) */
		
		/* V. se genera un archivo DepuracionAreaX-B.csv y este se procesa para generar dos sublistas de terminos a eliminar 
		generaNuevosELiminados(areaComparar);//*/
	}
	
	/**
	 * Compara dos listas (de terminos) y genera un archivo con los que son comunes en ambos
	 * @param termsA
	 * @param termsB
	 * @return
	 */
	public static void compareTermFile(int iAreaA, int iAreaB, String pathTerms){
		
		if(iAreaA == iAreaB){
			System.out.println("NO ES POSIBLE COMPARAR "+mapaAreas.get(iAreaA) + " consigo misma");
		}else{
			File f = new File(DEPURADO_ROOT+"terminos/area"+iAreaA+".csv");
			if(f.exists())
			  {
				List<String> termsA = FileUtily.getLinesFile(DEPURADO_ROOT+"terminos/area"+iAreaA+".csv", null);
				f = new File(DEPURADO_ROOT+"terminos/area"+iAreaB+".csv");
				if(f.exists()){
					List<String> termsB = FileUtily.getLinesFile(DEPURADO_ROOT+"terminos/area"+iAreaB+".csv", null);
					
					System.out.println("terminosA: " + termsA.size() + " terminosB: " + termsB.size() );
					
					StringBuilder sbTermsC = new StringBuilder();
					Iterator<String> itTermsA = termsA.iterator();
					String lineaA;
					List<String> lsTerms, lsTermsB;
					String terminoA, incidA, terminoB, incidB;
					
					/* Header */
					sbTermsC.append("Termino")
					.append(";").append(mapaAreas.get(iAreaA))
					.append(";").append(mapaAreas.get(iAreaB))
					.append("\n");
					
					while(itTermsA.hasNext()){
						lineaA = itTermsA.next();
						System.out.println("lineaA: "+lineaA);
						lsTerms = Arrays.asList(lineaA.split("\\s*,\\s*"));
						terminoA = lsTerms.get(0);
						incidA = lsTerms.get(1);
						System.out.println(terminoA + "("+incidA+")");
						Iterator<String> itTermsB = termsB.iterator();
						
						while(itTermsB.hasNext()){
							lsTermsB = Arrays.asList(itTermsB.next().split("\\s*,\\s*"));
							terminoB = lsTermsB.get(0);
							incidB = lsTermsB.get(1);
							System.out.println("\t"+terminoB + "("+incidB+")");
							System.out.print(terminoA + "/" + terminoB);
							if(terminoA.toUpperCase().replace(" ", "").equals(terminoB.toUpperCase().replace(" ", ""))){
								//termsC.add(terminoA);
								System.out.println(" > coincide: "+ incidA + "/" + incidB);
								sbTermsC.append(terminoA)
								.append(";").append(incidA)
								.append(";").append(incidB)
								.append("\n");
//								System.out.println(" coincide: " +incidB );
							}
							else{
								System.out.println();
							}
						}
					}
					//System.out.println("Existen "+termsC.size()+" términos repetidos");
					String fileName = DEPURADO_ROOT+"depuracion/commons-"+iAreaA+"_"+iAreaB+".1.csv";
					FileUtily.writeStringInFile(fileName, sbTermsC.toString(), false);
					System.out.println("Se creo archivo de coincidencias: \n"+fileName);
				}else{
					System.out.println("No existe archivo (B): "+ DEPURADO_ROOT+"terminos/area"+iAreaB+".csv");
				}
			  }
			else{
				System.out.println("No existe archivo (A): "+ DEPURADO_ROOT+"terminos/area"+iAreaA+".csv");
			}
		}		
	}
	
	/**
	 * Hace el comparativo entre comunes con Area 8 y el listado de eliminados en anteriores comparaciones (EliminarX.txt)
	 * @param iAreaB
	 */
	protected static void comparaYaEliminados(int iAreaB){
		if(areaPrincipal == iAreaB){
			System.out.println("NO ES POSIBLE COMPARAR "+mapaAreas.get(iAreaB) + " consigo misma");
			return;
		}
		String fileDepurado = DEPURADO_ROOT+"depuracion/commons-"+areaPrincipal+"_"+iAreaB+".1.csv";
		String fileAreaEliminadosA =  DEPURADO_ROOT+"eliminar/EliminarArea"+areaPrincipal+".txt";
		String fileAreaEliminadosB =  DEPURADO_ROOT+"eliminar/EliminarArea"+iAreaB+".txt";
		
		File f = new File(fileDepurado);
		if(f.exists())
		  {
			StringBuilder sbOut = new StringBuilder().append("Termino")
					.append(";").append(mapaAreas.get(areaPrincipal))
					.append(";").append(mapaAreas.get(iAreaB))
					.append(";").append("Ya_Eliminado")
					.append("\n");
			List<String> lsDepurado = FileUtily.getLinesFile(fileDepurado);
			
			//A. formando lista de Objetos Termino
			if(lsDepurado.size()>0){
				String lineaDep;
				Iterator<TerminoDto> itTerminoDto;
				List<TerminoDto> lsTerminosDto = new ArrayList<TerminoDto>();
				Iterator<String> itLineaDepurado = lsDepurado.iterator();
				long contador =0;
				
				while(itLineaDepurado.hasNext()){
					lineaDep = itLineaDepurado.next();
					if(contador>0){//Omite encabezado de documento
						System.out.println("lineaDep: "+ lineaDep);
						lsTerminosDto.add(new TerminoDto(lineaDep));
					}
					contador++;
				}
				System.out.println("# de Dto's: "+ lsTerminosDto.size() );
				
				
				//B. Iterar Terminos vs eliminados Principal
				List<String> lsTermsYaElimA = FileUtily.getLinesFile(fileAreaEliminadosA);
				if(lsTermsYaElimA.size()>0){
					boolean encontrado = false;
					TerminoDto termDto;
					
					itTerminoDto = lsTerminosDto.iterator();										
					while(itTerminoDto.hasNext()){
						encontrado = false;
						termDto = itTerminoDto.next();						
						System.out.println("Se busca "+ termDto.getTermino() + " EN la lista eliminados de "+mapaAreas.get(areaPrincipal) +" (Principal");
						Iterator<String> itElimA = lsTermsYaElimA.iterator();
						String terminoElimA;
						while(itElimA.hasNext() && !encontrado){
							terminoElimA = itElimA.next();
							if(!terminoElimA.startsWith("/*")){ //OMITIR COMENTARIOS */			
								System.out.println(
//										"linea: "+lineaDep + "\n LsTDep: ("+lsTDep.size()+") "+(lsTDep.toString().replace(",",",\n")) +
										"\n Término: " + terminoElimA );
								if(terminoElimA.equals(termDto.getTermino())){
									System.out.println("Encontrado, eliminado en listaA, Break");
									termDto.setIndiceA("--");
									termDto.setEliminado("-1");
									encontrado=true;
								}
							}
						}
					}
				}
				//C. Iterar Terminos vs eliminados AreaB
				List<String> lsTermsYaElimB = FileUtily.getLinesFile(fileAreaEliminadosB);
				if(lsTermsYaElimB.size()>0){
					boolean encontrado = false;
					TerminoDto termDto;
					itTerminoDto = lsTerminosDto.iterator();										
					while(itTerminoDto.hasNext()){
						encontrado = false;
						termDto = itTerminoDto.next();						
						System.out.println("Se busca "+ termDto.getTermino() + " EN la lista eliminados de "+mapaAreas.get(iAreaB) +" ");
						Iterator<String> itElimB = lsTermsYaElimB.iterator();
						String terminoElimB;
						while(itElimB.hasNext() && !encontrado){
							terminoElimB = itElimB.next();
							if(!terminoElimB.startsWith("/*")){ //OMITIR COMENTARIOS */			
								System.out.println(
//										"linea: "+lineaDep + "\n LsTDep: ("+lsTDep.size()+") "+(lsTDep.toString().replace(",",",\n")) +
										"\n Término: " + terminoElimB );
								if(terminoElimB.equals(termDto.getTermino())){
									System.out.println("Encontrado, eliminado en listaB, Break");
									termDto.setIndiceB("--");
									termDto.setEliminado("-1");
									encontrado=true;
								}
							}
						}
					}
				}
				
				
				//D. Escribir en el Builder
				itTerminoDto = lsTerminosDto.iterator();
				TerminoDto termDtoFin;
				while(itTerminoDto.hasNext()){
					termDtoFin = itTerminoDto.next();
					sbOut.append(termDtoFin.getTermino())	//lsTDep.get(0);
					.append(";").append( termDtoFin.getIndiceA() )	//lsTDep.get(1);
					.append(";").append( termDtoFin.getIndiceB() )//lsTDep.get(2))//Encontrado en lista B
					.append(";").append( termDtoFin.getEliminado() )
					.append("\n");
				}				
				
				System.out.println("# terminos: " + lsTerminosDto.size() );
				String fileName = DEPURADO_ROOT+"depuracion/commons-"+areaPrincipal+"_"+iAreaB+".2.csv";
				FileUtily.writeStringInFile(fileName, sbOut.toString(), false);
				System.out.println("Se creo archivo de coincidencias 2: \n"+fileName);
			
			}else{
				System.out.println("No hay términos a procesar para " + mapaAreas.get(iAreaB));
			}
		  }
		else{
			System.out.println("NO existe el archivo solicitado: " + fileDepurado );
		}
	}
	
	/**
	 * Lee el archivo de Depuración (Evaluado por persona para descartar términos en AreaPrincipal [X] y AreaB)
	 * obtenido de archivo xls
	 * @param iAreaB
	 */
	protected static void generaNuevosELiminados(int iAreaB) {
		if(areaPrincipal == iAreaB){
			System.out.println("NO ES POSIBLE COMPARAR "+mapaAreas.get(iAreaB) + " consigo misma");
			return;
		}
		List<String> lsLineas;
		String cvsDepuracion = DEPURADO_ROOT+"depuracion/DepuracionArea"+areaPrincipal+"-"+iAreaB+".csv";
		File f = new File(cvsDepuracion);
		int iProcesado = 0;
		if(f.exists())
		  {
			StringBuilder sbDelPrinc = new StringBuilder("/* Vs ").append(mapaAreas.get(iAreaB)).append(" (").append(iAreaB).append(")  */"), 
					sbDelAreaB = new StringBuilder("/* Vs ").append(mapaAreas.get(areaPrincipal)).append(" (").append(areaPrincipal).append(")  */");
			lsLineas = FileUtily.getLinesFile(cvsDepuracion, null);
			if(!lsLineas.isEmpty()){
				String linea, termino;
				List<String> elems;
				Iterator<String> itLinea = lsLineas.iterator();
				while(itLinea.hasNext()){
					linea = itLinea.next();
					elems = Arrays.asList(linea.split("\\s*,\\s*"));
					termino = elems.get(0);
					// elems.get(1) => # AreaPrincipal/elimAnteriormente --
					// elems.get(2) => # Area B
					// elems.get(3) => Bandera eliminado anteriormente
					
					if(elems.size()==1){
						//Elimina en ambas listas
//						System.out.println("elems["+elems.size()+"]: "+elems.toString() +" "+ termino + " se Elimina en ambas listas");
						sbDelPrinc.append("\n").append(termino);
						sbDelAreaB.append("\n").append(termino);
					}
					else if(elems.size()==2){
						//Se conserva en Lista A, se elimina de Lista B
//						System.out.println("elems["+elems.size()+"]: "+elems.toString() +" "+termino + " se Elimina en lista AreaB "+ mapaAreas.get(iAreaB));
						sbDelAreaB.append("\n").append(termino);
					}
					else if(elems.size()==3){
						//1 [termino, ,Y] Se elimina en AreaPrincipal
						if(elems.get(1).equals("")){
//							System.out.println("elems["+elems.size()+"]: "+elems.toString() +" "+termino + " se Elimina en lista AreaPrincipal "+ mapaAreas.get(areaPrincipal));
							sbDelPrinc.append("\n").append(termino);
						}
						//2. [termino, X, Y] Se conserva en las dos
						else{
//							System.out.println("elems["+elems.size()+"]: "+elems.toString() +" "+" Se conserva en las dos listas");
						}
					}
					else{ // elems[4] (Eliminado anteriormente??)
						if(elems.get(3).equals("-1")){ //Eliminado anteriormente en ListaA o ListaB
							
							if(elems.get(1).equals("")){
//								System.out.println("elems["+elems.size()+"]: "+elems.toString() +" "+termino + " Ya fue eliminado en AreaB "+ mapaAreas.get(iAreaB) +" y se elimina de AreaPrincipal");
								sbDelAreaB.append("\n").append(termino);
							}
							else if(elems.get(2).equals("")){
//								System.out.println("elems["+elems.size()+"]: "+elems.toString() +" "+termino + " Ya fue eliminado en AreaPrincipal "+ mapaAreas.get(areaPrincipal) +" y se elimina de AreaB");
								sbDelPrinc.append("\n").append(termino);
							}
						}else{
							System.out.println("???"+ "elems["+elems.size()+"]: "+elems.toString() );
						}
					}
					System.out.println("elems["+elems.size()+"]: "+elems.toString() );
					iProcesado++;
				}
				FileUtily.writeStringInFile(DEPURADO_ROOT+"depuracion/_EliminarArea"+areaPrincipal+".txt", sbDelPrinc.toString(), false);
				FileUtily.writeStringInFile(DEPURADO_ROOT+"depuracion/_EliminarArea"+iAreaB+".txt", sbDelAreaB.toString(), false);
				System.out.println("Se procesaron "+iProcesado+" términos, y se crearon los archivos (temporales) de ELiminar");
				
			}
		  }
		else{
			System.out.println("No existe archivo de depuración "+cvsDepuracion+". \nEste se genera después de la depuración manual en el .xls");
		}
		
	}
	
	

}


