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
            if(action.isEnabled(this.plugin)) {
                action.onAfk(event, this.plugin);
            }
        });
    }
    public void executeReturn(PlayerReturnEvent event) {
        actionSet.forEach(action -> {
            if(action.isEnabled(this.plugin)) {
                action.onReturn(event, this.plugin);
            }
        });
    }

    public void registerAction(ActionExecutor action) {
        actionSet.add(action);
    }
}
