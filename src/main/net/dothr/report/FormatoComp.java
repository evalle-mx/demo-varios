package net.dothr.report;

import java.io.FileOutputStream;

import org.apache.poi.xwpf.usermodel.Borders;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.TextAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

/**
 * Primera versión del Formato de Compensación propuesto
 * @author dothr
 *
 */
public class FormatoComp {

	private static String OUTPATH = "/home/dothr/Documents/Tmp/"
			//"/home/dothr/Documents/SELEX/TMP/+
			;
	private static String tab = "    ";
	private final static String fontFamily = "";
	private final static int fontSize = 10;
	
	
	public static void main(String[] args) {
		try {
//			formatoComp1();
			formatoCompOpt();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * OBtiene un objeto de Parrafo genérico
	 * @param doc
	 * @return
	 */
	private static XWPFParagraph getParag(XWPFDocument doc){
		 XWPFParagraph paragraph = doc.createParagraph();
		    paragraph.setAlignment(ParagraphAlignment.LEFT);
		    paragraph.setBorderBottom(Borders.DOUBLE);
		    paragraph.setBorderRight(Borders.DOUBLE);
		    paragraph.setBorderLeft(Borders.DOUBLE);
		    paragraph.setBorderBetween(Borders.SINGLE);
		    paragraph.setBorderTop(Borders.DOUBLE);

		    paragraph.setVerticalAlignment(TextAlignment.TOP);
		    
		    return paragraph;
	}
	
	protected static void setDatosPersonales(XWPFRun rDatos){
		 rDatos.setFontFamily(fontFamily);
		 rDatos.setFontSize(fontSize);
//		    r1.setBold(true);
		 rDatos.setText("Datos Personales");
		 rDatos.addBreak();
//		        r1.setUnderline(UnderlinePatterns.DOT_DOT_DASH);
		 rDatos.setText("Nombre: ");
		 rDatos.setText("SELEX DEMO "+tab);
		 rDatos.setText("Fecha de Nacimiento: ");
		 rDatos.setText("Aug 1, 1980 ");
		 rDatos.addBreak();

		 rDatos.setText("Género: ");
		 rDatos.setText("Masculino"+tab);
		 rDatos.setText("Estado civil:");
		 rDatos.setText("Soltero(a)"+tab);
		 rDatos.setText("País de origen: ");
		 rDatos.setText("México");
		 rDatos.addBreak();

		 rDatos.setText("Teléfono Celular: ");
		 rDatos.setText(" [55] 32956775"+tab);
		 rDatos.addBreak();
		 rDatos.setText("Teléfono Recados: ");
		 rDatos.setText("[55] 25155155 Ext: 2"+tab);
		 rDatos.addBreak();
		 rDatos.setText("Skype: ");
		 rDatos.setText("nettospeed");
		 rDatos.addBreak();

		 rDatos.setText("CURP: ");
		 rDatos.setText("VAVE8008012G9HDF"+tab);
		 rDatos.setText("Número de Hijos: ");
		 rDatos.setText("2"+tab);
		 rDatos.addBreak();
	}
	
	protected static void setEconomica(XWPFRun rComp){
		rComp.setFontFamily(fontFamily);
		rComp.setFontSize(fontSize);
	    rComp.setText("Compensación económica");
	    rComp.addBreak();
	    rComp.setText("Sueldo Base: ");
	    rComp.setText("$ 15000"+tab);
	    rComp.setText("Periodicidad: ");
	    rComp.setText("Mensual ");
	    rComp.setText("Fondo de Ahorro: ");
	    rComp.setText("25000 ");
	    rComp.addBreak();   
	    
	    rComp.setText("Vales de Despensa: ");
	    rComp.setText("$ 1150"+tab);
	    rComp.setText("Vales de Gasolina:");
	    rComp.setText("$ 300"+tab);
	    rComp.setText("Vales de Restaurante: ");
	    rComp.setText("800");
	    rComp.addBreak();
	    
	    rComp.setText("Otra prestación: ");
	    rComp.setText("Ninguna");
	    rComp.addBreak();
	}
	
	protected static void setPrestaciones(XWPFRun rPrest){
		rPrest.setFontFamily(fontFamily);
		rPrest.setFontSize(fontSize);
	    rPrest.setText("Prestaciones");
	    rPrest.addBreak();
	    
	    rPrest.setText("Aguinaldo: ");
	    rPrest.setText("15 días"+tab);
	    rPrest.setText("Último monto Utilidades:");
	    rPrest.setText("$ 1,000"+tab);
	    rPrest.addBreak();
	    
	    rPrest.setText("Días de Vacaciones: ");
	    rPrest.setText("16 días"+tab);
	    rPrest.setText("Prima:");
	    rPrest.setText("11"+tab);
	    rPrest.addBreak();
	    
	    rPrest.setText("Servicio de Comedor: ");
	    rPrest.setText("$ 25 diarios"+tab);
	    rPrest.addBreak();
	}
	
	protected static void setBeneficios(XWPFRun rBenef){
		rBenef.setFontFamily(fontFamily);
		rBenef.setFontSize(fontSize);
//	    r1.setBold(true);
	    rBenef.setText("Beneficios");
//	        r1.setUnderline(UnderlinePatterns.DOT_DOT_DASH);
	    rBenef.addBreak();
	    
	    rBenef.setText("Celular: ");
	    rBenef.setText("Si"+tab);
	    rBenef.setText("Club-Gym: ");
	    rBenef.setText("No"+tab);
	    rBenef.setText("Check Up: ");
	    rBenef.setText("Si"+tab);
	    rBenef.addBreak();
	    
	    rBenef.setText("Auto de la compañia ");
	    rBenef.addBreak();	    
	    rBenef.setText("Marca: ");
	    rBenef.setText("NISSAN"+tab);
	    rBenef.setText("Modelo: ");
	    rBenef.setText("Tsuru"+tab);
	    rBenef.setText("Gastos Pagados: ");
	    rBenef.setText("Si"+tab);
	    rBenef.addBreak();
	    rBenef.setText("Opción de Compra: ");
	    rBenef.setText("Si"+tab);
	    rBenef.setText("Tiempo de Cambio: ");
	    rBenef.setText("4 años"+tab);
	    rBenef.addBreak();
	    
	    rBenef.setText("BONOS ");
	    rBenef.addBreak();
	    rBenef.setText("Bono Puntualidad ");
	    rBenef.setText("20 % Quincenal"+tab);
	    rBenef.addBreak();
	    rBenef.setText("Bono Productividad ");
	    rBenef.setText("$ 2,850 Quincenal"+tab);
	    rBenef.addBreak();
	    
	    rBenef.setText("SEGUROS ");
	    rBenef.addBreak();
	    rBenef.setText("Seguro Dental (Individual)");
	    rBenef.addBreak();
	    rBenef.setText("Seguro Gastos Medicos Mayores (Individual)");
	    rBenef.addBreak();
	    rBenef.setText("Seguro de vida (familiar)");
	    rBenef.addBreak();
	}
	
	protected static void setReferencias(XWPFRun rRef){
		rRef.setFontFamily(fontFamily);
		rRef.setFontSize(fontSize);
//	    r1.setBold(true);
	    rRef.setText("Referencias Laborales");
//	        r1.setUnderline(UnderlinePatterns.DOT_DOT_DASH);
	    rRef.addBreak();
	    
	    rRef.setText("Nombre: ");
	    rRef.setText("Roberto Carlos"+tab);
	    rRef.addBreak();
	    rRef.setText("Empresa: ");
	    rRef.setText("Real Madrid FC"+tab);
	    rRef.setText("Puesto: ");
	    rRef.setText("Defensa"+tab);
	    rRef.addBreak();
	    rRef.setText("Email: ");
	    rRef.setText("rcarlos4@rm.com"+tab);
	    rRef.setText("Tel. Partícular: ");
	    rRef.setText("25151515"+tab);
	    rRef.setText("Móvil: ");
	    rRef.setText("55325151");
	    rRef.addBreak();rRef.addBreak();
	    
	    rRef.setText("Nombre: ");
	    rRef.setText("Britney Spears"+tab);
	    rRef.addBreak();
	    rRef.setText("Empresa: ");
	    rRef.setText("USA Discs"+tab);
	    rRef.setText("Puesto: ");
	    rRef.setText("Cantante"+tab);
	    rRef.addBreak();
	    rRef.setText("Email: ");
	    rRef.setText("bitbit@mail.com"+tab);
	    rRef.setText("Móvil: ");
	    rRef.setText("559999151");
	    rRef.addBreak();rRef.addBreak();
	    
	    rRef.setText("Nombre: ");
	    rRef.setText("ALfonso gonzalez"+tab);
	    rRef.addBreak();
	    rRef.setText("Empresa: ");
	    rRef.setText("EDS"+tab);
	    rRef.setText("Puesto: ");
	    rRef.setText("Desarrollador"+tab);
	    rRef.addBreak();
	    rRef.setText("Email: ");
	    rRef.setText("poncho@rm.com"+tab);
	    rRef.setText("Tel. Partícular: ");
	    rRef.setText("25159988"+tab);
	    rRef.addBreak();
	}
	
	protected static void formatoCompOpt() throws Exception {
		 XWPFDocument doc = new XWPFDocument();
		 XWPFParagraph paragraph1 = doc.createParagraph();
		 paragraph1.setAlignment(ParagraphAlignment.CENTER);
		 paragraph1.setBorderBottom(Borders.DOUBLE);
		 paragraph1.setBorderTop(Borders.DOUBLE);

		 paragraph1.setBorderRight(Borders.DOUBLE);
		 paragraph1.setBorderLeft(Borders.DOUBLE);
		 paragraph1.setBorderBetween(Borders.SINGLE);

		 paragraph1.setVerticalAlignment(TextAlignment.TOP);
	        
	        XWPFRun mainTitleRun = paragraph1.createRun();
	        mainTitleRun.setBold(true);
	        mainTitleRun.setText("FORMATO DE COMPENSACIÓN");
	        mainTitleRun.setFontFamily("Courier");
	        mainTitleRun.setFontSize(20);
	        
	    /* Datos Personales */
	    XWPFParagraph dpDatos = getParag(doc);
	    XWPFRun rDatos = dpDatos.createRun();
	    setDatosPersonales(rDatos);
	        
	    /* Compensación económica */
	    XWPFParagraph pCompEcon = getParag(doc); 
	    XWPFRun rComp = pCompEcon.createRun();
	    setEconomica(rComp);
	    
	    /* Prestaciones */
	    XWPFParagraph pPrestac = getParag(doc);
	    XWPFRun rPrest = pPrestac.createRun();
	    setPrestaciones(rPrest);
	    
	    /*  Beneficios*/
	    XWPFParagraph pBenefs = getParag(doc);
	    XWPFRun rBenef = pBenefs.createRun();
	    setBeneficios(rBenef);
	    
	    /*  Referencias Laborales*/
	    XWPFParagraph pRefers = getParag(doc);
	    XWPFRun rRef = pRefers.createRun();
	    setReferencias(rRef);
	    
	    /*>> OUTPUT <<  */
	    FileOutputStream out = new FileOutputStream(OUTPATH+"formatoCompOpt.docx");
	    doc.write(out);
	    out.close();
	    doc.close();
	    System.out.println("Se genero archivo: " + OUTPATH+"formatoCompOpt.docx" );
	}
	
	
	
	protected static void formatoComp1() throws Exception {
		 XWPFDocument doc = new XWPFDocument();
		 String tab = "    ";
		 
		 
		 XWPFParagraph paragraph1 = doc.createParagraph();
		 paragraph1.setAlignment(ParagraphAlignment.CENTER);
		 paragraph1.setBorderBottom(Borders.DOUBLE);
		 paragraph1.setBorderTop(Borders.DOUBLE);

		 paragraph1.setBorderRight(Borders.DOUBLE);
		 paragraph1.setBorderLeft(Borders.DOUBLE);
		 paragraph1.setBorderBetween(Borders.SINGLE);

		 paragraph1.setVerticalAlignment(TextAlignment.TOP);
	        
	        XWPFRun mainTitleRun = paragraph1.createRun();
	        mainTitleRun.setBold(true);
	        mainTitleRun.setText("FORMATO DE COMPENSACIÓN");
	        mainTitleRun.setFontFamily("Arial");
	        mainTitleRun.setFontSize(20);
	        
	    /* Datos Personales */
	    XWPFParagraph dpDatos = doc.createParagraph();
	    dpDatos.setAlignment(ParagraphAlignment.LEFT);
	    dpDatos.setBorderBottom(Borders.DOUBLE);
	    dpDatos.setBorderRight(Borders.DOUBLE);
	    dpDatos.setBorderLeft(Borders.DOUBLE);
	    dpDatos.setBorderBetween(Borders.SINGLE);
	    dpDatos.setBorderTop(Borders.DOUBLE);

	    dpDatos.setVerticalAlignment(TextAlignment.TOP);
	    
	    XWPFRun rDatos = dpDatos.createRun();
	    rDatos.setFontFamily("Arial");
//	    r1.setBold(true);
	    rDatos.setText("Datos Personales");
//	        r1.setUnderline(UnderlinePatterns.DOT_DOT_DASH);
	    rDatos.addBreak();	    
	    //r1.setFontFamily("Arial");
	    rDatos.setText("Nombre: ");
	    rDatos.setText("SELEX DEMO "+tab);
	    rDatos.setText("Fecha de Nacimiento: ");
	    rDatos.setText("Aug 1, 1980 ");
	    rDatos.addBreak();
	    
	    rDatos.setText("Género: ");
	    rDatos.setText("Masculino"+tab);
	    rDatos.setText("Estado civil:");
	    rDatos.setText("Soltero(a)"+tab);
	    rDatos.setText("País de origen: ");
	    rDatos.setText("México");
	    rDatos.addBreak();
	    
	    rDatos.setText("Teléfono Celular: ");
	    rDatos.setText(" [55] 32956775"+tab);
	    rDatos.addBreak();
	    rDatos.setText("Teléfono Recados: ");
	    rDatos.setText("[55] 25155155 Ext: 2"+tab);
	    rDatos.addBreak();
	    rDatos.setText("Skype: ");
	    rDatos.setText("nettospeed");
	    rDatos.addBreak();
	    
	    rDatos.setText("CURP: ");
	    rDatos.setText("VAVE8008012G9HDF"+tab);
	    rDatos.setText("Número de Hijos: ");
	    rDatos.setText("2"+tab);
	    rDatos.addBreak();
	        
	    /* Compensación económica */
	    XWPFParagraph pCompEcon = doc.createParagraph();
	    pCompEcon.setAlignment(ParagraphAlignment.LEFT);
	    pCompEcon.setBorderBottom(Borders.DOUBLE);
	    pCompEcon.setBorderRight(Borders.DOUBLE);
	    pCompEcon.setBorderLeft(Borders.DOUBLE);
	    pCompEcon.setBorderBetween(Borders.SINGLE);
	    pCompEcon.setBorderTop(Borders.DOUBLE);

	    pCompEcon.setVerticalAlignment(TextAlignment.TOP);
	    
	    XWPFRun rComp = pCompEcon.createRun();
	    rComp.setFontFamily("Arial");
//	    r1.setBold(true);
	    rComp.setText("Compensación económica");
//	        r1.setUnderline(UnderlinePatterns.DOT_DOT_DASH);
	    rComp.addBreak();	    
	    //r1.setFontFamily("Arial");
	    rComp.setText("Sueldo Base: ");
	    rComp.setText("$ 15000"+tab);
	    rComp.setText("Periodicidad: ");
	    rComp.setText("Mensual ");
	    rComp.setText("Fondo de Ahorro: ");
	    rComp.setText("25000 ");
	    rComp.addBreak();
	    
	    
	    
	    rComp.setText("Vales de Despensa: ");
	    rComp.setText("$ 1150"+tab);
	    rComp.setText("Vales de Gasolina:");
	    rComp.setText("$ 300"+tab);
	    rComp.setText("Vales de Restaurante: ");
	    rComp.setText("800");
	    rComp.addBreak();
	    

	    rComp.setText("Otra prestación: ");
	    rComp.setText("Ninguna");
	    rComp.addBreak();    
	    
	    
	    /* Prestaciones */
	    XWPFParagraph pPrestac = doc.createParagraph();
	    pPrestac.setAlignment(ParagraphAlignment.LEFT);
	    pPrestac.setBorderBottom(Borders.DOUBLE);
	    pPrestac.setBorderRight(Borders.DOUBLE);
	    pPrestac.setBorderLeft(Borders.DOUBLE);
	    pPrestac.setBorderBetween(Borders.SINGLE);
	    pPrestac.setBorderTop(Borders.DOUBLE);

	    pPrestac.setVerticalAlignment(TextAlignment.TOP);
	    
	    XWPFRun rPrest = pPrestac.createRun();
	    rPrest.setFontFamily("Arial");
//	    r1.setBold(true);
	    rPrest.setText("Prestaciones");
//	        r1.setUnderline(UnderlinePatterns.DOT_DOT_DASH);
	    rPrest.addBreak();	    
	    //r1.setFontFamily("Arial");
	    
	    rPrest.setText("Aguinaldo: ");
	    rPrest.setText("15 días"+tab);
	    rPrest.setText("Último monto Utilidades:");
	    rPrest.setText("$ 1,000"+tab);
	    rPrest.addBreak();
	    
	    rPrest.setText("Días de Vacaciones: ");
	    rPrest.setText("16 días"+tab);
	    rPrest.setText("Prima:");
	    rPrest.setText("11"+tab);
	    rPrest.addBreak();
	    
	    rPrest.setText("Servicio de Comedor: ");
	    rPrest.setText("$ 25 diarios"+tab);
	    rPrest.addBreak();
	    
	    /*  Beneficios*/
	    XWPFParagraph pBenefs = doc.createParagraph();
	    pBenefs.setAlignment(ParagraphAlignment.LEFT);
	    pBenefs.setBorderBottom(Borders.DOUBLE);
	    pBenefs.setBorderRight(Borders.DOUBLE);
	    pBenefs.setBorderLeft(Borders.DOUBLE);
	    pBenefs.setBorderBetween(Borders.SINGLE);
	    pBenefs.setBorderTop(Borders.DOUBLE);

	    pBenefs.setVerticalAlignment(TextAlignment.TOP);
	    
	    XWPFRun rBenef = pBenefs.createRun();
	    rBenef.setFontFamily("Arial");
//	    r1.setBold(true);
	    rBenef.setText("Beneficios");
//	        r1.setUnderline(UnderlinePatterns.DOT_DOT_DASH);
	    rBenef.addBreak();	    
	    //r1.setFontFamily("Arial");
	    
	    rBenef.setText("Celular: ");
	    rBenef.setText("Si"+tab);
	    rBenef.setText("Club-Gym: ");
	    rBenef.setText("No"+tab);
	    rBenef.setText("Check Up: ");
	    rBenef.setText("Si"+tab);
	    rBenef.addBreak();
	    
	    rBenef.setText("Auto de la compañia ");
	    rBenef.addBreak();	    
	    rBenef.setText("Marca: ");
	    rBenef.setText("NISSAN"+tab);
	    rBenef.setText("Modelo: ");
	    rBenef.setText("Tsuru"+tab);
	    rBenef.setText("Gastos Pagados: ");
	    rBenef.setText("Si"+tab);
	    rBenef.addBreak();
	    rBenef.setText("Opción de Compra: ");
	    rBenef.setText("Si"+tab);
	    rBenef.setText("Tiempo de Cambio: ");
	    rBenef.setText("4 años"+tab);
	    rBenef.addBreak();
	    
	    rBenef.setText("BONOS ");
	    rBenef.addBreak();
	    rBenef.setText("Bono Puntualidad ");
	    rBenef.setText("20 % Quincenal"+tab);
	    rBenef.addBreak();
	    rBenef.setText("Bono Productividad ");
	    rBenef.setText("$ 2,850 Quincenal"+tab);
	    rBenef.addBreak();
	    
	    rBenef.setText("SEGUROS ");
	    rBenef.addBreak();
	    rBenef.setText("Seguro Dental (Individual)");
	    rBenef.addBreak();
	    rBenef.setText("Seguro Gastos Medicos Mayores (Individual)");
	    rBenef.addBreak();
	    rBenef.setText("Seguro de vida (familiar)");
	    rBenef.addBreak();
	    
	    
	    /*  Referencias Laborales*/
	    XWPFParagraph pRefers = doc.createParagraph();
	    pRefers.setAlignment(ParagraphAlignment.LEFT);
	    pRefers.setBorderBottom(Borders.DOUBLE);
	    pRefers.setBorderRight(Borders.DOUBLE);
	    pRefers.setBorderLeft(Borders.DOUBLE);
	    pRefers.setBorderBetween(Borders.SINGLE);
	    pRefers.setBorderTop(Borders.DOUBLE);

	    pRefers.setVerticalAlignment(TextAlignment.TOP);
	    
	    XWPFRun rRef = pRefers.createRun();
	    rRef.setFontFamily("Arial");
//	    r1.setBold(true);
	    rRef.setText("Referencias Laborales");
//	        r1.setUnderline(UnderlinePatterns.DOT_DOT_DASH);
	    rRef.addBreak();	    
	    //r1.setFontFamily("Arial");
	    
	    rRef.setText("Nombre: ");
	    rRef.setText("Roberto Carlos"+tab);
	    rRef.addBreak();
	    rRef.setText("Empresa: ");
	    rRef.setText("Real Madrid FC"+tab);
	    rRef.setText("Puesto: ");
	    rRef.setText("Defensa"+tab);
	    rRef.addBreak();
	    rRef.setText("Email: ");
	    rRef.setText("rcarlos4@rm.com"+tab);
	    rRef.setText("Tel. Partícular: ");
	    rRef.setText("25151515"+tab);
	    rRef.setText("Móvil: ");
	    rRef.setText("55325151");
	    rRef.addBreak();rRef.addBreak();
	    
	    rRef.setText("Nombre: ");
	    rRef.setText("Britney Spears"+tab);
	    rRef.addBreak();
	    rRef.setText("Empresa: ");
	    rRef.setText("USA Discs"+tab);
	    rRef.setText("Puesto: ");
	    rRef.setText("Cantante"+tab);
	    rRef.addBreak();
	    rRef.setText("Email: ");
	    rRef.setText("bitbit@mail.com"+tab);
	    rRef.setText("Móvil: ");
	    rRef.setText("559999151");
	    rRef.addBreak();rRef.addBreak();
	    
	    rRef.setText("Nombre: ");
	    rRef.setText("ALfonso gonzalez"+tab);
	    rRef.addBreak();
	    rRef.setText("Empresa: ");
	    rRef.setText("EDS"+tab);
	    rRef.setText("Puesto: ");
	    rRef.setText("Desarrollador"+tab);
	    rRef.addBreak();
	    rRef.setText("Email: ");
	    rRef.setText("poncho@rm.com"+tab);
	    rRef.setText("Tel. Partícular: ");
	    rRef.setText("25159988"+tab);
	    rRef.addBreak();
	        
	    /*
	        XWPFParagraph p2 = doc.createParagraph();
	        p2.setAlignment(ParagraphAlignment.RIGHT);

	        //BORDERS
	        p2.setBorderBottom(Borders.DOUBLE);
	        p2.setBorderTop(Borders.DOUBLE);
	        p2.setBorderRight(Borders.DOUBLE);
	        p2.setBorderLeft(Borders.DOUBLE);
	        p2.setBorderBetween(Borders.SINGLE);

	        XWPFRun r2 = p2.createRun();
	        r2.setText("jumped over the lazy dog");
	        r2.setStrikeThrough(true);
	        r2.setFontSize(20);

	        XWPFRun r3 = p2.createRun();
	        r3.setText("and went away");
	        r3.setStrikeThrough(true);
	        r3.setFontSize(20);
	        r3.setSubscript(VerticalAlign.SUPERSCRIPT);//*/
	        
	        //******************************
	        FileOutputStream out = new FileOutputStream(OUTPATH+"formatoComp1.docx");
	        doc.write(out);
	        out.close();
	        doc.close();
	        System.out.println("Se genero archivo: " + OUTPATH+"formatoComp1.docx" );
	}
}
