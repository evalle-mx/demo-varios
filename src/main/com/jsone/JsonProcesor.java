package com.jsone;

import org.json.JSONArray;
import org.json.JSONException;

import utily.FileUtily;
import utily.JsonUtily;

public class JsonProcesor {
	
	
	public static void main(String[] args) {
		try {
			addData();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	private static void addData() throws JSONException{
		String pathJson = "/home/dothr/app/webServer/sitios/demos/angular/multiSelects/adityasharat/dhr/";
		
		JSONArray jsArr = (JSONArray)JsonUtily.getJson(pathJson+"paises.json");
		if(jsArr!=null){
			
			for(int x=0; x<jsArr.length();x++){
				jsArr.getJSONObject(x).put("idPais", (x+1) );
			}
			
			FileUtily.writeStringInFile(pathJson+"paises2.json", jsArr.toString(), false);
		}
	}
	
	

}
