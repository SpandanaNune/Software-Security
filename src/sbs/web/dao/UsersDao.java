package sbs.web.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Component("usersDao")
public class UsersDao {

	@Autowired
	private SessionFactory sessionFactory;
	
	public Session session(){
		return sessionFactory.getCurrentSession();
	}
	@Transactional
	public void createUser(User user){
		session().save(user);
	}
	@SuppressWarnings("unchecked")
	public List<User> getAllUsers(){
		return session().createQuery("from User").list();
	}
}
