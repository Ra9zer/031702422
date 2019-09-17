import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {
        DataBase.readJsonData();
        String inputPath = args[0];
        List<String> input = FileUtil.FiletoString(inputPath);
        List<Result> results = new ArrayList<>();
        input.forEach(s -> results.add(new Separation(s).separation().toResult()));
        Moshi moshi = new Moshi.Builder().build();
        Type type = Types.newParameterizedType(List.class, Result.class);
        JsonAdapter<List<Result>> jsonadapter = moshi.adapter(type);
        String output = jsonadapter.toJson(results);
        String outputPath = args[1];
        FileUtil.StringtoFile(output,outputPath);
    }

}