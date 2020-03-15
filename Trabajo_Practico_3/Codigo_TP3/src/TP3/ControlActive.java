package TP3;

public class ControlActive extends Thread{

    private Monitor mon;
    private ControlOn control_on;
    private Buffer buffer;

    private long hilos_procesador_tiempo[];
    private InvTLog inv_t_log;

    public ControlActive(ControlOn control_on , Monitor mon, Buffer buffer, long hilos_procesador_tiempo[], InvTLog inv_t_log){
        this.control_on 				= control_on;
        this.mon 						= mon;
        this.buffer 					= buffer;
		this.hilos_procesador_tiempo 	= hilos_procesador_tiempo;
		this.inv_t_log					= inv_t_log;
    }

    public void run(){
        //Se encarga de procesar el dato.
        while(!(currentThread().isInterrupted())){
        	try{
        		if(buffer.getId() == 1){
        			
                    disparar(11);
                    inv_t_log.logInvT(11, "active", 1,"");
                    
                    disparar(13);
                    inv_t_log.logInvT(13, "active", 1,"");
                    control_on.setEstadoActivo("ACTIVE");
                    
                    hilos_procesador_tiempo[1] = System.currentTimeMillis();
					long tiempo = hilos_procesador_tiempo[1] - control_on.getTiempoDeHabilitacion(7);
					if ( tiempo < control_on.getSvRate()) {
						
						int i = 0;
						if (hilos_procesador_tiempo[1] > hilos_procesador_tiempo[0] && hilos_procesador_tiempo[0] != 0)
							i = 1;
						Thread.sleep((control_on.getSvRate() - tiempo) + i);
						hilos_procesador_tiempo[1] = 0;
					}
                    buffer.consumir();
                    disparar(7);
                    inv_t_log.logInvT(7, "active", 1,"\n");
                    control_on.setEstadoActivo("IDLE");
        		}

	            if(buffer.getId() == 2){
	            	
	                    disparar(12);
	                    inv_t_log.logInvT(12, "active", 2,"");
	                    
	                    disparar(14);
	                    inv_t_log.logInvT(14, "active", 2,"");
	                    control_on.setEstadoActivo("ACTIVE");
	                    
	                    hilos_procesador_tiempo[1] = System.currentTimeMillis();
						long tiempo = hilos_procesador_tiempo[1] - control_on.getTiempoDeHabilitacion(8);
						if ( tiempo < control_on.getSvRate()) {
							
							int i = 0;
							if (hilos_procesador_tiempo[1] > hilos_procesador_tiempo[0] && hilos_procesador_tiempo[0] != 0)
								i = 1;
							Thread.sleep((control_on.getSvRate() - tiempo) + i);
							hilos_procesador_tiempo[1] = 0;
						}
						buffer.consumir();
	                    disparar(8);
	                    inv_t_log.logInvT(18, "active", 2,"\n");
	                    control_on.setEstadoActivo("IDLE");
	            }
        	}
            catch(InterruptedException e){
            	System.out.println("Hilo ControlActive " + buffer.getId() + " INTERRUMPIDO");
                return;
            }
        }
        System.out.println("Hilo ControlActive " + buffer.getId() + " INTERRUMPIDO");
    }

    public void disparar(int transicion) throws InterruptedException{
        mon.disparo(transicion); 
    }
}
