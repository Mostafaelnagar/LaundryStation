package app.laundrystation.models.laundries;

import android.graphics.drawable.Drawable;

public class SectionModel {
    public int section_id;
    public String name;
    public Drawable imageUrl;

    public SectionModel() {
    }

    public SectionModel(int section_id, String name, Drawable imageUrl) {
        this.section_id = section_id;
        this.name = name;
        this.imageUrl = imageUrl;
    }

    public int getSection_id() {
        return section_id;
    }

    public void setSection_id(int section_id) {
        this.section_id = section_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Drawable getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(Drawable imageUrl) {
        this.imageUrl = imageUrl;
    }
}
