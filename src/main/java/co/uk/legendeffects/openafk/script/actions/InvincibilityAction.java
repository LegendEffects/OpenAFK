package co.uk.legendeffects.openafk.script.actions;

import co.uk.legendeffects.openafk.OpenAFK;
import co.uk.legendeffects.openafk.script.AbstractAction;
import co.uk.legendeffects.openafk.script.ActionType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.plugin.Plugin;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class InvincibilityAction extends AbstractAction implements Listener {
    private final OpenAFK plugin;

    public InvincibilityAction(OpenAFK plugin) {
        super("invincibility");

        this.plugin = plugin;
    }

    private Set<Player> invinciblePlayers = new HashSet<>();

    @Override
    public void execute(Player player, ActionType type, Map<String, String> config) {
        if(config.get("to").equalsIgnoreCase("true")) {
            invinciblePlayers.add(player);
        } else if(config.get("to").equalsIgnoreCase("false")) {
            invinciblePlayers.remove(player);
        }
    }

    @Override
    public boolean verifySyntax(Map<String, String> actionConfig, Plugin plugin) {
        if(!actionConfig.containsKey("to")) {
            plugin.getLogger().warning("[InvincibilityAction] No 'to' parameter was provided.");
            return false;
        }

        if(!actionConfig.get("to").equalsIgnoreCase("true") && !actionConfig.get("to").equalsIgnoreCase("false")) {
            plugin.getLogger().warning("[InvincibilityAction] Invalid 'to' parameter. Expected true or false.");
            return false;
        }

        return true;
    }

    @EventHandler
    public void onHit(EntityDamageByEntityEvent event) {
        if(!(event.getEntity() instanceof Player)) {
            return;
        }

        Player target = (Player) event.getEntity();
        if(!this.invinciblePlayers.contains(target)) {
            return;
        }

        event.setCancelled(true);

        if(event.getDamager() instanceof Player) {
            Player attacker = (Player) event.getDamager();

            String message = this.plugin.getConfig().getString("messages.playerIsInvincible", "");
            if(message != null && message.length() != 0) attacker.sendMessage(OpenAFK.parse(target, message));
        }


    }
}
