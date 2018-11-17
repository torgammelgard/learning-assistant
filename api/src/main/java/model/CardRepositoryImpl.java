package model;

import model.entities.Card;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class CardRepositoryImpl implements CardRepository {

    @Inject
    private CardDAO cardDAO;

    @Override
    public Optional<Card> getCard(long id) {
        return cardDAO.getCard(id);
    }

    @Override
    public List<Card> getCards(String collectionName) {
        return cardDAO.getCards();
    }

    @Override
    public boolean addCard(Card card, String collectionName) {
        return false;
    }

    @Override
    public boolean deleteCard(Card card, String collectionName) {
        return false;
    }

    @Override
    public boolean updateDate(Card cardToEdit, Card editedCard, String collectionName) {
        return false;
    }
}
