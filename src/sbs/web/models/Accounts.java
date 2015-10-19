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
	private long banker_id;
	private long user_id;
	
	public long getAccountNo() {
		return accountNo;
	}
	public void setAccountNo(long accountNo) {
		this.accountNo = accountNo;
	}
	public long getUser_id() {
		return user_id;
	}
	public void setUser_id(long user_id) {
		this.user_id = user_id;
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
	public long getBanker_id() {
		return banker_id;
	}
	public void setBanker_id(long banker_id) {
		this.banker_id = banker_id;
	}
	
	@Override
	public String toString() {
		return "Accounts [accountNo=" + accountNo + ", user_id=" + user_id + ", account_type=" + account_type
				+ ", balance=" + balance + ", isLocked=" + isLocked + ", banker_id=" + banker_id + "]";
	}
		
}