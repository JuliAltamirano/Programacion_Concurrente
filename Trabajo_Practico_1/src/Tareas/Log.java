package Tareas;
import java.io.PrintWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Log implements Runnable{
	
	private Buffer almacen;
	private Thread Main;
	private Thread productor1;
	private Thread productor2;
	private Thread productor3;
	private Thread productor4;
	private Thread productor5;
	private Thread productor6;
	private Thread productor7;
	private Thread productor8;
	private Thread productor9;
	private Thread productor10;
	private Thread consumidor1;
	private Thread consumidor2;
	
	private FileWriter fw;
	PrintWriter pw;

	public Log( Buffer almacen, Thread Main, Thread productor1, Thread productor2, Thread productor3, Thread productor4, Thread productor5, Thread productor6, Thread productor7, Thread productor8, Thread productor9, Thread productor10, Thread consumidor1, Thread consumidor2 ) {
		
		this.almacen = almacen;
		this.Main = Main;
		this.productor1 = productor1;
		this.productor2 = productor2;
		this.productor3 = productor3;
		this.productor4 = productor4;
		this.productor5 = productor5;
		this.productor6 = productor6;
		this.productor7 = productor7;
		this.productor8 = productor8;
		this.productor9 = productor9;
		this.productor10 = productor10;
		this.consumidor1 = consumidor1;
		this.consumidor2 = consumidor2;
		
	}

	public void run(){
		
		while( true) {
			
			try {
				fw = new FileWriter("archivo_log.txt", true);
				pw = new PrintWriter(fw);
				
				pw.println("La cantidad de lugares ocupados en el buffer es: " + almacen.size_b());
				pw.println("Estado del Main: " + Main.getState());
				pw.println("Estado del Productor 1: " + productor1.getState());
				pw.println("Estado del Productor 2: " + productor2.getState());
				pw.println("Estado del Productor 3: " + productor3.getState());
				pw.println("Estado del Productor 4: " + productor4.getState());
				pw.println("Estado del Productor 5: " + productor5.getState());
				pw.println("Estado del Productor 6: " + productor6.getState());
				pw.println("Estado del Productor 7: " + productor7.getState());
				pw.println("Estado del Productor 8: " + productor8.getState());
				pw.println("Estado del Productor 9: " + productor9.getState());
				pw.println("Estado del Productor 10: " + productor10.getState());
				pw.println("Estado del Consumidor 1: " + consumidor1.getState());
				pw.println("Estado del Consumidor 2: " + consumidor2.getState());
				
				
				if (productor1.getState().compareTo(Thread.State.TERMINATED) == 0 && productor2.getState().compareTo(Thread.State.TERMINATED) ==0 && productor3.getState().compareTo(Thread.State.TERMINATED) ==0 && productor4.getState().compareTo(Thread.State.TERMINATED) ==0 && productor5.getState().compareTo(Thread.State.TERMINATED) ==0 && productor6.getState().compareTo(Thread.State.TERMINATED) ==0 && productor7.getState().compareTo(Thread.State.TERMINATED) ==0 && productor8.getState().compareTo(Thread.State.TERMINATED) ==0 && productor9.getState().compareTo(Thread.State.TERMINATED) ==0 && productor10.getState().compareTo(Thread.State.TERMINATED) ==0) {
					
					pw.close();
					break;
				}
				
				Thread.sleep(2000);
				pw.close();
				
			} catch (IOException | InterruptedException e1) {
				
				e1.printStackTrace();
			}
		}
	}
}
