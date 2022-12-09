package de.leontendev.utils.Databases;

import cn.nukkit.Server;
import de.leontendev.utils.JobConfig;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQL {

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
        if (JobConfig.useMysql()) {
            if (!isConnected()) {
                Class.forName("com.mysql.jdbc.Driver");
                connection = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + db + "?serverTimezone=UTC", user, pw);
                Server.getInstance().getLogger().info(JobConfig.prefix() + "§aDatabase successfully connected!");
            }
        }
    }

    public static void disconnect() throws SQLException {
        if (JobConfig.useMysql()) {
            if (isConnected()) {
                connection.close();
            }
        }
    }


}
