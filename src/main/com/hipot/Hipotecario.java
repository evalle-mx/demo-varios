package com.hipot;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import utily.FileUtily;

public class Hipotecario extends DemoCalcHipot {

	private final static double C_COMISION = 290.00;	// Comision: 250+40
	private final static double C_TASA = 11.04688379;
	/**
	 * @param args
	 */
	public static void main(String[] args) {
//		pagoReduceD();

//		calculoOriginal("HipotecarioInicial"); //ok
//		calculoOct13("HipotPorPagos-Ad1", new DatosInicialesDto(1)); //ok
		
//		calculoDic16(); //ok
		
//		calculoNov17();
		
//		demoInteres(); //En clase padre
		
//		calculo_nPagos("HipotPorPagos-X", new DatosInicialesDto(2)); // nPago siguiente a adelanto y saldo de nPago 
		
//		testDecrem();		
//		sumaCantidades();
//		supuesto();
//		restador();
		calculoFeb18();
	}
	
	
	
	
	/**
	 * Método de calculo empleado en el adelanto realizado en DIciembre 13 de 2016
	 */
	public static void calculoDic16(){
		double seguro = 183.19, segDecrem=0.43, segVida=0.0;
		double factor = 10.63, incremento = 0.08;
		double segDano=127.14, segCasa=262.70;
		double comision = C_COMISION;
		double tasa = 1301.69; //;C_TASA;
		
		StringBuilder sb = new StringBuilder("Intereses;Capital;SeguroVida;SeguroDaños;Comision;Pago Hipotec;SegCasa;PagoTotal;Saldo\n");
		double capital=1223.14; //Ultimo pago Interes - Capital
		double saldo = 147286.86;	//Ultimo saldo 
		int segmento = 5;	//Linea/pago con respecto a año de Prestamo (Año Siete)
		int nPago = 77;		//Número de pago a partir (ultimo+1)
		int mes=12, anio=16;  //Mes y año del calendario
		
		int salto = 0;
		 
		
		double pagoHipot=0.0, pagoTotal=0.0;

		
		do{
			if(salto==2) {						
				segDecrem = rounded(segDecrem+0.01);
				salto=0;
			}
			seguro = rounded(seguro-segDecrem);
			segVida = rounded(seguro-segDano);
			factor = factor+incremento;
			tasa = rounded(tasa-(factor));
        	capital = rounded(capital+factor);
        	
        	pagoHipot = rounded(tasa+capital+seguro+comision);
        	saldo = rounded(saldo-capital);
        	pagoTotal = rounded(pagoHipot+segCasa);
        	if(segmento==6){
        		anio++;
        		mes=1;
        	}
        	//System.out.println(interes + ";"+capital+";"+saldo);
        	//Intereses;Capital;SeguroVida;SeguroDaños;Comision;Pago Hipotec;SegCasa;PagoTotal;Saldo
        	sb.append(mes).append("/").append(anio).append(";").append(nPago).append(";")
        	.append(tasa).append(";").append(capital).append(";").append(segVida).append(";").append(segDano).append(";")
        	.append(comision).append(";").append(pagoHipot).append(";").append(segCasa).append(";").append(pagoTotal).append(";")
        	.append(saldo).append("\n").append(segmento==12?"\n":"");
        	if(segmento==12){
        		segmento=1;
        	}else{
        		segmento++;
        	}
        	mes++;
        	nPago++;
		}while(saldo>0 );
		System.out.println(sb.toString());
	}
	
	
	/**
	 * Método de calculo empleado en el adelanto realizado en DIciembre 13 de 2016
	 */
	/*public static void calculoNov17(){
		StringBuilder sb = new StringBuilder("Intereses;Capital;SeguroVida;SeguroDaños;Comision;Pago Hipotec;SegCasa;PagoTotal;Saldo\n");
		//Iniciales
		double saldo = 78406.02;	//Saldo Pendiente
		double interesLast = 1178.38, capitalLast= 1346.45; //Ultimo pago Interes - Capital
		
		//Constantes en todo el Plazo	
		double segurosPago = 185.65, segDecrem=0.43;
		double fInteres = 10.63, incremento = 0.08;
		
		//a Calcular
		double segVida=0.0,pagoHipot=0.0, pagoTotal=0.0, intMensual=0.0;
		
		
		//Formato documento:		 
		int segmento = 4;	//Linea/pago con respecto a año de Prestamo (Año Siete)
		int nPago = 88;		//Número de pago a partir (ultimo+1)
		int nPagoFin = 159;		
		int mes=11, anio=17;  //Mes y año del calendario
		
		
		 		
		do{
			segurosPago = rounded(segurosPago-segDecrem);
			segVida = rounded(segurosPago-segDano);
			
			/*
			 si tenemos un tipo de interés del 10% anual y lo queremos pasar a mensual:
			  I12 = ((1+0,1)(1/12))-1
			 *
			intMensual = ((1.0 +0.1063)(1/12))-1;
			
			factor = factor+incremento;
        	interes = rounded(interes-(factor));
        	capital = rounded(capital+factor);
        	
        	pagoHipot = rounded(interes+capital+seguro+comision);
        	saldo = rounded(saldo-capital);
        	pagoTotal = rounded(pagoHipot+segCasa);
        	if(segmento==6){
        		anio++;
        		mes=1;
        	}
        	//System.out.println(interes + ";"+capital+";"+saldo);
        	//Intereses;Capital;SeguroVida;SeguroDaños;Comision;Pago Hipotec;SegCasa;PagoTotal;Saldo
        	sb.append(mes).append("/").append(anio).append(";").append(nPago).append(";")
        	.append(interes).append(";").append(capital).append(";").append(segVida).append(";").append(segDano).append(";")
        	.append(comision).append(";").append(pagoHipot).append(";").append(segCasa).append(";").append(pagoTotal).append(";")
        	.append(saldo).append("\n").append(segmento==12?"\n":"");
        	if(segmento==12){
        		segmento=1;
        	}else{
        		segmento++;
        	}
        	mes++;
        	nPago++;
		}while(saldo>0 );
		System.out.println(sb.toString());
	} //*/
	
	
	
