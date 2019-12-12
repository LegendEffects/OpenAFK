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
        if(config.containsKey("stay")) stayFor = Integer.parseInt(config.get("stay"));
        else stayFor = 999999999;

        int fadeIn = Integer.parseInt(config.get("fadeIn"));
        int fadeOut = Integer.parseInt(config.get("fadeOut"));

        player.sendTitle(OpenAFK.parse(player, config.get("title")), OpenAFK.parse(player, config.getOrDefault("subtitle", "")), fadeIn, stayFor, fadeOut);
    }

    @Override
    public boolean verifySyntax(Map<String, String> actionConfig, Plugin plugin) {
        if(actionConfig.containsKey("reset")) {
            return true;
        }

        if(actionConfig.containsKey("permanent") && actionConfig.containsKey("stay")) {
            plugin.getLogger().warning("[TitleAction] Can only have Permanent or Stay parameters but both were found.");
            return false;
        }
        if(!actionConfig.containsKey("permanent") && !actionConfig.containsKey("stay")) {
            plugin.getLogger().warning("[TitleAction] No permanent or stay parameter was defined.");
            return false;
        }

        if(actionConfig.containsKey("stay") && !actionConfig.get("stay").matches("\\d+")) {
            plugin.getLogger().warning("[TitleAction] Stay parameter wasn't a valid integer.");
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
