package by.clevertec.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import org.yaml.snakeyaml.Yaml;

public class YamlReader {

    public static String chooseCacheType() {
        try (InputStream inputStream = YamlReader.class.getClassLoader().getResourceAsStream("application.yml")) {
            Yaml yaml = new Yaml();
            Map<String, Object> data = yaml.load(inputStream);
            return String.valueOf(data.get("cache"));
        } catch (IOException e) {
            throw new RuntimeException("Error reading property from application.yml", e);
        }
    }

    public static String chooseCacheCapacity() {
        try (InputStream inputStream = YamlReader.class.getClassLoader().getResourceAsStream("application.yml")) {
            Yaml yaml = new Yaml();
            Map<String, Object> data = yaml.load(inputStream);
            return String.valueOf(data.get("capacity"));
        } catch (IOException e) {
            throw new RuntimeException("Error reading property from application.yml", e);
        }
    }
}
