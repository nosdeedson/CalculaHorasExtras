package Controller;

import javax.swing.JRadioButton;

public abstract class SomaHoras {

	public static  double somaHoraDomingo( double saida, double chegada, boolean viagem) { 
				
		double soma = chegada - saida;

		if(viagem) {
			//System.out.println("aqui viagem domingo ");
			if( soma > 12.5 ) {
				soma -= 2;
			}
			else if( saida >= 11 && soma <= 12.5) {
				soma -= 1;
			}
			else if( saida < 11 && soma <= 12.5 && soma >= 8 ) {
				soma -= 1;
			}
		}
		else {
			//System.out.println("aqui nao viagem domingo ");
			if( soma > 12.5 ) {
				soma -= 3;
			}
			else if( saida >= 11 && soma <= 12.5) {
				soma -= 1.5;
			}
			else if( saida < 11 && soma <= 12.5 && soma >= 8 ) {
				soma -= 1.5;
			}
		}
		return soma * 2;

	}
	
	public static  double somaHoraSabado( double saida, double chegada, boolean viagem) { 
		// multiplica a hora por 1.5 desconta 2 horas de almoço se o total das horas for maior que 12 e a hora de saida for maior que 11
		
		double soma = chegada - saida;

		if(viagem) {
			if( soma > 12.5 ) {
				soma -= 2;
			}
			else if( saida >= 11 && soma <= 12.5) {
				soma -= 1;
			}
			else if( saida < 11 && soma <= 12.5 && soma >= 8 ) {
				soma -= 1;
			}
		}
		else {
			if( soma > 12.5 ) {
				soma -= 3;
			}
			else if( saida >= 11 && soma <= 12.5) {
				soma -= 1.5;
			}
			else if( saida < 11 && soma <= 12.5 && soma >= 8 ) {
				soma -= 1.5;
			}
		}
		return soma * 1.5;
	}
	
	public static  double somaHoraDiaSemana( double saida, double chegada, boolean viagem) { 
		// multiplica a hora or dois desconta 2 horas de almoço se o total das horas for maior que 12 e a hora de saida for maior que 11
		//System.out.println("dia semana");
		double soma = chegada - saida;

		if(viagem) {
			
			if( soma > 12.5 ) {
				soma -= 2;
			}
			else if( saida >= 11 && soma <= 12.5) {
				soma -= 1;
			}
			else if( saida < 11 && soma <= 12.5 && soma >= 8) {
				soma -= 1;
			}
		}
		else {
			//System.out.println("aqui nao viagem dia semana ");
			if( soma > 12.5 ) {
				soma -= 3;
			}
			else if( saida >= 11 && soma <= 12.5) {
				soma -= 1.5;
			}
			else if( saida < 11 && soma <= 12.5 && soma >= 8 ) {
				soma -= 1.5;
			}
		}
		
		return soma;
	}
	
	
}//fim classe 
