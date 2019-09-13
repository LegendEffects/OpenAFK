package co.uk.legendeffects.openafk.script.actions;

import co.uk.legendeffects.openafk.OpenAFK;
import co.uk.legendeffects.openafk.script.Action;
import co.uk.legendeffects.openafk.script.ActionType;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;

public class ActionBarAction extends Action {

    private BukkitTask updateQueue;
    private OpenAFK plugin;
    private final HashMap<Player, String> recipients = new HashMap<>();

    public ActionBarAction(OpenAFK plugin) {
        super("actionbar");
        this.plugin = plugin;
    }

    @Override
    public void execute(Player player, ActionType type, String[] args) {
        if(args.length < 1 || args.length > 3) {
            return;
        }

        if(updateQueue == null && args[0].equals("constant")) {
            updateQueue = new BukkitRunnable() {
                @Override
                public void run() {
                    recipients.forEach((playerLoop, message) -> { sendActionBar(playerLoop, message); });
                }
            }.runTaskTimer(plugin, 0L, 40L);
        }

        if(args[0].equals("constant")) {
            if(args.length == 3 && args[2].equals("all")) {
                if(args[1].equals("")) {
                    recipients.clear();
                    return;
                }

                Bukkit.getOnlinePlayers().forEach(playerLoop -> { recipients.put(playerLoop, args[1]); });
                return;
            }

            if(args[1].equals("REMOVE")) {
                recipients.remove(player);
                return;
            }

            recipients.put(player, args[1]);
        } else {
            if(args.length == 2 && args[1].equals("all")) {
                Bukkit.getOnlinePlayers().forEach(playerLoop -> { sendActionBar(playerLoop, args[0]); });
                return;
            }

            sendActionBar(player, args[0]);
        }

    }

    private void sendActionBar(Player player, String message) {
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(OpenAFK.parse(player, message)));
    }
}
