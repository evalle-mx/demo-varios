package net.dothr.report;

import java.awt.Color;
import java.io.FileOutputStream;
import java.util.Iterator;

import org.json.JSONObject;

import net.dothr.dto.AcademicBackgroundDto;
import net.dothr.dto.CertificacionDto;
import net.dothr.dto.ContactInfoDto;
import net.dothr.dto.CurriculumDto;
import net.dothr.dto.FileDto;
import net.dothr.dto.IdiomaDto;
import net.dothr.dto.PersonSkillDto;
import net.dothr.dto.WorkExperienceDto;
import docs.AbstractPDFCreator;

import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Image;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

public class CurriculumPersonPDFImpl extends AbstractPDFCreator {

	private StringBuilder sbtmp;
	
	private static final String OutPath = "/home/dothr/app/webServer/repository/docs/X/";
	private static final String httpServerLoc = "http://localhost/repository/docs/X/";
	private static final String defaultImgPath = "/home/dothr/app/webServer/resources/dothr/images/silh.jpg";
	private static final String violetaImgPath = "/home/dothr/app/webServer/demo/selex/imagenes/p-violetta.roma.jpg";
	
	private static float[] columnWidths = {3, (float)10.25 };
	private static float imgX = 80f;
	private static float imgY = 50f;

	private static Color colorImgP = new Color(0x2E2E2E);
	private static Color colorDemog = new Color(0xBFBFBF);
	private static Color colorProfes = new Color(0xF4F5F4);
	
	/* Versión Grises */
	private static final Font fontTituloSDemo = FontFactory.getFont(
			FontFactory.HELVETICA_BOLD, 
			12, 
			Font.NORMAL, 
			new Color(0xF4F5F4)
			);
	private static final Font fontTxtWhiteMd = FontFactory.getFont(
			FontFactory.HELVETICA_BOLD, 
			9, 
			Font.NORMAL, 
			new Color(0xF4F5F4)	//0, 0, 255)
			);
	private static final Font fontTxtWhiteSm = FontFactory.getFont(
			FontFactory.HELVETICA_BOLD, 
			8, 
			Font.NORMAL, 
			new Color(0xF4F5F4)	//0, 0, 255)
			);
	/* Columna Datos Profesionales */
	private static final Font fontNombre = FontFactory.getFont(
			FontFactory.HELVETICA_BOLDOBLIQUE, 
			14, 
			Font.NORMAL, 
			new Color(0x424242)	//0, 0, 255)
			);
	private static final Font fontTituloSPr = FontFactory.getFont(
			FontFactory.HELVETICA_BOLD, 
			14, 
			Font.NORMAL, 
			new Color(0x428BCA)	//0, 0, 255)
			);
	private static final Font fontTitulo = FontFactory.getFont(
			FontFactory.HELVETICA_BOLD, 
			11, 
			Font.NORMAL, 
			new Color(0x151515)
			);
	private static final Font fontFecha = FontFactory.getFont(
			FontFactory.HELVETICA, 
			10, 
			Font.NORMAL, 
			new Color(0x848484)
			);
	
	private static final Font fontTxtNormalBold = FontFactory.getFont(
			FontFactory.HELVETICA, 
			10, 
			Font.BOLD, 
			new Color(0x424242)
			);
	private static final Font fontTxtNormal = FontFactory.getFont(
			FontFactory.HELVETICA, 
			10, 
			Font.NORMAL, 
			new Color(0x424242)
			);
	private static final Font fontEmpresa = FontFactory.getFont(
			FontFactory.HELVETICA_BOLD, 
			11, 
			Font.UNDERLINE, 
			new Color(0x151515)
			);
	private static final Font fontDominio = FontFactory.getFont(
			FontFactory.HELVETICA_BOLD, 
			10, 
			Font.NORMAL, 
			new Color(0x151515)
			);
	
	private static final String sepDemog = "____________________\n";
	private static final String sepProfs = "________________________________________________________________________";
	//*/
	
