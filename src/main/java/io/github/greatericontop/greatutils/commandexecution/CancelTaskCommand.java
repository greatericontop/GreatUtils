package io.github.greatericontop.greatutils.commandexecution;

/*
 * Copyright (C) 2025-present greateric.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

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