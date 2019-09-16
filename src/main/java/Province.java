import com.squareup.moshi.Json;

import java.util.List;

public class Province {
    String name;
    @Json(name = "children")
    List<City> cities;

    public String getName() {
        return name;
    }

    public List<City> getCities() {
        return cities;
    }
}