package net.achymake.harvester.commands;

import net.achymake.harvester.Harvester;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class HarvesterCommand implements CommandExecutor, TabCompleter {
    private Harvester getPlugin() {
        return Harvester.getInstance();
    }
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            if (args.length == 0) {
                Harvester.send((Player) sender, "&6" + getPlugin().getName() + ":&f " + getPlugin().getDescription().getVersion());
                return true;
            }
            if (args.length == 1) {
                if (args[0].equalsIgnoreCase("reload")) {
                    Harvester.reload();
                    Harvester.send((Player) sender, "&6Harvester:&f files reloaded");
                    return true;
                }
            }
        }
        if (sender instanceof ConsoleCommandSender) {
            if (args.length == 0) {
                Harvester.send((ConsoleCommandSender) sender, getPlugin().getName() + ": " + getPlugin().getDescription().getVersion());
                return true;
            }
            if (args.length == 1) {
                if (args[0].equalsIgnoreCase("reload")) {
                    Harvester.reload();
                    Harvester.send((ConsoleCommandSender) sender, "Harvester: files reloaded");
                    return true;
                }
            }
        }
        return false;
    }
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        List<String> commands = new ArrayList<>();
        if (args.length == 1){
            commands.add("reload");
        }
        return commands;
    }
}