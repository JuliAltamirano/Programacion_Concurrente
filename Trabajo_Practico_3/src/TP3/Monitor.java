package TP3;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Monitor {

		private ReentrantLock lock = new ReentrantLock(true);
		private final Condition bloqueado = lock.newCondition();
		private final Condition bloqueado_temp = lock.newCondition();
		private RedDePetri rdp;

		public Monitor(RedDePetri rdp){
			this.rdp = rdp;
		}

		//Se le agrega InterruptedException para el caso en que se interrumpa el hilo que esta en await()
		public void disparo(int transicion) throws InterruptedException{
			//La entrada al monitor requiere tomar un LOCK para asegurar la exclusion mutua.
			try{
				lock.lock();
				
				//Si la transicion no puede dispararse el hilo esperar en una cola de condicion.
				while(!rdp.habilitada(transicion)) {

					if( rdp.esTemporal(transicion) && rdp.getTiempoDeHabilitacion(transicion) != 0 ) {			// Se ejecuta si la transicion es temporal 
						
						long tiempo_actual = System.currentTimeMillis(); 
						long tiempo = tiempo_actual - rdp.getTiempoDeHabilitacion(transicion); 	
						bloqueado_temp.await(tiempo, TimeUnit.MILLISECONDS);	// Espera en la cola de temporales el tiempo restante
						
						rdp.habilitar();
					}	
					else {			// En caso que no sea temporal
						bloqueado.await();
					}	
				}
				
				rdp.disparo(transicion);
				//El salir del monitor implica liberar el lock de exclusion mutua y despertar los hilos que esperaban.
				//Solo dos continuaran despiertos (Uno para cada Buffer) y el resto volvera a dormir (await())
				bloqueado.signalAll(); //comprobar	
				
			} finally {
				//Al cumplirse la condicion de disparo se dispara la transicion y se vuelve a la secuencia de codigo
				//mientras se sigue estando dentro del monitor (lock.lock).
				lock.unlock();
			}
		}
}