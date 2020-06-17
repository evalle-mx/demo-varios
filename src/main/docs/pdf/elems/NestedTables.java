/*
 * http://developers.itextpdf.com/examples/tables/nested-tables#943-nestedtables.java
 */
package docs.pdf.elems;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
 
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
//import sandbox.WrapToTest;

//@WrapToTest
public class NestedTables {

	
	public static final String DEST = "/home/dothr/Documents/Tmp/nested_tables.pdf"; 
			//"files/out/nested_tables.pdf";
	
    public static void main(String[] args) throws IOException, DocumentException {
        File file = new File(DEST);
        file.getParentFile().mkdirs();	//Realiza la creación de directorios contenedores
        new NestedTables().createPdf(DEST);
        System.out.println("Se creo archivo en " + DEST);
    }
    
    public void createPdfDotHR(String dest) throws IOException, DocumentException {
        Document document = new Document(PageSize.A4.rotate());
        PdfPTable table;
        PdfWriter.getInstance(document, new FileOutputStream(dest));
        document.open();
//        float[] columnWidths = {183, 31, 88, 49, 35, 25, 35, 35, 35, 32, 32, 33, 35, 60, 46, 26 };
        float[] columnWidths = {3, (float)10.25 };
        
        /* Tabla 1 */
//        table = new PdfPTable(columnWidths);        
//        document.add(new Paragraph("Añadir Tabla-1 directamente a otra Tabla\n"));
//        table.setTotalWidth(770F);
//        table.setLockedWidth(true);
//        
//        buildNestedTables(table);        
//        document.add(table);
        
        /* Tabla 2 */
        table = new PdfPTable(columnWidths);
        document.add(new Paragraph("Agregar tabla-2 al constructor de celdas"));
        table.setTotalWidth(770F);
        table.setLockedWidth(true);
        
        buildNestedTables2(table);
        document.add(table);
        
        /* Tabla 3 */
//        table = new PdfPTable(columnWidths);
//        document.add(new Paragraph("Agregar tabla-3 como elemento a una celda"));
//        table.setTotalWidth(770F);
//        table.setLockedWidth(true);
//        
//        buildNestedTables3(table);
//        document.add(table);
        
        document.close();
    }
 
    public void createPdf(String dest) throws IOException, DocumentException {
        Document document = new Document(PageSize.A4.rotate());
        PdfPTable table;
        PdfWriter.getInstance(document, new FileOutputStream(dest));
        document.open();
        float[] columnWidths = {183, 31, 88, 49, 35, 25, 35, 35, 35, 32, 32, 33, 35, 60, 46, 26 };
        
        /* Tabla 1 */
        table = new PdfPTable(columnWidths);        
        document.add(new Paragraph("Añadir Tabla-1 directamente a otra Tabla\n"));
        table.setTotalWidth(770F);
        table.setLockedWidth(true);
        
        buildNestedTables(table);        
        document.add(table);
        
        /* Tabla 2 */
        table = new PdfPTable(columnWidths);
        document.add(new Paragraph("Agregar tabla-2 al constructor de celdas"));
        table.setTotalWidth(770F);
        table.setLockedWidth(true);
        
        buildNestedTables2(table);
        document.add(table);
        
        /* Tabla 3 */
        table = new PdfPTable(columnWidths);
        document.add(new Paragraph("Agregar tabla-3 como elemento a una celda"));
        table.setTotalWidth(770F);
        table.setLockedWidth(true);
        
        buildNestedTables3(table);
        document.add(table);
        
        document.close();
    }
 
    private void buildNestedTables(PdfPTable outerTable) {
    	PdfPCell cell;
    	
    	PdfPTable innerTable1 = new PdfPTable(1);
        innerTable1.addCell("Celda 1.1");
        innerTable1.addCell("Celda 1.2");
        outerTable.addCell(innerTable1);
        
        PdfPTable innerTable2 = new PdfPTable(2);
        innerTable2.addCell("Celda 1.3");
        innerTable2.addCell("Celda 1.4");
        outerTable.addCell(innerTable2);
        
        cell = new PdfPCell();
        cell.setColspan(14);
        outerTable.addCell(cell);
   }
 
    private void buildNestedTables2(PdfPTable outerTable) {
    	PdfPCell cell;
    	PdfPTable innerTable1 = new PdfPTable(1);
        innerTable1.setWidthPercentage(100);
        innerTable1.addCell("Celda 2.1");
        innerTable1.addCell("Celda 2.2");
        cell = new PdfPCell(innerTable1);
        outerTable.addCell(cell);
        
        PdfPTable innerTable2 = new PdfPTable(2);
        innerTable2.setWidthPercentage(100);
        innerTable2.addCell("Celda 2.3");
        innerTable2.addCell("Celda 2.4");
        cell = new PdfPCell(innerTable2);
        outerTable.addCell(cell);
        
        cell = new PdfPCell();
        cell.setColspan(14);
        outerTable.addCell(cell);
   }
 
    private void buildNestedTables3(PdfPTable outerTable) {
    	PdfPCell cell;
        PdfPTable innerTable1 = new PdfPTable(1);
    	innerTable1.setWidthPercentage(100);
    	innerTable1.addCell("Celda 3.1");
        innerTable1.addCell("Celda 3.2");
        cell = new PdfPCell();
        cell.addElement(innerTable1);
        outerTable.addCell(cell);
        
        PdfPTable innerTable2 = new PdfPTable(2);
        innerTable2.setWidthPercentage(100);        
        innerTable2.addCell("Celda 3.3");
        innerTable2.addCell("Celda 3.4");
        cell = new PdfPCell();
        cell.addElement(innerTable2);
        outerTable.addCell(cell);
        
        cell = new PdfPCell();
        cell.setColspan(14);
        outerTable.addCell(cell);
   }
}
