package Tareas;

public class Consumidor implements Runnable {

	private Monitor monitor; 
	private Buffer buffer1;
	private Buffer buffer2;
	private int numBuffer;
	private int consumido;
	
	public Consumidor ( Monitor monitor, Buffer buffer1, Buffer buffer2 ) {
		
		this.monitor = monitor;
		this.buffer1 = buffer1;
		this.buffer2 = buffer2;
		numBuffer = 0;
		consumido = 0;
	}
	
	public void run() {
		
		while (true) {
			
			try {
				
				numBuffer = monitor.bufferGet();
				
				if ( numBuffer == 1 ) {
					
					System.out.println("Get Buffer 1");
					monitor.dispararMarca(3);
					buffer1.get();
				}
				
				else if ( numBuffer == 2 ){
					
					System.out.println("Get Buffer 2");
					monitor.dispararMarca(4);
					buffer2.get();
															
				}
				
				monitor.returnBuffer( numBuffer );				
				monitor.dispararMarca(5);
				monitor.signalBufferLleno();
				consumido = consumido + 1;
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	}

	public int getConsumido() {
		return consumido;
	}
	
}