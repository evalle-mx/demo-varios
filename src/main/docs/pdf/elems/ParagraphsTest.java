package docs.pdf.elems;

import com.lowagie.text.Document;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;
import java.io.File;
import java.io.FileOutputStream;

public class ParagraphsTest {

	private static final String OutPath = "/home/dothr/Documents/Tmp/";
	//private static final String DEST = "";
	
	
	public static void main(String[] args) {
		try {
			testParagraphSpacingBefore();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * http://developers.itextpdf.com/examples/itext5-building-blocks/phrase-and-paragraph-examples
	 * @throws Exception
	 */
	public static void testParagraphSpacingBefore() throws Exception {
		String filename = OutPath+"1.ParagraphSpacingBefore.pdf";
		File file = new File(filename);
        file.getParentFile().mkdirs();
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(filename));
        document.open();
        Paragraph paragraph1 = new Paragraph("First paragraph");
        Paragraph paragraph2 = new Paragraph("Second paragraph");
        paragraph2.setSpacingBefore(380f);
        document.add(paragraph1);
        document.add(paragraph2);
        document.close();
        System.out.println("Documento generado: "+filename);
	}
}
