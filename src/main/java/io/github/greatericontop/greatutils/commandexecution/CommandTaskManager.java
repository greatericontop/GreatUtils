package io.github.greatericontop.greatutils.commandexecution;

import io.github.greatericontop.greatutils.GreatUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class CommandTaskManager {

    public final Map<UUID, List<CommandTask>> commandTasks = new HashMap<>();

    private final GreatUtils plugin;
    public CommandTaskManager(GreatUtils plugin) {
        this.plugin = plugin;
    }

    public void put(CommandSender sender, BukkitTask task, String commandStr) {
        UUID uuid;
        if (sender instanceof Player p) {
            uuid = p.getUniqueId();
        } else if (sender instanceof ConsoleCommandSender) {
            uuid = UUID.fromString("00000000-0000-0000-0000-000000000000");
        } else {
            // e.g. command blocks
            return;
        }
        CommandTask commandTask = new CommandTask(commandStr, task.getTaskId());
        if (!commandTasks.containsKey(uuid)) {
            commandTasks.put(uuid, new ArrayList<>());
        }
        commandTasks.get(uuid).add(commandTask);
    }

    public @Nullable List<CommandTask> get(CommandSender sender) {
        UUID uuid;
        if (sender instanceof Player p) {
            uuid = p.getUniqueId();
        } else if (sender instanceof ConsoleCommandSender) {
            uuid = UUID.fromString("00000000-0000-0000-0000-000000000000");
        } else {
            return null;
        }
        return commandTasks.get(uuid);
    }

}
