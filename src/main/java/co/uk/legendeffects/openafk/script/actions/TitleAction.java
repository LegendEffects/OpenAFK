package co.uk.legendeffects.openafk.script.actions;

import co.uk.legendeffects.openafk.OpenAFK;
import co.uk.legendeffects.openafk.script.AbstractAction;
import co.uk.legendeffects.openafk.script.ActionType;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.Map;

public class TitleAction extends AbstractAction {
    public TitleAction() {
        super("title");
    }

    @Override
    public void execute(Player player, ActionType type, Map<String, String> config) {
        if(config.containsKey("reset")) {
            player.resetTitle();
            return;
        }

        int stayFor;
        if(config.get("stay").equalsIgnoreCase("permanent"))
            stayFor = 999999999;
        else
            stayFor = Integer.parseInt(config.get("stay"));

        int fadeIn = Integer.parseInt(config.get("fadeIn"));
        int fadeOut = Integer.parseInt(config.get("fadeOut"));

        player.sendTitle(OpenAFK.parse(player, config.get("title")), OpenAFK.parse(player, config.getOrDefault("subtitle", "")), fadeIn, stayFor, fadeOut);
    }

    @Override
    public boolean verifySyntax(Map<String, String> actionConfig, Plugin plugin) {
        if(actionConfig.containsKey("reset")) {
            return true;
        }

        if(!actionConfig.containsKey("stay")) {
            plugin.getLogger().warning("[TitleAction] No stay parameter was defined.");
            return false;
        }
        if(!actionConfig.get("stay").matches("\\d+") && !actionConfig.get("stay").equalsIgnoreCase("permanent")) {
            plugin.getLogger().warning("[TitleAction] Stay parameter wasn't a valid integer or permanent.");
            return false;
        }

        if(!actionConfig.containsKey("fadeIn")) {
            plugin.getLogger().warning("[TitleAction] No fadeIn parameter was defined.");
            return false;
        }
        if(!actionConfig.get("fadeIn").matches("\\d+")) {
            plugin.getLogger().warning("[TitleAction] fadeIn parameter wasn't a valid integer.");
            return false;
        }

        if(!actionConfig.containsKey("fadeOut")) {
            plugin.getLogger().warning("[TitleAction] No fadeOut parameter was defined.");
            return false;
        }
        if(!actionConfig.get("fadeOut").matches("\\d+")) {
            plugin.getLogger().warning("[TitleAction] fadeOut parameter wasn't a valid integer.");
            return false;
        }


        if(!actionConfig.containsKey("title")) {
            plugin.getLogger().warning("[TitleAction] No title parameter was defined.");
            return false;
        }

        return true;
    }
}
