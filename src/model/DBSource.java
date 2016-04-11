package model;

import com.mongodb.MongoClient;

/**
 * Created by torgammelgard on 2016-04-11.
 */
public class DBSource {

    private static final MongoClient mongoClient = new MongoClient();

    private DBSource() {
    }

    public static MongoClient getMongoClient() {
        return mongoClient;
    }

    public static void closeMongoClient() {
        mongoClient.close();
    }
}
