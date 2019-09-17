import com.squareup.moshi.Json;

import java.util.ArrayList;
import java.util.List;

public class Province {
    String name="";
    @Json(name = "children")
    List<City> cities=new ArrayList<>();

    public String getName() {
        return name;
    }

    public List<City> getCities() {
        return cities;
    }
}