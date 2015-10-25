package sbs.web.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sbs.web.dao.UsersDao;
import sbs.web.models.Authorities;
import sbs.web.models.PII;
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
	
	public void updateUser(User user) {
		usersDao.updateUser(user);
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
	public Users getUserbyField(String field, String value)
	{
		Users getuser = (Users) usersDao.getUserbyField(field, value);
		return getuser;
	}
	public User getUserregisterbyUsername(String username) {
		User getuser = (User) usersDao.getUserregisterbyUsername(username);

		return getuser;
	}
	
	public User getUserProfilebyField(String field, String value) {
		User getuser = (User) usersDao.getUserProfilebyField(field, value);
		return getuser;
	}

	public void deletePII(String username)
	{
		usersDao.deletePIIRequest(username);
	}
	
	public void approvePII(String username)
	{
		PII pii = (PII)usersDao.getPII(username);
		pii.setApproved(true);
		usersDao.updatePII(pii);
	}
	
	public List<PII> getAllPIIs()
	{
		return usersDao.getAllPIIRequests();
	}
	
	public PII getPII(String username)
	{
		return (PII)usersDao.getPII(username);
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

	public void saveOrUpdateUsers(Users users) {
		usersDao.saveOrUpdateUsers(users);
	}
}
