package docs.pdf;

import java.awt.Color;
import java.io.FileOutputStream;

import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

public class DothrCVpdf2 {

	private static final String OutPath = "/home/dothr/Documents/Tmp/";
	private static final String imgPath = "/home/dothr/Pictures/Puka_n.jpg";
	private static final Rectangle tamPag = PageSize.A4;	//PageSize.A4.rotate()); /* Horizontal */
	private static final float pagWidth = 570F;
	
	
	private static float[] columnWidths = {3, (float)10.25 };
	private static float imgX = 80f;
	private static float imgY = 50f;

	private static Color colorImgP = new Color(0x2E2E2E);
	private static Color colorDemog = new Color(0xBFBFBF);
	private static Color colorProfes = new Color(0xF4F5F4);
	
	private static final Font fontTituloSDemo = FontFactory.getFont(
			FontFactory.HELVETICA_BOLD, 
			12, 
			Font.NORMAL, 
			new Color(0xF4F5F4)
			);
	private static final Font fontTxtWhiteMd = FontFactory.getFont(
			FontFactory.HELVETICA_BOLD, 
			9, 
			Font.NORMAL, 
			new Color(0xF4F5F4)	//0, 0, 255)
			);
	private static final Font fontTxtWhiteSm = FontFactory.getFont(
			FontFactory.HELVETICA_BOLD, 
			8, 
			Font.NORMAL, 
			new Color(0xF4F5F4)	//0, 0, 255)
			);
	/* Columna Datos Profesionales */
	private static final Font fontNombre = FontFactory.getFont(
			FontFactory.HELVETICA_BOLDOBLIQUE, 
			14, 
			Font.NORMAL, 
			new Color(0x424242)	//0, 0, 255)
			);
	private static final Font fontTituloSPr = FontFactory.getFont(
			FontFactory.HELVETICA_BOLD, 
			14, 
			Font.NORMAL, 
			new Color(0x428BCA)	//0, 0, 255)
			);
	private static final Font fontTitulo = FontFactory.getFont(
			FontFactory.HELVETICA_BOLD, 
			11, 
			Font.NORMAL, 
			new Color(0x151515)
			);
	private static final Font fontFecha = FontFactory.getFont(
			FontFactory.HELVETICA, 
			10, 
			Font.NORMAL, 
			new Color(0x848484)
			);
	
	private static final Font fontTxtNormalBold = FontFactory.getFont(
			FontFactory.HELVETICA, 
			10, 
			Font.BOLD, 
			new Color(0x424242)
			);
	private static final Font fontTxtNormal = FontFactory.getFont(
			FontFactory.HELVETICA, 
			10, 
			Font.NORMAL, 
			new Color(0x424242)
			);
	
	private static final String sepDemog = "____________________\n";
	private static final String sepProfs = "________________________________________________________________________";
	
	public static void main(String[] args) {
		createPdfDotHR(OutPath+"dotHR-pdf2.pdf", null);
	}
	
