package sbs.web.controllers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import javax.validation.Valid;

import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.itextpdf.text.DocumentException;

import sbs.web.models.Accounts;
import sbs.web.models.OTP;
import sbs.web.models.Transaction;
import sbs.web.models.TransactionDetails;
import sbs.web.models.TransactionLog;
import sbs.web.models.Transaction_CompositeKey;
import sbs.web.models.User;
import sbs.web.service.AccountsService;
import sbs.web.service.TransactionService;
import sbs.web.service.UserService;
import sbs.web.service.UtilityService;
import sbs.web.utilities.SendMail;
import sbs.web.utils.PDFUtils;
import sbs.web.utils.PKIUtil;

@Controller
public class TransactionController {

	static int transactionIDCounter;
	TransactionService transactionService;
	AccountsService accountService;
	UtilityService utilityService;
	private UserService userService;
	private static final Logger logger = Logger.getLogger(TransactionController.class);

	private static String defaultPath = System.getProperty("catalina.home") + "/users_keys/";

	@Autowired
	public void setTransactionService(TransactionService transactionService) {
		System.out.println("Setting TransactionService in TransactionController");
		this.transactionService = transactionService;
		
	}

	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	@Autowired
	public void setUtilityService(UtilityService utilityService) {
		this.utilityService = utilityService;
	}

	@Autowired
	public void setAccountService(AccountsService accountService) {
		this.accountService = accountService;
	}

	@RequestMapping(value = "/debitCreditToAccount")
	public String debitCreditToAccount(Model model, @Valid TransactionDetails transactionDetails, BindingResult result,
			Principal principal) {

		String username;
		username = principal.getName();

		if (result.hasErrors()) {
			ArrayList<Accounts> accounts = (ArrayList<Accounts>) accountService.getAccountDetails(username);
			model.addAttribute("accounts", accounts);
			return "debitcredit";
		}

		long accountNumber = Long.parseLong(transactionDetails.getFromAccountNo());
		String transactionType = transactionDetails.getTransaction_type();
		double amount = Double.parseDouble(transactionDetails.getBalance());

		Transaction_CompositeKey compositeKey = new Transaction_CompositeKey();
		compositeKey.setAccountNo(accountNumber);
		
		//caluculating Transaction counter
		System.out.println("Initiallizing Transaction controller");
		List<Transaction> transactions = transactionService.getTransactions();
		int maxVal = 0, curVal;
		for (Transaction tran : transactions) {
			curVal = tran.getPrimaryKey().getTransactionId();
			if (maxVal < curVal) {
				maxVal = curVal;
			}
		}
		System.out.println("Maximum value is " + transactionIDCounter);
		transactionIDCounter = maxVal;
		compositeKey.setTransactionId(++transactionIDCounter);

		// populate Transaction data
		Transaction transaction = new Transaction();
		transaction.setPrimaryKey(compositeKey);
		transaction.setStatus("APPROVED");
		transaction.setAmount(amount);
		transaction.setTransactionType(transactionType);
		transaction.setCritical(false);

		// set status

		 if (amount > 1000) {
			 result.rejectValue("balance", "DuplicateKeyException.transactionDetails.balance", "You can credit or debit upto $1000");
			 ArrayList<Accounts> accounts = (ArrayList<Accounts>) accountService.getAccountDetails(username);
				model.addAttribute("accounts", accounts);
				return "debitcredit";
		  }

		try {
			System.out.println("Checking if " + compositeKey.getAccountNo() + " exists..");
			// validate account- it valid since it is provied with the drop down
			// check balance
			Accounts from = accountService.getAccountForID(accountNumber);
			if (transactionType.equalsIgnoreCase("DEBIT")) {
				// deduct and update
				if (from.getBalance() - amount < 0) {
					// return a message that insufficient balance
					result.rejectValue("balance", "DuplicateKeyException.transactionDetails.balance", "Insufficient balance");
					ArrayList<Accounts> accounts = (ArrayList<Accounts>) accountService.getAccountDetails(username);
					model.addAttribute("accounts", accounts);
					
					return "debitcredit";
				}
				from.setBalance(from.getBalance() - amount);
			} else {
				from.setBalance(from.getBalance() + amount);
			}
			transactionService.saveTransaction(transaction);
			accountService.updateAccount(from);
		} catch (Exception e) {
			logger.error("Failure :" + e.getMessage());
			e.printStackTrace();
		}
		return "welcome";
	}
	// ************Pankaj*************

