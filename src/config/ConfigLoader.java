package config;

import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;

public class ConfigLoader {
    public static Config cargarConfig(String rutaArchivo) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(new File(rutaArchivo), Config.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

