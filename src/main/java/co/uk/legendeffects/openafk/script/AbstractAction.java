package co.uk.legendeffects.openafk.script;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.Map;

public abstract class AbstractAction {
    private final String id;

    public AbstractAction(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    abstract public void execute(Player player, ActionType type, Map<String,String> config);

    public boolean verifySyntax(Map<String,String> actionConfig, Plugin plugin) {
        return true;
    }
}