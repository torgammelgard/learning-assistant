package model;

import java.util.List;
import java.util.Map;

public interface NoteService {

    Map<String, Integer> getStats(String collectionName);
    List<String> getDatabaseNames();
    void deleteCard(Card card, String deckName);
    List<String> getCollectionNames();
    List<Card> getCollection(String deckName);
    void editCard(Card originalCard, Card updatedCard, String deckName);
    List<Card> search(String searchString, List<Integer> filter, String deckName);
    void addCard(Card card, String deckName);
}
