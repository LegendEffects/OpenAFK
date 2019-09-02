package co.uk.legendeffects.openafk.actions;

import co.uk.legendeffects.openafk.OpenAFK;
import co.uk.legendeffects.openafk.events.PlayerAfkEvent;
import co.uk.legendeffects.openafk.events.PlayerReturnEvent;

public final class MessageAction implements ActionExecutor {

    @Override
    public boolean isEnabled(OpenAFK plugin) {
        return plugin.getConfig().getBoolean("actions.message.enabled");
    }

    @Override
    public void onAfk(PlayerAfkEvent event, OpenAFK plugin) {
        event.getPlayer().sendMessage(OpenAFK.parse(event.getPlayer(), plugin.getConfig().getString("actions.message.message")));
    }

    @Override
    public void onReturn(PlayerReturnEvent event, OpenAFK plugin) {
        event.getPlayer().sendMessage(OpenAFK.parse(event.getPlayer(), plugin.getConfig().getString("actions.message.returnMessage")));
    }
}