	public static void calculo_nPagos(String fileName, DatosInicialesDto inicialDto){
		StringBuilder sb = new StringBuilder(), sbCsv = new StringBuilder("#pago;PagoIntereses;PagoCapital;SeguroVida;SeguroDaños;Comision;PagoHip;SeguroCasa;PagoTotal;SaldoCredito\n");

		double comision = C_COMISION;
		double tasa = inicialDto.getTasa();//C_TASA; //11.04688379  [14.695]		
		double cuotaMen = inicialDto.getCuotaMen();//4673.79;	//Varia
		double segDanos = inicialDto.getSegDanos();	//127.14;	//Varia (123.15);
		double segCasaI = inicialDto.getSegCasaI();	//243.47;
		double factorVida = inicialDto.getFactorVida();	//.000258659;//Varia (.000233),00035533
		int anioIni = inicialDto.getAnioIni();
		
		double restante = inicialDto.getPrestamo();	//prestamo;
		int renglon = inicialDto.getRenglon()!=null?inicialDto.getRenglon():0;
		double iAnual = tasa/100;
		
		//por calcular y temporal
		Double iMensual;
		Double tmp1, tmp2, tmp3;
		
		Double pagoInteres;
		Double pagoCapital;
		Double segVida;	//a partir de pago 48	seguroVida = Restante*factorVida
		Double segCasa;	//segCasa = 243.47 a partir de pago 37
		Double pagoHip;
		Double pagoTotal;
		
		/* Formula:   I12 = ((1+TASA)ʌ(1/12))-1 */
		tmp1 = 1+(iAnual);	//System.out.println("tmp1: "+ tmp1); //1.05
		tmp2 = 1.0/12.0;	//System.out.println("tmp2: "+ tmp2); //0.083333333
		tmp3 = Math.pow(tmp1, tmp2);	//System.out.println("tmp3: "+ tmp3);	//1.004074124
		iMensual = tmp3-1;	System.out.println("int12: " + iMensual );	//0.004074124
		
		sb.append("\nMonto Prestamo: ").append( currency(inicialDto.getPrestamo()) ).append(", Tasa Anual: ").append(tasa)
//		.append(", Años para pagar: ").append(anios )
		.append(" Cuota Mensual: ").append(cuotaMen).append("\n++++++++++++++++++").append("\n");
		
		segCasa = 0.0;
		int nPago=inicialDto.getnPagoIni()+1;
		do{
			pagoInteres = iMensual*restante;
			if(nPago>47){
				segVida = restante*factorVida;
			}else{
				segVida = 0.0;
			}
			if(restante>cuotaMen){
				pagoCapital = cuotaMen-pagoInteres;
				restante = restante-pagoCapital;
			}else{
				pagoCapital = restante;
				restante = 0;
				cuotaMen = pagoCapital+pagoInteres;
				
			}
			
			pagoHip = pagoInteres+pagoCapital+segVida+segDanos+comision;
			
			if(nPago>36){
				segCasa = segCasaI; //243.47;
			}
			pagoTotal = pagoHip+segCasa;
			
			
			//* Imprimir en pantalla:
//			if(nPago==66){ System.out.println("66\t3,659.80");
			if(nPago>79 && nPago<88){
				//System.out.println(nPago + ">"+currency(segVida));
				System.out.println(nPago+"\t"+currency(pagoInteres)
						+"\t"+currency(pagoCapital)
						+"\t"+currency(segVida)+"\t"+currency(segDanos)
						+"\t"+currency(comision)+"\t"+currency(pagoHip)
						+"\t"+currency(segCasa)+"\t"+currency(pagoTotal)
						+"\t"+currency(restante)
						);
			}//*/
			
			
			/* Formato impresion */
			sb.append("[").append(nPago).append("] ")
//			.append("Cuota= $ ").append(currency(cuotaMen)).append("\tP-Interes= $ ").append(currency(pagoInteres))
//			.append("\tP-Capital= $ ").append(currency(pagoCapital)).append("\tSegVida= $ ")
//			.append(currency(segVida)).append("\tSegDaños= $ ").append(currency(segDanos))
//			.append("\tComision= $ ").append(currency(comision)).append("\tpagoHip= $ ").append(currency(pagoHip)).append("\tSegCasa= $ ").append(currency(segCasa)).append("\tPagoTotal= $ ").append(currency(pagoTotal)).append("\tRestante= $ ").append(currency(restante))
			
			.append("\t").append(currency(pagoInteres))
			.append("\t").append(currency(pagoCapital)).append("\t")
			.append(currency(segVida)).append("\t").append(currency(segDanos))
			.append("\t").append(currency(comision)).append("\t").append(currency(pagoHip)).append("\t").append(currency(segCasa)).append("\t").append(currency(pagoTotal)).append("\t").append(currency(restante))
			.append("\n");
			
			/* Formato CSV */	

			if(renglon == 0){
				sbCsv.append("Año;")
				.append(anioIni++)
				.append("\n");
			}
			renglon++;
			sbCsv.append(nPago)
//			.append(";").append(currency(cuotaMen))
			.append(";").append(currency(pagoInteres)).append(";").append(currency(pagoCapital)).append(";").append(currency(segVida))
			.append(";").append(currency(segDanos)).append(";").append(currency(comision)).append(";").append(currency(pagoHip)).append(";").append(currency(segCasa))
			.append(";").append(currency(pagoTotal)).append(";").append(currency(restante)).append("\n");
			if(renglon==12){				
				renglon = 0;
			}
			nPago++;
		}while(restante>0);
		
//		System.out.println(sb.toString());
		FileUtily.writeStringInFile("/home/dothr/workspace/MyProjects/files/salida/"+fileName+".csv", sbCsv.toString(), false);
	}
	
	
	/**
	 * Muestra esquema original de pagos Hipotecario
	 * @param fileName
	 */
	public static void calculoOriginal(String fileName){
		StringBuilder sb = new StringBuilder(), sbCsv = new StringBuilder("#pago;PagoIntereses;PagoCapital;SeguroVida;SeguroDaños;Comision;PagoHip;SeguroCasa;PagoTotal;SaldoCredito\n");

		double comision = C_COMISION;
		double tasa = C_TASA;
		
		double prestamo = 519792.00, cuotaMen = 5197.92;
		int anios = 20;
		double segDanos = 123.15, factorVida = .000233, segCasa = 0.0;	//segCasa = 243.47 a partir de pago 37, seguroVida = Restante*factorVida		
		double restante = prestamo;
		double iAnual = tasa/100;
		
		//por calcular y temporal
		Double iMensual;
		Double tmp1, tmp2, tmp3;
		
		Double pagoInteres;
		Double pagoCapital;
		Double segVida;	//a partir de pago 48		
		Double pagoHip;
		Double pagoTotal;
		
		int nPago = 1;
		
//		System.out.println("iAnual: " + iAnual);
		/* Formula:   I12 = ((1+0.05)ʌ(1/12))-1 */
		tmp1 = 1+(iAnual);	
//		System.out.println("tmp1: "+ tmp1); //1.05
		tmp2 = 1.0/12.0;
//		System.out.println("tmp2: "+ tmp2); //0.083333333
		tmp3 = Math.pow(tmp1, tmp2);
//		System.out.println("tmp3: "+ tmp3);	//1.004074124
		iMensual = tmp3-1;
//		System.out.println("int12: " + iMensual );	//0.004074124
		
		
		sb.append("\nMonto Prestamo: ").append( currency(prestamo) )
		.append(", Tasa Anual: ").append(tasa)
		.append(", Años para pagar: ").append(anios )
		.append(" Cuota Mensual: ").append(cuotaMen)
		.append("\n++++++++++++++++++").append("\n");
		
		//System.out.println("\n######## "+tasa+"");
		for(int y=0; y<anios; y++){
			sb.append("Año ").append(y+1).append("  \n");
			sbCsv.append("Año;").append(y+1).append("\n");
				for(int x=0; x<12; x++){
					pagoInteres = iMensual*restante;
					if(nPago>47){
						segVida = restante*factorVida;
					}else{
						segVida = 0.0;
					}
					
					if(restante>cuotaMen){
						pagoCapital = cuotaMen-pagoInteres;
						restante = restante-pagoCapital;
					}else{
						pagoCapital = restante;
						restante = 0;
						cuotaMen = pagoCapital+pagoInteres;
					}
					
					pagoHip = pagoInteres+pagoCapital+segVida+segDanos+comision;
					
					if(nPago>36){
						segCasa = 243.47;
					}
					pagoTotal = pagoHip+segCasa;
					
					/*if(nPagos>2 && nPagos<8){
						System.out.println(nPagos+"\t"+currency(cuotaMen)+"\t"+currency(pagoInteres)+"\t"+currency(pagoCapital) +"\t"+currency(restante));
					}
					if(nPagos==37 || nPagos==96){
						System.out.println(nPagos+"\t"+currency(cuotaMen)+"\t"+currency(pagoInteres)+"\t"+currency(pagoCapital) +"\t"+currency(restante));
					}*/
					
					
					/* Formato impresion */
					sb.append("[").append(nPago).append("] ")
					.append("Cuota= $ ").append(currency(cuotaMen))
					.append("\tP-Interes= $ ").append(currency(pagoInteres))
					.append("\tP-Capital= $ ").append(currency(pagoCapital))
					.append("\tSegVida= $ ").append(currency(segVida))
					.append("\tSegDaños= $ ").append(currency(segDanos))
					.append("\tComision= $ ").append(currency(comision))
					.append("\tpagoHip= $ ").append(currency(pagoHip))
					.append("\tSegCasa= $ ").append(currency(segCasa))
					.append("\tPagoTotal= $ ").append(currency(pagoTotal))
					.append("\tRestante= $ ").append(currency(restante))
					.append("\n");
					
					/* Formato CSV */
					sbCsv.append(nPago)
//					.append(";").append(currency(cuotaMen))
					.append(";").append(currency(pagoInteres))
					.append(";").append(currency(pagoCapital))
					.append(";").append(currency(segVida))
					.append(";").append(currency(segDanos))
					.append(";").append(currency(comision))
					.append(";").append(currency(pagoHip))
					.append(";").append(currency(segCasa))
					.append(";").append(currency(pagoTotal))
					.append(";").append(currency(restante))
					/*.append(";").append(pagoInteres) //Sin redondeo
					.append(";").append(pagoCapital)
					.append(";").append(restante)*/
					.append("\n");
					
					nPago++;
				}
		}		
		
		System.out.println(sb.toString());
		FileUtily.writeStringInFile("/home/dothr/workspace/MyProjects/files/salida/"+fileName+".csv", sbCsv.toString(), false);
	}
	
