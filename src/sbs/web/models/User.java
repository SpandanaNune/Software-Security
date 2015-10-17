//package sbs.web.models;
//
//import java.sql.Date;
//
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.Id;
//import javax.persistence.Table;
//import javax.validation.constraints.NotNull;
//import javax.validation.constraints.Size;
//
//@Entity
//@Table(name = "userregister")
//public class User {
//
//	@Id
//	@Column(name = "userid")
//	private int userid;
//
//	// @Column(name = "username")
//	@Size(min = 5, max = 45, message = "Name must be atleast 5 characters")
//	@NotNull
//	private String username;
//
//	// @Column(name = "email")
//	@NotNull
//	@Size(min = 5, max = 45, message = "Invalid Email Address")
//	private String email;
//
//	// @Column(name = "password")
//	@Size(min = 5, max = 45, message = "firstname must be atleast 5 characters")
//	private String firstname;
//	@Size(min = 5, max = 45, message = "lastname must be atleast 5 characters")
//	private String lastname;
//	
//	
//	private Date dob;
//	@Size(min = 5, max = 45, message = "firstname must be atleast 5 characters")
//	private int SSN;
//	private int phone;
//	@Size(min = 5, max = 45, message = "lastname must be atleast 5 characters")
//	private String addr1;
//	@Size(min = 5, max = 45, message = "lastname must be atleast 5 characters")
//	private String addr2;
//	@Size(min = 5, max = 45, message = "lastname must be atleast 5 characters")
//	private String city;
//	@Size(min = 5, max = 45, message = "lastname must be atleast 5 characters")
//	private String state;
//	@Size(min = 5, max = 45, message = "firstname must be atleast 5 characters")
//	private int zip;
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	public Date getDob() {
//		return dob;
//	}
//	public void setDob(Date dob) {
//		this.dob = dob;
//	}
//	public int getSSN() {
//		return SSN;
//	}
//	public void setSSN(int sSN) {
//		SSN = sSN;
//	}
//	public int getPhone() {
//		return phone;
//	}
//	public void setPhone(int phone) {
//		this.phone = phone;
//	}
//	public String getAddr1() {
//		return addr1;
//	}
//	public void setAddr1(String addr1) {
//		this.addr1 = addr1;
//	}
//	public String getAddr2() {
//		return addr2;
//	}
//	public void setAddr2(String addr2) {
//		this.addr2 = addr2;
//	}
//	public String getCity() {
//		return city;
//	}
//	public void setCity(String city) {
//		this.city = city;
//	}
//	public String getState() {
//		return state;
//	}
//	public void setState(String state) {
//		this.state = state;
//	}
//	public int getZip() {
//		return zip;
//	}
//	public void setZip(int zip) {
//		this.zip = zip;
//	}
//	
//	public int getUserid() {
//		return userid;
//	}
//	public void setUserid(int userid) {
//		this.userid = userid;
//	}
//	public String getUsername() {
//		return username;
//	}
//	public void setUsername(String username) {
//		this.username = username;
//	}
//	public String getEmail() {
//		return email;
//	}
//	public void setEmail(String email) {
//		this.email = email;
//	}
//	public String getFirstname() {
//		return firstname;
//	}
//	public void setFirstname(String firstname) {
//		this.firstname = firstname;
//	}
//	public String getLastname() {
//		return lastname;
//	}
//	public void setLastname(String lastname) {
//		this.lastname = lastname;
//	}
//	@Override
//	public String toString() {
//		return "User [userid=" + userid + ", username=" + username + ", email=" + email + ", firstname=" + firstname
//				+ ", lastname=" + lastname + ", dob=" + dob + ", SSN=" + SSN + ", phone=" + phone + ", addr1=" + addr1
//				+ ", addr2=" + addr2 + ", city=" + city + ", state=" + state + ", zip=" + zip + "]";
//	}
//	
//}

package sbs.web.models;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;

@Entity
@Table(name = "userregister")
public class User {
	
	@Id
	// @Column(name = "username")
	@Size(min = 5, max = 45, message = "Name must be atleast 5 characters")
	@NotNull
	private String username;

	// @Column(name = "email")
	@NotNull
	@Pattern(regexp=".+@.+\\..+", message="Email Address incorrect")
	private String email;

	// @Column(name = "password")
	@Size(min = 2, max = 45, message = "firstname must be atleast 2 characters and less than 45 characters")
	private String firstname;
	@Size(min = 2, max = 45, message = "lastname must be atleast 2 characters and less than 45 characters")
	private String lastname;
	
	//@Column(name = "dob")
	@NotNull
	@Pattern(regexp="^(1[0-2]|0[1-9])/(3[01]|[12][0-9]|0[1-9])/[0-9]{4}$", message="Incorrect Date of Birth")
	private String dob;
	
	//@Column(name = "ssn")
	@NotNull
	@Pattern(regexp="^[0-9]{9}$", message="Incorrect SSN")
	private String SSN;
	
	//@Column(name = "phone")
	@NotNull
	@Pattern(regexp="^[0-9]{10}$", message="Incorrect Phone Number")
	private String phone;
	
    //@Column(name = "addr1")
	@NotNull
	@Size(min = 5, max = 45, message = "Address must be atleast 5 characters")
	private String Addr1;
	private String Addr2;
	
	//@Column(name = "city")
	@Size(min = 2, max = 45, message = "City must be atleast 2 characters")
	private String City;
	
	//@Column(name = "state")
	@Size(min = 2, max = 45, message = "State must be atleast 2 characters")
	private String State;
	
	//@Column(name = "zip")
	@NotNull
	@Pattern(regexp="^[0-9]{5}$", message="Incorrect Zip Code")
	private String Zip;
	
	private boolean canlogin;
	private boolean isnewuser;
	
	public boolean isCanlogin() {
		return canlogin;
	}
	public void setCanlogin(boolean canlogin) {
		this.canlogin = canlogin;
	}
	public boolean isIsnewuser() {
		return isnewuser;
	}
	public void setIsnewuser(boolean isnewuser) {
		this.isnewuser = isnewuser;
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
	public String getDob() {
		return dob;
	}
	public void setDob(String dob) {
		this.dob = dob;
	}
	public String getSSN() {
		return SSN;
	}
	public void setSSN(String sSN) {
		SSN = sSN;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getAddr1() {
		return Addr1;
	}
	public void setAddr1(String addr1) {
		Addr1 = addr1;
	}
	public String getAddr2() {
		return Addr2;
	}
	public void setAddr2(String addr2) {
		Addr2 = addr2;
	}
	public String getCity() {
		return City;
	}
	public void setCity(String city) {
		City = city;
	}
	public String getState() {
		return State;
	}
	public void setState(String state) {
		State = state;
	}
	public String getZip() {
		return Zip;
	}
	public void setZip(String zip) {
		Zip = zip;
	}
	@Override
	public String toString() {
		return "User [username=" + username + ", email=" + email + ", firstname=" + firstname
				+ ", lastname=" + lastname + ", dob=" + dob + ", SSN=" + SSN + ", phone=" + phone + ", Addr1=" + Addr1
				+ ", Addr2=" + Addr2 + ", City=" + City + ", State=" + State + ", Zip=" + Zip + ", canlogin=" + canlogin
				+ ", isnewuser=" + isnewuser + "]";
	}
}
