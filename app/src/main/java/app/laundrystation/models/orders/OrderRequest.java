package app.laundrystation.models.orders;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class OrderRequest{

	@SerializedName("msg")
	private String msg;

	@SerializedName("data")
	private List<OrderDetail> data;

	@SerializedName("status")
	private int status;

	public void setMsg(String msg){
		this.msg = msg;
	}

	public String getMsg(){
		return msg;
	}

	public void setData(List<OrderDetail> data){
		this.data = data;
	}

	public List<OrderDetail> getData(){
		return data;
	}

	public void setStatus(int status){
		this.status = status;
	}

	public int getStatus(){
		return status;
	}

	@Override
 	public String toString(){
		return 
			"OrderRequest{" + 
			"msg = '" + msg + '\'' + 
			",data = '" + data + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}