package sbs.web.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import sbs.web.models.Transaction;

@Transactional
@Component("transactionDao")
public class TransactionDao {

	@Autowired
	private SessionFactory sessionFactory;
	
	public Session session(){
		return sessionFactory.getCurrentSession();
	}
	
	@Transactional
	public void createTransaction(Transaction transaction){
		session().save(transaction);
	}
	
	@SuppressWarnings("unchecked")
	public List<Transaction> getAllTransactions(int accountNo){
		return session().createQuery("from Transaction where accountNo="+accountNo).list();
	}
	
	public List<Transaction> getAllCriticalTransaction(){
		return session().createQuery("from Transaction where isCritical = 1").list();
	}

	//check if we need to add transactional
	public void addTransactions(Transaction fromTransaction, Transaction toTransaction) {
		session().save(fromTransaction);
		System.out.println(fromTransaction.getPrimaryKey().getTransactionID());
		System.out.println(toTransaction.getPrimaryKey().getTransactionID());
		session().save(toTransaction);
	}
	
}
