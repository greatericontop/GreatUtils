package io.github.greatericontop.greatutils.commandexecution;

import io.github.greatericontop.greatutils.GreatUtils;
import io.github.greatericontop.greatutils.util.GreatCommands;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.List;

public class CancelTaskCommand implements CommandExecutor, TabCompleter {

    private final GreatUtils plugin;
    public CancelTaskCommand(GreatUtils plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length < 1) {
            sender.sendMessage("§cSpecify what to cancel!");
            return false;
        }
        if (args[0].equalsIgnoreCase("all")) {
            List<CommandTask> tasks = plugin.commandTaskManager.get(sender);
            if (tasks == null || tasks.isEmpty()) {
                sender.sendMessage("§cYou have nothing to cancel!");
                return true;
            }
            for (CommandTask task : tasks) {
                Bukkit.getScheduler().cancelTask(task.taskId());
            }
            sender.sendMessage("§3Cancelled all your tasks!");
            return true;
        }
        int[] taskIds = GreatCommands.argumentIntegerConsumeRest(0, args);
        if (taskIds == null) {
            sender.sendMessage("§cTask IDs must be integers!");
            return false;
        }
        for (int taskId : taskIds) {
            Bukkit.getScheduler().cancelTask(taskId);
            sender.sendMessage("§3Cancelled task §f%d§3.".formatted(taskId));
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 1) {
            return List.of("<task number>", "all");
        }
        return List.of("<another task number>");
    }

}