package utily;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class NumberUtily {

	
	public static String formatedNumber(double number, String format){
		  String formatedNumber = String.format("%.2f",number);
		  if(format!=null && format.trim().length()>0){
			  try{
				  NumberFormat formatter = new DecimalFormat(format);
				  formatedNumber = formatter.format(number);
			  }catch (Exception e) {
				e.printStackTrace();
			} 
		  }
		  
		  return formatedNumber;
	  }
}
