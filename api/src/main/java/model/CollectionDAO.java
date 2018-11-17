package model;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

import static model.DatabaseHelper.DB_NAME;

public class CollectionDAO {

    private MongoDatabase mongoDatabase;

    @Inject
    @MongoFromContainer
    private MongoClient mongoClient;

    @PostConstruct
    public void init() {
        this.mongoDatabase = mongoClient.getDatabase(DB_NAME);
    }

    public List<String> getCollectionNames() {
        List<String> collectionNames = new ArrayList<>();
        mongoDatabase.listCollectionNames().into(collectionNames);
        return collectionNames;
    }
}
