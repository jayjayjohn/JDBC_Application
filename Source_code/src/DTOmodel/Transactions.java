package DTOmodel;


//Table: cdw_sapp_creditcard

public class Transactions {
	
	
	
	private int transactionId, day, month, year,branchCode,count;
	private String transactionType,creditCardNum, custSSN;
	private float transactionValue,sum;
	private Customer customer;
	
	public void setCustomer(Customer c) {
		this.customer = c;
	}
	
	public Customer getCustomer() {
		return this.customer;
	}
	
	
//	public String getCustName() {
//		return custName;
//	}
//	public void setCustName(String name) {
//		this.custName=name;
//	}
	public int getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(int transactionId) {
		this.transactionId = transactionId;
	}
	public int getDay() {
		return day;
	}
	public void setDay(int day) {
		this.day = day;
	}
	public int getMonth() {
		return month;
	}
	public void setMonth(int month) {
		this.month = month;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public String getCreditCardNum() {
		return creditCardNum;
	}
	public void setCreditCardNum(String creditCardNum) {
		this.creditCardNum = creditCardNum;
	}
	public String getCustSSN() {
		return custSSN;
	}
	public void setCustSSN(String custSSN) {
		this.custSSN = custSSN;
	}
	public int getBranchCode() {
		return branchCode;
	}
	public void setBranchCode(int branchCode) {
		this.branchCode = branchCode;
	}
	public String getTransactionType() {
		return transactionType;
	}
	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}
	public float getTransactionValue() {
		return transactionValue;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public float getSum() {
		return sum;
	}
	public void setSum(float sum) {
		this.sum = sum;
	}
	public void setTransactionValue(float transactionValue) {
		this.transactionValue = transactionValue;
	}
	
	
}