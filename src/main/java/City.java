
import com.squareup.moshi.Json;

import java.util.List;

public class City {
    String name;
    @Json(name = "children")
    List<Area> areas;

    public String getName() {
        return name;
    }

    public List<Area> getAreas() {
        return areas;
    }
}
