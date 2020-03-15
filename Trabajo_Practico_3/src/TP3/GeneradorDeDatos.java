package TP3;

public class GeneradorDeDatos extends Thread {	
	
    private Monitor mon;
    private int arr_rate;
   
    private int generados = 0;
    private int MAX_DATOS;
    
    public GeneradorDeDatos( Monitor m, int max , int arr_rate) {
        super();
     
        this.mon = m;
        this.MAX_DATOS = max;
        this.arr_rate = arr_rate;
        
    }


    public void run() {
    	
    	while(generados < MAX_DATOS) {
    		
    		try {
    			Thread.sleep(arr_rate);
    			mon.disparo(2);
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
