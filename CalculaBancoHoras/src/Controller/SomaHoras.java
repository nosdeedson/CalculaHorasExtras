package Controller;

import javax.swing.JRadioButton;

import model.Viagem;

public abstract class SomaHoras {
	
	public static  double somaHoraDomingo( Viagem v) { 
		// calcula domingo ou feriado	
		// multiplica a hora por dois, desconta 2 horas de janta se o total das horas trabalhadas for maior que 12
		// e chegada for depois das 20 horas
		double soma = 0.0;
		soma  = verificaTamanhoViagem( v, soma);
		return soma * 2;
	}
	
	public static  double somaHoraSabado( Viagem v) { 
		// multiplica a hora por 1.5 desconta, desconta 2 horas de almo�o se o total das horas for maior que 12
		//e a chegada depois das 20 horas
		// calcula sabado e feriado municipal
		double soma = 0.0;
		soma = verificaTamanhoViagem(v, soma);
		return soma * 1.5;
	}
	
	public static  double somaHoraDiaSemana( Viagem v) { 
		// calcula dia de semana normal
		// desconta janta se o total das horas for maior que 12 e chegada for depois das 20 horas
		double soma = 0.0;
		soma = verificaTamanhoViagem( v, soma);
		return soma;
	}
	
	private static double verificaTamanhoViagem( Viagem v, double soma) {
		soma = v.getChegada() - v.getSaida();
		if(v.isViagemLonga()) {
			if(v.getSaida() <= 10.5) {
				if(soma > 12.5 && v.getChegada() >= 20.5) {
					soma -= 2;
				}
				else if( v.getSaida() == 0 && v.getChegada() <= 11.5) {// saida maior que 10,5
					return soma;
				}
				else if( soma <= 14 && v.getChegada() <= 22.9) {
					soma -= 1;
				}
				else {
					soma -= 1;	
				}
			}
			else {
				soma -= -1;
			}
		}
		else {
			if(v.getSaida() <= 10.5) {
				if(soma > 12.5 && v.getChegada() >= 20.5) {
					soma -= 3;
				}
				else if( v.getSaida() == 0 && v.getChegada() <= 11.5) {
					return soma;
				}
				else if( soma <= 14 && v.getChegada() <= 22.9) {
					soma -= 1.5;
				}
				else {
					soma -= 1.5;	
				}
			}
			else {// saida maior que 10,5	
				if( v.getSaida() == 0 && v.getChegada() <= 11.5)
					return soma;
				else
					soma -= 1.5;
			}
		}
		return soma;
	}
	
}//fim classe 
