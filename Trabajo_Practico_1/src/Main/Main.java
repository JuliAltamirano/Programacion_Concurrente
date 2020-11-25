package Main;

import Tareas.Buffer;
import Tareas.Consumidor;
import Tareas.Log;
import Tareas.Productor;

public class Main {

	public static void main(String[] args) {
		
		Buffer almacen = new Buffer();
		
		Thread productor1 = new Thread(new Productor(almacen));
		Thread productor2 = new Thread(new Productor(almacen));
		Thread productor3 = new Thread(new Productor(almacen));
		Thread productor4 = new Thread(new Productor(almacen));
		Thread productor5 = new Thread(new Productor(almacen));
		Thread productor6 = new Thread(new Productor(almacen));
		Thread productor7 = new Thread(new Productor(almacen));
		Thread productor8 = new Thread(new Productor(almacen));
		Thread productor9 = new Thread(new Productor(almacen));
		Thread productor10 = new Thread(new Productor(almacen));
		
		Consumidor consumidor = new Consumidor( almacen );
		Thread consumidor1 = new Thread( consumidor );
		Thread consumidor2 = new Thread( consumidor );
		
		Log log = new Log ( almacen, Thread.currentThread(), productor1, productor2, productor3, productor4, productor5, productor6, productor7, productor8, productor9, productor10, consumidor1, consumidor2 );	
		Thread hilo_log = new Thread( log );
		
		productor1.start();
		productor2.start();
		productor3.start();
		productor4.start();
		productor5.start();
		productor6.start();
		productor7.start();
		productor8.start();
		productor9.start();
		productor10.start();
		
		consumidor1.start();
		consumidor2.start();

		hilo_log.start();
		
	}
}
	


