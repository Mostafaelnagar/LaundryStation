package app.laundrystation.models.laundries;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

class Doc {
    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("doc")
    @Expose
    public String doc;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDoc() {
        return doc;
    }

    public void setDoc(String doc) {
        this.doc = doc;
    }
}
