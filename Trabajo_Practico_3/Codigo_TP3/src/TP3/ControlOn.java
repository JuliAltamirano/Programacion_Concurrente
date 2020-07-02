package TP3;

public class ControlOn extends Thread {

	private Monitor mon;
	private Buffer buffer;
	private RedDePetri rdp;
	private String estado_on = "STANDBY";
	private String estado_activo = "IDLE";
	private long hilos_procesador_tiempo[];
	private int svRate;
	private InvTLog inv_t_log;
	
	public ControlOn(Buffer buffer, Monitor mon, RedDePetri rdp, long hilos_procesador_tiempo[], int svRate, InvTLog inv_t_log) {
		this.buffer 					= buffer;
		this.mon 						= mon;
		this.rdp 						= rdp;
		this.hilos_procesador_tiempo 	= hilos_procesador_tiempo;
		this.svRate 					= svRate;
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
										
				/*	disparar(7);
					if (!getHabilitadaTemporal(7)) {
							
						hilos_procesador_tiempo[0] = System.currentTimeMillis();
						long tiempo = hilos_procesador_tiempo[0] - getTiempoDeHabilitacion(7);
						int i = 0;
						if (hilos_procesador_tiempo[0] > hilos_procesador_tiempo[1] && hilos_procesador_tiempo[1] != 0)
							i = 1;
						Thread.sleep((svRate - tiempo) + i);
						hilos_procesador_tiempo[0] = 0;
						disparar(7);
					} */
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

				/*	disparar(8);
					if (!getHabilitadaTemporal(8)) {
						
						hilos_procesador_tiempo[0] = System.currentTimeMillis();
						long tiempo = hilos_procesador_tiempo[0] - getTiempoDeHabilitacion(8);
						int i = 0;
						if (hilos_procesador_tiempo[0] > hilos_procesador_tiempo[1] && hilos_procesador_tiempo[1] != 0)
							i = 1;
						Thread.sleep((svRate - tiempo) + i);
						hilos_procesador_tiempo[0] = 0;
						disparar (8);
					} */
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
	
	public int getSvRate() {
		return svRate;
	}
	
	public long getTiempoDeHabilitacion(int transicion) {
		return rdp.getTiempoDeHabilitacion(transicion);
	}
	
	public void disparar(int transicion) throws InterruptedException {
		mon.disparo(transicion);
	}
}