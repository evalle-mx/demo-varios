package docs.pdf;

import java.awt.Color;
import java.io.FileOutputStream;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

public class DhrTablesTester {

	private static final String OutPath = "/home/dothr/Documents/Tmp/";
	private static final String imgPath = "/home/dothr/Pictures/Puka_n.jpg";
	
	public static void main(String[] args) {
//		cellFixedHeightPDF();
		createPdfDotHR(OutPath+"PdfTablesTester.pdf");
	}

	
	
	
	private static Paragraph getParag(){
		StringBuilder sb = new StringBuilder();
		sb.append("TITULO \n\n");
		for(int x=0; x<25;x++){
			sb.append("\n").append(x+1).append(". Netto Netto Netto Netto Netto Netto Netto Netto Netto Netto Netto Netto Netto Netto Netto Netto Netto Netto Netto Netto Netto Netto Netto Netto Netto Netto Netto Netto Netto Netto Netto Netto Netto Netto Netto Netto Netto Netto Netto Netto.");
		}
		
		
		Paragraph expParag = new Paragraph();        
		expParag.setSpacingBefore(10f);
		expParag.setSpacingAfter(8f);
		expParag.add(sb.toString());
        
        return expParag;
	}
	
	
	
	
	
	/* ************************* Metodo 1 ************************************************ */
	public static void cellFixedHeightPDF (){
		String fileName = "DotHR-PDF"
				//+ DateUtily.thisDateFormated("ddMMss") 
				+ ".pdf";
		Document document = new Document(PageSize.A4);
	    try {
//	      PdfWriter writer = 
	    		  PdfWriter.getInstance(document,  new FileOutputStream(OutPath + fileName));
	      document.open();

	      //Tabla1
	      PdfPTable table1 = createTable1();//new PdfPTable(2);
	      document.add(table1);
	      
	      //Tabla 2
	      PdfPTable table2 = createTable2();
	      document.add(table2);
	      
	    } catch (Exception de) {
	      de.printStackTrace();
	    }
	    System.out.println("documento " + OutPath
				+ fileName + " creado ");
	    document.close();
	  }
	
	public static PdfPTable createTable1() throws DocumentException {
		PdfPTable table = new PdfPTable(2);
	      table.setWidthPercentage(100);//establece al  100% en el ancho del documento
//	      table.getDefaultCell().setPadding(50);
//	      table.getDefaultCell().setBorder(Rectangle.NO_BORDER);//establece Sin border a toda la celda
//	      table.getDefaultCell().setBorder(Rectangle.BOX);//establece border a toda la celda
	      float[] celdas = {3, (float)10.25};
	      table.setWidths(celdas);
	      
	      PdfPCell cellLateral = new PdfPCell(getParag());
	      cellLateral.setBorderWidth(Rectangle.NO_BORDER);
	      cellLateral.setPadding(10f);
//	      cell.setFixedHeight(150f);
	      cellLateral.setBackgroundColor(new Color(0xBFBFBF));	// 2E2E2E => ImgPerfil
	      table.addCell(cellLateral);
	      
	      
	      //table.getDefaultCell().setBorder(Rectangle.NO_BORDER);
	      PdfPCell cellPrincipal = new PdfPCell(getParag());
	      cellPrincipal.setBorderWidth(Rectangle.NO_BORDER);//establece el ancho del border
	      cellPrincipal.setPadding(10f);
//	      cellPrincipal.setPaddingLeft(10f);
	      cellPrincipal.setBackgroundColor(new Color(0xF4F5F4));	//Color.cyan);
//	      cellBenef.setNoWrap(false);
	      table.addCell(cellPrincipal);	
	      
	      return table;
	}
	
