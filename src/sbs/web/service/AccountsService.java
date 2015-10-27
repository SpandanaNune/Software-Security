package sbs.web.service;

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

	public List<Accounts> getAccountDetails(String username) {
		return accountsDao.getAccountDetails(username);
	}



	public Accounts getMerchantAccountDetails(String username) {
		return accountsDao.getMerchantAccountDetails(username);
	}

	public Accounts getAccountForID(long toUserAccount) {
		return accountsDao.getAccountForID(toUserAccount);
	}

	public void updateAccount(Accounts from) {
		accountsDao.updateAccount(from);
	}

	public List<Accounts> getAccountsForBanker(String bankerName) {
		return accountsDao.getAccountDetailsForBanker(bankerName);

	}

}
