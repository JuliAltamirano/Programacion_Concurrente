
import fileinput
import re


def InvTCheck(inv_t_log):
	cadena = [inv_t_log.readline() , 0]

	while(True):
		
		cadena = re.subn('T2(.*?)((T0(.*?)((T11(.*?))|(T9(.*?)T5(.*?)))T13(.*?)T7((.*?)T3)?)|(T1(?=-)(.*?)((T12(.*?))|(T10(.*?)T6(.*?)))T14(.*?)T8((.*?)T4)?))', 
		'\g<1>\g<4>\g<7>\g<9>\g<10>\g<11>\g<13>\g<15>\g<18>\g<20>\g<21>\g<22>\g<24>', cadena[0].rstrip())

		if(cadena[1] == 0):
			break
	cadena = re.subn('-' , '' , cadena[0].rstrip())

	if(cadena[0] == ""):
		return("NO HAY FALLO DE T-INVARIANTES")
	else:
		return("FALLO EN T-INVARIANTES\n" + cadena)

inv_t_log = open("log_tinv_regex" , "r")

print('\nResultado de chequeo en T-Invariantes: ' + InvTCheck(inv_t_log) + '\n')