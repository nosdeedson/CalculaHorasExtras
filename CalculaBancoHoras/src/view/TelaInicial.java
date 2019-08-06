package view;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Controller.CalculaViagem;
import Controller.SomaHoras;
import helper.GerarRelatorioPDF;
import helper.Helper;
import model.Viagem;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.SwingConstants;
import java.awt.GridLayout;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JRadioButton;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.border.MatteBorder;

public class TelaInicial extends JFrame {

	private JPanel contentPane;

	protected JTextField txtDiasSemanaSaida[] = new JTextField[7];
	protected JTextField txtDiasSemanaChegada[] = new JTextField[7];
	protected JTextField txtDiasSemanaTotal[] = new JTextField[7];
	private JTextField txtHorasTrabalhadasSemana;
	private JTextField txtHorasTrabalharSemana;
	private JTextField txtBancoHorasSemana;

	private ButtonGroup diasSemanaRadioButton[] = new ButtonGroup[7];
	private ButtonGroup viagemGrupo[] = new ButtonGroup[7];

	private JRadioButton rdbtnFeriado[] = new JRadioButton[7];
	private JRadioButton rdbtnFeriadoMunicipal[] = new JRadioButton[7];
	private JRadioButton rdbtnViagem[] = new JRadioButton[7];

	private Viagem viagem[] = new Viagem[8];

	private int paraWhile = -1;
	private double horasTrabalhadasSemana;
	private double horasTrabalhadasMes;

	private double horasTrabalharSemana;
	private double horasTrabalharMes;

	private double horasBancoSemana;
	private double horasBancoMes;

	private String mes;
	private String motorista;

	private List<Viagem> listaViagensPDF = new ArrayList<Viagem>();

	private JButton clean[] = new JButton[7];
	private JTextField txtHorasTrabalharMes;
	private JTextField txtHorasTrabalhadasMes;
	private JTextField txtHorasBancoMes;

	private JLabel lbldiasSemana[] = new JLabel[7];

	private boolean limpaViagemLonga = false;
	// private boolean domingo = true;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaInicial frame = new TelaInicial();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public TelaInicial() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(10, 200, 1300, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		// motorista = JOptionPane.showInputDialog(null, " Digite o nome do motorista.
		// ");
		// mes = JOptionPane.showInputDialog(null, " Entre com o m�s a ser digitado. ");