	public static void createPdfDotHR(String dest, Object cvPersona) {
		try{

	        Document document =  new Document(tamPag);
	        PdfPTable mainTable;
	        PdfWriter.getInstance(document, new FileOutputStream(dest));
	        document.open();	        
	        
	        /* ****   TABLA PRINCIPAL   **** */
	        mainTable = new PdfPTable(columnWidths);
	        mainTable.setWidthPercentage(100);
	        mainTable.setTotalWidth(pagWidth);
	        mainTable.setLockedWidth(true);

	        /* ********* INSERCIÓN DE SECCIONES (celdas) ******* */
	        PdfPCell cell, subCell;
	    	PdfPTable innerTable1 = new PdfPTable(1);
	    	innerTable1.setSpacingAfter(5);		//setPadding(5);
	        innerTable1.setWidthPercentage(100);
	        /* subCelda de Imagen y Datos Personales */
	        subCell = getImgCell(cvPersona);        
	        innerTable1.addCell(subCell);
	        
	        /* subCelda de Datos Demográficos */
	        subCell = getDemogCell(cvPersona);
	        innerTable1.addCell(subCell);
	        /* Inserta subTabla1 en nueva celda */
	        cell = new PdfPCell(innerTable1);
	        cell.setBackgroundColor(colorDemog);
	        cell.setBorderWidth(Rectangle.NO_BORDER);
	        /* Inserta celda en Columna Uno de la tabla mayor */
	        mainTable.addCell(cell);
	        
	        PdfPTable innerTable2 = new PdfPTable(1);
	        innerTable2.setSpacingAfter(5);
	        innerTable2.setWidthPercentage(80);
	        /* subCelda de Datos Profesionales */
	        subCell = getExpCell(cvPersona);
	        innerTable2.addCell(subCell);
	        cell = new PdfPCell(innerTable2);
	        cell.setBackgroundColor(colorProfes);
	        cell.setBorderWidth(Rectangle.NO_BORDER);
	        /* Inserta celda en Columna Dos de la tabla mayor */
	        mainTable.addCell(cell);
	        /* ********* INSERCIÓN DE SECCIONES (celdas) FIN ******* */        
	        
	        
	        document.add(mainTable);
	        
	        document.close();
	        System.out.println("Archivo generado: "+dest);
		}catch (Exception ex){
			ex.printStackTrace();
		}
    }
	
	
	/**
	 * Segmento de Imagen de Perfil y datos Personales
	 * @param cvPersona
	 * @return
	 * @throws Exception
	 */
	protected static PdfPCell getImgCell(Object cvPersona) throws Exception{
		PdfPCell subCell = new PdfPCell();
        subCell.setBorderWidth(Rectangle.NO_BORDER);
        subCell.setBackgroundColor(colorImgP);
        Image img = Image.getInstance(imgPath);
        img.scaleAbsolute(imgX, imgY);
        	//scaleAbsolute(80f, 50f);
        img.setSpacingBefore(5f);
        img.setSpacingAfter(0f);
        img.setAlignment(Element.ALIGN_MIDDLE);
        subCell.addElement(img);
        /* Datos Persona */
		
		Paragraph imgParag = new Paragraph();
        imgParag.setSpacingBefore(1f);
        imgParag.setSpacingAfter(8f);
        imgParag.add(new Chunk(" Candidato Solicitado\n", fontTxtWhiteMd) );
        
        imgParag.add(new Chunk(" Titulo: ", fontTxtWhiteMd) );
        imgParag.add(new Chunk("Nobiliario"+"\n", fontTxtWhiteSm) );
        imgParag.add(new Chunk(" Edad: ", fontTxtWhiteMd) );
        imgParag.add(new Chunk("109"+"\n", fontTxtWhiteSm) );
        imgParag.add(new Chunk(" Genero: ", fontTxtWhiteMd) );
        imgParag.add(new Chunk("Masculino"+"\n", fontTxtWhiteSm) );
        
        /* *** */
        
        
        subCell.addElement(imgParag);
        return subCell;
	}
	
	/**
	 * Segmento datos Demograficos
	 * @param cvPersona
	 * @return
	 */
	protected static PdfPCell getDemogCell(Object cvPersona) {
		PdfPCell subCell = new PdfPCell();
        subCell.setBorderWidth(Rectangle.NO_BORDER);
        
        
        subCell.addElement(gTxtSecc("Contacto",-1));
        
        Paragraph dataParag = new Paragraph();
        dataParag.setSpacingBefore(10f);
		dataParag.setSpacingAfter(8f);
		/* Contacto(s) */
        dataParag.add(new Chunk(longEmail("selex1Extremadamentelargo@selex.com.mx")+"\n", fontTxtWhiteSm));
        dataParag.add(new Chunk(longEmail("[55] 28503734")+"\n", fontTxtWhiteSm));
        
        dataParag.add(new Chunk(sepDemog, fontTxtWhiteSm) );
        /* Salario & Ubicacion */
        dataParag.add(new Chunk("Rango Salarial:\n", fontTxtWhiteMd) );
        dataParag.add(new Chunk("$18,000.00 - $20,000.00"+"\n", fontTxtWhiteSm) );
        
        dataParag.add(new Chunk("Ubicación:\n", fontTxtWhiteMd) );
        dataParag.add(new Chunk("San Andrés Totoltepec, Tlalpan. Distrito Federal"+"\n", fontTxtWhiteSm) );
        dataParag.add(new Chunk(sepDemog, fontTxtWhiteSm) );
        
        /* Demograficos */
        dataParag.add(new Chunk("Edo.Civil: ", fontTxtWhiteMd) );
        dataParag.add(new Chunk("Soltero(a)"+"\n", fontTxtWhiteSm) );
        
        dataParag.add(new Chunk("Cambio de Domicilio: ", fontTxtWhiteMd) );
        dataParag.add(new Chunk("Si"+"\n", fontTxtWhiteSm) );
        
        dataParag.add(new Chunk("Disp. Horario: ", fontTxtWhiteMd) );
        dataParag.add(new Chunk("Si"+"\n", fontTxtWhiteSm) );
        
        dataParag.add(new Chunk("Disp. Viajar: ", fontTxtWhiteMd) );
        dataParag.add(new Chunk("Sólo ocasionalmente"+"\n", fontTxtWhiteSm) );
        dataParag.add(new Chunk(sepDemog, fontTxtNormal) );
        
        subCell.addElement(dataParag);
        return subCell;
	}
	
