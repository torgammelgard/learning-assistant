package model;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.Arrays;

/**
 * Created by torgammelgard on 2016-04-11.
 */
public class DBSource {

    private static final String DB_NAME = "learning_assistant";
    private static final String COLLECTION_NAME = "countries";
    private static final MongoClient mongoClient = new MongoClient();

    private DBSource() {
    }

    public static MongoClient getMongoClient() {
        return mongoClient;
    }

    public static void closeMongoClient() {
        mongoClient.close();
    }

    public static boolean addCard(Card card) {
        MongoDatabase db = mongoClient.getDatabase(DB_NAME);

        db.getCollection(COLLECTION_NAME).insertOne(
                new Document("question", card.getQuestion()).append("answerAlternatives", Arrays.asList(card.getAnswerAlternatives())));
        System.out.println("Count is : " + db.getCollection(COLLECTION_NAME).count());

        return true;
    }

    public static Card getCard() {
        return null; // TODO
    }
}
