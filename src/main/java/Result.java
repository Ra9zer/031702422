

import com.squareup.moshi.Json;

import java.util.List;

public class Result {

    @Json(name = "姓名")
    private String name;

    @Json(name = "手机")
    private String phone;

    @Json(name = "地址")
    private List<String> address;

    public String getName() {
        return name;
    }

    public Result(String name, String phone, List<String> address) {
        this.name = name;
        this.phone = phone;
        this.address = address;
        //System.out.println(address);
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<String> getAddress() {
        return address;
    }

    public void setAddress(List<String> address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Result{" +
                "name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", address=" + address +
                '}';
    }
}