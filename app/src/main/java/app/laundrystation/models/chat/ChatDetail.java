package app.laundrystation.models.chat;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ChatDetail {
    @SerializedName("sender_type")
    private String senderType;

    @SerializedName("messages")
    private List<Message> messages;

    @SerializedName("target_id")
    private int targetId;

    @SerializedName("order_id")
    private int orderId;

    @SerializedName("sender_id")
    private int senderId;

    public void setSenderType(String senderType){
        this.senderType = senderType;
    }

    public String getSenderType(){
        return senderType;
    }

    public void setMessages(List<Message> messages){
        this.messages = messages;
    }

    public List<Message> getMessages(){
        return messages;
    }

    public void setTargetId(int targetId){
        this.targetId = targetId;
    }

    public int getTargetId(){
        return targetId;
    }

    public void setOrderId(int orderId){
        this.orderId = orderId;
    }

    public int getOrderId(){
        return orderId;
    }

    public void setSenderId(int senderId){
        this.senderId = senderId;
    }

    public int getSenderId(){
        return senderId;
    }

    @Override
    public String toString(){
        return
                "ChatDetail{" +
                        "sender_type = '" + senderType + '\'' +
                        ",messages = '" + messages + '\'' +
                        ",target_id = '" + targetId + '\'' +
                        ",order_id = '" + orderId + '\'' +
                        ",sender_id = '" + senderId + '\'' +
                        "}";
    }
}
