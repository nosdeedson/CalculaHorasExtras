package helper;

import javax.swing.JRadioButton;
import javax.swing.JTextField;

import view.TelaInicial;

public abstract class Helper {
	
	public static double trataString( String valor) {
		double retorno = 0;
		boolean flag = false;
		String aux="";
		for(int i = 0; i< valor.length();i++) {
			if(valor.charAt(i) == '3' && flag)
				aux += '5';
			else if(valor.charAt(i) == ',' ) {
				aux += '.';
				flag = true;
			}
			else
			aux += valor.charAt(i);
		}
		retorno = Double.parseDouble(aux);
		return retorno;
	}

}
