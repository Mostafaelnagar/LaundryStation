package app.laundrystation.models.register;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ReqDetailsModel {
    @SerializedName("status")
    @Expose
    public Integer status;
    @SerializedName("msg")
    @Expose
    public String msg;
    @SerializedName("data")
    @Expose
    public UserData userData;

    public ReqDetailsModel(Integer status, String msg, UserData userData) {
        this.status = status;
        this.msg = msg;
        this.userData = userData;
    }

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

    public UserData getUserData() {
        return userData;
    }

    public void setUserData(UserData userData) {
        this.userData = userData;
    }
}
