package docs;

import java.awt.Color;
import java.util.Date;

import net.dothr.dto.CompensacionDto;
import net.dothr.dto.CurriculumDto;

import org.json.JSONArray;
import org.json.JSONObject;

import utily.DateUtily;
import utily.JsonUtily;
import utily.NumberUtily;

import com.google.gson.Gson;
import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Rectangle;

public class AbstractPDFCreator {

	private static final String DATE_FORMAT_JAVA = "yyyy-MM-dd HH:mm:ss";
	
	private static Gson gson;
	
	/* Tamaño de Pagina */
	protected static final Rectangle tamPag = PageSize.A4;	//PageSize.A4.rotate()); /* Horizontal */
	protected static final float pagWidth = 570F;
	
	protected static final Font fTitulo = FontFactory.getFont(
			FontFactory.HELVETICA_BOLD, 
			16, 
			Font.NORMAL, 
			new Color(0x0780da)
			);
	protected static final Font fSubTitulo = FontFactory.getFont(
			FontFactory.HELVETICA_BOLD, 
			10, 
			Font.NORMAL, 
			new Color(0x151515)
			);
	protected static final Font fTextoBold = FontFactory.getFont(
			FontFactory.HELVETICA, 
			8, 
			Font.BOLD, 
			new Color(0x424242)
			);
	protected static final Font fTexto = FontFactory.getFont(
			FontFactory.HELVETICA, 
			8, 
			Font.NORMAL, 
			new Color(0x424242)
			);
	
	
	/* ***** auxiliares   ******* */
	public static String filtNotNull(String value){
		if(value == null){
			value = "";
		}
		return value;
	}
	public static String filtSinDef(String value){
		if(value==null || value.trim().equals("")){
			return "-Sin Definir-";
		}
		return value;
	}
	public static String filtFechaRang(String anIni, String anFin, String act){
		if(anIni==null){
			anIni = "";
		}
		if(anFin==null ){
			if(act!=null && act.equals("1")){
				anFin = "Actual";
			}else{
				anFin = "";
			}
		}
		return anIni+" - "+anFin;
	}
	/**
	 * Realiza salto de linea para correos demasiado largos
	 * @param email
	 * @return
	 */
	public static String filtXLongLine(String email){
		if(email!=null){
			if(email.length()>25){
				if(email.indexOf("@")>25){
					email = email.substring(0,25)+(email.substring(25,email.length()).replace("@", "\n@"));
				}else{
					email=email.replace("@", "\n@");
				}
			}
		}
		return email;
	}
	
	/**
	 * Agrega los Metadados (titulo, fecha, autor) del archivo físico
	 * @param document
	 */
	protected void addMetaData(Document document, String nombreDoc) {
        document.addTitle(nombreDoc);
//        document.addSubject("Using iText");
        document.addKeywords("Formato, PDF, iText");
        document.addAuthor("DotHR-PIRH, SELEX");
        document.addCreator("Mónica Quintero");
    }
	
	/**
	 * Agrega n saltos de linea al Parrafo
	 * @param paragraph
	 * @param number
	 */
	protected static void addEmptyLine(Paragraph paragraph, int nSaltos) {
        for (int i = 0; i < nSaltos; i++) {
            paragraph.add(new Paragraph(" "));
        }
    }
	
	protected Paragraph emptyParag(int nSaltos){
		Paragraph paragraph = new Paragraph();
		for (int i = 0; i < nSaltos; i++) {
            paragraph.add(new Paragraph(" "));
        }
		return paragraph;
	}
	
	/**
	 * convierte una cadena simple en formato moneda
	 * @param stCandidad
	 * @return
	 */
	protected String getCurrencyFormated(String stCandidad) {
		String formated;
		if(stCandidad==null){
			stCandidad = "0";
		}
		Double numero = Double.parseDouble(stCandidad);
		formated= "$ "+NumberUtily.formatedNumber(numero, "###,###,###.00");
		return formated;
	}
	
	/**
	 * Convierte 1/0 a si/No 
	 * @param stValue
	 * @return
	 */
	protected String siNo(Object stValue){
		if(stValue==null || !stValue.equals("1")){
			return "No";
		}else{
			return "Sí";
		}
	}
	
	/**
	 * Da formato a la fecha
	 * @param stFecha
	 * @return
	 */
	protected String dateFormated(String stFecha){
		if(stFecha==null){
			return "";
		}
		else{
			String fechaForm = "";
			try {
				Date fecha = DateUtily.string2Date(stFecha, DATE_FORMAT_JAVA);
				fechaForm = DateUtily.fechaLatino(fecha);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return fechaForm;
		}
	}
		
	
	public static CompensacionDto getDto (JSONObject jsonCompensa){
		gson = new Gson();
		CompensacionDto dto = new CompensacionDto();
		dto = gson.fromJson(jsonCompensa.toString(), CompensacionDto.class);
		return dto;
	}
	
	public static CurriculumDto getCVDto (JSONObject jsonPersona){
		gson = new Gson();
		CurriculumDto dto = new CurriculumDto();
		dto = gson.fromJson(jsonPersona.toString(), CurriculumDto.class);
		return dto;
	}
	
	public static JSONObject getJsonObject(String fileName, String fileDir) throws Exception {
		String stPersona = JsonUtily.getJsonFile(fileName, fileDir);
		JSONArray jsResp = new JSONArray(stPersona);
		return jsResp.getJSONObject(0);
	}
	
	
}
