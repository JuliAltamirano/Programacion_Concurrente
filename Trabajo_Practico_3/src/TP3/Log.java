package TP3;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Log extends Thread{

	private FileWriter file = null;
    private PrintWriter pw; //log general
    
    private FileWriter file2 = null;
    private PrintWriter pw2; //log InvT

    private FileWriter file3 = null;
    private PrintWriter pw3; //log InvT_Regex    
    
    private Buffer[] buffer;
    private RedDePetri rdp;
    
    private InvTLog inv_t_log;
    private String[] inv_t;
    
    private int delay;
    private ControlOn[] control_on;
    
    private String LOG_DIR = "C:/Disco local/Carpetas/Facu/Facultad/4to Año - 2019/1° semestre/Programación Concurrente/Trabajos Prácticos/TP3_Aichino_Altamirano_Argayo_Bosack/Codigo_TP3" + "/log.txt";
	private String TINV_DIR = "C:/Disco local/Carpetas/Facu/Facultad/4to Año - 2019/1° semestre/Programación Concurrente/Trabajos Prácticos/TP3_Aichino_Altamirano_Argayo_Bosack/Codigo_TP3" + "/log_tinv.txt";
	private String TINV_REGEX_DIR = "C:/Disco local/Carpetas/Facu/Facultad/4to Año - 2019/1° semestre/Programación Concurrente/Trabajos Prácticos/TP3_Aichino_Altamirano_Argayo_Bosack/Codigo_TP3" + "/log_tinv_regex.txt";
	
	public Log(int delay, ControlOn control_on[], Buffer buffer[], RedDePetri rdp, InvTLog inv_t_log){
    	this.delay 		= delay;
        this.control_on = control_on;
    	this.buffer 	= buffer;
    	this.rdp 		= rdp;
    	this.inv_t_log  = inv_t_log;
        file = null;
        file2 = null;
        pw = null;   
    }


    public void run() {
    	
    	//Log General
    	
		try{
			file = new FileWriter(LOG_DIR);
			pw = new PrintWriter(file);
		} catch (Exception e){
			e.printStackTrace();
		}

		//Log Invarintes T
		
		try{
			file2 = new FileWriter(TINV_DIR);
			pw2 = new PrintWriter(file2);
		} catch (Exception e){
			e.printStackTrace();
		}
		
		//Log Invariantes T para Regex
		
		try{
			file3 = new FileWriter(TINV_REGEX_DIR);
			pw3 = new PrintWriter(file3);
		} catch (Exception e){
			e.printStackTrace();
		}
		try {
			while (!(isInterrupted())) {

	            pw.println("Elementos en buffer 1: " + buffer[0].getSize());
	            pw.println("Elementos en buffer 2: " + buffer[1].getSize());
	            
	            pw.println("Estado ControlOn1: " +  control_on[0].getEstadoOn() + " " + control_on[0].getEstadoActivo());
	            pw.println("Elementos procesados por ControlOn1: " + buffer[0].getProcesados());
	            
	            pw.println("Estado ControlOn2: " +  control_on[1].getEstadoOn() + " " + control_on[1].getEstadoActivo());
	            pw.println("Elementos procesados por ControlOn2: " + buffer[1].getProcesados());
	            
	            pw.println(rdp.imprimeInvP());
	            pw.println("");
	            
	            sleep(delay);
			}
        }catch (InterruptedException e) {
            System.out.println("Log INTERRUMPIDO");
            return;
        }

    }

    public void terminar(float tiempo , int arr_rate , int sv_rate1 , int sv_rate2){
        interrupt();
        pw.println("");
        pw.println("");
        pw.println("Elementos generados: " + (buffer[0].getIngresados() + buffer[1].getIngresados()));
        pw.println("Arrival Rate: " + arr_rate);
        pw.println("Service Rate CPU1: "+sv_rate1+" Service Rate CPU2: "+sv_rate2);
        pw.println("Elementos procesados por ControlOn1: " + buffer[0].getProcesados());
        pw.println("Elementos procesados por ControlOn2: " + buffer[1].getProcesados());
        pw.println("");
        pw.println("Resultado P Invariantes");
        pw.println(rdp.falloInvP());
        pw.println("");
        pw.println("Tiempo Final de ejecucion: " + tiempo);
        
        //Imprimo Invariantes T

        inv_t = inv_t_log.getInvTLog();
        pw2.println("Impresion de Log ControlOn Buffer 1");
        pw2.println("");
        pw2.println(inv_t[0]);
        pw2.println("");
        pw2.println("Impresion de Log ControlOn Buffer 2");
        pw2.println("");
        pw2.println(inv_t[1]);
        pw2.println("");
        pw2.println("Impresion de Log ControlActive Buffer 1");
        pw2.println("");
        pw2.println(inv_t[2]);
        pw2.println("");
        pw2.println("Impresion de Log ControlActive Buffer 2");
        pw2.println("");
        pw2.println(inv_t[3]);
        
        //Imprimo T-Ivariantes para analisis con Regex
        
        pw3.print(rdp.getInvTLog());
        
        try{
			if(null != file && null != file2)
				file.close();
				file2.close();
				file3.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    

}
