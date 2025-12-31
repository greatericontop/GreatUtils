package io.github.greatericontop.greatutils.warps;

import io.github.greatericontop.greatutils.GreatUtils;
import io.github.greatericontop.greatutils.util.GreatCommands;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

public class WarpCommand implements CommandExecutor, TabCompleter {

    private final GreatUtils plugin;
    public WarpCommand(GreatUtils plugin) {
        this.plugin = plugin;
    }

    public List<String> getAvailableWarps() {
        return new ArrayList<>(plugin.warpConfig.getConfigurationSection("warps").getKeys(false));
    }

    public boolean warpExists(String warpName) {
        return plugin.warpConfig.getString("warps." + warpName) != null;
    }

    public Location loadWarp(String warpName) {
        return plugin.warpConfig.getObject("warps."+warpName, Location.class);
    }

    public void saveWarp(Location location, String warpName) {
        plugin.warpConfig.set("warps."+warpName, location);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String warpName = GreatCommands.argumentString(0, args);
        String subOperation = GreatCommands.argumentString(1, args);
        if (warpName == null) {
            sender.sendMessage("§3Available warps: §f%s".formatted(String.join("§7, §f", getAvailableWarps())));
            return false;
        }
        if (!(sender instanceof Player player)) {
            sender.sendMessage("§cPlayers only!");
            return true;
        }

        if (subOperation == null) {
            if (!warpExists(warpName)) {
                player.sendMessage("§cWarp §f%s §cdoes not exist.".formatted(warpName));
                return true;
            }
            player.teleport(loadWarp(warpName));
            return true;
        }
        if (subOperation.equalsIgnoreCase("set")) {
            saveWarp(player.getLocation(), warpName);
            player.sendMessage("§3Saved warp §f%s§3.".formatted(warpName));
            return true;
        }
        if (subOperation.equalsIgnoreCase("delete")) {
            saveWarp(null, warpName);
            player.sendMessage("§3Deleted warp §f%s§3.".formatted(warpName));
            return true;
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 1) {
            return StringUtil.copyPartialMatches(args[0], getAvailableWarps(), new ArrayList<>(getAvailableWarps().size()));
        }
        if (args.length == 2) {
            return StringUtil.copyPartialMatches(args[1], List.of("update", "delete"), new ArrayList<>(2));
        }
        return null;
    }

}
