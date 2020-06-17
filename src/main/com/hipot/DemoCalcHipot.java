package com.hipot;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import utily.FileUtily;

public abstract class DemoCalcHipot {

	/**
	 * Redondea a dos decimales 
	 * @param value
	 * @return
	 */
	protected static double rounded(double value){
		return (double)Math.round(value * 100d) / 100d; 
	}
	/**
	 * Convierte doble en cadena formato moneda
	 * @param numero
	 * @return
	 */
	protected static String currency(double numero){
		 String format = "###,###,###.00";
		  String formatedNumber = String.format("%.2f", numero);
			  try{
				  NumberFormat formatter = new DecimalFormat(format);
				  formatedNumber = formatter.format(numero);
			  }catch (Exception e) {
				e.printStackTrace();
			}
		  return formatedNumber;
	}
	
	protected static String retroCurrency(String currency){
		return currency.replace("$", "").replace(",", "");
	}
	
	

	
	/**
	 * DEmostración de calculo de interes total sobre un prestamo
	 */
	protected static void demoInteres(){
		/* constantes */
		int numeroAnios =1;
		double tasa = 10.63;
		double cantidad = 10000; //$10,000	
		
		/* A calcular */
		double iAnual=tasa/100; 
		double totalPago = 0;
		double totalIntereses = 0;
		
		StringBuilder sb = new StringBuilder("Calculo Hipotecario \n");
		
		sb.append("\nMonto Prestamo: ").append( currency(cantidad) )
		.append(", Tasa Anual: ").append(tasa)
		.append(", Años para pagar: ").append(numeroAnios ).append("\n");
		//System.out.println("POW: " + (Math.pow((1+iAnual),numeroAnios) ) );
		totalPago = cantidad * (Math.pow((1+iAnual), numeroAnios));
		totalIntereses = totalPago-cantidad;
		sb.append("Total Intereses: ").append( currency(totalIntereses) ).append("\n");
		//sb.append("Total a Pagar: ").append( String.format("%.2f",totalPago)).append("\n");
		sb.append("Total a Pagar: ").append( currency(totalPago) ).append("\n");
		
		System.out.println(sb);
	}
	
