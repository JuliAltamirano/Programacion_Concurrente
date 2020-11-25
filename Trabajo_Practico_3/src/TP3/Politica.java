package TP3;

import java.util.Random;

public class Politica {

	private Random rnd;
	private Buffer buffers[];


    public Politica(Buffer buffers[]){
        this.buffers = buffers;
        rnd= new Random();
        
    }

    public int politica(){
    	if (buffers[0].getSize() == buffers[1].getSize()) {
    		
    		int seleccionado= rnd.nextInt(2);
    		return seleccionado;
       	}
        else if(buffers[0].getSize() > buffers[1].getSize())
            return 1;
        else
        	return 0;
    }
}