package utily;
/**
 * Clase con metodos estaticos que proporcionan utilidades para generacion, manipulacion o transformacion
 * de fechas
 * @author EAVV
 * 
 */
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

public class DateUtily {
	
	private static TimeZone timeZone = TimeZone.getTimeZone("CST");
	private static Calendar sistDate = Calendar.getInstance(timeZone,new Locale("es","MX"));
	private static Locale LOCALE = new Locale("es","MX");
	private static GregorianCalendar gregDate =  new GregorianCalendar(timeZone,new Locale("es","MX"));
	
	private static final String defaultDatePattern = "dd/MM/yyyy";
	
	private String[] datePatterns = { "dd MMMMM yyyy", "dd.MM.yy", "MM/dd/yy",
			"yyyy.MM.dd G 'at' hh:mm:ss z", "EEE, MMM d, ''yy",
			"h:mm a", "H:mm:ss:SSS", "K:mm a,z",
			"yyyy.MMMMM.dd GGG hh:mm aaa" };
	
	/**
	 * Returns a simple Date (sqlTime)
	 * @return Date
	 * @format Thu May 05 10:58:24 CDT 2011
	 */
	public static Date getToday(){				
		return new Date();
	}
	/**
	 * Returns a simple Date with zero Hours, Mins, Sec (sqlTime)
	 * @return Date
	 * @format Thu May 05 00:00:00 CDT 2011
	 */
	public static Date getTodayZero(){		
		Calendar fechaCal = Calendar.getInstance();
		fechaCal.set(Calendar.HOUR_OF_DAY, 0);
		fechaCal.set(Calendar.MINUTE, 0);
		fechaCal.set(Calendar.SECOND, 0);
		fechaCal.set(Calendar.MILLISECOND, 0);
		
		return fechaCal.getTime();
	}
	/**
	 * Returns a simple Date with 23 Hours, 59 Mins, 59 Sec (sqlTime)
	 * @return Date
	 * @format Thu May 05 00:00:00 CDT 2011
	 */
	public static Date getTodayLast(){		
		Calendar fechaCal = Calendar.getInstance();
		fechaCal.set(Calendar.HOUR_OF_DAY, 23);
		fechaCal.set(Calendar.MINUTE, 59);
		fechaCal.set(Calendar.SECOND, 59);
//		fechaCal.set(Calendar.MILLISECOND, 0);
		
		return fechaCal.getTime();
	}
	 /**
     * iDayOfTheWeek. returns the number of the day of the week
     * @return integer 0,1,...  (sunday == 0)
     */
    public static int iDayOfTheWeek() 
      {
        int day = gregDate.get( Calendar.DAY_OF_WEEK );
        return day;
      }
	
	/**
	 * Date with CST time (calendar date) 
	 * @return Date
	 * @format Thu May 05 10:58:24 CDT 2011
	 */
	public static Date getUtilCalDateCST(){
		return sistDate.getTime();
	}
	
	/**
	 * Date with CST time (gregorian Calendar date)
	 * @return Date
	 * @format Thu May 05 10:58:24 CDT 2011
	 */
	public static Date getUtilGregDateCST(){
		return gregDate.getTime();
	}
	/**
	 * Returns a Date (sqlTime)
	 * @return Date
	 * @format 2007-09-28
	 */	
	public static Date getSqlDate(){		
		java.sql.Date sqlDate = new java.sql.Date(new Date().getTime());
		return sqlDate;
	}	
	/**
	 * Returns the Time (hh:mm:ss)
	 * @return Date
	 * @format 17:08:22
	 */	
	public static Date getSqlTime(){		
		java.sql.Time sqlTime = new java.sql.Time(new Date().getTime());
		return sqlTime;
	}	
	/**
	 * Returns the Time (hh:mm:ss)
	 * in relation with CST
	 * @return Date
	 * @format 17:08:22
	 */	
	public static Date getSqlTimeCST(){		
		Date utilDate = getUtilCalDateCST();		
		return getSqlTimeCST(utilDate);
	}
	/**
	 * Returns Date in Sql.time
	 * @param Date utilDate
	 * @return Date
	 */
	public static Date getSqlTimeCST(Date utilDate){
		return new java.sql.Time(utilDate.getTime());		
	}
	/**
	 *  Returns a Timestamp (sqlTimestamp)
	 *  @return Timestamp
	 *  @format: 2007-09-20 17:08:22.161
	 */
	public static Timestamp getTimeStamp(){
		Date utilDate = new Date(); //fecha actual		 
		return getTimeStamp(utilDate);		
	}
	/**
	 * Returns a Timestamp (sqlTimestamp)
	 * @param d (Date)
	 * @return Timestamp
	 */
	public static Timestamp getTimeStamp(Date d){		
		  long lnMilisegundos = d.getTime();				
		return new Timestamp(lnMilisegundos);		
	}
	
