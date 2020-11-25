package Tareas;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.io.FileWriter;
import java.io.IOException;

public class Log implements Runnable{
	
	private Thread Main;
	private Buffer buff1;
	private Buffer buff2;
	private ArrayList <Consumidor> consumidores;
	private ArrayList <Productor> productores;
	private ArrayList <Thread> hilos;
	
	
	private FileWriter fw;
	PrintWriter pw;
	
	public Log(Buffer buff1, Buffer buff2, ArrayList <Consumidor> consumidores, ArrayList <Productor> productores, Thread Main, ArrayList <Thread> hilos) {
		
		this.buff1 = buff1;
		this.buff2 = buff2;
		this.consumidores = consumidores;
		this.productores = productores;
		this.Main = Main;
		this.hilos = hilos;	
	}
	
public void run(){
		
		while( true) {
			
			try {
				fw = new FileWriter("archivo_log.txt", true);
				pw = new PrintWriter(fw);
				
				Thread.sleep(100);
				
				pw.println("Estado del Main: " + Main.getState());
				pw.println("Estado del Productor 1: " + hilos.get(0).getState());
				pw.println("Estado del Productor 2: " + hilos.get(1).getState());
				pw.println("Estado del Productor 3: " + hilos.get(2).getState());
				pw.println("Estado del Productor 4: " + hilos.get(3).getState());
				pw.println("Estado del Productor 5: " + hilos.get(4).getState());
				pw.println("Estado del Consumidor 1: " + hilos.get(5).getState());
				pw.println("Estado del Consumidor 2: " + hilos.get(6).getState());
				pw.println("Estado del Consumidor 3: " + hilos.get(7).getState());
				pw.println("Estado del Consumidor 4: " + hilos.get(8).getState());
				pw.println("Estado del Consumidor 5: " + hilos.get(9).getState());
				pw.println("Estado del Consumidor 6: " + hilos.get(10).getState());
				pw.println("Estado del Consumidor 7: " + hilos.get(11).getState());
				pw.println("Estado del Consumidor 8: " + hilos.get(12).getState());
				
				if (hilos.get(0).getState().compareTo(Thread.State.TERMINATED) == 0 && hilos.get(1).getState().compareTo(Thread.State.TERMINATED) ==0 && hilos.get(2).getState().compareTo(Thread.State.TERMINATED) ==0 && hilos.get(3).getState().compareTo(Thread.State.TERMINATED) ==0 && hilos.get(4).getState().compareTo(Thread.State.TERMINATED) ==0) {
					Thread.sleep(4000);
					pw.println("Estado del Main: " + Main.getState());
					pw.println("Estado del Productor 1: " + hilos.get(0).getState());
					pw.println("Estado del Productor 2: " + hilos.get(1).getState());
					pw.println("Estado del Productor 3: " + hilos.get(2).getState());
					pw.println("Estado del Productor 4: " + hilos.get(3).getState());
					pw.println("Estado del Productor 5: " + hilos.get(4).getState());
					pw.println("Estado del Consumidor 1: " + hilos.get(5).getState());
					pw.println("Estado del Consumidor 2: " + hilos.get(6).getState());
					pw.println("Estado del Consumidor 3: " + hilos.get(7).getState());
					pw.println("Estado del Consumidor 4: " + hilos.get(8).getState());
					pw.println("Estado del Consumidor 5: " + hilos.get(9).getState());
					pw.println("Estado del Consumidor 6: " + hilos.get(10).getState());
					pw.println("Estado del Consumidor 7: " + hilos.get(11).getState());
					pw.println("Estado del Consumidor 8: " + hilos.get(12).getState());
					pw.println("El Consumidor 1 consumio: " + consumidores.get(0).getConsumido() + " productos");
					pw.println("El Consumidor 2 consumio: " + consumidores.get(1).getConsumido() + " productos");
					pw.println("El Consumidor 3 consumio: " + consumidores.get(2).getConsumido() + " productos");
					pw.println("El Consumidor 4 consumio: " + consumidores.get(3).getConsumido() + " productos");
					pw.println("El Consumidor 5 consumio: " + consumidores.get(4).getConsumido() + " productos");
					pw.println("El Consumidor 6 consumio: " + consumidores.get(5).getConsumido() + " productos");
					pw.println("El Consumidor 7 consumio: " + consumidores.get(6).getConsumido() + " productos");
					pw.println("El Consumidor 8 consumio: " + consumidores.get(7).getConsumido() + " productos");
					pw.println("El Productor 1 produjo: " + productores.get(0).getProducido() + " productos");
					pw.println("El Productor 2 produjo: " + productores.get(1).getProducido() + " productos");
					pw.println("El Productor 3 produjo: " + productores.get(2).getProducido() + " productos");
					pw.println("El Productor 4 produjo: " + productores.get(3).getProducido() + " productos");
					pw.println("El Productor 5 produjo: " + productores.get(4).getProducido() + " productos");
					pw.println("El Buffer 1 produjo: " + buff1.getProducido() + " productos");
					pw.println("El Buffer 1 consumio: " + buff1.getConsumido() + " productos");
					pw.println("El Buffer 2 produjo: " + buff2.getProducido() + " productos");
					pw.println("El Buffer 2 consumio: " + buff2.getConsumido() + " productos");
					

					System.out.println("");
					System.out.println("Registro LOG finalizo su ejecucion");
					pw.close();
					break;
				}
				
				pw.close();
				
			} catch (IOException | InterruptedException e1) {
				e1.printStackTrace();
			}
		}
	}

}
