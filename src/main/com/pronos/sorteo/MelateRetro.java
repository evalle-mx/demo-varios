package com.pronos.sorteo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


import org.apache.log4j.Logger;

import com.pronos.core.CombCommon;
import com.pronos.util.PronosUtily;
import com.pronos.vo.CuadrilVo;
import com.pronos.vo.MelateVo;
import com.pronos.vo.NumeroSorteoVo;

public class MelateRetro {
	
	static Logger log4j = Logger.getLogger( MelateRetro.class);
	public static final String CVS_PATH ="/home/dothr/workspace/MyProjects/files/melate/Melate-Retro.csv";
	
	public static final String TXT_OUT_PATH ="/home/dothr/workspace/MyProjects/files/melate/Melate-Retro-Nuevos";
//	private static final String ALL_COMBS_PATH ="/home/dothr/workspace/MyProjects/files/melate/Melate-Retro-Todas.txt";
	
	protected static ArrayList<MelateVo> lsHistorico = null;
	protected static ArrayList<MelateVo> lsAll = null;
	public static final int numMaxMretro = 39;
	public static final int factIncidencia = 5;
	public static final int costoJuego = 10;
	
	/**
	 * Inicializa los valores para trabajar en los subsecuentes metodos
	 */
	protected static void setClassValues(){
		log4j.debug("<setClassValues> Inicializando valores ");
		lsAll = new ArrayList<MelateVo>();
		 
		 if(lsHistorico==null){
				lsHistorico = PronosUtily.getLsHistoricoMelate(CVS_PATH);
		  }
		 
		 lsAll.addAll(lsHistorico);
	}
	
	public static void main(String[] args) {
		testMethods();
	}
	
	/**
	 * MEtodo de pruebas
	 */
	public static void testMethods(){
		setClassValues();
		
		 
//		getCombinacion();
//		MelateVo vo = creaCombinaUnica();
		
		
		genMultiplesArchivo();
//		log4j.debug("VoFInal: " + creaCombinacionMascara().toText() );
//		 MelateVo vo = creaCombMezclandoDos(); 
				 //creaCombCuadril();
//		 log4j.debug("VO final: " + vo.toText() +"\n"+vo.toString());
		 
		 
//		 genMultiplesArchivo();
//		 test();
		 
//		 System.out.println("getVoInList(646): " + getVoInList(lsHistorico, 646).toString() );
		 
	}
	
	
	
	/**
	 * Obtiene la combinación por numero de Concurso
	 * @param lsCombs
	 * @param numCOncurso
	 * @return
	 */
	private static MelateVo getVoInList(List<MelateVo> lsCombs, int numCOncurso){
		Iterator<MelateVo> itList = lsCombs.iterator();
		boolean founded = false;
		MelateVo vo=null;
		while(itList.hasNext() && !founded){
			vo = itList.next();
			if(vo.getNumConcurso()==numCOncurso){
				founded=true;
			}
		}
		if(founded){
			return vo;
		}else{
			return null;
		}
	}
	
