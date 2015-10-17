package sbs.web.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import sbs.web.models.Transaction;

public class PDFUtils {
	private static String FILE;
	static String USER_PASS = "Hello123";
	 
	static String OWNER_PASS = "Owner123";
	 private static Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 18,
		      Font.BOLD);
		  private static Font redFont = new Font(Font.FontFamily.TIMES_ROMAN, 12,
		      Font.NORMAL, BaseColor.RED);
		  private static Font subFont = new Font(Font.FontFamily.TIMES_ROMAN, 16,
		      Font.BOLD);
		  private static Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 12,
		      Font.BOLD);
		  public static void generatePDF(ArrayList<Transaction> transactions,String filePath) throws FileNotFoundException, DocumentException
		  {
			 
			  FILE = filePath;
			  
			  System.out.println(FILE);
				String USER_PASS = "abc"; 	 
		    	String OWNER_PASS = "abc";
		    	 Document document = new Document();
		      PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(FILE));
		      writer.setEncryption(USER_PASS.getBytes(), OWNER_PASS.getBytes(),
		    	        PdfWriter.ALLOW_PRINTING, PdfWriter.ENCRYPTION_AES_128);
		      document.open();
		      addTitlePage(document,1234,"Swetha");
		      addDetails(document, transactions);
		      document.close();

		  }
		  
		  private static void addTitlePage(Document document,int accountNo,String userName)
			      throws DocumentException {
			    Paragraph preface = new Paragraph();
			    addEmptyLine(preface, 1);
			    // Lets write a big header
			    preface.add(new Paragraph("Money Tree Banking Corporation", catFont));
			    addEmptyLine(preface, 4);

			    preface.add(new Paragraph("Statement Of Account", catFont));

			    addEmptyLine(preface, 1);
			    preface.add(new Paragraph("Name: "+userName,smallBold));
			    addEmptyLine(preface, 1);
			    preface.add(new Paragraph("Account No: " +accountNo , smallBold));

			    addEmptyLine(preface, 3);

			    preface.add(new Paragraph("Generated on: "+new Date(),
			        redFont));

			    document.add(preface);
			    // Start a new page
			    document.newPage();
			  }
		  static void addDetails(Document document,ArrayList<Transaction> transactions) throws DocumentException
		  {
			  Paragraph preface = new Paragraph();
			    preface.add(new Paragraph("Transaction History", catFont));
			    addEmptyLine(preface, 3);

			    PdfPTable table = new PdfPTable(4);

			    PdfPCell c1 = new PdfPCell(new Phrase("Transaction Id"));
			    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
			    table.addCell(c1);

			    c1 = new PdfPCell(new Phrase("Transaction Type"));
			    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
			    table.addCell(c1);

			    c1 = new PdfPCell(new Phrase("Amount"));
			    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
			    table.addCell(c1);
			    c1 = new PdfPCell(new Phrase("Transaction Status"));
			    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
			    table.addCell(c1);
			    table.setHeaderRows(1);
			    
			    for(Transaction t: transactions)
			    {
			    	table.addCell(String.valueOf(t.getPrimaryKey().getTransactionID()));
			    	table.addCell(t.getTransactionType());
			    	table.addCell(String.valueOf(t.getAmount()));
			    	table.addCell(t.getStatus());
			    }
			    document.add(preface);
			  document.add(table);

		  }
		  private static void addEmptyLine(Paragraph paragraph, int number) {
			    for (int i = 0; i < number; i++) {
			      paragraph.add(new Paragraph(" "));
			    }
			  }


}
