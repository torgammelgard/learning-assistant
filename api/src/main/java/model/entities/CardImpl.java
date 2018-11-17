package model.entities;

import javax.persistence.Entity;
import java.util.Arrays;
import java.util.List;

@Entity
public class CardImpl implements Card {

    private long id;
    private String question;
    private String[] answerAlternatives;
    private Priority priority;

    public CardImpl() {
        question = "";
        answerAlternatives = new String[1];
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    @Override
    public Priority getPriority() {
        return priority;
    }

    @Override
    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    @Override
    public String getQuestion() {
        return question;
    }

    @Override
    public void setQuestion(String question) {
        this.question = question;
    }

    @Override
    public List<String> getAnswerAlternatives() {
        return Arrays.asList(answerAlternatives);
    }

    @Override
    public void setAnswerAlternatives(String[] answerAlternatives) {
        this.answerAlternatives = answerAlternatives;
    }

    @Override
    public String toString() {
        return "CardImpl with question (" + question + ")" + " and correct answer (" + answerAlternatives[0] + ")";
    }
}
