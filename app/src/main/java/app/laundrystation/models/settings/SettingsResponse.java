package app.laundrystation.models.settings;

 import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class SettingsResponse implements Serializable {

	@SerializedName("msg")
	private String msg;

	@SerializedName("data")
	private SettingsData data;

	@SerializedName("status")
	private int status;

	public void setMsg(String msg){
		this.msg = msg;
	}

	public String getMsg(){
		return msg;
	}

	public void setData(SettingsData data){
		this.data = data;
	}

	public SettingsData getData(){
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
			"SettingsResponse{" + 
			"msg = '" + msg + '\'' + 
			",data = '" + data + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}