	/**
	 * Demostración de calculo de interes total sobre un prestamo a diferentes periodos (Numero de años)
	 */
	protected static void demoInteres2(Double prestamo, Double tasa, int periodos){
		StringBuilder sb = new StringBuilder();
		
//		 Double prestamo = 100000.00;// 10000.00;
//		 Double tasa = 5.00;//10.63;
//		 int periodos = 30; //10;	//años
		 
		 //Auxiliar
		 int i=0;       //Año-periodo
		 
		 /* A calcular */
		 Double compuesto;	//totalPago
		 Double intereses;
		 double iAnual=tasa/100;
		 System.out.println("iAnual: " + iAnual );
		 System.out.println("iMensual: " + (iAnual/12));
		 
		 sb.append("Año\tMonto\tIntereses\n");

	        for(i=0;i<=periodos;i++){
	        	compuesto=prestamo*Math.pow(1+iAnual, i);
	        	intereses = compuesto-prestamo;
	            sb.append(i+"\t$ "+ currency(compuesto)+"\t$ " + currency(intereses)+"\n");
	        }		
		System.out.println(sb);
	}
	
	
	/**
	 * Muestra un calculo mensual por solo un año
	 */
	public static void demoCalc(){
		double prestamo = 100000;
		double tasa = 5;
		double pagoMen = 530.06;		
		//
		double restante = prestamo;
		double iAnual = tasa/100;
		Double iMensual;
		Double interesMensual;
		Double tmp1, tmp2, tmp3;
		Double pagoCapital;
		System.out.println("iAnual: " + iAnual);
		tmp1 = 1+(iAnual);	
		System.out.println("tmp1: "+ tmp1); //1.05
		tmp2 = 1.0/12.0;
		System.out.println("tmp2: "+ tmp2); //0.083333333
		tmp3 = Math.pow(tmp1, tmp2);
		System.out.println("tmp3: "+ tmp3);	//1.004074124
		iMensual = tmp3-1;
		System.out.println("int12: " + iMensual );	//0.004074124
		
		for(int x=0; x<12; x++){
			interesMensual = iMensual *restante;
			pagoCapital = pagoMen-interesMensual;
			System.out.println("\n\nPago Mensual: $" + pagoMen);
			System.out.println("int["+x+"] $ "+ interesMensual );
			System.out.println("pagoCap["+x+"] $ "+ pagoCapital );
			restante = restante-pagoCapital;
		}
//		interesMensual = iMensual *restante;
//		System.out.println("\n\nPago Mensual: $" + pagoMen);
//		System.out.println("int.Mensual: $ "+ interesMensual );
		
		
		
	}
	
	
	public static void demoCalc2(){
		StringBuilder sb = new StringBuilder();
		StringBuilder sbCsv = new StringBuilder("#Pago;Cuota;PagoInteres;PagoCapital;Saldo\n");
		double prestamo = 100000;
		double tasa = 5;
		int anios = 30;
		double cuotaMen = 530.06;
		double restante = prestamo;
		double iAnual = tasa/100;
		
		//por calcular y temporal
		
		Double iMensual;
		Double tmp1, tmp2, tmp3;
		int nPagos = 1;
		Double pagoInteres;
		Double pagoCapital;
		
		System.out.println("iAnual: " + iAnual);
		/* Formula:   I12 = ((1+0.05)ʌ(1/12))-1 */
		tmp1 = 1+(iAnual);	
		System.out.println("tmp1: "+ tmp1); //1.05
		tmp2 = 1.0/12.0;
		System.out.println("tmp2: "+ tmp2); //0.083333333
		tmp3 = Math.pow(tmp1, tmp2);
		System.out.println("tmp3: "+ tmp3);	//1.004074124
		iMensual = tmp3-1;
		System.out.println("int12: " + iMensual );	//0.004074124
		
		
		sb.append("\nMonto Prestamo: ").append( currency(prestamo) )
		.append(", Tasa Anual: ").append(tasa)
		.append(", Años para pagar: ").append(anios )
		.append(" Cuota Mensual: ").append(cuotaMen)
		.append("\n++++++++++++++++++").append("\n");
		
		for(int y=0; y<anios; y++){
			sb.append("Año ").append(y+1).append("  \n");
			sbCsv.append("Año;").append(y+1).append("\n");
				for(int x=0; x<12; x++){
					pagoInteres = iMensual*restante;
					if(restante>cuotaMen){
						pagoCapital = cuotaMen-pagoInteres;
						restante = restante-pagoCapital;
					}else{
						pagoCapital = restante;
						restante = 0;
						cuotaMen = pagoCapital+pagoInteres;
					}
					
//					System.out.println("\n\nPago Mensual: $" + pagoMen);
//					System.out.println("int["+x+"] $ "+ interesMensual );
//					System.out.println("pagoCap["+x+"] $ "+ pagoCapital );
					
					
					/* Formato impresion */
					sb.append("Pago ").append(nPagos).append(":  ")
					.append("\tPago Mensual (fijo)= $ ").append(cuotaMen)
					.append("\tPagoInteres= $ ").append(rounded(pagoInteres))
					.append("\tPagoCapital= $ ").append(rounded(pagoCapital))
					.append("\tRestante= $ ").append(rounded(restante)).append("\n");
					
					/* Formato CSV */
					sbCsv.append(nPagos)
					.append(";").append(rounded(cuotaMen))
					.append(";").append(rounded(pagoInteres))
					.append(";").append(rounded(pagoCapital))
					.append(";").append(rounded(restante))
					/*.append(";").append(pagoInteres) //Sin redondeo
					.append(";").append(pagoCapital)
					.append(";").append(restante)*/
					.append("\n");
					
					nPagos++;
				}
		}		
		
		System.out.println(sb.toString());
		FileUtily.writeStringInFile("/home/dothr/workspace/MyProjects/files/salida/demoHipot.csv", sbCsv.toString(), false);
	}
	
	public static void main(String[] args) {
		demoInteres2( 100000.00, 11.02268879, 30);	//100000.00, 5.00, 30);
//		demoCalc();
//		demoCalc2();
	}
}