	/**
	 * Returns a Timestamp (sqlTimestamp) From a String
	 * @param std (String) default: dd/MM/yyyy
	 * @return Timestamp
	 */
	public static Timestamp getTimeStamp(String std){
		Date d = string2Date(std);
		  long lnMilisegundos = d.getTime();				
		return new Timestamp(lnMilisegundos);		
	}
	/**
	 *  Returns a long getTime (Date.getTime())
	 *  @return long
	 *  @format: 2007-09-20 17:08:22.161
	 */
	public static long getLongTimeStamp(){
		return getLongTimeStamp(new Date());		
	}
	/**
	 *  Returns a long getTime (Date.getTime())
	 *  @return long
	 *  @format: 2007-09-20 17:08:22.161
	 */
	public static long getLongTimeStamp(Date d){		
		  long lnMilisegundos = d.getTime();				
		return lnMilisegundos;		
	}	
	/**
	 * genera Fecha de hoy formateada por default
	 * @return String
	 */
	public static String thisDateFormated(){
		return thisDateFormated(new Date(), defaultDatePattern);
	}
	/**
	 * Regresa Fecha de hoy con patron solicitado
	 * @param pattern
	 * @return String
	 */
	public static String thisDateFormated(String pattern){
		return thisDateFormated(new Date(), pattern);
	}
	/**
	 * Regresa fecha solicitada con patron asignado
	 * @param d (Date)
	 * @param pattern (String) i.e. dd/MM/yyyy
	 * @return String
	 */
	public static String thisDateFormated(Date d, String pattern){
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.format(d);
	}
	
	/**
     * Verifica que que el parametro sea una fecha valida   
	 * @param sDate 
	 * @param pattern, i.e. dd/MM/yyyy
     * @return boolean
     */
    public static boolean isValidDate(String sDate,String pattern){
    	try{
    			SimpleDateFormat sdf=new SimpleDateFormat(pattern);
    			sdf.setLenient(false);
    			sdf.parse(sDate);
    			return true;
    		}catch(ParseException e){
    			return false;
    		}
    	}	
	
	/* ************************************************************** */
	/* ********************* Metodos personalizados ***************** */
	/* ************************************************************** */
	
	/**
	 * Regresa Date con la fecha asignada
	 * @param s (String) i.e. 01/12/1900
	 * @return Date
	 */
	public static Date string2Date(String s){
		return string2Date(s, defaultDatePattern);
	}
	/**
	 * Regresa Date con la fecha y patron asignados
	 * @param s 
	 * @param pattern i.e.(dd/MM/yyyy)
	 * @return Date
	 */
	public static Date string2Date(String s, String pattern){		
		Date d = null;
	    try
	    {
	      SimpleDateFormat sd = new SimpleDateFormat();
	      sd.applyPattern(pattern);	      
	      d = sd.parse(s);
	    }
	    catch (Exception e)
	    {
	      System.out.println("Capturada en Util.stringToDate(). Recibida: " + s);
	      e.printStackTrace();
	    }
	    return d;
	}	
	/**
	 * Transforma una fecha en long (timeStamp long) en String
	 * @param milis
	 * @return default pattern String date
	 */
	public static String longDate2String(long milis){
		return longDate2String(milis, defaultDatePattern);		
	}
	/**
	 * Transforma una fecha en long (timeStamp long) en String
	 * con pattern solicitado
	 * @param milis
	 * @param pattern i.e.(dd/MM/yyyy)
	 * @return default pattern String date
	 */
	public static String longDate2String(long milis, String pattern){		 
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.format(new Date(milis));		
	}
	
	/**
	 * Method to compare to dates (Strings)in format 
	 * default dd/MM/yyyy and returns
	 * 1: date1 > date2, -1: date1 < date2
	 * 0: equals
	 * @param stDate1
	 * @param stDate2 
	 * @return integer
	 */
	public static int compareDt1Dt2 (String stDate1, String stDate2){
		int comp =0;
		Date dt1 = string2Date(stDate1, defaultDatePattern);
		Date dt2 = string2Date(stDate2, defaultDatePattern);
		comp = dt1.compareTo(dt2);
		return comp;
	}
	
	/**
	 * Method to compare to dates (Strings)
	 * 1: date1 > date2, -1: date1 < date2
	 * 0: equals
	 * @param stDate1
	 * @param stDate2
	 * @param pattern YYYY-MM-DD hh:mm:ss
	 * @return integer
	 */
	public static int compareDt1Dt2 (String stDate1, String stDate2, String pattern){
		int comp =0;
		Date dt1 = string2Date(stDate1, pattern);
		Date dt2 = string2Date(stDate2, pattern);
		comp = dt1.compareTo(dt2);
		return comp;
	}
	/**
	 * Method to compare hours in format hh:mm:ss
	 * @param stH1
	 * @param stH2
	 * @return integer
	 */
	public static int compareHours(String stHour1, String stHour2){		
		int comp = 0;
		SimpleDateFormat sdf;
	    sdf = new SimpleDateFormat("hh:mm:ss");
	    Date d1 = null;
	    Date d2 = null;
	    try{
	    	d1 = sdf.parse(stHour1);
	    	d2 = sdf.parse(stHour2);
	    	comp = d1.compareTo(d2);
	    } catch (Exception e)
	    {
	    	e.printStackTrace();
	    }
		return comp;
	}
	
