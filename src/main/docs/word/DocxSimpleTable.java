package docs.word;

import java.io.FileOutputStream;
import java.io.OutputStream;

import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.UnderlinePatterns;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTable.XWPFBorderType;

public class DocxSimpleTable {

	
	public static void main(String[] args) {
		try {
//			simpleTableDoc();
			simpleTableTest();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	protected static void simpleTableTest() throws Exception {
		String pathDoc = "/home/dothr/Documents/Tmp/sampleTable.docx";
		XWPFDocument doc = new XWPFDocument();
		
		// create table with 3 rows and 4 columns
		XWPFTable table = doc.createTable(3, 2);		
//		table.setInsideHBorder(XWPFBorderType.NONE, 10, 5, "f91515"); //Rojo
		table.setInsideVBorder(XWPFBorderType.NONE, 10, 5, "15f9d0"); //AzulClaro
		
		// write to first row, first column
		XWPFParagraph p1 = table.getRow(0).getCell(0).getParagraphs().get(0);
		p1.setAlignment(ParagraphAlignment.CENTER);
		XWPFRun r1 = p1.createRun();
		r1.setBold(true);
		r1.setText("ID");

		// write to first row, second column
		XWPFParagraph p2 = table.getRow(0).getCell(1).getParagraphs().get(0);
		p2.setAlignment(ParagraphAlignment.CENTER);
		XWPFRun r2 = p2.createRun();
		r2.setBold(true);
		r2.setText("EMAIL");

		// write to second row
		table.getRow(1).getCell(0).setText("1000");
		table.getRow(1).getCell(1).setText("contact@roytuts.com");

		// write to third row
		table.getRow(2).getCell(0).setText("1001");
		table.getRow(2).getCell(1).setText("contact@roytuts.com");
		
		
		OutputStream out = new FileOutputStream(pathDoc);
		System.out.println("Se creó: "+pathDoc);
		try {
			doc.write(out);
		} finally {
			out.close();
		}
	}
	
	
	/**
	 * Ejemplo de Tabla [3 filas 4 columnas] y un parrafo  
	 * @throws Exception
	 */
	protected static void simpleTableDoc() throws Exception {
		XWPFDocument doc = new XWPFDocument();
		
		// create table with 3 rows and 4 columns
		XWPFTable table = doc.createTable(3, 4);

		// write to first row, first column
		XWPFParagraph p1 = table.getRow(0).getCell(0).getParagraphs().get(0);
		p1.setAlignment(ParagraphAlignment.CENTER);
		XWPFRun r1 = p1.createRun();
		r1.setBold(true);
		r1.setText("ID");

		// write to first row, second column
		XWPFParagraph p2 = table.getRow(0).getCell(1).getParagraphs().get(0);
		p2.setAlignment(ParagraphAlignment.CENTER);
		XWPFRun r2 = p2.createRun();
		r2.setBold(true);
		r2.setText("First Name");

		// write to first row, third column
		XWPFParagraph p3 = table.getRow(0).getCell(2).getParagraphs().get(0);
		p3.setAlignment(ParagraphAlignment.CENTER);
		XWPFRun r3 = p3.createRun();
		r3.setBold(true);
		r3.setText("Last Name");

		// write to first row, fourth column
		XWPFParagraph p4 = table.getRow(0).getCell(3).getParagraphs().get(0);
		p4.setAlignment(ParagraphAlignment.CENTER);
		XWPFRun r4 = p4.createRun();
		r4.setBold(true);
		r4.setText("Email");

		// write to second row
		table.getRow(1).getCell(0).setText("1000");
		table.getRow(1).getCell(1).setText("Soumitra");
		table.getRow(1).getCell(2).setText("Roy");
		table.getRow(1).getCell(3).setText("contact@roytuts.com");

		// write to third row
		table.getRow(2).getCell(0).setText("1001");
		table.getRow(2).getCell(1).setText("Soumitra");
		table.getRow(2).getCell(2).setText("Sarkar");
		table.getRow(2).getCell(3).setText("contact@roytuts.com");

		// create a paragraph with Strike-Through text
		XWPFParagraph p5 = doc.createParagraph();
		// left alignment
		p5.setAlignment(ParagraphAlignment.LEFT);
		// wrap words
		p5.setWordWrapped(true);
		// XWPFRun object defines a region of text with a common set of
		// properties
		XWPFRun r5 = p5.createRun();
		String t5 = "Sample Paragraph Post. This is a sample Paragraph post. Sample Paragraph text is being cut and pasted again and again. This is a sample Paragraph post. peru-duellmans-poison-dart-frog.";
		r5.setText(t5);
		// make StrikeThrough
		r5.setStrikeThrough(true);

		// create a paragraph with Underlined text
		XWPFParagraph p6 = doc.createParagraph();
		// left alignment
		p6.setAlignment(ParagraphAlignment.LEFT);
		// wrap words
		p6.setWordWrapped(true);
		// XWPFRun object defines a region of text with a common set of
		// properties
		XWPFRun r6 = p6.createRun();
		String t6 = "Sample Paragraph Post. This is a sample Paragraph post. Sample Paragraph text is being cut and pasted again and again. This is a sample Paragraph post. peru-duellmans-poison-dart-frog.";
		r6.setText(t6);
		// make Underlined
		r6.setUnderline(UnderlinePatterns.SINGLE);

		OutputStream out = new FileOutputStream("/home/dothr/Documents/Tmp/DocxTable.docx");
		try {
			doc.write(out);
		} finally {
			out.close();
		}
		
	}
}