	@RequestMapping(value = "/createMerchnatTransaction")
	public String createMerchantTransactions(Model model, @Valid TransactionDetails transactionDetails,
			BindingResult result, HttpServletRequest request, Principal principal) throws IOException {

		String username;
		username = principal.getName();

		if (result.hasErrors()) {
			ArrayList<Accounts> accounts = (ArrayList<Accounts>) accountService.getAccountDetails(username);
			model.addAttribute("accounts", accounts);
			return "merchanttransaction";
		}

		model.addAttribute("transactionDetails", transactionDetails);

		System.out.println("check1");
		long fromUserAccount = Long.parseLong(transactionDetails.getFromAccountNo());
		long toOtherUserAccount;
		try{
			
			toOtherUserAccount = Long.parseLong(transactionDetails.getToOtherAccountNo());
		}catch(NumberFormatException e){
			ArrayList<Accounts> accounts = (ArrayList<Accounts>) accountService.getAccountDetails(username);
			model.addAttribute("accounts", accounts);
			result.rejectValue("toOtherAccountNo", "DuplicateKeyException.transactionDetails.balance",
					"Account number should be numeric");
			System.out.println("Invalid other account number, not of length 8");
			logger.error("Failure :" + e.getMessage());
			return "merchanttransaction";
		}
		

		double amount = Double.parseDouble(transactionDetails.getBalance());
		if (fromUserAccount == toOtherUserAccount) {
			ArrayList<Accounts> accounts = (ArrayList<Accounts>) accountService.getAccountDetails(username);
			model.addAttribute("accounts", accounts);
			result.rejectValue("toOtherAccountNo", "DuplicateKeyException.transactionDetails.toOtherAccountNo",
					"From and To Account should be different");
			return "merchanttransaction";
		}
		// transactionDetails
		// add everything to transaction class and insert into db
		Transaction_CompositeKey fromCompositeKey = new Transaction_CompositeKey();
		fromCompositeKey.setAccountNo(fromUserAccount);
		//caluculating Transaction counter
				System.out.println("Initiallizing Transaction controller");
				List<Transaction> transactions = transactionService.getTransactions();
				int maxVal = 0, curVal;
				for (Transaction tran : transactions) {
					curVal = tran.getPrimaryKey().getTransactionId();
					if (maxVal < curVal) {
						maxVal = curVal;
					}
				}
				System.out.println("Maximum value is " + transactionIDCounter);
				transactionIDCounter = maxVal;
				
				
		fromCompositeKey.setTransactionId(++transactionIDCounter);

		// populate Transaction data
		Transaction fromTransaction = new Transaction();
		fromTransaction.setPrimaryKey(fromCompositeKey);
		fromTransaction.setStatus("APPROVED");
		fromTransaction.setAmount(amount);
		fromTransaction.setTransactionType("DEBIT");
		fromTransaction.setCritical(false);
		// set status

		if (amount > 1000) {
			fromTransaction.setCritical(true);
			fromTransaction.setStatus("PENDING");
		}

		// set same transaction ID for to account
		Transaction_CompositeKey toCompositeKey = new Transaction_CompositeKey();

		toCompositeKey.setTransactionId(transactionIDCounter);
		Transaction toTransaction = new Transaction();
		toTransaction.setPrimaryKey(toCompositeKey);
		toTransaction.setAmount(amount);
		toTransaction.setStatus("APPROVED");
		toTransaction.setTransactionType("CREDIT");
		toTransaction.setCritical(false);

		// Which account to insert
		System.out.println("Setting other user " + toOtherUserAccount);
		toCompositeKey.setAccountNo(toOtherUserAccount);
		// }

		// set status

		if (amount > 1000) {
			toTransaction.setCritical(true);
			toTransaction.setStatus("PENDING");
		}
		try {

			// toUserAccount
			System.out.println("Checking if " + toCompositeKey.getAccountNo() + " exists");
			Accounts acc = accountService.getAccountForID(toCompositeKey.getAccountNo());

			if (acc != null) {

				Accounts from = accountService.getAccountForID(fromUserAccount);
				if (from.getBalance() - amount < 0) {
					// return a message that insufficient balance
					ArrayList<Accounts> accounts = (ArrayList<Accounts>) accountService.getAccountDetails(username);
					model.addAttribute("accounts", accounts);
					result.rejectValue("balance", "DuplicateKeyException.transactionDetails.balance", "Insufficient balance");
					System.out.println("Insufficient balance");
					return "merchanttransaction";
				}

				System.out.println("To Account exists, Going ahead with the transaction");
				transactionService.addTransactions(fromTransaction, toTransaction);
				// if from and to transaction is "approved" then update the
				// balance for from and to account

				if (toTransaction.getStatus().equalsIgnoreCase("APPROVED")) {

					// Accounts from =
					// accountService.getAccountForID(fromCompositeKey.getAccountNo());
					Accounts to = accountService.getAccountForID(toCompositeKey.getAccountNo());

					System.out.println("Deducting/Adding account balance for both accounts");
					// deduct balance in both accounts.
					from.setBalance(from.getBalance() - amount);
					System.out.println(to.getBalance());
					System.out.println(amount);
					to.setBalance(to.getBalance() + amount);
					System.out.println("From account details " + from.toString());
					System.out.println("To account details " + to.toString());
					accountService.updateAccount(from);
					accountService.updateAccount(to);
				}
				// return an appropriate
			} else {
				// say that the to account number is invalid
			}
			// model.addAttribute("transactions", transactions);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Failure :" + e.getMessage());
		}
		return "welcome";

	}

	
	@RequestMapping(value = "/makePaymentTransaction")
	public String createPaymentTransactions(Model model, @Valid TransactionDetails transactionDetails,
			BindingResult result, HttpServletRequest request, Principal principal) throws IOException {

		
		String username;
		username = principal.getName();
		
		model.addAttribute("transactionDetails", new TransactionDetails());
		
		try{
			
			ArrayList<Accounts> accounts = (ArrayList<Accounts>)accountService.getAccountDetails(username);
			model.addAttribute("accounts",accounts);
			
			// get all merchant account 
			List<User> userList = userService.getAllMerchantAccounts();
			
			System.out.println("Number of Merchants: "+userList.size());
			
			ArrayList<Accounts> toaccounts= new ArrayList<Accounts>();
			Accounts merchantAccount; 
			for(User user: userList){
				System.out.println("Getting accounts of merchant "+user.getUsername());
				merchantAccount=accountService.getMerchantAccountDetails(user.getUsername());
				System.out.println("Got account for "+merchantAccount.getAccountNo());
				toaccounts.add(merchantAccount);
			}
			System.out.println("Merchants account count: "+toaccounts.size());
			model.addAttribute("toaccounts", toaccounts);
		
			
		}catch ( Exception e) {
			logger.error("Failure :" + e.getMessage());
			e.printStackTrace();
		}
		
		if (result.hasErrors()) {
//			ArrayList<Accounts> accounts = (ArrayList<Accounts>) accountService.getAccountDetails(username);
//			model.addAttribute("accounts", accounts);
			System.out.println("Input has errors");
			System.out.println("Other account number "+transactionDetails.getToOtherAccountNo());
			System.out.println("Finding errors, " + result.toString());
			return "makepayment";
		}

		model.addAttribute("transactionDetails", transactionDetails);

		long fromUserAccount = Long.parseLong(transactionDetails.getFromAccountNo());
		long toOtherUserAccount = Long.parseLong(transactionDetails.getToOtherAccountNo());
		double amount = Long.parseLong(transactionDetails.getBalance());

		// transactionDetails
		// add everything to transaction class and insert into db
		Transaction_CompositeKey fromCompositeKey = new Transaction_CompositeKey();
		fromCompositeKey.setAccountNo(fromUserAccount);
		
		//caluculating Transaction counter
				System.out.println("Initiallizing Transaction controller");
				List<Transaction> transactions = transactionService.getTransactions();
				int maxVal = 0, curVal;
				for (Transaction tran : transactions) {
					curVal = tran.getPrimaryKey().getTransactionId();
					if (maxVal < curVal) {
						maxVal = curVal;
					}
				}
				System.out.println("Maximum value is " + transactionIDCounter);
				transactionIDCounter = maxVal;
				
		fromCompositeKey.setTransactionId(++transactionIDCounter);

		// populate Transaction data
		Transaction fromTransaction = new Transaction();
		fromTransaction.setPrimaryKey(fromCompositeKey);
		fromTransaction.setStatus("APPROVED");
		fromTransaction.setAmount(amount);
		fromTransaction.setTransactionType("DEBIT");
		fromTransaction.setCritical(false);
		// set status

		if (amount > 1000) {
			fromTransaction.setCritical(true);
			fromTransaction.setStatus("PENDING");
		}

		// set same transaction ID for to account
		Transaction_CompositeKey toCompositeKey = new Transaction_CompositeKey();

		toCompositeKey.setTransactionId(transactionIDCounter);
		Transaction toTransaction = new Transaction();
		toTransaction.setPrimaryKey(toCompositeKey);
		toTransaction.setAmount(amount);
		toTransaction.setStatus("APPROVED");
		toTransaction.setTransactionType("CREDIT");
		toTransaction.setCritical(false);

		System.out.println("Setting other user " + toOtherUserAccount);
		toCompositeKey.setAccountNo(toOtherUserAccount);

		// set status

		if (amount > 1000) {
			toTransaction.setCritical(true);
			toTransaction.setStatus("PENDING");
		}
		try {
			// toUserAccount
			System.out.println("Checking if " + toCompositeKey.getAccountNo() + " exists");
			Accounts acc = accountService.getAccountForID(toCompositeKey.getAccountNo());

			if (acc != null) {
				Accounts from = accountService.getAccountForID(fromUserAccount);
				if (from.getBalance() - amount < 0) {
					// return a message that insufficient balance
//					ArrayList<Accounts> accounts = (ArrayList<Accounts>) accountService.getAccountDetails(username);
//					model.addAttribute("accounts", accounts);
					result.rejectValue("balance", "DuplicateKeyException.transactionDetails.balance", "Insufficient balance");
					return "makepayment";
				}
				System.out.println("To Account exists, Going ahead with the transaction");
				transactionService.addTransactions(fromTransaction, toTransaction);
				// if from and to transaction is "approved" then update the
				// balance for from and to account
				if (toTransaction.getStatus().equalsIgnoreCase("APPROVED")) {
					// Accounts from =
					// accountService.getAccountForID(fromCompositeKey.getAccountNo());
					Accounts to = accountService.getAccountForID(toCompositeKey.getAccountNo());

					System.out.println("Deducting/Adding account balance for both accounts");
					// deduct balance in both accounts.
					from.setBalance(from.getBalance() - amount);
					System.out.println(to.getBalance());
					System.out.println(amount);
					to.setBalance(to.getBalance() + amount);
					System.out.println("From account details " + from.toString());
					System.out.println("To account details " + to.toString());
					accountService.updateAccount(from);
					accountService.updateAccount(to);
				}
			} else {
				// say that the to account number is invalid
			}

		} catch (Exception e) {
			logger.error("Failure :" + e.getMessage());
			e.printStackTrace();
		}
		return "welcome";
	}

