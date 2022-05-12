package tk.dqmino.bedtime;

import com.google.gson.Gson;
import tk.dqmino.bedtime.config.Configuration;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.ExecutorService;

public class SimpleConfigurationSerializer implements Serializer {

    private final ExecutorService executorService;
    private final Configuration config;
    private final File configFile;
    private final Gson gson;

    public SimpleConfigurationSerializer(ExecutorService executorService, Configuration config,
                                         File configFile, Gson gson) {
        this.executorService = executorService;
        this.config = config;
        this.configFile = configFile;
        this.gson = gson;
    }

    @Override
    public void serialize() {
        executorService.submit(() -> {
            try (FileWriter myWriter = new FileWriter(configFile)) {
                myWriter.write(Configuration.toSerialized(config, gson));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

}
