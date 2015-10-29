package sbs.web.models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
@Table(name = "users")
public class Users {

	@Id
	@Size(min = 8, max = 45)
	@Pattern(regexp = "^[A-Za-z0-9]+$", message = "Username must be alphanumeric")
	private String username;
	
	@Size(min = 8, max = 100)
	@Pattern(regexp = "^[A-Za-z0-9_@]*$", message = "Password must be alphanumeric and ")
	private String password;

	@Pattern(regexp = ".+@.+\\..+", message = "Invalid Email Address format")
	private String email;
	private int siteKeyID;
	
	private boolean enabled;
	private boolean accountNonExpired;
	private boolean accountNonLocked;
	private boolean credentialsNonExpired;
	
	@Size(min = 5, max = 45)
	@Pattern(regexp = "^[A-Za-z0-9]+$", message = "Answer1 must be alphanumeric")
	
	private String q1;
	
	@Size(min = 5, max = 45)
	@Pattern(regexp = "^[A-Za-z0-9]+$", message = "Answer2 must be alphanumeric")
	private String q2;
	
	@Size(min = 5, max = 45)
	@Pattern(regexp = "^[A-Za-z0-9]+$", message = "Answer3 must be alphanumeric")
	private String q3;

	public String getQ1() {
		return q1;
	}

	public void setQ1(String q1) {
		this.q1 = q1;
	}

	public String getQ2() {
		return q2;
	}

	public void setQ2(String q2) {
		this.q2 = q2;
	}

	public String getQ3() {
		return q3;
	}

	public void setQ3(String q3) {
		this.q3 = q3;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getSiteKeyID() {
		return siteKeyID;
	}

	public void setSiteKeyID(int siteKeyID) {
		this.siteKeyID = siteKeyID;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public boolean isAccountNonExpired() {
		return accountNonExpired;
	}

	public void setAccountNonExpired(boolean accountNonExpired) {
		this.accountNonExpired = accountNonExpired;
	}

	public boolean isAccountNonLocked() {
		return accountNonLocked;
	}

	public void setAccountNonLocked(boolean accountNonLocked) {
		this.accountNonLocked = accountNonLocked;
	}

	public boolean isCredentialsNonExpired() {
		return credentialsNonExpired;
	}

	public void setCredentialsNonExpired(boolean credentialsNonExpired) {
		this.credentialsNonExpired = credentialsNonExpired;
	}

	@Override
	public String toString() {
		return "Users [ username=" + username + ", password=" + password + ", email=" + email + ", siteKeyID="
				+ siteKeyID + ", enabled=" + enabled + ", accountNonExpired=" + accountNonExpired
				+ ", accountNonLocked=" + accountNonLocked + ", credentialsNonExpired=" + credentialsNonExpired + "]";

	}
	
	@Override
	public int hashCode()
	{
		return username.hashCode();
	}

}
