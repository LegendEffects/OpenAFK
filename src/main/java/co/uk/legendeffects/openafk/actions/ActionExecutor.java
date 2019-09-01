package co.uk.legendeffects.openafk.actions;

import co.uk.legendeffects.openafk.OpenAFK;
import co.uk.legendeffects.openafk.events.PlayerAfkEvent;
import co.uk.legendeffects.openafk.events.PlayerReturnEvent;

public interface ActionExecutor {
    boolean isEnabled(OpenAFK plugin);

    void onAfk(PlayerAfkEvent event, OpenAFK plugin);

    void onReturn(PlayerReturnEvent event, OpenAFK plugin);
}
