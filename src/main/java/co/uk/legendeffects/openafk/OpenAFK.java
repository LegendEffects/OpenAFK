package co.uk.legendeffects.openafk;

import co.uk.legendeffects.openafk.handlers.AfkEvent;
import co.uk.legendeffects.openafk.handlers.ReturnEvent;
import co.uk.legendeffects.openafk.util.CheckTask;
import co.uk.legendeffects.openafk.util.Config;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashSet;
import java.util.Set;

public class OpenAFK extends JavaPlugin {

    private static OpenAFK instance;

    static OpenAFK getInstance() {
        return instance;
    }

    private Config config;
    public Set<Player> afkPlayers = new HashSet<>();

    @Override
    public void onEnable() {
        instance = this;

        this.config = new Config(this);
        this.config.createConfig("messages.yml");
        this.config.createConfig("config.yml");



        getServer().getPluginManager().registerEvents(new AfkEvent(), this);
        getServer().getPluginManager().registerEvents(new ReturnEvent(), this);

        new CheckTask(this).runTaskTimerAsynchronously(this, 0L, this.getConfig("config.yml").getLong("checkInterval"));
    }

    public FileConfiguration getConfig(String configName) {
        return config.getConfig(configName);
    }
}
