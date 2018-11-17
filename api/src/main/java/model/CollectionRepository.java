package model;

import model.entities.Card;

import java.util.List;

public interface CollectionRepository {
    List<Card> getCollection(String collectionName);
    List<String> getCollectionNames();
}
