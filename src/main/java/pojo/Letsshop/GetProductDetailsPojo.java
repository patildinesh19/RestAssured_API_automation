package pojo.Letsshop;

import java.util.List;

public class GetProductDetailsPojo
{
	private List<Data> data;
	private int count;
	private String message;
	
	public List<Data> getData() {
		return data;
	}
	public void setData(List<Data> data) {
		this.data = data;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}	
}