	/**
	 * Genera una lista de combinaciones y las exporta a un archivo de texto <br> 
	 * @param numGenerar
	 */
	public static void genMultiplesArchivo(){
		StringBuilder rp = new StringBuilder();
		StringBuilder sbCombs = new StringBuilder("/* Generadas ")
			.append(PronosUtily.todayFormated("YY/mm/dd HH:mm:ss"))
			.append(" [factor:").append(factIncidencia)
				.append("  */ \n");
		List<MelateVo> combinaciones1,combinaciones2,combinaciones3,combinaciones4;
		
		try {
			//1. 25 De tipo 1 (Unica)
			combinaciones1 = generaMultiples(20,1,3);	
			Iterator<MelateVo> itVo1 = combinaciones1.iterator();
			while(itVo1.hasNext()){
				sbCombs.append(itVo1.next().toText()).append("\n");
			}
			rp.append("Se agregaron ").append(combinaciones1.size()).append(" combinaciones tipo 1 \n");
			//2. 25 De tipo 2 (Mascara)
			combinaciones2 = generaMultiples(20,2,5);		
			Iterator<MelateVo> itVo2 = combinaciones2.iterator();
			while(itVo2.hasNext()){
				sbCombs.append(itVo2.next().toText()).append("\n");
			}
			rp.append("Se agregaron ").append(combinaciones2.size()).append(" combinaciones tipo 2 \n");
			//3. 25 De tipo 3 (Cuadril)
			combinaciones3 = generaMultiples(30,3,4);		
			Iterator<MelateVo> itVo3 = combinaciones3.iterator();
			while(itVo3.hasNext()){
				sbCombs.append(itVo3.next().toText()).append("\n");
			}
			rp.append("Se agregaron ").append(combinaciones3.size()).append(" combinaciones tipo 3 \n");
			//4. 25 De tipo 4 (Mix)
			combinaciones4 = generaMultiples(30,4,5);		
			Iterator<MelateVo> itVo4 = combinaciones4.iterator();
			while(itVo4.hasNext()){
				sbCombs.append(itVo4.next().toText()).append("\n");
			}
			rp.append("Se agregaron ").append(combinaciones4.size()).append(" combinaciones tipo 4 \n");
			//5. 6 (o 5) de tipo 0 (UNa al azar remplazando adicional)
			int rdm = PronosUtily.getAleatorio( (lsHistorico.size()-11)) +11;
			if(rdm>=lsHistorico.size()){rdm = lsHistorico.size()-1;}
			Iterator<MelateVo> itVo5 = creaReplace(getVoInList(lsHistorico, rdm)).iterator();
			int elems = 1;
			while(itVo5.hasNext()){
				sbCombs.append(itVo5.next().toText()).append("\n");
				elems++;
			}
			rp.append("Se agregaron ").append(elems).append(" combinaciones tipo 5 \n");
			String outPathF = TXT_OUT_PATH+PronosUtily.todayFormated("ddHHmm_ss")+".txt";
			PronosUtily.writeInFile(outPathF, sbCombs.toString(), true);
			log4j.debug("FIN DE PROCESO: " +
					"\n lsHistorico: " + lsHistorico.size() + "\n lsAll: "+ lsAll.size()+
					"\n Archivo: "+outPathF);
			log4j.debug(rp.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Genera un numero de combinaciones en una lista de VO's
	 * @param numGenerar
	 * @param tipo (Determina porque proceso se generan las combinaciones)
	 * @return
	 */
	public static List<MelateVo> generaMultiples(int numGenerar, int tipo, int restriccion){
		 List<MelateVo> lsNuevas = new ArrayList<MelateVo>();
		 if(lsHistorico!=null && lsAll!=null){
			 for(int x=0; x<numGenerar;x++){
				 MelateVo combVo;
				 boolean coincidente = true;
				 do{
					 if(tipo==1){
						 combVo =creaCombinaUnica(); 
					 }else if(tipo==2){
						 combVo =creaCombinacionMascara(); 
					 }else if(tipo==3){
						 combVo =creaCombCuadril(); 
					 }else {	//4
						 combVo =creaCombMezclandoDos();
					 }
					 coincidente = CombCommon.coincideCombinacion(combVo, lsNuevas, restriccion);	//AQUI es más restrictivo para aumentar el rango
				 }while(coincidente);						 
				 lsNuevas.add(combVo);
				 lsAll.add(combVo);
			 }
		 }
		 return lsNuevas;
	}
	
	
	
	
	/*  ************************* METODO PARA COMBINACION SEISRPLC [0] ****************** */
	/**
	 * Genera 6 combinaciones a partir de un original con el adicional en las F's secuencialmente
	 * 
	 * @param voOri
	 * @return
	 */
	protected static ArrayList<MelateVo> creaReplace(MelateVo voOri){
		ArrayList<MelateVo> lsAleatVo = new ArrayList<MelateVo>();
		Integer fAd = voOri.getfAd();
		
		//Si el VO no contiene adicional, se genera uno que no este dentro de las 6 F's
		if(fAd==null){
			fAd = getAleatOriginal(voOri);
		}
		
		//1. Reemplaza la f1
		MelateVo vo1 = new MelateVo(voOri.toList());
		setReplace(vo1, fAd, 1);
		vo1.reOrder();
		if(!CombCommon.coincideCombinacion(vo1, lsAll, 6)){
			lsAleatVo.add(vo1);
		}
		//F2
		MelateVo vo2 = new MelateVo(voOri.toList());
		setReplace(vo2, fAd, 2);
		vo2.reOrder();
		if(!CombCommon.coincideCombinacion(vo2, lsAll, 6)){
			lsAleatVo.add(vo2);
		}
		//F3
		MelateVo vo3 = new MelateVo(voOri.toList());
		setReplace(vo3, fAd, 3);
		vo3.reOrder();
		if(!CombCommon.coincideCombinacion(vo3, lsAll, 6)){
			lsAleatVo.add(vo3);
		}
		//F4
		MelateVo vo4 = new MelateVo(voOri.toList());
		setReplace(vo4, fAd, 4);
		vo4.reOrder();
		if(!CombCommon.coincideCombinacion(vo4, lsAll, 6)){
			lsAleatVo.add(vo4);
		}
		//F5
		MelateVo vo5 = new MelateVo(voOri.toList());
		setReplace(vo5, fAd, 5);
		vo5.reOrder();
		if(!CombCommon.coincideCombinacion(vo5, lsAll, 6)){
			lsAleatVo.add(vo5);
		}
		//F6
		MelateVo vo6 = new MelateVo(voOri.toList());
		setReplace(vo6, fAd, 6);
		vo6.reOrder();
		if(!CombCommon.coincideCombinacion(vo6, lsAll, 6)){
			lsAleatVo.add(vo6);
		}
		return lsAleatVo;
	}
	
	/**
	 * reemplaza dentro del Vo el adicional en la posición designada
	 * @param voNuevo
	 * @param fAd (adicional)
	 * @param pos (F a remplazar)
	 */
	protected static void setReplace(MelateVo voNuevo, Integer fAd, int pos){
		if(pos==1){
			voNuevo.setF1(fAd);
		}else if(pos==2){
			voNuevo.setF2(fAd);
		}else if(pos==3){
			voNuevo.setF4(fAd);
		}else if(pos==4){
			voNuevo.setF4(fAd);
		}else if(pos==5){
			voNuevo.setF5(fAd);
		}else {
			voNuevo.setF6(fAd);
		}
	}
	
	/**
	 * Obtiene un aleatorio que no exista en el objeto original
	 * @param voOri
	 * @return
	 */
	private static Integer getAleatOriginal(MelateVo voOri){
		Integer replAd;
		boolean existe = false;
		do{
			replAd = PronosUtily.getAleatorio(38);
			if(voOri.toList().contains(replAd)){
				existe = true;
			}
		}while(existe);
		return replAd;
	}
	
	
	/*  ************************* METODO PARA MIX-COMBINACION [-4] ****************** */
	
	/**
	 * @metodo [4]
	 * random a lista de combs, seleccionar 2 combinaciones e intercambiar 2 numeros al azar
	 * @return
	 */
	protected static MelateVo creaCombMezclandoDos(){
//		log4j.debug("<creaCombMezclandoDos> lsAll.size() " + lsAll.size());
		MelateVo combVo = new MelateVo(),comb1,comb2;
		boolean coincidente = true;
		try{
			int tamHistorico = lsHistorico.size();
			int mitad = tamHistorico/2;
			do{
				//Obtener 2 combinaciones aleatoriamente de historico (de la primera mitad)
				
				comb1 = lsHistorico.get(PronosUtily.getAleatorio(mitad));
				//La segunda comb se obtiene de la 2a Mitad
				int indxSegundo =  PronosUtily.getAleatorio(mitad) *2;
				if(indxSegundo>=lsHistorico.size()){
					indxSegundo = indxSegundo-1;
				}
				comb2 = lsHistorico.get(indxSegundo);
				
				//Tomar elementos 1,3,5 de Comb1
				combVo.setF1(comb1.getF1());
				combVo.setF2(comb1.getF3());
				combVo.setF3(comb1.getF5());
				
				//Tomar elementos 2,4,6 de Comb2
				combVo.setF4(comb2.getF2());
				combVo.setF5(comb2.getF4());
				combVo.setF6(comb2.getF6());
					
				combVo.reOrder();
				
				coincidente = CombCommon.coincideCombinacion(combVo, lsAll, factIncidencia);
				
			}while(coincidente || !combVo.noDuplicados());
		}catch (Exception e){
			log4j.fatal("<Excepcion> error en el proceso: ", e);
		}
		combVo.setNumConcurso(-4);
		return combVo;
	}	
	
	/* *************************  METODO PARA METODO CUADRILES [-3] ************************* */
	/**
	 * @metodo [3]
	 * Crea un Vo a partir de cuadriles probabilisticos (2 numeros de baja, 2 de media, 2 de alta)
	 * @return
	 */
	protected static MelateVo creaCombCuadril(){
//		log4j.debug("<creaCombCuadril> lsAll.size() " + lsAll.size());
		MelateVo combVo = new MelateVo();
		CuadrilVo cuadrilVO = calculaCuadril(lsHistorico);
		boolean coincidente = true;
		do{
			//Se asignan dos valores de cuadril uno (Baja)
			int[] arreglo = {-1,-2,-3,-4,-5,-6};
			arreglo[0] = cuadrilVO.getCuad1()[ PronosUtily.getAleatorio(cuadrilVO.getCuad1().length-1) ];
			Integer tempInt;
			do{
				tempInt = cuadrilVO.getCuad1()[ PronosUtily.getAleatorio(cuadrilVO.getCuad1().length-1) ];
			}while(arreglo[0]==tempInt);	//Combinacion.unicInArray(arreglo, baja2, arreglo.length));
			arreglo[1] = tempInt;
			
			//Se asignan dos valores del cuadril dos (mediaBaja)
			arreglo[2] = cuadrilVO.getCuad2()[ PronosUtily.getAleatorio(cuadrilVO.getCuad2().length-1) ];
			do{
				tempInt = cuadrilVO.getCuad2()[ PronosUtily.getAleatorio(cuadrilVO.getCuad2().length-1) ];
			}while(arreglo[2]==tempInt);
			arreglo[3] = tempInt;
			
			//Se asignan dos valores del cuadril tres (mediaAlta)
			arreglo[4] = cuadrilVO.getCuad3()[ PronosUtily.getAleatorio(cuadrilVO.getCuad3().length-1) ];
			do{
				tempInt = cuadrilVO.getCuad3()[ PronosUtily.getAleatorio(cuadrilVO.getCuad3().length-1) ];
			}while(arreglo[4]==tempInt);
			arreglo[5] = tempInt;
			
			combVo.setF1( arreglo[0] );
			combVo.setF2( arreglo[1] );
			combVo.setF3( arreglo[2] );
			combVo.setF4( arreglo[3] );
			combVo.setF5( arreglo[4] );
			combVo.setF6( arreglo[5] );
			
			coincidente = CombCommon.coincideCombinacion(combVo, lsAll, factIncidencia);
			
		}while(coincidente);
		combVo.setNumConcurso(new Integer("-3"));
		return combVo;
	}
	
	/**
	 * Hace una sumatoria de incidencias de cada numero elegible 
	 * y los divide en 3 (39/3) cuadriles dependiendo su frecuencia (Baja, media, alta)
	 * @param lsCombs
	 * @return
	 */
	private static CuadrilVo calculaCuadril(List<MelateVo> lsCombs){
		CuadrilVo cuadril = new CuadrilVo();		
		try{
			List<NumeroSorteoVo> lsNums = PronosUtily.getFrecuenciaMRetro(lsCombs);
			Integer[] baja = new Integer[13];
			Integer[] media = new Integer[13];
			Integer[] alta = new Integer[13];
			int indx = 0;
			
			int x=0;
			for(x=0;x<13;x++){
				baja[indx]=lsNums.get(x).getNumero();
				indx++;
			}
			indx = 0;
			for(x=13;x<26;x++){
				media[indx]=lsNums.get(x).getNumero();
				indx++;
			}
			indx = 0;
			for(x=26;x<lsNums.size();x++){ //Size debe ser 39
				alta[indx]=lsNums.get(x).getNumero();
				indx++;
			}
			
			cuadril.setCuad1(baja);
			cuadril.setCuad2(media);
			cuadril.setCuad3(alta);
			
		}catch (Exception e){
			log4j.fatal("Error: ", e);
		}
		
		return cuadril;
	}
	
	
	/* *************************  METODO PARA METODO MASCARA [-2] ************************* */
	/**
	 * @metodo [2]
	 * Genera un numero de combinaciones unicas en una lista de VO's
	 * <b>FORMA DE GENERACIÓN 2, "MASCARA"</b>
	 * @return
	 */
	protected static MelateVo creaCombinacionMascara(){
		log4j.debug("<creaCombinacionMascara> lsAll.size() " + lsAll.size());
		MelateVo combVo = new MelateVo();
		try{
//			log4j.debug("lsHistorico.size() " + lsHistorico.size());
			/* genera indices a contrastar para generar mascara */
			int base = PronosUtily.getAleatorio(lsHistorico.size()-1);
			int mask = PronosUtily.getAleatorio(lsHistorico.size()-1);
			while(base == mask){
				mask = PronosUtily.getAleatorio(lsHistorico.size());				
			}
			log4j.debug("base: " + base + ", mask: "+ mask );
			//Obtiene combinaciones a comparar
			MelateVo baseVo = lsHistorico.get(base);
			MelateVo maskVo = lsHistorico.get(mask);

			log4j.debug("baseVo("+base+"): " + baseVo.toText() );
			log4j.debug("maskVo("+mask+"): " + maskVo.toText() );
			
			int[] difs = new int[6];
			difs[0]= baseVo.getF1()-maskVo.getF1();
			difs[1]= baseVo.getF2()-maskVo.getF2();
			difs[2]= baseVo.getF3()-maskVo.getF3();
			difs[3]= baseVo.getF4()-maskVo.getF4();
			difs[4]= baseVo.getF5()-maskVo.getF5();
			difs[5]= baseVo.getF6()-maskVo.getF6();
			String diferen = "";
			for(int x =0;x<difs.length;x++){
				diferen += difs[x]+",";	
			}
			log4j.debug("diferencia: " + diferen);
						
			combVo = getVoFromMask(difs);
			combVo.setNumConcurso(new Integer("-2"));
		}catch (Exception e){
			log4j.fatal("error", e);
		}
		return combVo;
	}
	
	/**
	 * Genera el nuevo VO obtenido con la diferencia aplicada a 3er Vo
	 * @param difs
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private static MelateVo getVoFromMask(int[] difs) throws Exception {
		MelateVo tempoVo, combVo;
		boolean hayNegativo = false, esCoincidente=false;
		int indice = 0;
		ArrayList<MelateVo> lsFuente = (ArrayList<MelateVo>)lsHistorico.clone();
		do{
			hayNegativo = false;
//			log4j.debug("lsFuente.size(): " + lsFuente.size() );
			indice = PronosUtily.getAleatorio(lsFuente.size()-1);
			tempoVo = lsFuente.get( indice );
			//log4j.debug("Vo a aplicar Mascara: " + tempoVo.toText() );
			combVo = getVoMasked(difs,tempoVo);
			if(combVo.getF1()<1 || combVo.getF2()<1 || combVo.getF3()<1 || combVo.getF4()<1 || combVo.getF5()<1 || combVo.getF6()<1 ){
				hayNegativo=true; 
			}
					
			if(hayNegativo){
				log4j.debug("Se recalcula porque tiene negativos: " + combVo.toText() );
				log4j.debug("Se elimina el tempoVo de fuente");
				lsFuente.remove(indice);
			}else{
				if(combVo.noDuplicados()){
					combVo.reOrder();
					esCoincidente = CombCommon.coincideCombinacion(combVo, lsAll, factIncidencia);
				}
			}
		}while(hayNegativo || !combVo.noDuplicados() || esCoincidente);
		log4j.debug("Vo a aplicar Mascara: " + tempoVo.toText() );
		log4j.debug("combVo: " + combVo.toText() ); 
		
		return combVo;
	}
	
	/**
	 * Realiza el set de VO mascara + diferencia
	 * @param difs
	 * @param maskVo
	 * @return
	 */
	private static MelateVo getVoMasked(int[] difs, MelateVo maskVo){
		MelateVo combVo=new MelateVo();
		combVo.setF1(maskVo.getF1()+difs[0]);
		combVo.setF2(maskVo.getF2()+difs[1]);
		combVo.setF3(maskVo.getF3()+difs[2]);
		combVo.setF4(maskVo.getF4()+difs[3]);
		combVo.setF5(maskVo.getF5()+difs[4]);
		combVo.setF6(maskVo.getF6()+difs[5]);
		
		return combVo;
	}
	
	/* *************************  METODO PARA METODO SIMPLE (UNICAS [-1]) ************************* */
	/**
	 * @metodo [1]
	 * Genera una combinación unica comparandola contra el listadoHIstorico (archivo.csv)
	 * <b>FORMA DE GENERACIÓN 1, "UNICA"</b>
	 * @return
	 */
	protected static MelateVo creaCombinaUnica(){
		boolean coincide = true;
//		log4j.debug("<creaCombinaUnica> lsAll.size() " + lsAll.size());
		MelateVo combVo;
		do{  /* Mientras coincida con el factor, se genera nueva combinacion */
			combVo = getCombinacion();			
			coincide = CombCommon.coincideCombinacion(combVo, lsAll, factIncidencia);
		}while(coincide);
		combVo.setNumConcurso(new Integer("-1"));
		log4j.debug("generada: " + combVo.toList());
		return combVo;
	}
	
	/**
	 * Metodo simple para crear una combinación aleatoria de melate retro
	 */
	protected static MelateVo getCombinacion(){
//		log4j.debug("<getCombinacion>");
		return CombCommon.genMelateVo(numMaxMretro);
	}
	
	
	
	
	
}
