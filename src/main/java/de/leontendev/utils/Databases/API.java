package de.leontendev.utils.Databases;

import cn.nukkit.Player;
import de.leontendev.utils.JobConfig;

public class API {

    private static final boolean useMysql = JobConfig.useMysql();

    public static void createPlayer(Player player){
        if (useMysql){
            MySQL.createPlayer(player.getUniqueId());
        }else {
            MongoDB.createPlayer(player);
        }
    }

    public static void addJob(Player player, String job){
        if (useMysql){
            MySQL.addJob(player.getUniqueId(), job);
        }else {
            MongoDB.addJob(player, job);
        }
    }

    public static String getJob(Player player){
        if (useMysql){
            return MySQL.getJob(player.getUniqueId());
        }else {
            return MongoDB.getJob(player);
        }
    }

    public static boolean hasJob(Player player){
        if (useMysql){
            return MySQL.hasJob(player.getUniqueId());
        }else {
            return MongoDB.hasJob(player);
        }
    }

    public static Integer getLvl(Player player, String job){
        if (useMysql){
            return MySQL.getLvl(player.getUniqueId(), job);
        }else {
            return MongoDB.getLvl(player, job);
        }
    }

    public static Integer getXP(Player player, String job){
        if (useMysql){
            return MySQL.getXP(player.getUniqueId(), job);
        }else {
            return MongoDB.getXP(player, job);
        }
    }

    public static void addXP(Player player, int xp, String job){
        if (useMysql){
            MySQL.addXP(player.getUniqueId(), xp, job);
        }else {
            MongoDB.addXP(player, xp, job);
        }
    }

    public static void addLvl(Player player, int lvl, String job){
        if (useMysql){
            MySQL.addLVL(player.getUniqueId(), lvl, job);
        }else {
            MongoDB.addLvl(player, lvl, job);
        }
    }

    public static void resetXP(Player player, String job){
        if (useMysql){
            MySQL.resetXP(player.getUniqueId(), job);
        }else {
            MongoDB.resetXP(player, job);
        }
    }
}
