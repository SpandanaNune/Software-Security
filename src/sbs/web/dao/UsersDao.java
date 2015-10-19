package sbs.web.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


import sbs.web.models.User;

import sbs.web.models.Authorities;

import sbs.web.models.Users;

@Transactional
@Component("usersDao")
public class UsersDao {

	@Autowired
	private SessionFactory sessionFactory;

	public Session session() {
		return sessionFactory.getCurrentSession();
	}

	@Transactional

	public void createUser(User user) {
		session().saveOrUpdate(user);
	}
	
	public void setAuthority(Authorities auth){
		session().saveOrUpdate(auth);
	}
	
	public void userActivation(Users users) {
		session().saveOrUpdate(users);
	}

	@SuppressWarnings("unchecked")
	public List<User> getAllUsers() {
		return session().createQuery("from User").list();
	}

	public Object validateUser(User user) {
//		org.hibernate.Query query = session().createQuery("from User where username = '" + user.getUsername() + "'");
//		return query.uniqueResult();
		return null;
	}

	public Object getUserbyUsername(String username) {
		org.hibernate.Query query = session().createQuery("from Users where username = '" + username + "'");
		return query.uniqueResult();
	}
	
	public Object getUserbyField(String field, String value) {
		org.hibernate.Query query = session().createQuery("from Users where " + field + "='" + value + "'");
		return query.uniqueResult();
	}
	
	public Object getUserregisterbyUsername(String username) {
		org.hibernate.Query query = session().createQuery("from User where username = '" + username + "'");
		return query.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	public List<User> getAllNewUsers() {
		return session().createQuery("from User where isnewuser = 1").list();
	}

	public void deleteUserRequest(String username) {
		
		User user = (User)getUserregisterbyUsername(username);
			session().delete(user);
		
//		String hql = "UPDATE User set isnewuser = :isnewuser_ " + "WHERE username = :username_";
//		Query query = session().createQuery(hql);
//		query.setParameter("isnewuser_", false);
//		query.setParameter("username_", username);
//		int result = query.executeUpdate();
//		System.out.println("Rows affected: " + result);
	}
	
	public List<User> getAllActiveUsers(){
		return session().createQuery("from User where canlogin = 1").list();	
	}
	
	
//	public List<User> getAllActiveUsers(){
//		String hql = "from User ur, Users us where ur.username = us.username and us.enabled = 1";
//		return session().createQuery(hql).list();	
//	}

}
