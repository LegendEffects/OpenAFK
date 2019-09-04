package co.uk.legendeffects.openafk.detection;

import co.uk.legendeffects.openafk.OpenAFK;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class FishingDetection implements Listener {
    private final OpenAFK plugin;

    public FishingDetection(OpenAFK plugin) {
        this.plugin = plugin;
    }

    private Set<Player> playersFishing = new HashSet<>();
    private HashMap<Player, Integer> violationLevel = new HashMap<>();

    @EventHandler
    public void fishEvent(PlayerFishEvent event) {
        Player player = event.getPlayer();

        if(event.getState() == PlayerFishEvent.State.FISHING) {
            playersFishing.add(player);
        } else {
            playersFishing.remove(player);
        }
    }

    @EventHandler
    public void playerInteract(PlayerInteractEvent event) {
        if(event.getAction() == Action.RIGHT_CLICK_BLOCK && event.getMaterial() == Material.FISHING_ROD && playersFishing.contains(event.getPlayer())) {
            if(event.getClickedBlock().getBlockData().getMaterial() == Material.NOTE_BLOCK) {
                increaseViolationLevel(event.getPlayer());
            }
        }
    }

    private void increaseViolationLevel(Player player) {
        if(!violationLevel.containsKey(player)) {
            violationLevel.put(player, 1);
        } else {
            violationLevel.put(player, violationLevel.get(player)+1);
        }

        FileConfiguration config = plugin.getConfig();
        if(violationLevel.get(player) == config.getInt("detection.fishing.violationsNeeded")) {

            // Force look down
            if(config.getBoolean("detection.fishing.enableSetPitch")) {
                Location newLocation = player.getLocation();
                newLocation.setPitch((float) config.getDouble("detection.fishing.setPitch", 100.00));
                player.teleport(newLocation);
            }
            player.sendMessage(OpenAFK.parse(player, config.getString("detection.fishing.violationMessage")));

            violationLevel.remove(player);
        }
    }
}
