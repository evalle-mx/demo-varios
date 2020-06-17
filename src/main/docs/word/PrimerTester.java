package docs.word;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.math.BigInteger;
import java.util.List;

import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.BreakType;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.UnderlinePatterns;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTHeight;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTShd;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTString;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTcPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTrPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTVerticalJc;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STShd;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STVerticalJc;

public class PrimerTester {

	private static final String outPath = "/home/dothr/Documents/Tmp/";
	private static final String imagenSilh = "/home/dothr/Pictures/Puka_n.jpg";
	
	public static void main(String[] args) {
		try {
			/*  SimpleTable.java  */
			createSimpleTable();			
			/*  ApacheInsertImage */
//			apacheInsertImage();
			/*  apacheCreateTable */
//			apacheCreateTable();
//			createStyledTable();
			/*  apacheFormattedText */
//			apacheFormattedText();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
 /* ************************  SimpleTable.java   ******************************** */
	public static void createSimpleTable() throws Exception { 
		String outFile = outPath+"Apache_simpleTable.docx";
	     XWPFDocument doc = new XWPFDocument(); 
	 
	     XWPFTable table = doc.createTable(3, 3); 
	 
	     table.getRow(1).getCell(1).setText("EXAMPLE OF TABLE"); 
	 
	     // table cells have a list of paragraphs; there is an initial 
	     // paragraph created when the cell is created. If you create a 
	     // paragraph in the document to put in the cell, it will also 
	     // appear in the document following the table, which is probably 
	     // not the desired result. 
	     XWPFParagraph p1 = table.getRow(0).getCell(0).getParagraphs().get(0); 
	 
	     XWPFRun r1 = p1.createRun(); 
	     r1.setBold(true); 
	     r1.setText("The quick brown fox"); 
	     r1.setItalic(true); 
	     r1.setFontFamily("Courier"); 
	     r1.setUnderline(UnderlinePatterns.DOT_DOT_DASH); 
	     r1.setTextPosition(100); 
	 
	     table.getRow(2).getCell(2).setText("only text"); 
	 
	     FileOutputStream out = new FileOutputStream(outFile); 
	     doc.write(out); 
	     out.close(); 
	     
	     System.out.println("Se genero: " + outFile );
	 } 

	/**
	  * Create a table with some row and column styling. I "manually" add the 
	  * style name to the table, but don't check to see if the style actually 
	  * exists in the document. Since I'm creating it from scratch, it obviously 
	  * won't exist. When opened in MS Word, the table style becomes "Normal". 
	  * I manually set alternating row colors. This could be done using Themes, 
	  * but that's left as an exercise for the reader. The cells in the last 
	  * column of the table have 10pt. "Courier" font. 
	  * I make no claims that this is the "right" way to do it, but it worked 
	  * for me. Given the scarcity of XWPF examples, I thought this may prove 
	  * instructive and give you ideas for your own solutions. 
	 
	  * @throws Exception 
	  */ 
	 public static void createStyledTable() throws Exception { 
		 String outFile = outPath+"Apache_styledTable.docx";
	  // Create a new document from scratch 
	     XWPFDocument doc = new XWPFDocument(); 
	  // -- OR -- 
	     // open an existing empty document with styles already defined 
	     //XWPFDocument doc = new XWPFDocument(new FileInputStream("base_document.docx")); 
	 
	  // Create a new table with 6 rows and 3 columns 
	  int nRows = 6; 
	  int nCols = 3; 
	     XWPFTable table = doc.createTable(nRows, nCols); 
	 
	     // Set the table style. If the style is not defined, the table style 
	     // will become "Normal". 
	     CTTblPr tblPr = table.getCTTbl().getTblPr(); 
	     CTString styleStr = tblPr.addNewTblStyle(); 
	     styleStr.setVal("StyledTable"); 
	 
	     // Get a list of the rows in the table 
	     List<XWPFTableRow> rows = table.getRows(); 
	     int rowCt = 0; 
	     int colCt = 0; 
	     for (XWPFTableRow row : rows) { 
	      // get table row properties (trPr) 
	      CTTrPr trPr = row.getCtRow().addNewTrPr(); 
	      // set row height; units = twentieth of a point, 360 = 0.25" 
	      CTHeight ht = trPr.addNewTrHeight(); 
	      ht.setVal(BigInteger.valueOf(360)); 
	 
	         // get the cells in this row 
	      List<XWPFTableCell> cells = row.getTableCells(); 
	         // add content to each cell 
	      for (XWPFTableCell cell : cells) { 
	       // get a table cell properties element (tcPr) 
	       CTTcPr tcpr = cell.getCTTc().addNewTcPr(); 
	       // set vertical alignment to "center" 
	       CTVerticalJc va = tcpr.addNewVAlign(); 
	       va.setVal(STVerticalJc.CENTER); 
	 
	       // create cell color element 
	       CTShd ctshd = tcpr.addNewShd(); 
	             ctshd.setColor("auto"); 
	             ctshd.setVal(STShd.CLEAR); 
	             if (rowCt == 0) { 
	              // header row 
	              ctshd.setFill("A7BFDE"); 
	             } 
	          else if (rowCt % 2 == 0) { 
	           // even row 
	              ctshd.setFill("D3DFEE"); 
	          } 
	          else { 
	           // odd row 
	              ctshd.setFill("EDF2F8"); 
	          } 
	 
	             // get 1st paragraph in cell's paragraph list 
	             XWPFParagraph para = cell.getParagraphs().get(0); 
	             // create a run to contain the content 
	             XWPFRun rh = para.createRun(); 
	             // style cell as desired 
	             if (colCt == nCols - 1) { 
	              // last column is 10pt Courier 
	              rh.setFontSize(10); 
	              rh.setFontFamily("Courier"); 
	             } 
	             if (rowCt == 0) { 
	              // header row 
	                 rh.setText("header row, col " + colCt); 
	              rh.setBold(true); 
	                 para.setAlignment(ParagraphAlignment.CENTER); 
	             } 
	          else if (rowCt % 2 == 0) { 
	           // even row 
	                 rh.setText("row " + rowCt + ", col " + colCt); 
	                 para.setAlignment(ParagraphAlignment.LEFT); 
	          } 
	          else { 
	           // odd row 
	                 rh.setText("row " + rowCt + ", col " + colCt); 
	                 para.setAlignment(ParagraphAlignment.LEFT); 
	          } 
	             colCt++; 
	      } // for cell 
	      colCt = 0; 
	      rowCt++; 
	     } // for row 
	 
	     // write the file 
	     FileOutputStream out = new FileOutputStream(outFile); 
	     doc.write(out); 
	     out.close(); 
	     
	     System.out.println("Se genero "+outFile);
	 }
	 
	 /* ************************  ApacheInsertImage.java   ******************************** */
	 public static void apacheInsertImage () throws Exception {
		// The path to the documents directory. 
	        String dataDir = outPath;
	        		//Utils.getDataDir(ApacheInsertImage.class); 
	        
	        XWPFDocument doc = new XWPFDocument(); 
	        XWPFParagraph p = doc.createParagraph(); 
	         
	        String imgFile = imagenSilh;//"aspose.jpg"; 
	        XWPFRun r = p.createRun(); 
	         
	        int format = XWPFDocument.PICTURE_TYPE_JPEG;
	        r.setText(imgFile); 
	        r.addBreak(); 
	        r.addPicture(new FileInputStream(imgFile), format, imgFile, Units.toEMU(90), Units.toEMU(108)); // 160x200 pixels 
	        r.addBreak(BreakType.PAGE); 
	 
	        FileOutputStream out = new FileOutputStream(dataDir + "Apache_ImagesInDoc.docx"); 
	        doc.write(out); 
	        out.close(); 
	      
	        System.out.println("Process Completed Successfully"); 
	 }
	 
	 /* ****************** apacheCreateTable  ******************** */
	 public static void apacheCreateTable() throws Exception {
		// The path to the documents directory. 
	        String dataDir = outPath;
	        		//Utils.getDataDir(ApacheCreateTable.class); 
	        String dataFile = dataDir + "Apache_CreateTable.doc";
	 
	        XWPFDocument document = new XWPFDocument(); 
	 
	        // New 2x2 table 
	        XWPFTable tableOne = document.createTable(); 
	        XWPFTableRow tableOneRowOne = tableOne.getRow(0); 
	        tableOneRowOne.getCell(0).setText("Hello"); 
	        tableOneRowOne.addNewTableCell().setText("World"); 
	 
	        XWPFTableRow tableOneRowTwo = tableOne.createRow(); 
	        tableOneRowTwo.getCell(0).setText("This is"); 
	        tableOneRowTwo.getCell(1).setText("a table"); 
	 
	        // Add a break between the tables 
	        document.createParagraph().createRun().addBreak(); 
	 
	        // New 3x3 table 
	        XWPFTable tableTwo = document.createTable(); 
	        XWPFTableRow tableTwoRowOne = tableTwo.getRow(0); 
	        tableTwoRowOne.getCell(0).setText("col one, row one"); 
	        tableTwoRowOne.addNewTableCell().setText("col two, row one"); 
	        tableTwoRowOne.addNewTableCell().setText("col three, row one"); 
	 
	        XWPFTableRow tableTwoRowTwo = tableTwo.createRow(); 
	        tableTwoRowTwo.getCell(0).setText("col one, row two"); 
	        tableTwoRowTwo.getCell(1).setText("col two, row two"); 
	        tableTwoRowTwo.getCell(2).setText("col three, row two"); 
	 
	        XWPFTableRow tableTwoRowThree = tableTwo.createRow(); 
	        tableTwoRowThree.getCell(0).setText("col one, row three"); 
	        tableTwoRowThree.getCell(1).setText("col two, row three"); 
	        tableTwoRowThree.getCell(2).setText("col three, row three"); 
	 
	        FileOutputStream outStream = new FileOutputStream(dataFile); 
	        document.write(outStream); 
	        outStream.close(); 
	        System.out.println("Se escribio "+dataFile);
	 }
	 
	 /* ************************* ApacheFormattedText  ***************** */
	 public static void apacheFormattedText() throws Exception {
		 
		 String dataFile = outPath + "Apache_FormattedText_Out.docx";
			
			// Create a new document from scratch
		       XWPFDocument doc = new XWPFDocument();
		       
		       // create paragraph
		       XWPFParagraph para = doc.createParagraph();
		       
		       // create a run to contain the content
		       XWPFRun rh = para.createRun();
		       
		       // Format as desired
		   		rh.setFontSize(14);
		   		rh.setFontFamily("Verdana");
		       rh.setText("This is the formatted Text");
		       rh.setColor("FFF000");
		       para.setAlignment(ParagraphAlignment.LEFT);
		       
		       // write the file
		       FileOutputStream out = new FileOutputStream(dataFile);
		       doc.write(out);
		       out.close();
		       
		       System.out.println("Process Completed Successfully " + dataFile );
	 }
}
