package Main;


import java.lang.reflect.Array;
import java.util.ArrayList;

import Tareas.Buffer;
import Tareas.Consumidor;
import Tareas.Monitor;
import Tareas.Productor;
import Tareas.RdP;
import Tareas.Log;

public class Main {
	
		public static void main (String[] args) {
			
			ArrayList <Consumidor> consumidores = new ArrayList ();
			ArrayList <Productor> productores = new ArrayList ();
			ArrayList <Thread> hilos = new ArrayList ();
			
			int [] m0 = {5,0,8,0,0,10,0,15};
			RdP redPetri = new RdP(m0); 
			
			Buffer buffer1 = new Buffer(10, 0);
			Buffer buffer2 = new Buffer(15, 0);
			
			Monitor monitor = new Monitor( redPetri );
			
			Productor prod1 = new Productor(monitor, buffer1, buffer2);
			Productor prod2 = new Productor(monitor, buffer1, buffer2);
			Productor prod3 = new Productor(monitor, buffer1, buffer2);
			Productor prod4 = new Productor(monitor, buffer1, buffer2);
			Productor prod5 = new Productor(monitor, buffer1, buffer2);

			
			Thread productor1 = new Thread(prod1);
			Thread productor2 = new Thread(prod2);
			Thread productor3 = new Thread(prod3);
			Thread productor4 = new Thread(prod4);
			Thread productor5 = new Thread(prod5);
			
			Consumidor con1 = new Consumidor(monitor, buffer1, buffer2);
			Consumidor con2 = new Consumidor(monitor, buffer1, buffer2);
			Consumidor con3 = new Consumidor(monitor, buffer1, buffer2);
			Consumidor con4 = new Consumidor(monitor, buffer1, buffer2);
			Consumidor con5 = new Consumidor(monitor, buffer1, buffer2);
			Consumidor con6 = new Consumidor(monitor, buffer1, buffer2);
			Consumidor con7 = new Consumidor(monitor, buffer1, buffer2);
			Consumidor con8 = new Consumidor(monitor, buffer1, buffer2);
			
			Thread consumidor1 = new Thread(con1);
			Thread consumidor2 = new Thread(con2);
			Thread consumidor3 = new Thread(con3);
			Thread consumidor4 = new Thread(con4);
			Thread consumidor5 = new Thread(con5);
			Thread consumidor6 = new Thread(con6);   
			Thread consumidor7 = new Thread(con7);
			Thread consumidor8 = new Thread(con8);

			consumidores.add(con1);
			consumidores.add(con2);
			consumidores.add(con3);
			consumidores.add(con4);
			consumidores.add(con5);
			consumidores.add(con6);
			consumidores.add(con7);
			consumidores.add(con8);
			
			productores.add(prod1);
			productores.add(prod2);
			productores.add(prod3);
			productores.add(prod4);
			productores.add(prod5);
			
			hilos.add(productor1);
			hilos.add(productor2);
			hilos.add(productor3);
			hilos.add(productor4);
			hilos.add(productor5);
			hilos.add(consumidor1);
			hilos.add(consumidor2);
			hilos.add(consumidor3);
			hilos.add(consumidor4);
			hilos.add(consumidor5);
			hilos.add(consumidor6);
			hilos.add(consumidor7);
			hilos.add(consumidor8);
			
			
			//Log log = new Log (buffer1, buffer2, con1, con2, con3, con4, con5, con6, con7, con8, prod1, prod2, prod3, prod4, prod5, Thread.currentThread(), productor1, productor2, productor3, productor4, productor5, consumidor1, consumidor2, consumidor3, consumidor4, consumidor5, consumidor6, consumidor7, consumidor8);
			Log log = new Log (buffer1, buffer2, consumidores, productores, Thread.currentThread(), hilos);
			Thread hilo_log = new Thread( log );
		
			productor1.start();
			productor2.start();
			productor3.start();
			productor4.start();
			productor5.start();
			
			consumidor1.start();
			consumidor2.start();
			consumidor3.start();
			consumidor4.start();
			consumidor5.start();
			consumidor6.start();
			consumidor7.start();
			consumidor8.start();
			
			hilo_log.start();

		}
}
