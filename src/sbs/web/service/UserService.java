package sbs.web.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sbs.web.dao.User;
import sbs.web.dao.UsersDao;


@Service("userService")
public class UserService {
private UsersDao usersDao;
	
	@Autowired
	public void setUserDao(UsersDao usersDao) {
		this.usersDao = usersDao;
	}

	public List<User> getCurrent() {
		return usersDao.getAllUsers();
	}
	public void testSave(User user){
		usersDao.createUser(user);
	}
}
