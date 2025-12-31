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

import io.github.greatericontop.greatutils.util.GreatCommands;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.List;

public class SudoCommand implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String targetPlayerString = GreatCommands.argumentString(0, args);
        Player targetPlayer = GreatCommands.argumentPlayer(0, args);
        String chatString = GreatCommands.argumentStringConsumeRest(1, args);

        if (targetPlayerString != null && targetPlayerString.equalsIgnoreCase("@a")) {
            if (chatString == null) {
                sender.sendMessage("§cSpecify what chat message or command to do!");
                return false;
            }
            for (Player player : Bukkit.getOnlinePlayers()) {
                player.chat(chatString);
            }
            return true;
        }

        if (targetPlayer == null) {
            sender.sendMessage("§cTarget player not found.");
            return false;
        }
        if (chatString == null) {
            sender.sendMessage("§cSpecify what chat message or command to do!");
            return false;
        }
        targetPlayer.chat(chatString);
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 1) {
            return GreatCommands.tabCompletePlayers(args[0], "@a");
        }
        return List.of("<chat or command...>");
    }

}
