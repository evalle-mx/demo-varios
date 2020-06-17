package docs.pdf;

import java.awt.Color;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;




import utily.DateUtily;
import utily.StringUtily;

import com.lowagie.text.Cell;
import com.lowagie.text.Chapter;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.ListItem;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.Section;
import com.lowagie.text.Table;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.pdf.hyphenation.TernaryTree.Iterator;

public class AforePdfBasics {
	// public class ITextTest {
	private static final String DOC_AUTHOR = "TREO.App";
	private static final String OutPath = "/home/dothr/Documents/Tmp/";	//"D:/SalidaJava/cheques/";
	private static String fileName = "ITextTest";
	private static final String tipoFuente = "Arial";
	private static final int tamFuente = 10;
	private static final String rutaFuenteArial = "files/arial.ttf"; 

	

	public static void main(String[] args) {
		
//		simpleDoc();
//		writeLines();
//		print2Pages();
//		fontFactoryStylesPDF();
//		registeredFamilies();
//		paragraphAttributes();
//		customizedPage();
//		simpleLetterExample();
		
		/*AFORE BANAMEX */
//		cellFixedCheque();
//		cellFixedHeightPDF(); // ==> OK
		

		createCheque(3);//==>
	}
	
	
	/**
	 * Imprime un simple Archivo PDF con un determinado tamanio de pagina
	 */
	public static void simpleDoc() {
		System.out.println("Printing PDF file ..");
		fileName = "simpleDoc.pdf";
		Rectangle pageSize = new Rectangle(0, 0, 238, 369);
		Document document = new Document(pageSize);
		try {
			PdfWriter.getInstance(document, new FileOutputStream(OutPath
					+ fileName));
			document.open();
			document.add(new Paragraph("Hi, this is your simple PDF file!"));
		} catch (DocumentException de) {
			System.err.println(de.getMessage());
		} catch (IOException ioe) {
			System.err.println(ioe.getMessage());
		}
		document.close();
		System.out.println("Documento creado: "+OutPath+ fileName);
	}
	
	
	/**
	 * Prueba simple de escritura por i lineas
	 */
	@SuppressWarnings("unused")
	public static void writeLines() {
		int nLines = 50;
		fileName = "write"+nLines+"Lines." + "LETTER" + "."
		+ DateUtily.thisDateFormated("ddMMss") + ".pdf";
		
		try {
			Document document = new Document(PageSize.LETTER, 1, 1, 1, 1);

			PdfWriter writer = PdfWriter.getInstance(document,
					new FileOutputStream(OutPath + fileName));
			document.open();
			document.addAuthor("TREO.App");
			document.add(new Paragraph("Primera pagina del documento."));
			for (int i = 0; i < nLines; i++) {
				document.add(new Paragraph(
						i
								+ ":123456789B123456789C123456789D123456789E123456789F123456789G123456789H123456789I123456789J123456789", // "linea "+i,
						FontFactory.getFont(FontFactory.defaultEncoding, 10
						// , Font.BOLD, new Color(255, 150, 200)
								)));
			}
			document.close();
			System.out.println("documento creado: " + OutPath + fileName);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	

	/**
	 * Ejemplifica como crear dos paginas, ademas con diferentes tamanios
	 * 1.default, 2.A3
	 */
	public static void print2Pages() {
		Document document = new Document();
		fileName = "print2Pages.pdf";
		try {
			PdfWriter.getInstance(document, new FileOutputStream(OutPath
					+ fileName ));
			document.open();
			document.add(new Paragraph("First Page."));
			document.setPageSize(PageSize.A3);
			document.newPage();
			document.add(new Paragraph("This PageSize is A3."));
		} catch (DocumentException de) {
			System.err.println(de.getMessage());
		} catch (IOException ioe) {
			System.err.println(ioe.getMessage());
		}
		document.close();
		System.out.println("documento creado: "+OutPath+ fileName);
	}
	
	/**
	 * Registra nueva fuente
	 */
	public static void fontFactoryStylesPDF() {
	    Document document = new Document();
	    fileName = "FontFactoryStylesPDF.pdf"; 
	    

	    try {
	      PdfWriter.getInstance(document, new FileOutputStream(
	          OutPath + fileName ));
	      document.open();

	      FontFactory.register(rutaFuenteArial);
	      Phrase myPhrase = new Phrase("This is font family Arial ", FontFactory.getFont("Arial", 8));
	      document.add(myPhrase);
	    } catch (Exception e) {
	      System.err.println(e.getMessage());
	    }
	    document.close();
	    System.out.println("documento creado: "+OutPath+ fileName);
	  }
	
	/**
	 * Obtiene el listado de Familia de fuentes registrada
	 */
	public static void registeredFamilies() {
	    Document document = new Document();
	    fileName = "UsingFontFactoryPDF.pdf";
	    
	    try {
	      PdfWriter.getInstance(document,  new FileOutputStream(OutPath + fileName ));
	      document.open();

	      Paragraph p = new Paragraph("Font Families", FontFactory.getFont(FontFactory.HELVETICA, 16f));
	      document.add(p);
	      FontFactory.registerDirectories();
	      TreeSet families = new TreeSet(FontFactory.getRegisteredFamilies());

	      for (java.util.Iterator i = families.iterator(); i.hasNext(); ) {
	          p = new Paragraph((String) i.next());
	          document.add(p);
	        }
	    } catch (Exception e) {
	      System.err.println(e.getMessage());
	      e.printStackTrace();
	    }

	    document.close();
	    System.out.println("documento creado: "+OutPath+ fileName);
	  }
	
	/**
	 * Muestra como utilizar los elementos de Alineacion
	 */
	public static void paragraphAttributes() {
		System.out.println("The Paragraph object");
		Document document = new Document();
		fileName = "ParagraphAttributes.pdf";
		try {
			PdfWriter.getInstance(document, new FileOutputStream(OutPath
					+ fileName));

			document.open();
			Paragraph[] p = new Paragraph[5];
			p[0] = new Paragraph("RoseIndia.net (0)");
			p[1] = new Paragraph("RoseIndia.net (1)");
			p[2] = new Paragraph("RoseIndia.net (2)");
			p[3] = new Paragraph("RoseIndia.net (3)");
			p[4] = new Paragraph("RoseIndia.net (4)");

			for (int i = 0; i < 5; i++) {
				p[i].setAlignment(Element.ALIGN_RIGHT);
				document.add(p[i]);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		document.close();
		System.out.println("documento creado: "+OutPath+ fileName);
	}

	/**
	 * Ejemplifica como crear un tamanio de pagina costumizado en Puntos
	 * @param xPoints 
	 * @param yPoints
	 */
	public static void customizedPage(int xPoints, int yPoints, String dFileName) {
		// int xPoints = 216, yPoints = 720;
		// Rectangle pageSize = new Rectangle(216, 720); //new Rectangle(0, 0, 216, 369);
		Rectangle pageSize = new Rectangle(xPoints, yPoints); // Rectangle(xPoints, yPoints);
		
		Document document = new Document(pageSize);
		fileName = dFileName; 

		try {
			PdfWriter.getInstance(document, new FileOutputStream(OutPath
					+ fileName ));
			document.open();
			document.add(new Paragraph("The size of this page is " + xPoints
					+ "*" + yPoints + " points."));
			// document.add(new
			// Paragraph("216pt / 72 points per inch = 3 inch"));
			double xInches = (double)xPoints / 72;
			document.add(new Paragraph(xPoints + "pt / 72 points per inch = "
					+ xInches + "  inch"));

			// document.add(new
			// Paragraph("720pt / 72 points per inch = 10 inch"));
			double yInches = (double)yPoints / 72;
			document.add(new Paragraph(yPoints + "pt / 72 points per inch = "
					+ yInches + " inch"));

			// document.add(new
			// Paragraph("The size of this page is 3x10 inch."));
			document.add(new Paragraph("The size of this page is " + xInches + "x"
					+ yInches + " inch."));
			// document.add(new Paragraph("3 inch x 2.54 = 7.62 cm"));
			int decimalPlaces = 1;
			BigDecimal bdTest = new BigDecimal(xInches* 2.54);
			bdTest = bdTest.setScale(decimalPlaces, BigDecimal.ROUND_HALF_UP);
			double xCms2 = bdTest.doubleValue();
			bdTest = new BigDecimal(yInches* 2.54);
			bdTest = bdTest.setScale(decimalPlaces, BigDecimal.ROUND_HALF_UP);
			double yCms2 = bdTest.doubleValue();
			document.add(new Paragraph(xInches + " inch x 2.54 = " + xCms2
					+ " cm"));
			// document.add(new Paragraph("10 inch x 2.54 = 25.4 cm"));
			document.add(new Paragraph(yInches + " inch x 2.54 = " + yCms2
					+ " cm"));
			// document.add(new
			// Paragraph("The size of this page is 7.62x25.4 cm."));
			
			document.add(new Paragraph("The size of this page is " + xCms2 + "x"
					+ yCms2 + " cm."));

		} catch (DocumentException de) {
			System.err.println(de.getMessage());
		} catch (IOException ioe) {
			System.err.println(ioe.getMessage());
		}
		document.close();
		System.out.println("document " + OutPath
				+ fileName + " created");
	}

	/**
	 * Ejemplifica como crear un tamanio de pagina costumizado en Centimetros
	 * @param xCms
	 * @param yCms
	 */
	public static void customizedPageCentimeters(double xCms, double yCms) {		

		double dXInches = xCms / 2.54;
		double dYInches = yCms / 2.54;
		System.out
				.println("dXInches: " + dXInches + ",  dYInches: " + dYInches);

		double dXPoints = dXInches * 72;
		double dYPoints = dYInches * 72;

		int xPoints = (int) dXPoints;
		int yPoints = (int) dYPoints;

		System.out.println("xPoints: " + xPoints + ",  yPoints: " + yPoints);
		String dFileName = "CustomPDFDocument."+xCms+"x"+yCms+".pdf";
		customizedPage(xPoints, yPoints, dFileName);// 216, 720

	}
	/**
	 * Metodo para probar los metodos de customizedPage
	 */
	public static void customizedPage(){
		double xCms = 21.5; // 7.62;
		double yCms = 18.0; // 25.4;
		
		customizedPageCentimeters(xCms, yCms);
	}
	

	/**
	 * Ejemplifica un documento con Capitulo, secciones, tablas y colores
	 * (Formato Avanzado)
	 */
	@SuppressWarnings("unused")
	public static void simpleLetterExample() {
		try {
			fileName = fileName + "LETTER" + ".pdf";

			Document document = new Document(PageSize.A4, 50, 50, 50, 50);

			PdfWriter writer = PdfWriter.getInstance(document,
					new FileOutputStream(OutPath + fileName));
			document.open();
			document.add(new Paragraph("Primera pagina del documento."));
			document.add(new Paragraph(
					"Mas texto en la primera pagina con diferente color y tipo de fuente.",
					FontFactory.getFont(FontFactory.COURIER, 14, Font.BOLD,
							new Color(255, 150, 200))));

			// ********************
			Paragraph title1 = new Paragraph("Capitulo 1", FontFactory.getFont(
					FontFactory.HELVETICA, 18, Font.BOLDITALIC, new Color(0, 0,
							255)));
			Chapter chapter1 = new Chapter(title1, 1);

			chapter1.setNumberDepth(0);
			Paragraph title11 = new Paragraph(
					"Esta es la Seccion 1 en Capitulo 1", FontFactory.getFont(
							FontFactory.HELVETICA, 16, Font.BOLD, new Color(
									255, 0, 0)));

			Section section1 = chapter1.addSection(title11);
			Paragraph someSectionText = new Paragraph(
					"Este texto viene como parte de la Seccion 1 del Capitulo 1.");
			section1.add(someSectionText);
			someSectionText = new Paragraph(
					"A continuacion esta una tabla de 3 X 2.");
			section1.add(someSectionText);
			Table t = new Table(3, 2);
			t.setBorderColor(new Color(220, 255, 100));
			t.setPadding(5);
			t.setSpacing(5);
			t.setBorderWidth(1);
			Cell c1 = new Cell("Header1");
			t.addCell(c1);
			c1 = new Cell("Header2");
			t.addCell(c1);
			c1 = new Cell("Header3");
			t.addCell(c1);
			t.addCell("1.1");
			t.addCell("1.2");
			t.addCell("1.3");
			section1.add(t);

			com.lowagie.text.List l = new com.lowagie.text.List(true, true, 10);
			l.add(new ListItem("1er item de la lista"));
			l.add(new ListItem("2do item de la lista"));
			section1.add(l);

			// ********************************

			document.add(chapter1);
			document.close();
			System.out.println("documento creado: " + OutPath + fileName);
		} catch (Exception e2) {
			e2.printStackTrace();
			System.out.println(e2.getMessage());
		}

	}

	public static void cellFixedHeightPDF (){
		fileName = "CellFixedHeightPDF"
				//+ DateUtily.thisDateFormated("ddMMss") 
				+ ".pdf";
		Document document = new Document(PageSize.A4);
	    try {
	      PdfWriter writer = PdfWriter.getInstance(document,  new FileOutputStream(OutPath + fileName));
	      document.open();

	      PdfPTable table = new PdfPTable(2);
	      table.setWidthPercentage(100);//establece al 100% del documento
	      table.getDefaultCell().setBorder(Rectangle.NO_BORDER);//establece border a toda la celda
	      float[] celdas = {3, (float)10.25};
	      table.setWidths(celdas);

	      
	      PdfPCell cell = new PdfPCell(new Paragraph("Cell Cell Cell Cell Cell Cell Cell Cell Cell Cell Cell Cell Cell Cell Cell Cell Cell Cell Cell Cell Cell Cell Cell Cell Cell Cell Cell Cell Cell Cell Cell Cell Cell Cell Cell Cell Cell Cell Cell Cell Cell Cell "));
	      cell.setBorder(0);
	      cell.setFixedHeight(150f);
	      cell.setBackgroundColor(Color.cyan);
	      
	      table.addCell("wrap");
	      cell.setNoWrap(false);
	      table.addCell(cell);
	      
//	      table.getDefaultCell().setBorder(Rectangle.BOX);//establece border a toda la celda
//	      table.addCell("no wrap 150f");
//	      cell.setFixedHeight(150f);
//	      cell.setNoWrap(true);
//	      table.addCell(cell);
	      
	      //table.getDefaultCell().setBorder(Rectangle.NO_BORDER);
	      PdfPCell cellBenef = new PdfPCell(new Paragraph("CellB CellB CellB CellB CellB CellB CellB CellB CellB CellB CellB CellB CellB CellB CellB CellB CellB CellB CellB CellB CellB CellB CellB CellB CellB CellB CellB CellB CellB CellB CellB CellB CellB CellB CellB CellB CellB CellB CellB CellB CellB CellB "));
	      cellBenef.setBorderWidth(0);//establece el ancho del border
	      cellBenef.setNoWrap(false);	      
	      
	      table.addCell(cellBenef);	      
	      table.addCell(cell);
	      
	      
	      
	      
	      
	      document.add(table);
	    } catch (Exception de) {
	      de.printStackTrace();
	    }
	    System.out.println("documento " + OutPath
				+ fileName + " creado ");
	    document.close();
	  }
	
	
	
	public static void cellFixedCheque(){
		fileName = "cellFixedCheque." + DateUtily.thisDateFormated("ddMMss") + ".pdf";		
		
		Rectangle pageSize = new Rectangle(0, 0, 609, 510); // Rectangle(xPoints, yPoints);
		Document document = new Document(pageSize);
		 try {
		      PdfWriter writer = PdfWriter.getInstance(document,  new FileOutputStream(OutPath + fileName));
		      document.open();

		      PdfPTable table = new PdfPTable(2);
		      table.setWidthPercentage(100);//establece al 100% del documento
		      table.getDefaultCell().setBorder(Rectangle.NO_BORDER);//establece border a toda la celda
		      float[] celdas = {15, (float)3.5};
		      table.setWidths(celdas);

		      table.addCell("   ");table.addCell("   ");
		      table.addCell("   ");table.addCell("13 de Febrero de 2012");
		      table.addCell("   ");table.addCell("   ");
		      //PdfPCell cell = new PdfPCell(new Paragraph("Text Text Text Text Text Text Text Text Text Text Text Text Text Text Text Text Text Text Text Text Text Text Text Text Text Text Text Text Text Text Text Text Text Text Text Text Text Text Text Text Text Text "));
		      PdfPCell cell = new PdfPCell(new Paragraph("$ 25,110.00"));
		      cell.setBorder(0);
		      
		      //table.addCell("wrap");
		      table.addCell("Valeria zimmerman");
		      cell.setNoWrap(false);
		      table.addCell(cell);
		      celdas[0] = 18; celdas[1] = 1;
		      table.setWidths(celdas);
		      //PdfPCell cellBenef = new PdfPCell(new Paragraph("Beneficiario Beneficiario Beneficiario Beneficiario Beneficiario Beneficiario Beneficiario Beneficiario Beneficiario Beneficiario Beneficiario Beneficiario Beneficiario Beneficiario Beneficiario Beneficiario "));
		      PdfPCell cellBenef = new PdfPCell(new Paragraph("QUINIENTOS VENTIUN MILLONES QUINIENTOS VEINTE MIL CIENTO CUARENTA Y DOS PESOS. 25/100  "));
		      //cellBenef.setBorderWidth(1);//establece el ancho del border
		      cellBenef.setNoWrap(false);	      
		      
		      table.addCell(cellBenef);	      
		      table.addCell(" ");
		      
		      document.add(table);
		    } catch (Exception de) {
		      de.printStackTrace();
		    }
		    System.out.println("document " + OutPath + fileName + " created");
		    document.close();
	}
	
	//*********************************************************************************************************
	//***********************************************   TREO    ***********************************************
	//*********************************************************************************************************
	
	
	@SuppressWarnings("unused")
	public static void createCheque(int nCheques) {
		fileName = "cheque." + DateUtily.thisDateFormated("ddMMss") + ".pdf";
		
		Font fontCheque = FontFactory.getFont("Arial", 10);
		int maxLghRenglon = 80;		
		//int nCheques = 2;
		
		double xCms = 20.8;	//21.5 // 7.62;
		double yCms = 17.3; //18.0 // 25.4;
		
		double dXInches = xCms / 2.54;
		double dYInches = yCms / 2.54;
		System.out
				.println("dXInches: " + dXInches + ",  dYInches: " + dYInches);

		double dXPoints = dXInches * 72;
		double dYPoints = dYInches * 72;

		int xPoints = (int) dXPoints;
		int yPoints = (int) dYPoints;
		System.out.println("xPoints: " + xPoints + ",  yPoints: " + yPoints);
		
		String stFecha = "01 de Enero de 2012";
		//String fecha = StringUtily.addTokenL(" ", "01 de Enero de 2012", 140);
		String stBeneficiario = "Beneficiario nuevo a de Pruebas Cheque";
		String stImporteNum = "999,999.00";
		
		
		String stImporteLetra = "NOVECIENTOS NOVENTA Y NUEVE";
		List<String> lsImporteLetra = null;
		String stImpsLeye = "* PARA ABONO EN CUENTA DEL BENEFICIARIO*";
		String stConcepto = "CONCEPTO DE PAGO";
		List<String> lsConceptos = null;
		String stChequeNum = "";
		List<String> lsAppsContables = null;
		
//		String cuenta = "1102";
//		String scta  = "01";
//		String sscta = "1001";
//		String ssscta= "A";
//		String impDebe = "$150,999.99";
//		String impHaber = "$150,999.99";
		
//		String[] appsC = { 	cuenta + "      "+scta+"     " +sscta+"      "+ssscta+"          " + impDebe + "   " + impHaber, 
//							cuenta + "      "+scta+"     " +sscta+"      "+ssscta+"          " + impDebe + "   " + impHaber
//							};
//		String appContable = cuenta + "      "+scta+"     " +sscta+"      "+ssscta+"          " + impDebe + "   " + impHaber;
//		String fullLinea = "123456789B123456789C123456789D123456789E123456789F123456789G123456789H12345678";
		
		lsImporteLetra = StringUtily.cadenaARenglones(stImporteLetra, maxLghRenglon);
		lsConceptos = StringUtily.cadenaARenglones(stConcepto, maxLghRenglon);
		lsAppsContables = new ArrayList<String>();
		lsAppsContables.add("1102|01|1001|A|$150,999.99");
		lsAppsContables.add("1101|02|1001|A|$150,999.00");
		
		try {	
			
			System.out.println("xPoints: " + xPoints + ",  yPoints: " + yPoints);
			Rectangle pageSize = new Rectangle(xPoints, yPoints); // Rectangle(xPoints, yPoints);
			Document document = new Document(pageSize, 30,40, 35,35);

			PdfWriter writer = PdfWriter.getInstance(document,
					new FileOutputStream(OutPath + fileName));
			document.open();
			document.addAuthor(DOC_AUTHOR);			
			
			for(int x=0; x< nCheques; x++){
				if(x>0){
					System.out.println("****  nueva Pagina: " + (x+1) + " *****");
					document.newPage();
				}
				
				//Phrase myPhrase = new Phrase("This is font family Arial ", FontFactory.getFont("Arial", 8));
				
				document.add(new Paragraph(" "));
				Paragraph pFecha = //new Paragraph(fecha);
				new Paragraph(stFecha, fontCheque);
//						"Texto.",
//						FontFactory.getFont(FontFactory.COURIER, 14, Font.BOLD,
//								new Color(255, 150, 200)))
				pFecha.setAlignment(Element.ALIGN_RIGHT);
				document.add(pFecha);
				document.add(new Paragraph(" ", fontCheque));
				
				PdfPTable table = new PdfPTable(2);
			      table.setWidthPercentage(100);//establece al 100% del documento
			      table.getDefaultCell().setBorder(Rectangle.NO_BORDER);//establece border a toda la celda
			      float[] celdas = {15, (float)3.5};
			      table.setWidths(celdas);
			      table.addCell(stBeneficiario);			      
			      table.addCell(stImporteNum);
			    document.add(table);
				
//				int disponible = 145 - (stBeneficiario.length()+2);
//				System.out.println("disponible: " + disponible );
//				String stBenefCant = stBeneficiario + " " + (x+1) + StringUtily.addTokenL(" ", stImporteNum, disponible
//						 ); 
//				document.add(new Paragraph(stBenefCant, fontCheque));
				
				for(int i=0; i<lsImporteLetra.size();i++){
					Paragraph pCantidad = new Paragraph((String)lsImporteLetra.get(i), fontCheque);				
					pCantidad.setAlignment(Element.ALIGN_JUSTIFIED);
					document.add(pCantidad);
				}
				
				//document.add(new Paragraph(" "));
				Paragraph pImpsLeye = new Paragraph(stImpsLeye, fontCheque);
				pImpsLeye.setAlignment(Element.ALIGN_CENTER);
				document.add( pImpsLeye );
				
				document.add(new Paragraph(" "));
				document.add(new Paragraph(" "));
				document.add(new Paragraph(" "));
//				System.out.println("tama�o de fullLinea = " + fullLinea.length() );
				//tamanio maximo de linea 79
//				Paragraph pFullLinea = new Paragraph(fullLinea, fontCheque);
				document.add(new Paragraph());
				document.add(new Paragraph(" "));
				document.add(new Paragraph(" "));
				document.add(new Paragraph(" "));
				document.add(new Paragraph(" "));
				document.add(new Paragraph(" "));
				//document.add(new Paragraph(" "));

				for(int k=0; k<lsConceptos.size();k++){
					Paragraph pConcepto = new Paragraph(lsConceptos.get(k), fontCheque);
					pConcepto.setAlignment(Element.ALIGN_LEFT);
					document.add(pConcepto);
				}
				
				stChequeNum = StringUtily.addTokenL("0", String.valueOf(x+1), 5);
				Paragraph pNumCheque = new Paragraph("Cheque No: " + stChequeNum, fontCheque);
				pNumCheque.setAlignment(Element.ALIGN_LEFT);
				document.add(pNumCheque);
				document.add(new Paragraph(" "));
				
				if(lsAppsContables!=null && lsAppsContables.size()>0){
					for(int j = 0; j< lsAppsContables.size();j++){
						String[] appsC = lsAppsContables.get(j).split("|");
								//StringUtily.getTokenizerData(lsAppsContables.get(j), "|");
						if(appsC!=null){						
								String appFull = appsC[0] + "      "+appsC[1]+"     " +appsC[2]
								                 +"      "+appsC[3]+"          " + appsC[4] + "   " 
								                 //+ appsC[5]
								                 ;
								Paragraph pAppContable = new Paragraph(appFull, fontCheque);
								pAppContable.setAlignment(Element.ALIGN_LEFT);
								document.add(new Paragraph(pAppContable));
							}
						}
					}				
			}//fin de for
			
			document.close();
			System.out.println("documento creado: " + OutPath + fileName);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
