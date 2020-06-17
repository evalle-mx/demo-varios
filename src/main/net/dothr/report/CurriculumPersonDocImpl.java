package net.dothr.report;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.util.Iterator;

import net.dothr.dto.AcademicBackgroundDto;
import net.dothr.dto.CertificacionDto;
import net.dothr.dto.CompensacionDto;
import net.dothr.dto.ContactInfoDto;
import net.dothr.dto.CurriculumDto;
import net.dothr.dto.FileDto;
import net.dothr.dto.IdiomaDto;
import net.dothr.dto.PersonSkillDto;
import net.dothr.dto.WorkExperienceDto;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTable.XWPFBorderType;
import org.json.JSONObject;
//import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPageMar;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTSectPr;

import docs.AbstractDocCreator;

public class CurriculumPersonDocImpl extends AbstractDocCreator {
	
	private static final String fileSysDocs = "/home/dothr/app/webServer/repository/docs/X/"; //files.repdocs.temp
	
	private static String DOC_OUT_PATH = fileSysDocs + "Cv-Persona-TEST.doc";

	private static final String violetaImgPath = "/home/dothr/app/webServer/demo/selex/imagenes/p-violetta.roma.jpg";
	
	private StringBuilder sbAux = new StringBuilder();
	
	public static void main(String[] args) {
		JSONObject jsonPersona;
		try {
			jsonPersona = getJsonObject("read-45.json", 
					"/home/dothr/Documents/SELEX/JsonUI/module/curriculumManagement/");

//			System.out.println(jsonPersona);
			CurriculumDto cvDto = getCVDto(jsonPersona);
			System.out.println("cvDto: " + cvDto );
			//Fix para imagen:
			///home/dothr/app/webServer/demo/selex/imagenes/p-violetta.roma.jpg
			if(cvDto.getImgPerfil()!=null && cvDto.getImgPerfil().getUrl()!=null){
				cvDto.getImgPerfil().setUrl(violetaImgPath);
			}
			
			//* EMPLEADO EN AppTransactional:
			FileDto fileDto = new FileDto();
			fileDto.setFileName("CV.Prueba");
			fileDto.setIdSolicitante("99");
			fileDto.setAuthor("Netto");
			fileDto.setCreator("MyProjects");
			fileDto.setFileTitle("Cv Persona Netto");
			
			
			CurriculumPersonDocImpl docCreator = new CurriculumPersonDocImpl();
			docCreator.createCvDoc2(cvDto, DOC_OUT_PATH);
			//createCvDoc1(cvDto, DOC_OUT_PATH);
			
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
	/**
	 * Genera un archivo con la información en una sola columna
	 * @param dto
	 * @param outPath
	 * @return
	 * @throws Exception
	 */
	protected FileDto createCvDoc1(CurriculumDto dto, String outPath) throws Exception {
		FileDto fileDto = new FileDto();
		
		XWPFDocument doc = new XWPFDocument();
				
		 /*  Establecer margenes de la pagina [ 'org.apache.poi', name: 'ooxml-schemas', version: '1.3'] */
//			CTSectPr sectPr = doc.getDocument().getBody().addNewSectPr();
//			CTPageMar pageMar = sectPr.addNewPgMar();
//			pageMar.setTop(BigInteger.valueOf(560L));
//			
//			pageMar.setLeft(BigInteger.valueOf(720L));		
//			pageMar.setRight(BigInteger.valueOf(720L));
//			
//			pageMar.setBottom(BigInteger.valueOf(360L));
			/* **** */
		
		XWPFParagraph documentTitle = doc.createParagraph();
		documentTitle.setAlignment(ParagraphAlignment.CENTER);
		
		sbAux = new StringBuilder(dto.getNombre()!=null?dto.getNombre()+" ":"")
		.append(dto.getApellidoPaterno()!=null?dto.getApellidoPaterno()+" ":"")
		.append(dto.getApellidoMaterno()!=null?dto.getApellidoMaterno()+" ":"");
		
		mainTitle(documentTitle.createRun(), sbAux.toString(), false);
		
		XWPFParagraph pDatosMain = getParag(doc);
//	    sectionTitle(pDatos.createRun(), "Datos Personales");
		pDatosMain.setAlignment(ParagraphAlignment.CENTER);
		setDatosPersonales(pDatosMain.createRun(), dto);
		
		XWPFParagraph pContactos = getParag(doc);
	    sectionTitle(pContactos.createRun(), "Contacto");
		setContacto(pContactos.createRun(), dto);
		
		XWPFParagraph pDemograf = getParag(doc);
//	    sectionTitle(pDemograf.createRun(), "Demograficos");
		setDemograficos(pDemograf.createRun(), dto);
		
		if(dto.getEscolaridad()!=null && !dto.getEscolaridad().isEmpty()){
			XWPFParagraph pEstudios = getParag(doc);
		    sectionTitle(pEstudios.createRun(), "Formación Académica");
			setEstudios(pEstudios.createRun(), dto);
		}
		if(dto.getExperienciaLaboral()!=null && !dto.getExperienciaLaboral().isEmpty()){
			XWPFParagraph pExperiencia = getParag(doc);
		    sectionTitle(pExperiencia.createRun(), "Experiencia Laboral");
			setExperiencia(pExperiencia.createRun(), dto);
		}
		
		if(dto.getCertificacion()!=null && !dto.getCertificacion().isEmpty()){
			XWPFParagraph pCertific = getParag(doc);
		    sectionTitle(pCertific.createRun(), "Certificaciones y diplomados");
			setCertificacion(pCertific.createRun(), dto);
		}
		
		if(dto.getIdioma()!=null || dto.getHabilidad()!=null){
			XWPFParagraph pIdioma = getParag(doc);
			sectionTitle(pIdioma.createRun(), "Idiomas y habilidades");
			setIdiomaHab(pIdioma.createRun(), dto);
		}
		
		
		OutputStream out = new FileOutputStream(DOC_OUT_PATH);
		System.out.println("Se creó: "+DOC_OUT_PATH);
		try {
			doc.write(out);
		} finally {
			out.close();
			doc.close();
		}
		
		return fileDto;
	}
	
	
	
	protected FileDto createCvDoc2(CurriculumDto dto, String outPath) throws Exception {
		FileDto fileDto = new FileDto();
		
		
		
		XWPFDocument doc = new XWPFDocument();
				
		 /*  Establecer margenes de la pagina [ 'org.apache.poi', name: 'ooxml-schemas', version: '1.3'] */
//			CTSectPr sectPr = doc.getDocument().getBody().addNewSectPr();
//			CTPageMar pageMar = sectPr.addNewPgMar();
//			pageMar.setTop(BigInteger.valueOf(560L));
//			
//			pageMar.setLeft(BigInteger.valueOf(720L));		
//			pageMar.setRight(BigInteger.valueOf(720L));
//			
//			pageMar.setBottom(BigInteger.valueOf(360L));
			/* **** */
		
			XWPFParagraph documentTitle = doc.createParagraph();
			documentTitle.setAlignment(ParagraphAlignment.CENTER);
			
			sbAux = new StringBuilder(dto.getNombre()!=null?dto.getNombre()+" ":"")
			.append(dto.getApellidoPaterno()!=null?dto.getApellidoPaterno()+" ":"")
			.append(dto.getApellidoMaterno()!=null?dto.getApellidoMaterno()+" ":"");
			
			mainTitle(documentTitle.createRun(), sbAux.toString(), false);
			
			// Tabla con dos filas, dos columnas
			XWPFTable table = doc.createTable(2, 2);		
			table.setInsideHBorder(XWPFBorderType.NONE, 10, 5, "f91515"); //Rojo
			table.setInsideVBorder(XWPFBorderType.NONE, 10, 5, "15f9d0"); //AzulClaro
			table.getCTTbl().addNewTblGrid().addNewGridCol().setW(BigInteger.valueOf(3000));
			table.getCTTbl().getTblGrid().addNewGridCol().setW(BigInteger.valueOf(7800));
			
			/* FILA CERO */
			// Columna 0,0: Imagen
			XWPFParagraph pImagen = table.getRow(0).getCell(0).getParagraphs().get(0);
			pImagen.setAlignment(ParagraphAlignment.CENTER);
			XWPFRun r1 = pImagen.createRun();
			setImage(r1, dto);
			
			//Columna 0,1: Datos Principales
			XWPFParagraph pDatosMain = table.getRow(0).getCell(1).getParagraphs().get(0);
			pDatosMain.setAlignment(ParagraphAlignment.LEFT);
			setDatosPersonales(pDatosMain.createRun(), dto);
			setContacto(pDatosMain.createRun(), dto);
			
			/* FILA UNO Columna 0 (Izquierda) */
			//Contactos
			XWPFParagraph pLeft = table.getRow(1).getCell(0).getParagraphs().get(0);			
			
			//Demográficos
			setDemograficos(pLeft.createRun(), dto);
		
			/* FILA UNO Columna 1 (Derecha) */
			XWPFParagraph pRight = table.getRow(1).getCell(1).getParagraphs().get(0);
			if(dto.getEscolaridad()!=null && !dto.getEscolaridad().isEmpty()){
				 sectionTitle(pRight.createRun(), "Formación Académica");
				 setEstudios(pRight.createRun(), dto);
			}
			if(dto.getExperienciaLaboral()!=null && !dto.getExperienciaLaboral().isEmpty()){
				sectionTitle(pRight.createRun(), "Experiencia Laboral");
				setExperiencia(pRight.createRun(), dto);
			}
			if(dto.getCertificacion()!=null && !dto.getCertificacion().isEmpty()){
				sectionTitle(pRight.createRun(), "Certificaciones y diplomados");
				setCertificacion(pRight.createRun(), dto);
			}
			if(dto.getIdioma()!=null || dto.getHabilidad()!=null){
				sectionTitle(pRight.createRun(), "Idiomas y habilidades");
				setIdiomaHab(pRight.createRun(), dto);
			}		
		
		
		
		OutputStream out = new FileOutputStream(DOC_OUT_PATH);
		System.out.println("Se creó: "+DOC_OUT_PATH);
		try {
			doc.write(out);
		} finally {
			out.close();
			doc.close();
		}
		
		return fileDto;
	}
	
	
	/* ************ */
	protected void setImage(XWPFRun rImage, CurriculumDto dto) throws InvalidFormatException, IOException{
		InputStream pic=null;
		String imgPath = violetaImgPath;
		if(dto.getImgPerfil()!= null && dto.getImgPerfil().getUrl()!=null){
			imgPath = dto.getImgPerfil().getUrl();
		}
        try {
            pic = new FileInputStream(imgPath);
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();            
        }
        rImage.addBreak();
        //TODO evaluar tipo de archivo
        rImage.addPicture(pic, XWPFDocument.PICTURE_TYPE_JPEG, imgPath ,Units.toEMU(100), Units.toEMU(100));
        rImage.addBreak();
	}
	
	protected void setDatosPersonales(XWPFRun rDatos, CurriculumDto dto){
		rDatos.setFontFamily(textoFont);
		rDatos.setFontSize(textoSize);
		rDatos.setBold(true);
		rDatos.setColor(textoColor);
		
		rDatos.addBreak();
		rDatos.setText("Correo Electrónico: ");
		rDatos.setText( dto.getEmail() );
		rDatos.addBreak();
		 if(dto.getEdad()!=null){
			 rDatos.setText("Edad: ");
			 rDatos.setText(dto.getEdad()+" ");
			 rDatos.addBreak();
		 }
		 rDatos.setText("Genero: ");
		 rDatos.setText( dto.getLbGenero()!=null? dto.getLbGenero():lbGenero(dto.getIdTipoGenero()));
		 rDatos.addBreak();
	}
	
	protected void setContacto(XWPFRun rContacto, CurriculumDto dto){
		rContacto.setFontFamily(textoFont);
		rContacto.setFontSize(textoSize);
		rContacto.setColor(textoColor);
		rContacto.addBreak();
		 if(dto.getContacto()!=null && !dto.getContacto().isEmpty()){
			 ContactInfoDto contactDto;
			 Iterator<ContactInfoDto> itConts = dto.getContacto().iterator();
			 while(itConts.hasNext()){
				 contactDto = itConts.next();
				 rContacto.setText( (contactDto.getLbTipoContacto()!=null?contactDto.getLbTipoContacto():lbTipoContacto(contactDto.getIdTipoContacto()))
						 + ": ");
				 rContacto.setText((contactDto.getNumero()!=null?contactDto.getNumero():contactDto.getContacto()!=null?contactDto.getContacto():"")); //" [55] 32956775"+tab);
				 rContacto.addBreak();
			 }
		 }
		 else{
			 rContacto.setText("No hay Información de contacto disponible");
			 rContacto.addBreak();
		 }
	}
	
	protected void setDemograficos(XWPFRun rDemog, CurriculumDto dto) {
		rDemog.setFontFamily(textoFont);
		rDemog.setFontSize(textoSize);
		rDemog.setColor(textoColor);
		
		rDemog.setText("Rango Salarial: ");
		rDemog.addBreak();
		
		rDemog.setText( getCurrencyFormated( dto.getSalarioMin() ));
		rDemog.setText(" -  ");
		rDemog.setText( getCurrencyFormated( dto.getSalarioMax() ));
		rDemog.addBreak();
		
		rDemog.setText("Ubicación: ");
		rDemog.addBreak();
		if(dto.getLocalizacion()!=null && !dto.getLocalizacion().isEmpty()){
			sbAux = new StringBuilder()
        	.append(dto.getLocalizacion().get(0).getStEstado()!=null?dto.getLocalizacion().get(0).getStEstado():"").append(" ")
        	.append(dto.getLocalizacion().get(0).getStMunicipio()!=null?dto.getLocalizacion().get(0).getStMunicipio():"").append(" ")
        	.append(dto.getLocalizacion().get(0).getStColonia()!=null?dto.getLocalizacion().get(0).getStColonia():"");
			rDemog.setText(sbAux.toString());
			rDemog.addBreak();
        }
		//Edo.Civil: Soltero(a)
		rDemog.setText(getLine(26));
		rDemog.addBreak();
		
		rDemog.setText("Edo.Civil: ");
		rDemog.setText( notNull(dto.getLbEstadoCivil(), "N/D") );
		rDemog.addBreak();
		//Cambio de Domicilio: No
		rDemog.setText("Cambio de Domicilio: ");
		rDemog.setText( siNo(dto.getCambioDomicilio()) );
		rDemog.addBreak();
		//Disp. Horario: Si
		rDemog.setText("Disp. Horario: ");
		rDemog.setText( siNo(dto.getDisponibilidadHorario()) );
		rDemog.addBreak();
		//Disp. Viajar: Si, con frecuencia
		rDemog.setText("Disp. Viajar: ");
		rDemog.setText( notNull(dto.getLbDispViajar(), "N/D") );
		rDemog.addBreak();		 
	}

	protected void setEstudios(XWPFRun rEstudios, CurriculumDto dto) {
		rEstudios.setFontFamily(textoFont);
		rEstudios.setFontSize(textoSize);
		rEstudios.setColor(textoColor);
		
		if(dto.getEscolaridad()!=null){
        	AcademicBackgroundDto acadDto;
        	Iterator<AcademicBackgroundDto> itAcad = dto.getEscolaridad().iterator();
        	while(itAcad.hasNext()){
        		acadDto = itAcad.next();
        		rEstudios.setText(notNull(acadDto.getTitulo()));
    			rEstudios.addBreak();
        		rEstudios.setText(notNull(acadDto.getAnioInicio() + " - " +notNull(acadDto.getAnioFin()) ));
    			rEstudios.addBreak();
        		rEstudios.setText(notNull(acadDto.getNombreInstitucion()));
    			rEstudios.addBreak();
    			rEstudios.addBreak();
        	}
		}
		else{
			rEstudios.setText("No hay Información de Escolaridad");
			rEstudios.addBreak();
		}
	}
	
	protected void setExperiencia(XWPFRun rExperiencia, CurriculumDto dto) {
		rExperiencia.setFontFamily(textoFont);
		rExperiencia.setFontSize(textoSize);
		rExperiencia.setColor(textoColor);
		
		if(dto.getExperienciaLaboral()!=null){
			WorkExperienceDto expLabDto;
        	Iterator<WorkExperienceDto> itExpLab = dto.getExperienciaLaboral().iterator();
        	while(itExpLab.hasNext()){
        		expLabDto = itExpLab.next();
        		rExperiencia.setText(notNull( expLabDto.getNombreEmpresa() ));
        		rExperiencia.setText("/ "+notNull( expLabDto.getPuesto() ));
    			rExperiencia.addBreak();
        		rExperiencia.setText(notNull(expLabDto.getAnioInicio() + " - " +notNull(expLabDto.getAnioFin()) ));
    			rExperiencia.addBreak();
        		rExperiencia.setText(notNull(expLabDto.getTexto()));
    			rExperiencia.addBreak();
    			if(expLabDto.getMotivoSeparacion()!=null){
    				rExperiencia.setText( "Motivo de Separación: ");
            		rExperiencia.setText( expLabDto.getMotivoSeparacion() );
            		rExperiencia.addBreak();
    			}
    			rExperiencia.addBreak();
        	}
		}
		else{
			rExperiencia.setText("No hay Información de Experiencia Laboral");
			rExperiencia.addBreak();
		}
	}
	
	protected void setCertificacion(XWPFRun rCertif, CurriculumDto dto) {
		rCertif.setFontFamily(textoFont);
		rCertif.setFontSize(textoSize);
		rCertif.setColor(textoColor);
		
		if(dto.getCertificacion()!=null){
        	CertificacionDto certDto;
        	Iterator<CertificacionDto> itCert = dto.getCertificacion().iterator();
        	while(itCert.hasNext()){
        		certDto = itCert.next();
        		rCertif.setText(notNull(certDto.getTituloCert()));
        		if(certDto.getInstitucion()!=null){
        			rCertif.setText( " " + notNull(certDto.getInstitucion()));
        		}
    			rCertif.addBreak();
        	}
		}
	}
	
	protected void setIdiomaHab(XWPFRun rLang, CurriculumDto dto) {
		rLang.setFontFamily(textoFont);
		rLang.setFontSize(textoSize);
		rLang.setColor(textoColor);
		
		if(dto.getIdioma()!=null){
			Iterator<IdiomaDto> itIdioma = dto.getIdioma().iterator();
        	IdiomaDto idiomaDto;
        	while(itIdioma.hasNext()){
        		idiomaDto = itIdioma.next();
        		rLang.setText(notNull(idiomaDto.getLbIdioma()));
        		rLang.setText( " [" + notNull(idiomaDto.getLbDominio())+ "] ");
    			rLang.addBreak();
        	}
		}
		if(dto.getHabilidad()!=null){
			Iterator<PersonSkillDto> itSkill = dto.getHabilidad().iterator();
        	PersonSkillDto skillDto;
        	while(itSkill.hasNext()){
        		skillDto = itSkill.next();
        		rLang.setText(notNull(skillDto.getDescripcion()));
        		rLang.setText( " [" + notNull(skillDto.getLbDominio())+ "] ");
    			rLang.addBreak();
        	}
		}
	}
}
