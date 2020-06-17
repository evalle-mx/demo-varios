package netto;

public class CommonTester {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
//		iterador(18);

		numerador(126, 501);
	}

	public static void numerador(int inicio, long max){
		for(int i=inicio; i<=max; i++){
			System.out.println(i);
		}
	}
	
	/**
	 * Escribe las diferentes combinaciones de numeros 
	 * (entre el uno hasta el nMax)
	 */
	public static void iterador(long nMax){
		for ( int i = 0; i < nMax; i++ ) {
	        for ( int j = i + 1; j < nMax; j++ ) {
	            for ( int k = j + 1; k < nMax; k++ ) {
	                for (int l = k + 1; l < nMax; l++) {
	                    for (int m = l + 1; m < nMax; m++) {
	                        System.out.println((i+1)+"-"+(j+1)+"-"+(k+1)+"-"+(l+1)+"-"+(m+1));
	                    }
	                }
	            }
	        }
	    }
	}
}
