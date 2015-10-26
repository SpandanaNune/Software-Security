package sbs.web.models;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Transaction_CompositeKey implements Serializable{ 
	
	@Column(name = "transactionId")
	 int transactionId=1000;
	
	@Column(name = "accountNo")
	private long accountNo;
	
	public int getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(int transactionId) {
		this.transactionId = transactionId;
	}
	public long getAccountNo() {
		return accountNo;
	}
	public void setAccountNo(long accountNo) {
		this.accountNo = accountNo;
	}
	@Override
	public String toString() {
		return "[transactionId=" + transactionId + ", accountNo=" + accountNo + "]";
	}
}
