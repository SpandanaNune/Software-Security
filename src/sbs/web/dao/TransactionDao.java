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
	public List<Transaction> getAllTransactions(long accountNo){
		return session().createQuery("from Transaction where accountNo="+accountNo).list();
	}
	@SuppressWarnings("unchecked")
	public List<Transaction> getAllTransactions(String date){
		return session().createQuery("from Transaction where DATE(createdDate)='"+date+"'").list();
	}
	
	@SuppressWarnings("unchecked")
	public List<Transaction> getAllCriticalTransaction(){
		return session().createQuery("from Transaction where isCritical = 1").list();
	}

	//check if we need to add transactional
	public void addTransactions(Transaction fromTransaction, Transaction toTransaction) {
		session().save(fromTransaction);
		System.out.println(fromTransaction.getPrimaryKey().getTransactionId());
		System.out.println(toTransaction.getPrimaryKey().getTransactionId());
		session().save(toTransaction);
	}
	
}
