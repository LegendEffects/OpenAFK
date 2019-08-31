package co.uk.legendeffects.openafk;

import co.uk.legendeffects.openafk.commands.Test;
import co.uk.legendeffects.openafk.handlers.AfkEvent;
import co.uk.legendeffects.openafk.handlers.ReturnEvent;
import co.uk.legendeffects.openafk.util.CheckTask;
import co.uk.legendeffects.openafk.util.Config;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public final class Core extends JavaPlugin {

    private Config config;

    @Override
    public void onEnable() {
        this.config = new Config(this);
        this.config.createConfig("messages.yml");
        this.config.createConfig("config.yml");

        getCommand("test").setExecutor(new Test(this));

        getServer().getPluginManager().registerEvents(new AfkEvent(), this);
        getServer().getPluginManager().registerEvents(new ReturnEvent(), this);

        new CheckTask(this).runTaskTimerAsynchronously(this, 0L, this.getConfig("config.yml").getLong("checkInterval"));
    }

    public FileConfiguration getConfig(String configName) {
        return config.getConfig(configName);
    }
}
