package sbs.web.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user")
public class User {
	
	@Id
	@Column(name = "userid")
	private int userid;
	
	//@Column(name = "username")
	private String username;
	
	//@Column(name = "email")
	private String email;
	
	//@Column(name = "password")
	private String password;
	
	private String enabled;
	
	public String getEnabled() {
		return enabled;
	}

	public void setEnabled(String enabled) {
		this.enabled = enabled;
	}

	
	public User() {
		
	}

	public User(int userid, String username, String email, String password) {
		this.userid = userid;
		this.username = username;
		this.password = password;
		this.email = email;
		
	}

	
	public int getUserid() {
		return userid;
	}
	public void setUserid(int userid) {
		this.userid = userid;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	@Override
	public String toString() {
		return "[userid=" + userid + ", username=" + username + ", email=" + email + ", password=" + password
				+ "]";
	}
	
}
