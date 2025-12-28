package io.github.greatericontop.greatutils;

import io.github.greatericontop.greatutils.commandexecution.CancelTaskCommand;
import io.github.greatericontop.greatutils.commandexecution.CommandTaskManager;
import io.github.greatericontop.greatutils.commandexecution.ExecLaterCommand;
import io.github.greatericontop.greatutils.commandexecution.ExecLaterRNGCommand;
import io.github.greatericontop.greatutils.commandexecution.ExecTimerCommand;
import io.github.greatericontop.greatutils.commandexecution.MyTasksCommand;
import io.github.greatericontop.greatutils.commandexecution.SudoCommand;
import org.bukkit.plugin.java.JavaPlugin;

public class GreatUtils extends JavaPlugin {

    public CommandTaskManager commandTaskManager;

    @Override
    public void onEnable() {
        commandTaskManager = new CommandTaskManager(this);

        this.getCommand("execlater").setExecutor(new ExecLaterCommand(this));
        this.getCommand("execlaterrng").setExecutor(new ExecLaterRNGCommand(this));
        this.getCommand("exectimer").setExecutor(new ExecTimerCommand(this));
        this.getCommand("mytasks").setExecutor(new MyTasksCommand(this));
        this.getCommand("canceltask").setExecutor(new CancelTaskCommand(this));
        this.getCommand("sudo").setExecutor(new SudoCommand());
    }

}
