package Tareas;

public class Productor implements Runnable {

	private Buffer almacen; 
	
	public Productor ( Buffer almacen ) {
		this.almacen = almacen;
	}
	
	public void run() {
			int i=0; 
			while ( i<100 ) { 
				almacen.set();
				i++;
			}
	}
	
}