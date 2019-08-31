package co.uk.legendeffects.openafk.util;

import com.google.common.base.Charsets;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


/**
 * Thanks to Choco <3
 * https://github.com/2008Choco/VeinMiner/blob/master/src/main/java/wtf/choco/veinminer/utils/ConfigWrapper.java
 */
public class ConfigWrapper {
    private final JavaPlugin plugin;
    private final String path;

    private final File file;
    private FileConfiguration config;

    public ConfigWrapper(JavaPlugin plugin, String path) {
        this.plugin = plugin;
        this.path = path;

        this.file = new File(plugin.getDataFolder(), path);
        if (!file.exists()) {
            plugin.saveResource(path, false);
        }

        this.config = YamlConfiguration.loadConfiguration(file);
    }

    public FileConfiguration getRaw() {
        return config;
    }

    public void save() {
        try {
            this.config.save(file);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void reload() {
        this.config = YamlConfiguration.loadConfiguration(file);

        final InputStream defaultConfigStream = plugin.getResource(path);
        if (defaultConfigStream == null) {
            return;
        }

        this.config.setDefaults(YamlConfiguration.loadConfiguration(new InputStreamReader(defaultConfigStream, Charsets.UTF_8)));
    }
}
