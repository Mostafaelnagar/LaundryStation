package app.laundrystation.models;

 import com.google.gson.annotations.SerializedName;

public class OrderObject {

    @SerializedName("service_type")
    private String serviceType;

    @SerializedName("service_price")
    private String servicePrice;

    @SerializedName("service_name")
    private String serviceName;

    @SerializedName("service_id")
    private int serviceId;

    @SerializedName("service_count")
    private int serviceCount;

    @SerializedName("service_total")
    private double serviceTotal;
    @SerializedName("img")
    private String img;

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    private int position;

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServicePrice(String servicePrice) {
        this.servicePrice = servicePrice;
    }

    public String getServicePrice() {
        return servicePrice;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceId(int serviceId) {
        this.serviceId = serviceId;
    }

    public int getServiceId() {
        return serviceId;
    }

    public void setServiceCount(int serviceCount) {
        this.serviceCount = serviceCount;
    }

    public int getServiceCount() {
        return serviceCount;
    }

    public void setServiceTotal(int serviceTotal) {
        this.serviceTotal = serviceTotal;
    }

    public double getServiceTotal() {
        return serviceTotal;
    }

    @Override
    public String toString() {
        return
                "ServicesItem{" +
                        "service_type = '" + serviceType + '\'' +
                        ",service_price = '" + servicePrice + '\'' +
                        ",service_name = '" + serviceName + '\'' +
                        ",service_id = '" + serviceId + '\'' +
                        ",service_count = '" + serviceCount + '\'' +
                        ",service_total = '" + serviceTotal + '\'' +
                        "}";
    }
}
