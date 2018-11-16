import model.entities.Card;
import model.entities.CardImpl;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class CardImplTest {
    private static final String QUESTION = "Test question";
    private static final List<String> ANSWERS = Arrays.asList("A", "B");

    @Test
    public void testCard() {
        Card card = new CardImpl();
        card.setQuestion(QUESTION);
        card.setAnswerAlternatives(ANSWERS.toArray(new String[0]));

        Assert.assertEquals(QUESTION, card.getQuestion());
    }
}
