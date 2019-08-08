package app.laundrystation.models.settings;

 import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class SettingsData implements Serializable {

	@SerializedName("terms")
	private String terms;

	@SerializedName("about")
	private String about;

	public void setTerms(String terms){
		this.terms = terms;
	}

	public String getTerms(){
		return terms;
	}

	public void setAbout(String about){
		this.about = about;
	}

	public String getAbout(){
		return about;
	}

	@Override
 	public String toString(){
		return 
			"SettingsData{" +
			"terms = '" + terms + '\'' + 
			",about = '" + about + '\'' + 
			"}";
		}
}