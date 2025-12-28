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
import java.util.Random;

public class ExecLaterRNGCommand implements CommandExecutor, TabCompleter {

    private final Random rnd;
    private final GreatUtils plugin;
    public ExecLaterRNGCommand(GreatUtils plugin) {
        this.plugin = plugin;
        rnd = new Random();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Integer delayMin = GreatCommands.argumentTime(0, args);
        Integer delayMax = GreatCommands.argumentTime(1, args);
        if (delayMin == null || delayMin < 0 || delayMax == null || delayMax < 0) {
            sender.sendMessage("§cInvalid delay. Examples: 20t, 1s, 5m");
            return false;
        }
        if (delayMax < delayMin) {
            sender.sendMessage("§cMax delay must be greater than or equal to min delay!");
            return false;
        }
        String commandToRun = GreatCommands.argumentStringConsumeRest(2, args);
        if (commandToRun == null) {
            sender.sendMessage("§cEnter a command!");
            return false;
        }
        int delay = rnd.nextInt(delayMin, delayMax+1);
        BukkitTask bt = Bukkit.getScheduler().runTaskLater(plugin, () -> Bukkit.dispatchCommand(sender, commandToRun), delay);
        plugin.commandTaskManager.put(sender, bt, commandToRun);
        sender.sendMessage(String.format("§3Running §f%s §3in §f%d §3ticks. §7(#%d)", commandToRun, delay, bt.getTaskId()));
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 1) {
            return GreatCommands.tabCompleteTime(args[0], "<min delay>");
        }
        if (args.length == 2) {
            return GreatCommands.tabCompleteTime(args[1], "<max delay>");
        }
        return List.of("<command...>");
    }

}
