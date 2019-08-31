package co.uk.legendeffects.openafk;

import co.uk.legendeffects.openafk.commands.OpenAFKCommand;
import co.uk.legendeffects.openafk.handlers.AfkEvent;
import co.uk.legendeffects.openafk.handlers.ReturnEvent;
import co.uk.legendeffects.openafk.util.CheckTask;
import co.uk.legendeffects.openafk.util.ConfigWrapper;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashSet;
import java.util.Set;

public class OpenAFK extends JavaPlugin {

    private static OpenAFK instance;

    private ConfigWrapper config;
    private ConfigWrapper messages;

    public Set<Player> afkPlayers = new HashSet<>();

    @Override
    public void onEnable() {
        instance = this;

        config = new ConfigWrapper(this, "config.yml");
        messages = new ConfigWrapper(this, "messages.yml");

        getServer().getPluginManager().registerEvents(new AfkEvent(), this);
        getServer().getPluginManager().registerEvents(new ReturnEvent(), this);

        getCommand("openafk").setExecutor(new OpenAFKCommand(this));

        new CheckTask(this).runTaskTimerAsynchronously(this, 0L, this.getConfig().getLong("checkInterval"));
    }

    static OpenAFK getPlugin() {
        return instance;
    }

    public FileConfiguration getConfig() {
        return this.config.getRaw();
    }

    public FileConfiguration getMessages() {
        return this.messages.getRaw();
    }
}
