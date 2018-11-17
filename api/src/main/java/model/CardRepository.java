package model;

import model.entities.Card;

import java.util.List;

public interface CardRepository {
    Card getCard(long id);
    List<Card> getCards(String collectionName);
    boolean addCard(Card card, String collectionName);
    boolean deleteCard(Card card, String collectionName);
    boolean updateDate(Card cardToEdit, Card editedCard, String collectionName);
}
