package docs.pdf.elems;

import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.ColumnText;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfStamper;
 
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class NumberPagesXofY {

	public static final String SRC = "/home/dothr/Documents/Tmp/CV-E1P235.pdf";
    public static final String DEST = "/home/dothr/Documents/Tmp/CV-E1P235-numbered.pdf";
 
    public static void main(String[] args) throws IOException, DocumentException {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new NumberPagesXofY().manipulatePdf(SRC, DEST);
    }
 
    public void manipulatePdf(String src, String dest) throws IOException, DocumentException {
        PdfReader reader = new PdfReader(src);
        int n = reader.getNumberOfPages();
        PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(dest));
        PdfContentByte pagecontent;
        for (int i = 0; i < n; ) {
            pagecontent = stamper.getOverContent(++i);
            ColumnText.showTextAligned(pagecontent, Element.ALIGN_RIGHT,
                    new Phrase(String.format("page %s of %s", i, n)), 559, 806, 0);
        }
        stamper.close();
        reader.close();
    }
}
