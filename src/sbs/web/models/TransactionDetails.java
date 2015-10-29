package sbs.web.models;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class TransactionDetails {

//	@Pattern(regexp="^[0-9]{8}$", message="Invalid Account Number. It should be 8 digits.")
	private String fromAccountNo;
//	@Pattern(regexp="^[0-9]*$", message="Invalid Account Number. It should be 8 digits.")
	private String toMyAccountNo;
//	@Pattern(regexp="^[0-9]{8}", message="Invalid Account Number. It should be 8 digits.")
	private String toOtherAccountNo;
	@Size(min =1, max=6, message="Amount should be less than 999999")
	@Pattern(regexp="^[0-9]+$+", message="Invalid amount number")
	private String balance;
	
	private String account_type;
	
	private String transaction_type;
	
	public String getTransaction_type() {
		return transaction_type;
	}
	public void setTransaction_type(String transaction_type) {
		this.transaction_type = transaction_type;
	}
	public String getFromAccountNo() {
		return fromAccountNo;
	}
	public void setFromAccountNo(String fromAccountNo) {
		this.fromAccountNo = fromAccountNo;
	}
	
	public String getToMyAccountNo() {
		return toMyAccountNo;
	}
	public void setToMyAccountNo(String toMyAccountNo) {
		this.toMyAccountNo = toMyAccountNo;
	}
	public String getToOtherAccountNo() {
		return toOtherAccountNo;
	}
	public void setToOtherAccountNo(String toOtherAccountNo) {
		this.toOtherAccountNo = toOtherAccountNo;
	}
	public String getBalance() {
		return balance;
	}
	public void setBalance(String balance) {
		this.balance = balance;
	}
	public String getAccount_type() {
		return account_type;
	}
	public void setAccount_type(String account_type) {
		this.account_type = account_type;
	}
	
}