	/**
	 * Segmento de Datos Profesionales (Experiencias, Escolaridades, idiomas y Habilidades)
	 * @param cvPersona
	 * @return
	 */
	protected static PdfPCell getExpCell(Object cvPersona) {
		PdfPCell subCell = new PdfPCell(), tmpCell;
        subCell.setBorderWidth(Rectangle.NO_BORDER);
        
        subCell.addElement( gTxtSecc("Nombre de Candidato", 0) );
        
        /* section Academic */
        subCell.addElement(gTxtSecc("Formación Académica", 1));
		for(int x=0; x<2;x++){
			PdfPTable expTable = new PdfPTable(2);
			expTable.setWidthPercentage(100);
			tmpCell = new PdfPCell();
			tmpCell.setBorderWidth(Rectangle.NO_BORDER);
			tmpCell.addElement(gTxtSecc("Lic. en Adm de Empresas-"+x, 2));
			
			expTable.addCell(tmpCell);
			tmpCell = new PdfPCell();
			tmpCell.setBorderWidth(Rectangle.NO_BORDER);
			tmpCell.addElement(gTxtSecc("1997-2001", 3));
			expTable.addCell(tmpCell);
			subCell.addElement(expTable);
			
			expTable = new PdfPTable(1);
			expTable.setWidthPercentage(100);
			tmpCell = new PdfPCell();
			tmpCell.setBorderWidth(Rectangle.NO_BORDER);
			tmpCell.addElement(gTxtSecc("UAM", 4));
			expTable.addCell(tmpCell);
			subCell.addElement(expTable);
			
			expTable = new PdfPTable(1);
			expTable.setWidthPercentage(100);
			tmpCell = new PdfPCell();
			tmpCell.setBorderWidth(Rectangle.NO_BORDER);
			tmpCell.addElement(gTxtSecc("Estudios Universitarios" +
					". Trabajo en equipo y responsabilidad.\n", 5));
			expTable.addCell(tmpCell);
			subCell.addElement(expTable);			
		}
		subCell.addElement(gTxtSecc(sepProfs, 4));
		
		
		/* section WorkExperience */
		subCell.addElement(gTxtSecc("Experiencia Laboral", 1));
		for(int y=0; y<3;y++){
			PdfPTable expTable = new PdfPTable(2);
			expTable.setWidthPercentage(100);
			tmpCell = new PdfPCell();
			tmpCell.setBorderWidth(Rectangle.NO_BORDER);
			tmpCell.addElement(gTxtSecc("Consultor Jr.", 2));
			
			expTable.addCell(tmpCell);
			tmpCell = new PdfPCell();
			tmpCell.setBorderWidth(Rectangle.NO_BORDER);
			tmpCell.addElement(gTxtSecc("2010-Actual", 3));
			expTable.addCell(tmpCell);
			subCell.addElement(expTable);
			
			expTable = new PdfPTable(1);
			expTable.setWidthPercentage(100);
			tmpCell = new PdfPCell();
			tmpCell.setBorderWidth(Rectangle.NO_BORDER);
			tmpCell.addElement(gTxtSecc("Grupo Elektra", 4));
			expTable.addCell(tmpCell);
			subCell.addElement(expTable);
			
			expTable = new PdfPTable(1);
			expTable.setWidthPercentage(100);
			tmpCell = new PdfPCell();
			tmpCell.setBorderWidth(Rectangle.NO_BORDER);
			tmpCell.addElement(gTxtSecc("Administración de oficína Emisión de facturas Seguimiento y control de pagos de clientes y proveedores" +
					". Administración de nóminas " +
					". Control de incidencias con clientes.\n", 5));
			expTable.addCell(tmpCell);
			subCell.addElement(expTable);
			
			expTable = new PdfPTable(1);
			expTable.setWidthPercentage(100);					
			Paragraph p = new Paragraph();			
			Chunk chunk1 = new Chunk("Motivo Separación: ", fontTxtNormalBold);
			Chunk chunk2 = new Chunk("Reestructura del Área.\n", fontTxtNormal);
			p.add(chunk1);p.add(chunk2);
			tmpCell = new PdfPCell(p);
			tmpCell.setBorderWidth(Rectangle.NO_BORDER);
			expTable.addCell(tmpCell);
			subCell.addElement(expTable);

		}
		subCell.addElement(gTxtSecc(sepProfs, 4));
		
		/* section Certificaciones */
		subCell.addElement(gTxtSecc("Certificaciones", 1));
		//Idiomas
		for(int a=0; a<2; a++){
			PdfPTable expTable = new PdfPTable(1);
			expTable.setWidthPercentage(100);
			tmpCell = new PdfPCell();
			tmpCell.setBorderWidth(Rectangle.NO_BORDER);
			tmpCell.addElement(gTxtSecc("Certificación-"+(a+1), 2));
			expTable.addCell(tmpCell);
			subCell.addElement(expTable);
		}
		
		/* section IDIomas/Habilidades */
		subCell.addElement(gTxtSecc("Idiomas y Habilidades", 1));
		//Idiomas
		for(int b=0; b<2; b++){
			PdfPTable expTable = new PdfPTable(3);
			expTable.setWidthPercentage(100);
			tmpCell = new PdfPCell();
			tmpCell.setBorderWidth(Rectangle.NO_BORDER);
			tmpCell.addElement(gTxtSecc("Idioma-"+(b+1), 5));
			
			expTable.addCell(tmpCell);
			tmpCell = new PdfPCell();
			tmpCell.setColspan(2);
			tmpCell.setBorderWidth(Rectangle.NO_BORDER);
			tmpCell.addElement(gTxtSecc(""+((b+1)*20)+"% ", 4));
			expTable.addCell(tmpCell);
			subCell.addElement(expTable);
		}
		//Habilidades
		for(int c=0; c<2; c++){
			PdfPTable expTable = new PdfPTable(3);
			expTable.setWidthPercentage(100);
			tmpCell = new PdfPCell();
			tmpCell.setBorderWidth(Rectangle.NO_BORDER);
			tmpCell.addElement(gTxtSecc("Habilidad-"+(c+1), 5));
			
			expTable.addCell(tmpCell);
			tmpCell = new PdfPCell();
			tmpCell.setColspan(2);
			tmpCell.setBorderWidth(Rectangle.NO_BORDER);
			tmpCell.addElement(gTxtSecc(""+((c+1)*20)+"% ", 4));
			expTable.addCell(tmpCell);
			subCell.addElement(expTable);
		}
        
//		subCell.addElement(gTxtSecc(sepProfs, 4));
		return subCell;
	}
	
