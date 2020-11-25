package TP3;

public class ControlOn extends Thread {

	private Monitor mon;
	private Buffer buffer;
	private String estado_on = "STANDBY";
	private String estado_activo = "IDLE";
	private InvTLog inv_t_log;
	
	public ControlOn(Buffer buffer, Monitor mon, InvTLog inv_t_log) {
		this.buffer 					= buffer;
		this.mon 						= mon;
		this.inv_t_log					= inv_t_log;
	}

	public void run() {
		while (!(currentThread().isInterrupted())) {
			try {
				if (buffer.getId() == 1) {

				///// ENCENDIDO CPU
					disparar(9);
					inv_t_log.logInvT(9, "on", 1,"");
					disparar(5);
					inv_t_log.logInvT(5, "on", 1,"");
					setEstadoOn("ON");

					disparar(13);
					inv_t_log.logInvT(13, "on", 1,"");
					setEstadoActivo("ACTIVE");
										
					disparar(7); 
					buffer.consumir();
					inv_t_log.logInvT(7, "on", 1,"");
					setEstadoActivo("IDLE");

					disparar(3);
					inv_t_log.logInvT(3, "on", 1,"\n");
					setEstadoOn("STANDBY");
				}
				if (buffer.getId() == 2) {
				
					disparar(10);
					inv_t_log.logInvT(10, "on", 2,"");
					disparar(6);
					inv_t_log.logInvT(6, "on", 2,"");
					setEstadoOn("ON");
				
					disparar(14);
					inv_t_log.logInvT(14, "on", 2,"");
					setEstadoActivo("ACTIVE");

					disparar(8);
					buffer.consumir();
					inv_t_log.logInvT(8, "on", 2,"");
					setEstadoActivo("IDLE");
					
					disparar(4);
					inv_t_log.logInvT(4, "on", 2,"\n");
					setEstadoOn("STANDBY");				
				}
			}
			catch (InterruptedException e) {
				System.out.println("Hilo ControlOn " + buffer.getId() + " INTERRUMPIDO ");
				return;
			}
		}
		System.out.println("Hilo ControlOn " + buffer.getId() + " INTERRUMPIDO");
	}

	public String getEstadoOn() {
		return estado_on;
	}

	public void setEstadoOn(String estado_on) {
		this.estado_on = estado_on;
	}

	public String getEstadoActivo() {
		return estado_activo;
	}

	public void setEstadoActivo(String estado_activo) {
		this.estado_activo = estado_activo;
	}
	
	public void disparar(int transicion) throws InterruptedException {
		mon.disparo(transicion);
	}
}