	/**
	 * Agrega el tiempo determinado por parametros
	 * @param stHour
	 * @param years
	 * @param months
	 * @param days
	 * @param hours
	 * @param mins
	 * @param secs
	 * @param pattern
	 * @return
	 */
	public static String addTime(String stDate, int years, int months, int days, int hours, int mins, int secs, String pattern){
		String response = stDate;
		System.out.println("Recibida: " + stDate);
		SimpleDateFormat sdf;
	    sdf = new SimpleDateFormat(pattern);	    
	    Calendar cal = Calendar.getInstance();	    
	    try{
	    	cal.setTime(sdf.parse(stDate));
	    	cal.add(Calendar.YEAR, years);
	    	cal.add(Calendar.MONTH, months);
	    	cal.add(Calendar.DAY_OF_MONTH, days);
	    	cal.add(Calendar.HOUR, hours);
	    	cal.add(Calendar.MINUTE, mins);
	    	cal.add(Calendar.SECOND, secs);	    	
	    	response = sdf.format(cal.getTime());	    
	    } catch (Exception e)
	    {e.printStackTrace();}	    
		return response;
	}
	/**
	 * Agrega el tiempo determinado por parametros
	 * @param stHour
	 * @param years
	 * @param months
	 * @param days
	 * @param hours
	 * @param mins
	 * @param secs
	 * @param pattern
	 * @return
	 */
	public static String addTime(Date date, int years, int months, int days, int hours, int mins, int secs, String pattern){
		String response = null;
	    Calendar cal = Calendar.getInstance();	   
	    SimpleDateFormat sdf;
	    sdf = new SimpleDateFormat(pattern);
	    try{
	    	cal.setTime(date);
	    	cal.add(Calendar.YEAR, years);
	    	cal.add(Calendar.MONTH, months);
	    	cal.add(Calendar.DAY_OF_MONTH, days);
	    	cal.add(Calendar.HOUR, hours);
	    	cal.add(Calendar.MINUTE, mins);
	    	cal.add(Calendar.SECOND, secs);	    	
	    	response = sdf.format(cal.getTime());	    
	    } catch (Exception e)
	    {e.printStackTrace();}	    
		return response;
	}
	/**
	 * Agrega a�os, meses y dias, con formato dd/MM/yyyy
	 * @param stDate
	 * @param years
	 * @param months
	 * @param days
	 * @return
	 */
	public static String addDays(String stDate, int years, int months, int days){		
		return addTime(stDate, years, months, days, 0, 0, 0, "dd/MM/yyyy");
	}
	/**
	 * Agrega horas, minutos y segundos, con formato hh:mm:ss
	 * @param stDate
	 * @param hours
	 * @param mins
	 * @param secs
	 * @return
	 */
	public static String addHours(String stDate, int hours, int mins, int secs){		
		return addTime(stDate, 0, 0, 0, hours, mins, secs, "hh:mm:ss");
	}
	
	
	/**
	 * Muestra diferentes Patrones
	 */
	public static void showPatterns(){
		String fechaTest = "01/02/2000 00:01:02";
		Date d = string2Date(fechaTest, "dd/MM/yyyy hh:mm:ss");
		System.out.println("[showPatterns] (01/02/2000 00:01:02" );
		System.out.println("dd/MM/yyyy > "+ thisDateFormated(d,"dd/MM/yyyy"));
		System.out.println("yyyy-MM-dd > "+ thisDateFormated(d,"yyyy-MM-dd"));		
		System.out.println("dd/MM/yyyy hh:mm:ss > "+ thisDateFormated(d,"dd/MM/yyyy hh:mm:ss"));
		System.out.println("yyyy-MM-dd hh:mm:ss > "+ thisDateFormated(d,"yyyy-MM-dd hh:mm:ss"));
		System.out.println("yyyyMMddhhmmss > "+ thisDateFormated(d,"yyyyMMddhhmmss"));
		System.out.println("hh:mm:ss > "+ thisDateFormated(d,"hh:mm:ss"));
		System.out.println("HH:mm:ss > "+ thisDateFormated(d,"HH:mm:ss"));
		System.out.println("E MMM dd yyyy > "+ thisDateFormated(d,"E MMM dd yyyy"));
		System.out.println("getLongTimeStamp > "+ getLongTimeStamp(d));				
		System.out.println("prueba : " + getTimeStamp("05/05/2011") );
		System.out.println("yyyyMMddhhmmssnn > "+ thisDateFormated(d,"yyyyMMddhhmmssss"));
	}


