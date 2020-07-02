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
    	
    	boolean primer_disparo = true;  // Con esto se chequea si se esta en el primer disparo (sirve para el if y else)
    	
    	while(generados < MAX_DATOS) {
    		
/*    		try {
    			
    			if (primer_disparo) {	// En la primer vuelta el tiempo del arrival rate tiene que dormir
    				
    				Thread.sleep(arr_rate);
	    			mon.disparo(2);
	    			primer_disparo = false;
    			}    				
    			else {
	    			mon.disparo(2);
					if (!rdp.getHabilitadaTemporal(2)) {
						
						long tiempo_actual = System.currentTimeMillis(); //Devuelve el tiempo actual de la maquina
						long tiempo = tiempo_actual - rdp.getTiempoDeHabilitacion(2); //la variable tiempo es lo que ya paso de arrival rate
						Thread.sleep(arr_rate - tiempo);  // Aca duerme el tiempo necesario hasta llegar al arrival rate
		    			mon.disparo(2);
					}
    			}
    		}
    		catch(InterruptedException e) {
    			System.out.printf("ERROR DE GENERAR DATOS");
    			return;
    		}
*/
    		generados++;
    		System.out.printf("GENERADOR: Datos generados: %d\n" , generados);
    		
    	}
    	
    }

}
