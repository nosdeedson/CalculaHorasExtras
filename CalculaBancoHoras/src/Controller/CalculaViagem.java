package Controller;

import javax.swing.JTextField;

import model.Viagem;

public class CalculaViagem {
	public static Viagem calculaviagem( Viagem viagem, int indice, JTextField txtSaida) {
		
		if(indice == 0 || indice == 6 || indice == 7) {
			double hora = -1;
			if( indice == 0 || indice == 7)
				hora = SomaHoras.somaHoraDomingo(viagem);
			else
				hora = SomaHoras.somaHoraSabado(viagem);
	
			viagem.setHoraTrabalhadaDia(hora);
			viagem.setHoraTrabalharDia(0);
			viagem.setHoraBancoDia(viagem.getHoraTrabalhadaDia());
			viagem.setDecrementaBanco(hora);
			viagem.setDecrementaHorasSemana(hora);
		}
		else {
			double hora;
			if( viagem.isFeriado() == true)
				hora = SomaHoras.somaHoraDomingo(viagem);
			else if( viagem.isFeriadoMunicipal() == true)
				hora = SomaHoras.somaHoraSabado(viagem);
			else
				hora = SomaHoras.somaHoraDiaSemana(viagem);
			viagem.setHoraTrabalhadaDia(hora);
			
			if(txtSaida.getText().equals("meia noite") || viagem.isFeriadoMunicipal()== true || 
					viagem.isFeriado() == true) {
				viagem.setHoraTrabalharDia(0);
				//System.out.println(" if aqui feriado municipal" +viagem.isFeriadoMunicipal() +
						//"trabalhar " + viagem.getHoraTrabalharDia());
			}
			else {
				viagem.setHoraTrabalharDia(8);
				//System.out.println(" else aqui feriado municipalfff" );
			}
			viagem.setHoraBancoDia(viagem.getHoraTrabalhadaDia()- viagem.getHoraTrabalharDia());
			viagem.setDecrementaBanco(viagem.getHoraBancoDia());
			viagem.setDecrementaHorasSemana(hora);
		}
		
		return viagem;
	}
}// fim classe
