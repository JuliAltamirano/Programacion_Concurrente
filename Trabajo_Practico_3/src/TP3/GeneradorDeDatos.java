package TP3;

public class GeneradorDeDatos extends Thread {	
	
    private Monitor mon;
   
    private int generados = 0;
    private int MAX_DATOS;
    
    public GeneradorDeDatos( Monitor m, int max ) {
        super();
     
        this.mon = m;
        this.MAX_DATOS = max;        
        
    }


    public void run() {
  
    	while(generados < MAX_DATOS) {   		
    		try {
    			mon.disparo(2);		// Se dispara siempre la transicion de generacion de datos 			
    		}
    		catch(InterruptedException e) {
    			System.out.printf("ERROR DE GENERAR DATOS");
    			return;
    		}

    		generados++;
    		System.out.printf("GENERADOR: Datos generados: %d\n" , generados);
    	}
    }
}
