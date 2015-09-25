package sbs.web.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sbs.web.dao.UsersDao;
import sbs.web.models.User;


@Service("userService")
public class UserService {
private UsersDao usersDao;
	
	@Autowired
	public void setUserDao(UsersDao usersDao) {
		this.usersDao = usersDao;
	}

	public List<User> getAllUsers() {
		return usersDao.getAllUsers();
	}
	public void createUser(User user){
		usersDao.createUser(user);
	}

	public User validateUser(User user) {
		User validatedUser = (User)usersDao.validateUser(user);
		return validatedUser;
	}
}
