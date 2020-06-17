package docs;

import java.util.Date;

import net.dothr.dto.CompensacionDto;
import net.dothr.dto.CurriculumDto;

import org.apache.poi.xwpf.usermodel.TextAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.json.JSONArray;
import org.json.JSONObject;

import com.google.gson.Gson;

import utily.DateUtily;
import utily.JsonUtily;
import utily.NumberUtily;

public abstract class AbstractDocCreator {

	private static Gson gson;
	
	private static final String DATE_FORMAT_JAVA = "yyyy-MM-dd HH:mm:ss";
	
	protected final static String mainTitleFont = "Calibri"; //Arial
	protected final static int mainTitleSize = 14;	//20
	protected final static String mainTitleColor = "4F81BD";	//1F497D / 4F81BD
	
	protected final static String subTitleFont = "Arial";
	protected final static int subTitleSize = 12;
	protected final static String subTitleColor = "3c78d8"; //3c78d8 / 808080
	
	protected final static String subTextFont = "Arial";
	protected final static int subTextSize = 12;
	protected final static String subTextColor = "1C1C1C";
	
	protected final static String textoFont = "Arial";	
	protected final static int textoSize = 9;
	protected final static String textoColor = "1C1C1C"; //a6a6a6 / 1C1C1C
	
	private StringBuilder sbA;
	
	private String tipoContactos = "Email Adicional;Facebook;Google+;Twitter;Yahoo messenger;LinkedIn;Flickr;DeviantART;YouTube;Skype;Teléfono Recados;Teléfono Oficina;Teléfono Celular;Página personal;Blog;Github";
	private String[] stTipo = tipoContactos.split(";");
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

	protected String notNull(Object stValue){
		if(stValue==null){
			return "";
		}
		return (String)stValue;
	}
	protected String notNull(Object stValue, String defValue){
		if(stValue==null){
			return defValue;
		}
		return (String)stValue;
	}
	protected String lbGenero (String idGenero){
		if(idGenero==null){		 
			idGenero = "0";
		}		
		
		if(idGenero.equals("1")){
			return "Femenino";
		}
		else if(idGenero.equals("1")){
			return "Masculino";
		}
		else{
			return "Indefinido";
		}
	}
	protected String lbTipoContacto(String idTipoContacto){
		if(idTipoContacto==null){		 
			idTipoContacto = "1";
		}
		String lbTipoC = "";
		Integer iTipo;
		try{
			iTipo = Integer.parseInt(idTipoContacto);
			if(iTipo<stTipo.length){
				lbTipoC = stTipo[iTipo-1];
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return lbTipoC;
		
	}
	protected String getLine(int size){
		sbA = new StringBuilder();
		for(int x=0;x<=size;x++){
			sbA.append("_");
		}
		return sbA.toString();
	}
	
	/* ************************** */
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
	
	
	/* ********************************************* */
	/**
	 * OBtiene un objeto de Parrafo genérico
	 * @param doc
	 * @return
	 */
	protected XWPFParagraph getParag(XWPFDocument doc){
		 XWPFParagraph paragraph = doc.createParagraph();
//		    paragraph.setAlignment(ParagraphAlignment.LEFT);
//		    paragraph.setBorderBottom(Borders.DOUBLE);
//		    paragraph.setBorderRight(Borders.DOUBLE);
//		    paragraph.setBorderLeft(Borders.DOUBLE);
//		    paragraph.setBorderBetween(Borders.SINGLE);
//		    paragraph.setBorderTop(Borders.DOUBLE);

		    paragraph.setVerticalAlignment(TextAlignment.TOP);
		    
		    return paragraph;
	}
	/**
	 * Añade Titulo Principal de Documento
	 * @param mainTitleRun
	 * @param titulo
	 */
	protected void mainTitle(XWPFRun mainTitleRun, String titulo, boolean toUpper){
        mainTitleRun.setBold(true);
        mainTitleRun.setText(toUpper?titulo.toUpperCase():titulo);
        mainTitleRun.setFontFamily(mainTitleFont);
        mainTitleRun.setColor(mainTitleColor);
        mainTitleRun.setFontSize(mainTitleSize);
        mainTitleRun.addBreak();
	}
	/**
	 * Agrega el titulo de la sección
	 * @param rDTitle
	 * @param titulo
	 */
	protected void sectionTitle(XWPFRun rDTitle, String titulo){
		rDTitle.setFontFamily(subTitleFont);
		rDTitle.setFontSize(subTitleSize);
		rDTitle.setColor(subTitleColor);
		rDTitle.setBold(true);
		rDTitle.setText(titulo);
//		rDTitle.setUnderline(UnderlinePatterns.DASH_LONG);
		rDTitle.addBreak();
	}
}
