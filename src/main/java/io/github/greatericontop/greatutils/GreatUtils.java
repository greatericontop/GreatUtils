package io.github.greatericontop.greatutils;

import io.github.greatericontop.greatutils.commandexecution.CommandTask;
import io.github.greatericontop.greatutils.commandexecution.CommandTaskManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class GreatUtils extends JavaPlugin {

    public CommandTaskManager commandTaskManager;

    @Override
    public void onEnable() {
        commandTaskManager = new CommandTaskManager(this);

    }

}
