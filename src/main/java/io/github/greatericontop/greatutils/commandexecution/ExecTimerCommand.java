package io.github.greatericontop.greatutils.commandexecution;

import io.github.greatericontop.greatutils.GreatUtils;
import io.github.greatericontop.greatutils.util.GreatCommands;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.List;

public class ExecTimerCommand implements CommandExecutor, TabCompleter {

    private final GreatUtils plugin;
    public ExecTimerCommand(GreatUtils plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length < 2) {
            sender.sendMessage("§cSpecify an interval and a number of times!");
            return false;
        }
        Integer maybeTimes = GreatCommands.argumentInteger(0, args);
        Integer times;
        Integer interval;
        if (maybeTimes == null) {
            // Other way
            interval = GreatCommands.argumentTime(0, args);
            times = GreatCommands.argumentInteger(1, args);
        } else {
            interval = GreatCommands.argumentTime(1, args);
            times = maybeTimes; // :times: has to be effectively final
        }
        if (times == null || interval == null) {
            sender.sendMessage("§cEnter a valid interval and number of times!");
            return false;
        }
        if (times <= 0) {
            sender.sendMessage("§cNumber of times must be positive!");
            return false;
        }
        if (interval <= 0) {
            sender.sendMessage("§cInterval must be positive!");
            return false;
        }

        String commandToRun = GreatCommands.argumentStringConsumeRest(2, args);
        if (commandToRun == null) {
            sender.sendMessage("§cEnter a command!");
            return false;
        }

        BukkitTask bt = new BukkitRunnable() {
            int timesLeft = times;
            @Override
            public void run() {
                if (timesLeft <= 0) {
                    this.cancel();
                    return;
                }
                Bukkit.dispatchCommand(sender, commandToRun);
                timesLeft--;
            }
        }.runTaskTimer(plugin, interval, interval);
        plugin.commandTaskManager.put(sender, bt, commandToRun);
        sender.sendMessage(String.format("§3Running §f%s §bx%d §3every §f%d §3ticks. §7(#%d)", commandToRun, times, interval, bt.getTaskId()));
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 1) {
            // Time or interval, just use time suggestion (it's a superset)
            return GreatCommands.tabCompleteTime(args[0], "<number of times>", "<interval>");
        }
        if (args.length == 2) {
            if (GreatCommands.argumentInteger(0, args) != null) {
                return GreatCommands.tabCompleteTime(args[1], "<interval>");
            } else {
                return List.of("<number of times>");
            }
        }
        return List.of("<command...>");
    }

}