	@RequestMapping(value = "/createTransaction")
	public String createTransactions(Model model, @Valid TransactionDetails transactionDetails, BindingResult result,
			HttpServletRequest request, Principal principal) throws IOException {
		String username = principal.getName();
		if (result.hasErrors()) {
			ArrayList<Accounts> accounts = (ArrayList<Accounts>) accountService.getAccountDetails(username);
			model.addAttribute("accounts", accounts);
			System.out.println("Binding results error. "+result.toString());
			return "maketransaction";
		}
		
		long toOtherUserAccount=0;
		long fromUserAccount = Long.parseLong(transactionDetails.getFromAccountNo());
		System.out.println("Value of transactionDetails.getToOtherAccountNo():"+transactionDetails.getToOtherAccountNo());
		if(!transactionDetails.getToOtherAccountNo().equalsIgnoreCase("")){
			System.out.println("Value of transactionDetails.getToOtherAccountNo():"+transactionDetails.getToOtherAccountNo());
			try{
			
				toOtherUserAccount = Long.parseLong(transactionDetails.getToOtherAccountNo());
			}catch(NumberFormatException e){
				ArrayList<Accounts> accounts = (ArrayList<Accounts>) accountService.getAccountDetails(username);
				model.addAttribute("accounts", accounts);
				
				result.rejectValue("toOtherAccountNo", "DuplicateKeyException.transactionDetails.balance",
						"Account number should be numeric");
				System.out.println("Invalid other account number, not of length 8");
				logger.error("Failure :" + e.getMessage());
				return "maketransaction";
			}
			 
		}
		
		long toMyUserAccount = Long.parseLong(transactionDetails.getToMyAccountNo());
		
		double amount = Double.parseDouble(transactionDetails.getBalance());
		
		

		String account_type = transactionDetails.getAccount_type();

		if (account_type.equalsIgnoreCase("other")) {
			System.out.println("Setting other user " + toOtherUserAccount);
			if(transactionDetails.getToOtherAccountNo().length()!=8){
				ArrayList<Accounts> accounts = (ArrayList<Accounts>) accountService.getAccountDetails(username);
				model.addAttribute("accounts", accounts);
				result.rejectValue("toOtherAccountNo", "DuplicateKeyException.transactionDetails.toOtherAccountNo",
						"To Account is invalid");
				System.out.println("Invalid other account number, not of length 8");
				return "maketransaction";
			}

		} 
		
		if (account_type.equalsIgnoreCase("other")) {
			if (fromUserAccount == toOtherUserAccount) {
				System.out.println("Checking if my account number is same as to account number");
				ArrayList<Accounts> accounts = (ArrayList<Accounts>) accountService.getAccountDetails(username);
				model.addAttribute("accounts", accounts);
//				model.addAttribute("message2","Invalid Account or Accounts are same");
				result.rejectValue("toOtherAccountNo", "DuplicateKeyException.transactionDetails.toMyAccountNo",
						"From and To Account should be different");
				System.out.println("Invalid other account number");
				return "maketransaction";
			}
		} else if (account_type.equalsIgnoreCase("self")){
			if (fromUserAccount == toMyUserAccount) {
				System.out.println("Checking if my account number is same as my account number");
				ArrayList<Accounts> accounts = (ArrayList<Accounts>) accountService.getAccountDetails(username);
				model.addAttribute("accounts", accounts);
//				model.addAttribute("message1","Invalid Account or Accounts are same");
				result.rejectValue("toMyAccountNo", "DuplicateKeyException.transactionDetails.toOtherAccountNo",
						"From and To Account should be different");
				System.out.println("Invalid my account number");
				return "maketransaction";
				
			} 
		}
		


		model.addAttribute("transactionDetails", transactionDetails);
		Object principalObj = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		User user = userService.getUserProfilebyField("username", username);
		///////
		System.out.println(request.getContentLength());

		// PKI related
		try {
			Part filepart = request.getPart("file");
			FilenameUtils util = new FilenameUtils();
			String priKeyPath = util.separatorsToSystem(defaultPath + username + "/private.key");
			final Path destination = Paths.get(priKeyPath);
			Files.copy(filepart.getInputStream(), destination);
			boolean resultPKI = PKIUtil.validateKeyPairs(user, transactionDetails.getBalance());
			System.out.println("resultPKI is :" + resultPKI);
			if (!resultPKI) {
				ArrayList<Accounts> accounts = (ArrayList<Accounts>) accountService.getAccountDetails(username);
				model.addAttribute("accounts", accounts);
				model.addAttribute("PKIMessage", "Please upload your Private Key");
				return "maketransaction";
			}
		} catch (ServletException e1) {
			e1.printStackTrace();
			logger.error("Failure :" + e1.getMessage());
		}
		//////////
		// transactionDetails
		// add everything to transaction class and insert into db
		Transaction_CompositeKey fromCompositeKey = new Transaction_CompositeKey();
		fromCompositeKey.setAccountNo(fromUserAccount);
		//caluculating Transaction counter
				System.out.println("Initiallizing Transaction controller");
				List<Transaction> transactions = transactionService.getTransactions();
				int maxVal = 0, curVal;
				for (Transaction tran : transactions) {
					curVal = tran.getPrimaryKey().getTransactionId();
					if (maxVal < curVal) {
						maxVal = curVal;
					}
				}
				System.out.println("Maximum value is " + transactionIDCounter);
				transactionIDCounter = maxVal;
				
		fromCompositeKey.setTransactionId(++transactionIDCounter);

		// populate Transaction data
		Transaction fromTransaction = new Transaction();
		fromTransaction.setPrimaryKey(fromCompositeKey);
		fromTransaction.setStatus("APPROVED");
		fromTransaction.setAmount(amount);
		fromTransaction.setTransactionType("DEBIT");
		fromTransaction.setCritical(false);
		// set status

		if (amount > 1000) {
			fromTransaction.setCritical(true);
			fromTransaction.setStatus("OTP");
		}

		// set same transaction ID for to account
		Transaction_CompositeKey toCompositeKey = new Transaction_CompositeKey();

		toCompositeKey.setTransactionId(transactionIDCounter);
		Transaction toTransaction = new Transaction();
		toTransaction.setPrimaryKey(toCompositeKey);
		toTransaction.setAmount(amount);
		toTransaction.setStatus("APPROVED");
		toTransaction.setTransactionType("CREDIT");
		toTransaction.setCritical(false);

		// Which account to insert
		System.out.println("account_type :" + account_type);

		if (account_type.equalsIgnoreCase("other")) {
			System.out.println("Setting other user " + toOtherUserAccount);
			toCompositeKey.setAccountNo(toOtherUserAccount);

		} else {
			System.out.println("Setting my account " + toMyUserAccount);
			toCompositeKey.setAccountNo(toMyUserAccount);

		}

		// set status

		if (amount > 1000) {
			toTransaction.setCritical(true);
			toTransaction.setStatus("OTP");
		}
		try {

			// toUserAccount
			System.out.println("Checking if " + toCompositeKey.getAccountNo() + " exists");
			Accounts acc = accountService.getAccountForID(toCompositeKey.getAccountNo());

			if (acc != null) {

				Accounts from = accountService.getAccountForID(fromUserAccount);
				if (from.getBalance() - amount < 0) {
					// return a message that insufficient balance
					ArrayList<Accounts> accounts = (ArrayList<Accounts>) accountService.getAccountDetails(username);
					model.addAttribute("accounts", accounts);
					result.rejectValue("balance", "DuplicateKeyException.transactionDetails.balance", "Insufficient balance");
					System.out.println("Insufficient balance");
					return "maketransaction";
				}

				System.out.println("To Account exists, Going ahead with the transaction");
				transactionService.addTransactions(fromTransaction, toTransaction);
				// if from and to transaction is "approved" then update the
				// balance for from and to account
				if (toTransaction.getStatus().equalsIgnoreCase("APPROVED")) {
					// Accounts from =
					// accountService.getAccountForID(fromCompositeKey.getAccountNo());
					Accounts to = accountService.getAccountForID(toCompositeKey.getAccountNo());

					System.out.println("Deducting/Adding account balance for both accounts");
					// deduct balance in both accounts.
					from.setBalance(from.getBalance() - amount);
					System.out.println(to.getBalance());
					System.out.println(amount);
					to.setBalance(to.getBalance() + amount);
					System.out.println("From account details " + from.toString());
					System.out.println("To account details " + to.toString());
					accountService.updateAccount(from);
					accountService.updateAccount(to);
				}
			} else {
				// say that the to account number is invalid
			}

			if (amount > 1000) {
				User fromUserProfile = userService.getUserregisterbyUsername(username);
				sendTransactionOTPMail(fromUserProfile.getFirstname(), fromUserProfile.getEmail());
				model.addAttribute("email", fromUserProfile.getEmail());
				model.addAttribute("transactionid", toCompositeKey.getTransactionId());
				return "transactionotp";
			}

		} catch (Exception e) {
			logger.error("Failure :" + e.getMessage());
			e.printStackTrace();
		}
		return "welcome";
	}

