//package docs.word;
//
//import java.io.FileOutputStream;
//import java.io.OutputStream;
//import java.math.BigInteger;
//
//import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
//import org.apache.poi.xwpf.usermodel.XWPFDocument;
//import org.apache.poi.xwpf.usermodel.XWPFParagraph;
//import org.apache.poi.xwpf.usermodel.XWPFRun;
//import org.apache.poi.xwpf.usermodel.XWPFTable;
//import org.apache.poi.xwpf.usermodel.XWPFTable.XWPFBorderType;
//import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPageMar;
//import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTSectPr;
//
//public class MarginDocument {
//
//	
//	public static void main(String[] args) {
//		try {
//			simpleDoc();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//	
//	protected static void simpleDoc() throws Exception {
//		String pathDoc = "/home/dothr/Documents/Tmp/marginDoc.docx";
//		
//		XWPFDocument doc = new XWPFDocument();
//
//		/*  Establecer margenes de la pagina [ 'org.apache.poi', name: 'ooxml-schemas', version: '1.3'] */
//		CTSectPr sectPr = doc.getDocument().getBody().addNewSectPr();
//		CTPageMar pageMar = sectPr.addNewPgMar();
//		pageMar.setTop(BigInteger.valueOf(560L));
//		
//		pageMar.setLeft(BigInteger.valueOf(720L));		
//		pageMar.setRight(BigInteger.valueOf(720L));
//		
//		pageMar.setBottom(BigInteger.valueOf(360L));
//		/* **** */
//		 
//		XWPFParagraph documentTitle = doc.createParagraph();		
//		
//		documentTitle.setAlignment(ParagraphAlignment.CENTER);
//		XWPFRun run = documentTitle.createRun();
//
//		run.setText("Nombre del CV");
//		run.setBold(true);
//		run.setFontFamily("Calibri");
//		run.setFontSize(13);
//		run.setColor("4F81BD"); //Azul
//
////		run.addBreak();
////
////		run.setText("Titulo de la persona");
////		run.setBold(false);
////		run.setFontFamily("Calibri");
////		run.setFontSize(12);
////		run.setColor("01110e"); //Gris casi negro
//		
//		
//		
//		
//		// create table with 3 rows and 4 columns
//		XWPFTable table = doc.createTable(3, 2);		
////		table.setInsideHBorder(XWPFBorderType.NONE, 10, 5, "f91515"); //Rojo
//		table.setInsideVBorder(XWPFBorderType.NONE, 10, 5, "15f9d0"); //AzulClaro
//		
//		table.getCTTbl().addNewTblGrid().addNewGridCol().setW(BigInteger.valueOf(3000));
//		table.getCTTbl().getTblGrid().addNewGridCol().setW(BigInteger.valueOf(7800));
//		
//		// write to first row, first column
//		XWPFParagraph p1 = table.getRow(0).getCell(0).getParagraphs().get(0);
//		p1.setAlignment(ParagraphAlignment.CENTER);
//		XWPFRun r1 = p1.createRun();
//		r1.setBold(true);
//		r1.setText("ID");
//
//		// write to first row, second column
//		XWPFParagraph p2 = table.getRow(0).getCell(1).getParagraphs().get(0);
//		p2.setAlignment(ParagraphAlignment.CENTER);
//		XWPFRun r2 = p2.createRun();
//		r2.setBold(true);
//		r2.setText("EMAIL");
//
//		// write to second row
//		table.getRow(1).getCell(0).setText("1000");
//		table.getRow(1).getCell(1).setText("contact@roytuts.com");
//
//		// write to third row
//		table.getRow(2).getCell(0).setText("1001");
//		table.getRow(2).getCell(1).setText("contact@roytuts.com");
//		
//		
//		OutputStream out = new FileOutputStream(pathDoc);
//		System.out.println("Se creó: "+pathDoc);
//		try {
//			doc.write(out);
//		} finally {
//			out.close();
//		}
//	}
//}
