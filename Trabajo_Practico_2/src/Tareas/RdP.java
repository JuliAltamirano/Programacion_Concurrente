package Tareas;


public class RdP {

	private static final int[][]  W = {{-1,1,1,0,0,0},{1,-1,-1,0,0,0},{0,0,0,-1,-1,1},{0,0,0,1,1,-1},{0,1,0,-1,0,0},{0,-1,0,1,0,0},{0,0,1,0,-1,0},{0,0,-1,0,1,0}};
	private int [] marca;
	private int [] S;	//vector de disparo de las transiciones
	
	public RdP ( int [] marca ){
		
		this.marca= marca;
		S = new int [6];
	}
	
	public int[] multiplicacion( int [] S ) {
		
		this.S = S; 
		int [] resultado = new int [8];
		
		for ( int i = 0; i < S.length; i++ ) {
							
				for ( int j = 0; j < W.length; j++ ) {
					
					resultado [j] += S[i] * W[j][i];
					
				}
			}
		//System.out.println( resultado[0] + " - " + resultado[1] + " - " + resultado[2] + " - " + resultado[3] + " - " + resultado[4] + " - " + resultado[5] + " - " + resultado[6] + " - " + resultado[7] );
		
		return resultado;
	}
	
	public boolean T_habilitada ( int [] S) {
		
		this.S = S;
		int [] multip = new int [8];
		multip = multiplicacion (S);
		
		int [] resultado = new int [8];
		boolean habilitado = true;
		
		for ( int i = 0; i < marca.length; i++ ) {
			
			resultado [i] = marca [i] + multip [i];
			
			if ( resultado[i] < 0) {
				habilitado = false;
			}
		}
		
		return habilitado;
	}
	
	public void actualizarMarca (int [] S ) {
		
		this.S = S;
		int [] multip = new int [8];
		multip = multiplicacion (S);
		
		for ( int i = 0; i < marca.length; i++ ) {
			
			marca [i] = marca [i] + multip [i];
		}
		
		//System.out.println( marca[0] + " - " + marca[1] + " - " + marca[2] + " - " + marca[3] + " - " + marca[4] + " - " + marca[5] + " - " + marca[6] + " - " + marca[7] );
	}
	
}