	/**	 * Prueba todos los metodos desplegando resultado en pantalla */
	public static void testAllMethods(){
		System.out.println("[getToday]: " + getToday());
		System.out.println("[getTodayZero]: " + getTodayZero());
		System.out.println("[getTodayLast]: " + getTodayLast());
		System.out.println("[iDayOfTheWeek]: " + iDayOfTheWeek());
		System.out.println("[getUtilCalDateCST]: " + getUtilCalDateCST());
		System.out.println("[getUtilGregDateCST]: " + getUtilGregDateCST());
		System.out.println("[getSqlDate]: " + getSqlDate());
		System.out.println("[getSqlTime]: " + getSqlTime());
		System.out.println("[getSqlTimeCST]: " + getSqlTimeCST());
		System.out.println("[getTimeStamp]: " + getTimeStamp());
		System.out.println("[getLongTimeStamp]: " + getLongTimeStamp());
		System.out.println("[thisDateFormated]: " + thisDateFormated());
		System.out.println("---------");
		System.out.println("[string2Date] (\"01/12/1900\"): " + string2Date("01/12/1900"));
		
		System.out.println("******");
		showPatterns();
		System.out.println( "[addTime] (01/02/2005 08:00:25) > " + addTime("01/02/2005 08:00:25",5,2,1,2,15,40,"dd/MM/yyyy hh:mm:ss") );
		
	}
	/**
	 * $$$$$$$$$$$$     MAIN METHOD    $$$$$$$$$$$$$$$$
	 * @param args
	 */
	public static void main(String[] args) {
		
		testAllMethods();
		
	}
	
	/**
	 * Obtiene fecha con el día Maximo del Mes
	 * @param anio
	 * @param mes
	 * @return
	 */
	public static Date getDateWithLastDay(String anio, String mes){
		Date fecha = null;
		if(anio!=null  && !anio.trim().equals("") && anio.trim().length()==4 ){
			if(mes!=null  && !mes.trim().equals("")){
				Calendar cal = Calendar.getInstance(timeZone, LOCALE);
				cal.clear();
				cal.set(Calendar.YEAR, Integer.parseInt(anio));
				cal.set(Calendar.MONTH, Integer.parseInt(mes) -1); // +- 1?
				cal.set(Calendar.DATE, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
				fecha = cal.getTime();
			}
		}
		
		return fecha;
	}
	
	public static String fechaLatino(Date fecha){
		Calendar gregDate = new GregorianCalendar();
		gregDate.setTime(fecha);
		
//		System.out.println("gregDate -> "+gregDate);
//		System.out.println("year   -> "+gregDate.get(Calendar.YEAR));
//		System.out.println("month  -> "+gregDate.get(Calendar.MONTH));
//		System.out.println("dom    -> "+gregDate.get(Calendar.DAY_OF_MONTH));
//		System.out.println("dow    -> "+gregDate.get(Calendar.DAY_OF_WEEK));
//		System.out.println("hour   -> "+gregDate.get(Calendar.HOUR));
//		System.out.println("minute -> "+gregDate.get(Calendar.MINUTE));
//		System.out.println("second -> "+gregDate.get(Calendar.SECOND));
//		System.out.println("milli  -> "+gregDate.get(Calendar.MILLISECOND));
//		System.out.println("ampm   -> "+gregDate.get(Calendar.AM_PM));
//		System.out.println("hod    -> "+gregDate.get(Calendar.HOUR_OF_DAY));
		
		return mesLatino(gregDate.get(Calendar.MONTH))
				+" "+gregDate.get(Calendar.DAY_OF_MONTH)
				+", "+gregDate.get(Calendar.YEAR);
	}
	
	/**
	 * Temporal para obtener el Més en idioma Español
	 * @param mes
	 * @return
	 */
	public static String mesLatino(int mes){
		String stMes = "";
		switch (mes) {
		case 1:
			stMes = "Enero";
			break;	
		case 2:
			stMes = "Febrero";
			break;
		case 3:
			stMes = "Marzo";
			break;
		case 4:
			stMes = "Abril";
			break;
		case 5:
			stMes = "Mayo";
			break;
		case 6:
			stMes = "Junio";
			break;
		case 7:
			stMes = "Julio";
			break;
		case 8:
			stMes = "Agosto";
			break;
		case 9:
			stMes = "Septiembre";
			break;
		case 10:
			stMes = "Octubre";
			break;
		case 11:
			stMes = "Noviembre";
			break;
		case 12:
			stMes = "Diciembre";
			break;
		}
		return stMes;
	}

}
