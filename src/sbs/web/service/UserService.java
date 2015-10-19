package sbs.web.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sbs.web.dao.UsersDao;

import sbs.web.models.UserProfile;

import sbs.web.models.Authorities;

import sbs.web.models.Users;

@Service("userService")
public class UserService {
	private UsersDao usersDao;

	@Autowired
	public void setUserDao(UsersDao usersDao) {
		this.usersDao = usersDao;
	}

	public List<UserProfile> getAllUsers() {
		return usersDao.getAllUsers();
	}

	public void createUser(UserProfile user) {
		usersDao.createUser(user);
	}
	
	public void setAuthority(Authorities auth){
		usersDao.setAuthority(auth);
	}

	public UserProfile validateUser(UserProfile user) {
		UserProfile validatedUser = (UserProfile) usersDao.validateUser(user);
		return validatedUser;
	}

	public Users getUserbyUsername(String username) {
		Users getuser = (Users) usersDao.getUserbyUsername(username);
		return getuser;
	}

	public UserProfile getUserregisterbyUsername(String username) {
		UserProfile getuser = (UserProfile) usersDao.getUserregisterbyUsername(username);

		return getuser;
	}
public Users getUserbyField(String field, String value)
{
	Users getuser = (Users) usersDao.getUserbyField(field, value);
	return getuser;
}


	public List<UserProfile> getAllNewUsers() {
		return usersDao.getAllNewUsers();
	}
	
	public List<UserProfile> getAllActiveUsers() {
		return usersDao.getAllActiveUsers();
	}

	public void deleteUserRequest(String username) {
		usersDao.deleteUserRequest(username);
	}
	
	public void userActivation(Users users){
		usersDao.userActivation(users);
	}
}
