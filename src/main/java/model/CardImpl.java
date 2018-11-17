package model;

import java.util.Arrays;
import java.util.List;

public class CardImpl implements Card {

    private String question;
    private List<String> answerAlternatives;
    private Priority priority;

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
        return answerAlternatives;
    }

    @Override
    public void setAnswerAlternatives(String[] answerAlternatives) {
        this.answerAlternatives = Arrays.asList(answerAlternatives);
    }

    @Override
    public Priority getPriority() {
        return priority;
    }

    @Override
    public void setPriority(Priority priority) {
        this.priority = priority;
    }
}