	public static void main(String[] args) {
		try {
			JSONObject jsonPersona = getJsonObject("read-45.json", 
					"/home/dothr/Documents/SELEX/JsonUI/module/curriculumManagement/");
//			System.out.println(jsonPersona);
			CurriculumDto cvDto = getCVDto(jsonPersona);
			System.out.println("cvDto: " + cvDto );
			//Fix para imagen:
			///home/dothr/app/webServer/demo/selex/imagenes/p-violetta.roma.jpg
			if(cvDto.getImgPerfil()!=null && cvDto.getImgPerfil().getUrl()!=null){
				cvDto.getImgPerfil().setUrl(violetaImgPath);
			}
			
			CurriculumPersonPDFImpl cvPdf = new CurriculumPersonPDFImpl();
			cvPdf.createPdfDotHR("CV-Prueba.pdf", cvDto);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	
	/**
	 * Metodo principal de Creación de PDF (TIpo CV) a partir de un 
	 * Objeto CurriculumDto, que regresa un Objeto FileDto con url del archivo Creado o Error al generar
	 * @param dest
	 * @param cvPersona
	 * @return
	 */
	public FileDto createPdfDotHR(String fileName, CurriculumDto cvPersona) {
		FileDto fileDto = new FileDto();
		
		try{

	        Document document =  new Document(tamPag);
	        
	        PdfWriter.getInstance(document, new FileOutputStream(OutPath+fileName));
	        document.open();        
	        
	        /* >>>>>>>>>  ARMADO DEL DOCUMENTO  <<<<<<<<<<<<< */
	        PdfPTable mainTable;	                
	        
	        /* ****   TABLA PRINCIPAL   **** */
	        mainTable = new PdfPTable(columnWidths);
	        mainTable.setWidthPercentage(100);
	        mainTable.setTotalWidth(pagWidth);
	        mainTable.setLockedWidth(true);

	        /* ********* INSERCIÓN DE SECCIONES (celdas) ******* */
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
	        cell.setBackgroundColor(colorDemog);
	        cell.setBorderWidth(Rectangle.NO_BORDER);
	        /* Inserta celda en Columna Uno de la tabla mayor */
	        mainTable.addCell(cell);
	        
	        PdfPTable innerTable2 = new PdfPTable(1);
	        innerTable2.setSpacingAfter(5);
	        innerTable2.setWidthPercentage(80);
	        /* subCelda de Datos Profesionales */
	        subCell = getExpCell(cvPersona);
	        innerTable2.addCell(subCell);
	        cell = new PdfPCell(innerTable2);
	        cell.setBackgroundColor(colorProfes);
	        cell.setBorderWidth(Rectangle.NO_BORDER);
	        /* Inserta celda en Columna Dos de la tabla mayor */
	        mainTable.addCell(cell);
	        
	        document.add(mainTable);
	        
	        /* >>>>>>>>>>> FIN DE CUERPO DEL ARMADO DE DOCUMENTO  <<<<<<<<<<<<< */
	        document.close();
	        
	        //fileDto.setUrl(dest.replace("/home/dothr/app/webServer", "http://localhost"));	//convertir a url	        
	        fileDto.setUrl(httpServerLoc+fileName);	//convertir a url
		}catch (Exception ex){
			ex.printStackTrace();
			fileDto.setUrl(null);
			fileDto.setCode("000");
			fileDto.setType("F");
			fileDto.setMessage("Error: "+ex.getMessage());
		}
		
		if(fileDto.getUrl()==null){
			fileDto.setCode("000");
			fileDto.setType("E");
			fileDto.setMessage("Error: al crear el Documento");
		}
		
		return fileDto;
    }
	
	
	/**
	 * Segmento de Imagen de Perfil y datos Personales
	 * @param cvPersona
	 * @return
	 * @throws Exception
	 */
	protected PdfPCell getImgCell(CurriculumDto cvPersona) throws Exception{
		PdfPCell subCell = new PdfPCell();
		Image img;
		sbtmp = new StringBuilder();
		String imgPath = cvPersona.getImgPerfil()!=null&&cvPersona.getImgPerfil().getUrl()!=null? cvPersona.getImgPerfil().getUrl(): defaultImgPath;
        subCell.setBorderWidth(Rectangle.NO_BORDER);
        subCell.setBackgroundColor(colorImgP);
        try{
        	img = Image.getInstance(imgPath);
        }catch (Exception eImg){
        	//log4j.error("Error al obtener imagen de Usuario, empleando Default.", eImg);
        	eImg.printStackTrace();
        	System.out.println("Error al obtener imagen de Usuario, empleando Default." + eImg.getMessage());
        	img = Image.getInstance(defaultImgPath);
        }
        
        img.scaleAbsolute(imgX, imgY);
        	//scaleAbsolute(80f, 50f);
        img.setSpacingBefore(5f);
        img.setSpacingAfter(0f);
        img.setAlignment(Element.ALIGN_MIDDLE);
        subCell.addElement(img);
        /* Datos Persona */
		
		Paragraph imgParag = new Paragraph();
        imgParag.setSpacingBefore(1f);
        imgParag.setSpacingAfter(8f);
        sbtmp.append(cvPersona.getNombre()!=null?cvPersona.getNombre()+" ":"")
        .append(cvPersona.getApellidoPaterno()!=null?cvPersona.getApellidoPaterno()+" ":"")
        .append(cvPersona.getApellidoMaterno()!=null?cvPersona.getApellidoMaterno()+"":"")
        .append("\n");
        
        imgParag.add(new Chunk(sbtmp.toString(), fontTxtWhiteMd) );
        if(cvPersona.getLbGradoMax()!=null){
        	imgParag.add(new Chunk(" Titulo: ", fontTxtWhiteMd) );
            imgParag.add(new Chunk(cvPersona.getLbGradoMax()+"\n", fontTxtWhiteSm) );
        }
        if(cvPersona.getEdad()!=null){
        	imgParag.add(new Chunk(" Edad: ", fontTxtWhiteMd) );
            imgParag.add(new Chunk(cvPersona.getEdad()+"\n", fontTxtWhiteSm) );
        }
        if(cvPersona.getLbGenero()!=null){
        	imgParag.add(new Chunk(" Genero: ", fontTxtWhiteMd) );
            imgParag.add(new Chunk(cvPersona.getLbGenero()+"\n", fontTxtWhiteSm) );
        }
        
        /* *** */
        subCell.addElement(imgParag);
        return subCell;
	}
	
	/**
	 * Segmento datos Demograficos
	 * @param cvPersona
	 * @return
	 */
	protected PdfPCell getDemogCell(CurriculumDto cvPersona) throws Exception {
		PdfPCell subCell = new PdfPCell();
        subCell.setBorderWidth(Rectangle.NO_BORDER);
        Paragraph dataParag = new Paragraph();
        dataParag.setSpacingBefore(10f);
		dataParag.setSpacingAfter(8f);
		boolean addSeparador = false;
		
		/* Contacto(s) */
		dataParag.add(new Chunk("Contacto:\n", fontTxtWhiteMd) );
		dataParag.add(new Chunk(filtXLongLine(cvPersona.getEmail())+"\n", fontTxtWhiteSm));
		if(cvPersona.getContacto()!=null){
			
			ContactInfoDto dto;
			Iterator<ContactInfoDto> itCont = cvPersona.getContacto().iterator();
			while(itCont.hasNext()){
				dto = itCont.next();				
				//TODO agregar Icono con dto.getIdTipoContacto()
				sbtmp = new StringBuilder();
				sbtmp
				.append(dto.getCodigoArea()!=null?"["+dto.getCodigoArea()+"] ":" " )
				.append(dto.getNumero()!=null?dto.getNumero():" " )
				.append(dto.getContacto()!=null?dto.getContacto():" " )
				.append(dto.getAdicional()!=null?" Ext: "+dto.getAdicional():"")
				.append("\n");
				dataParag.add(new Chunk(sbtmp.toString(), fontTxtWhiteSm));
			}
		}
        dataParag.add(new Chunk(sepDemog, fontTxtWhiteSm) );
        
        /* Salario & Ubicacion */
        if(cvPersona.getSalarioMin()!=null || cvPersona.getSalarioMax()!=null){
        	dataParag.add(new Chunk("Rango Salarial:\n", fontTxtWhiteMd) );
            dataParag.add(new Chunk( getCurrencyFormated(cvPersona.getSalarioMin()) + " - " + 
            		getCurrencyFormated(cvPersona.getSalarioMax()) +"\n", fontTxtWhiteSm) );
            addSeparador=true;
        }
        if(cvPersona.getLocalizacion()!=null && !cvPersona.getLocalizacion().isEmpty()){
        	sbtmp = new StringBuilder();
        	sbtmp
        	.append(cvPersona.getLocalizacion().get(0).getStEstado()!=null?cvPersona.getLocalizacion().get(0).getStEstado():"").append(" ")
        	.append(cvPersona.getLocalizacion().get(0).getStMunicipio()!=null?cvPersona.getLocalizacion().get(0).getStMunicipio():"").append(" ")
        	.append(cvPersona.getLocalizacion().get(0).getStColonia()!=null?cvPersona.getLocalizacion().get(0).getStColonia():"");
        	dataParag.add(new Chunk("Ubicación:\n", fontTxtWhiteMd) );
            dataParag.add(new Chunk(
            		sbtmp.toString()
            		//{{cvpersona.domicilio.stEstado}} {{cvpersona.domicilio.stMunicipio}}, {{cvpersona.domicilio.stColonia}}
            		+"\n", fontTxtWhiteSm) );
            addSeparador=true;
        }
        if(addSeparador){
        	dataParag.add(new Chunk(sepDemog, fontTxtWhiteSm) );
        	addSeparador=false;
        }
        
        /* Demograficos */
        if(cvPersona.getLbEstadoCivil()!=null){
        	dataParag.add(new Chunk("Edo.Civil: ", fontTxtWhiteMd) );
            dataParag.add(new Chunk(cvPersona.getLbEstadoCivil()+"\n", fontTxtWhiteSm) );
            addSeparador=true;
        }
        if(cvPersona.getCambioDomicilio()!=null){
        	dataParag.add(new Chunk("Cambio de Domicilio: ", fontTxtWhiteMd) );
            dataParag.add(new Chunk(siNo(cvPersona.getCambioDomicilio())+"\n", fontTxtWhiteSm) );
            addSeparador=true;
        }
        if(cvPersona.getDisponibilidadHorario()!=null){            
            dataParag.add(new Chunk("Disp. Horario: ", fontTxtWhiteMd) );
            dataParag.add(new Chunk(siNo(cvPersona.getDisponibilidadHorario())+"\n", fontTxtWhiteSm) );
            addSeparador=true;
        }
        if(cvPersona.getLbDispViajar()!=null){            
        	dataParag.add(new Chunk("Disp. Viajar: ", fontTxtWhiteMd) );
            dataParag.add(new Chunk(cvPersona.getLbDispViajar()+"\n", fontTxtWhiteSm) );
            addSeparador=true;
        }
        
        if(addSeparador){
        	dataParag.add(new Chunk(sepDemog, fontTxtWhiteSm) );
        	addSeparador=false;
        }
        
        subCell.addElement(dataParag);
        return subCell;
	}
	
	/**
	 * Segmento de Datos Profesionales (Experiencias, Escolaridades, idiomas y Habilidades)
	 * @param cvPersona
	 * @return
	 */
	protected PdfPCell getExpCell(CurriculumDto cvPersona) throws Exception {
		PdfPCell subCell = new PdfPCell(), tmpCell;
        subCell.setBorderWidth(Rectangle.NO_BORDER);
        boolean addSeparador=false;
        
        if(cvPersona.getNombre()!=null || cvPersona.getApellidoPaterno()!=null){
            sbtmp = new StringBuilder(cvPersona.getNombre()!=null?cvPersona.getNombre()+" ":"")
            .append(cvPersona.getApellidoPaterno()!=null?cvPersona.getApellidoPaterno()+" ":"")
            .append(cvPersona.getApellidoMaterno()!=null?cvPersona.getApellidoMaterno()+"\n":"");
            
            subCell.addElement( gTxtSecc(sbtmp.toString(), 0) );
        }
        
        /* section Academic */
        if(cvPersona.getEscolaridad()!=null){
        	addSeparador=true;
        	AcademicBackgroundDto acadDto;
        	subCell.addElement(gTxtSecc("Formación Académica", 1));
        	Iterator<AcademicBackgroundDto> itAcad = cvPersona.getEscolaridad().iterator();
        	while(itAcad.hasNext()){
        		acadDto = itAcad.next();
        		PdfPTable expTable = new PdfPTable(2);
    			expTable.setWidthPercentage(100);
    			tmpCell = new PdfPCell();
    			tmpCell.setBorderWidth(Rectangle.NO_BORDER);
    			tmpCell.addElement(gTxtSecc(filtSinDef(acadDto.getTitulo()), 2));
    			
    			expTable.addCell(tmpCell);
    			tmpCell = new PdfPCell();
    			tmpCell.setBorderWidth(Rectangle.NO_BORDER);
    			tmpCell.addElement(gTxtSecc(filtFechaRang(acadDto.getAnioInicio(), acadDto.getAnioFin(), null), 3));
    			expTable.addCell(tmpCell);
    			subCell.addElement(expTable);
    			
    			expTable = new PdfPTable(1);
    			expTable.setWidthPercentage(100);
    			tmpCell = new PdfPCell();
    			tmpCell.setBorderWidth(Rectangle.NO_BORDER);
    			tmpCell.addElement(gTxtSecc(filtSinDef(acadDto.getNombreInstitucion()), 4));
    			expTable.addCell(tmpCell);
    			subCell.addElement(expTable);
    			
    			expTable = new PdfPTable(1);
    			expTable.setWidthPercentage(100);
    			tmpCell = new PdfPCell();
    			tmpCell.setBorderWidth(Rectangle.NO_BORDER);
    			tmpCell.addElement(gTxtSecc(filtNotNull(acadDto.getTexto()), 99));
    			expTable.addCell(tmpCell);
    			subCell.addElement(expTable);
        	}
        	if(addSeparador){
        		subCell.addElement(gTxtSecc(sepProfs, 3));
             	addSeparador=false;
            }
        }
		
		/* section WorkExperience */
        if(cvPersona.getExperienciaLaboral()!=null){
        	addSeparador=true;
        	subCell.addElement(gTxtSecc("Experiencia Laboral", 1));
        	Iterator<WorkExperienceDto> itExpLab = cvPersona.getExperienciaLaboral().iterator();
        	WorkExperienceDto expLabDto;
        	while(itExpLab.hasNext()){
        		expLabDto = itExpLab.next();
        		PdfPTable expTable = new PdfPTable(2);
    			expTable.setWidthPercentage(100);
    			tmpCell = new PdfPCell();
    			tmpCell.setBorderWidth(Rectangle.NO_BORDER);
    			tmpCell.addElement(gTxtSecc(filtSinDef(expLabDto.getPuesto()), 2));
    			
    			expTable.addCell(tmpCell);
    			tmpCell = new PdfPCell();
    			tmpCell.setBorderWidth(Rectangle.NO_BORDER);
    			tmpCell.addElement(gTxtSecc(filtFechaRang(expLabDto.getAnioInicio(), expLabDto.getAnioFin(), expLabDto.getTrabajoActual()), 3));
    			expTable.addCell(tmpCell);
    			subCell.addElement(expTable);
    			
    			expTable = new PdfPTable(1);
    			expTable.setWidthPercentage(100);
    			tmpCell = new PdfPCell();
    			tmpCell.setBorderWidth(Rectangle.NO_BORDER);
    			tmpCell.addElement(gTxtSecc(filtSinDef(expLabDto.getNombreEmpresa()), 4));//4
    			expTable.addCell(tmpCell);
    			subCell.addElement(expTable);
    			
    			expTable = new PdfPTable(1);
    			expTable.setWidthPercentage(100);
    			tmpCell = new PdfPCell();
    			tmpCell.setBorderWidth(Rectangle.NO_BORDER);
    			tmpCell.addElement(gTxtSecc(filtNotNull(expLabDto.getTexto()), 99));
    			expTable.addCell(tmpCell);
    			subCell.addElement(expTable);
    			
    			if(expLabDto.getMotivoSeparacion()!=null){
    				expTable = new PdfPTable(1);
        			expTable.setWidthPercentage(100);					
        			Paragraph p = new Paragraph();			
        			Chunk chunk1 = new Chunk("Motivo Separación: ", fontTxtNormalBold);
        			Chunk chunk2 = new Chunk(expLabDto.getMotivoSeparacion()+"\n", fontTxtNormal);
        			p.add(chunk1);p.add(chunk2);
        			tmpCell = new PdfPCell(p);
        			tmpCell.setBorderWidth(Rectangle.NO_BORDER);
        			expTable.addCell(tmpCell);
        			subCell.addElement(expTable);
    			}
        	}
        	if(addSeparador){
        		subCell.addElement(gTxtSecc(sepProfs, 3));
             	addSeparador=false;
            }
        }
		
		/* section Certificaciones */
        if(cvPersona.getCertificacion()!=null){
        	addSeparador=true;
        	subCell.addElement(gTxtSecc("Certificaciones", 1));
        	Iterator<CertificacionDto> itCert = cvPersona.getCertificacion().iterator();
        	CertificacionDto certDto;
        	while(itCert.hasNext()){
        		certDto = itCert.next();
        		PdfPTable expTable = new PdfPTable(1);
    			expTable.setWidthPercentage(100);
    			tmpCell = new PdfPCell();
    			tmpCell.setBorderWidth(Rectangle.NO_BORDER);
    			tmpCell.addElement(gTxtSecc(filtSinDef(certDto.getTituloCert()), 2));
    			expTable.addCell(tmpCell);
    			subCell.addElement(expTable);
        	}
        	if(addSeparador){
        		subCell.addElement(gTxtSecc(sepProfs, 3));
             	addSeparador=false;
            }
        }		
		
		/* section IDIomas/Habilidades */
        if(cvPersona.getIdioma()!=null || cvPersona.getHabilidad()!=null){
        	subCell.addElement(gTxtSecc("Idiomas y Habilidades", 1));
        	addSeparador=true;
        	//Idiomas
            if(cvPersona.getIdioma()!=null){        	
            	Iterator<IdiomaDto> itIdioma = cvPersona.getIdioma().iterator();
            	IdiomaDto idiomaDto;
            	while(itIdioma.hasNext()){
            		idiomaDto = itIdioma.next();
            		PdfPTable expTable = new PdfPTable(3);
        			expTable.setWidthPercentage(100);
        			tmpCell = new PdfPCell();
        			tmpCell.setBorderWidth(Rectangle.NO_BORDER);
        			tmpCell.addElement(gTxtSecc(filtSinDef(idiomaDto.getLbIdioma()), 99));
        			
        			expTable.addCell(tmpCell);
        			tmpCell = new PdfPCell();
        			tmpCell.setColspan(2);
        			tmpCell.setBorderWidth(Rectangle.NO_BORDER);
        			tmpCell.addElement(gTxtSecc(filtNotNull(idiomaDto.getLbDominio()), 5));
        			expTable.addCell(tmpCell);
        			subCell.addElement(expTable);
            	}
            }
            if(cvPersona.getHabilidad()!=null){
            	Iterator<PersonSkillDto> itSkill = cvPersona.getHabilidad().iterator();
            	PersonSkillDto skillDto;
            	while(itSkill.hasNext()){
            		skillDto = itSkill.next();
            		PdfPTable expTable = new PdfPTable(3);
        			expTable.setWidthPercentage(100);
        			tmpCell = new PdfPCell();
        			tmpCell.setBorderWidth(Rectangle.NO_BORDER);
        			tmpCell.addElement(gTxtSecc(filtNotNull(skillDto.getDescripcion()), 99));
        			
        			expTable.addCell(tmpCell);
        			tmpCell = new PdfPCell();
        			tmpCell.setColspan(2);
        			tmpCell.setBorderWidth(Rectangle.NO_BORDER);
        			tmpCell.addElement(gTxtSecc(filtNotNull(skillDto.getLbDominio()), 5));
        			expTable.addCell(tmpCell);
        			subCell.addElement(expTable);
            	}
            }
        }
//		subCell.addElement(gTxtSecc(sepProfs, 3));
		return subCell;
	}
	
	/**
	 * Genera parrafo a partir de un texto y un tipo <br>
	 * 1=>TítuloSección<br> 2=>Título exp/Acad<br> 3=>fecha <br>4 Empresa<br> x=>Texto
	 * @param texto
	 * @param type
	 * @return
	 */
	private Paragraph gTxtSecc(String texto, int type){
		Paragraph expParag = new Paragraph();
		if(type==-1){ //TítuloSecciónDemografico
			expParag.setFont(fontTituloSDemo);
			texto.toUpperCase();
			expParag.setAlignment(Element.ALIGN_LEFT);
		}else if(type==0){ //TítuloSecciónDemografico
			expParag.setFont(fontNombre);
			expParag.setAlignment(Element.ALIGN_CENTER);
		}else if(type==1){ //TítuloSección
			expParag.setFont(fontTituloSPr);
			texto.toUpperCase();
			expParag.setAlignment(Element.ALIGN_LEFT);
		}else if(type==2){ //Título exp/Acad
			expParag.setFont(fontTitulo);
			expParag.setAlignment(Element.ALIGN_LEFT);
		}
		else if(type==3 ){ //fecha
			expParag.setFont(fontFecha);
			expParag.setAlignment(Element.ALIGN_RIGHT);
		}
		else if(type==4){ //Empresa/Institucion
			expParag.setFont(fontEmpresa);
			expParag.setAlignment(Element.ALIGN_LEFT);
		}
		else if(type==5){ //Empresa/Institucion
			expParag.setFont(fontDominio);
			expParag.setAlignment(Element.ALIGN_LEFT);
		}
		

		else { // Texto/Default
			expParag.setFont(fontTxtNormal);
		}
		expParag.setSpacingBefore(5f);
		expParag.setSpacingAfter(5f);
		expParag.add(texto);
		return expParag;
	}
}
