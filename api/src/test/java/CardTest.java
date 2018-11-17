import model.entities.Card;
import model.entities.CardImpl;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class CardTest {
    private static final int ID = 282828;
    private static final String QUESTION = "What is this";
    private static final String[] ANSWERS = {"This is what", "Or this"};

    @Test
    public void testCard() {
        Card card = new CardImpl();
        card.setId(ID);
        card.setQuestion(QUESTION);
        card.setAnswerAlternatives(ANSWERS);

        assertEquals(ID, card.getId());
        assertEquals(QUESTION, card.getQuestion());
        assertArrayEquals(ANSWERS, card.getAnswerAlternatives().toArray());
    }
}
