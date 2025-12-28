package io.github.greatericontop.greatutils.commandexecution;

import io.github.greatericontop.greatutils.GreatUtils;
import io.github.greatericontop.greatutils.util.GreatCommands;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.scheduler.BukkitTask;

import java.util.List;

public class ExecLaterCommand implements CommandExecutor, TabCompleter {

    private final GreatUtils plugin;
    public ExecLaterCommand(GreatUtils plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Integer delay = GreatCommands.argumentTime(0, args);
        if (delay == null || delay < 0) {
            sender.sendMessage("§cInvalid delay. Examples: 20t, 1s, 5m");
            return false;
        }
        String commandToRun = GreatCommands.argumentStringConsumeRest(1, args);
        if (commandToRun == null) {
            sender.sendMessage("§cEnter a command!");
            return false;
        }
        BukkitTask bt = Bukkit.getScheduler().runTaskLater(plugin, () -> Bukkit.dispatchCommand(sender, commandToRun), delay);
        plugin.commandTaskManager.put(sender, bt, commandToRun);
        sender.sendMessage(String.format("§3Running §f%s §3in §f%d §3ticks. §7(#%d)", commandToRun, delay, bt.getTaskId()));
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 1) {
            return GreatCommands.tabCompleteTime(args[0], "<delay>");
        }
        return List.of("<command...>");
    }

}