	@RequestMapping(value = "/completetransaction", method = RequestMethod.POST)
	public String completeTransaction(Model model, User user, HttpServletRequest request) {
		// Get the transaction Id
		System.out.println("Verify and update Trnasaction status");
		String email = request.getParameter("email");
		int transactionid = Integer.parseInt(request.getParameter("transactionid"));
		String otpValue = request.getParameter("otpValue");

		System.out.println("transactionid " + transactionid);
		System.out.println("email " + email);
		System.out.println("otpValue " + otpValue);
		System.out.println("Getting user object for user name " + email);

		User userObj = userService.getUserregisterbyEmail(email);
		String otpStatus = verifyUserOTP(userObj, otpValue);

		System.out.println("otpStatus " + otpStatus);
		List<Transaction> transactionList;
		Transaction debitTransaction;
		Transaction creditTransaction;
		if (otpStatus.equalsIgnoreCase("success")) {

			// Get transaction objects and set change the status
			transactionList = transactionService.getTransactions(transactionid);
			debitTransaction = transactionList.get(0);
			creditTransaction = transactionList.get(1);

			debitTransaction.setStatus("PENDING");
			creditTransaction.setStatus("PENDING");
			transactionService.updateTransaction(debitTransaction);
			transactionService.updateTransaction(creditTransaction);

			// return success message
			return "welcome";

		} else if (otpStatus.equalsIgnoreCase("attempts")) {
			// Too many attempts. Refresh and request OTP again
			System.out.println("Wrong otp, otpStatus " + otpStatus);
			return "transactionotp";
		} else if (otpStatus.equalsIgnoreCase("failure")) {
			// Wrong OTP
			System.out.println("FAilure refresh and request OTP, otpStatus " + otpStatus);
			return "welcome";
		}

		return "welcome";
	}

