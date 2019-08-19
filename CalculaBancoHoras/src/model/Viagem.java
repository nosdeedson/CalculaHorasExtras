package model;

public class Viagem {
	private double saida ,chegada, horaDiaPosterior; // atribuir valor na view 
	private double horaBancoDia, horaTrabalharDia, horaTrabalhadaDia,
	decrementaBanco, decrementaHorasSemana; // atribuir valor na controller
	
	private boolean viagemLonga = false, feriado = false, feriadoMunicipal= false; // atribuir valor na view  
	private boolean inicioMes;
	public Viagem(double saida, double chegada, double horaDiaPosterior, double horaBancoDia, double horaTrabalharDia,
			double horaTrabalhadaDia, double decrementaBanco, double decrementaHorasSemana, boolean viagemLonga,
			boolean feriado, boolean feriadoMunicipal) {
		super();
		this.saida = saida;
		this.chegada = chegada;
		this.horaDiaPosterior = horaDiaPosterior;
		this.horaBancoDia = horaBancoDia;
		this.horaTrabalharDia = horaTrabalharDia;
		this.horaTrabalhadaDia = horaTrabalhadaDia;
		this.decrementaBanco = decrementaBanco;
		this.decrementaHorasSemana = decrementaHorasSemana;
		this.viagemLonga = viagemLonga;
		this.feriado = feriado;
		this.feriadoMunicipal = feriadoMunicipal;
	}

	public Viagem() {
		super();
	}

	public double getSaida() {
		return saida;
	}

	public void setSaida(double saida) {
		this.saida = saida;
	}

	public double getChegada() {
		return chegada;
	}

	public void setChegada(double chegada) {
		this.chegada = chegada;
	}

	public double getHoraDiaPosterior() {
		return horaDiaPosterior;
	}

	public void setHoraDiaPosterior(double horaDiaPosterior) {
		this.horaDiaPosterior = horaDiaPosterior;
	}

	public double getHoraBancoDia() {
		return horaBancoDia;
	}

	public void setHoraBancoDia(double horaBancoDia) {
		this.horaBancoDia = horaBancoDia;
	}

	public double getHoraTrabalharDia() {
		return horaTrabalharDia;
	}

	public void setHoraTrabalharDia(double horaTrabalharDia) {
		this.horaTrabalharDia = horaTrabalharDia;
	}

	public double getHoraTrabalhadaDia() {
		return horaTrabalhadaDia;
	}

	public void setHoraTrabalhadaDia(double horaTrabalhadaDia) {
		this.horaTrabalhadaDia = horaTrabalhadaDia;
	}

	public double getDecrementaBanco() {
		return decrementaBanco;
	}

	public void setDecrementaBanco(double decrementaBanco) {
		this.decrementaBanco = decrementaBanco;
	}

	public double getDecrementaHorasSemana() {
		return decrementaHorasSemana;
	}

	public void setDecrementaHorasSemana(double decrementaHorasSemana) {
		this.decrementaHorasSemana = decrementaHorasSemana;
	}

	public boolean isViagemLonga() {
		return viagemLonga;
	}

	public void setViagemLonga(boolean viagemLonga) {
		this.viagemLonga = viagemLonga;
	}

	public boolean isFeriado() {
		return feriado;
	}

	public void setFeriado(boolean feriado) {
		this.feriado = feriado;
	}

	public boolean isFeriadoMunicipal() {
		return feriadoMunicipal;
	}

	public void setFeriadoMunicipal(boolean feriadoMunicipal) {
		this.feriadoMunicipal = feriadoMunicipal;
	}

	public boolean isInicioMes() {
		return inicioMes;
	}

	public void setInicioMes(boolean inicioMes) {
		this.inicioMes = inicioMes;
	}

	

	
}// fim classe
