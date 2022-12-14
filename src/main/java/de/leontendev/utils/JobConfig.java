package de.leontendev.utils;

import cn.nukkit.utils.Config;
import de.leontendev.JobSystem;


import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class JobConfig {

    private final JobSystem plugin;
    private static Config config;
    private final File file;

    public JobConfig(JobSystem plugin) {
        this.plugin = plugin;
        this.file = new File(plugin.getDataFolder(), "config.yml");
        config = new Config(this.file, 2);
    }

    public void createDefault() {
        List<String> strings = new ArrayList<>();
        strings.add("world");
        this.addDefault("jobsystem.prefix", "§cJobSystem §f» ");
        this.addDefault("jobsystem.command.name", "job");
        this.addDefault("jobsystem.command.description", "Take a job!");
        this.addDefault("jobsystem.command.usagemessage", "Please use /job!");
        this.addDefault("jobsystem.database.mysql.usemysql", true);
        this.addDefault("jobsystem.database.mysql.host", "localhost");
        this.addDefault("jobsystem.database.mysql.port", 3306);
        this.addDefault("jobsystem.database.mysql.database", "mc");
        this.addDefault("jobsystem.database.mysql.username", "root");
        this.addDefault("jobsystem.database.mysql.password", "12345");
        this.addDefault("jobsystem.database.mongodb.uri", "mongodb://localhost:27017");
        this.addDefault("jobsystem.database.mongodb.database", "mc");
        this.addDefault("jobsystem.database.mongodb.collection", "jobs");
        this.addDefault("jobsystem.settings.useui", true);
        this.addDefault("jobsystem.settings.allowedworlds", strings);
        this.addDefault("jobsystem.jobs.lumberjack.xp", 5);
        this.addDefault("jobsystem.jobs.hunter.xp", 5);
        this.addDefault("jobsystem.jobs.miner.xp", 5);
    }

    public static String prefix() {
        return config.getString("jobsystem.prefix");
    }

    public static String commandName(){
        return config.getString("jobsystem.command.name");
    }

    public static String commandDescription(){
        return config.getString("jobsystem.command.description");
    }

    public static String commandUsageMessage(){
        return config.getString("jobsystem.command.usagemessage");
    }

    public static boolean useMysql(){
        return config.getBoolean("jobsystem.database.mysql.usemysql");
    }

    public static String mysqlHost(){
        return config.getString("jobsystem.database.mysql.host");
    }

    public static Integer mysqlPort(){
        return config.getInt("jobsystem.database.mysql.port");
    }

    public static String mysqlDatabase(){
        return config.getString("jobsystem.database.mysql.database");
    }

    public static String mysqlUsername(){
        return config.getString("jobsystem.database.mysql.username");
    }

    public static String mysqlPassword(){
        return config.getString("jobsystem.database.mysql.password");
    }

    public static String mongoDBURI(){
        return config.getString("jobsystem.database.mongodb.uri");
    }

    public static String mongoDBDatabase(){
        return config.getString("jobsystem.database.mongodb.database");
    }

    public static String mongoDBCollection(){
        return config.getString("jobsystem.database.mongodb.collection");
    }

    public static boolean useUI(){
        return config.getBoolean("jobsystem.settings.useui");
    }

    public static java.util.List allowedWorlds(){
        return config.getStringList("jobsystem.settings.allowedworlds");
    }

    public static Integer lumberjackXP(){
        return config.getInt("jobsystem.jobs.lumberjack.xp");
    }

    public static Integer hunterXP(){
        return config.getInt("jobsystem.jobs.hunter.xp");
    }

    public static Integer minerXP(){
        return config.getInt("jobsystem.jobs.miner.xp");
    }



    public void addDefault(String path, Object object) {
        if (!config.exists(path)) {
            config.set(path, object);
            config.save(this.file);
        }
    }
}
