import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;


public class FileUtil {
    public static List<String> FiletoString(String file) throws FileNotFoundException {
        FileInputStream inputStream = new FileInputStream(file);
        return FiletoString(inputStream);
    }

    public static List<String> FiletoString(InputStream file) {
        List<String> stringList = new ArrayList<>();
        try {
            InputStreamReader streamReader = new InputStreamReader(file, StandardCharsets.UTF_8);
            BufferedReader reader = new BufferedReader(streamReader);
            String line;
            while ((line = reader.readLine()) != null) {
                stringList.add(line);
            }
            reader.close();
            file.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return stringList;
    }

    public static void StringtoFile(String content, String filepath) {

        FileWriter fwriter = null;
        try {
            fwriter = new FileWriter(filepath);
            fwriter.write(content);
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                fwriter.flush();
                fwriter.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

}
