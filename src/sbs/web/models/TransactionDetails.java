package sbs.web.models;

public class TransactionDetails {

	private int fromAccountNo;
	private int toAccountNo;
	private double balance;
	private boolean account_type;
	
	
	public int getFromAccountNo() {
		return fromAccountNo;
	}
	public void setFromAccountNo(int fromAccountNo) {
		this.fromAccountNo = fromAccountNo;
	}
	public int getToAccountNo() {
		return toAccountNo;
	}
	public void setToAccountNo(int toAccountNo) {
		this.toAccountNo = toAccountNo;
	}
	public double getBalance() {
		return balance;
	}
	public void setBalance(double balance) {
		this.balance = balance;
	}
	public boolean isAccount_type() {
		return account_type;
	}
	public void setAccount_type(boolean account_type) {
		this.account_type = account_type;
	}
	
	
	
}
