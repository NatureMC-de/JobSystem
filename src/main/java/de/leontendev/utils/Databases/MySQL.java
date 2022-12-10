package de.leontendev.utils.Databases;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.level.Sound;
import de.leontendev.events.own.JobAddLevelEvent;
import de.leontendev.utils.JobConfig;

import java.sql.*;
import java.util.UUID;

public class MySQL {

    private static final boolean use = JobConfig.useMysql();

    private static String host = JobConfig.mysqlHost();
    private static int port = JobConfig.mysqlPort();
    private static String db = JobConfig.mysqlDatabase();
    private static String user = JobConfig.mysqlUsername();
    private static String pw = JobConfig.mysqlPassword();

    public static Connection connection;

    public static boolean isConnected(){
        return (connection == null ? false : true);
    }

    public static void connect() throws SQLException, ClassNotFoundException {
        if (use) {
            if (!isConnected()) {
                Class.forName("com.mysql.jdbc.Driver");
                connection = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + db + "?serverTimezone=UTC", user, pw);
                Server.getInstance().getLogger().info(JobConfig.prefix() + "§aDatabase successfully connected!");
                createTable();
            }
        }
    }

    public static void disconnect() throws SQLException {
        if (use) {
            if (isConnected()) {
                connection.close();
            }
        }
    }

    public static void createTable(){
        if (use){
            PreparedStatement ps;
            try {
                ps = connection.prepareStatement("CREATE TABLE IF NOT EXISTS jobs"
                        + "(UUID VARCHAR(100), CURRENTJOB VARCHAR(100), LUMBERJACKLVL INT(100), LUMBERJACKXP INT(100), HUNTERLVL INT(100), HUNTERXP INT(100), MINERLVL INT(100), MINERXP INT(100), PRIMARY KEY (UUID))");
                ps.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    public static void createPlayer(UUID uuid){
        try {
            if (!exists(uuid)){
                PreparedStatement ps2 = connection.prepareStatement("INSERT INTO jobs (UUID, CURRENTJOB, LUMBERJACKLVL, LUMBERJACKXP, HUNTERLVL, HUNTERXP, MINERLVL, MINERXP) VALUES (?, ?, ?, ?, ?, ?, ? ,?)");
                ps2.setString(1, uuid.toString());
                ps2.setString(2, "");
                ps2.setInt(3, 0);
                ps2.setInt(4, 0);
                ps2.setInt(5, 0);
                ps2.setInt(6, 0);
                ps2.setInt(7, 0);
                ps2.setInt(8, 0);
                ps2.executeUpdate();
                return;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static boolean exists(UUID uuid){
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM jobs WHERE UUID=?");
            ps.setString(1, uuid.toString());
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()){
                return true;
            }
            return false;
        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    public static void addJob(UUID uuid, String job){
        try {
            PreparedStatement ps = connection.prepareStatement("UPDATE jobs SET CURRENTJOB=? WHERE UUID=?");
            ps.setString(1, job);
            ps.setString(2, uuid.toString());
            ps.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static String getJob(UUID uuid){
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT CURRENTJOB FROM jobs WHERE UUID=?");
            ps.setString(1, uuid.toString());
            ResultSet rs = ps.executeQuery();
            String job = "";
            if (rs.next()){
                job = rs.getString("CURRENTJOB");
                return job;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return "";
    }

    public static boolean hasJob(UUID uuid){
        if (getJob(uuid).equalsIgnoreCase("")){
            return false;
        }else {
            return true;
        }
    }

    public static Integer getLvl(UUID uuid, String job){
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT " +  job.toUpperCase() + "LVL FROM jobs WHERE UUID=?");
            ps.setString(1, uuid.toString());
            ResultSet rs = ps.executeQuery();
            int lvl = 0;
            if (rs.next()){
                lvl = rs.getInt(job.toUpperCase() + "LVL");
                return lvl;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return 0;
    }

    public static Integer getXP(UUID uuid, String job){
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT " +  job.toUpperCase() + "XP FROM jobs WHERE UUID=?");
            ps.setString(1, uuid.toString());
            ResultSet rs = ps.executeQuery();
            int xp = 0;
            if (rs.next()){
                xp = rs.getInt(job.toUpperCase() + "XP");
                return xp;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return 0;
    }

    public static void addXP(UUID uuid, int xp, String job){
        int lvl = getLvl(uuid, job);
        int playerxp = getXP(uuid, job);
        try {
            PreparedStatement ps = connection.prepareStatement("UPDATE jobs SET " + job.toUpperCase() + "XP=? WHERE UUID=?");
            int fullxp = xp + playerxp;
            int lvlxp = lvl * 100;
            if (fullxp >= lvlxp){
                addLVL(uuid, lvl + 1, job);
                resetXP(uuid, job);
                Player player = Server.getInstance().getPlayer(uuid).get();
                int fulllvl = lvl + 1;
                //TODO: Weiter führen!
                if (job.equalsIgnoreCase("lumberjack")) {
                    player.sendTitle("-- §aLumberJack§f --", "§eLvl: §c" + fulllvl);
                }
                player.getLevel().addSound(player.getDirectionVector(), Sound.RANDOM_LEVELUP);
                Server.getInstance().getPluginManager().callEvent(new JobAddLevelEvent(Server.getInstance().getPlayer(uuid).get(), job, lvl, lvl+1));
            }else {
                ps.setInt(1, fullxp);
                ps.setString(2, uuid.toString());
                ps.executeUpdate();
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static void addLVL(UUID uuid, int lvl, String job){
        try {
            PreparedStatement ps = connection.prepareStatement("UPDATE jobs SET " + job.toUpperCase() + "LVL=? WHERE UUID=?");
            ps.setInt(1, lvl);
            ps.setString(2, uuid.toString());
            ps.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static void resetXP(UUID uuid, String job){
        try {
            PreparedStatement ps = connection.prepareStatement("UPDATE jobs SET " + job.toUpperCase() + "XP=? WHERE UUID=?");
            ps.setInt(1, 0);
            ps.setString(2, uuid.toString());
            ps.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
