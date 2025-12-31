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
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
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
        return prune(commandTasks.get(uuid));
    }

    private @Nullable List<CommandTask> prune(@Nullable List<CommandTask> tasks) {
        if (tasks == null)  return null;
        // Collect active task IDs into a hashset for O(1) lookup
        Set<Integer> activeTaskIds = new HashSet<>();
        for (BukkitTask bt : Bukkit.getScheduler().getPendingTasks()) {
            activeTaskIds.add(bt.getTaskId());
        }
        for (int i = tasks.size() - 1; i >= 0; i--) {
            if (!activeTaskIds.contains(tasks.get(i).taskId())) {
                tasks.remove(i);
            }
        }
        return tasks; // Modifies in place and returns reference to the same list
    }

}