	/**
	 * Genera parrafo a partir de un texto y un tipo <br>
	 * 1=>TítuloSección<br> 2=>Título exp/Acad<br> 3=>fecha <br>4 Empresa<br> x=>Texto
	 * @param texto
	 * @param type
	 * @return
	 */
	private static Paragraph gTxtSecc(String texto, int type){
		Paragraph expParag = new Paragraph();
		if(type==-1){ //TítuloSecciónDemografico
			expParag.setFont(fontTituloSDemo);
			texto.toUpperCase();
			expParag.setAlignment(Element.ALIGN_LEFT);
		}else if(type==0){ //TítuloSecciónDemografico
			expParag.setFont(fontNombre);
			expParag.setAlignment(Element.ALIGN_CENTER);
		}else if(type==1){ //TítuloSección
			expParag.setFont(fontTituloSPr);
			texto.toUpperCase();
			expParag.setAlignment(Element.ALIGN_LEFT);
		}else if(type==2){ //Título exp/Acad
			expParag.setFont(fontTitulo);
			expParag.setAlignment(Element.ALIGN_LEFT);
		}
		else if(type==3 || type==4){ //fecha
			expParag.setFont(fontFecha);
			expParag.setAlignment(type==3?Element.ALIGN_RIGHT:Element.ALIGN_LEFT);
		}

		else { // Texto/Default
			expParag.setFont(fontTxtNormal);
		}
		expParag.setSpacingBefore(5f);
		expParag.setSpacingAfter(5f);
		expParag.add(texto);
		return expParag;
	}
	
	private static String longEmail(String email){
		if(email!=null){
			if(email.length()>25){
				if(email.indexOf("@")>25){
					email = email.substring(0,25)+(email.substring(25,email.length()).replace("@", "\n@"));
				}else{
					email=email.replace("@", "\n@");
				}
			}
		}
		return email;
	}
}
