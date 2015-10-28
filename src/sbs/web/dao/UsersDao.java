package sbs.web.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import sbs.web.models.Accounts;
import sbs.web.models.Authorities;
import sbs.web.models.PII;
import sbs.web.models.User;
import sbs.web.models.Users;

@Transactional
@Component("usersDao")
public class UsersDao {
	
	@Autowired
	private PasswordEncoder passwordEncoder ;
	
	@Autowired
	private SessionFactory sessionFactory;

	public Session session() {
		return sessionFactory.getCurrentSession();
	}

	public void createUser(User user) {
		//System.out.println(user.toString());
		session().saveOrUpdate(user);
	}
	
	public void addNewAccount(Accounts account) {
		session().saveOrUpdate(account);
	}
	@Transactional

	public void updateUser(User user) {
		session().saveOrUpdate(user);
	}

	public void setAuthority(Authorities auth){
		session().saveOrUpdate(auth);
	}
	
	public void deleteUser(User user) {
		session().delete(user);
	}
	
	@Transactional
	public void saveOrUpdateUsers(Users users) {
		users.setPassword(passwordEncoder.encode(users.getPassword()));
		session().saveOrUpdate(users);
	}

	@SuppressWarnings("unchecked")
	public List<User> getAllUsers() {
		return session().createQuery("from User").list();
	}

	public Object validateUser(User user) {
		org.hibernate.Query query = session().createQuery("from User where username = '" + user.getUsername() + "'");
		return query.uniqueResult();
	//	return null;
	}
	
	public Object getUserActivatebyUsername(String username) {
		org.hibernate.Query query = session().createQuery("from Authorities where username = '" + username + "'");
		return query.uniqueResult();
	}
	
	@SuppressWarnings("unchecked")
	public List<User> getAllNewMerchants() {
		return session().createQuery("from User where ismerchant = 1 and isnewuser=1").list();
	}

	public Object getUserbyUsername(String username) {
		org.hibernate.Query query = session().createQuery("from Users where username = '" + username + "'");
		return query.uniqueResult();
	}
	
	@SuppressWarnings("unchecked")
	public List<User> getUserProfileByField(String field, String value) {
		return session().createQuery("from User where " + field + "='" + value + "'").list();	
	}
	
	@SuppressWarnings("unchecked")
	public List<Users> getUsersByField(String field, String value) {
		return session().createQuery("from Users where " + field + "='" + value + "'").list();	
	}
	
	@SuppressWarnings("unchecked")
	public List<Users> getUsersByFieldBool(String field, boolean value) {
		return session().createQuery("from Users where " + field + "=" + value + "").list();	
	}
	public Object getUserByFieldBool(String field, boolean value,String userName) {
		return session().createQuery("from Users where username='"+userName+"' and " + field + "=" + value + "").uniqueResult();	
	}
	
	public Object getUserProfilebyField(String field, String value) {
		org.hibernate.Query query = session().createQuery("from User where " + field + "='" + value + "'");
		return query.uniqueResult();
	}
	
	public Object getUserregisterbyUsername(String username) {
		org.hibernate.Query query = session().createQuery("from User where username = '" + username + "' and is_deleted=0");
		return query.uniqueResult();
	}

	
	@SuppressWarnings("unchecked")
	public List<User> getAllNewUsers() {
		return session().createQuery("from User where isnewuser = 1").list();
	}
	
	@SuppressWarnings("unchecked")
	public List<User> getAllNewRoleUsers() {
		return session().createQuery("from User where isnewuser=1 and username in (select username from Authorities where authority in ('ROLE_NEW'))").list();		
	}
	
	@SuppressWarnings("unchecked")
	public List<User> getAllNewRoleMerchants() {
		return session().createQuery("from User where isnewuser=1 and ismerchant=1 and username in (select username from Authorities where authority in ('ROLE_NEWMERCHANT'))").list();
	}
	@SuppressWarnings("unchecked")
	public List<User> getAllNewRoleEmployees() {
		return session().createQuery("from User where username in (select username from Authorities where authority in ('ROLE_NEWEMPLOYEE'))").list();
	}
	
	@SuppressWarnings("unchecked")
	public List<User> getAllNewRoleManagers() {
		return session().createQuery("from User where username in (select username from Authorities authority in ('ROLE_NEWMANAGER'))").list();
	}
	
