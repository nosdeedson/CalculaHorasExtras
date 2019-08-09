package helper;

import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

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
			
			PdfPTable table = new PdfPTable(7);
			PdfPCell cell = new PdfPCell( new Phrase("Dia Semana"));
			PdfPCell cell1 = new PdfPCell( new Phrase("Saída"));
			PdfPCell cell2= new PdfPCell( new Phrase("Chegada"));
			PdfPCell cell3 = new PdfPCell( new Phrase("Trabalhar"));
			PdfPCell cell4 = new PdfPCell( new Phrase("Trabalhada"));
			PdfPCell cell5 = new PdfPCell( new Phrase("Horas Banco"));
			PdfPCell cell6 = new PdfPCell( new Phrase("Horas dia posterior") );
			
			table.addCell(cell);
			table.addCell(cell1);
			table.addCell(cell2);
			table.addCell(cell3);
			table.addCell(cell4);
			table.addCell(cell5);
			table.addCell(cell6);
			
			double totalBanco = 0.0;
			double totalTrabalhado = 0.0;
			double totalTrabalhar = 0.0;
			int cont = 0;
			for( int i = 0; i < listaViagemMes.size(); i++) {
				cell = new PdfPCell( new Phrase(setDiaSemana(cont) ));
				cell1 = new PdfPCell( new Phrase(Double.toString(listaViagemMes.get(i).getSaida()) ));
				cell2 = new PdfPCell( new Phrase(Double.toString(listaViagemMes.get(i).getChegada()) ));
				cell3 = new PdfPCell( new Phrase(Double.toString(listaViagemMes.get(i).getHoraTrabalharDia())));
				cell4 = new PdfPCell( new Phrase(Double.toString(listaViagemMes.get(i).getHoraTrabalhadaDia()) ));
				cell5 = new PdfPCell( new Phrase(Double.toString(listaViagemMes.get(i).getHoraBancoDia())));
				cell6 = new PdfPCell( new Phrase( Double.toString(listaViagemMes.get(i).getHoraDiaPosterior() )));
				
				table.addCell(cell);
				table.addCell(cell1);
				table.addCell(cell2);
				table.addCell(cell3);
				table.addCell(cell4);
				table.addCell(cell5);
				table.addCell(cell6);
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
			
			cell = new PdfPCell( new Phrase(""));
			cell1 = new PdfPCell( new Phrase(""));
			cell2 = new PdfPCell( new Phrase("Total"));
			cell3 = new PdfPCell( new Phrase(Double.toString(totalTrabalhar)));
			cell4 = new PdfPCell( new Phrase(Double.toString(totalTrabalhado)));
			cell5 = new PdfPCell( new Phrase(Double.toString(totalBanco)));
			cell6 = new PdfPCell( new Phrase(""));
			table.addCell(cell);
			table.addCell(cell1);
			table.addCell(cell2);
			table.addCell(cell3);
			table.addCell(cell4);
			table.addCell(cell5);
			table.addCell(cell6);
			doc.add(table);
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
