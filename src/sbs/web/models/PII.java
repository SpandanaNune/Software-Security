package sbs.web.models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "pii")
public class PII {
	@Id
	private String userName;
	
	private String oldSSN;
	
	private String newSSN;
	
	private boolean isApproved;
	
	private boolean isMerchant;
	
	

	public boolean isMerchant() {
		return isMerchant;
	}

	public void setMerchant(boolean isMerchant) {
		this.isMerchant = isMerchant;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getOldSSN() {
		return oldSSN;
	}

	public void setOldSSN(String oldSSN) {
		this.oldSSN = oldSSN;
	}

	public String getNewSSN() {
		return newSSN;
	}

	public void setNewSSN(String newSSN) {
		this.newSSN = newSSN;
	}

	public boolean isApproved() {
		return isApproved;
	}

	public void setApproved(boolean isApproved) {
		this.isApproved = isApproved;
	}
	
	

}
