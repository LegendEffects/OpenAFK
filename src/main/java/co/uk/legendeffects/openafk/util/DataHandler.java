package co.uk.legendeffects.openafk.util;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class DataHandler {
    private final JavaPlugin plugin;
    private final HashMap<Player, FileConfiguration> attributedData = new HashMap<>();

    public DataHandler(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    private FileConfiguration loadPlayerData(Player player) {
        File file = getPlayerDataFile(player);

        if (!file.exists()) {
            file.getParentFile().mkdirs();
            try {
                file.createNewFile();
            } catch(IOException e) {
                e.printStackTrace();
            }
        }

        FileConfiguration data = YamlConfiguration.loadConfiguration(file);
        attributedData.put(player, data);

        return data;
    }

    public void savePlayer(Player player) {
        try {
            attributedData.get(player).save(getPlayerDataFile(player));
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public FileConfiguration getPlayer(Player player) {
        if (attributedData.containsKey(player)) {
            return attributedData.get(player);
        }

        return loadPlayerData(player);
    }

    public void unloadPlayer(Player player) {
        attributedData.remove(player);
    }

    public boolean deletePlayer(Player player) {
        unloadPlayer(player);
        return getPlayerDataFile(player).delete();
    }

    public boolean playerHasData(Player player) {
        return getPlayerDataFile(player).exists();
    }

    private File getPlayerDataFile(Player player) {
        return new File(plugin.getDataFolder(), "players" + File.separator + player.getUniqueId() + ".yml");
    }
}
