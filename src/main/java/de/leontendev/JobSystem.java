package de.leontendev;

import cn.nukkit.Server;
import cn.nukkit.plugin.PluginBase;
import de.leontendev.commands.JobCMD;
import de.leontendev.events.onBreak;
import de.leontendev.events.onJoin;
import de.leontendev.utils.Databases.MongoDB;
import de.leontendev.utils.Databases.MySQL;
import de.leontendev.utils.JobConfig;

import java.sql.SQLException;

public class JobSystem extends PluginBase {

    public static JobSystem plugin;
    private JobConfig config;

    @Override
    public void onEnable() {
        plugin = this;
        config = new JobConfig(plugin);
        config.createDefault();
        registerCommands();
        registerEvents();
        connectDataBases();
        Server.getInstance().getLogger().info(JobConfig.prefix() + "§aThe plugin has been activated!");
    }

    @Override
    public void onDisable(){
        Server.getInstance().getLogger().info(JobConfig.prefix() + "§aThe plugin has been disabled!");
        try {
            MySQL.disconnect();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void registerCommands(){
        getServer().getCommandMap().register("", new JobCMD(JobConfig.commandName(), JobConfig.commandDescription(), JobConfig.commandUsageMessage(), new String[]{}));
    }

    public void registerEvents(){
        getServer().getPluginManager().registerEvents(new onBreak(), this);
        getServer().getPluginManager().registerEvents(new onJoin(), this);
    }

    public void connectDataBases(){
        try {
            MySQL.connect();
        } catch (SQLException | ClassNotFoundException e) {
            Server.getInstance().getLogger().info(JobConfig.prefix() + "§cCan't connect to mysql database!");
            e.printStackTrace();
        }
        MongoDB.connect();
    }
}
