package sbs.web.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "accounts")

public class Accounts {

	@Id
	@Column(name = "accountNo")
	private long accountNo;
	private boolean account_type;
	private double balance;
	private boolean isLocked; 
	private String bankername;
	private String username;
	public long getAccountNo() {
		return accountNo;
	}
	public void setAccountNo(long accountNo) {
		this.accountNo = accountNo;
	}
	public boolean isAccount_type() {
		return account_type;
	}
	public void setAccount_type(boolean account_type) {
		this.account_type = account_type;
	}
	public double getBalance() {
		return balance;
	}
	public void setBalance(double balance) {
		this.balance = balance;
	}
	public boolean isLocked() {
		return isLocked;
	}
	public void setLocked(boolean isLocked) {
		this.isLocked = isLocked;
	}
	public String getBankername() {
		return bankername;
	}
	public void setBankername(String bankername) {
		this.bankername = bankername;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	@Override
	public String toString() {
		return "Accounts [accountNo=" + accountNo + ", account_type=" + account_type + ", balance=" + balance
				+ ", isLocked=" + isLocked + ", bankername=" + bankername + ", username=" + username + "]";
	}
	
}