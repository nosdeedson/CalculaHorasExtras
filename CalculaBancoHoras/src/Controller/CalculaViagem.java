package Controller;

import javax.swing.JTextField;

import model.Viagem;

public class CalculaViagem {
	public static Viagem calculaviagem( Viagem viagem, int indice, JTextField txtSaida) {
		
		if(indice == 0 || indice == 6 || indice == 7) {
			double saida = viagem.getSaida();
			double chegada = viagem.getChegada();
			boolean flag = viagem.isViagemLonga();
			double hora = -1;
			if( indice == 0 || indice == 7)
				hora = SomaHoras.somaHoraDomingo(saida, chegada, flag);
			else
				hora = SomaHoras.somaHoraSabado(saida, chegada, flag);
	
			viagem.setHoraTrabalhadaDia(hora);
			viagem.setHoraTrabalharDia(0);
			viagem.setHoraBancoDia(viagem.getHoraTrabalhadaDia());
			viagem.setDecrementaBanco(hora);
			viagem.setDecrementaHorasSemana(hora);
		}
		else {
			double saida = viagem.getSaida();
			double chegada = viagem.getChegada();
			boolean flag = viagem.isViagemLonga();
			double hora;
			if( viagem.isFeriado() == true)
				hora = SomaHoras.somaHoraDomingo(saida, chegada, flag);
			else if( viagem.isFeriadoMunicipal() == true)
				hora = SomaHoras.somaHoraSabado(saida, chegada, flag);
			else
				hora = SomaHoras.somaHoraDiaSemana(saida, chegada, flag);
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
