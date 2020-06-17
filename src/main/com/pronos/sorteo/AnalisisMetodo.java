package com.pronos.sorteo;

import java.util.ArrayList;

import com.pronos.core.CombCommon;
import com.pronos.util.PronosUtily;
import com.pronos.vo.MelateVo;

public class AnalisisMetodo extends MelateRetro {
	
	
	

	/**
	 * @param args
	 */
	public static void main(String[] args) {	
		
		
		generaHastaGanador("4,8,18,25,27,28");

	}
	
	
	/**
	 * Realiza una PRUEBA para determinar el numero de iteraciones que realiza la aplicación
	 * hasta llegar a la combinación ganadora
	 * @param txtGanador
	 */
	protected static void generaHastaGanador(String cadenaGanador){
		setClassValues();
		
		
		boolean primerPremio = false;
		Long indiceGenerado = new Long(1);
		MelateVo ganVo = new MelateVo(cadenaGanador);
		int similitud = -1;
		do{
			MelateVo vo = creaCombMezclandoDos();  // +de 11740 sin llegar a ganador
//					creaCombCuadril();	// + de 14,100 sin llegar a ganador
					//creaCombinacionMascara(); // Se cicla (Usa Historico) y no llega al resultado
					//creaCombinaUnica(); //+ de 24,300 sin llegar a ganador
			lsAll.add(vo);
			similitud = CombCommon.getSimilitud(ganVo.toList(), vo.toList());
			if(indiceGenerado%10==0){
				log4j.debug("combinaciones generadas: " + indiceGenerado + " generados, ( " +
						PronosUtily.toCurrency(indiceGenerado*costoJuego) + ") ");
			}
			if(lsAll.size()%100==0){
				log4j.debug("lsAll.size " + lsAll.size() );
			}
			if(similitud==5){	//iguales
				primerPremio = true;
			}
			indiceGenerado++;
		}while(!primerPremio);
		log4j.debug("Se llego a ganador después de: " + indiceGenerado);
	}

}
