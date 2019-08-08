package app.laundrystation.models.register;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UserData {
    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("social_token")
    @Expose
    public String socialToken;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("email")
    @Expose
    public String email;
    @SerializedName("phone")
    @Expose
    public String phone;
    @SerializedName("img")
    @Expose
    public String img;
    @SerializedName("social_img")
    @Expose
    public String socialImg;
    @SerializedName("jwt")
    @Expose
    public String jwt;
    @SerializedName("activated")
    @Expose
    public Integer activated;
    @SerializedName("addresses")
    @Expose
    public List<Address> addresses = null;

    public UserData() {
    }

    public UserData(Integer id, String socialToken, String name, String email, String phone, String img, String socialImg, String jwt, Integer activated, List<Address> addresses) {
        this.id = id;
        this.socialToken = socialToken;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.img = img;
        this.socialImg = socialImg;
        this.jwt = jwt;
        this.activated = activated;
        this.addresses = addresses;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSocialToken() {
        return socialToken;
    }

    public void setSocialToken(String socialToken) {
        this.socialToken = socialToken;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getSocialImg() {
        return socialImg;
    }

    public void setSocialImg(String socialImg) {
        this.socialImg = socialImg;
    }

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }

    public Integer getActivated() {
        return activated;
    }

    public void setActivated(Integer activated) {
        this.activated = activated;
    }

    public List<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }
}