	/**
	 * @url http://developers.itextpdf.com/examples/itext-action-second-edition/chapter-4
	 * @return
	 * @throws DocumentException
	 */
	public static PdfPTable createTable2() throws DocumentException {
        PdfPTable table = new PdfPTable(3);
        table.setWidthPercentage(288 / 5.23f);
        table.setWidths(new int[]{2, 1, 1});
        PdfPCell cell;
        cell = new PdfPCell(new Phrase("Table 1"));
        cell.setColspan(3);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("Cell with rowspan 2"));
        cell.setRowspan(2);
        table.addCell(cell);
        table.addCell("row 1; cell 1");
        table.addCell("row 1; cell 2");
        table.addCell("row 2; cell 1");
        table.addCell("row 2; cell 2");
        return table;
    }
	
	/* *************************  METODO 2 ******************************* */
	public static void createPdfDotHR(String dest) {
		try{

	        Document document =  new Document(PageSize.A4);
	        		//new Document(PageSize.A4.rotate()); /* Horizontal */
	        PdfPTable table;
	        PdfWriter.getInstance(document, new FileOutputStream(dest));
	        document.open();
	        float[] columnWidths = {3, (float)10.25 };
	        
	        /* Tabla 2 */
	        table = new PdfPTable(columnWidths);
	        table.setWidthPercentage(100);
//	        document.add(new Paragraph("Agregar tabla al constructor de celdas"));
	        table.setTotalWidth(570F); /* para horizontal*/
	        table.setLockedWidth(true);
	        
	        buildNestedTables2(table, null);
	        document.add(table);
	        
	        document.close();
	        System.out.println("Archivo generado: "+dest);
		}catch (Exception ex){
			ex.printStackTrace();
		}
    }
	
	/**
	 * Constructor de Tablas anidadas
	 * @param outerTable
	 * @param cvPersona
	 * @throws Exception
	 */
	protected static void buildNestedTables2(PdfPTable outerTable, Object cvPersona) throws Exception {
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
        cell.setBackgroundColor(new Color(0xBFBFBF));
        cell.setBorderWidth(Rectangle.NO_BORDER);
        /* Inserta celda en Columna Uno de la tabla mayor */
        outerTable.addCell(cell);
        
        PdfPTable innerTable2 = new PdfPTable(1);
        innerTable2.setSpacingAfter(5);
        innerTable2.setWidthPercentage(80);
        /* subCelda de Datos Profesionales */
        subCell = getExpCell(cvPersona);
        innerTable2.addCell(subCell);
        cell = new PdfPCell(innerTable2);
        cell.setBackgroundColor(new Color(0xF4F5F4));
        cell.setBorderWidth(Rectangle.NO_BORDER);
        /* Inserta celda en Columna Dos de la tabla mayor */
        outerTable.addCell(cell);
   }

	/**
	 * Segmento de Imagen de Perfil y datos Personales
	 * @param cvPersona
	 * @return
	 * @throws Exception
	 */
	private static PdfPCell getImgCell(Object cvPersona) throws Exception{
		PdfPCell subCell = new PdfPCell();
        subCell.setBorderWidth(Rectangle.NO_BORDER);
        subCell.setBackgroundColor(new Color(0x2E2E2E));
        Image img = Image.getInstance(imgPath);
        img.
        	//
        	scaleAbsolute(80f, 50f);
        img.setSpacingBefore(10f);
        img.setAlignment(Element.ALIGN_MIDDLE);
        subCell.addElement(img);
        /* Texto */
        StringBuilder sb = new StringBuilder();
		sb.append("Candidato Solicitado\nEdad:99\nTitulo:Nobiliario");
		Paragraph imgParag = new Paragraph();        
        imgParag.setSpacingBefore(10f);
        imgParag.setSpacingAfter(8f);
        imgParag.add(sb.toString());
        /* *** */
        
        
        subCell.addElement(imgParag);
        return subCell;
	}
	
	/**
	 * Segmento datos Demograficos
	 * @param cvPersona
	 * @return
	 */
	private static PdfPCell getDemogCell(Object cvPersona) {
		PdfPCell subCell = new PdfPCell();
        subCell.setBorderWidth(Rectangle.NO_BORDER);
        
        StringBuilder sb = new StringBuilder();
		sb.append("Datos Demograficos \n\n")
		.append("DotHR DotHR DotHR DotHR DotHR DotHR DotHR DotHR DotHR DotHR DotHR DotHR DotHR DotHR DotHR DotHR DotHR DotHR DotHR DotHR DotHR DotHR DotHR DotHR DotHR DotHR DotHR DotHR DotHR DotHR DotHR DotHR DotHR DotHR DotHR DotHR DotHR DotHR DotHR DotHR.");
		Paragraph dataParag = new Paragraph();        
		dataParag.setSpacingBefore(10f);
		dataParag.setSpacingAfter(8f);
		dataParag.add(sb.toString());
        
        subCell.addElement(dataParag);
        return subCell;
	}
	
	/**
	 * Segmento de Datos Profesionales (Experiencias, Escolaridades, idiomas y Habilidades)
	 * @param cvPersona
	 * @return
	 */
	private static PdfPCell getExpCell(Object cvPersona) {
		PdfPCell subCell = new PdfPCell();
        subCell.setBorderWidth(Rectangle.NO_BORDER);
        StringBuilder sb = new StringBuilder();
		sb.append("Experiencias, Academias, Idiomas/Habilidades \n\n");
		for(int x=0; x<25;x++){
			sb.append("\n").append(x+1).append(". Netto Netto Netto Netto Netto Netto Netto Netto Netto Netto Netto Netto Netto Netto Netto Netto Netto Netto Netto Netto Netto Netto Netto Netto Netto Netto Netto Netto Netto Netto Netto Netto Netto Netto Netto Netto Netto Netto Netto Netto.");
		}
		
		Paragraph expParag = new Paragraph();        
		expParag.setSpacingBefore(10f);
		expParag.setSpacingAfter(8f);
		expParag.add(sb.toString());
		
        subCell.addElement(expParag);
		return subCell;
	}
}
