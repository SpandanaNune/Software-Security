package sbs.web.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import sbs.web.models.Accounts;


@Transactional
@Component("accountsDao")
public class AccountsDao {

	@Autowired
	private SessionFactory sessionFactory;
	
	public Session session(){
		return sessionFactory.getCurrentSession();
	}
		@SuppressWarnings("unchecked")
		public List<Accounts> getAccountDetails(String username) {
			return session().createQuery("from Accounts where username = '"+username+"'").list();
		}
		
		
		
		
		@SuppressWarnings("unchecked")
		public List<Accounts> getAccountDetailsForBanker(String bankerName) {
			return session().createQuery("from Accounts where bankername = '"+bankerName+"'").list();
		}
		
		public Accounts getAccountForID(long toUserAccount) {
            return (Accounts) session().createQuery("from Accounts where accountNo = "+toUserAccount).uniqueResult();
        }
        public void updateAccount(Accounts from) {
             session().saveOrUpdate(from);
        }
		public Accounts getMerchantAccountDetails(String username) {
			return (Accounts) session().createQuery("from Accounts where username = '"+username+"'").uniqueResult();
		}
        
       
		
}
