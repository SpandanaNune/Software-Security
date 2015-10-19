package sbs.web.models;

public class TransactionLog {
	
	String logFilter;
	String date;
	long accountNo;
	String name;
	
	

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public long getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(long accountNo) {
		this.accountNo = accountNo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLogFilter() {
		return logFilter;
	}

	public void setLogFilter(String logFilter) {
		this.logFilter = logFilter;
	}
	
	

}
