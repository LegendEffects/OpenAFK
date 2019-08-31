package co.uk.legendeffects.openafk.util;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import java.util.UUID;

public class LocationHelper {
    public String serialize(Location l) {
        return l.getWorld().getUID().toString()+","+l.getBlockX()+","+l.getBlockY()+","+l.getBlockZ()+","+l.getPitch()+","+l.getYaw();
    }

    public Location deserialize(String l) {
        final String[] parts = l.split(",");

        final World w = Bukkit.getServer().getWorld(UUID.fromString(parts[0]));
        final double x = Integer.parseInt(parts[1]);
        final double y = Integer.parseInt(parts[2]);
        final double z = Integer.parseInt(parts[3]);

        final float pitch = Float.parseFloat(parts[4]);
        final float yaw = Float.parseFloat(parts[5]);

        return new Location(w, x, y, z, yaw, pitch);
    }
}
