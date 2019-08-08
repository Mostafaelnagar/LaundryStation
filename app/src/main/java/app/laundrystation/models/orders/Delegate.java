package app.laundrystation.models.orders;

import com.google.gson.annotations.SerializedName;

public class Delegate{

	@SerializedName("img")
	private String img;

	@SerializedName("phone")
	private String phone;

	@SerializedName("jwt")
	private Object jwt;

	@SerializedName("name")
	private String name;

	@SerializedName("id")
	private Object id;

	@SerializedName("email")
	private Object email;

	public void setImg(String img){
		this.img = img;
	}

	public String getImg(){
		return img;
	}

	public void setPhone(String phone){
		this.phone = phone;
	}

	public String getPhone(){
		return phone;
	}

	public void setJwt(Object jwt){
		this.jwt = jwt;
	}

	public Object getJwt(){
		return jwt;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setId(Object id){
		this.id = id;
	}

	public Object getId(){
		return id;
	}

	public void setEmail(Object email){
		this.email = email;
	}

	public Object getEmail(){
		return email;
	}

	@Override
 	public String toString(){
		return 
			"Delegate{" + 
			"img = '" + img + '\'' + 
			",phone = '" + phone + '\'' + 
			",jwt = '" + jwt + '\'' + 
			",name = '" + name + '\'' + 
			",id = '" + id + '\'' + 
			",email = '" + email + '\'' + 
			"}";
		}
}