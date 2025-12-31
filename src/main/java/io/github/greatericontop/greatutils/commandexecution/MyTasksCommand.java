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
