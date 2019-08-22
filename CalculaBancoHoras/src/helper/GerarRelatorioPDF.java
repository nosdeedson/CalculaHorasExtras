package helper;

import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import javax.swing.JOptionPane;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.sun.javafx.css.CalculatedValue;

import Controller.VerificaMes;
import model.Viagem;

public class GerarRelatorioPDF {
	
	public static void gerarRelatorioMesPDF( List<Viagem> listaViagemMes, String motorista, String mes) {
		
		Document doc = new Document();// achar o primeiro dia do mes
		JOptionPane.showMessageDialog(null, " Os PDFs gerados tem que ser salvos. \n Senão serão perdidos. ");
		String arquivoPDF = "relatorio_motorista.pdf";
		
		try {
			PdfWriter.getInstance(doc, new FileOutputStream(arquivoPDF));
			doc.open();
			Paragraph p = new Paragraph(" Relatório referente ao mês: " + mes);
			p.setAlignment(1);
			doc.add(p);
			p = new Paragraph(" ");
			doc.add(p);
			p = new Paragraph("Motorista: "  + motorista);
			p.setAlignment(1);
			doc.add(p);
			p = new Paragraph(" ");
			doc.add(p);
			
			PdfPTable table = new PdfPTable(9);
			table.setTotalWidth(7.0f);
			float tams[] = {0.60f, 0.60f, 0.80f, 0.45f, 0.55f,0.60f,0.66f,0.50f,0.66f};
			table.setWidths(tams);
			Font font = FontFactory.getFont(FontFactory.TIMES_ROMAN, 10);
			
			PdfPCell cell[] = new PdfPCell[9];
			cell[0] = new PdfPCell(new Phrase("Dia", font));
			cell[1] = new PdfPCell(new Phrase("Data", font));
			cell[2] = new PdfPCell(new Phrase("Feriado", font));
			cell[3] = new PdfPCell( new Phrase("Saída", font));
			cell[4]= new PdfPCell( new Phrase("Chegada", font));
			cell[5] = new PdfPCell( new Phrase("Trabalhar", font));
			cell[6] = new PdfPCell( new Phrase("Trabalhada", font));
			cell[7] = new PdfPCell( new Phrase("Horas Banco", font));
			cell[8] = new PdfPCell( new Phrase("Horas dia posterior", font) );
			
		
			for( int i = 0; i< cell.length ; i++) {
				table.addCell(cell[i]);
			}
			
			double totalBanco = 0.0;
			double totalTrabalhado = 0.0;
			double totalTrabalhar = 0.0;
			int totalFolgas = 0;
			int cont = 0;
			int inicioMes = 0;
			String data="";
			data = VerificaMes.mesStringParaMesNumero(mes);
			Calendar c = Calendar.getInstance();
			int d = c.get(Calendar.YEAR);
			int dia = 1;
			// usar a variavel i para achar o primeiro dia no mes em um while
			while( listaViagemMes.get(inicioMes).isInicioFimMes() == false) {
				// quando inicio de mes for verdadeiro é o inicio do mes
				// assim sendo começa-se a preencher o PDF a partir do primeiro dia trabalhado
				cont++;
				inicioMes++;
				// testar este while
			}
			for( int i= inicioMes; i < listaViagemMes.size(); i++) {
				
				BaseColor backgroundColor= new BaseColor(220,220,220);
				
				cell[0] = new PdfPCell( new Phrase(setDiaSemana(cont),font ));
				
				cell[1] = new PdfPCell( new Phrase(Integer.toString(dia)+"/"+data+"/"+ Integer.toString(d), font));
				dia++;
				if( listaViagemMes.get(i).isFeriadoMunicipal()== true && (cont != 0 || cont != 6) ) {
					cell[2] = new PdfPCell( new Phrase("Municipal/Ponto Facultativo", font));
				}
				else if( listaViagemMes.get(i).isFeriado() == true && (cont != 0 || cont != 6) ) {
					cell[2] = new PdfPCell( new Phrase("Nacional", font));
				}
				else
					cell[2] = new PdfPCell( new Phrase("Não", font));
				cell[3] = new PdfPCell( new Phrase(Double.toString(listaViagemMes.get(i).getSaida()), font ) );
				cell[4] = new PdfPCell( new Phrase(Double.toString(listaViagemMes.get(i).getChegada()), font ));
				cell[5] = new PdfPCell( new Phrase(Double.toString(listaViagemMes.get(i).getHoraTrabalharDia()), font ));
				if( listaViagemMes.get(i).getHoraTrabalhadaDia() > 0 )
					cell[6] = new PdfPCell( new Phrase(Double.toString(listaViagemMes.get(i).getHoraTrabalhadaDia()), font ));
				else {
					if( cont == 0 || cont == 6)
						cell[6] = new PdfPCell(new Phrase("0", font));
					if( cont > 0 && cont < 6) {
						cell[6] = new PdfPCell(new Phrase("folga", font));
						totalFolgas ++;
					}
				}
				cell[7] = new PdfPCell( new Phrase(Double.toString(listaViagemMes.get(i).getHoraBancoDia()), font ));
				cell[8] = new PdfPCell( new Phrase( Double.toString(listaViagemMes.get(i).getHoraDiaPosterior()), font ));

				if( i % 2 == 0) {
					for( int l = 0; l < cell.length; l++)
						cell[l].setBackgroundColor(backgroundColor);
				}
				for( int j = 0; j< cell.length ; j++) {
					table.addCell(cell[j]);
				}
				totalBanco += listaViagemMes.get(i).getHoraBancoDia();
				totalTrabalhar += listaViagemMes.get(i).getHoraTrabalharDia();
				totalTrabalhado += listaViagemMes.get(i).getHoraTrabalhadaDia();
				cont++;
				if(cont == 7) {
					cont = 0;
				}
				// finaliza o for quando acha a variável inicioFimMes com valor 
				if(listaViagemMes.get(i).isInicioFimMes()== true && i > inicioMes )
					break;
			}
			doc.add(table);
			table = new PdfPTable(4);
			float tams1[]= {0.480f, 0.135f, 0.155f, 0.230f };
			table.setWidths(tams1);
			cell[0]= new PdfPCell( new Phrase("Total", font));
			cell[1] = new PdfPCell( new Phrase(Double.toString(totalTrabalhar), font));
			cell[2] = new PdfPCell( new Phrase(Double.toString(totalTrabalhado), font));
			cell[3]= new PdfPCell( new Phrase(Double.toString(totalBanco), font));
			
			for( int k = 0; k < 4 ; k++) {
				table.addCell(cell[k]);
			}
			doc.add(table);
			p = new Paragraph(" ");
			doc.add(p);
			p = new Paragraph(" ");
			doc.add(p);
			// total de horas/dias do banco
			double diasTirar =0.0;
			diasTirar = totalBanco /8.0;
			if(diasTirar > 8)
				p = new Paragraph(" Total de dias banco: " + diasTirar + " dias.");
			else
				p = new Paragraph(" Total de horas banco: " + totalBanco + " horas.");
			p.setAlignment(1);
			doc.add(p);
			// dias trabalhados no mes
			p = new Paragraph("Total de horas trabalhados: " + totalTrabalhado);
			p.setAlignment(1);
			doc.add(p);
			// dias trabalhados a trabalhar
			p = new Paragraph("Total de horas trabalhar: " + totalTrabalhar);
			p.setAlignment(1);
			doc.add(p);
			// folgas tiradas
			p = new Paragraph(" Total de dias de folgas tiradas: " + totalFolgas);
			p.setAlignment(1);
			doc.add(p);
			// dias a tirar menos a folga
			if( diasTirar > 8)
				p = new Paragraph("Total de dias a tirar: " + diasTirar);
			else {
				p = new Paragraph("Total de horas a tirar: " + totalBanco);
				p.setAlignment(1);
				doc.add(p);
			}
			doc.close();
			try {
				Desktop.getDesktop().open(new File(arquivoPDF));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (FileNotFoundException | DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private  static String setDiaSemana( int indice) {
		String dia = "";
		if( indice == 0)
			dia = "Domingo";
		if( indice == 1)
			dia = "Segunda";
		if( indice == 2)
			dia  ="Terça";
		if( indice == 3)
			dia = "Quarta";
		if( indice == 4)
			dia  ="Quinta";
		if( indice == 5)
			dia = "Sexta";
		if( indice == 6)
			dia  = "Sábado";
		return dia;
	}

}// fim classe