		JLabel lblDigiteHoraEntrada = new JLabel("Selecione quando necess\u00E1rio");
		lblDigiteHoraEntrada.setHorizontalAlignment(SwingConstants.CENTER);
		lblDigiteHoraEntrada.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 15));
		lblDigiteHoraEntrada.setBounds(138, 40, 269, 14);
		contentPane.add(lblDigiteHoraEntrada);

		JPanel panelbotoes = new JPanel();
		panelbotoes.setBounds(10, 65, 1264, 196);
		contentPane.add(panelbotoes);
		panelbotoes.setLayout(new GridLayout(0, 8, 10, 10));

		// inicializa o vetores de atributos
		for (int i = 0; i < viagem.length; i++) {
			if (i < 7) {
				lbldiasSemana[i] = new JLabel();
				setaDiaSemana(i);
				lbldiasSemana[i].setBounds(10, 65, 80, 15);
				panelbotoes.add(lbldiasSemana[i]);
				lbldiasSemana[i].setHorizontalAlignment(SwingConstants.CENTER);
				lbldiasSemana[i].setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 14));

				rdbtnFeriado[i] = new JRadioButton("Feriado");
				rdbtnFeriado[i].setFont(new Font("Tahoma", Font.PLAIN, 13));
				rdbtnFeriado[i].setHorizontalAlignment(SwingConstants.LEFT);
				panelbotoes.add(rdbtnFeriado[i]);

				rdbtnFeriadoMunicipal[i] = new JRadioButton("Feriado Municipal/Ponto Facultativo");
				rdbtnFeriadoMunicipal[i].setFont(new Font("Tahoma", Font.PLAIN, 13));
				rdbtnFeriadoMunicipal[i].setToolTipText("Marque quando for feriado municipal ou ponto facultativo");
				rdbtnFeriado[i].setBounds(10, 70, 150, 20);
				panelbotoes.add(rdbtnFeriadoMunicipal[i]);
				rdbtnViagem[i] = new JRadioButton("Viagem");
				rdbtnViagem[i].setFont(new Font("Tahoma", Font.PLAIN, 13));
				rdbtnViagem[i].setToolTipText("Marque quando for viagem que n�o seja para Itajub�");
				rdbtnViagem[i].setHorizontalAlignment(SwingConstants.RIGHT);
				viagemGrupo[i] = new ButtonGroup();
				viagemGrupo[i].add(rdbtnViagem[i]);
				panelbotoes.add(rdbtnViagem[i]);

				txtDiasSemanaSaida[i] = new JTextField();
				txtDiasSemanaSaida[i].setHorizontalAlignment(SwingConstants.CENTER);
				txtDiasSemanaSaida[i].setBackground(new Color(128, 128, 128));
				panelbotoes.add(txtDiasSemanaSaida[i]);
				txtDiasSemanaSaida[i].setColumns(10);

				diasSemanaRadioButton[i] = new ButtonGroup();// aplicar aos outros rdbtn
				diasSemanaRadioButton[i].add(rdbtnFeriado[i]);
				diasSemanaRadioButton[i].add(rdbtnFeriadoMunicipal[i]);

				txtDiasSemanaChegada[i] = new JTextField();
				txtDiasSemanaChegada[i].setHorizontalAlignment(SwingConstants.CENTER);
				txtDiasSemanaChegada[i].setBackground(new Color(128, 128, 128));
				panelbotoes.add(txtDiasSemanaChegada[i]);
				txtDiasSemanaChegada[i].setColumns(10);

				txtDiasSemanaTotal[i] = new JTextField();
				txtDiasSemanaTotal[i].setHorizontalAlignment(SwingConstants.CENTER);
				panelbotoes.add(txtDiasSemanaTotal[i]);
				txtDiasSemanaTotal[i].setColumns(10);
				clean[i] = new JButton("Limpar");
				clean[i].setToolTipText("Limpa os dados desta linha para nova digita\u00E7\u00E3o.");
				panelbotoes.add(clean[i]);
			}
			viagem[i] = new Viagem();

		}

		JButton btnNovaSemana = new JButton("Nova Semana");
		btnNovaSemana.setToolTipText("Limpa todos os campos para nova digita\u00E7\u00E3o de outra semana.");
		btnNovaSemana.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 15));
		btnNovaSemana.setBounds(867, 295, 158, 23);
		contentPane.add(btnNovaSemana);

		JLabel lblHoraAcumudalos = new JLabel("Banco de Horas Semana");
		lblHoraAcumudalos.setHorizontalAlignment(SwingConstants.CENTER);
		lblHoraAcumudalos.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 15));
		lblHoraAcumudalos.setBounds(574, 299, 197, 14);
		contentPane.add(lblHoraAcumudalos);

		txtBancoHorasSemana = new JTextField();
		txtBancoHorasSemana.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 12));
		txtBancoHorasSemana.setHorizontalAlignment(SwingConstants.CENTER);
		txtBancoHorasSemana.setBounds(781, 297, 50, 20);
		contentPane.add(txtBancoHorasSemana);
		txtBancoHorasSemana.setColumns(10);

		txtHorasTrabalharSemana = new JTextField();
		txtHorasTrabalharSemana.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 12));
		txtHorasTrabalharSemana.setHorizontalAlignment(SwingConstants.CENTER);
		txtHorasTrabalharSemana.setBounds(225, 297, 50, 20);
		contentPane.add(txtHorasTrabalharSemana);
		txtHorasTrabalharSemana.setColumns(10);

		JLabel lblHorasTrabalhar = new JLabel("Horas Trabalhar Semana");
		lblHorasTrabalhar.setHorizontalAlignment(SwingConstants.CENTER);
		lblHorasTrabalhar.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 15));
		lblHorasTrabalhar.setBounds(10, 299, 205, 14);
		contentPane.add(lblHorasTrabalhar);

		JLabel lblHoraSada = new JLabel("Hora Sa\u00EDda");
		lblHoraSada.setHorizontalAlignment(SwingConstants.CENTER);
		lblHoraSada.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 15));
		lblHoraSada.setBounds(646, 40, 84, 14);
		contentPane.add(lblHoraSada);

		JLabel lblHoraChegada = new JLabel("Hora Chegada");
		lblHoraChegada.setHorizontalAlignment(SwingConstants.CENTER);
		lblHoraChegada.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 15));
		lblHoraChegada.setBounds(804, 40, 131, 14);
		contentPane.add(lblHoraChegada);

		JLabel lblTotalHorasDia = new JLabel("Total Horas Dia");
		lblTotalHorasDia.setHorizontalAlignment(SwingConstants.CENTER);
		lblTotalHorasDia.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 15));
		lblTotalHorasDia.setBounds(974, 40, 131, 14);
		contentPane.add(lblTotalHorasDia);

		JLabel lblTotalDeHoras = new JLabel("Horas trabalhadas Semana");
		lblTotalDeHoras.setHorizontalAlignment(SwingConstants.CENTER);
		lblTotalDeHoras.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 15));
		lblTotalDeHoras.setBounds(293, 299, 211, 14);
		contentPane.add(lblTotalDeHoras);

		txtHorasTrabalhadasSemana = new JTextField();
		txtHorasTrabalhadasSemana.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 12));
		txtHorasTrabalhadasSemana.setHorizontalAlignment(SwingConstants.CENTER);
		txtHorasTrabalhadasSemana.setBounds(514, 297, 50, 20);
		contentPane.add(txtHorasTrabalhadasSemana);
		txtHorasTrabalhadasSemana.setColumns(10);

		JLabel lblHorasTrabalharMes = new JLabel("Horas trabalhar m\u00EAs");
		lblHorasTrabalharMes.setHorizontalAlignment(SwingConstants.CENTER);
		lblHorasTrabalharMes.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 15));
		lblHorasTrabalharMes.setBounds(37, 337, 189, 14);
		contentPane.add(lblHorasTrabalharMes);

		txtHorasTrabalharMes = new JTextField();
		txtHorasTrabalharMes.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 12));
		txtHorasTrabalharMes.setHorizontalAlignment(SwingConstants.CENTER);
		txtHorasTrabalharMes.setBounds(225, 335, 50, 20);
		contentPane.add(txtHorasTrabalharMes);
		txtHorasTrabalharMes.setColumns(10);

		JLabel lblHorasTraalhadasMes = new JLabel("Horas trabalhadas m\u00EAs");
		lblHorasTraalhadasMes.setHorizontalAlignment(SwingConstants.CENTER);
		lblHorasTraalhadasMes.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 15));
		lblHorasTraalhadasMes.setBounds(315, 337, 189, 14);
		contentPane.add(lblHorasTraalhadasMes);

		txtHorasTrabalhadasMes = new JTextField();
		txtHorasTrabalhadasMes.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 12));
		txtHorasTrabalhadasMes.setHorizontalAlignment(SwingConstants.CENTER);
		txtHorasTrabalhadasMes.setBounds(514, 335, 50, 20);
		contentPane.add(txtHorasTrabalhadasMes);
		txtHorasTrabalhadasMes.setColumns(10);

		JLabel lblBancoDeHorasMes = new JLabel("Banco de horas m\u00EAs");
		lblBancoDeHorasMes.setHorizontalAlignment(SwingConstants.CENTER);
		lblBancoDeHorasMes.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 15));
		lblBancoDeHorasMes.setBounds(598, 337, 173, 14);
		contentPane.add(lblBancoDeHorasMes);

		txtHorasBancoMes = new JTextField();
		txtHorasBancoMes.setHorizontalAlignment(SwingConstants.CENTER);
		txtHorasBancoMes.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 12));
		txtHorasBancoMes.setBounds(781, 335, 50, 20);
		contentPane.add(txtHorasBancoMes);
		txtHorasBancoMes.setColumns(10);

		JButton btnNovoMes = new JButton("Novo M\u00EAs");
		btnNovoMes.setToolTipText("Zera valores para digita\u00E7\u00E3o de um novo m\u00EAs.");

		btnNovoMes.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 12));
		btnNovoMes.setBounds(867, 334, 158, 23);
		contentPane.add(btnNovoMes);

		JLabel lblDigiteOMotorista = new JLabel("Digite novo Motorista");
		lblDigiteOMotorista.setToolTipText("Digite o nome do motorista apenas uma vez.");
		lblDigiteOMotorista.setHorizontalAlignment(SwingConstants.CENTER);
		lblDigiteOMotorista.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 14));
		lblDigiteOMotorista.setBounds(1072, 295, 202, 23);
		contentPane.add(lblDigiteOMotorista);

		JButton btnNovoMotorista = new JButton("Novo Motorista");
		btnNovoMotorista.setToolTipText("Aperte quando for digitar horas de outro motorista.\r\n");
		btnNovoMotorista.setFont(new Font("Times New Roman", Font.BOLD, 15));
		btnNovoMotorista.setBounds(1086, 334, 166, 23);
		contentPane.add(btnNovoMotorista);

		// fun�oes dias

		// fun��es domingo

		txtDiasSemanaSaida[0].addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent e) {
				saidaGain(0);
				/*
				txtDiasSemanaSaida[0].setBackground(new Color(250, 250, 250));
				txtDiasSemanaSaida[0].setText("");
				mudaEnfaseRDBTNParaVemelho(0);
				paraWhile = 0;*/
			}

			@Override
			public void focusLost(FocusEvent e) {
				// TODO Auto-generated method stub
				viagem[0].setSaida(Helper.trataString(txtDiasSemanaSaida[0].getText()));
			}
		});

		txtDiasSemanaChegada[0].addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent e) {// resolver aqui
				chegadaGain(0);
				/*txtDiasSemanaChegada[0].setBackground(new Color(250, 250, 250));
				if (txtDiasSemanaSaida[6].getText().equals("")) {// aqui sabado sem digitar
					if (txtDiasSemanaSaida[0].getText().equals("")) {
						txtDiasSemanaSaida[0].setText("meia noite");
						txtDiasSemanaChegada[0].setBackground(new Color(250, 250, 250));
						viagem[0].setSaida(0);
					}
				} else if (!txtDiasSemanaSaida[6].getText().equals("") && txtDiasSemanaSaida[0].getText().equals("")) {// aqui																								// digitar
					txtDiasSemanaSaida[0].setText("meia noite");
					txtDiasSemanaChegada[0].setBackground(new Color(250, 250, 250));
					viagem[7].setSaida(0);
				} else if (!txtDiasSemanaSaida[0].getText().equals("") && !txtDiasSemanaSaida[6].getText().equals("")) {// aqui																									// domingo
					txtDiasSemanaSaida[0].setText("meia noite");
					txtDiasSemanaChegada[0].setBackground(new Color(250, 250, 250));
					viagem[7].setSaida(0);
					txtDiasSemanaChegada[0].setText("");
				}*/
			}

			@Override
			public void focusLost(FocusEvent e) {
				chegadaLost(0);
				//txtDiasSemanaSaida[1].requestFocus();
				/*if (txtDiasSemanaSaida[6].getText().equals("")
						|| txtDiasSemanaSaida[0].getText().equals("meia noite") && viagem[0].getChegada() == 0) {

					criaViagem(0);
					if (txtDiasSemanaSaida[0].getText().equals("meia noite")) {
						int i = 6;
						while (txtDiasSemanaChegada[i].getText().equals("") && i >= paraWhile) {
							criaViagem(i);
							if (i == 6) {
								limpatxtViagemMaisDeUmDia(0, lbldiasSemana[0], lbldiasSemana[6]);
							}
							i--;
						}
						paraWhile = -1;
					}
				} else if (!txtDiasSemanaSaida[6].getText().equals("") 
						&& !txtDiasSemanaSaida[0].getText().equals("")) {
					criaViagem(7);
				}
				
				if (limpaViagemLonga == false)
					mudaEnfaseCamposParaVerde(0);
				limpaViagemLonga = false;*/
			}
		});

		// segunda fun��es
		txtDiasSemanaSaida[1].addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent e) {
				// TODO Auto-generated method stub
				saidaGain(1);
				/*txtDiasSemanaSaida[1].setBackground(new Color(250, 250, 250));
				txtDiasSemanaSaida[1].setText("");
				mudaEnfaseRDBTNParaVemelho(1);
				paraWhile = 1;*/
			}

			@Override
			public void focusLost(FocusEvent e) {
				viagem[1].setSaida(Helper.trataString(txtDiasSemanaSaida[1].getText()));
			}
		});
		txtDiasSemanaChegada[1].addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent e) {
				chegadaGain(1);
				/*txtDiasSemanaChegada[1].setBackground(new Color(250, 250, 250));
				txtDiasSemanaChegada[1].setText("");
				if (txtDiasSemanaSaida[1].getText().equals("")) {
					txtDiasSemanaSaida[1].setText("meia noite");
					viagem[1].setSaida(0);
				}*/
			}

			@Override
			public void focusLost(FocusEvent e) {
				chegadaLost(1);
				/*criaViagem(1);
				if (txtDiasSemanaSaida[1].getText().equals("meia noite")) {
					int i = 0;
					while (txtDiasSemanaChegada[i].getText().equals("") && i >= paraWhile) {
						criaViagem(i);
						if (i == 0) {
							limpatxtViagemMaisDeUmDia(1, lbldiasSemana[1], lbldiasSemana[0]);
						}
						i--;
					}
					paraWhile = -1;
				}
				if (limpaViagemLonga == false)
					mudaEnfaseCamposParaVerde(1);
				limpaViagemLonga = false;*/
			}// fim metodo 2
		}); // fim addlistener
		// fun��es ter�a
		txtDiasSemanaSaida[2].addFocusListener(new FocusListener() {

			@Override
			public void focusGained(FocusEvent e) {
				saidaGain(2);
				/*txtDiasSemanaSaida[2].setBackground(new Color(250, 250, 250));
				txtDiasSemanaSaida[2].setText("");
				mudaEnfaseRDBTNParaVemelho(2);
				paraWhile = 2;*/
			}

			@Override
			public void focusLost(FocusEvent e) {
				viagem[2].setSaida(Helper.trataString(txtDiasSemanaSaida[2].getText()));
			}
		});
		txtDiasSemanaChegada[2].addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent e) {
				chegadaGain(2);
				/*txtDiasSemanaChegada[2].setBackground(new Color(250, 250, 250));
				txtDiasSemanaChegada[2].setText("");
				if (txtDiasSemanaSaida[2].getText().equals("")) {
					txtDiasSemanaSaida[2].setText("meia noite");
					viagem[2].setSaida(0);
				}*/
			}

			@Override
			public void focusLost(FocusEvent e) {
				// TODO Auto-generated method stub
				chegadaLost(2);
				/*criaViagem(2);
				if (txtDiasSemanaSaida[2].getText().equals("meia noite")) {
					int i = 1;
					while (txtDiasSemanaChegada[i].getText().equals("") && i >= paraWhile) {
						criaViagem(i);
						if (i == 1) {
							limpatxtViagemMaisDeUmDia(2, lbldiasSemana[2], lbldiasSemana[1]);
						}
						i--;
					}
					paraWhile = -1;
				}
				if (limpaViagemLonga == false)
					mudaEnfaseCamposParaVerde(2);
				limpaViagemLonga = false;*/
			}// fim segundo metodo

		});
		// fun��es quarta
		txtDiasSemanaSaida[3].addFocusListener(new FocusListener() {

			@Override
			public void focusGained(FocusEvent e) {
				saidaGain(3);
				/*txtDiasSemanaSaida[3].setBackground(new Color(250, 250, 250));
				txtDiasSemanaSaida[3].setText("");
				mudaEnfaseRDBTNParaVemelho(3);
				paraWhile = 3;*/
			}

			@Override
			public void focusLost(FocusEvent e) {
				viagem[3].setSaida(Helper.trataString(txtDiasSemanaSaida[3].getText()));
			}
		});
		txtDiasSemanaChegada[3].addFocusListener(new FocusListener() {

			@Override
			public void focusGained(FocusEvent e) {
				chegadaGain(3);
				/*txtDiasSemanaChegada[3].setBackground(new Color(250, 250, 250));
				txtDiasSemanaChegada[3].setText("");
				if (txtDiasSemanaSaida[3].getText().equals("")) {
					txtDiasSemanaSaida[3].setText("meia noite");
					viagem[3].setSaida(0);
				}*/
			}

			@Override
			public void focusLost(FocusEvent e) {
				// TODO Auto-generated method stub
				chegadaLost(3);
				/*criaViagem(3);
				if (txtDiasSemanaSaida[3].getText().equals("meia noite")) {
					int i = 2;
					while (txtDiasSemanaChegada[i].getText().equals("") && i >= paraWhile) {
						criaViagem(i);
						if (i == 2) {
							limpatxtViagemMaisDeUmDia(3, lbldiasSemana[3], lbldiasSemana[2]);
						}
						i--;
					}
					paraWhile = -1;
				}
				if (limpaViagemLonga == false)
					mudaEnfaseCamposParaVerde(3);
				limpaViagemLonga = false;*/
			}
		});
		// fun��es quinta
		txtDiasSemanaSaida[4].addFocusListener(new FocusListener() {

			@Override
			public void focusGained(FocusEvent e) {
				saidaGain(4);
				/*txtDiasSemanaSaida[4].setBackground(new Color(250, 250, 250));
				txtDiasSemanaSaida[4].setText("");
				mudaEnfaseRDBTNParaVemelho(4);
				paraWhile = 4;*/
			}

			@Override
			public void focusLost(FocusEvent e) {
				viagem[4].setSaida(Helper.trataString(txtDiasSemanaSaida[4].getText()));
			}
		});
		txtDiasSemanaChegada[4].addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent e) {
				chegadaGain(4);
				/*txtDiasSemanaChegada[4].setBackground(new Color(250, 250, 250));
				txtDiasSemanaChegada[4].setText("");
				if (txtDiasSemanaSaida[4].getText().equals("")) {
					txtDiasSemanaSaida[4].setText("meia noite");
					viagem[4].setSaida(0);
				}*/
			}

			@Override
			public void focusLost(FocusEvent e) {
				// TODO Auto-generated method stub
				chegadaLost(4);
				/*criaViagem(4);
				if (txtDiasSemanaSaida[4].getText().equals("meia noite")) {
					int i = 3;
					while (txtDiasSemanaChegada[i].getText().equals("") && i >= paraWhile) {
						criaViagem(i);
						if (i == 3) {
							limpatxtViagemMaisDeUmDia(4, lbldiasSemana[4], lbldiasSemana[3]);
						}
						i--;
					}
					paraWhile = -1;
				}
				if (limpaViagemLonga == false)
					mudaEnfaseCamposParaVerde(4);
				limpaViagemLonga = false;*/
			}
		});
		// fun�oes sexta
		txtDiasSemanaSaida[5].addFocusListener(new FocusListener() {

			@Override
			public void focusGained(FocusEvent e) {
				saidaGain(5);
				/*txtDiasSemanaSaida[5].setBackground(new Color(250, 250, 250));
				txtDiasSemanaSaida[5].setText("");
				mudaEnfaseRDBTNParaVemelho(5);
				paraWhile = 5;*/
			}

			@Override
			public void focusLost(FocusEvent e) {
				viagem[5].setSaida(Helper.trataString(txtDiasSemanaSaida[5].getText()));
			}
		});
		txtDiasSemanaChegada[5].addFocusListener(new FocusListener() {

			@Override
			public void focusGained(FocusEvent e) {
				chegadaGain(5);
				/*txtDiasSemanaChegada[5].setBackground(new Color(250, 250, 250));
				txtDiasSemanaChegada[5].setText("");
				if (txtDiasSemanaSaida[5].getText().equals("")) {
					txtDiasSemanaSaida[5].setText("meia noite");
					viagem[5].setSaida(0);
				}*/
			}

			@Override
			public void focusLost(FocusEvent e) {
				// TODO Auto-generated method stub
				chegadaLost(5);
				/*criaViagem(5);
				if (txtDiasSemanaSaida[5].getText().equals("meia noite")) {
					int i = 4;
					while (txtDiasSemanaChegada[i].getText().equals("") && i >= paraWhile) {
						criaViagem(i);
						if (i == 4) {
							limpatxtViagemMaisDeUmDia(5, lbldiasSemana[5], lbldiasSemana[4]);
						}
						i--;
					}
					paraWhile = -1;
				}
				if (limpaViagemLonga == false)
					mudaEnfaseCamposParaVerde(5);
				limpaViagemLonga = false;*/
			}
		});
		// fun��es sabado
		txtDiasSemanaSaida[6].addFocusListener(new FocusListener() {

			@Override
			public void focusGained(FocusEvent e) {
				saidaGain(6);
				/*txtDiasSemanaSaida[6].setBackground(new Color(250, 250, 250));
				txtDiasSemanaSaida[6].setText("");
				mudaEnfaseRDBTNParaVemelho(6);
				paraWhile = 6;*/
			}

			@Override
			public void focusLost(FocusEvent e) {
				viagem[6].setSaida(Helper.trataString(txtDiasSemanaSaida[6].getText()));
			}
		});
		txtDiasSemanaChegada[6].addFocusListener(new FocusListener() {

			@Override
			public void focusGained(FocusEvent e) {
				chegadaGain(6);
				/*txtDiasSemanaChegada[6].setBackground(new Color(250, 250, 250));
				txtDiasSemanaChegada[6].setText("");
				if (txtDiasSemanaSaida[6].getText().equals("")) {
					txtDiasSemanaSaida[6].setText("meia noite");
					viagem[6].setSaida(0);
				}*/
			}

			@Override
			public void focusLost(FocusEvent e) {
				// TODO Auto-generated method stub
				//chegadaLost(6);
				criaViagem(6);
				if (txtDiasSemanaSaida[6].getText().equals("meia noite")) {
					// System.out.println("sabado!!!!");
					int i = 5;
					while (txtDiasSemanaChegada[i].getText().equals("") && i >= paraWhile) {
						criaViagem(i);
						if (i == 5) {
							limpatxtViagemMaisDeUmDia(6, lbldiasSemana[6], lbldiasSemana[5]);
						}
						i--;
					}
					paraWhile = -1;
				}
				if (limpaViagemLonga == false)
					mudaEnfaseCamposParaVerde(6);
				limpaViagemLonga = false;
			}

		});

		// bot�es limpa linhas
		for (int i = 0; i < clean.length; i++) {
			int j = i;
			clean[i].addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					setHorasTrabalhadasMes(viagem[j].getHoraTrabalhadaDia(), false);
					setHorasTrabalhadasSemana(viagem[j].getHoraTrabalhadaDia(), false);
					// horas trabalhar
					setHorasTrabalharSemana(viagem[j].getHoraTrabalharDia(), false);
					setHorasTrabalharMes(viagem[j].getHoraTrabalharDia(), false);
					// horas banco
					setHorasBancoSemana(viagem[j].getDecrementaBanco(), false);
					setHorasBancoMes(viagem[j].getDecrementaBanco(), false);
					escreveTXT(j);
					resetaViagem(j);
					diasSemanaRadioButton[j].clearSelection();
					viagemGrupo[j].clearSelection();
					txtDiasSemanaChegada[j].setBackground(new Color(128, 128, 128));
					txtDiasSemanaSaida[j].setBackground(new Color(250, 250, 250));
					txtDiasSemanaSaida[j].requestFocus();
					txtDiasSemanaSaida[j + 1].setBackground(new Color(128, 128, 128));
				}
			});

		}
		// funcao
		btnNovaSemana.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				preencheListaPDF();

				if (txtDiasSemanaSaida[6].getText().equals("")) {
					mudaEnfaseCamposParaVerde(6);
				}
				for (int i = 0; i < viagem.length; i++) {
					resetaViagem(i);
					diasSemanaRadioButton[i].clearSelection();
					viagemGrupo[i].clearSelection();
					txtDiasSemanaChegada[i].setBackground(new Color(128, 128, 128));
					txtDiasSemanaSaida[i].setBackground(new Color(128, 128, 128));
				}
				setHorasTrabalharSemana(getHorasTrabalharSemana(), false);
				setHorasTrabalhadasSemana(getHorasTrabalhadasSemana(), false);
				setHorasBancoSemana(getHorasBancoSemana(), false);
				txtHorasTrabalharSemana.setText(Double.toString(getHorasTrabalharSemana()));
				txtHorasTrabalhadasSemana.setText(Double.toString(getHorasTrabalhadasSemana()));
				txtBancoHorasSemana.setText(Double.toString(getHorasBancoSemana()));
				txtDiasSemanaSaida[0].requestFocus();
			}
		});
		btnNovoMes.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				super.mousePressed(e);
				preencheListaPDF();
				if (txtDiasSemanaSaida[6].getText().equals("")) {
					mudaEnfaseCamposParaVerde(6);
				}
				for (int i = 0; i < viagem.length; i++) {
					resetaViagem(i);
					txtDiasSemanaChegada[i].setBackground(new Color(128, 128, 128));
					txtDiasSemanaSaida[i].setBackground(new Color(128, 128, 128));
				}
				setHorasTrabalharSemana(getHorasTrabalharSemana(), false);
				setHorasTrabalharMes(getHorasTrabalharMes(), false);
				setHorasTrabalhadasSemana(getHorasTrabalhadasSemana(), false);
				setHorasTrabalhadasMes(getHorasTrabalhadasMes(), false);
				setHorasBancoSemana(getHorasBancoSemana(), false);
				setHorasBancoMes(getHorasBancoMes(), false);
				txtBancoHorasSemana.setText("");
				txtHorasBancoMes.setText("");
				txtHorasTrabalhadasMes.setText("");
				txtHorasTrabalhadasSemana.setText("");
				txtHorasTrabalharMes.setText("");
				txtHorasTrabalharSemana.setText("");
				txtDiasSemanaSaida[0].requestFocus();
				GerarRelatorioPDF.gerarRelatorioMesPDF(listaViagensPDF, motorista, mes);
				listaViagensPDF = new ArrayList<Viagem>();
			}
		});
		btnNovoMotorista.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				super.mousePressed(e);
				preencheListaPDF();
				for (int i = 0; i < viagem.length; i++) {
					resetaViagem(i);
				}
				setHorasTrabalharSemana(getHorasTrabalharSemana(), false);
				setHorasTrabalharMes(getHorasTrabalharMes(), false);
				setHorasTrabalhadasSemana(getHorasTrabalhadasSemana(), false);
				setHorasTrabalhadasMes(getHorasTrabalhadasMes(), false);
				setHorasBancoSemana(getHorasBancoSemana(), false);
				setHorasBancoMes(getHorasBancoMes(), false);
				txtBancoHorasSemana.setText("");
				txtHorasBancoMes.setText("");
				txtHorasTrabalhadasMes.setText("");
				txtHorasTrabalhadasSemana.setText("");
				txtHorasTrabalharMes.setText("");
				txtHorasTrabalharSemana.setText("");
				motorista = JOptionPane.showInputDialog(null, " Digite novo Motorista.");
				mes = JOptionPane.showInputDialog(null, " Digite m�s.");

			}
		});

	}

	// fun��es
	public void criaViagem(int indice) {
		
		if (indice == 7) {
			System.out.println(" here, here");
			
			 viagem[indice].setChegada(Double.parseDouble(txtDiasSemanaChegada[0].getText()));
			 double horas = (viagem[indice].getSaida() + viagem[indice].getChegada() )* 2;
			 int i = 6;
			 while( i >= paraWhile) {
				 criaViagem(i);
				 if( i == 6) {
					 viagem[6].setHoraBancoDia(viagem[6].getHoraBancoDia() + horas);
					 viagem[6].setHoraTrabalhadaDia(viagem[6].getHoraTrabalhadaDia() + horas);
					 viagem[6].setDecrementaBanco(viagem[6].getDecrementaBanco() + horas);
					 viagem[6].setDecrementaHorasSemana(viagem[6].getDecrementaHorasSemana() + horas);
					 setHorasBancoSemana(horas, true);
					 setHorasBancoMes(horas, true);
					 setHorasTrabalhadasSemana(horas, true);
					 setHorasTrabalhadasMes(horas, true);
					 
					 txtDiasSemanaTotal[6].setText( Double.toString(viagem[6].getHoraTrabalhadaDia()));
					 txtHorasTrabalhadasSemana.setText(Double.toString(getHorasBancoSemana()));
					 txtHorasTrabalhadasMes.setText(Double.toString(getHorasTrabalhadasMes()));
					 txtBancoHorasSemana.setText(Double.toString(getHorasBancoSemana()));
					 txtHorasBancoMes.setText( Double.toString(getHorasBancoMes()));
					 txtDiasSemanaSaida[0].setText( Double.toString(viagem[6].getSaida()));
					 txtDiasSemanaChegada[0].setText(Double.toString(viagem[6].getChegada()));
					 JOptionPane.showMessageDialog(null, "As horas de domingo foram adinonadas as horas de s�bado");
					 txtDiasSemanaSaida[0].setText(Double.toString(viagem[0].getSaida()));
					 txtDiasSemanaChegada[0].setText(Double.toString(viagem[0].getChegada()));
				 }
				 System.out.println(viagem[indice].getSaida());
				 System.out.println(viagem[indice].getChegada());
				 System.out.println(horas);
			 }

		} else {
			verificaFeriadoViagem(indice);
			if (txtDiasSemanaChegada[indice].getText().equals("")) {
				viagem[indice].setChegada(24);
				txtDiasSemanaChegada[indice].setText("meia noite");
			} else
				viagem[indice].setChegada(Helper.trataString(txtDiasSemanaChegada[indice].getText()));
			CalculaViagem.calculaviagem(viagem[indice], indice, txtDiasSemanaSaida[indice]);

			txtDiasSemanaTotal[indice].setText(Double.toString(viagem[indice].getHoraTrabalhadaDia()));
			escreveVariaveisNaoViagem(indice);
			escreveTXT(indice);
		}
	}

	public void escreveTXT(int indice) {
		txtHorasTrabalhadasSemana.setText(Double.toString(getHorasTrabalhadasSemana()));
		txtHorasTrabalhadasMes.setText(Double.toString(getHorasTrabalhadasMes()));
		txtBancoHorasSemana.setText(Double.toString(getHorasBancoSemana()));
		txtHorasBancoMes.setText(Double.toString(getHorasBancoMes()));
		txtHorasTrabalharSemana.setText(Double.toString(getHorasTrabalharSemana()));
		txtHorasTrabalharMes.setText(Double.toString(getHorasTrabalharMes()));
		if (txtDiasSemanaSaida[indice].getText().equals(""))
			txtDiasSemanaSaida[indice].setText("0");
	}

	public void escreveVariaveisNaoViagem(int indice) {

		if (indice == 0 || indice == 6 || txtDiasSemanaSaida[indice].getText().equals("meia noite")
				|| viagem[indice].isFeriado() == true || viagem[indice].isFeriadoMunicipal() == true) {
			setHorasTrabalharSemana(0, true);
			setHorasTrabalharMes(0, true);
		} else {
			setHorasTrabalharSemana(8, true);
			setHorasTrabalharMes(8, true);
		}
		setHorasTrabalhadasSemana(viagem[indice].getHoraTrabalhadaDia(), true);
		setHorasTrabalhadasMes(viagem[indice].getHoraTrabalhadaDia(), true);

		setHorasBancoSemana(viagem[indice].getHoraBancoDia(), true);
		setHorasBancoMes(viagem[indice].getHoraBancoDia(), true);
	}

	public void limpatxtViagemMaisDeUmDia(int indice, JLabel diaAtual, JLabel diaAnterior) {

		if (indice != 0) {
			viagem[indice - 1].setHoraBancoDia(viagem[indice].getHoraBancoDia() + viagem[indice - 1].getHoraBancoDia());
			viagem[indice - 1].setDecrementaBanco(viagem[indice - 1].getHoraBancoDia());
			viagem[indice - 1].setDecrementaHorasSemana(
					viagem[indice].getDecrementaHorasSemana() + viagem[indice - 1].getDecrementaHorasSemana());
			viagem[indice - 1].setHoraTrabalhadaDia(
					viagem[indice].getHoraTrabalhadaDia() + viagem[indice - 1].getHoraTrabalhadaDia());
			txtDiasSemanaTotal[indice - 1].setText(Double.toString(viagem[indice - 1].getHoraTrabalhadaDia()));
			viagem[indice - 1].setHoraDiaPosterior(viagem[indice].getHoraTrabalhadaDia());

		} else {
			viagem[6].setHoraBancoDia(viagem[indice].getHoraBancoDia() + viagem[6].getHoraBancoDia());
			viagem[6].setDecrementaBanco(viagem[6].getHoraBancoDia());
			viagem[6].setDecrementaHorasSemana(
					viagem[indice].getDecrementaHorasSemana() + viagem[6].getDecrementaHorasSemana());
			viagem[6].setHoraTrabalhadaDia(viagem[indice].getHoraTrabalhadaDia() + viagem[6].getHoraTrabalhadaDia());
			txtDiasSemanaTotal[6].setText(Double.toString(viagem[6].getHoraTrabalhadaDia()));
			viagem[5].setHoraDiaPosterior(viagem[6].getHoraTrabalhadaDia());

		}
		txtHorasTrabalharSemana.setText(Double.toString(getHorasTrabalharSemana()));
		txtHorasTrabalharMes.setText(Double.toString(getHorasTrabalharMes()));
		txtHorasBancoMes.setText(Double.toString(getHorasBancoMes()));
		txtBancoHorasSemana.setText(Double.toString(getHorasBancoSemana()));
		resetaViagem(indice);
		JOptionPane.showMessageDialog(null, " As HORAS EXTRAS de " + diaAtual.getText() + " foram adicionado(s) as "
				+ "horas de " + diaAnterior.getText());
		limpaViagemLonga = true;
		mudaEnfaseParaCinza(indice);
	}

	public void mudaEnfaseParaCinza(int indice) {
		txtDiasSemanaChegada[indice].setBackground(new Color(128, 128, 128));
		txtDiasSemanaSaida[indice].requestFocus();
	}

	public void mudaEnfaseCamposParaVerde(int indice) {

		if (indice != 6)
			txtDiasSemanaSaida[indice + 1].requestFocus();
		//txtDiasSemanaChegada[indice].setBackground(new Color(0, 204, 0));
		//txtDiasSemanaSaida[indice].setBackground(new Color(0, 204, 0));
		mudaRDBTNParaPreto(indice);
	}

	public void mudaRDBTNParaPreto(int indice) {
		if (indice != 6)
			txtDiasSemanaSaida[indice + 1].requestFocus();
		lbldiasSemana[indice].setForeground(new Color(0, 0, 0));
		rdbtnFeriado[indice].setForeground(new Color(0, 0, 0));
		rdbtnFeriadoMunicipal[indice].setForeground(new Color(0, 0, 0));
		rdbtnViagem[indice].setForeground(new Color(0, 0, 0));
	}

	public void mudaEnfaseRDBTNParaVemelho(int indice) {
		lbldiasSemana[indice].setForeground(new Color(250, 0, 0));
		rdbtnFeriado[indice].setForeground(new Color(250, 0, 0));
		rdbtnFeriadoMunicipal[indice].setForeground(new Color(250, 0, 0));
		rdbtnViagem[indice].setForeground(new Color(250, 0, 0));
	}

	public void preencheListaPDF() {

		for (int i = 0; i < 7; i++) {
			Viagem v = new Viagem();
			v.setChegada(viagem[i].getChegada());
			v.setDecrementaBanco(viagem[i].getDecrementaBanco());
			v.setDecrementaHorasSemana(viagem[i].getDecrementaHorasSemana());
			v.setFeriado(viagem[i].isFeriado());
			v.setFeriado(viagem[i].isFeriadoMunicipal());
			v.setHoraBancoDia(viagem[i].getHoraBancoDia());
			v.setHoraTrabalhadaDia(viagem[i].getHoraTrabalhadaDia());
			v.setHoraTrabalharDia(viagem[i].getHoraTrabalharDia());
			v.setSaida(viagem[i].getSaida());
			v.setViagemLonga(viagem[i].isViagemLonga());
			v.setHoraDiaPosterior(viagem[i].getHoraDiaPosterior());
			listaViagensPDF.add(v);
		}

	}

	public void resetaViagem(int indice) {
		viagem[indice].setChegada(0);
		viagem[indice].setSaida(0);
		viagem[indice].setFeriado(false);
		viagem[indice].setFeriadoMunicipal(false);
		viagem[indice].setViagemLonga(false);

		viagem[indice].setDecrementaBanco(0);
		viagem[indice].setDecrementaHorasSemana(0);
		viagem[indice].setHoraBancoDia(0);
		viagem[indice].setHoraTrabalhadaDia(0);
		viagem[indice].setHoraTrabalharDia(0);
		txtDiasSemanaSaida[indice].setText("");
		txtDiasSemanaChegada[indice].setText("");
		txtDiasSemanaTotal[indice].setText("");
	}

	private void setaDiaSemana(int indice) {
		if (indice == 0)
			lbldiasSemana[indice].setText("Domingo");
		if (indice == 1)
			lbldiasSemana[indice].setText("Segunda");
		if (indice == 2)
			lbldiasSemana[indice].setText("Ter�a");
		if (indice == 3)
			lbldiasSemana[indice].setText("Quarta");
		if (indice == 4)
			lbldiasSemana[indice].setText("Quinta");
		if (indice == 5)
			lbldiasSemana[indice].setText("Sexta");
		if (indice == 6)
			lbldiasSemana[indice].setText("Sabado");
		if (indice == 7)
			lbldiasSemana[indice].setText("Domingo");
	}
	public void saidaGain(int indice) {
		txtDiasSemanaSaida[indice].setBackground(new Color(250, 250, 250));
		txtDiasSemanaSaida[indice].setText("");
		mudaEnfaseRDBTNParaVemelho(indice);
		paraWhile = 0;
	}
	public void chegadaGain(int indice) {
		txtDiasSemanaChegada[indice].setBackground(new Color(250, 250, 250));
		if (txtDiasSemanaSaida[6].getText().equals("")) {// aqui sabado sem digitar
			if (txtDiasSemanaSaida[indice].getText().equals("")) {
				txtDiasSemanaSaida[indice].setText("meia noite");
				txtDiasSemanaChegada[indice].setBackground(new Color(250, 250, 250));
				viagem[0].setSaida(indice);
			}
		} else if (!txtDiasSemanaSaida[6].getText().equals("") && txtDiasSemanaSaida[indice].getText().equals("")) {// aqui																								// digitar
			txtDiasSemanaSaida[indice].setText("meia noite");
			txtDiasSemanaChegada[indice].setBackground(new Color(250, 250, 250));
			viagem[7].setSaida(indice);
		} else if (!txtDiasSemanaSaida[indice].getText().equals("") && !txtDiasSemanaSaida[6].getText().equals("")) {// aqui																									// domingo
			txtDiasSemanaSaida[indice].setText("meia noite");
			txtDiasSemanaChegada[0].setBackground(new Color(250, 250, 250));
			viagem[7].setSaida(0);
			txtDiasSemanaChegada[indice].setText("");
		}
	}
	public void chegadaLost(int indice) {
		if (txtDiasSemanaSaida[6].getText().equals("")
				|| txtDiasSemanaSaida[0].getText().equals("meia noite") && viagem[indice].getChegada() == 0) {

			criaViagem(indice);
			if (txtDiasSemanaSaida[indice].getText().equals("meia noite")) {
				int i = 6;
				while (txtDiasSemanaChegada[i].getText().equals("") && i >= paraWhile) {
					criaViagem(i);
					if (i == 6) {
						limpatxtViagemMaisDeUmDia(indice, lbldiasSemana[indice], lbldiasSemana[indice]);
					}
					i--;
				}
				paraWhile = -1;
			}
		} else if (!txtDiasSemanaSaida[6].getText().equals("") 
				&& !txtDiasSemanaSaida[indice].getText().equals("")) {
			criaViagem(7);
		}
		
		/*if (limpaViagemLonga == false)
			mudaEnfaseCamposParaVerde(indice);
		limpaViagemLonga = false;*/
		txtDiasSemanaSaida[indice + 1].requestFocus();
	}
	public void verificaFeriadoViagem(int indice) {
		if (rdbtnFeriado[indice].isSelected() == true) {
			viagem[indice].setFeriado(true);
		} else {
			viagem[indice].setFeriado(false);
		}
		if (rdbtnFeriadoMunicipal[indice].isSelected() == true) {
			viagem[indice].setFeriadoMunicipal(true);
		} else {
			viagem[indice].setFeriadoMunicipal(false);
		}
		if (rdbtnViagem[indice].isSelected() == true) {
			viagem[indice].setViagemLonga(true);
		} else {
			viagem[indice].setViagemLonga(false);
		}
	}

	// getters and setters
	public double getHorasTrabalhadasSemana() {
		return horasTrabalhadasSemana;
	}

	public void setHorasTrabalhadasSemana(double horasTrabalhadasSemana, boolean operacao) {
		if (operacao)
			this.horasTrabalhadasSemana += horasTrabalhadasSemana;
		else
			this.horasTrabalhadasSemana -= horasTrabalhadasSemana;
	}

	public double getHorasBancoSemana() {
		return horasBancoSemana;
	}

	public void setHorasBancoSemana(double horasBancoSemana, boolean operacao) {
		if (operacao)
			this.horasBancoSemana += horasBancoSemana;
		else
			this.horasBancoSemana -= horasBancoSemana;
	}

	public double getHorasTrabalhadasMes() {
		return horasTrabalhadasMes;
	}

	public void setHorasTrabalhadasMes(double horasTrabalhadasMes, boolean operacao) { // true soma
		if (operacao)
			this.horasTrabalhadasMes += horasTrabalhadasMes;
		else
			this.horasTrabalhadasMes -= horasTrabalhadasMes;
	}

	public double getHorasBancoMes() {
		return horasBancoMes;
	}

	public void setHorasBancoMes(double horasBancoMes, boolean operacao) {
		if (operacao)
			this.horasBancoMes += horasBancoMes;
		else
			this.horasBancoMes -= horasBancoMes;
	}

	public double getHorasTrabalharSemana() {
		return horasTrabalharSemana;
	}

	public void setHorasTrabalharSemana(double horasTrabalharSemana, boolean operacao) {
		if (operacao)
			this.horasTrabalharSemana += horasTrabalharSemana;
		else
			this.horasTrabalharSemana -= horasTrabalharSemana;
	}

	public double getHorasTrabalharMes() {
		return horasTrabalharMes;
	}

	public void setHorasTrabalharMes(double horasTrabalharMes, boolean operacao) {
		if (operacao)
			this.horasTrabalharMes += horasTrabalharMes;
		else
			this.horasTrabalharMes -= horasTrabalharMes;
	}

	public boolean isLimpaViagemLonga() {
		return limpaViagemLonga;
	}

	public void setLimpaViagemLonga(boolean limpaViagemLonga) {
		this.limpaViagemLonga = limpaViagemLonga;
	}
}// fim classe
