package TP3;

import java.util.ArrayList;

public class Buffer{
	private ArrayList<String> buffer = new ArrayList<>();
	private int id; // Nombre del Buffer
	private int ingresados = 0;
	private int procesados = 0;

	public Buffer(int id){
		this.id=id;
	}
	
	public void  producir(String dato){
		
		buffer.add(dato);
		ingresados++;

	}

	public void consumir() {
			
		buffer.remove( getSize() -1);
		procesados++;
	
	}

	public int getSize(){

		return buffer.size();
	}

	public int getId() {
		return id;
	}

	public int getIngresados() {
		return ingresados;
	}

	public int getProcesados() {
		return procesados;
	}
}