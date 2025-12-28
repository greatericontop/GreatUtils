package io.github.greatericontop.greatutils.commandexecution;

import io.github.greatericontop.greatutils.GreatUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.List;

public class MyTasksCommand implements CommandExecutor {

    private final GreatUtils plugin;
    public MyTasksCommand(GreatUtils plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        List<CommandTask> tasks = plugin.commandTaskManager.get(sender);
        if (tasks == null || tasks.isEmpty()) {
            sender.sendMessage("§3You have no tasks. Make some with §b/execlater§3!");
            return true;
        }
        sender.sendMessage("§3Your tasks:");
        for (CommandTask ct : tasks) {
            sender.sendMessage(String.format("§3[#§f%d§3] §f%s", ct.taskId(), ct.command()));
        }
        sender.sendMessage("§3Use §b/canceltask §3to cancel a task.");
        return true;
    }

}
