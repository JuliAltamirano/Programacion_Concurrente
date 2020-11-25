package Tareas;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class Buffer{
	
	private int producido;
	private int consumido;
	private int sleep_sec;
	private Random rand;
	private Queue <Integer> buffer; 
	

	public Buffer (int MAXSIZE, int sleep_sec) {

		this.sleep_sec = sleep_sec;
		producido = 0;
		consumido = 0;
		rand = new Random();
		buffer = new LinkedList<>();
	}
	
	
	public void set () {
		try {
			Thread.sleep(sleep_sec);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		int nro_rand = rand.nextInt(1000);
		buffer.add( nro_rand );
		producido = producido + 1;
		System.out.println("El productor " + Thread.currentThread().getName() + " esta agregando el producto: " + nro_rand);		
		System.out.println("------------------------------------------------------------------------------------------------------------------------------------------");			
	}
	
	public void get() {
		try {
			Thread.sleep(sleep_sec);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		Integer elemento = buffer.poll();
		consumido = consumido + 1;
		if (elemento != null) {
			System.out.println("El producto obtenido por el " + Thread.currentThread().getName() + " es " + elemento + ". En el Buffer quedan " + buffer.size() + " elementos." );
			System.out.println("Los productos restantes son: " + buffer);
			System.out.println("------------------------------------------------------------------------------------------------------------------------------------------");
		}
	}

	public int getProducido() {
		return producido;
	}

	public int getConsumido() {
		return consumido;
	}

	public int size_b() {
		return buffer.size();
	}
	
}


