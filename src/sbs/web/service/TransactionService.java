package sbs.web.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sbs.web.dao.TransactionDao;
import sbs.web.models.Transaction;

@Service("transactionService")
public class TransactionService {

	private TransactionDao transactionDao;

	@Autowired
	public void setTransactionDao(TransactionDao transactionDao) {
		this.transactionDao = transactionDao;
	}

	public List<Transaction> getAllTransactions(long accountNo) {
		return transactionDao.getAllTransactions(accountNo);
	}

	public List<Transaction> getAllTransactions(String date) {
		return transactionDao.getAllTransactions(date);
	}

	public List<Transaction> getAllCriticalTransaction() {
		return transactionDao.getAllCriticalTransaction();
	}

	public List<Transaction> getAllCriticalTransactions(long accountNo) {
		return transactionDao.getAllCriticalTransactionForAccount(accountNo);
	}

	public void addTransactions(Transaction fromTransaction, Transaction toTransaction) {
		transactionDao.addTransactions(fromTransaction, toTransaction);

	}

	public List<Transaction> getTransaction(int transactionId) {
		return transactionDao.getTransaction(transactionId);
	}

	public void updateTransaction(Transaction t) {
		transactionDao.updateTransaction(t);

	}

	public void saveTransaction(Transaction transaction) {
		transactionDao.saveTransaction(transaction);
		
	}

	public List<Transaction> getTransactions(int transactionid) {
		return transactionDao.getTransactions(transactionid);
	}

	public List<Transaction> getTransactions() {
		return transactionDao.getTransactions();
	}

}
