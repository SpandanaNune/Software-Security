package sbs.web.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import sbs.web.models.OTP;

@Transactional
@Component("utilityDao")
public class UtilityDao {
	@Autowired
	private SessionFactory sessionFactory;
	
	public Session session(){
		return sessionFactory.getCurrentSession();
	}

	public void insertOTP(OTP otp) {
		session().saveOrUpdate(otp);
	}
	
	public OTP checkOTP(OTP otpObj) {
		org.hibernate.Query query = session().createQuery("from OTP where mailID = '"+otpObj.getMailID()+"'");
		return (OTP) query.uniqueResult();
		
	}

	public void updateOTP(OTP otpObj) {
		String hql = "UPDATE OTP set attempts = :attempts "  + 
	             "WHERE mailID = :mailID";
		org.hibernate.Query query = session().createQuery(hql);
		query.setParameter("attempts", otpObj.getAttempts());
		query.setParameter("mailID", otpObj.getMailID());
		query.executeUpdate();
	}
	public void deleteOTP(OTP otpObj) {
		System.out.println("Too many attempts. Deleting the OTP");
		//delete the entry in database
		String hql = "DELETE FROM OTP "  + 
	             "WHERE mailID = :mailID";
		org.hibernate.Query query = session().createQuery(hql);
		query.setParameter("mailID",  otpObj.getMailID());
		query.executeUpdate();
	}
	
}
