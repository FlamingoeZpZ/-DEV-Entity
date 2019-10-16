package dev.dbdh.Discord.Utilities;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.Arrays;

public class Database {
    
    private static MongoClient client;
    private static MongoDatabase db;

    public static void connect() {
        MongoCredential credentials = MongoCredential.createCredential("Entity", "admin", System.getenv("ENTITYSQLPASSWORD").toCharArray());
        client = new MongoClient(new ServerAddress("localhost", 27018), Arrays.asList(credentials));
        db = client.getDatabase("Entity_DataBase");
    }

    public static MongoCollection<Document> getCollection(String collection) {
        return db.getCollection(collection);
    }

    public static void close() {
        client.close();
    }

}