	/**
	 * Genera tabla Amortización con Maximo de pagos fijo (nPagoFin)
	 * @param fileName
	 * @param inicialDto
	 */
	public static void calculoOct13(String fileName, DatosInicialesDto inicialDto){
		StringBuilder sb = new StringBuilder(), sbCsv = new StringBuilder("#pago;PagoIntereses;PagoCapital;SeguroVida;SeguroDaños;Comision;PagoHip;SeguroCasa;PagoTotal;SaldoCredito\n");

		double comision = C_COMISION;
		double tasa = C_TASA;
		double cuotaMen = inicialDto.getCuotaMen();//4673.79;	//Varia
		double segDanos = inicialDto.getSegDanos();	//127.14;	//Varia (123.15);
		double segCasaI = inicialDto.getSegCasaI();	//243.47;
		double factorVida = inicialDto.getFactorVida();	//.000258659;//Varia (.000233),00035533
		int anioIni = inicialDto.getAnioIni();
		
		double restante = inicialDto.getPrestamo();	//prestamo;
		int renglon = inicialDto.getRenglon()!=null?inicialDto.getRenglon():0;		
		double iAnual = tasa/100;
		
		//por calcular y temporal
		Double iMensual;
		Double tmp1, tmp2, tmp3;
		
		Double pagoInteres;
		Double pagoCapital;
		Double segVida;	//a partir de pago 48	seguroVida = Restante*factorVida
		Double segCasa;	//segCasa = 243.47 a partir de pago 37
		Double pagoHip;
		Double pagoTotal;

		/* Formula:   I12 = ((1+0.05)ʌ(1/12))-1 */
		tmp1 = 1+(iAnual);	//System.out.println("tmp1: "+ tmp1); //1.05
		tmp2 = 1.0/12.0;	//System.out.println("tmp2: "+ tmp2); //0.083333333
		tmp3 = Math.pow(tmp1, tmp2);	//System.out.println("tmp3: "+ tmp3);	//1.004074124
		iMensual = tmp3-1;	// System.out.println("int12: " + iMensual );	//0.004074124
		sb.append("\nMonto Prestamo: ").append( currency(inicialDto.getPrestamo()) ).append(", Tasa Anual: ").append(tasa)
//		.append(", Años para pagar: ").append(anios )
		.append(" Cuota Mensual: ").append(cuotaMen).append("\n++++++++++++++++++").append("\n");
		
		segCasa = 0.0;
		/* Se usa for porque esta definido el numero de pagos (240) */
		for(int nPago=inicialDto.getnPagoIni()+1; nPago<=inicialDto.getnPagoFin(); nPago++ ){
			pagoInteres = iMensual*restante;
			if(nPago>47){
				segVida = restante*factorVida;
			}else{
				segVida = 0.0;
			}
			if(restante>cuotaMen){
				pagoCapital = cuotaMen-pagoInteres;
				restante = restante-pagoCapital;
			}else{
				pagoCapital = restante;
				restante = 0;
				cuotaMen = pagoCapital+pagoInteres;
			}
			pagoHip = pagoInteres+pagoCapital+segVida+segDanos+comision;
			if(nPago>36){
				segCasa = segCasaI;
			}
			pagoTotal = pagoHip+segCasa;
			
			/* Imprimir en pantalla:
			if(nPago>50 && nPago<61){
				System.out.println(nPago + ">"+currency(segVida));
			} //*/
			
			/* Formato impresion */
			sb.append("[").append(nPago).append("] ")
			.append("Cuota= $ ").append(currency(cuotaMen)).append("\tP-Interes= $ ").append(currency(pagoInteres))
			.append("\tP-Capital= $ ").append(currency(pagoCapital)).append("\tSegVida= $ ")
			.append(currency(segVida)).append("\tSegDaños= $ ").append(currency(segDanos))
			.append("\tComision= $ ").append(currency(comision)).append("\tpagoHip= $ ").append(currency(pagoHip)).append("\tSegCasa= $ ").append(currency(segCasa)).append("\tPagoTotal= $ ").append(currency(pagoTotal)).append("\tRestante= $ ").append(currency(restante))
			.append("\n");
			
			/* Formato CSV */
			if(renglon == 0){
				sbCsv.append("Año;").append(anioIni++).append("\n");
			}
			renglon++;
			sbCsv.append(nPago)
			.append(";").append(currency(pagoInteres)).append(";").append(currency(pagoCapital)).append(";").append(currency(segVida))
			.append(";").append(currency(segDanos)).append(";").append(currency(comision)).append(";").append(currency(pagoHip)).append(";").append(currency(segCasa))
			.append(";").append(currency(pagoTotal)).append(";").append(currency(restante)).append("\n");
			if(renglon==12){				
				renglon = 0;
			}
		}
		
//		System.out.println(sb.toString());
		FileUtily.writeStringInFile("/home/dothr/workspace/MyProjects/files/salida/"+fileName+".csv", sbCsv.toString(), false);
	}
	
