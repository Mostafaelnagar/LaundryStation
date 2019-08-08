package app.laundrystation.models.laundries;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Request_Details {
    @SerializedName("status")
    @Expose
    public Integer status;
    @SerializedName("msg")
    @Expose
    public String msg;
    @SerializedName("data")
    @Expose
    public Request_LaundryData data;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Request_LaundryData getData() {
        return data;
    }

    public void setData(Request_LaundryData data) {
        data = data;
    }
}
