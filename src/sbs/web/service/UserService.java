package sbs.web.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sbs.web.dao.UsersDao;
import sbs.web.models.Accounts;
import sbs.web.models.Authorities;
import sbs.web.models.User;
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

	public List<Users> getUsersByField(String field, String value) {
		return usersDao.getUsersByField(field, value);
	}
	
	public List<User> getUserProfileByField(String field, String value) {
		return usersDao.getUserProfileByField(field, value);
	}
	
	public List<Users> getUsersByFieldBool(String field, boolean value) {
		return usersDao.getUsersByFieldBool(field, value);
	}

	public List<Accounts> getAccountsByField(String field, long value) {
		return usersDao.getAccountsByField(field, value);
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
	
	public void addNewAccount(Accounts account){
		usersDao.addNewAccount(account);
	}
}
