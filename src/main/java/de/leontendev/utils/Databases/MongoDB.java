package de.leontendev.utils.Databases;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.level.Sound;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import de.leontendev.events.own.JobAddLevelEvent;
import de.leontendev.events.own.JobAddXpEvent;
import de.leontendev.utils.JobConfig;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.logging.Level;
import java.util.logging.Logger;

public class MongoDB {

    private static MongoClient mc;
    private static MongoCollection<Document> jobs;

    public static void connect(){
        if (!JobConfig.useMysql()) {
            String uri = JobConfig.mongoDBURI();
            mc = MongoClients.create(uri);
            MongoDatabase mongoDatabase = mc.getDatabase(JobConfig.mongoDBDatabase());
            jobs = mongoDatabase.getCollection(JobConfig.mongoDBCollection());
            Server.getInstance().getLogger().info(JobConfig.prefix() + "§aMongoDB successfully connected!");
            Logger mongoLogger = Logger.getLogger("org.mongodb.driver");
            mongoLogger.setLevel(Level.WARNING);
        }
    }

    public static void createPlayer(Player player){
        if (!exists(player)) {
            Document document = new Document("uuid", player.getUniqueId())
                    .append("currentjob", "")
                    .append("lumberjacklvl", 0)
                    .append("lumberjackxp", 0)
                    .append("hunterlvl", 0)
                    .append("hunterxp", 0)
                    .append("minerlvl", 0)
                    .append("minerxp", 0);
            jobs.insertOne(document);
        }
    }

    public static boolean exists(Player player){
        Document document = new Document("uuid", player.getUniqueId());
        Document found = jobs.find(document).first();
        return found != null;
    }

    public static void addJob(Player player, String job){
        Document document = new Document("uuid", player.getUniqueId());
        Document found = jobs.find(document).first();
        Bson newEntry = new Document("currentjob", job);
        Bson newEntrySet = new Document("$set", newEntry);
        assert found != null;
        jobs.updateOne(found, newEntrySet);
    }

    public static String getJob(Player player){
        Document document = new Document("uuid", player.getUniqueId());
        Document found = jobs.find(document).first();
        assert found != null;
        return found.getString("currentjob");
    }

    public static boolean hasJob(Player player){
        Document document = new Document("uuid", player.getUniqueId());
        Document found = jobs.find(document).first();
        assert found != null;
        return !found.getString("currentjob").equalsIgnoreCase("");
    }

    public static Integer getLvl(Player player, String job){
        Document document = new Document("uuid", player.getUniqueId());
        Document found = jobs.find(document).first();
        assert found != null;
        return found.getInteger(job.toLowerCase() + "lvl");
    }

    public static Integer getXP(Player player, String job){
        Document document = new Document("uuid", player.getUniqueId());
        Document found = jobs.find(document).first();
        assert found != null;
        return found.getInteger(job.toLowerCase() + "xp");
    }

    public static void addXP(Player player, int xp, String job){
        Document document = new Document("uuid", player.getUniqueId());
        Document found = jobs.find(document).first();
        int lvl = getLvl(player, job);
        int xpBefore = getXP(player, job);
        int fullxp = xp + xpBefore;
        int lvlxp = lvl * 100;
        if (fullxp >= lvlxp){
            addLvl(player, lvl + 1, job);
            resetXP(player, job);
            int fulllvl = lvl + 1;
            //TODO: Weiter führen!
            if (job.equalsIgnoreCase("lumberjack")) {
                player.sendTitle("-- §aLumberJack§f --", "§eLvl: §c" + fulllvl);
            }
            player.getLevel().addSound(player.getDirectionVector(), Sound.RANDOM_LEVELUP);
            Server.getInstance().getPluginManager().callEvent(new JobAddLevelEvent(player, job, lvl, lvl+1));
        }else {
            Bson newEntry = new Document(job + "xp", xp);
            Bson newEntrySet = new Document("$set", newEntry);
            assert found != null;
            jobs.updateOne(found, newEntrySet);
            Server.getInstance().getPluginManager().callEvent(new JobAddXpEvent(player, job, fullxp));
        }
    }

    public static void addLvl(Player player, int lvl, String job){
        Document document = new Document("uuid", player.getUniqueId());
        Document found = jobs.find(document).first();
        Bson newEntry = new Document(job + "lvl", lvl);
        Bson newEntrySet = new Document("$set", newEntry);
        assert found != null;
        jobs.updateOne(found, newEntrySet);
    }

    public static void resetXP(Player player, String job){
        Document document = new Document("uuid", player.getUniqueId());
        Document found = jobs.find(document).first();
        Bson newEntry = new Document(job + "xp", 0);
        Bson newEntrySet = new Document("$set", newEntry);
        assert found != null;
        jobs.updateOne(found, newEntrySet);
    }
}
