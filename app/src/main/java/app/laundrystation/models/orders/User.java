package app.laundrystation.models.orders;

import java.util.List;
import com.google.gson.annotations.SerializedName;

import app.laundrystation.models.register.Address;

public class User{

	@SerializedName("social_img")
	private String socialImg;

	@SerializedName("social_token")
	private String socialToken;

	@SerializedName("img")
	private String img;

	@SerializedName("addresses")
	private List<Address> addresses;

	@SerializedName("phone")
	private String phone;

	@SerializedName("jwt")
	private String jwt;

	@SerializedName("name")
	private String name;

	@SerializedName("id")
	private int id;

	@SerializedName("email")
	private String email;

	@SerializedName("activated")
	private int activated;

	public void setSocialImg(String socialImg){
		this.socialImg = socialImg;
	}

	public String getSocialImg(){
		return socialImg;
	}

	public void setSocialToken(String socialToken){
		this.socialToken = socialToken;
	}

	public String getSocialToken(){
		return socialToken;
	}

	public void setImg(String img){
		this.img = img;
	}

	public String getImg(){
		return img;
	}

	public void setAddresses(List<Address> addresses){
		this.addresses = addresses;
	}

	public List<Address> getAddresses(){
		return addresses;
	}

	public void setPhone(String phone){
		this.phone = phone;
	}

	public String getPhone(){
		return phone;
	}

	public void setJwt(String jwt){
		this.jwt = jwt;
	}

	public String getJwt(){
		return jwt;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setEmail(String email){
		this.email = email;
	}

	public String getEmail(){
		return email;
	}

	public void setActivated(int activated){
		this.activated = activated;
	}

	public int getActivated(){
		return activated;
	}

	@Override
 	public String toString(){
		return 
			"User{" + 
			"social_img = '" + socialImg + '\'' + 
			",social_token = '" + socialToken + '\'' + 
			",img = '" + img + '\'' + 
			",addresses = '" + addresses + '\'' + 
			",phone = '" + phone + '\'' + 
			",jwt = '" + jwt + '\'' + 
			",name = '" + name + '\'' + 
			",id = '" + id + '\'' + 
			",email = '" + email + '\'' + 
			",activated = '" + activated + '\'' + 
			"}";
		}
}