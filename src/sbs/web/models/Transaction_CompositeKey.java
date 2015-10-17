package sbs.web.models;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Transaction_CompositeKey implements Serializable{ 
	
	@Column(name = "transactionId")
	 int transactionID=1000;
	
	@Column(name = "accountNo")
	private int accountNo;
	
	public int getTransactionID() {
		return transactionID;
	}
	public void setTransactionID(int transactionID) {
		this.transactionID = transactionID;
	}
	public int getAccountNo() {
		return accountNo;
	}
	public void setAccountNo(int accountNo) {
		this.accountNo = accountNo;
	}
	@Override
	public String toString() {
		return "[transactionId=" + transactionID + ", accountNo=" + accountNo + "]";
	}
}
