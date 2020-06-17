package docs.word;

import java.io.File;
import java.io.FileOutputStream;

import org.apache.poi.xwpf.usermodel.VerticalAlign;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

public class FontStylesDemo {

	private static final String outPath = "/home/dothr/Documents/Tmp/";
	
	public static void main(String[] args) {
		try {
			createFile();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	protected static void createFile() throws Exception {
		 String fileName = "fontstyles.docx";
		//Blank Document
	      XWPFDocument document = new XWPFDocument(); 
	        
	      //Write the Document in file system
	      FileOutputStream out = new FileOutputStream(new File(outPath+fileName));
	        
	      //create paragraph
	      XWPFParagraph paragraph = document.createParagraph();
	        
	      //Set Bold an Italic
	      XWPFRun paragraphOneRunOne = paragraph.createRun();
	      paragraphOneRunOne.setBold(true);
	      paragraphOneRunOne.setItalic(true);
	      paragraphOneRunOne.setText("Font Style");
	      paragraphOneRunOne.addBreak();
	        
	      //Set text Position
	      XWPFRun paragraphOneRunTwo = paragraph.createRun();
	      paragraphOneRunTwo.setText("Font Style two");
	      paragraphOneRunTwo.setTextPosition(100);
	 
	      //Set Strike through and Font Size and Subscript
	      XWPFRun paragraphOneRunThree = paragraph.createRun();
	      paragraphOneRunThree.setDoubleStrikethrough(true); //setStrike(true);
	      paragraphOneRunThree.setFontSize(20);
	      paragraphOneRunThree.setSubscript(VerticalAlign.SUBSCRIPT);
	      paragraphOneRunThree.setText(" Different Font Styles");
	        
	      document.write(out);
	      out.close();
	      System.out.println(outPath+ fileName+ " written successully");
	}
	
}
