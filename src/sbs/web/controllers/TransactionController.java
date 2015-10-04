package sbs.web.controllers;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.itextpdf.text.DocumentException;

import sbs.web.models.Transaction;
import sbs.web.service.TransactionService;
import sbs.web.utils.PDFUtils;

@Controller
public class TransactionController {
	
	
	TransactionService transactionService;
	
	@Autowired
	public void setTransactionService(TransactionService transactionService) {
		this.transactionService = transactionService;
	}

	@RequestMapping(value="/transactionhistory")
	public String showTransactions(Model model) {	
		model.addAttribute("name","swetha");
		ArrayList<Transaction> transactions = (ArrayList<Transaction>)transactionService.getAllTransactions(1234);
		model.addAttribute("transactions", transactions);
		return "transactionhistory";
	}
	
	
	
	@RequestMapping(value = "/downloadTransactions", method=RequestMethod.POST)
	public String downloadTransactions(Model model) {	
		System.out.println("Transaction History");
		ArrayList<Transaction> transactions = (ArrayList<Transaction>)transactionService.getAllTransactions(1234);
		try {
			PDFUtils.generatePDF(transactions);
		} catch (FileNotFoundException | DocumentException e) {
			e.printStackTrace();
		}
		
		model.addAttribute("transactions", transactions);


		return "transactionhistory";
	}
}
