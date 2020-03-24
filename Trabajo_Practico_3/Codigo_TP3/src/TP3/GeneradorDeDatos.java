package TP3;

public class GeneradorDeDatos extends Thread {	
	
    private Monitor mon;
    private RedDePetri rdp;
    private int arr_rate;
   
    private int generados = 0;
    private int MAX_DATOS;
    
    public GeneradorDeDatos( Monitor m, int max , int arr_rate, RedDePetri rdp) {
        super();
     
        this.mon = m;
        this.MAX_DATOS = max;
        this.arr_rate = arr_rate;
        this.rdp = rdp;        
    }


    public void run() {
    	
    	boolean primer_disparo = true;
    	
    	while(generados < MAX_DATOS) {
    		
    		try {
    			
    			if (primer_disparo) {
    				
    				Thread.sleep(arr_rate);
	    			mon.disparo(2);
	    			primer_disparo = false;
    			}    				
    			else {
	    			mon.disparo(2);
					if (!rdp.getHabilitadaTemporal(2)) {
						
						long tiempo_actual = System.currentTimeMillis();
						long tiempo = tiempo_actual - rdp.getTiempoDeHabilitacion(2);
						Thread.sleep(arr_rate - tiempo);
		    			mon.disparo(2);
					}
    			}
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
