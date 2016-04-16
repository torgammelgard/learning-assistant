package model;

import com.mongodb.Block;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.DeleteResult;
import org.bson.Document;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by torgammelgard on 2016-04-11.
 */
public class DBSource {

    private static final String DB_NAME = "learning_assistant";
    private static final MongoClient mongoClient = new MongoClient();

    private DBSource() {
    }

    public static MongoClient getMongoClient() {
        return mongoClient;
    }

    public static void closeMongoClient() {
        mongoClient.close();
    }

    public static void addCard(Card card, String collectionName) {
        MongoDatabase db = mongoClient.getDatabase(DB_NAME);

        db.getCollection(collectionName).insertOne(
                new Document("question", card.getQuestion()).append("answerAlternatives", Arrays.asList(card.getAnswerAlternatives())));
    }

    public static boolean deleteCard(Card card, String collectionName) {
        MongoDatabase db = mongoClient.getDatabase(DB_NAME);

        DeleteResult deleteResult = db.getCollection(collectionName).deleteOne(new Document("question", card.getQuestion()));
        return deleteResult.getDeletedCount() > 0;
    }

    public static List<Card> getCollection(String collectionName) {
        MongoDatabase db = mongoClient.getDatabase(DB_NAME);

        FindIterable<Document> res = db.getCollection(collectionName).find();

        ArrayList<Card> cards = new ArrayList<>();

        res.forEach(new Block<Document>() {

            @Override
            public void apply(Document document) {
                Card card = new Card();
                card.setQuestion((String) document.get("question"));
                ArrayList<Object> ansAlts = (ArrayList<Object>) document.get("answerAlternatives");
                card.setAnswerAlternatives(ansAlts.toArray(new String[]{}));
                cards.add(card);
            }
        });

        return cards;
    }
}
