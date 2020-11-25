package Tareas;

public class Consumidor  implements Runnable {

	private Buffer almacen; 
	
	public Consumidor ( Buffer almacen ) {
		
		this.almacen = almacen;
	}
	
	public void run() {
		while (true) {		
				almacen.get();
		}
	}
	
}

