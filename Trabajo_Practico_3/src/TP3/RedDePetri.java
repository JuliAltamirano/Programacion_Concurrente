package TP3;

public class RedDePetri {

    private int[] 	marca;
    private int[][] matriz_b;
    private int[][] matriz_w;
    private int[][] matriz_inv_p;
    int cant_p;
    int cant_t;
    private boolean[]	habilitadas;
    private long[] 		tiempo_t_habilitadas;
    private boolean[] 	prueba_inv_p;
    private boolean		fallo_inv_p;
    private int 		cant_fallo_inv_p;
    private String 		log_inv_t;

    RedDePetri(int[] marca, int[][] matriz_b, int[][] matriz_w, int[][] matriz_inv_p, int cant_t, int cant_p ){
        this.marca 				= marca;
        this.matriz_b			= matriz_b;
        this.matriz_w			= matriz_w;
        this.matriz_inv_p 		= matriz_inv_p;
        this.cant_p 			= cant_p;
        this.cant_t 			= cant_t;
        habilitadas 			= new boolean[cant_t];
        tiempo_t_habilitadas 	= new long[3];
        prueba_inv_p			= new boolean[matriz_inv_p.length];
        fallo_inv_p				= true;
        cant_fallo_inv_p		= 0;
        log_inv_t				= "";
        
        habilitar();
    }
    //Para sensibilizar las transiciones iterara sobre el marcado actual y la matriz W
    //siguiendo el lineamiento logico de la ecuacion fundamental de redes de petri.
    
    private void habilitar(){
        for(int i = 0 ; i < cant_t ; i++){
            for(int j = 0; j <cant_p ; j++){
            	
            	if (i==3 && (marca[0] != 0 || marca[2] != 0) ) {
					habilitadas[i] = false;
					break;
				}
				else if (i==4 && (marca[1] != 0 || marca[3] != 0) ) {
					habilitadas[i] = false;
					break;
				}
            	
				if (! (marca[j] >= matriz_b[j][i])) {
                   habilitadas[i] = false;
                   break;
                }
                habilitadas[i] = true;
            }
        }
    }
    
    public boolean habilitada(int transicion){

        return habilitadas[transicion];
    }

    //El disparo de una transicion corresponde a la modificacion del marcado actual
    //a partir de la matriz de incidencia combinada W = (I+) + (I-).
    public void disparo( int transicion){
    	
        for(int i = 0; i < cant_p; i++){
            marca[i] += matriz_w[i][transicion];
        }
        habilitar();
        pruebaInvP();
        InvTLog(transicion);

        if ((transicion == 0 || transicion == 1) && habilitada(2))
        	setTiempoDeHabilitacion(0); // se habilito T2 con el disparo de T0 o T1
        if ((transicion == 13 && habilitada(7)) || (transicion == 14 && habilitada(8)))
        	setTiempoDeHabilitacion(transicion-12); // se habilitaron: T7 con el disparo de T13 o T8 con el disparo de T14 (transicion - 6)
    }
    
    private void setTiempoDeHabilitacion (int posicion_tiempo) { //posicion del tiempo almacenado en el array (0, 1 y 2), correspondiente a las transiciones 2,7 u 8 respectivamente
    	
    	tiempo_t_habilitadas[posicion_tiempo] = System.currentTimeMillis();
    }
    
    public long getTiempoDeHabilitacion (int transicion) {
    	
    	return tiempo_t_habilitadas[transicion-7];
    }
    
    public void pruebaInvP(){

    	for(int i = 0; i < matriz_inv_p.length; i++) {
    		int chequeo = 0;
    		int j = 0;
    		
    		//Cuento el largo de la ecuacion del P-Invariante
    		while(matriz_inv_p[i][j]!=123) {
    			j++;
    		}
    		
    		for(int n = 0; n < j-1; n++) {
    			chequeo = chequeo + marca[matriz_inv_p[i][n]];
    		}
    		if(chequeo == matriz_inv_p[i][j-1]) {
    			prueba_inv_p[i] = true;
    		}
    		else {
    			prueba_inv_p[i] = false;
    			System.out.println("Invariante P numero "+i+" FALLO la prueba");
    			fallo_inv_p = false;
    			cant_fallo_inv_p = cant_fallo_inv_p + 1;
    		}
    	}
    }
    
    public boolean[] getPruebaInvP() {
    	return prueba_inv_p;
    }
    
    public String imprimeInvP() {
    	String retorna = "";
    	
    	retorna = "Ecuacion Invariante P nro "; 
    	for(int i = 0; i < prueba_inv_p.length; i++) {
    		retorna += i + ": " + prueba_inv_p[i] + " | ";
    	}
    	return retorna;
    }
    
    public String falloInvP() {
    	if(fallo_inv_p) {
    		return "No hubo fallos en pruebas de P Invariantes";
    	}
    	else {
    		return "Hubo "+cant_fallo_inv_p+" de fallos en pruebas de P Invariantes";
    	}
    }
        
    public void InvTLog(int transicion) {
    	log_inv_t = log_inv_t +"T" + transicion + "-";
    }
    
    public String getInvTLog() {
    	return log_inv_t;
    }
}