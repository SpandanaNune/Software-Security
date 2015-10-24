package sbs.web.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sbs.web.dao.AccountsDao;
import sbs.web.models.Accounts;

@Service("accountsService")
public class AccountsService {

	private AccountsDao accountsDao;
	
	@Autowired
	public void setAccountsDao(AccountsDao accountsDao) {
		this.accountsDao = accountsDao;
	}


	//add this method in account service
		public List<Accounts> getAccountDetails(String username) {
			return accountsDao.getAccountDetails(username);
			
		}

}
