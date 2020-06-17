package com.fotos;

import java.io.File;
import java.util.Iterator;
import java.util.List;

import utily.FileUtily;

/**
 * Esta clase solo renombra los archivos en el proceso final ya cuando se han
 * agregado Facebook y whatsApp
 * @author dothr
 *
 */
public class RenameFotos {

	private static String fotosPath = "/home/dothr/Pictures/Camara/2017-10-22.Catrinas/";
//			"/home/dothr/Pictures/Camara/FotosCanada/Test/";
	private static String listaFile = "lista.txt";
	
	
	public static void main(String[] args) {
//		renameFotos("Montreal", 1);	// Empieza con 1 o ultimo+1
		renameFotos("OfrendaPosada");
//		replaceName("ProcesionCatrinas", "Proc.Catrinas");
	}
	
	protected static void replaceName(String nomAnt, String nuevoNom) {
		StringBuilder sbRen = new StringBuilder("Original;nuevo\n");
		List<String> lsLine = FileUtily.getLinesFile(fotosPath+listaFile, null);
		System.out.println("lsLine.size "+lsLine.size());
		Iterator<String> itLine = lsLine.iterator();
		File fileTmp;
		String fileName, nombre, segDia, segHora, extension, newName;
		while(itLine.hasNext()){
			fileName = itLine.next();
			newName = fileName.replace(nomAnt, nuevoNom);
			fileTmp= new File(fotosPath+fileName);
			fileTmp.renameTo(new File(fotosPath, newName));
			sbRen.append(fileName).append(";").append(newName).append("\n");
		}

		FileUtily.writeStringInFile(fotosPath+"renamedByList2.csv", sbRen.toString(), false);
	}
	
	/**
	 * Renombra las fotos respetando hora de creacion de fileName original<br>
	 * <b>formato:</b> <i>20170916_061647.jpg</i>
	 * @param preNombre
	 */
	protected static void renameFotos(String preNombre){
		StringBuilder sbRen = new StringBuilder("Original;nuevo\n");
		List<String> lsLine = FileUtily.getLinesFile(fotosPath+listaFile, null);
		System.out.println("lsLine.size "+lsLine.size());
		Iterator<String> itLine = lsLine.iterator();
		File fileTmp;
		String fileName, nombre, segDia, segHora, extension, newName;
		while(itLine.hasNext()){
			fileName = itLine.next();
			extension = fileName.substring(fileName.lastIndexOf("."), fileName.length());
			
			nombre = fileName.substring(0,fileName.lastIndexOf("."));
			segDia = nombre.substring(0,8);
			segHora = nombre.substring(8,nombre.length());
			
					
			//Google photos
			if(segHora.indexOf("COLL")!=-1){ segHora= segHora.replace("COLLAGE", "COLL");}
			if(segHora.indexOf("EFFECTS")!=-1){ segHora= segHora.replace("EFFECTS", "EF");}
			if(segHora.indexOf("ANIMATION")!=-1){ segHora= segHora.replace("ANIMATION", "ANIM");}
					
			//Camara/Instagram/WhatsApp/Face
			if(segHora.indexOf("_D")!=-1 ){ segHora= segHora.replace("_D", "-NK"); }
			if(segHora.indexOf("_NK")!=-1){ segHora= segHora.replace("_NK", "-NK"); }
			if(segHora.indexOf("_Insta")!=-1){ segHora= segHora.replace("_Insta", "-Inst"); }
			if(segHora.indexOf("_Face")!=-1){ segHora= segHora.replace("_Face", "-FB"); }
			if(segHora.indexOf("WA01")!=-1){ segHora= segHora.replace("WA01", "W"); }
			
			System.out.println(" nombre: "+nombre+"\n "+ "ext: "+extension+"\n "
					+"segDia: "+segDia+"\n "+"segHora: "+segHora );
			newName = preNombre+segHora+extension;
			System.out.println("newName: "+newName+"\n");
			
			fileTmp= new File(fotosPath+fileName);
			fileTmp.renameTo(new File(fotosPath, newName));
			sbRen.append(fileName).append(";").append(newName).append("\n");
		}
		FileUtily.writeStringInFile(fotosPath+"renamedByList2.csv", sbRen.toString(), false);
	}
	
	/**
	 * Renombra los archivos con base en una lista en un archivo CSV y un consecutivo
	 */
	protected static void renameFotos(String preNombre, long nInicial){
		StringBuilder sbRen = new StringBuilder("Original;nuevo\n");
		long indice = nInicial;//1;
		int sizeName = 3;	//100=>3, 1000=>4
		List<String> lsLine = FileUtily.getLinesFile(fotosPath+listaFile, null);
		System.out.println("lsLine.size "+lsLine.size());
		Iterator<String> itLine = lsLine.iterator();
		File fileTmp;
		String line, name, ext, gtype, newName;
		while(itLine.hasNext()){
			line = itLine.next();
			gtype="";
			
			name = line;
			ext = line.substring(line.lastIndexOf("."), line.length());
			if(line.indexOf("COLL")!=-1){ gtype="-COLL";}
			if(line.indexOf("EFFECTS")!=-1){ gtype="-EF"; }
			if(line.indexOf("PAN")!=-1){ gtype="-PAN"; }
			if(line.indexOf("ANIM")!=-1){ gtype="-ANIM"; }
			
			if(line.indexOf("_D.")!=-1 || line.indexOf("_NK.")!=-1){ gtype="-NK"; }
			if(line.indexOf("Insta")!=-1){ gtype="-Inst"; }
			if(line.indexOf("_W")!=-1){ gtype="-W"; }
			
			newName = preNombre+"-"+addZero(indice, sizeName)+gtype+(ext.toLowerCase());
					//+"."+ name.substring(0,8)+".jpg";
					//addZero(indice, sizeName)+"."+ name; /*añade indice al principio*/
			
			System.out.println(name + ";" + newName );
			fileTmp= new File(fotosPath+name);
			fileTmp.renameTo(new File(fotosPath, newName));
			sbRen.append(name).append(";").append(newName).append("\n");
			indice++;
			//fileTmp = new File(pathToMove);
		}
		FileUtily.writeStringInFile(fotosPath+"renamedByList.csv", sbRen.toString(), false);
	}
	
	/**
	 * Agrega ceros a la izquierda dependiendo el tamaño solicitado
	 * @param indx
	 * @param size (1=>10, 2=>100, 3=>1000)
	 * @return
	 */
	private static String addZero(long indx, int size){
		String word = String.valueOf(indx);
		int add = size-word.length();
		while(add>0){
			word = "0"+word;
			add--;
		}
		return word;
	}

}
