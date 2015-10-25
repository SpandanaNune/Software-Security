package sbs.web.controllers;

import java.security.Principal;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import sbs.web.models.Accounts;
import sbs.web.models.TransactionDetails;
import sbs.web.service.AccountsService;
import sbs.web.service.TransactionService;

@Controller
public class AccountsController {

		AccountsService accountsService;
	
		@Autowired
		public void setAccountsService(AccountsService accountsService) {
			this.accountsService = accountsService;
		}

		@RequestMapping(value="/openDebitCreditTransaction")
		public String openDebitCreditTransactions(Model model, Principal principal){
			String username;
//			username=principal.getName();
			username="arjun";
			
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
//			username=principal.getName();
			username="arjun";
			
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
		
		
}
