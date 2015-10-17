package sbs.web.controllers;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import sbs.web.models.Accounts;
import sbs.web.service.AccountsService;
import sbs.web.service.TransactionService;

@Controller
public class AccountsController {

		AccountsService accountsService;
	
		@Autowired
		public void setAccountsService(AccountsService accountsService) {
			this.accountsService = accountsService;
		}

		@RequestMapping(value="/getAccountDetails")
		public String getAccountDetails(Model model) {	
			System.out.println("Inside getAccountDetails");
			String username="mallikarjun";
			try{
				
				ArrayList<Accounts> accounts = (ArrayList<Accounts>)accountsService.getAccountDetails(username);
				System.out.println("User: "+username+" has "+accounts.size()+" accounts");
				model.addAttribute("accounts",accounts);
				System.out.println(accounts.get(0).getAccountNo());
				System.out.println(accounts.get(1).getAccountNo());
			}catch ( Exception e) {
				e.printStackTrace();
			}
			return "accountdetails";
			
		}
}
