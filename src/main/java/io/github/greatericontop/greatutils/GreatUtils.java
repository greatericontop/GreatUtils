package io.github.greatericontop.greatutils;

import io.github.greatericontop.greatutils.commandexecution.CancelTaskCommand;
import io.github.greatericontop.greatutils.commandexecution.CommandTaskManager;
import io.github.greatericontop.greatutils.commandexecution.ExecLaterCommand;
import io.github.greatericontop.greatutils.commandexecution.ExecLaterRNGCommand;
import io.github.greatericontop.greatutils.commandexecution.ExecTimerCommand;
import io.github.greatericontop.greatutils.commandexecution.MyTasksCommand;
import io.github.greatericontop.greatutils.commandexecution.SudoCommand;
import io.github.greatericontop.greatutils.kits.KitCommand;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class GreatUtils extends JavaPlugin {

    public CommandTaskManager commandTaskManager;
    public YamlConfiguration kitConfig = null;

    @Override
    public void onEnable() {
        commandTaskManager = new CommandTaskManager(this);
        File kitFile = new File(this.getDataFolder(), "kits.yml");

        kitConfig = YamlConfiguration.loadConfiguration(kitFile);
        if (kitConfig.getConfigurationSection("kits") == null) {
            kitConfig.createSection("kits");
        }

        this.getCommand("execlater").setExecutor(new ExecLaterCommand(this));
        this.getCommand("execlaterrng").setExecutor(new ExecLaterRNGCommand(this));
        this.getCommand("exectimer").setExecutor(new ExecTimerCommand(this));
        this.getCommand("mytasks").setExecutor(new MyTasksCommand(this));
        this.getCommand("canceltask").setExecutor(new CancelTaskCommand(this));
        this.getCommand("sudo").setExecutor(new SudoCommand());
        this.getCommand("kit").setExecutor(new KitCommand(this));

        Bukkit.getScheduler().runTaskTimer(this, this::saveAll, 2401L, 2401L);
    }


    public void saveAll() {
        try {
            kitConfig.save(new File(this.getDataFolder(), "kits.yml"));
        } catch (Exception e) {
            this.getLogger().severe("Could not save kits data!");
        }
    }

}
