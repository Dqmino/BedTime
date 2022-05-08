package tk.dqmino.bedtime.config;

import com.google.gson.Gson;
import tk.dqmino.bedtime.config.impl.SimpleConfiguration;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public interface Configuration {
    static Configuration of(File file, Gson gson) throws IOException {
        StringBuilder builder = new StringBuilder();
        Files.lines(Paths.get(file.getAbsolutePath()), StandardCharsets.UTF_8).forEach(builder::append);
        return gson.fromJson(builder.toString(), SimpleConfiguration.class);
    }

    static String toSerialized(Configuration config, Gson gson) {
        return gson.toJson(config);
    }

    boolean isModEnabled();

    int getX();

    void setX(int x);

    int getY();

    void setY(int y);

    double getSize();

    void setSize(double size);

    void setEnable(boolean bool);
}
