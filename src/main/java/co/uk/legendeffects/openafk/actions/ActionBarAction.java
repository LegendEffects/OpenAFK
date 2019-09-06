package co.uk.legendeffects.openafk.actions;

import co.uk.legendeffects.openafk.OpenAFK;
import co.uk.legendeffects.openafk.events.PlayerAfkEvent;
import co.uk.legendeffects.openafk.events.PlayerReturnEvent;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;

public class ActionBarAction implements ActionExecutor {

    private final HashMap<Player, String> recipients = new HashMap<>();

    @Override
    public boolean isEnabled(OpenAFK plugin) {
        boolean enabled = plugin.getConfig().getBoolean("actions.actionbar.enabled");
        if(enabled) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    recipients.forEach((player, message) -> { sendActionBar(player, message); });
                }
            }.runTaskTimer(plugin, 0L, 40L);
        }

        return enabled;
    }

    @Override
    public void onAfk(PlayerAfkEvent event, OpenAFK plugin) {
        recipients.put(event.getPlayer(), plugin.getConfig().getString("actions.actionbar.message"));
    }

    @Override
    public void onReturn(PlayerReturnEvent event, OpenAFK plugin) {
        recipients.remove(event.getPlayer());
        sendActionBar(event.getPlayer(), plugin.getConfig().getString("actions.actionbar.returnMessage"));
    }

    private void sendActionBar(Player player, String message) {
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(OpenAFK.parse(player, message)));
    }
}
