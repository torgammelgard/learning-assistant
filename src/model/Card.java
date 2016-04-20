package model;

/**
 * Created by torgammelgard on 2016-04-12.
 */
public class Card {
    public enum PRIORITY {LOW, MEDIUM, HIGH}

    private String question;
    private String[] answerAlternatives;

    private PRIORITY priority;

    public PRIORITY getPriority() {
        return priority;
    }

    public void setPriority(PRIORITY priority) {
        this.priority = priority;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String[] getAnswerAlternatives() {
        return answerAlternatives;
    }

    public void setAnswerAlternatives(String[] answerAlternatives) {
        this.answerAlternatives = answerAlternatives;
    }

    @Override
    public String toString() {
        return "Card with question (" + question + ")" + " and correct answer (" + answerAlternatives[0] + ")";
    }
}
