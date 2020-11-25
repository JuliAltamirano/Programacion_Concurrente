package TP3;

public class ArrivalDato extends Thread {

	private Politica politica;
	private Monitor monitor;
	private Buffer buffer;
	private String dato;
	
	public ArrivalDato(Politica politica, Monitor monitor, Buffer buffer) {
		
		super();
		this.politica = politica;
		this.buffer = buffer;
		this.monitor = monitor;	
	}
	
	public void run() {
		
		while(!(currentThread().isInterrupted())) {
								
			try {
				
				int politica_num = politica.politica();  //Retorna valor para seleccionar buffer
				
				if(buffer.getId() == 1 && politica_num == 0) {
					
					monitor.disparo(politica_num);
					dato = "dato";
					buffer.producir(dato);
				}				
				else if(buffer.getId() == 2 && politica_num == 1 ) {
					
					monitor.disparo(politica_num);
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
