package Server;

import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

public class Configurations {
    private static Configurations instance = null;
    private static Integer lock=0;
    private Properties properties;
    private FileOutputStream output;


    public static Configurations getInstance() throws IOException {
        if(instance == null)
            instance = new Configurations();
        return instance;
    }

    private Configurations() throws IOException {
        properties = new Properties();
        output = new FileOutputStream("resources/config.properties");
        properties.setProperty("threadPoolSize","20");
        properties.setProperty("mazeGeneratingAlgorithm","MyMazeGenerator");
        properties.setProperty("mazeSearchingAlgorithm","BreadthFirstSearch");
        properties.store(output,null);
    }

    public void setProperties(String key, String value) throws IOException {
        properties.setProperty(key,value);
        properties.store(output, null);
    }
    public String getProperties(String key) {
        return properties.getProperty(key);
    }
}

