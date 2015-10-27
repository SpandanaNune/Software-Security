package sbs.web.controllers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.security.Principal;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

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

@Controller
public class TransactionController {

	static int transactionIDCounter = 500;
	TransactionService transactionService;
	AccountsService accountService;
	UtilityService utilityService;
	private UserService userService;

	private static String defaultPath = System.getProperty("catalina.home") + "/users_keys/";

	@Autowired
	public void setTransactionService(TransactionService transactionService) {
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
	public String debitCreditToAccount(Model model, TransactionDetails transactionDetails) {

		long accountNumber = transactionDetails.getFromAccountNo();
		String transactionType = transactionDetails.getTransaction_type();
		double amount = transactionDetails.getBalance();

		Transaction_CompositeKey compositeKey = new Transaction_CompositeKey();
		compositeKey.setAccountNo(accountNumber);
		compositeKey.setTransactionId(++transactionIDCounter);

		// populate Transaction data
		Transaction transaction = new Transaction();
		transaction.setPrimaryKey(compositeKey);
		transaction.setStatus("APPROVED");
		transaction.setAmount(amount);
		transaction.setTransactionType(transactionType);
		transaction.setCritical(false);

		// set status

		// if (amount > 1000) {
		// transaction.setCritical(true);
		// transaction.setStatus("PENDING");
		// }

		try {
			System.out.println("Checking if " + compositeKey.getAccountNo() + " exists..");
			// validate account- it valid since it is provied with the drop down
			// check balance
			Accounts from = accountService.getAccountForID(accountNumber);
			if (transactionType.equalsIgnoreCase("DEBIT")) {
				// deduct and update
				if (from.getBalance() - amount < 0) {
					// return a message that insufficient balance
					return "welcome";
				}
				from.setBalance(from.getBalance() - amount);
			} else {
				from.setBalance(from.getBalance() + amount);
			}
			transactionService.saveTransaction(transaction);
			accountService.updateAccount(from);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "welcome";
	}
	// ************Pankaj*************

	@RequestMapping(value = "/createMerchnatTransaction")
	public String createMerchantTransactions(Model model, TransactionDetails transactionDetails,
			HttpServletRequest request) throws IOException {
		model.addAttribute("transactionDetails", transactionDetails);

		System.out.println("check1");
		long fromUserAccount = transactionDetails.getFromAccountNo();
		long toOtherUserAccount = transactionDetails.getToOtherAccountNo();
		double amount = transactionDetails.getBalance();

		// transactionDetails
		// add everything to transaction class and insert into db
		Transaction_CompositeKey fromCompositeKey = new Transaction_CompositeKey();
		fromCompositeKey.setAccountNo(fromUserAccount);
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
					return "welcome";
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
		}
		return "welcome";

	}

	@RequestMapping(value = "/makePaymentTransaction")
	public String createPaymentTransactions(Model model, TransactionDetails transactionDetails,
			HttpServletRequest request) throws IOException {
		model.addAttribute("transactionDetails", transactionDetails);

		long fromUserAccount = transactionDetails.getFromAccountNo();
		long toOtherUserAccount = transactionDetails.getToOtherAccountNo();
		double amount = transactionDetails.getBalance();

		// transactionDetails
		// add everything to transaction class and insert into db
		Transaction_CompositeKey fromCompositeKey = new Transaction_CompositeKey();
		fromCompositeKey.setAccountNo(fromUserAccount);
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
					return "welcome";
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
			e.printStackTrace();
		}
		return "welcome";
	}

