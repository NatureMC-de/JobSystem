package de.leontendev.utils.Databases;

import cn.nukkit.Server;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import de.leontendev.utils.JobConfig;
import org.bson.Document;

import java.util.logging.Level;
import java.util.logging.Logger;

public class MongoDB {

    private static MongoClient mc;
    private static MongoCollection<Document> jobs;

    public static void connect(){
        if (JobConfig.useMongoDB()) {
            String uri = JobConfig.mongoDBURI();
            mc = MongoClients.create(uri);
            MongoDatabase mongoDatabase = mc.getDatabase(JobConfig.mongoDBDatabase());
            jobs = mongoDatabase.getCollection(JobConfig.mongoDBCollection());
            Server.getInstance().getLogger().info(JobConfig.prefix() + "§aMongoDB successfully connected!");
            Logger mongoLogger = Logger.getLogger("org.mongodb.driver");
            mongoLogger.setLevel(Level.WARNING);
        }
    }
}
