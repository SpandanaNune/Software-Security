package sbs.web.models;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "transaction")
public class Transaction {
	
	@Id
	Transaction_CompositeKey primaryKey;
	@Column(name = "transactionType")
	private String transactionType;
	@Column(name = "transactionAmount")
	private double amount;
	@Column(name = "status")
	private String status;
	@Column(name = "isCritical")
	private boolean isCritical;
	@Column(name = "dt_created")
	private String createdDate;

	public Transaction_CompositeKey getPrimaryKey() {
		return primaryKey;
	}
	public void setPrimaryKey(Transaction_CompositeKey primaryKey) {
		this.primaryKey = primaryKey;
	}
	public String getTransactionType() {
		return transactionType;
	}
	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public boolean isCritical() {
		return isCritical;
	}
	public void setCritical(boolean isCritical) {
		this.isCritical = isCritical;
	}
	public String getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	@Override
	public String toString() {
		return "Transaction [primaryKey=" + primaryKey + ", transactionType=" + transactionType + ", amount=" + amount
				+ ", status=" + status + ", isCritical=" + isCritical + ", createdDate=" + createdDate + "]";
	}
	
	
}

