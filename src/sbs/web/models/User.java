package sbs.web.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "user")
public class User {

	@Id
	@Column(name = "userid")
	private int userid;

	// @Column(name = "username")
	@Size(min = 5, max = 45, message = "Name must be atleast 5 characters")
	@NotNull
	private String username;

	// @Column(name = "email")
	@NotNull
	@Size(min = 5, max = 45, message = "Invalid Email Address")
	private String email;

	// @Column(name = "password")
	@Size(min = 5, max = 45, message = "firstname must be atleast 5 characters")
	private String firstname;
	@Size(min = 5, max = 45, message = "lastname must be atleast 5 characters")
	private String lastname;
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
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	@Override
	public String toString() {
		return "User [userid=" + userid + ", username=" + username + ", email=" + email + ", firstname=" + firstname
				+ ", lastname=" + lastname + "]";
	}

}
