package bean;

public class Properties {
	private String address;
	private String status;
	public String getAddress() {
		return address;
	}
	public Properties() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "Veg [address=" + address + ", status=" + status + "]";
	}
	public Properties(String address, String status) {
		super();
		this.address = address;
		this.status = status;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	
	


}
