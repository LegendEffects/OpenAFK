package co.uk.legendeffects.openafk.script;

import org.bukkit.entity.Player;

public abstract class Action {
    private final String id;

    public Action(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    abstract public void execute(Player player, ActionType type, String[] args);
}