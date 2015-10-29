package sbs.web.controllers;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import sbs.web.models.Accounts;
import sbs.web.models.TransactionDetails;
import sbs.web.models.User;
import sbs.web.service.AccountsService;
import sbs.web.service.UserService;

@Controller
public class AccountsController {

		AccountsService accountsService;
		UserService userService;
	
		@Autowired
		public void setAccountsService(AccountsService accountsService) {
			this.accountsService = accountsService;
		}
		
		@Autowired
		public void setUserService(UserService userService) {
			this.userService = userService;
		}

		@RequestMapping(value="/openDebitCreditTransaction")
		public String openDebitCreditTransactions(Model model, Principal principal){
			String username;
			username=principal.getName();
			
			model.addAttribute("transactionDetails", new TransactionDetails());
			
			try{
				ArrayList<Accounts> accounts = (ArrayList<Accounts>)accountsService.getAccountDetails(username);
				System.out.println("User: "+username+" has "+accounts.size()+" accounts");
				model.addAttribute("accounts",accounts);
				System.out.println(accounts.get(0).getAccountNo());
				System.out.println(accounts.get(1).getAccountNo());
				model.addAttribute("accounts", accounts);
				
			}catch ( Exception e) {
				e.printStackTrace();
			}
			System.out.println("Go to Make Transaction Page");
			return "debitcredit";
		}
		
		@RequestMapping(value="/openTransaction")
		public String openTransactions(Model model, Principal principal){
			String username;
			username=principal.getName();
			System.out.println(username);
//			username="arjun";
			model.addAttribute("transactionDetails", new TransactionDetails());
			try{
				
				ArrayList<Accounts> accounts = (ArrayList<Accounts>)accountsService.getAccountDetails(username);
				System.out.println("User: "+username+" has "+accounts.size()+" accounts");
				model.addAttribute("accounts",accounts);
				System.out.println(accounts.get(0).getAccountNo());
				System.out.println(accounts.get(1).getAccountNo());
				model.addAttribute("accounts", accounts);
				
			}catch ( Exception e) {
				e.printStackTrace();
			}
			System.out.println("Go to Make Transaction Page");
			return "maketransaction";
		}
		
		@RequestMapping(value="/openMakePayment")
		public String openMakePayment(Model model, Principal principal){
			String username;
			username=principal.getName();
//			username="arjun";
			
			model.addAttribute("transactionDetails", new TransactionDetails());
			
			try{
				
				ArrayList<Accounts> accounts = (ArrayList<Accounts>)accountsService.getAccountDetails(username);
				System.out.println("User: "+username+" has "+accounts.size()+" accounts");
				model.addAttribute("accounts",accounts);
				System.out.println(accounts.get(0).getAccountNo());
				System.out.println(accounts.get(1).getAccountNo());
				model.addAttribute("accounts", accounts);
				
				// get all merchant account 
				List<User> userList = userService.getAllMerchantAccounts();
				
				System.out.println("Number of Merchants: "+userList.size());
				
				ArrayList<Accounts> toaccounts= new ArrayList<Accounts>();
				Accounts merchantAccount; 
				for(User user: userList){
					merchantAccount=accountsService.getMerchantAccountDetails(user.getUsername());
					System.out.println("Got account for "+merchantAccount.getAccountNo());
					toaccounts.add(merchantAccount);
				}
				System.out.println("Merchants account count: "+toaccounts.size());
				model.addAttribute("toaccounts", toaccounts);
				
			}catch ( Exception e) {
				e.printStackTrace();
			}
			System.out.println("Go to Make Payment Page");
			return "makepayment";
		}

		@RequestMapping(value="/openMerchantTransaction")
	    public String openMerchantTransaction(Model model, Principal principal){
	        String username;
	        username=principal.getName();
//	        username="arjun";
	        
	        model.addAttribute("transactionDetails", new TransactionDetails());
	        
	        try{
	            
	        	ArrayList<Accounts> accounts = (ArrayList<Accounts>) accountsService.getAccountDetails(username);
	            model.addAttribute("accounts",accounts);
	            
	        }catch ( Exception e) {
	            e.printStackTrace();
	        }
	        System.out.println("Go to Make Transaction Page");
	        return "merchanttransaction";
	    }
		
}
