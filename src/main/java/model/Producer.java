package model;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

import javax.enterprise.inject.Produces;
import javax.inject.Singleton;

@Singleton
public class Producer {

    private static final MongoClientURI MONGO_CLIENT_URI_FROM_HOST = new MongoClientURI("mongodb://root:example@localhost:27017");

    // TODO - 'mongo-db' is the name of the Mongodb docker container
    private static final MongoClientURI MONGO_CLIENT_URI_FROM_CONTAINER = new MongoClientURI("mongodb://root:example@mongo-db:27017");

    @Produces
    @MongoFromContainer
    public MongoClient mongoClientFromContainer() {
        return new MongoClient(MONGO_CLIENT_URI_FROM_CONTAINER);
    }

    @Produces
    @MongoFromHost
    public MongoClient mongoClientFromHost() {
        return new MongoClient(MONGO_CLIENT_URI_FROM_HOST);
    }
}
