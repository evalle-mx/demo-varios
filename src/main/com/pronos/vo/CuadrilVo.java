package com.pronos.vo;

import java.util.Iterator;

public class CuadrilVo {
	
	private Integer[] cuad1;
	private Integer[] cuad2;
	private Integer[] cuad3;
//	private Integer[] cuad4;
	
	
	public Integer[] getCuad1() {
		return cuad1;
	}
	public void setCuad1(Integer[] cuad1) {
		this.cuad1 = cuad1;
	}
	public Integer[] getCuad2() {
		return cuad2;
	}
	public void setCuad2(Integer[] cuad2) {
		this.cuad2 = cuad2;
	}
	public Integer[] getCuad3() {
		return cuad3;
	}
	public void setCuad3(Integer[] cuad3) {
		this.cuad3 = cuad3;
	}
//	public Integer[] getCuad4() {
//		return cuad4;
//	}
//	public void setCuad4(Integer[] cuad4) {
//		this.cuad4 = cuad4;
//	}

	public String toString(){
		StringBuilder sb = new StringBuilder("[\n");
		
		try{
			if(this.cuad1!=null){
				sb.append("cuad1={");		
				System.out.println(this.cuad1.length);
				for(int a=0;a<this.cuad1.length;a++){
					sb.append(this.cuad1[a]).append((a==this.cuad1.length-1?"":","));
				}
				sb.append("}\n");
			}
			if(this.cuad2!=null){
				sb.append("cuad2={");
				for(int b=0;b<this.cuad2.length;b++){
					sb.append(this.cuad2[b]).append((b==this.cuad2.length-1?"":","));
				}
				sb.append("}\n");
			}
			if(this.cuad3!=null){
				sb.append("cuad3={");
				for(int c=0;c<this.cuad3.length;c++){
					sb.append(this.cuad3[c]).append((c==this.cuad3.length-1?"":","));
				}
				sb.append("}\n");
			}
//			if(this.cuad4!=null){
//				sb.append("cuad4={");
//				for(int d=0;d<this.cuad4.length;d++){
//					sb.append(this.cuad4[d]).append((d==this.cuad4.length-1?"":","));
//				}
//				sb.append("}\n");
//			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return sb.append("]").toString();
	}
	
}
