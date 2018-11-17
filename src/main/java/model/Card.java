package model;

import java.util.List;

public interface Card {

    String getQuestion();

    void setQuestion(String question);

    List<String> getAnswerAlternatives();

    void setAnswerAlternatives(String[] answerAlternatives);

    Priority getPriority();

    void setPriority(Priority priority);
}
