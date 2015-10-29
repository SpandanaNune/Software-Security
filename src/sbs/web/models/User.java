package sbs.web.models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
@Table(name = "user_profile")
public class User {

	@Id
	@Size(min = 8, max = 45)
	@Pattern(regexp = "^[A-Za-z0-9]+$", message = "Username must be alphanumeric")
	private String username;

	@Size(min = 2, max = 45)
	@Pattern(regexp = "^[A-Za-z0-9]+$", message = "Name must be alphanumeric")
	private String firstname;

	@Size(min = 2, max = 45)
	@Pattern(regexp = "^[A-Za-z0-9]+$", message = "Lastname must be alphanumeric")
	private String lastname;

	@Pattern(regexp = ".+@.+\\..+", message = "Invalid Email Address format")
	private String email;

	@Pattern(regexp = "^(1[0-2]|0[1-9])/(3[01]|[12][0-9]|0[1-9])/[0-9]{4}$", message = "Invalid Date of Birth format")
	private String dob;
	
	@Pattern(regexp="^[0-9]{9}$", message="Should contains 9 digits")
	private String SSN;

	@Pattern(regexp = "^[0-9]{10}$", message = "Phone number should be of 10 digits.")
	private String phone;

	@Size(min = 5, max = 45)
	@Pattern(regexp = "^[A-Za-z0-9]+$", message = "Address1 must be alphanumeric")
	private String Addr1;

	@Size(min = 5, max = 45)
	@Pattern(regexp = "^[A-Za-z0-9]+$", message = "Address2 must be alphanumeric and of atleast 5 characters and less than 45 characters")
	private String Addr2;

	@Size(min = 2, max = 45)
	@Pattern(regexp = "^[A-Za-z0-9]+$", message = "City must be alphanumeric")
	private String City;

	@Size(min = 2, max = 45)
	@Pattern(regexp = "^[A-Za-z0-9]+$", message = "State must be alphanumeric")
	private String State;

	@Pattern(regexp = "^[0-9]{5}$", message = "Incorrect Zip Code format")
	private String Zip;

	private String reset_pass_token;
	private boolean isnewuser;
	private boolean is_deleted;
	private boolean ismerchant;

	public boolean isIsmerchant() {
		return ismerchant;
	}

	public void setIsmerchant(boolean ismerchant) {
		this.ismerchant = ismerchant;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username.toLowerCase();
	}

	public boolean isIsnewuser() {
		return isnewuser;
	}

	public void setIsnewuser(boolean isnewuser) {
		this.isnewuser = isnewuser;
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

	public String getReset_pass_token() {
		return reset_pass_token;
	}

	public void setReset_pass_token(String reset_pass_token) {
		this.reset_pass_token = reset_pass_token;
	}

	public boolean isIs_deleted() {
		return is_deleted;
	}

	public void setIs_deleted(boolean is_deleted) {
		this.is_deleted = is_deleted;
	}

	@Override
	public String toString() {
		return "User [username=" + username + ", firstname=" + firstname + ", lastname=" + lastname + ", email=" + email
				+ ", dob=" + dob + ", SSN=" + SSN + ", phone=" + phone + ", Addr1=" + Addr1 + ", Addr2=" + Addr2
				+ ", City=" + City + ", State=" + State + ", Zip=" + Zip + ", reset_pass_token=" + reset_pass_token
				+ ", isnewuser=" + isnewuser + ", is_deleted=" + is_deleted + ", ismerchant=" + ismerchant + "]";
	}

}
