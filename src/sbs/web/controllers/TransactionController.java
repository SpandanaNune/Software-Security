package sbs.web.controllers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.itextpdf.text.DocumentException;

import sbs.web.models.Accounts;
import sbs.web.models.Transaction;
import sbs.web.models.TransactionDetails;
import sbs.web.models.TransactionLog;
import sbs.web.models.Transaction_CompositeKey;
import sbs.web.models.User;
import sbs.web.service.AccountsService;
import sbs.web.service.TransactionService;
import sbs.web.utilities.SendMail;
import sbs.web.utils.PDFUtils;

@Controller
public class TransactionController {
	
	static int transactionIDCounter=500;
	TransactionService transactionService;
	AccountsService accountService;
	@Autowired
	public void setTransactionService(TransactionService transactionService) {
		this.transactionService = transactionService;
	}
	
	@Autowired
	public void setAccountService(AccountsService accountService) {
		this.accountService = accountService;
	}
	
	@RequestMapping(value="/createTransaction")
	public String createTransactions(Model model, TransactionDetails transactionDetails) {
		System.out.println("inside transactionDetails");
		model.addAttribute("transactionDetails",transactionDetails);
		
		
		System.out.println(transactionDetails.getBalance());
		System.out.println(transactionDetails.getFromAccountNo());
		System.out.println(transactionDetails.getToAccountNo());
		
		//validate to account
		int fromUserAccount =transactionDetails.getFromAccountNo();
		int toUserAccount =transactionDetails.getToAccountNo();
		double amount=transactionDetails.getBalance();
		
		
//		String fromUserAccount ="1000";
//		String toUserAccount ="2000";
//		String amount="233.45";
//		
//		transactionDetails
		//add everything to transaction class and insert into db
		Transaction_CompositeKey fromCompositeKey = new Transaction_CompositeKey();
		fromCompositeKey.setAccountNo(fromUserAccount);
		fromCompositeKey.setTransactionId(++transactionIDCounter);
		
		//populate Transaction data
		Transaction fromTransaction = new Transaction();
		fromTransaction.setPrimaryKey(fromCompositeKey);
		fromTransaction.setStatus("pending");
		fromTransaction.setAmount(amount);
		fromTransaction.setTransactionType("Debit");
		fromTransaction.setCritical(false);
		
		System.out.println("fromTransaction "+fromTransaction.toString());
		//set status
		if(amount >1000){
			fromTransaction.setCritical(true);
		}
		
		
		//set same transaction ID for to account
		Transaction_CompositeKey toCompositeKey = new Transaction_CompositeKey();
		toCompositeKey.setAccountNo(toUserAccount);
		toCompositeKey.setTransactionId(transactionIDCounter);
		Transaction toTransaction = new Transaction();
		toTransaction.setPrimaryKey(toCompositeKey);
		toTransaction.setAmount(amount);
		toTransaction.setStatus("pending");
		toTransaction.setTransactionType("Credit");
		toTransaction.setCritical(false);
		
		System.out.println("toTransaction "+toTransaction.toString());
		//set status
		if(amount >1000){
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
	public String retrieveTransactionsLog(@Valid TransactionLog transactionLog,BindingResult result,Model model) {	
		System.out.println(transactionLog.getLogFilter());
		List<Transaction> transactions = new ArrayList<Transaction>();
		if(transactionLog!=null && transactionLog.getLogFilter()!=null)
		{
		if(transactionLog.getLogFilter().equals("date"))
		{
			transactions.addAll(transactionService.getAllTransactions(transactionLog.getDate()));
			System.out.println(transactionLog.getDate());

		}else if(transactionLog.getLogFilter().equals("account"))
		{
			transactions.addAll(transactionService.getAllTransactions(transactionLog.getAccountNo()));
			System.out.println(transactionLog.getAccountNo());

		}
		else if(transactionLog.getLogFilter().equals("name"))
		{
			List<Accounts> accounts =accountService.getAccountDetails(transactionLog.getName());
			for(Accounts acct:accounts)
			{
				transactions.addAll(transactionService.getAllTransactions(acct.getAccountNo()));
			}
			System.out.println(transactionLog.getName());

		}
		}
		model.addAttribute("transactions", transactions);


		return "transactionlog";
	}
	
	@RequestMapping(value="/transactionhistory")
	public String showTransactions(Model model) {	
		model.addAttribute("name","swetha");
		ArrayList<Transaction> transactions = (ArrayList<Transaction>)transactionService.getAllTransactions(1000);
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
//			user.setUsername("sswetha2809");
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
//				user.setUsername("sswetha2809");
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
	
	@RequestMapping(value = "/accepttransactionbtn")
	public String ApproveTransactionsByManager(Model model,@RequestParam("Accept") int transactionId){
		boolean approved=false;
		Transaction t=(Transaction)transactionService.getTransaction(transactionId);
		System.out.println(t);
		t.setStatus("APPROVED");
		Accounts acct = accountService.getAccountForID(t.getPrimaryKey().getAccountNo());
		double balance = acct.getBalance();
		if(t.getTransactionType().equalsIgnoreCase("CREDIT"))
		{
			acct.setBalance(balance + t.getAmount());
			approved= true;
			
		}else if(t.getTransactionType().equalsIgnoreCase("DEBIT"))
		{
			if(t.getAmount() <= balance)
			{
				acct.setBalance(balance - t.getAmount());
				approved = true;
			}
		}
		
		if(approved)
		{
			transactionService.updateTransaction(t);
			accountService.updateAccount(acct);
		}
		List<Transaction> transction = transactionService.getAllCriticalTransaction();
		model.addAttribute("transaction", transction);
		return "approvetransaction";
	}
	
	@RequestMapping(value = "/deletetransactionbtn")
	public String DeleteTransactionByManager(Model model,@RequestParam("Accept") int transactionId){
		Transaction t=(Transaction)transactionService.getTransaction(transactionId);
		System.out.println(t);
		t.setStatus("DECLINED");
		transactionService.updateTransaction(t);
		List<Transaction> transction = transactionService.getAllCriticalTransaction();
		model.addAttribute("transaction", transction);
		return "approvetransaction";
	}
	
	@RequestMapping(value="/approvetransaction")
	public String approveCriticalTransactions(Model model) {	
		List<Transaction> transction = transactionService.getAllCriticalTransaction();
		model.addAttribute("transaction", transction);
		return "approvetransaction";
	}

	
	@RequestMapping(value="/bankers")
	public String bankersTransactions(Model model) {	
		ArrayList<Transaction> transactions = new ArrayList<Transaction>();
		ArrayList<Accounts> accounts = (ArrayList<Accounts>)accountService.getAccountsForBanker("banker");
		for(Accounts account: accounts)
		{
			transactions.addAll(transactionService.getAllCriticalTransactions(account.getAccountNo()));
		}
		
		model.addAttribute("transactions", transactions);
		return "bankers";
	}



@RequestMapping("/accepttransaction")
	public String approveTransaction(Model model, @RequestParam("Accept") int transactionId) {
	boolean approved = false;
		Transaction t=(Transaction)transactionService.getTransaction(transactionId);
		System.out.println(t);
		t.setStatus("APPROVED");
		Accounts acct = accountService.getAccountForID(t.getPrimaryKey().getAccountNo());
		double balance = acct.getBalance();
		if(t.getTransactionType().equalsIgnoreCase("CREDIT"))
		{
			acct.setBalance(balance + t.getAmount());
			approved= true;
			
		}else if(t.getTransactionType().equalsIgnoreCase("DEBIT"))
		{
			if(t.getAmount() <= balance)
			{
				acct.setBalance(balance - t.getAmount());
				approved = true;
			}
		}
		
		if(approved)
		{
			transactionService.updateTransaction(t);
			accountService.updateAccount(acct);
		}
		ArrayList<Transaction> transactions = new ArrayList<Transaction>();
		ArrayList<Accounts> accounts = (ArrayList<Accounts>)accountService.getAccountsForBanker("banker");
		for(Accounts account: accounts)
		{
			transactions.addAll(transactionService.getAllCriticalTransactions(account.getAccountNo()));
		}
		
		model.addAttribute("transactions", transactions);
		return "bankers";
		
	}
	@RequestMapping("/declinetransaction")
	public String declineTransaction(Model model, @RequestParam("Decline") int transactionId) {
		Transaction t=(Transaction)transactionService.getTransaction(transactionId);
		System.out.println(t);
		t.setStatus("DECLINED");
		transactionService.updateTransaction(t);
		
		ArrayList<Transaction> transactions = new ArrayList<Transaction>();
		ArrayList<Accounts> accounts = (ArrayList<Accounts>)accountService.getAccountsForBanker("banker");
		for(Accounts account: accounts)
		{
			transactions.addAll(transactionService.getAllCriticalTransactions(account.getAccountNo()));
		}
		
		model.addAttribute("transactions", transactions);
		return "bankers";
		
	}
}
