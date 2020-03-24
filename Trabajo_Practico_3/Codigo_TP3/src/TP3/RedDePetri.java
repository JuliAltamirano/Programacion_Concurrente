package TP3;

public class RedDePetri {

    private int[] 	marca;
    private int[][] matriz_b;
    private int[][] matriz_w;
    private int[][] matriz_inv_p;
    int cant_p, cant_t, arrRate, svRate1, svRate2;
    private boolean[]	habilitadas;
    private boolean[]	habilitadas_temporales;
    private long[] 		tiempo_t_habilitadas;
    private boolean[] 	prueba_inv_p;
    private boolean		fallo_inv_p;
    private int 		cant_fallo_inv_p;
    private String 		log_inv_t;

    RedDePetri(int[] marca, int[][] matriz_b, int[][] matriz_w, int[][] matriz_inv_p, int cant_t, int cant_p, int arrRate, int svRate1, int svRate2 ){
        this.marca 				= marca;
        this.matriz_b			= matriz_b;
        this.matriz_w			= matriz_w;
        this.matriz_inv_p 		= matriz_inv_p;
        this.cant_p 			= cant_p;
        this.cant_t 			= cant_t;
        this.arrRate			= arrRate;
        this.svRate1			= svRate1;
        this.svRate2			= svRate2;
        
        habilitadas 				= new boolean[cant_t];
        habilitadas_temporales		= new boolean[3];
        habilitadas_temporales[0]	= false;
        habilitadas_temporales[1]	= false;
        habilitadas_temporales[2]	= false;
        tiempo_t_habilitadas 		= new long[3];
        
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
    
    public void setHabilitadaTemporal( int transicion, boolean habilitar ) {
    	
    	if (transicion == 2)
    		habilitadas_temporales[0] = habilitar;
    	else if (transicion == 7 || transicion == 8) 
    		habilitadas_temporales[transicion-6] = habilitar;
    }
    
    public boolean getHabilitadaTemporal( int transicion ) {
    	
    	if (transicion == 2)
    		return habilitadas_temporales[0];
    	return habilitadas_temporales[transicion-6];
    }
    
    public boolean habilitadaTemporal(int transicion){

    	long tiempo_actual = System.currentTimeMillis();
		long tiempo = tiempo_actual - getTiempoDeHabilitacion(transicion);
		if ((transicion == 2 && tiempo < arrRate) || (transicion == 7 && tiempo < svRate1) || (transicion == 8 && tiempo < svRate2)) {
			return false;
		}
		return true;		
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

        setTiempoDeHabilitacion(transicion);        
    }
    
    private void setTiempoDeHabilitacion (int transicion) { //posicion del tiempo almacenado en el array (0, 1 y 2), correspondiente a las transiciones 2,7 u 8 respectivamente
    	
    	if (transicion == 0 || transicion == 1)
    		tiempo_t_habilitadas[0] = System.currentTimeMillis(); // se habilito T2 con el disparo de T0 o T1
    	else if ((transicion == 13 && habilitada(7)) || (transicion == 14 && habilitada(8)))
    		tiempo_t_habilitadas[transicion-12] = System.currentTimeMillis(); // se habilito T2 con el disparo de T0 o T1
    }
    
	public long getTiempoDeHabilitacion (int transicion) { //posicion del tiempo almacenado en el array (0, 1 y 2), correspondiente a las transiciones 2,7 u 8 respectivamente
    	
		if (transicion == 2)
    		return tiempo_t_habilitadas[0];
    	else if (transicion == 7 || transicion == 8)
    		return tiempo_t_habilitadas[transicion-6];
    	return 0;
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