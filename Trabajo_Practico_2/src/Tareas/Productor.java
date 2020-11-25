package Tareas;

public class Productor implements Runnable {
	
	private Monitor monitor;
	private Buffer buffer1;
	private Buffer buffer2;
	private int numBuffer;
	private int producido;
	
	public Productor ( Monitor monitor, Buffer buffer1, Buffer buffer2 ) {
		
		this.monitor = monitor;
		this.buffer1 = buffer1;
		this.buffer2 = buffer2;
		numBuffer = 0;
		producido = 0;
	}
	
	public void run() {
				
		for(int i=0; i<10000; i++)
		{
			try {
				
				monitor.dispararMarca(0);
				numBuffer = monitor.bufferSet();
				
				if ( numBuffer == 1 ) {
					
					System.out.println("Set Buffer 1");
					monitor.dispararMarca(1);
					buffer1.set();
				}
				
				else if ( numBuffer == 2 ) {
					
					System.out.println("Set Buffer 2");
					monitor.dispararMarca(2);
					buffer2.set();
				}
				
				monitor.returnBuffer( numBuffer );
				monitor.signalBufferVacio();
				producido = producido + 1;
				
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}
	}

	public int getProducido() {
		return producido;
	}

}
