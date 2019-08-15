package helper;

import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import javax.swing.JOptionPane;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import model.Viagem;

public class GerarRelatorioPDF {
	
	public static void gerarRelatorioMesPDF( List<Viagem> listaViagemMes, String motorista, String mes) {
		
		Document doc = new Document();
		JOptionPane.showMessageDialog(null, " Os PDFs gerados tem que ser salvos. \n Senão serão perdidos. ");
		String arquivoPDF = "relatorio_" +"motorista.pdf";
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
			
			PdfPTable table = new PdfPTable(8);
			PdfPCell cell[] = new PdfPCell[8];
			cell[0] = new PdfPCell(new Phrase("Dia Semana"));
			cell[1] = new PdfPCell( new Phrase("Saída"));
			cell[2]= new PdfPCell( new Phrase("Chegada"));
			cell[3] = new PdfPCell( new Phrase("Trabalhar"));
			cell[3].setSpaceCharRatio(10);
			cell[4] = new PdfPCell( new Phrase("Trabalhada"));
			cell[5] = new PdfPCell( new Phrase("Horas Banco"));
			cell[6] = new PdfPCell( new Phrase("Horas dia posterior") );
			cell[7] = new PdfPCell(new Phrase("Feriado"));
		
			for( int i = 0; i< cell.length ; i++) {
				table.addCell(cell[i]);
			}
			
			double totalBanco = 0.0;
			double totalTrabalhado = 0.0;
			double totalTrabalhar = 0.0;
			int totalFolgas = 0;
			int cont = 0;
			for( int i = 0; i < listaViagemMes.size(); i++) {
				
				BaseColor backgroundColor= new BaseColor(220,220,220);
				
				cell[0] = new PdfPCell( new Phrase(setDiaSemana(cont) ));
				cell[1] = new PdfPCell( new Phrase(Double.toString(listaViagemMes.get(i).getSaida()) ));
				cell[2] = new PdfPCell( new Phrase(Double.toString(listaViagemMes.get(i).getChegada()) ));
				cell[3] = new PdfPCell( new Phrase(Double.toString(listaViagemMes.get(i).getHoraTrabalharDia())));
				if( listaViagemMes.get(i).getHoraTrabalhadaDia() > 0 )
					cell[4] = new PdfPCell( new Phrase(Double.toString(listaViagemMes.get(i).getHoraTrabalhadaDia()) ));
				else {
					if( cont == 0 || cont == 6)
						cell[4] = new PdfPCell(new Phrase("0"));
					if( cont > 0 && cont < 6) {
						cell[4] = new PdfPCell(new Phrase("folga"));
						totalFolgas ++;
					}
				}
				cell[5] = new PdfPCell( new Phrase(Double.toString(listaViagemMes.get(i).getHoraBancoDia())));
				cell[6] = new PdfPCell( new Phrase( Double.toString(listaViagemMes.get(i).getHoraDiaPosterior() )));
				if( listaViagemMes.get(i).isFeriadoMunicipal()== true && (cont != 0 || cont != 6) ) {
					cell[7] = new PdfPCell( new Phrase("Municipal"));
				}
				else if( listaViagemMes.get(i).isFeriado() == true && (cont != 0 || cont != 6) ) {
					cell[7] = new PdfPCell( new Phrase("Nacional"));
				}
				else
					cell[7] = new PdfPCell( new Phrase("Normal"));
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
			}
			doc.add(table);
			table = new PdfPTable(8);
			cell[0] = new PdfPCell( new Phrase(""));
			cell[1]= new PdfPCell( new Phrase(""));
			cell[2]= new PdfPCell( new Phrase("Total"));
			cell[3] = new PdfPCell( new Phrase(Double.toString(totalTrabalhar)));
			cell[4] = new PdfPCell( new Phrase(Double.toString(totalTrabalhado)));
			cell[5]= new PdfPCell( new Phrase(Double.toString(totalBanco)));
			cell[6] = new PdfPCell( new Phrase(""));
			cell[7] = new PdfPCell( new Phrase(""));
			for( int k = 0; k< cell.length ; k++) {
				table.addCell(cell[k]);
			}
			doc.add(table);
			p = new Paragraph(" ");
			doc.add(p);
			p = new Paragraph(" ");
			doc.add(p);
			// total de horas/dias do banco
			double diasTirar =0.0;
			diasTirar = totalBanco /8;
			if(diasTirar > 8)
				p = new Paragraph(" Total de dias banco: " + diasTirar);
			else
				p = new Paragraph(" Total de horas banco: " + totalBanco);
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
			dia  = "Sabado";
		return dia;
	}
}
