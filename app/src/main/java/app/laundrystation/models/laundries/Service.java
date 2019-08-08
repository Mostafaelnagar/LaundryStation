package app.laundrystation.models.laundries;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Service implements Serializable {
    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("section_id")
    @Expose
    public Integer sectionId;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("wash")
    @Expose
    public String wash;
    @SerializedName("ironing")
    @Expose
    public String ironing;
    @SerializedName("wash_ironing")
    @Expose
    public String washIroning;
    @SerializedName("img")
    @Expose
    public String img;


    @Expose
    boolean showPopUp=false;

    @Expose
    private int count=0;


    protected Service(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readInt();
        }
        name = in.readString();
        wash = in.readString();
        ironing = in.readString();
        washIroning = in.readString();
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getCount() {
        return count;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWash() {
        return wash;
    }

    public void setWash(String wash) {
        this.wash = wash;
    }

    public String getIroning() {
        return ironing;
    }

    public void setIroning(String ironing) {
        this.ironing = ironing;
    }

    public String getWashIroning() {
        return washIroning;
    }

    public void setWashIroning(String washIroning) {
        this.washIroning = washIroning;
    }

    public Integer getSectionId() {
        return sectionId;
    }

    public void setSectionId(Integer sectionId) {
        this.sectionId = sectionId;
    }

    public boolean isShowPopUp() {
        return showPopUp;
    }

    public void setShowPopUp(boolean showPopUp) {
        this.showPopUp = showPopUp;
    }
}
