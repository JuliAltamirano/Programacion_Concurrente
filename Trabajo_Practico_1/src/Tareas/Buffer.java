package Tareas;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Buffer {
	
	private Lock queueLock;
	private final Condition bufferNoLleno;
	private static final int MAXSIZE = 10; 
	private Random rand;
	private Random sleep;
	private Queue <Integer> almacen; 
	

	public Buffer () {
		
		queueLock = new ReentrantLock(true);
		bufferNoLleno = queueLock.newCondition();
		rand = new Random();
		sleep = new Random();
		almacen = new LinkedList<>();
	}
	
	
	public void set () {
		
		queueLock.lock();
			int nro_rand = rand.nextInt(1000);
			if (almacen.size() == MAXSIZE) {
				System.out.println("Buffer lleno. El " + Thread.currentThread().getName() + " tira el producto");
			}
			else {
				almacen.add( nro_rand );
				System.out.println("El productor " + Thread.currentThread().getName() + " esta agregando el producto: " + nro_rand);
				System.out.println("------------------------------------------------------------------------------------------------------------------------------------------");
				bufferNoLleno.signalAll();
			}
			queueLock.unlock();
	}
	

	public void get() {
		
		queueLock.lock(); 
		try {
			while (almacen.size() == 0) {				
				bufferNoLleno.await();
			}
			Thread.sleep( sleep.nextInt(50) );
			Integer elemento = almacen.poll();
			if (elemento != null) {
				System.out.println("El producto obtenido por el " + Thread.currentThread().getName() + " es " + elemento + ". En el Buffer quedan " + almacen.size() + " elementos." );
				System.out.println("Los productos restantes son: " + almacen);
				System.out.println("------------------------------------------------------------------------------------------------------------------------------------------");
			}
		} catch ( InterruptedException e ) {	
		} finally {
			queueLock.unlock();
		}					
	}

	public int size_b() {
		return almacen.size();
	}
}


