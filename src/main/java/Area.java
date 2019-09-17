import com.squareup.moshi.Json;

import java.util.ArrayList;
import java.util.List;

public class Area {
    String name="";
    @Json(name = "children")
    List<Street> strees = new ArrayList<>();
    public String getName() {
        return name;
    }
    public List<Street> getStreets(){
        return strees;
    }
}
