package sbs.web.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sbs.web.dao.UsersDao;

import sbs.web.models.User;

import sbs.web.models.Authorities;

import sbs.web.models.Users;

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

	public void createUser(User user) {
		usersDao.createUser(user);
	}

	public void setAuthority(Authorities auth) {
		usersDao.setAuthority(auth);
	}

	public User validateUser(User user) {
		User validatedUser = (User) usersDao.validateUser(user);
		return validatedUser;
	}

	public Users getUserbyUsername(String username) {
		Users getuser = (Users) usersDao.getUserbyUsername(username);
		return getuser;
	}

	public User getUserregisterbyUsername(String username) {
		User getuser = (User) usersDao.getUserregisterbyUsername(username);

		return getuser;
	}

	public Users getUserbyField(String field, String value) {
		Users getuser = (Users) usersDao.getUserbyField(field, value);
		return getuser;
	}

	public List<User> getAllNewUsers() {
		return usersDao.getAllNewUsers();
	}

	public List<User> getAllActiveUsers() {
		return usersDao.getAllActiveUsers();
	}

	public void deleteUserRequest(String username) {
		usersDao.deleteUserRequest(username);
	}

	public void userActivation(Users users) {
		usersDao.userActivation(users);
	}
}
