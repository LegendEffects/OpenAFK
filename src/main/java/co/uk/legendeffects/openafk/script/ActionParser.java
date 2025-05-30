package co.uk.legendeffects.openafk.script;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class ActionParser {
    private final Map<String, AbstractAction> actions = new HashMap<>();
    private final Map<String, List<Map<String, String>>> scripts = new HashMap<>();

    private final Plugin plugin;

    public ActionParser(Plugin plugin) {
        this.plugin = plugin;
    }

    // Parsing
    public void parse(Player player, ActionType firedFor, List<Map<String,String>> script) {
        script.forEach(line -> {
            AbstractAction action = actions.get(line.get("action"));
            action.execute(player, firedFor, line);
        });
    }

    // By script name
    public void parse(Player player, ActionType firedFor, String scriptName) {
        List<Map<String,String>> script = scripts.get(scriptName);
        if(script == null) {
            plugin.getLogger().warning("The script "+scriptName+" was attempted to be fired but couldn't be found in the config.");
            return;
        }

        this.parse(player, firedFor, script);
    }

    // Registering
    public void registerAction(AbstractAction action) {
        if(action instanceof Listener) {
            Bukkit.getServer().getPluginManager().registerEvents((Listener) action, this.plugin);
        }

        this.actions.put(action.getId(), action);
    }

    public void registerScript(String name, List<Map<?, ?>> script) {
        List<Map<String,String>> parsed = new LinkedList<>();
        AtomicInteger lineTracker = new AtomicInteger(1);

        script.forEach(unparsedLine -> {
            Map<String, String> line = new HashMap<>();
            int lineNum = lineTracker.getAndIncrement();

            // Parse the object because umm... types.
            unparsedLine.forEach((key, val) -> { line.put(String.valueOf(key), val == null ? null : String.valueOf(val)); });

            if(!line.containsKey("action")) {
                plugin.getLogger().warning("No action value is provided in script "+name+", on line "+lineNum+", skipping.");
                return;
            }
            if(!actions.containsKey(line.get("action"))) {
                plugin.getLogger().warning("Invalid action '"+line.get("action")+"' in script "+name+", on line "+lineNum+", skipping.");
                return;
            }

            if(!actions.get(line.get("action")).verifySyntax(line, this.plugin)) {
                plugin.getLogger().warning("Action "+actions.get(line.get("action")).getId()+" had an issue with the config provided.");
                return;
            }

            parsed.add(line);
        });

        this.scripts.put(name, parsed);
    }

    // Getting
    public Map<String, AbstractAction> getActions() {
        return actions;
    }

    public Map<String, List<Map<String, String>>> getScripts() {
        return scripts;
    }
}
