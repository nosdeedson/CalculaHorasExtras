package helper;

import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

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
		System.out.println(listaViagemMes.size());
		String arquivoPDF = "relatorio_" +"motorista.pdf";
		try {
			PdfWriter.getInstance(doc, new FileOutputStream(arquivoPDF));
			doc.open();
			Paragraph p = new Paragraph(" Relat�rio referente ao m�s: " + mes);
			p.setAlignment(1);
			doc.add(p);
			p = new Paragraph(" ");
			doc.add(p);
			p = new Paragraph("Motorista: "  + motorista);
			p.setAlignment(1);
			doc.add(p);
			p = new Paragraph(" ");
			doc.add(p);
			
			PdfPTable table = new PdfPTable(7);
			PdfPCell cell[] = new PdfPCell[7];
			cell[0] = new PdfPCell(new Phrase("Dia Semana"));
			cell[1] = new PdfPCell( new Phrase("Sa�da"));
			cell[2]= new PdfPCell( new Phrase("Chegada"));
			cell[3] = new PdfPCell( new Phrase("Trabalhar"));
			cell[4] = new PdfPCell( new Phrase("Trabalhada"));
			cell[5] = new PdfPCell( new Phrase("Horas Banco"));
			cell[6] = new PdfPCell( new Phrase("Horas dia posterior") );
		
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
				if( listaViagemMes.get(i).getHoraTrabalhadaDia() > 0)
					cell[4] = new PdfPCell( new Phrase(Double.toString(listaViagemMes.get(i).getHoraTrabalhadaDia()) ));
				else {
					cell[4] = new PdfPCell(new Phrase("Folga"));
					totalFolgas ++;
				}
				cell[5] = new PdfPCell( new Phrase(Double.toString(listaViagemMes.get(i).getHoraBancoDia())));
				cell[6] = new PdfPCell( new Phrase( Double.toString(listaViagemMes.get(i).getHoraDiaPosterior() )));
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
			table = new PdfPTable(7);
			cell[0] = new PdfPCell( new Phrase(""));
			cell[1]= new PdfPCell( new Phrase(""));
			cell[2]= new PdfPCell( new Phrase("Total"));
			cell[3] = new PdfPCell( new Phrase(Double.toString(totalTrabalhar)));
			cell[4] = new PdfPCell( new Phrase(Double.toString(totalTrabalhado)));
			cell[5]= new PdfPCell( new Phrase(Double.toString(totalBanco)));
			cell[6] = new PdfPCell( new Phrase(""));
			for( int k = 0; k< cell.length ; k++) {
				table.addCell(cell[k]);
			}
			doc.add(table);
			p = new Paragraph(" ");
			doc.add(p);
			p = new Paragraph(" ");
			doc.add(p);
			p = new Paragraph(" Total de folgas tiradas: " + totalFolgas);
			p.setAlignment(1);
			doc.add(p);
			p = new Paragraph(" Total de dias a tirar: " + totalTrabalhado /8);
			p.setAlignment(1);
			doc.add(p);
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
			dia  ="Ter�a";
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