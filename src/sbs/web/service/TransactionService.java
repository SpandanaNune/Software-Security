package sbs.web.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sbs.web.dao.TransactionDao;
import sbs.web.models.Accounts;
import sbs.web.models.Transaction;

@Service("transactionService")
public class TransactionService {
	
	private TransactionDao transactionDao;
	
	@Autowired
	public void setTransactionDao(TransactionDao transactionDao) {
		this.transactionDao = transactionDao;
	}
	
	public List<Transaction> getAllTransactions(int accountNo){
		return transactionDao.getAllTransactions(accountNo);
	}

	public void addTransactions(Transaction fromTransaction, Transaction toTransaction) {
		transactionDao.addTransactions(fromTransaction, toTransaction);
		
	}
	

}
