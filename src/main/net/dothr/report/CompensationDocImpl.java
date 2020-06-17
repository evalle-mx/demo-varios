package net.dothr.report;

import java.io.FileOutputStream;
import java.util.Iterator;

import net.dothr.dto.CompensacionDto;
import net.dothr.dto.FileDto;
import net.dothr.dto.BonoDto;
import net.dothr.dto.ContactInfoDto;
import net.dothr.dto.Mensaje;
import net.dothr.dto.ReferenceDto;
import net.dothr.dto.SeguroDto;
import net.dothr.dto.ValeDto;
import docs.AbstractDocCreator;

import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.TextAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.json.JSONObject;

public class CompensationDocImpl extends AbstractDocCreator  {

	private static String tab = "    ";
	
	private static final String fileSysDocs = "/home/dothr/app/webServer/repository/docs/X/"; //files.repdocs.temp
	
	private static String DOC_OUT_PATH = fileSysDocs + "formatoCompTEST.doc";
	
	public static void main(String[] args) {
		try {
			JSONObject jsonComp = getJsonObject("read.json", 
					"/home/dothr/Documents/SELEX/JsonUI/module/compensation/");
			
			CompensacionDto fcDto = getDto(jsonComp);
			System.out.println("fcDto: " + fcDto );			
			
			CompensationDocImpl formDoc = new CompensationDocImpl();
			
			//* EMPLEADO EN AppTransactional:
			FileDto fileDto = new FileDto();
			fileDto.setFileName("FormatoPrueba");
			fileDto.setIdSolicitante("99");
			fileDto.setAuthor("Netto");
			fileDto.setCreator("MyProjects");
			fileDto.setFileTitle("Formato de pruebas Netto");
			
			formDoc.createFormatoComp(fcDto, fileDto.getFileTitle(), DOC_OUT_PATH ); //*/
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Genera un Archivo .doc con el formato de compensación usado en AppTransactional
	 * @param dto
	 * @param tituloDoc
	 * @param outPath
	 * @return
	 */
	protected FileDto createFormatoComp(CompensacionDto dto, String tituloDoc,
			String outPath) {
		
		FileDto fileDto = new FileDto();
		try{

			XWPFDocument doc = new XWPFDocument();
			 
			 XWPFParagraph paragraph1 = doc.createParagraph();
			 paragraph1.setAlignment(ParagraphAlignment.CENTER);
//			 paragraph1.setBorderBottom(Borders.DOUBLE);
//			 paragraph1.setBorderTop(Borders.DOUBLE);
//			 paragraph1.setBorderRight(Borders.DOUBLE);
//			 paragraph1.setBorderLeft(Borders.DOUBLE);
//			 paragraph1.setBorderBetween(Borders.SINGLE);

			 paragraph1.setVerticalAlignment(TextAlignment.TOP);
			 mainTitle(paragraph1.createRun(), tituloDoc, true);   
		        
		    /* Datos Personales */
		    XWPFParagraph pDatos = getParag(doc);
		    sectionTitle(pDatos.createRun(), "Datos Personales");
		    setDatosPersonales(pDatos.createRun(), dto);
		        
		    /* Compensación económica */
		    XWPFParagraph pCompEcon = getParag(doc);
		    sectionTitle(pCompEcon.createRun(), "Compensación económica");
		    setEconomica(pCompEcon.createRun(), dto);
		    
		    /* Prestaciones */
		    XWPFParagraph pPrestac = getParag(doc);
		    sectionTitle(pPrestac.createRun(), "Prestaciones");
		    setPrestaciones(pPrestac.createRun(), dto);
		    
		    /*  Beneficios*/
		    XWPFParagraph pBenefs = getParag(doc);
		    sectionTitle(pBenefs.createRun(), "Beneficios");
		    setBeneficios(pBenefs.createRun(), dto);
		    
		    /*  Referencias Laborales*/
		    XWPFParagraph pRefers = getParag(doc);
		    sectionTitle(pRefers.createRun(), "Referencias Laborales");
		    setReferencias(pRefers.createRun(), dto);
		    
		    /*>> OUTPUT <<  */
		    FileOutputStream out = new FileOutputStream(outPath);
		    doc.write(out);
		    out.close();
//		    doc.close();
		    System.out.println("Se genero archivo: " + outPath );
		    fileDto.setFileDescripcion(tituloDoc);
		    
		}catch (Exception e){
			System.err.println("<createFormatoComp> Excepcion al crear documento: "+e.getMessage());
			e.printStackTrace();
			fileDto.setCode(Mensaje.SERVICE_CODE_000);
			fileDto.setType(Mensaje.SERVICE_TYPE_FATAL);
			fileDto.setMessage(Mensaje.MSG_WARNING + ": "+e.getMessage() );
		}
	    return fileDto;
	}
	
//	/**
//	 * OBtiene un objeto de Parrafo genérico
//	 * @param doc
//	 * @return
//	 */
//	protected XWPFParagraph getParag(XWPFDocument doc){
//		 XWPFParagraph paragraph = doc.createParagraph();
////		    paragraph.setAlignment(ParagraphAlignment.LEFT);
////		    paragraph.setBorderBottom(Borders.DOUBLE);
////		    paragraph.setBorderRight(Borders.DOUBLE);
////		    paragraph.setBorderLeft(Borders.DOUBLE);
////		    paragraph.setBorderBetween(Borders.SINGLE);
////		    paragraph.setBorderTop(Borders.DOUBLE);
//
//		    paragraph.setVerticalAlignment(TextAlignment.TOP);
//		    
//		    return paragraph;
//	}
//	/**
//	 * Añade Titulo Principal de Documento
//	 * @param mainTitleRun
//	 * @param titulo
//	 */
//	protected void mainTitle(XWPFRun mainTitleRun, String titulo){
//        mainTitleRun.setBold(true);
//        mainTitleRun.setText(titulo.toUpperCase());
//        mainTitleRun.setFontFamily(mainTitleFont);
//        mainTitleRun.setColor(mainTitleColor);
//        mainTitleRun.setFontSize(mainTitleSize);
//        mainTitleRun.addBreak();
//	}
//	/**
//	 * Agrega el titulo de la sección
//	 * @param rDTitle
//	 * @param titulo
//	 */
//	protected void sectionTitle(XWPFRun rDTitle, String titulo){
//		rDTitle.setFontFamily(subTitleFont);
//		rDTitle.setFontSize(subTitleSize);
//		rDTitle.setColor(subTitleColor);
//		rDTitle.setBold(true);
//		rDTitle.setText(titulo);
////		rDTitle.setUnderline(UnderlinePatterns.DASH_LONG);
//		rDTitle.addBreak();
//	}

	/**
	 * Sección de Datos personales
	 * @param rDatos
	 * @param dto
	 */
	protected void setDatosPersonales(XWPFRun rDatos, CompensacionDto dto){
		rDatos.setFontFamily(textoFont);
		 rDatos.setFontSize(textoSize);
		 rDatos.setColor(textoColor);
		 
		 rDatos.setText("Nombre: ");
		 rDatos.setText(dto.getNombreCompleto()+tab);
		 rDatos.addBreak();
		 rDatos.setText("Fecha de Nacimiento: ");
		 rDatos.setText(dateFormated( dto.getFechaNacimiento() ));
		 rDatos.addBreak();

		 rDatos.setText("Género: ");
		 rDatos.setText(dto.getLbGenero());
		 rDatos.addBreak();
		 rDatos.setText("Estado civil: ");
		 rDatos.setText(dto.getLbEstadoCivil());
		 rDatos.addBreak();
		 if(dto.getNumeroHijos()!=null){
			 rDatos.setText("Número de Hijos: ");
			 rDatos.setText(dto.getNumeroHijos());
			 rDatos.addBreak();
		 }
		 
		 rDatos.setText("País de origen: ");
		 rDatos.setText(dto.getStPais());// "México");
		 rDatos.addBreak();

		 if(dto.getContactos()!=null && !dto.getContactos().isEmpty()){
			 ContactInfoDto contactDto;
			 Iterator<ContactInfoDto> itConts = dto.getContactos().iterator();
			 while(itConts.hasNext()){
				 contactDto = itConts.next();
				 rDatos.setText(contactDto.getLbTipoContacto() + ": ");
				 rDatos.setText((contactDto.getNumero()!=null?contactDto.getNumero():contactDto.getContacto()!=null?contactDto.getContacto():"")+tab); //" [55] 32956775"+tab);
				 rDatos.addBreak();
			 }
		 }

		 rDatos.setText("CURP: ");
		 rDatos.setText(dto.getCurp());
		 
		 rDatos.addBreak();
	}
	/**
	 * Sección de Compensación económica
	 * @param rComp
	 * @param dto
	 */
	protected void setEconomica(XWPFRun rComp, CompensacionDto dto){
		rComp.setFontFamily(textoFont);
		rComp.setFontSize(textoSize);
		rComp.setColor(textoColor);
		
	    if(dto.getSueldo()!=null){
	    	rComp.setText("Sueldo Base: ");
		    rComp.setText( getCurrencyFormated(dto.getSueldo().getCantidad()));
		    rComp.addBreak();
		    rComp.setText("Periodicidad: ");
		    rComp.setText( notNull(dto.getSueldo().getLbPeriodicidadSueldo()) );
		    rComp.addBreak();
	    }   
	    
	    rComp.setText(" Fondo de Ahorro: ");
	    rComp.setText( getCurrencyFormated(dto.getCantidadFondoAhorro()) );	// "25000 ");
	    rComp.addBreak();   
	    
	    if(dto.getVales()!=null && !dto.getVales().isEmpty()){
	    	ValeDto valedto;
	    	Iterator<ValeDto> itVales = dto.getVales().iterator();
	    	while(itVales.hasNext()){
	    		valedto = itVales.next();
	    		if(valedto.getActivo()!=null && valedto.getActivo().equals("1")){
	    			rComp.setText(" > "+ valedto.getLbTipoVale()+": ");
				    rComp.setText( getCurrencyFormated(valedto.getCantidad()));
				    rComp.addBreak();
	    		}
	    	}
	    }
	    if(dto.getOtro()!=null){
	    	rComp.setText("Otra prestación: ");
		    rComp.setText(dto.getOtro());
		    rComp.addBreak();
	    }
	}
	/**
	 * Sección Prestaciones
	 * @param rPrest
	 * @param dto
	 */
	protected void setPrestaciones(XWPFRun rPrest, CompensacionDto dto){
		rPrest.setFontFamily(textoFont);
		rPrest.setFontSize(textoSize);
		rPrest.setColor(textoColor);
	    
	    rPrest.setText("Aguinaldo: ");
	    rPrest.setText( notNull(dto.getDiasAguinaldo(),"0")+tab); //"15 días"+tab);
	    rPrest.setText("Último monto Utilidades: ");
	    rPrest.setText( getCurrencyFormated(dto.getUltimoMontoUtilidades()));//  "1,000"+tab);
	    rPrest.addBreak();
	    
	    if(dto.getVacaciones()!=null){
	    	rPrest.setText("Días de Vacaciones: ");
		    rPrest.setText( notNull(dto.getVacaciones().getValorDias(),"0")+ " días  ");
		    rPrest.setText("Prima:");
		    rPrest.setText( dto.getVacaciones().getValorPrima()+ " % ");
		    rPrest.addBreak();
	    }
	    rPrest.setText("Servicio de Comedor: ");
	    rPrest.setText( getCurrencyFormated(dto.getComedor()));	// "25 diarios"+tab);
	    rPrest.addBreak();
	}
	/**
	 * Sección de Beneficios
	 * @param rBenef
	 * @param dto
	 */
	protected void setBeneficios(XWPFRun rBenef, CompensacionDto dto){
		rBenef.setFontFamily(textoFont);
		rBenef.setFontSize(textoSize);
		rBenef.setColor(textoColor);
	    
	    rBenef.setText("Celular: ");
	    rBenef.setText(siNo(dto.getCelular())+tab);	//"Si"+tab);
	    rBenef.setText("Club-Gym: ");
	    rBenef.setText(siNo(dto.getClubGym())+tab); //"No"+tab);
	    rBenef.setText("Check Up: ");
	    rBenef.setText(siNo(dto.getCheckUp())+tab); //"Si"+tab);
	    rBenef.addBreak();
	    
	    if(dto.getAutomovil()!=null){
	    	rBenef.setText("Auto de la compañia ");
		    rBenef.addBreak();	    
		    rBenef.setText("Marca: ");
		    rBenef.setText( notNull(dto.getAutomovil().getMarca())+tab);  // "NISSAN"+tab);
		    rBenef.setText("Modelo: ");
		    rBenef.setText( notNull(dto.getAutomovil().getModelo())+tab);  // "Tsuru"+tab);
		    rBenef.setText("Gastos Pagados: ");
		    rBenef.setText( siNo(dto.getAutomovil().getGastosPagados())+tab);
		    rBenef.addBreak();
		    rBenef.setText("Opción de Compra: ");
		    rBenef.setText( siNo(dto.getAutomovil().getOpcionCompra())+tab);
		    rBenef.setText("Tiempo de Cambio: ");
		    rBenef.setText( notNull(dto.getAutomovil().getTiempoCambio())+" años"+tab);
		    rBenef.addBreak();
	    }
	    
	    if(dto.getBonos()!=null && !dto.getBonos().isEmpty()){
	    	rBenef.setText("BONOS ");
		    rBenef.addBreak();
		    Iterator<BonoDto> itBono = dto.getBonos().iterator();
		    BonoDto dtoBono;
		    while(itBono.hasNext()){
		    	dtoBono = itBono.next();
		    	if(dtoBono.getPorcentajeCantidad()!=null || dtoBono.getValorCantidad()!=null){
		    		rBenef.setText("\t- "+ dtoBono.getTipoBono());
			    	if(dtoBono.getPorcentajeCantidad()!=null){
			    		rBenef.setText( " "+dtoBono.getPorcentajeCantidad()+ "% "+ notNull(dtoBono.getLbPerioricidadBono()));
			    	}else if(dtoBono.getValorCantidad()!=null){
			    		rBenef.setText( " "+getCurrencyFormated(dtoBono.getValorCantidad())+ " " +notNull(dtoBono.getLbPerioricidadBono()));
			    	}
				    rBenef.addBreak();
		    	}
		    }
	    }
	    if(dto.getSeguros()!=null && !dto.getSeguros().isEmpty()){
	    	rBenef.setText("SEGUROS ");
		    rBenef.addBreak();
		    Iterator<SeguroDto> itSeg = dto.getSeguros().iterator();
		    SeguroDto dtoSeg;
		    while(itSeg.hasNext()){
		    	dtoSeg = itSeg.next();
		    	rBenef.setText( "\t- "+ dtoSeg.getLbTipoSeguro() +(dtoSeg.getFamiliar()!=null?dtoSeg.getFamiliar().equals("1")?" [Familiar]":" [Individual]":"") ); //"Seguro Dental (Individual)");
			    rBenef.addBreak();
		    }
	    }	    
	}
	/**
	 * Sección de Referencias laborales
	 * @param rRef
	 * @param dto
	 */
	protected void setReferencias(XWPFRun rRef, CompensacionDto dto){
		rRef.setFontFamily(textoFont);
		rRef.setFontSize(textoSize);
		rRef.setColor(textoColor);
	    
	    if(dto.getReferencias()!=null && !dto.getReferencias().isEmpty()){
	    	Iterator<ReferenceDto> itRef = dto.getReferencias().iterator();
	    	ReferenceDto dtoRef;
	    	while(itRef.hasNext()){
	    		dtoRef = itRef.next();
	    		rRef.setText("Nombre: ");
			    rRef.setText( notNull(dtoRef.getNombre()) +tab);	//"Roberto Carlos"+tab);
			    rRef.addBreak();
			    rRef.setText("Empresa: ");
			    rRef.setText( notNull(dtoRef.getEmpresa()) +tab);	//"Real Madrid FC"+tab);
			    rRef.setText("Puesto: ");
			    rRef.setText( notNull(dtoRef.getPuesto()) +tab);	//"Defensa"+tab);
			    rRef.addBreak();
			    if(dtoRef.getEmail()!=null){
				    rRef.setText("Email: ");
				    rRef.setText( notNull(dtoRef.getEmail()) +tab);	//"rcarlos4@rm.com"+tab);			    	
			    }
			    if(dtoRef.getTelParticular()!=null){
				    rRef.setText("Partícular: ");
				    rRef.setText( notNull(dtoRef.getTelParticular()) +tab );	//"25151515"+tab);	
			    }
			    if(dtoRef.getTelCelular()!=null){
				    rRef.setText(" Móvil: ");
				    rRef.setText( notNull(dtoRef.getTelCelular()) );	//"55325151");
			    }
			    rRef.addBreak();rRef.addBreak();
	    	}
	    }else{
	    	rRef.setText("NO HAY NINGUNA REFERENCIA CAPTURADA");
		    rRef.addBreak();
	    }
	}
}
