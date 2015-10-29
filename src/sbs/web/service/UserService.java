package sbs.web.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sbs.web.dao.UsersDao;
import sbs.web.models.Accounts;
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
	public void deleteUser(User user) {
		usersDao.deleteUser(user);
	}
	
	public Authorities getUserActivatebyUsername(String username) {
		Authorities getuser = (Authorities) usersDao.getUserActivatebyUsername(username);  //Pankaj change
		return getuser;
	}
	public List<User> getAllNewMerchants() {
		return usersDao.getAllNewMerchants();
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

	public List<Authorities> getUserAuthoritiesByField(String field, String value) {
		return usersDao.getUserAuthoritiesByField(field, value);
	}

	public List<Users> getUsersByFieldBool(String field, boolean value) {
		return usersDao.getUsersByFieldBool(field, value);
	}
	
	public User getUserregisterbyEmail(String mail) {
		User getuser = (User) usersDao.getUserregisterbyEmail(mail);
		return getuser;
		
	}

	public Users getUserByFieldBool(String field, boolean value,String username) {
		return (Users)usersDao.getUserByFieldBool(field, value,username);
	}

	public List<Accounts> getAccountsByField(String field, long value) {
		return usersDao.getAccountsByField(field, value);
	}

	public User getUserProfilebyField(String field, String value) {
		User getuser = (User) usersDao.getUserProfilebyField(field, value);
		return getuser;
	}

	public void deletePII(String username) {
		usersDao.deletePIIRequest(username);
	}

	public void approvePII(String username) {
		PII pii = (PII) usersDao.getPII(username);
		pii.setApproved(true);
		usersDao.updatePII(pii);
	}
	
	public void createPII(PII pii)
	{
		usersDao.updatePII(pii);
	}
	
	public List<PII> getAllPIIs()
	{
		return usersDao.getAllPIIRequests();
	}


	public PII getPII(String username) {
		return (PII) usersDao.getPII(username);
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

	public void addNewAccount(Accounts account) {
		usersDao.addNewAccount(account);
	}

	
	public List<Authorities> getAllEmployees()
	{
		return usersDao.getAllEmployees();
	}
	
	public Authorities getEmployee(String username)
	{
		return (Authorities)usersDao.getEmployee(username);
	}
	
	public void deleteEmployee(Authorities auth)
	{
		usersDao.deleteEmployee(auth);
	}
	// Return users with Role_NEW and isnewuser=1
	public List<User> getAllNewRoleUsers() {
		return usersDao.getAllNewRoleUsers();
	}
	
	// Return merchants with Role_NEWMERCHANT and isnewuser=1 and is_merchant=1
	public List<User> getAllNewRoleMerchants() {
		return usersDao.getAllNewRoleMerchants();
	}
	
	// Return internal employees with Role_NEWEMPLOYEE
	public List<User> getAllNewRoleEmployees() {
		return usersDao.getAllNewRoleEmployees();
	}

	// Return internal managers with Role_NEWManager
	public List<User> getAllNewRoleManagers() {
		return usersDao.getAllNewRoleManagers();
	}
	
	//Returns all active merchants  
	
	public List<User> getAllMerchantAccounts() {
		return usersDao.getAllMerchantAccounts();
	}
	
	public List<User> getAllActiveMerchants(){
		return usersDao.getAllActiveMerchants();
	}
	
	public List<Users> getAllExternalCustomersByFieldBool(String field, boolean value) {
		return usersDao.getAllExternalCustomersByFieldBool(field, value);	
	}
	public List<Users> getAllExternalMerchantsByFieldBool(String field, boolean value) {
		return usersDao.getAllExternalMerchantsByFieldBool(field, value);	
	}
	public List<Users> getAllExternalUsersByFieldBool(String field, boolean value) {
		return usersDao.getAllExternalUsersByFieldBool(field, value);	
	}
	
	public List<Users> getAllInternalUsersByFieldBool(String field, boolean value) {
		return usersDao.getAllInternalUsersByFieldBool(field, value);	
	}
	
	public Authorities getAuthorityByField(String field, String value){
		return (Authorities)usersDao.getAuthorityByField(field, value);
	}

}
