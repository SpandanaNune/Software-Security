package sbs.web.models;

import javax.persistence.Column;
import javax.persistence.Id;

public class ExternalUser {
	
	@Id
	@Column(name = "userid")
	private int userid;
	
	private String firstname;
	private String lastname;
	private String dob;
	private int SSN;
	private String email;
	private int phone;
	private String Addr1;
	private String Addr2;
	private String City;
	private String State;
	private int Zip;

	
public String getFirstName() {
	return firstname;
}
public void setFirstName(String firstname) {
	this.firstname = firstname;
}
public String getLastName() {
	return lastname;
}
public void setLastName(String lastname) {
	this.lastname = lastname;
}
public String getDoB() {
	return dob;
}
public void setDoB(String dob) {
	this.dob = dob;
}
public int getSsN() {
	return SSN;
}
public void setSsN(int SSN) {
	this.SSN = SSN;
}
public String getEmaiL() {
	return email;
}
public void setEmaiL(String email) {
	this.email = email;
}
public String getAddR1() {
	return Addr1;
}
public void setAddR1(String Addr1) {
	this.Addr1 = Addr1;
}
public String getAddR2() {
	return Addr2;
}
public void setAddR2(String Addr2) {
	this.Addr2 = Addr2;
}
public String getCitY() {
	return City;
}
public void setCitY(String City) {
	this.City = City;
}
public String getState() {
	return State;
}
public void setState(String State) {
	this.State = State;
}
public int getZip() {
	return Zip;
}
public void setZip(int Zip) {
	this.Zip = Zip;
}
public int getPhone() {
	return phone;
}
public void setPhone(int phone) {
	this.phone = phone;
}

@Override
public String toString() {
	return "[firstname=" + firstname + ", lastname=" + lastname + ", dob="+ dob+ ", SSN="+ SSN+ ", email="+ email + ", Addr1="+ Addr1+  ", Addr2="+ Addr2+ ", City="+ City + ", State="+ State+ ", Zip="+Zip+ "]";
}

}