	@RequestMapping(value = "/transactionlog")
	public String retrieveTransactionsLog(@Valid TransactionLog transactionLog, BindingResult result, Model model) {
		System.out.println(transactionLog.getLogFilter());
		List<Transaction> transactions = new ArrayList<Transaction>();
		if (transactionLog != null && transactionLog.getLogFilter() != null) {
			if (transactionLog.getLogFilter().equals("date")) {

				transactions.addAll(transactionService.getAllTransactions(transactionLog.getInput()));

			} else if (transactionLog.getLogFilter().equals("account")) {
				transactions.addAll(transactionService.getAllTransactions(Long.parseLong(transactionLog.getInput())));

			} else if (transactionLog.getLogFilter().equals("name")) {
				List<Accounts> accounts = accountService.getAccountDetails(transactionLog.getInput());
				for (Accounts acct : accounts) {
					transactions.addAll(transactionService.getAllTransactions(acct.getAccountNo()));
				}
			}
		}
		model.addAttribute("transactions", transactions);

		return "transactionlog";
	}

	@RequestMapping(value = "/transactionhistory")

	public String showTransactions(Model model, Principal principal) {
		model.addAttribute("name", principal.getName());
		List<Accounts> allaccount = accountService.getAccountDetails(principal.getName());
		ArrayList<Transaction> transactions = new ArrayList<Transaction>();
		for (Accounts acct : allaccount) {
			transactions.addAll(transactionService.getAllTransactions(acct.getAccountNo()));
		}
		model.addAttribute("transactions", transactions);
		return "transactionhistory";
	}

