package co.uk.legendeffects.openafk.script.actions;

import co.uk.legendeffects.openafk.OpenAFK;
import co.uk.legendeffects.openafk.script.AbstractAction;
import co.uk.legendeffects.openafk.script.ActionType;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.Map;

public class ActionBarAction extends AbstractAction {
    private BukkitTask updateQueue;
    private HashMap<Player, String> recipients = new HashMap<>();
    private final Plugin plugin;

    public ActionBarAction(Plugin plugin) {
        super("actionbar");
        this.plugin = plugin;
    }

    @Override
    public void execute(Player player, ActionType type, Map<String, String> config) {

        // Create a queue runnable if one has no yet been made and it's a permanent message.
        if(updateQueue == null && config.containsKey("permanent")) {
            updateQueue = new BukkitRunnable() {
                @Override
                public void run() {
                    recipients.forEach((playerLoop, message) -> { sendActionBar(playerLoop, message); });
                }
            }.runTaskTimer(plugin, 0L, 40L);
        }

        // Reset
        if(config.containsKey("reset")) {
            if(config.getOrDefault("to", "player").equalsIgnoreCase("player")) {
                recipients.remove(player);
                return;
            }

            recipients.clear();
            return;
        }

        // Adding to the queue
        if(config.getOrDefault("to", "player").equalsIgnoreCase("player")) {
            if(config.containsKey("permanent")) {
                recipients.put(player, config.get("content"));
                return;
            }

            sendActionBar(player, config.get("content"));
        } else {
            if(config.containsKey("permanent")) {
                Bukkit.getOnlinePlayers().forEach(playerLoop -> { recipients.put(playerLoop, config.get("content")); });
                return;
            }

            Bukkit.getOnlinePlayers().forEach(playerLoop -> {
                sendActionBar(player, config.get("content"));
            });
        }


    }

    @Override
    public boolean verifySyntax(Map<String, String> actionConfig, Plugin plugin) {
        if(actionConfig.containsKey("reset")) {
            return true;
        }

        if(actionConfig.containsKey("to") && (!actionConfig.get("to").equalsIgnoreCase("player") && actionConfig.get("to").equalsIgnoreCase("all"))) {
            plugin.getLogger().warning("[ActionBarAction] Invalid to parameter value, expected \"player\" or \"all\".");
            return false;
        }

        if(!actionConfig.containsKey("content")) {
            plugin.getLogger().warning("[ActionBarAction] No content parameter was provided.");
            return false;
        }

        return true;
    }

    private void sendActionBar(Player player, String message) {
        System.out.println(this.recipients);
    }
}
