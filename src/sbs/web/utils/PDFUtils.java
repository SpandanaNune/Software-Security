package sbs.web.utils;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Date;

import org.apache.commons.io.FilenameUtils;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import sbs.web.models.Transaction;
import sbs.web.models.User;

public class PDFUtils {
	private static String FILE;
	static String USER_PASS = "Hello123";

	static String OWNER_PASS = "Owner123";
	private static Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
	private static Font redFont = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL, BaseColor.RED);
	private static Font subFont = new Font(Font.FontFamily.TIMES_ROMAN, 16, Font.BOLD);
	private static Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);

	public static void generatePDF(ArrayList<Transaction> transactions, String filePath,User user)
			throws DocumentException, MalformedURLException, IOException {

		FILE = filePath;

		System.out.println(FILE);
		String USER_PASS = user.getDob();
		String OWNER_PASS = user.getDob();
		Document document = new Document();
		PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(FILE));
		writer.setEncryption(USER_PASS.getBytes(), OWNER_PASS.getBytes(), PdfWriter.ALLOW_PRINTING,
				PdfWriter.ENCRYPTION_AES_128);
		document.open();
		addTitlePage(document, user.getFirstname());
		addDetails(document, transactions);
		document.close();

	}

	private static void addTitlePage(Document document,String userName)
			throws DocumentException, MalformedURLException, IOException {

		Paragraph preface = new Paragraph();
		addEmptyLine(preface, 1);
		// Lets write a big header
		preface.add(new Paragraph("Money Tree Banking Corporation", catFont));
		addEmptyLine(preface, 4);
		String logo = FilenameUtils.separatorsToSystem(System.getProperty("catalina.home") +"/images/mtbclogo.png");

		Image img;

		img = Image.getInstance(logo);
		if (img.getScaledWidth() > 100 || img.getScaledHeight() > 100) {
			img.scaleToFit(100, 100);
		}
		img.setAlignment(Element.ALIGN_RIGHT);
		document.add(img);

		preface.add(new Paragraph("Statement Of Account", catFont));

		addEmptyLine(preface, 1);
		preface.add(new Paragraph("Name: " + userName, smallBold));
		

		addEmptyLine(preface, 3);

		preface.add(new Paragraph("Generated on: " + new Date(), redFont));

		document.add(preface);
		// Start a new page
		document.newPage();
	}

	static void addDetails(Document document, ArrayList<Transaction> transactions) throws DocumentException {
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
		if(transactions!=null)
		for (Transaction t : transactions) {
			table.addCell(String.valueOf(t.getPrimaryKey().getTransactionId()));
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
