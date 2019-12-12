package co.uk.legendeffects.openafk.oldScript.actions;

import co.uk.legendeffects.openafk.OpenAFK;
import co.uk.legendeffects.openafk.oldScript.Action;
import co.uk.legendeffects.openafk.oldScript.ActionType;
import com.nametagedit.plugin.NametagEdit;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class NameTagAction extends Action {
    private final Plugin plugin;

    public NameTagAction(Plugin plugin) {
        super("nametag");

        this.plugin = plugin;
    }

    @Override
    public void execute(Player player, ActionType type, String[] args) {
        if(!Bukkit.getPluginManager().isPluginEnabled("NametagEdit")) {
            plugin.getLogger().warning("NametagEdit is required to use the NametagAction.");
            return;
        }

        if(args.length == 0) {
            plugin.getLogger().warning("Nametag action needs more arguments.");
            return;
        }

        System.out.println("Prefix");

        NametagEdit.getApi().setPrefix(player, OpenAFK.parse(player, args[0]));
        if(args.length > 1) {
            System.out.println("Suffix");
            NametagEdit.getApi().setPrefix(player, OpenAFK.parse(player, args[1]));
        }
    }
}
