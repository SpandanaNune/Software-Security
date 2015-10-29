package sbs.web.models;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "otp")
public class OTP {

	@Id
	@Column(name = "mailID")
	String mailID;
	String otpValue;
	String firstName;
	int attempts;
	
	public int getAttempts() {
		return attempts;
	}
	public void setAttempts(int attempts) {
		this.attempts = attempts;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getMailID() {
		return mailID;
	}
	public void setMailID(String mailID) {
		this.mailID = mailID;
	}
	public String getOtpValue() {
		return otpValue;
	}
	public void setOtpValue(String otpValue) {
		this.otpValue = otpValue;
	}
	@Override
	public String toString() {
		return "OTP [mailID=" + mailID + ", otpValue=" + otpValue + ", firstName=" + firstName + ", attempts="
				+ attempts + "]";
	}
	
	
}


	
	
