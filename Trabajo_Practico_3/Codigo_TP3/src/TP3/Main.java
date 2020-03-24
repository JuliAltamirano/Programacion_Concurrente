package TP3;

public class Main {

	private static int NUM_PLAZAS = 16;
	private static int NUM_TRANS = 15;

	private static int INVP_NUM = 5;
	private static int INVP_ELEM = 5;

	private static int NUM_DATOS = 1000;
	private static int ARR_RATE = 20;
	private static int SV_RATE1 = 20;
	private static int SV_RATE2 = 20;
	private static int LOG_DELAY = 100;
	
	private static String ROOT = "C:/Users/altam/Documents/Universidad/4º año/Primer Semestre/Programación Concurrente/Trabajos Prácticos/Programacion_Concurrente-master/Trabajo_Practico_3/Codigo_TP3";
	private static String MARK_DIR = "/marcado.xls";
	private static String W_DIR = "/matrizW.xls";
	private static String B_DIR ="/matrizB.xls";
	private static String INVP_DIR ="/invariantesP.xls";
	
	public static void main(String[] args) {

		////VARIABLES PARA CALCULO DE TIEMPO///////
		float t_final;
		long t_total;
		
		long t_inicio =  System.currentTimeMillis();

		long hilos_procesador1_tiempo[] = new long [2]; //ControlOn en 0 y ControlActive en 1		(buffer 1)
		long hilos_procesador2_tiempo[] = new long [2]; //ControlOn en 0 y ControlActive en 1		(buffer 2)

		//Transiciones
		//0/arrv_dta1 /1/arrv_dta2 /2/Arrvl_rt  /3/Pw_up_Dl1 /4/Pw_up_Dl2 /5/Pwr_dwn_th1 /6/Pwr_dwn_th2
		//7/st_ch1  /8/st_ch2  /9/stand_by_dl1  /10/stand_by_dl2  /11/stay_on1  /12/stay_on2  /13/svc_rt1
		//14/svc_rt2

		//Plazas
		//0/Active1  /1/Active2 /2/CPU2_Buffer  /3/CPU_ON1  /4/CPU_ON2  /5/CPU1_Buffer  /6/dato_in1
		// 7/dato_in2  /8/Idle1 /9/Idle2  /10/P0  /11/P1  /12/Power_on2  /13/Power_up /14/Stand_by1
		//15/ stand_by2
		
		ImportarDatos imp_datos = new ImportarDatos();
		//*******************************************MODIFICAR DE DOS MATRICES A MATRIZ W *************************
		RedDePetri rdp = new RedDePetri(
				imp_datos.creaVector(MARK_DIR,ROOT, NUM_PLAZAS),
				imp_datos.creaMatriz(B_DIR, ROOT, NUM_TRANS, NUM_PLAZAS),
				imp_datos.creaMatriz(W_DIR, ROOT, NUM_TRANS, NUM_PLAZAS),
				imp_datos.creaMatriz(INVP_DIR, ROOT, INVP_ELEM, INVP_NUM),
				NUM_TRANS, NUM_PLAZAS, ARR_RATE, SV_RATE1, SV_RATE2 );


		Buffer buffer[]= new Buffer[2];
		buffer[0] = new Buffer(1);
		buffer[1] = new Buffer(2);

		Monitor monitor = new Monitor(rdp);

		///////CREO HILOS//////////////////////////
		System.out.println("Main esta creando Hilos");

		Thread.currentThread().setName("MAIN");
		
		InvTLog inv_t_log = new InvTLog();

		ControlOn control_on[] = new ControlOn[2];
		control_on[0] = new ControlOn(buffer[0], monitor, rdp, hilos_procesador1_tiempo, SV_RATE1, inv_t_log);
		control_on[1] = new ControlOn(buffer[1] , monitor, rdp, hilos_procesador2_tiempo, SV_RATE2, inv_t_log);

		ControlActive control_active[] = new ControlActive[2];
		control_active[0] = new ControlActive(control_on[0], monitor, buffer[0], hilos_procesador1_tiempo, inv_t_log);
		control_active[1] = new ControlActive(control_on[1], monitor, buffer[1], hilos_procesador2_tiempo, inv_t_log);

		Politica politica = new Politica(buffer);
		
		GeneradorDeDatos generador= new GeneradorDeDatos(monitor, NUM_DATOS, ARR_RATE, rdp); //hilo generador de datos
		
		ArrivalDato arr_dato[]= new ArrivalDato[2];
		arr_dato[0]= new ArrivalDato(politica, monitor, buffer[0], rdp); //hilo de arrivo de datos a buffer1
		arr_dato[1]= new ArrivalDato(politica, monitor, buffer[1], rdp);	//hilo de arrivo de datos a buffer 2

		Log log = new Log(LOG_DELAY, control_on, buffer, rdp, inv_t_log);
		
		////////////INICIO HILOS////////////////////////
		System.out.println("Main inicia los hilos");
	
		IniciarHilos(control_active, control_on, log, generador, arr_dato);
	
		try {
			generador.join();
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}		
		System.out.println("Se terminan de generar los datos");
	
		///ESPERO QUE TERMINE DE PROCESAR////////////////
		
		while ( (buffer[0].getProcesados() + buffer[1].getProcesados()) < NUM_DATOS ) {
			System.out.print("");
		};
		
		System.out.println("Datos procesados: " + (buffer[0].getProcesados() + buffer[1].getProcesados()) );
		System.out.println("Datos procesados por buffer 1: "+buffer[0].getProcesados());
		System.out.println("Datos procesados por buffer 2: "+buffer[1].getProcesados());

		InterrumpirHilos (control_active, control_on, arr_dato);
		
		////CALCULO TIEMPO FINAL//////////////////////
		t_total = System.currentTimeMillis() - t_inicio;
		t_final = t_total/ 1000F;
		log.terminar(t_final, ARR_RATE , SV_RATE1 , SV_RATE2);

		try{
			log.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		System.out.println("Main finaliza su ejecucion");
	}
	
	private static void IniciarHilos(ControlActive[] control_active, ControlOn[] control_on, Log log, GeneradorDeDatos generador, ArrivalDato[] arr_dato) {

		for(int i = 0 ; i < 2 ; i++){
			control_active[i].setName("CA" + (i+1));
			control_active[i].setPriority(10);
			control_active[i].start();
			control_on[i].setName("CO" + (i +1));
			control_on[i].start();
			arr_dato[i].start();
			
		}
		generador.start();
		log.start();
	}

	private static void InterrumpirHilos(ControlActive[] control_active, ControlOn[] control_on, ArrivalDato[] arr_dato) {

		control_active[0].interrupt();
		control_on[0].interrupt();

		control_active[1].interrupt();
		control_on[1].interrupt();
		
		arr_dato[0].interrupt();
		arr_dato[1].interrupt();
		
		System.out.println("Main finalizando Hilos");		
	}
}
