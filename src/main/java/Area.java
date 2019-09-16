import com.squareup.moshi.Json;

import java.util.List;

public class Area {
    String name;
    @Json(name = "children")
    List<Street> strees;
    public String getName() {
        return name;
    }
    public List<Street> getStreets(){
        return strees;
    }
}
