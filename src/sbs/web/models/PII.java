package sbs.web.models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "pii")
public class PII {
	@Id
	private String userName;
	
	private long oldSSN;
	
	private long newSSN;
	
	private boolean isApproved;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public long getOldSSN() {
		return oldSSN;
	}

	public void setOldSSN(long oldSSN) {
		this.oldSSN = oldSSN;
	}

	public long getNewSSN() {
		return newSSN;
	}

	public void setNewSSN(long newSSN) {
		this.newSSN = newSSN;
	}

	public boolean isApproved() {
		return isApproved;
	}

	public void setApproved(boolean isApproved) {
		this.isApproved = isApproved;
	}
	
	

}
