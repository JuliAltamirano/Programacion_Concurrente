package TP3;

public class ControlActive extends Thread{

    private Monitor mon;
    private ControlOn control_on;
    private Buffer buffer;

    private InvTLog inv_t_log;

    public ControlActive(ControlOn control_on , Monitor mon, Buffer buffer, InvTLog inv_t_log){
        this.control_on 				= control_on;
        this.mon 						= mon;
        this.buffer 					= buffer;
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
                    
                    disparar(7);
					buffer.consumir();
                    inv_t_log.logInvT(7, "active", 1,"\n");
                    control_on.setEstadoActivo("IDLE");
        		}

	            if(buffer.getId() == 2){
	            	
	                    disparar(12);
	                    inv_t_log.logInvT(12, "active", 2,"");
	                    
	                    disparar(14);
	                    inv_t_log.logInvT(14, "active", 2,"");
	                    control_on.setEstadoActivo("ACTIVE");
	                    
	                    disparar(8);
						buffer.consumir();
	                    inv_t_log.logInvT(8, "active", 2,"\n");
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
