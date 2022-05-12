package tk.dqmino.bedtime;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import tk.dqmino.bedtime.command.BedTimeCommand;
import tk.dqmino.bedtime.config.Configuration;
import tk.dqmino.bedtime.config.impl.SimpleConfiguration;
import tk.dqmino.bedtime.listener.InventoryDrawingListener;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Mod(modid = BedTime.MODID, version = BedTime.VERSION)
public class BedTime {
    public static final String MODID = "bedtime";
    public static final String VERSION = "1.0";
    public static final String PREFIX = EnumChatFormatting.GOLD + "Bed" + EnumChatFormatting.RED + "Time>>> " +
            EnumChatFormatting.RESET;
    private Configuration config;
    private Gson gson;
    private File configFile;
    private ExecutorService executorService;


    @EventHandler
    public void preinit(FMLPreInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(new InventoryDrawingListener(config));
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        executorService = Executors.newCachedThreadPool();
        gson = new GsonBuilder().setPrettyPrinting().create();
        Loader.instance().getConfigDir().mkdirs();
        String path = Loader.instance().getConfigDir().getAbsolutePath();
        path = path.endsWith(File.separator) ? path.substring(0, path.length() - 1) : path;
        configFile = new File(path + File.separator + "config.json");

        try {
            if (configFile.createNewFile()) {
                // first time launching
                FileWriter myWriter = new FileWriter(configFile);
                myWriter.write(Configuration.toSerialized(new SimpleConfiguration(), gson));
                myWriter.close();
            }
            config = Configuration.of(configFile, gson);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ClientCommandHandler.instance.registerCommand(new BedTimeCommand(config,
                new SimpleConfigurationSerializer(executorService, config, configFile, gson)
        ));
    }


}
