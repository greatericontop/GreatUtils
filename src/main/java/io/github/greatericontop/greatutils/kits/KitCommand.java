package io.github.greatericontop.greatutils.kits;

import io.github.greatericontop.greatutils.GreatUtils;
import io.github.greatericontop.greatutils.util.GreatCommands;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public class KitCommand implements CommandExecutor, TabCompleter {

    private final GreatUtils plugin;
    public KitCommand(GreatUtils plugin) {
        this.plugin = plugin;
    }

    public List<String> getAvailableKits() {
        Set<String> keys = plugin.kitConfig.getConfigurationSection("kits").getKeys(false);
        return new ArrayList<>(keys);
    }

    public ItemStack[] loadKit(String kitName) {
        YamlConfiguration yamlConfiguration = new YamlConfiguration();
        try {
            yamlConfiguration.loadFromString(plugin.kitConfig.getString("kits." + kitName));
        } catch (Exception e) {
            throw new RuntimeException();
        }
        return ((Collection<ItemStack>) yamlConfiguration.get("inventory")).toArray(ItemStack[]::new);
    }

    public void saveKit(Player player, String kitName) {
        YamlConfiguration yamlConfiguration = new YamlConfiguration();
        yamlConfiguration.set("inventory", player.getInventory().getContents());
        plugin.kitConfig.set("kits."+kitName, yamlConfiguration.saveToString());
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String kitName = GreatCommands.argumentString(0, args);
        String subOperation = GreatCommands.argumentString(1, args);
        if (kitName == null) {
            sender.sendMessage("§3Available kits: §f%s".formatted(String.join("§7, §f", getAvailableKits())));
            return false;
        }
        if (!(sender instanceof Player player)) {
            sender.sendMessage("§cPlayers only!");
            return true;
        }

        if (subOperation == null) {
            // Give kit
            ItemStack[] kit = loadKit(kitName);
            player.getInventory().setContents(kit);
            player.sendMessage("§3Gave kit §f%s §3to you.".formatted(kitName));
            return true;
        }
        if (subOperation.equalsIgnoreCase("update")) {
            // Update/create kit
            saveKit(player, kitName);
            player.sendMessage("§3Saved kit §f%s§3.".formatted(kitName));
            return true;
        }
        if (subOperation.equalsIgnoreCase("everyone")) {
            ItemStack[] kit = loadKit(kitName);
            for (Player p : plugin.getServer().getOnlinePlayers()) {
                p.getInventory().setContents(kit);
                p.sendMessage("§b%s §3gave kit §f%s §3to you.".formatted(player.getName(), kitName));
            }
            player.sendMessage("§3Gave kit §f%s §3to §beveryone§3.".formatted(kitName));
            return true;
        }
        if (subOperation.equalsIgnoreCase("delete")) {
            plugin.kitConfig.set("kits."+kitName, null);
            player.sendMessage("§3Deleted kit §f%s§3.".formatted(kitName));
            return true;
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 1) {
            return StringUtil.copyPartialMatches(args[0], getAvailableKits(), new ArrayList<>(getAvailableKits().size()));
        }
        if (args.length == 2) {
            return StringUtil.copyPartialMatches(args[1], List.of("update", "everyone", "delete"), new ArrayList<>(3));
        }
        return null;
    }

}

