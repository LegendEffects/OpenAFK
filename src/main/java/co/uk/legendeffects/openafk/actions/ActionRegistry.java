package co.uk.legendeffects.openafk.actions;

import co.uk.legendeffects.openafk.OpenAFK;
import co.uk.legendeffects.openafk.events.PlayerAfkEvent;
import co.uk.legendeffects.openafk.events.PlayerReturnEvent;

import java.util.HashSet;
import java.util.Set;

public class ActionRegistry {
    private final Set<ActionExecutor> actionSet = new HashSet<>();
    private final OpenAFK plugin;

    public ActionRegistry(OpenAFK plugin) {
        this.plugin = plugin;
    }

    public void executeAfk(PlayerAfkEvent event) {
        actionSet.forEach(action -> {
            action.onAfk(event, this.plugin);
        });
    }
    public void executeReturn(PlayerReturnEvent event) {
        actionSet.forEach(action -> {
            action.onReturn(event, this.plugin);
        });
    }

    public void registerAction(ActionExecutor action) {
        if(action.isEnabled(this.plugin)) {
            actionSet.add(action);
        }
    }
}
