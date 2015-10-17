package sbs.web.models;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Transaction_CompositeKey implements Serializable{ 
	@Column(name = "transactionId")
	private int transactionId;
	@Column(name = "accountNo")
	private int accountNo;
	public int getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(int transactionId) {
		this.transactionId = transactionId;
	}
	public int getAccountNo() {
		return accountNo;
	}
	public void setAccountNo(int accountNo) {
		this.accountNo = accountNo;
	}
	@Override
	public String toString() {
		return "[transactionId=" + transactionId + ", accountNo=" + accountNo + "]";
	}
}
