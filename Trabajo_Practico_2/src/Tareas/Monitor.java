package Tareas;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Monitor{
	
	private RdP rdp;
	
	private static final int [] T1 = {0,1,0,0,0,0};
	private static final int [] T2 = {0,0,1,0,0,0};
	private static final int [] T3 = {0,0,0,1,0,0};
	private static final int [] T4 = {0,0,0,0,1,0};
	
	private boolean [] buffers;
	
	private Lock bufferLock;
	private final Condition bufferLleno;
	private final Condition bufferVacio;
	
	private int numAux1;
	private int numAux2;

	public Monitor( RdP rdp ) {
		
		this.rdp = rdp;
		
		buffers = new boolean [2];
		
		buffers[0] = true;
		buffers[1] = true;
		
		bufferLock = new ReentrantLock(true);
		bufferLleno = bufferLock.newCondition();
		bufferVacio= bufferLock.newCondition();
		
		numAux1 = 0;
		numAux2 = 0;
	}

	public int bufferSet() throws InterruptedException {
			
		while ( true ) {
			
			bufferLock.lock();
				
			while( (!rdp.T_habilitada(T1) && !rdp.T_habilitada(T2)) || (!buffers[0] && !buffers[1]) ) {
				
				//System.out.println(Thread.currentThread().getName() + " estoy en Await");
				bufferLleno.await();
			}		
			if( rdp.T_habilitada(T1) ) {
				
				if ( buffers[0] ) {
					
					buffers[0] = false;	
					numAux1 = 1;
					break;
				}
			} 
			if( rdp.T_habilitada(T2) ) {
							
				if ( buffers[1] ) {
					
					buffers[1] = false;	
					numAux1 = 2;
					break;
				}
			}
			bufferLock.unlock();
		}
		bufferLock.unlock();
		
		return numAux1;
	}
	
	public int bufferGet() throws InterruptedException {
		
		while ( true ) {
			
			bufferLock.lock();
						
			while( !rdp.T_habilitada(T3) && !rdp.T_habilitada(T4) || (!buffers[0] && !buffers[1]) ) {
				
				bufferVacio.await();
			}
		
			if( rdp.T_habilitada(T3) ) {
				
				if ( buffers[0] ) {
					
					buffers[0] = false;
					numAux2 = 1;
					break;
				}
			}
			if( rdp.T_habilitada(T4)) {
							
				if ( buffers[1] ) {
					
					buffers[1] = false;	
					numAux2 = 2;
					break;
				}
			}
			bufferLock.unlock();
		}
		
		bufferLock.unlock();
		return numAux2;
	}
		
	
	public  void returnBuffer ( int numero ) {
		
		bufferLock.lock();

		buffers [numero-1] = true;
		
		bufferLock.unlock();
	}
	
	public void dispararMarca ( int marca ) {
		
		bufferLock.lock();

		switch (marca) {
			
			case 0: 
				int [] T0 = {1,0,0,0,0,0};
				rdp.actualizarMarca( T0);
				break;
			case 1: 
				rdp.actualizarMarca(T1);
				break;
			case 2: 
				rdp.actualizarMarca(T2);
				break;
			case 3: 
				rdp.actualizarMarca(T3);
				break;
			case 4: 
				rdp.actualizarMarca(T4);
				break;
			case 5: 
				int [] T5 = {0,0,0,0,0,1};
				rdp.actualizarMarca(T5);
				break;
		}
		
		bufferLock.unlock();
	}
	
	public void signalBufferLleno () {
		
		bufferLock.lock();
		
		bufferLleno.signal();
		
		bufferLock.unlock();
	}
	
	public void signalBufferVacio () {
		
		bufferLock.lock();
		
		bufferVacio.signal();
		
		bufferLock.unlock();
	}
	
}
	

	

	
	
		

				

	