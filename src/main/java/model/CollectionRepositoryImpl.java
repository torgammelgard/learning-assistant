package model;

import model.entities.Card;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import java.util.List;

@RequestScoped
public class CollectionRepositoryImpl implements CollectionRepository {

    @Inject
    private CollectionDAO collectionDAO;

    @Override
    public List<Card> getCollection(String collectionName) {
        throw new NotImplementedException();
    }

    @Override
    public List<String> getCollectionNames() {
        return collectionDAO.getCollectionNames();
    }
}
