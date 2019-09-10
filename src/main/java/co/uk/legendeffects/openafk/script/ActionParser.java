package co.uk.legendeffects.openafk.script;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;

public class ActionParser {
    private HashMap<String, Action> actions = new HashMap<>();
    private HashMap<String, List<String>> scripts = new HashMap<>();

    public void parse(Player player, ActionType startAction, List<String> script) {
        script.forEach(line -> {
            String[] parts = line.split("\\|");
            String command = parts[0].toLowerCase();

            Action action = actions.get(command);
            if(action == null) {
                Bukkit.getLogger().log(Level.WARNING, "Action \""+command+"\" doesn't exist.");
                return;
            }

            String[] args = Arrays.copyOfRange(parts, 1, parts.length);

            action.execute(player, startAction, args);
        });
    }

    public void parse(Player player, ActionType type, String script) {
        parse(player, type, scripts.get(script));
    }

    public void registerAction(Action action) {
        actions.put(action.getId(), action);
    }
    public void registerScript(String name, List<String> script) {
        scripts.put(name, script);
    }
}
