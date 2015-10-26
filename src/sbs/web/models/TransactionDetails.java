package sbs.web.models;

public class TransactionDetails {

	private long fromAccountNo;
	private long toMyAccountNo;
	private long toOtherAccountNo;
	private double balance;
	private String account_type;
	private String transaction_type;
	
	
	public String getTransaction_type() {
		return transaction_type;
	}
	public void setTransaction_type(String transaction_type) {
		this.transaction_type = transaction_type;
	}
	public long getFromAccountNo() {
		return fromAccountNo;
	}
	public void setFromAccountNo(long fromAccountNo) {
		this.fromAccountNo = fromAccountNo;
	}
	
	public long getToMyAccountNo() {
		return toMyAccountNo;
	}
	public void setToMyAccountNo(long toMyAccountNo) {
		this.toMyAccountNo = toMyAccountNo;
	}
	public long getToOtherAccountNo() {
		return toOtherAccountNo;
	}
	public void setToOtherAccountNo(long toOtherAccountNo) {
		this.toOtherAccountNo = toOtherAccountNo;
	}
	public double getBalance() {
		return balance;
	}
	public void setBalance(double balance) {
		this.balance = balance;
	}
	public String getAccount_type() {
		return account_type;
	}
	public void setAccount_type(String account_type) {
		this.account_type = account_type;
	}
	
}
