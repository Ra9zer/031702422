import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;


public class DataBase {
    public static List<Province> provinces;
    public static void readJsonData() {
        // 读取文件数据
        StringBuilder stringBuilder = new StringBuilder();
        FileUtil.FiletoString(DataBase.class.getResourceAsStream("pcas-code.json")).forEach(stringBuilder::append);
        try {
            Moshi moshi = new Moshi.Builder().build();
            Type type = Types.newParameterizedType(List.class, Province.class);
            JsonAdapter<List<Province>> adapter = moshi.adapter(type);
            DataBase.provinces =adapter.fromJson(stringBuilder.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static List<Province> getProvinces() {
        return provinces;
    }
}