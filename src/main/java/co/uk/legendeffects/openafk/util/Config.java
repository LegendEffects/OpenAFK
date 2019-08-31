package co.uk.legendeffects.openafk.util;

import co.uk.legendeffects.openafk.OpenAFK;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class Config {
    private OpenAFK openAFK;
    private HashMap<String, FileConfiguration> configurations = new HashMap<String, FileConfiguration>();

    public Config(OpenAFK openAFK) {
        this.openAFK = openAFK;
    }

    public FileConfiguration getConfig(String configName) {
        if(configurations.containsKey(configName)) {
            return configurations.get(configName);
        } else {
            throw new NullPointerException();
        }
    }

    public void createConfig(String configName) {
        File config = new File(openAFK.getDataFolder(), configName);
        if(!config.exists()) {
            if(config.getParentFile().mkdirs()) {
                openAFK.saveResource(configName, false);
            } else {
                openAFK.getServer().getConsoleSender().sendMessage("Unable to create config folder, please check file permissions.");
            }
        }

        FileConfiguration customConfig = new YamlConfiguration();
        try {
            customConfig.load(config);
        } catch(IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }

        configurations.put(configName, customConfig);
    }
}
