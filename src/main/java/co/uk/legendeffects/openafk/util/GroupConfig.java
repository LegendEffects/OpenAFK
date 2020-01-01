package co.uk.legendeffects.openafk.util;

import co.uk.legendeffects.openafk.OpenAFK;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class GroupConfig {
    private final HashMap<String, ConfigurationSection> groups = new HashMap<>();

    private final OpenAFK plugin;

    public GroupConfig(OpenAFK plugin) {
        this.plugin = plugin;
    }

    public void init() {
        FileConfiguration config = plugin.getConfig();

        config.getConfigurationSection("groups").getKeys(false).forEach(key -> {
            ConfigurationSection group = config.getConfigurationSection("groups."+key);
            if(group == null) return;

            this.groups.put(key, group);
        });
    }

    public ConfigurationSection getGroupForPlayer(Player player) {
        for(String name : this.groups.keySet()) {
            if(player.hasPermission("openafk.group."+name)) {
                return this.groups.get(name);
            }
        }

        return null;
    }

    public ConfigurationSection getGroup(String group) {
        return this.groups.get(group);
    }

    public HashMap<String, ConfigurationSection> getGroups() {
        return this.groups;
    }
}
