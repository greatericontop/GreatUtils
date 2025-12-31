package io.github.greatericontop.greatutils;

import io.github.greatericontop.greatutils.commandexecution.CancelTaskCommand;
import io.github.greatericontop.greatutils.commandexecution.CommandTaskManager;
import io.github.greatericontop.greatutils.commandexecution.ExecLaterCommand;
import io.github.greatericontop.greatutils.commandexecution.ExecLaterRNGCommand;
import io.github.greatericontop.greatutils.commandexecution.ExecTimerCommand;
import io.github.greatericontop.greatutils.commandexecution.MyTasksCommand;
import io.github.greatericontop.greatutils.commandexecution.SudoCommand;
import io.github.greatericontop.greatutils.kits.KitCommand;
import io.github.greatericontop.greatutils.warps.WarpCommand;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class GreatUtils extends JavaPlugin {

    public CommandTaskManager commandTaskManager;
    public YamlConfiguration kitConfig = null;
    public YamlConfiguration warpConfig = null;

    @Override
    public void onEnable() {
        commandTaskManager = new CommandTaskManager(this);

        kitConfig = YamlConfiguration.loadConfiguration(new File(this.getDataFolder(), "kits.yml"));
        if (kitConfig.getConfigurationSection("kits") == null) {
            kitConfig.createSection("kits");
        }
        warpConfig = YamlConfiguration.loadConfiguration(new File(this.getDataFolder(), "warps.yml"));
        if (warpConfig.getConfigurationSection("warps") == null) {
            warpConfig.createSection("warps");
        }

        this.getCommand("execlater").setExecutor(new ExecLaterCommand(this));
        this.getCommand("execlaterrng").setExecutor(new ExecLaterRNGCommand(this));
        this.getCommand("exectimer").setExecutor(new ExecTimerCommand(this));
        this.getCommand("mytasks").setExecutor(new MyTasksCommand(this));
        this.getCommand("canceltask").setExecutor(new CancelTaskCommand(this));
        this.getCommand("sudo").setExecutor(new SudoCommand());
        this.getCommand("kit").setExecutor(new KitCommand(this));
        this.getCommand("warp").setExecutor(new WarpCommand(this));

        Bukkit.getScheduler().runTaskTimer(this, this::saveAll, 2401L, 2401L);
    }

    @Override
    public void onDisable() {
        saveAll();
    }


    public void saveAll() {
        try {
            kitConfig.save(new File(this.getDataFolder(), "kits.yml"));
        } catch (Exception e) {
            this.getLogger().severe("Could not save kits data!");
        }
        try {
            warpConfig.save(new File(this.getDataFolder(), "warps.yml"));
        } catch (Exception e) {
            this.getLogger().severe("Could not save warps data!");
        }
    }

}
