package TP3;

public class InvTLog {
    private static String[] log_inv_t = {"","","",""};

    
    public InvTLog() {
    	log_inv_t = new String[30000];
    }
    
    public void logInvT(int transicion, String fuente, int buffer, String pto_aparte) {
    	if(fuente=="on") {
    		if(buffer==1) {
    			log_inv_t[0] += "T"+transicion+" - "+pto_aparte;
    		}else {
    			log_inv_t[1] += "T"+transicion+" - "+pto_aparte;
    		}
    	}
    	else {
    		if(buffer==2) {
    			log_inv_t[2] += "T"+transicion+" - "+pto_aparte;
    		}else {
    			log_inv_t[3] += "T"+transicion+" - "+pto_aparte;
    		}
    	}
    }
    
    public String[] getInvTLog() {
    	return log_inv_t;
    }
}
