package sbs.web.controllers;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.itextpdf.text.DocumentException;

import sbs.web.models.Transaction;
import sbs.web.models.User;
import sbs.web.service.TransactionService;
import sbs.web.utilities.SendMail;
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
	
	
	
	@RequestMapping(value = "/downloadTransactions")
	public String downloadTransactions(Model model,HttpServletRequest request) {	
		System.out.println("Download Transaction History");
		ArrayList<Transaction> transactions = (ArrayList<Transaction>)transactionService.getAllTransactions(1234);
		try {
			User user = new User();
			user.setEmail("sswetha2809@gmail.com");
			user.setFirstname("swetha");
			user.setLastname("swaminathan");
			user.setUsername("sswetha2809");
			  String home = System.getProperty("user.home");
			  String filePath = home+"\\Downloads\\" + user.getFirstname(); 
			PDFUtils.generatePDF(transactions,filePath);

		} catch (FileNotFoundException | DocumentException e) {
			e.printStackTrace();
		}
		
		model.addAttribute("transactions", transactions);


		return "transactionhistory";
	}
	
	@RequestMapping(value = "/emailTransactions")
	public String emailTransactions(Model model,HttpServletRequest request) {	
		System.out.println("Mail Transaction History");
		ArrayList<Transaction> transactions = (ArrayList<Transaction>)transactionService.getAllTransactions(1234);
		try {
			ServletContext context = request.getSession().getServletContext();

				User user = new User();
				user.setEmail("sswetha2809@gmail.com");
				user.setFirstname("swetha");
				user.setLastname("swaminathan");
				user.setUsername("sswetha2809");
				String path = context.getRealPath("/WEB-INF/temp")+"//"+user.getFirstname();
				 System.out.println(path);
				PDFUtils.generatePDF(transactions,path);

				SendMail.sendStatement(user,path);

		} catch (FileNotFoundException | DocumentException e) {
			e.printStackTrace();
		}
		
		model.addAttribute("transactions", transactions);


		return "transactionhistory";
	}
}
