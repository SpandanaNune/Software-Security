package sbs.web.controllers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.itextpdf.text.DocumentException;

import sbs.web.models.Accounts;
import sbs.web.models.Transaction;
import sbs.web.models.Transaction_CompositeKey;
import sbs.web.models.User;
import sbs.web.service.TransactionService;
import sbs.web.utilities.SendMail;
import sbs.web.utils.PDFUtils;

@Controller
public class TransactionController {
	
	static int transactionIDCounter=1000;
	TransactionService transactionService;
	
	@Autowired
	public void setTransactionService(TransactionService transactionService) {
		this.transactionService = transactionService;
	}

	
	@RequestMapping(value="/createTransaction")
	public String createTransactions(Model model) {	
		
		String fromUserAccount ="1000";
		String toUserAccount ="2000";
		String amount="233.45";
		//add everything to transaction class and insert into db
		Transaction_CompositeKey fromCompositeKey = new Transaction_CompositeKey();
		fromCompositeKey.setAccountNo(Integer.parseInt(fromUserAccount));
		fromCompositeKey.setTransactionId(++transactionIDCounter);
		
		//populate Transaction data
		Transaction fromTransaction = new Transaction();
		fromTransaction.setPrimaryKey(fromCompositeKey);
		fromTransaction.setAmount(Double.parseDouble(amount));
		fromTransaction.setTransactionType("Debit");
		fromTransaction.setCritical(false);
		//set status
		if(Double.parseDouble(amount) >1000){
			fromTransaction.setCritical(true);
		}
		
		
		//set same transaction ID for to account
		Transaction_CompositeKey toCompositeKey = new Transaction_CompositeKey();
		toCompositeKey.setAccountNo(Integer.parseInt(toUserAccount));
		toCompositeKey.setTransactionId(transactionIDCounter);
		Transaction toTransaction = new Transaction();
		toTransaction.setPrimaryKey(toCompositeKey);
		toTransaction.setAmount(Double.parseDouble(amount));
		toTransaction.setTransactionType("Credit");
		toTransaction.setCritical(false);
		//set status
		if(Double.parseDouble(amount) >1000){
			toTransaction.setCritical(true);
		}
		try{
			transactionService.addTransactions(fromTransaction, toTransaction);
	//		model.addAttribute("transactions", transactions);
		}
		catch ( Exception e) {
			e.printStackTrace();
		}
		return "homepage";
	
	}
	
	@RequestMapping(value="/transactionlog")
	public String retrieveTransactionsLog(Model model) {	
		return "transactionlog";
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
			  String filePath = home+"\\Downloads\\" + user.getFirstname()+".pdf"; 
			PDFUtils.generatePDF(transactions,filePath);

		} catch (FileNotFoundException | DocumentException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
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
				// saving the generated pdf to a temp folder for e-mailing
				String path = context.getRealPath("/WEB-INF/temp")+"\\"+user.getFirstname()+".pdf";
				PDFUtils.generatePDF(transactions,path);

				SendMail.sendStatement(user,path);
				
				// delete the temp file after sending e-mail
				
				File file = new File(path);
				if(file.delete()){
	    			System.out.println(file.getName() + " is deleted!");
	    		}else{
	    			System.out.println("Delete operation is failed.");
	    		}

		} catch (FileNotFoundException | DocumentException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		model.addAttribute("transactions", transactions);


		return "transactionhistory";
	}
	
	@RequestMapping(value = "/approvetransaction")
	public String ApproveTransaction(Model model){
		List<Transaction> transction = transactionService.getAllCriticalTransaction();
		model.addAttribute("transaction", transction);
		return "approvetransaction";
	}
}