	public static void restador(){
		double saldo = 37.50;//1938.82;
		double rest = 0.29;
		int renglon = 7;
		do{
			saldo = saldo-rest;
			System.out.println(rounded(saldo) );
			if(renglon==12){
				System.out.println();
				renglon=0;
			}
			renglon++;
		}while(saldo>0.0);
	}
	
	private static void supuesto(){
		int pagosRestantes = 72;
		double saldo = 204028.70;//79068.60;
		double tasa = 11.00338379;//C_TASA;
		double iAnual = tasa/100;
		
		//por calcular y temporal
		Double iMensual;
		Double tmp1, tmp2, tmp3;
		
		Double pagoInteres;
		/* Formula:   I12 = ((1+0.05)ʌ(1/12))-1 */
		tmp1 = 1+(iAnual);	//System.out.println("tmp1: "+ tmp1); //1.05
		tmp2 = 1.0/12.0;	//System.out.println("tmp2: "+ tmp2); //0.083333333
		tmp3 = Math.pow(tmp1, tmp2);	//System.out.println("tmp3: "+ tmp3);	//1.004074124
		iMensual = tmp3-1;	 System.out.println("int12: " + iMensual );	//0.004074124 (Con tasa =5)
		
		pagoInteres = iMensual*saldo;
		System.out.println(rounded(pagoInteres) );
		/*
		79068.60×1.683447791 = 133107.860007463
		:: 78406.02 = 133107.86002936/1.683447791
		
		AbCap x 1.683447791 = 1346.45
		::
		AbCap = 1346.45/1.683447791
		
		*/
	}
	
