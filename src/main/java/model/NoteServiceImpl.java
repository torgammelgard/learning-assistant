package model;

import java.util.List;
import java.util.Map;

public class NoteServiceImpl implements NoteService {

    @Override
    public Map<String, Integer> getStats(String collectionName) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<String> getDatabaseNames() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void deleteCard(Card card, String deckName) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<String> getCollectionNames() {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Card> getCollection(String deckName) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void editCard(Card originalCard, Card updatedCard, String deckName) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Card> search(String searchString, List<Integer> filter, String deckName) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void addCard(Card card, String deckName) {
        throw new UnsupportedOperationException();
    }
}
