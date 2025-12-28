package io.github.greatericontop.greatutils.util;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GreatCommands {

    public static @Nullable String argumentString(int i, String[] args) {
        if (args.length <= i) {
            return null;
        }
        return args[i];
    }

    public static @Nullable String argumentStringFromChoices(int i, String[] args, String[] choices) {
        if (args.length <= i) {
            return null;
        }
        for (String choice : choices) {
            if (args[i].equals(choice)) {
                return choice;
            }
        }
        return null;
    }

    public static @Nullable String argumentStringConsumeRest(int iStart, String[] args) {
        if (args.length <= iStart) {
            return null;
        }
        return String.join(" ", Arrays.copyOfRange(args, iStart, args.length));
    }

    public static @Nullable Integer argumentInteger(int i, String[] args) {
        if (args.length <= i) {
            return null;
        }
        try {
            return Integer.parseInt(args[i]);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public static @Nullable int[] argumentIntegerConsumeRest(int iStart, String[] args) {
        if (args.length <= iStart) {
            return null;
        }
        int[] result = new int[args.length - iStart];
        for (int argIndex = iStart; argIndex < args.length; argIndex++) {
            try {
                result[argIndex - iStart] = Integer.parseInt(args[argIndex]);
            } catch (NumberFormatException e) {
                return null;
            }
        }
        return result;
    }

    public static @Nullable Player argumentPlayer(int i, String[] args) {
        if (args.length <= i) {
            return null;
        }
        return Bukkit.getPlayer(args[i]);
    }

    public static @Nullable Integer argumentTime(int i, String[] args) {
        if (args.length <= i) {
            return null;
        }
        String arg = args[i].toLowerCase();
        int multi;
        switch (arg.charAt(arg.length()-1)) {
            case 't' -> multi = 1;
            case 's' -> multi = 20;
            case 'm' -> multi = 1200;
            case 'h' -> multi = 72000;
            default -> {
                return null;
            }
        }
        try {
            return Integer.parseInt(arg.substring(0, arg.length()-1)) * multi;
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public static List<String> tabCompleteTime(String arg, String... paramNames) {
        if (arg.isEmpty()) {
            return List.of(paramNames);
        }
        if (arg.matches("^\\d+$")) {
            return List.of(arg+"t", arg+"s", arg+"m", arg+"h");
        }
        return null;
    }

    public static List<String> tabCompletePlayers(String arg, String... extraChoices) {
        List<String> suggestions = new ArrayList<>();
        for (String choice : extraChoices) {
            if (choice.toLowerCase().startsWith(arg.toLowerCase())) {
                suggestions.add(choice);
            }
        }
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (player.getName().toLowerCase().startsWith(arg.toLowerCase())) {
                suggestions.add(player.getName());
            }
        }
        return suggestions;
    }

}
