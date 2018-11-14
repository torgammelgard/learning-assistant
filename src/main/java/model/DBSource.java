package model;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import util.Logger;

import java.util.*;
import java.util.function.Consumer;
import java.util.regex.Pattern;

import static model.Prioritizable.*;

/**
 * Created by torgammelgard on 2016-04-11.
 */
public class DBSource {

    // Move to properties file
    private static final String DB_NAME = "learning_assistant";
    private static final String COLLECTION_NAME = "countries";
    private static final String KEY_QUESTION = "question";
    private static final String KEY_ANSWER_ALTERNATIVES = "answerAlternatives";
    private static final String KEY_PRIORITY = "Priority";
    private static final MongoClientURI mongoClientURI = new MongoClientURI("mongodb://root:example@127.0.0.1:27017");
    private static final MongoClient mongoClient = new MongoClient(mongoClientURI);

    private static MongoDatabase mongoDatabase = null;

    private DBSource() {
    }

    /**
     * @return the mongo client
     */
    public static MongoClient getMongoClient() {
        return mongoClient;
    }

    private static MongoDatabase getMongoDatabase() {
        if (mongoDatabase == null) {
            mongoDatabase = mongoClient.getDatabase(DB_NAME);
        }
        return mongoDatabase;
    }

    /**
     * Closes the mongo client
     */
    public static void closeMongoClient() {
        mongoClient.close();
    }

    /**
     * Adds a card to a collection
     *
     * @param card a card
     * @param collectionName a collection name
     */
    public static void addCard(Card card, String collectionName) {
        getMongoDatabase().getCollection(collectionName).insertOne(
            new Document(KEY_QUESTION, card.getQuestion())
                .append(KEY_ANSWER_ALTERNATIVES, Arrays.asList(card.getAnswerAlternatives().toArray()))
                .append(KEY_PRIORITY, card.getPriority().ordinal()));
    }

    /**
     * Deletes a card from a collection
     *
     * @param card       the card
     * @param collectionName the name of the collection
     * @return <code>true</code> if a card was deleted, <code>false</code> if not
     */
    public static boolean deleteCard(Card card, String collectionName) {
        if (collectionName == null) {
            Logger.log("CollectionName is null");
            return false;
        }

        Document document = new Document(KEY_QUESTION, card.getQuestion());
        DeleteResult deleteResult = mongoDatabase.getCollection(collectionName).deleteOne(document);
        return deleteResult.getDeletedCount() > 0;
    }

    /**
     * Updates a card
     *
     * @param cardToEdit     the card to edit
     * @param editedCard     the edited card
     * @param collectionName the name of the collection
     * @return <code>true</code> if update was successful, <code>false</code> if not
     */
    public static boolean editCard(Card cardToEdit, Card editedCard, String collectionName) {
        UpdateResult updateResult = getMongoDatabase().getCollection(collectionName)
            .updateOne(
                new Document(KEY_QUESTION, cardToEdit.getQuestion()),
                new Document("$set", new Document(KEY_QUESTION, editedCard.getQuestion())
                    .append(KEY_ANSWER_ALTERNATIVES, Arrays.asList(editedCard.getAnswerAlternatives().toArray()))
                    .append(KEY_PRIORITY, editedCard.getPriority().ordinal())));
        return updateResult.getModifiedCount() > 0;
    }

    /**
     * @return a list of collection names
     */
    public static List<String> getCollectionNames() {
        MongoIterable<String> collectionNames = getMongoDatabase().listCollectionNames();
        ArrayList<String> names = new ArrayList<>();
        collectionNames.into(names);
        return names;
    }

    /**
     * Gets the cards from a collection as a list
     *
     * @param collectionName the collection name
     * @return the cards as a list
     */
    public static List<Card> getCollection(String collectionName) {
        FindIterable<Document> documents = getMongoDatabase().getCollection(collectionName).find();
        ArrayList<Card> cards = new ArrayList<>();
        documents.map(DBSource::documentToCard).into(cards);
        return cards;
    }

    /**
     * Queries the collection using a search string and a filter
     *
     * @param searchString a search query
     * @param priorityFilter a priority filter
     * @param collectionName a collection name
     * @return a list of cards which matches the search query
     */
    public static List<Card> search(String searchString, List<Integer> priorityFilter, String collectionName) {
        AggregateIterable<Document> searchResult = getMongoDatabase().getCollection(collectionName).aggregate(
            Arrays.asList(
                new Document(
                    "$match",
                    new Document(KEY_QUESTION, Pattern.compile(searchString))
                        .append(
                            KEY_PRIORITY,
                            new Document(
                                "$in", Arrays.asList(priorityFilter.toArray()))
                        )
                )
            )
        );
        ArrayList<Card> cards = new ArrayList<>();
        searchResult.map(DBSource::documentToCard).into(cards);
        return cards;
    }

    /**
     * Queries the collection for information
     *
     * @param collectionName the name of the collection
     * @return a map of statistics
     */
    public static Map<String, Integer> getStats(String collectionName) {
        HashMap<String, Integer> map = new HashMap<>();
        AggregateIterable<Document> iterable = getMongoDatabase().getCollection(collectionName).aggregate(
            Arrays.asList(new Document("$group", new Document("_id", "$Priority").append("count", new Document("$sum", 1))))
        );

        iterable.forEach((Consumer<? super Document>) (document) -> {
            if (document.getInteger("_id") != null) {
                Integer i = document.getInteger("_id");
                map.put(Priority.values()[i].toString(), document.getInteger("count"));
            } else {
                map.put(null, document.getInteger("count"));
            }
        });
        return map;
    }

    /**
     * Creates a Card from a Mongo document
     *
     * @param document a Mongo document
     * @return a new card
     */
    private static Card documentToCard(Document document) {
        Card newCard = new CardImpl();
        newCard.setQuestion(document.getString(KEY_QUESTION));
        List ansAlts = (ArrayList) document.get(KEY_ANSWER_ALTERNATIVES);
        ArrayList<String> strings = new ArrayList<>();
        for (Object o : ansAlts) {
            if (o instanceof String) {
                strings.add((String) o);
            }
        }
        newCard.setAnswerAlternatives(strings.toArray(new String[]{}));
        if (document.keySet().contains(KEY_PRIORITY)) {
            int priority = document.getInteger(KEY_PRIORITY);
            newCard.setPriority(Priority.values()[priority]);
        } else {
            newCard.setPriority(null);
        }
        return newCard;
    }
}