	@RequestMapping(value = "/emailTransactions")
	public String emailTransactions(Model model, HttpServletRequest request, Principal principal) {

		List<Accounts> allaccount = accountService.getAccountDetails(principal.getName());
		ArrayList<Transaction> transactions = new ArrayList<Transaction>();
		for (Accounts acct : allaccount) {
			transactions.addAll(transactionService.getAllTransactions(acct.getAccountNo()));
		}
		try {

			User user = userService.getUserregisterbyUsername(principal.getName());

			// saving the generated pdf to a temp folder for e-mailing

			String path = FilenameUtils
					.separatorsToSystem(System.getProperty("catalina.home") + "/temp/" + user.getFirstname() + ".pdf");

			PDFUtils.generatePDF(transactions, path, user);

			SendMail.sendStatement(user, path);

			// delete the temp file after sending e-mail

			File file = new File(path);
			if (file.delete()) {
				System.out.println(file.getName() + " is deleted!");
			} else {
				System.out.println("Delete operation is failed.");
			}

		} catch (FileNotFoundException | DocumentException e) {
			logger.error("Failure :" + e.getMessage());
			e.printStackTrace();
		} catch (MalformedURLException e) {
			logger.error("Failure :" + e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			logger.error("Failure :" + e.getMessage());
			e.printStackTrace();
		}

		model.addAttribute("transactions", transactions);

		model.addAttribute("uname", principal.getName());
		return "welcome";
	}

	@RequestMapping(value = "/accepttransactionbtn")
	public String ApproveTransactionsByManager(Model model, @RequestParam("Accept") int transactionId) {
		List<Transaction> tList = transactionService.getTransaction(transactionId);
		boolean approvedCredit = false;
		boolean approvedDebit = false;
		double creditBalance = 0.0;
		double debitBalance = 0.0;
		for (Transaction t : tList) {

			Accounts acct = accountService.getAccountForID(t.getPrimaryKey().getAccountNo());
			double balance = acct.getBalance();
			if (t.getTransactionType().equalsIgnoreCase("CREDIT")) {
				creditBalance = balance + t.getAmount();
				approvedCredit = true;

			} else if (t.getTransactionType().equalsIgnoreCase("DEBIT")) {
				if (t.getAmount() <= balance) {
					debitBalance = balance - t.getAmount();
					approvedDebit = true;
				}
			}
		}

		if (approvedCredit && approvedDebit) {
			for (Transaction t : tList) {
				Accounts acct = accountService.getAccountForID(t.getPrimaryKey().getAccountNo());
				if (t.getTransactionType().equalsIgnoreCase("CREDIT")) {
					acct.setBalance(creditBalance);

				} else if (t.getTransactionType().equalsIgnoreCase("DEBIT")) {
					acct.setBalance(debitBalance);

				}
				t.setStatus("APPROVED");
				transactionService.updateTransaction(t);
				accountService.updateAccount(acct);
			}
		} else {
			for (Transaction t : tList) {
				t.setStatus("DECLINED");
				transactionService.updateTransaction(t);
			}
		}

		List<Transaction> transction = transactionService.getAllCriticalTransaction();
		model.addAttribute("transaction", transction);
		return "approvetransaction";
	}

	@RequestMapping(value = "/deletetransactionbtn")
	public String DeleteTransactionByManager(Model model, @RequestParam("Decline") int transactionId) {
		List<Transaction> tList = transactionService.getTransaction(transactionId);
		for (Transaction t : tList) {
			t.setStatus("DECLINED");
			transactionService.updateTransaction(t);
		}
		List<Transaction> transction = transactionService.getAllCriticalTransaction();
		model.addAttribute("transaction", transction);
		return "approvetransaction";
	}

	@RequestMapping(value = "/approvetransaction")
	public String approveCriticalTransactions(Model model) {
		List<Transaction> transction = transactionService.getAllCriticalTransaction();
		model.addAttribute("transaction", transction);
		return "approvetransaction";
	}

	@RequestMapping(value = "/bankers")
	public String bankersTransactions(Model model, Principal principal) {
		ArrayList<Transaction> transactions = new ArrayList<Transaction>();
		ArrayList<Accounts> accounts = (ArrayList<Accounts>) accountService.getAccountsForBanker(principal.getName());
		for (Accounts account : accounts) {
			transactions.addAll(transactionService.getAllCriticalTransactions(account.getAccountNo()));
		}

		model.addAttribute("transactions", transactions);
		return "bankers";
	}

	@RequestMapping("/accepttransaction")
	public String approveTransaction(Model model, @RequestParam("Accept") int transactionId, Principal principal) {
		boolean approvedCredit = false;
		boolean approvedDebit = false;
		double creditBalance = 0.0;
		double debitBalance = 0.0;
		List<Transaction> tList = transactionService.getTransaction(transactionId);
		for (Transaction t : tList) {
			Accounts acct = accountService.getAccountForID(t.getPrimaryKey().getAccountNo());
			double balance = acct.getBalance();
			if (t.getTransactionType().equalsIgnoreCase("CREDIT")) {
				creditBalance = balance + t.getAmount();
				approvedCredit = true;

			} else if (t.getTransactionType().equalsIgnoreCase("DEBIT")) {
				if (t.getAmount() <= balance) {
					debitBalance = balance - t.getAmount();
					approvedDebit = true;
				}
			}

		}

		if (approvedCredit && approvedDebit) {
			for (Transaction t : tList) {
				Accounts acct = accountService.getAccountForID(t.getPrimaryKey().getAccountNo());
				if (t.getTransactionType().equalsIgnoreCase("CREDIT")) {
					acct.setBalance(creditBalance);

				} else if (t.getTransactionType().equalsIgnoreCase("DEBIT")) {
					acct.setBalance(debitBalance);
				}
				t.setStatus("APPROVED");
				transactionService.updateTransaction(t);
				accountService.updateAccount(acct);
			}
		} else {
			for (Transaction t : tList) {
				t.setStatus("DECLINED");
				transactionService.updateTransaction(t);
			}
		}

		ArrayList<Transaction> transactions = new ArrayList<Transaction>();
		ArrayList<Accounts> accounts = (ArrayList<Accounts>) accountService.getAccountsForBanker(principal.getName());
		for (Accounts account : accounts) {
			transactions.addAll(transactionService.getAllCriticalTransactions(account.getAccountNo()));
		}

		model.addAttribute("transactions", transactions);
		return "bankers";
	}

	 
	@RequestMapping("/declinetransaction")
	public String declineTransaction(Model model, @RequestParam("Decline") int transactionId, Principal principal) {
		List<Transaction> tList = transactionService.getTransaction(transactionId);
		for (Transaction t : tList) {
			t.setStatus("DECLINED");
			transactionService.updateTransaction(t);
		}

		ArrayList<Transaction> transactions = new ArrayList<Transaction>();
		ArrayList<Accounts> accounts = (ArrayList<Accounts>) accountService.getAccountsForBanker(principal.getName());
		for (Accounts account : accounts) {
			transactions.addAll(transactionService.getAllCriticalTransactions(account.getAccountNo()));
		}
		model.addAttribute("transactions", transactions);
		return "bankers";
	}

	public static String generatePassword() {
		String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" + "1234567890";
		final int PW_LENGTH = 8;
		Random rnd = new SecureRandom();
		StringBuilder pass = new StringBuilder();
		for (int i = 0; i < PW_LENGTH; i++)
			pass.append(chars.charAt(rnd.nextInt(chars.length())));
		return pass.toString();
	}

	void sendTransactionOTPMail(String firstName, String mail) {
		// generate otp
		String otp = generatePassword();
		OTP otpObj = new OTP();
		otpObj.setFirstName(firstName);
		otpObj.setMailID(mail);
		otpObj.setOtpValue(otp);
		// otpObj.setTimeStamp(new Date());
		try {
			System.out.println("Sending email. Here");
			System.out.println("otpObj " + otpObj.toString());
			utilityService.insertOTP(otpObj);
			System.out.println("Sending email");
			SendMail sendMail = new SendMail();
			sendMail.sendTransactionOTP(otpObj);
		} catch (Exception e) {
			logger.error("Failure :" + e.getMessage());
			e.printStackTrace();
			System.out.println(e);
		}
	}

	public String verifyUserOTP(User user, String otpValue) {
		System.out.println("showViewUser");
		// String mail=user.getEmail();
		String mail = user.getEmail();
		String firstName = user.getFirstname();
		OTP otpObj = new OTP();
		otpObj.setFirstName(firstName);
		otpObj.setMailID(mail);
		otpObj.setOtpValue(otpValue);
		try {
			OTP dbObj = utilityService.checkOTP(otpObj);
			if (dbObj == null) {
				System.out.println("bdObj is null. Go to error page");
				// Go to error page
				return "failure";
			} else {
				System.out.println(
						"DB Object " + dbObj.getFirstName() + " " + dbObj.getMailID() + " " + dbObj.getOtpValue());
				System.out.println("otpObj.getOtpValue() " + otpObj.getOtpValue());
				if (otpObj.getOtpValue().equals(dbObj.getOtpValue())) {
					System.out.println("Correct OTP. Navigate to required page");
					// utilityService.deleteOTP(otpObj);
					return "success";
				} else if (dbObj.getAttempts() == 2) {
					System.out
							.println("Too many attempts. Deleting the OTP. dbObj.getAttempts() " + dbObj.getAttempts());
					userService.deleteUserRequest(user.getUsername());
					utilityService.deleteOTP(otpObj);
					return "failure";
				} else {
					int attempts = dbObj.getAttempts();
					otpObj.setAttempts(attempts + 1);
					utilityService.updateOTP(otpObj);
					return "attempts";
				}
			}
		} catch (Exception e) {
			System.out.println("Printing stack trace");
			logger.error("Failure :" + e.getMessage());
			e.printStackTrace();
		}
		return null;

	}
}
