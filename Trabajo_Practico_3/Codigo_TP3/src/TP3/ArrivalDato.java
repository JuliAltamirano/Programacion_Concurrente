package TP3;

public class ArrivalDato extends Thread {

	private Politica politica;
	private Monitor monitor;
	private Buffer buffer;
	private String dato;
	private RedDePetri rdp;
	
	
	public ArrivalDato(Politica politica, Monitor monitor, Buffer buffer, RedDePetri rdp) {
		
		super();
		this.politica = politica;
		this.buffer = buffer;
		this.monitor = monitor;
		this.rdp = rdp;		
	}
	
	public void run() {
		
		while(!(currentThread().isInterrupted())) {
								
			try {
				
				int politica_num = -1;
				boolean habilitada_T0 = false;
				boolean habilitada_T1 = false;
				
				if (rdp.habilitada(0) && rdp.habilitada(1)) {
					
					politica_num = politica.politica();
					habilitada_T0 = false;
					habilitada_T1 = false;
				}
				else if (rdp.habilitada(0) && !rdp.habilitada(1)) {

					politica_num = -1;
					habilitada_T0 = true;
					habilitada_T1 = false;
				}				
				else if (!rdp.habilitada(0) && rdp.habilitada(1)) {

					politica_num = -1;
					habilitada_T0 = false;
					habilitada_T1 = true;
				}					
				else {

					politica_num = -1;
					habilitada_T0 = false;
					habilitada_T1 = false;
					
				}
				
				if(buffer.getId() == 1 && (politica_num == 0 || habilitada_T0)) {
					
					monitor.disparo(0);
					dato = "dato";
					buffer.producir(dato);
				}				
				else if(buffer.getId() == 2 && (politica_num == 1 || habilitada_T1)) {
					
					monitor.disparo(1);
					dato = "dato";
					buffer.producir(dato);
				}				
			}
			catch(InterruptedException e) {
				System.out.println("Hilo arrivo de datos buffer " + buffer.getId() + " INTERRUMPIDO");
				return;
			}
		}
	}
}
