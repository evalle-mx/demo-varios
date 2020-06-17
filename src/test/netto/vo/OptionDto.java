package netto.vo;

public class OptionDto {

	private int intKey;
	private String value;	
		
	public OptionDto(int key, String value) {
		this.intKey = key;
		this.value = value;
	}
	
	public OptionDto() {
	}
	
	
	public boolean equals( Object objeto ) { 
		if (objeto == null) return false; 

		OptionDto producto = (OptionDto)objeto; 
		if (this.getValue() == producto.getValue() ) 
		return true; 

		return false; 
		} 

		public int hashCode() { 
		return this.getValue().hashCode(); 
		} 


	
	/** guetters & setters */

	public int getIntKey() {
		return intKey;
	}
	public void setIntKey(int intKey) {
		this.intKey = intKey;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
	public String toString(){
		return "["+intKey + ", " + value + "]";
	}
}
