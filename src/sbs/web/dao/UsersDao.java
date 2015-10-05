package sbs.web.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import sbs.web.models.User;
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
		// user.setEnabled(true);
		session().save(user);
	}
	
	public void userActivation(Users users) {
		session().save(users);
	}

	@SuppressWarnings("unchecked")
	public List<User> getAllUsers() {
		return session().createQuery("from User").list();
	}

	public Object validateUser(User user) {
		org.hibernate.Query query = session().createQuery("from User where username = '" + user.getUsername() + "'");
		return query.uniqueResult();
	}

	public Object getUserbyUsername(String username) {
		org.hibernate.Query query = session().createQuery("from Users where username = '" + username + "'");
		return query.uniqueResult();
	}

	public Object getUserregisterbyUsername(String username) {
		org.hibernate.Query query = session().createQuery("from User where username = '" + username + "'");
		return query.uniqueResult();
	}

	public List<User> getAllNewUsers() {
		return session().createQuery("from User where isnewuser = 1").list();
	}

	public void deleteUserRequest(String username) {
		String hql = "UPDATE User set isnewuser = :isnewuser_ " + "WHERE username = :username_";
		Query query = session().createQuery(hql);
		query.setParameter("isnewuser_", false);
		query.setParameter("username_", username);
		int result = query.executeUpdate();
		System.out.println("Rows affected: " + result);
	}

}
