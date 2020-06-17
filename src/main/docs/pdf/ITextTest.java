package docs.pdf;

import java.awt.Color;
import java.io.FileOutputStream;

import com.lowagie.text.Cell;
import com.lowagie.text.Chapter;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.List;
import com.lowagie.text.ListItem;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Section;
import com.lowagie.text.Table;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;

public class ITextTest {
	
	private static final String outFile = "/home/dothr/Documents/Tmp/1.ITextTest.pdf";//"D:\\SalidaJava\\ITextTest.pdf";	
	
	
	public static void main(String[] args) {
		simplePdf();
	}
	
	/**
	 * Genera un archivo pdf con un dos parrafos de texto simple 
	 */
	public static void simplePdf(){
		try {
			Document document = new Document(PageSize.A4, 50, 50, 50, 50);
//			PdfWriter writer = 
					PdfWriter.getInstance(document,
					new FileOutputStream(outFile));
			document.open();
			/* Página uno */
			document.add(new Paragraph("Primera página del documento."));
			document.add(new Paragraph(
							"Un poco más de texto en la primera página con diferente color y tipo de Fuente.",
							FontFactory.getFont(FontFactory.COURIER, 14,
									Font.BOLD, new Color(255, 150, 200))));

			Paragraph title1 = new Paragraph("Capitulo 1", FontFactory.getFont(
					FontFactory.HELVETICA, 18, Font.BOLDITALIC, new Color(0, 0,
							255)));
			Chapter chapter1 = new Chapter(title1, 1);
			chapter1.setNumberDepth(0);
			Paragraph title11 = new Paragraph("Esta es la Sección 1 en el Capítulo 1",
					FontFactory.getFont(FontFactory.HELVETICA, 16, Font.BOLD,
							new Color(255, 0, 0)));
			Section section1 = chapter1.addSection(title11);
			Paragraph someSectionText = new Paragraph(
					"Este texto viene como parte de la sección 1, capítulo 1.");
			section1.add(someSectionText);
			someSectionText = new Paragraph("A continuación una tabla de 3 X 2.");
			section1.add(someSectionText);
			Table t = new Table(3, 2);
			t.setBorderColor(new Color(220, 255, 100));
//			t.setBorder(0);
			t.setPadding(5);
			t.setSpacing(5);
//			t.setBorderWidth(1);
			Cell c1 = new Cell("header1");
			c1.setBorder(0);
			t.addCell(c1);
			c1 = new Cell("Header2");
			t.addCell(c1);
			c1 = new Cell("Header3");
			t.addCell(c1);
			t.addCell("1.1");
			t.addCell("1.2");
			t.addCell("1.3");
			section1.add(t);

			List l = new List(false, false, 10);	//true+false numeros, f+f linea, t+t letras, | # identación
			l.add(new ListItem("Primer elemento de la lista"));
			l.add(new ListItem("Segundo elemento"));
			for(int x=0; x<200;x++){
				l.add(new ListItem("Elemento "+ (x+3)+" de la lista"));
			}
			section1.add(l);
			
			/* Página dos*/
			document.add(chapter1);
			document.close();
			System.out.println("Documento generado: "+outFile);
		} catch (Exception e2) {
			System.out.println(e2.getMessage());
		}
	}
	
	
	
}