	private static void sumaCantidades(){
		List<String> lineas = FileUtily.getLinesFile("/home/dothr/workspace/MyProjects/files/salida/sumar.csv");
		Iterator<String> itLinea = lineas.iterator();
		String linea;
		List<String> tokens;
		StringBuilder sb = new StringBuilder();
		String tokSep = ";";	//";";
		while(itLinea.hasNext()){
			linea = itLinea.next();
			if(linea.indexOf(tokSep)!=-1 && linea.indexOf("$")!=-1){
				tokens = Arrays.asList( linea.split("\\s*"+tokSep+"\\s*") );				
				/*
				Double pagoHip = new Double( retroCurrency(tokens.get(0)) );
				Double segCasa  = new Double( retroCurrency(tokens.get(1)) );
				Double pagoTotal = pagoHip+segCasa;
				System.out.println(tokens + " " + rounded(pagoTotal) + " >"+tokens.size());*/
				
				System.out.println(tokens + " >"+tokens.size());
				Double pInteres = new Double( retroCurrency(tokens.get(0)) );
				Double pCapital = new Double( retroCurrency(tokens.get(1)) );
				Double segVida  = new Double( retroCurrency(tokens.get(2)) );
				Double segDanos = new Double( retroCurrency(tokens.get(3)) );
				Double comision = new Double( retroCurrency(tokens.get(4)) );
				Double pagoHip;
				Double segCasa  = new Double( retroCurrency(tokens.get(6)) );
				Double pagoTotal;
				
				pagoHip = pInteres+pCapital+segVida+segDanos+comision;
				pagoTotal = pagoHip+segCasa;
				sb.append(pInteres).append(";")
				.append(pCapital).append(";")
				.append(segVida).append(";")
				.append(segDanos).append(";")
				.append(comision).append(";")
				.append(rounded(pagoHip)).append(";")
				.append(segCasa).append(";")
				.append(rounded(pagoTotal)).append("\n");
			}
			else{
				sb.append("\n");
			}
			
			
		}
		System.out.println(sb);
		FileUtily.writeStringInFile("/home/dothr/workspace/MyProjects/files/salida/resultado.csv", sb.toString(), false);
	}
	
	
	public static void calculoFeb18(){
		double comision = C_COMISION;
		double tasa = 13.0169;	//;C_TASA;
		double segDanos = 127.14;	//Varia (123.15);
		double segCasa = 294.03;
		double segVida = 37.50;
		double factorVida = .00035533;//Varia (.000233),00035533
		double pagoHip = 1938.53;	//Double pagoHip;
		double decremento = 0.29;
		
		double restante = 15959.99;	//prestamo;
		int renglon = 5;		
		double iAnual = tasa/100;
		
		//por calcular y temporal
		Double iMensual;
		Double tmp1, tmp2, tmp3;
		
		Double pagoInteres;
		Double pagoCapital;
		Double cuotaMensual;
		
//		Double pagoTotal;
		StringBuilder sbCsv = new StringBuilder("#pago;PagoIntereses;PagoCapital;SeguroVida;SeguroDaños;Comision;PagoHip;SeguroCasa;PagoTotal;SaldoCredito\n");
		
		/* Formula:   I12 = ((1+0.05)ʌ(1/12))-1 */
		tmp1 = 1+(iAnual);	//System.out.println("tmp1: "+ tmp1); //1.05
		tmp2 = 1.0/12.0;	//System.out.println("tmp2: "+ tmp2); //0.083333333
		tmp3 = Math.pow(tmp1, tmp2);	//System.out.println("tmp3: "+ tmp3);	//1.004074124
		iMensual = tmp3-1;	// System.out.println("int12: " + iMensual );	//0.004074124
		
		
		segCasa = 0.0;
		
		
//		sb = new StringBuilder("Intereses;Capital;SeguroVida;SeguroDaños;Comision;Pago Hipotec;SegCasa;PagoTotal;Saldo\n");
		
		int nPago = 92;
		do{
			pagoInteres = iMensual*restante;
			segVida = restante*factorVida;
			//SI pagoHip = pInteres + pCapital +segVida + segDanos + comision:
			cuotaMensual = pagoHip-segVida-segDanos-comision;
			pagoCapital = cuotaMensual-pagoInteres;
			segVida = segVida-decremento;
			pagoHip = pagoHip-decremento;
			
			restante = restante-pagoCapital;
			
			/* Formato CSV */
//			if(renglon == 0){
//				sbCsv.append("Año;").append(anioIni++).append("\n");
//			}
			renglon++;
			sbCsv.append(nPago)
			.append(";").append(currency(pagoInteres)).append(";").append(currency(pagoCapital)).append(";").append(currency(segVida))
			.append(";").append(currency(segDanos)).append(";").append(currency(comision)).append(";").append(currency(pagoHip)).append(";").append(currency(segCasa))
			.append(";").append(currency(pagoHip)).append(";").append(currency(restante)).append("\n");
			if(renglon==12){				
				renglon = 0;
			}
			nPago++;
		}while(restante>0 );
		
		System.out.println(sbCsv.toString());
	}
	
}
