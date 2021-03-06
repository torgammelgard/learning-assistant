package model.entities;

import model.Prioritizable;

import java.util.List;

/**
 * A card with one question and multiple answers.
 */
public interface Card extends Prioritizable {

    long getId();

    void setId(long id);

    String getQuestion();

    void setQuestion(String question);

    List<String> getAnswerAlternatives();

    void setAnswerAlternatives(String[] answerAlternatives);
}
