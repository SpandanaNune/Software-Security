package sbs.web.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import sbs.web.models.User;

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
	public Object validateUser(User user) {
		org.hibernate.Query query = session().createQuery("from User where username = '"+user.getUsername()+"' and password ='"+user.getPassword()+"'");
		return query.uniqueResult();
	}
}