	@SuppressWarnings("unchecked")
	public List<User> getAllActiveMerchants() {
		return session().createQuery("from User where is_merchant=1 and username in (select username from Authorities where authority in ('ROLE_MERCHANT'))").list();
	}
	
	@SuppressWarnings("unchecked")
	public List<Users> getAllExternalUsersByFieldBool(String field, boolean value) {
		return session().createQuery("from Users where username in (select username from Authorities where authority in ('ROLE_USER', 'ROLE_MERCHANT')) and " + field + "=" + value + "").list();	
	}
	
	@SuppressWarnings("unchecked")
	public List<Users> getAllExternalCustomersByFieldBool(String field, boolean value) {
		return session().createQuery("from Users where username in (select username from Authorities where authority in ('ROLE_USER')) and " + field + "=" + value + "").list();	
	}
	
	@SuppressWarnings("unchecked")
	public List<Users> getAllExternalMerchantsByFieldBool(String field, boolean value) {
		return session().createQuery("from Users where username in (select username from Authorities where authority in ('ROLE_MERCHANT')) and " + field + "=" + value + "").list();	
	}
	
	@SuppressWarnings("unchecked")
	public List<Users> getAllInternalUsersByFieldBool(String field, boolean value) {
		return session().createQuery("from Users where username = (select username from Authorities where authority in ('ROLE_EMPLOYEE', 'ROLE_MANAGER')) and " + field + "=" + value + "").list();	
	}
	///Authority Specific Queries
	public Object getAuthorityByField(String field, String value) {
		org.hibernate.Query query = session().createQuery("from Authorities where " + field + "='" + value + "'");
		return query.uniqueResult();
	}
	
	public void deleteUserRequest(String username) {
		
		User user = (User)getUserregisterbyUsername(username);
			session().delete(user);
		
//		String hql = "UPDATE User set isnewuser = :isnewuser_ " + "WHERE username = :username_";
//		Query query = session().createQuery(hql);
//		query.setParameter("isnewuser_", false);
//		query.setParameter("username_", username);
//		int result = query.executeUpdate();
//		System.out.println("Rows affected: " + result);
	}
	
	@SuppressWarnings("unchecked")
	public List<User> getAllActiveUsers(){
		return session().createQuery("from User where isnewuser = 0 and isdeleted=0").list();	
	}
	

	@SuppressWarnings("unchecked")
	public List<Accounts> getAccountsByField(String field, long value){
		return session().createQuery("from Accounts where " + field + "=" + value + "").list();	
	}
	@SuppressWarnings("unchecked")
	public List<PII> getAllPIIRequests(){
		return session().createQuery("from PII where isApproved = 0").list();	
	}

	public Object getUserregisterbyEmail(String mail) {
		org.hibernate.Query query = session().createQuery("from User where email = '" + mail + "'");
		return query.uniqueResult();
	}
	
	public Object getPII(String userName)
	{
		return session().createQuery("from PII where username = '"+userName+"'").uniqueResult();	
	}
	public void deletePIIRequest(String username)
	{
		PII pii = (PII)getPII(username);
		 session().delete(pii);
	}
	
	public void updatePII(PII pii)
	{
		session().saveOrUpdate(pii);
	}
	
	@SuppressWarnings("unchecked")
	public List<Authorities> getUserAuthoritiesByField(String field, String value){
		return session().createQuery("from Authorities where " + field + "='" + value + "'").list();	

	}	
	@SuppressWarnings("unchecked")
	public List<Authorities> getAllEmployees()
	{
		return session().createQuery("from Authorities where authority ='ROLE_EMPLOYEE' OR authority='ROLE_MANAGER'").list();
	}
	
	public Object getEmployee(String username)
	{
		return session().createQuery("from Authorities where username='"+username+"'").uniqueResult();
	}
	
	public void deleteEmployee(Authorities auth)
	{
		 session().delete(auth);

	}
//	public List<User> getAllActiveUsers(){
//		String hql = "from User ur, Users us where ur.username = us.username and us.enabled = 1";
//		return session().createQuery(hql).list();	
//	}
	@SuppressWarnings("unchecked")
	public List<User> getAllMerchantAccounts() {
		return session().createQuery("from User where ismerchant = 1").list();
	}


}
