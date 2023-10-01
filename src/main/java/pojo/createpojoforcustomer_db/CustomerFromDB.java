package pojo.createpojoforcustomer_db;

public class CustomerFromDB 
{
	private int customerID;
	private String customername;
	private String purchesdate;
	private int amount;
	private String location;
	
	public int getCustomerID() {
		return customerID;
	}
	public void setCustomerID(int customerID) {
		this.customerID = customerID;
	}
	public String getCustomername() {
		return customername;
	}
	public void setCustomername(String customername) {
		this.customername = customername;
	}
	public String getPurchesdate() {
		return purchesdate;
	}
	public void setPurchesdate(String purchesdate) {
		this.purchesdate = purchesdate;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}

}
