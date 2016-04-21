package model;

import com.mongodb.Block;
import com.mongodb.MongoClient;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import com.sun.javadoc.Doc;
import org.bson.Document;

import java.util.*;
import java.util.regex.Pattern;

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
                new Document("question", card.getQuestion())
                        .append("answerAlternatives", Arrays.asList(card.getAnswerAlternatives()))
                        .append("priority", card.getPriority().ordinal()));
    }

    public static boolean deleteCard(Card card, String collectionName) {
        MongoDatabase db = mongoClient.getDatabase(DB_NAME);

        DeleteResult deleteResult = db.getCollection(collectionName).deleteOne(new Document("question", card.getQuestion()));
        return deleteResult.getDeletedCount() > 0;
    }

    public static boolean editCard(Card cardToEdit, Card editedCard, String collectionName) {
        MongoDatabase db = mongoClient.getDatabase(DB_NAME);

        UpdateResult updateResult = db.getCollection(collectionName).updateOne(new Document("question", cardToEdit.getQuestion()),
                new Document("$set", new Document("question", editedCard.getQuestion())
                        .append("answerAlternatives", Arrays.asList(editedCard.getAnswerAlternatives()))
                        .append("priority", editedCard.getPriority().ordinal())));
        return updateResult.getModifiedCount() > 0;
    }

    public static List<String> getCollectionNames() {
        MongoDatabase db = mongoClient.getDatabase(DB_NAME);

        MongoIterable<String> collectionNames = db.listCollectionNames();

        ArrayList<String> names = new ArrayList<>();

        collectionNames.forEach(new Block<String>() {
            @Override
            public void apply(String s) {
                names.add(s);
            }
        });
        return names;
    }

    public static List<Card> getCollection(String collectionName) {
        MongoDatabase db = mongoClient.getDatabase(DB_NAME);

        FindIterable<Document> res = db.getCollection(collectionName).find();

        ArrayList<Card> cards = new ArrayList<>();

        res.forEach(new Block<Document>() {

            @Override
            public void apply(Document document) {
                cards.add(DBSource.documentToCard(document));
            }
        });

        return cards;
    }

    public static List<Card> search(String searchString, List<Integer> priorityFilter, String collectionName) {
        MongoDatabase db = mongoClient.getDatabase(DB_NAME);

        AggregateIterable<Document> iterable = db.getCollection(collectionName).aggregate(
                Arrays.asList(new Document("$match", new Document("question", Pattern.compile(searchString))
                        .append("priority", new Document("$in", Arrays.asList(priorityFilter.toArray()))))));


        ArrayList<Card> cards = new ArrayList<>();

        iterable.forEach(new Block<Document>() {
            @Override
            public void apply(Document document) {
                cards.add(DBSource.documentToCard(document));
            }
        });
        return cards;
    }

    public static void agg_search(String collectionName) {
        MongoDatabase db = mongoClient.getDatabase(DB_NAME);

        AggregateIterable<Document> iterable = db.getCollection(collectionName).aggregate(
                Arrays.asList(new Document("$group", new Document("_id", "$priority").append("count", new Document("$sum", 1))))
        );

        iterable.forEach(new Block<Document>() {
            @Override
            public void apply(Document document) {
                System.out.println(document.toJson());
            }
        });
    }

    public static Map<String, Integer> getStats(String collectionName) {
        HashMap<String, Integer> map = new HashMap<>();
        MongoDatabase db = mongoClient.getDatabase(DB_NAME);

        AggregateIterable<Document> iterable = db.getCollection(collectionName).aggregate(
                Arrays.asList(new Document("$group", new Document("_id", "$priority").append("count", new Document("$sum", 1))))
        );

        iterable.forEach(new Block<Document>() {
            @Override
            public void apply(Document document) {
                // the id will be equal to the Card.PRIORITY ordinals
                if (document.getInteger("_id") != null) {
                    Integer i = document.getInteger("_id");
                    map.put(Card.PRIORITY.values()[i].toString(), document.getInteger("count"));
                } else {
                    map.put(null, document.getInteger("count"));
                }
            }
        });

        System.out.println(map);

        return map;
    }

    private static Card documentToCard(Document document) {
        Card card = new Card();
        card.setQuestion(document.getString("question"));
        ArrayList<Object> ansAlts = (ArrayList<Object>) document.get("answerAlternatives");
        card.setAnswerAlternatives(ansAlts.toArray(new String[]{}));
        if (document.keySet().contains("priority")) {
            int priority = document.getInteger("priority");
            card.setPriority(Card.PRIORITY.values()[priority]);
        } else {
            card.setPriority(null);
        }
        return card;
    }
}