	@RequestMapping(value = "/createTransaction")
	public String createTransactions(Model model, TransactionDetails transactionDetails, HttpServletRequest request,Principal principal)
			throws IOException {
		model.addAttribute("transactionDetails", transactionDetails);
		Object principalObj = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = principal.getName();

		///////
		System.out.println(request.getContentLength());

		// PKI related
		// try {
		// Part filepart = request.getPart("file");
		// String keyPath = defaultPath + username + "/private.key";
		// final Path destination = Paths.get(keyPath);
		// Files.copy(filepart.getInputStream(), destination);
		// //validateKeyPairs(User user, String message);
		//
		// } catch (ServletException e1) {
		// // TODO Auto-generated catch block
		// e1.printStackTrace();
		// }

		//////////
		long fromUserAccount = transactionDetails.getFromAccountNo();
		long toMyUserAccount = transactionDetails.getToMyAccountNo();
		long toOtherUserAccount = transactionDetails.getToOtherAccountNo();
		double amount = transactionDetails.getBalance();
		String account_type = transactionDetails.getAccount_type();

		// transactionDetails
		// add everything to transaction class and insert into db
		Transaction_CompositeKey fromCompositeKey = new Transaction_CompositeKey();
		fromCompositeKey.setAccountNo(fromUserAccount);
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
			System.out.println("Setting my account " + toOtherUserAccount);
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
					return "welcome";
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

			if(amount>1000){
				User fromUserProfile = userService.getUserregisterbyUsername(username);
				sendTransactionOTPMail(fromUserProfile.getFirstname(), fromUserProfile.getEmail());
				model.addAttribute("email", fromUserProfile.getEmail());
				model.addAttribute("transactionid", toCompositeKey.getTransactionId());
				return "transactionotp";
			}
			
		} catch (Exception e) {
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
	public String showTransactions(Model model) {
		model.addAttribute("name", "swetha");
		ArrayList<Transaction> transactions = (ArrayList<Transaction>) transactionService.getAllTransactions(1000);
		model.addAttribute("transactions", transactions);
		return "transactionhistory";
	}

	@RequestMapping(value = "/downloadTransactions")
	public String downloadTransactions(Model model, HttpServletRequest request) {
		System.out.println("Download Transaction History");
		ArrayList<Transaction> transactions = (ArrayList<Transaction>) transactionService.getAllTransactions(1234);
		try {
			User user = new User();
			user.setEmail("sswetha2809@gmail.com");
			user.setFirstname("swetha");
			user.setLastname("swaminathan");
			// user.setUsername("sswetha2809");
			String home = System.getProperty("user.home");
			String filePath = home + "\\Downloads\\" + user.getFirstname() + ".pdf";
			PDFUtils.generatePDF(transactions, filePath);

		} catch (FileNotFoundException | DocumentException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		model.addAttribute("transactions", transactions);

		return "transactionhistory";
	}

	@RequestMapping(value = "/emailTransactions")
	public String emailTransactions(Model model, HttpServletRequest request) {
		System.out.println("Mail Transaction History");
		ArrayList<Transaction> transactions = (ArrayList<Transaction>) transactionService.getAllTransactions(1000);
		try {

			User user = new User();
			user.setEmail("sswetha2809@gmail.com");
			user.setFirstname("swetha");
			user.setLastname("swaminathan");
			// saving the generated pdf to a temp folder for e-mailing
			String path = System.getProperty("catalina.home") + "\\temp\\" + user.getFirstname() + ".pdf";
			PDFUtils.generatePDF(transactions, path);

			SendMail.sendStatement(user, path);

			// delete the temp file after sending e-mail

			File file = new File(path);
			if (file.delete()) {
				System.out.println(file.getName() + " is deleted!");
			} else {
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
	public String ApproveTransactionsByManager(Model model, @RequestParam("Accept") int transactionId) {
		boolean approved = false;
		Transaction t = (Transaction) transactionService.getTransaction(transactionId);
		System.out.println(t);
		t.setStatus("APPROVED");
		Accounts acct = accountService.getAccountForID(t.getPrimaryKey().getAccountNo());
		double balance = acct.getBalance();
		if (t.getTransactionType().equalsIgnoreCase("CREDIT")) {
			acct.setBalance(balance + t.getAmount());
			approved = true;

		} else if (t.getTransactionType().equalsIgnoreCase("DEBIT")) {
			if (t.getAmount() <= balance) {
				acct.setBalance(balance - t.getAmount());
				approved = true;
			}
		}

		if (approved) {
			transactionService.updateTransaction(t);
			accountService.updateAccount(acct);
		}
		List<Transaction> transction = transactionService.getAllCriticalTransaction();
		model.addAttribute("transaction", transction);
		return "approvetransaction";
	}

	@RequestMapping(value = "/deletetransactionbtn")
	public String DeleteTransactionByManager(Model model, @RequestParam("Accept") int transactionId) {
		Transaction t = (Transaction) transactionService.getTransaction(transactionId);
		t.setStatus("DECLINED");
		transactionService.updateTransaction(t);
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
	public String bankersTransactions(Model model,Principal principal) {
		ArrayList<Transaction> transactions = new ArrayList<Transaction>();
		ArrayList<Accounts> accounts = (ArrayList<Accounts>) accountService.getAccountsForBanker(principal.getName());
		for (Accounts account : accounts) {
			transactions.addAll(transactionService.getAllCriticalTransactions(account.getAccountNo()));
		}

		model.addAttribute("transactions", transactions);
		return "bankers";
	}

	@RequestMapping("/accepttransaction")
	public String approveTransaction(Model model, @RequestParam("Accept") int transactionId) {
		boolean approved = false;
		Transaction t = (Transaction) transactionService.getTransaction(transactionId);
		System.out.println(t);
		t.setStatus("APPROVED");
		Accounts acct = accountService.getAccountForID(t.getPrimaryKey().getAccountNo());
		double balance = acct.getBalance();
		if (t.getTransactionType().equalsIgnoreCase("CREDIT")) {
			acct.setBalance(balance + t.getAmount());
			approved = true;

		} else if (t.getTransactionType().equalsIgnoreCase("DEBIT")) {
			if (t.getAmount() <= balance) {
				acct.setBalance(balance - t.getAmount());
				approved = true;
			}
		}

		if (approved) {
			transactionService.updateTransaction(t);
			accountService.updateAccount(acct);
		}
		ArrayList<Transaction> transactions = new ArrayList<Transaction>();
		ArrayList<Accounts> accounts = (ArrayList<Accounts>) accountService.getAccountsForBanker("banker");
		for (Accounts account : accounts) {
			transactions.addAll(transactionService.getAllCriticalTransactions(account.getAccountNo()));
		}

		model.addAttribute("transactions", transactions);
		return "bankers";

	}

	@RequestMapping("/declinetransaction")
	public String declineTransaction(Model model, @RequestParam("Decline") int transactionId) {
		Transaction t = (Transaction) transactionService.getTransaction(transactionId);
		System.out.println(t);
		t.setStatus("DECLINED");
		transactionService.updateTransaction(t);

		ArrayList<Transaction> transactions = new ArrayList<Transaction>();
		ArrayList<Accounts> accounts = (ArrayList<Accounts>) accountService.getAccountsForBanker("banker");
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
			e.printStackTrace();
		}
		return null;

	}
}
