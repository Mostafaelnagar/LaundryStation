package app.laundrystation.models.chat;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Message {
    @SerializedName("content")
    private String content;

    @SerializedName("sent_from")
    private String sentFrom;

    public Message(String content, String sentFrom) {
        this.content = content;
        this.sentFrom = sentFrom;
    }

    public void setContent(String content){
        this.content = content;
    }

    public String getContent(){
        return content;
    }

    public void setSentFrom(String sentFrom){
        this.sentFrom = sentFrom;
    }

    public String getSentFrom(){
        return sentFrom;
    }

    @Override
    public String toString(){
        return
                "Message{" +
                        "content = '" + content + '\'' +
                        ",sent_from = '" + sentFrom + '\'' +
                        "}";
    }
}
